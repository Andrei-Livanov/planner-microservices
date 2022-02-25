package ru.myapp.micro.planner.todo.controller

import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ru.myapp.micro.planner.entity.Priority
import ru.myapp.micro.planner.todo.search.PrioritySearchValues
import ru.myapp.micro.planner.todo.service.PriorityService
import ru.myapp.micro.planner.utils.rest.resttemplate.UserRestBuilder

@RestController
@RequestMapping("/priority")
class PriorityController(
    private val priorityService: PriorityService,
    private val userRestBuilder: UserRestBuilder
) {

    @PostMapping("/add")
    fun add(@RequestBody priority: Priority): ResponseEntity<Any> {

        if (priority.id != null && priority.id != 0L) {
            return ResponseEntity<Any>("redundant param: id MUST be null", HttpStatus.NOT_ACCEPTABLE)
        }

        if (priority.title == null || priority.title!!.trim().isEmpty()) {
            return ResponseEntity<Any>("missed param: title", HttpStatus.NOT_ACCEPTABLE)
        }

        if (priority.color == null || priority.color!!.trim().isEmpty()) {
            return ResponseEntity<Any>("missed param: color", HttpStatus.NOT_ACCEPTABLE)
        }

        if (priority.userId == null || priority.userId == 0L) {
            return ResponseEntity<Any>("missed param: userId MUST be not null", HttpStatus.NOT_ACCEPTABLE)
        }

        return if (userRestBuilder.userExists(priority.userId!!)) {
            ResponseEntity.ok(priorityService.add(priority))
        } else {
            ResponseEntity<Any>("user id=" + priority.userId + " not found", HttpStatus.NOT_ACCEPTABLE)
        }
    }

    @PutMapping("/update")
    fun update(@RequestBody priority: Priority): ResponseEntity<Any> {

        if (priority.id == null || priority.id == 0L) {
            return ResponseEntity<Any>("missed param: id", HttpStatus.NOT_ACCEPTABLE)
        }

        if (priority.title == null || priority.title!!.trim().isEmpty()) {
            return ResponseEntity<Any>("missed param: title", HttpStatus.NOT_ACCEPTABLE)
        }

        if (priority.color == null || priority.color!!.trim().isEmpty()) {
            return ResponseEntity<Any>("missed param: color", HttpStatus.NOT_ACCEPTABLE)
        }

        priorityService.update(priority)

        return ResponseEntity<Any>(HttpStatus.OK)
    }

    @DeleteMapping("/delete/{id}")
    fun delete(@PathVariable("id") id: Long): ResponseEntity<Any> {

        try {
            priorityService.deleteById(id)
        } catch (e: EmptyResultDataAccessException) {
            e.printStackTrace()
            return ResponseEntity<Any>("id=$id not found", HttpStatus.NOT_ACCEPTABLE)
        }

        return ResponseEntity<Any>(HttpStatus.OK)
    }

    @PostMapping("/all")
    fun findAll(@RequestBody userId: Long): List<Priority> {
        return priorityService.findAll(userId)
    }

    @PostMapping("/search")
    fun search(@RequestBody prioritySearchValues: PrioritySearchValues): ResponseEntity<Any> {

        return if (prioritySearchValues.userId == 0L) {
            ResponseEntity<Any>("missed param: user id", HttpStatus.NOT_ACCEPTABLE)
        } else {
            ResponseEntity.ok(priorityService.findByTitle(prioritySearchValues.title, prioritySearchValues.userId))
        }
    }

    @PostMapping("/id")
    fun findById(@RequestBody id: Long): ResponseEntity<Any> {

        val priority: Priority? = try {
            priorityService.findById(id)
        } catch (e: NoSuchElementException) {
            e.printStackTrace()
            return ResponseEntity<Any>("id=$id not found", HttpStatus.NOT_ACCEPTABLE)
        }

        return ResponseEntity.ok(priority)
    }

}
