package ru.myapp.micro.planner.todo.feign

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import ru.myapp.micro.planner.entity.User

@FeignClient(name = "planner-users", fallback = UserFeignClientFallback::class)
interface UserFeignClient {

    @PostMapping("/user/id")
    fun findUserById(@RequestBody id: Long): ResponseEntity<User?>?

}

@Component
class UserFeignClientFallback : UserFeignClient {

    override fun findUserById(id: Long): ResponseEntity<User?>? {
        return null
    }

}
