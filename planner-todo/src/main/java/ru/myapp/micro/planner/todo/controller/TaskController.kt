package ru.myapp.micro.planner.todo.controller

import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ru.myapp.micro.planner.entity.Task
import ru.myapp.micro.planner.todo.search.TaskSearchValues
import ru.myapp.micro.planner.todo.service.TaskService
import ru.myapp.micro.planner.utils.rest.resttemplate.UserRestBuilder
import java.text.ParseException
import java.util.*

@RestController
@RequestMapping("/task")
class TaskController(
    private val taskService: TaskService,
    private val userRestBuilder: UserRestBuilder
) {

    companion object {
        const val ID_COLUMN = "id"
    }

    @PostMapping("/add")
    fun add(@RequestBody task: Task): ResponseEntity<Any> {

        if (task.id != null && task.id != 0L) {
            return ResponseEntity<Any>("redundant param: id MUST be null", HttpStatus.NOT_ACCEPTABLE)
        }

        if (task.title == null || task.title!!.trim().isEmpty()) {
            return ResponseEntity<Any>("missed param: title", HttpStatus.NOT_ACCEPTABLE)
        }

        if (task.userId == null || task.userId == 0L) {
            return ResponseEntity<Any>("missed param: userId MUST not be null", HttpStatus.NOT_ACCEPTABLE)
        }

        return if (userRestBuilder.userExists(task.userId!!)) {
            ResponseEntity.ok(taskService.add(task))
        } else {
            ResponseEntity<Any>("user id = " + task.userId + " not found", HttpStatus.NOT_ACCEPTABLE)
        }
    }

    @PutMapping("/update")
    fun update(@RequestBody task: Task): ResponseEntity<Any> {

        if (task.id == null || task.id == 0L) {
            return ResponseEntity<Any>("missed param: id", HttpStatus.NOT_ACCEPTABLE)
        }

        if (task.title == null || task.title!!.trim().isEmpty()) {
            return ResponseEntity<Any>("missed param: title", HttpStatus.NOT_ACCEPTABLE)
        }

        taskService.update(task)

        return ResponseEntity<Any>(HttpStatus.OK)
    }

    @DeleteMapping("/delete/{id}")
    fun delete(@PathVariable("id") id: Long): ResponseEntity<Any> {

        try {
            taskService.deleteById(id)
        } catch (e: EmptyResultDataAccessException) {
            e.printStackTrace()
            return ResponseEntity<Any>("id=$id not found", HttpStatus.NOT_ACCEPTABLE)
        }

        return ResponseEntity<Any>(HttpStatus.OK)
    }

    @PostMapping("/all")
    fun findAll(@RequestBody userId: Long): ResponseEntity<List<Task>> {
        return ResponseEntity.ok(taskService.findAll(userId))
    }

    @PostMapping("/search")
    @Throws(ParseException::class)
    fun search(@RequestBody taskSearchValues: TaskSearchValues): ResponseEntity<Any> {

        // можно передавать не полный title, а любой текст для поиска
        val title = if (taskSearchValues.title != null) taskSearchValues.title else null

        // конвертируем Boolean в Integer
        val completed = taskSearchValues.completed != null && taskSearchValues.completed == 1

        val priorityId = if (taskSearchValues.priorityId != null) taskSearchValues.priorityId else null
        val categoryId = if (taskSearchValues.categoryId != null) taskSearchValues.categoryId else null
        val sortColumn = taskSearchValues.sortColumn
        val sortDirection = taskSearchValues.sortDirection
        val pageNumber = taskSearchValues.pageNumber
        val pageSize = taskSearchValues.pageSize
        val userId = taskSearchValues.userId

        // проверка на обязательные параметры
        if (userId == 0L) {
            return ResponseEntity<Any>("missed param: user id", HttpStatus.NOT_ACCEPTABLE)
        }

        // чтобы захватить в выборке все задачи по датам, независимо от времени - можно выставить
        // время с 00:00 до 23:59
        var dateFrom: Date? = null
        var dateTo: Date? = null

        // выставить 00:01 для начальной даты (если она указана)
        if (taskSearchValues.dateFrom != null) {
            val calendarFrom = Calendar.getInstance()
            calendarFrom.time = taskSearchValues.dateFrom
            calendarFrom[Calendar.HOUR_OF_DAY] = 0
            calendarFrom[Calendar.MINUTE] = 1
            calendarFrom[Calendar.SECOND] = 1
            calendarFrom[Calendar.MILLISECOND] = 1
            dateFrom = calendarFrom.time // записываем начальную дату с 00:01
        }

        // выставить 23:59 для конечной даты (если она указана)
        if (taskSearchValues.dateTo != null) {
            val calendarTo = Calendar.getInstance()
            calendarTo.time = taskSearchValues.dateTo
            calendarTo[Calendar.HOUR_OF_DAY] = 23
            calendarTo[Calendar.MINUTE] = 59
            calendarTo[Calendar.SECOND] = 59
            calendarTo[Calendar.MILLISECOND] = 999
            dateTo = calendarTo.time // записываем конечную дату с 23:59
        }

        // направление сортировки
        val direction =
            if (sortDirection.trim().isEmpty() || sortDirection.trim() == "asc") Sort.Direction.ASC else Sort.Direction.DESC

        // объект сортировки, который содержит стобец и направление
        // вторым полем для сортировки добавляем id, чтобы всегда сохранялся строгий порядок
        val sort = Sort.by(direction, sortColumn, ID_COLUMN)

        // объект постраничности
        val pageRequest = PageRequest.of(pageNumber, pageSize, sort)

        // результат запроса с постраничным выводом
        val result =
            taskService.findByParams(title, completed, priorityId, categoryId, userId, dateFrom, dateTo, pageRequest)

        // результат запроса
        return ResponseEntity.ok(result)
    }

    @PostMapping("/id")
    fun findById(@RequestBody id: Long): ResponseEntity<Any> {

        val task: Task? = try {
            taskService.findById(id)
        } catch (e: NoSuchElementException) {
            e.printStackTrace()
            return ResponseEntity<Any>("id=$id not found", HttpStatus.NOT_ACCEPTABLE)
        }

        return ResponseEntity.ok(task)
    }

}
