package ru.myapp.micro.planner.users.controller

import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.RequestMapping
import ru.myapp.micro.planner.users.service.UserService
import ru.myapp.micro.planner.users.mq.func.MessageFuncActions
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.data.domain.Page
import java.util.NoSuchElementException
import kotlin.Throws
import ru.myapp.micro.planner.users.search.UserSearchValues
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.http.HttpStatus
import ru.myapp.micro.planner.entity.User
import java.text.ParseException

@RestController
@RequestMapping("/user")
class UserController(
    private val userService: UserService,
    private val messageFuncActions: MessageFuncActions
) {

    // статичная константа
    companion object {
        const val ID_COLUMN = "id" // имя столбца id
    }

    @PostMapping("/add")
    fun add(@RequestBody user: User): ResponseEntity<Any> {

        if (user.id != null && user.id != 0L) {
            return ResponseEntity<Any>("redundant param: id must be null", HttpStatus.NOT_ACCEPTABLE)
        }

        if (user.email == null || user.email!!.trim().isEmpty()) {
            return ResponseEntity<Any>("missed param: email", HttpStatus.NOT_ACCEPTABLE)
        }

        if (user.password == null || user.password!!.trim().isEmpty()) {
            return ResponseEntity<Any>("missed param: password", HttpStatus.NOT_ACCEPTABLE)
        }

        if (user.username == null || user.username!!.trim().isEmpty()) {
            return ResponseEntity<Any>("missed param: username", HttpStatus.NOT_ACCEPTABLE)
        }

        val tmpUser = userService.add(user)

        // отправляем сообщение в очередь для генерации тестовых данных
        if (tmpUser.id != null) {
            messageFuncActions.sendNewUserMessage(tmpUser.id!!)
        }

        return ResponseEntity.ok(user)
    }

    @PutMapping("/update")
    fun update(@RequestBody user: User): ResponseEntity<Any> {

        if (user.id == null || user.id == 0L) {
            return ResponseEntity<Any>("missed param: id", HttpStatus.NOT_ACCEPTABLE)
        }

        if (user.email == null || user.email!!.trim().isEmpty()) {
            return ResponseEntity<Any>("missed param: email", HttpStatus.NOT_ACCEPTABLE)
        }

        if (user.password == null || user.password!!.trim().isEmpty()) {
            return ResponseEntity<Any>("missed param: password", HttpStatus.NOT_ACCEPTABLE)
        }

        if (user.username == null || user.username!!.trim().isEmpty()) {
            return ResponseEntity<Any>("missed param: username", HttpStatus.NOT_ACCEPTABLE)
        }

        userService.update(user)

        return ResponseEntity<Any>(HttpStatus.OK)
    }

    @PostMapping("/deletebyid")
    fun deleteByUserId(@RequestBody userId: Long): ResponseEntity<Any> {

        try {
            userService.deleteByUserId(userId)
        } catch (e: EmptyResultDataAccessException) {
            e.printStackTrace()
            return ResponseEntity<Any>("userId=$userId not found", HttpStatus.NOT_ACCEPTABLE)
        }

        return ResponseEntity<Any>(HttpStatus.OK)
    }

    @PostMapping("/deletebyemail")
    fun deleteByUserEmail(@RequestBody email: String): ResponseEntity<Any> {

        try {
            userService.deleteByUserEmail(email)
        } catch (e: EmptyResultDataAccessException) {
            e.printStackTrace()
            return ResponseEntity<Any>("email=$email not found", HttpStatus.NOT_ACCEPTABLE)
        }

        return ResponseEntity<Any>(HttpStatus.OK)
    }

    @PostMapping("/email")
    fun findByEmail(@RequestBody email: String): ResponseEntity<Any> {

        val user = try {
            userService.findByEmail(email)
        } catch (e: NoSuchElementException) {
            e.printStackTrace()
            return ResponseEntity<Any>("email=$email not found", HttpStatus.NOT_ACCEPTABLE)
        }

        return ResponseEntity.ok(user)
    }

    @PostMapping("/search")
    @Throws(ParseException::class)
    fun search(@RequestBody userSearchValues: UserSearchValues): ResponseEntity<Page<User?>> {

        // можно передавать не полный email, а любой текст для поиска
        val email = userSearchValues.email
        val sortColumn = userSearchValues.sortColumn
        val sortDirection = userSearchValues.sortDirection
        val pageNumber = userSearchValues.pageNumber
        val pageSize = userSearchValues.pageSize
        val username = userSearchValues.username ?: ""

        // направление сортировки
        val direction =
            if (sortDirection.trim().isEmpty() || sortDirection.trim() == "asc") Sort.Direction.ASC else Sort.Direction.DESC

        // объект сортировки, который содержит стобец и направление
        // вторым полем для сортировки добавляем id, чтобы всегда сохранялся строгий порядок
        val sort = Sort.by(direction, sortColumn, ID_COLUMN)

        // объект постраничности
        val pageRequest = PageRequest.of(pageNumber, pageSize, sort)

        // результат запроса с постраничным выводом
        val result = userService.findByParams(username, email, pageRequest)

        // результат запроса
        return ResponseEntity.ok(result)
    }

    @PostMapping("/id")
    fun findById(@RequestBody id: Long): ResponseEntity<Any> {

        val userOptional = userService.findById(id)

        try {
            if (userOptional.isPresent) { // если объект найден
                return ResponseEntity.ok(userOptional.get())
            }
        } catch (e: NoSuchElementException) {
            e.printStackTrace()
        }

        return ResponseEntity<Any>("id=$id not found", HttpStatus.NOT_ACCEPTABLE)
    }

}
