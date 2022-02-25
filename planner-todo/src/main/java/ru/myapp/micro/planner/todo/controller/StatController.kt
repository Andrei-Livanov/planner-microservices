package ru.myapp.micro.planner.todo.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import ru.myapp.micro.planner.entity.Stat
import ru.myapp.micro.planner.todo.service.StatService

@RestController
class StatController(
    private val statService: StatService
) {

    @PostMapping("/stat")
    fun findById(@RequestBody userId: Long): ResponseEntity<Stat> {
        return ResponseEntity.ok(statService.findStat(userId))
    }

}
