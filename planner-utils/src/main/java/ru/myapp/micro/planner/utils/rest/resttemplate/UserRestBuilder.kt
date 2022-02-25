package ru.myapp.micro.planner.utils.rest.resttemplate

import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate
import ru.myapp.micro.planner.entity.User

// специальный класс для вызовов микросервисов пользователей с помощью RestTemplate

@Component
class UserRestBuilder {

    companion object {
        private const val baseUrl = "http://localhost:8765/planner-users/user/"
    }

    fun userExists(userId: Long): Boolean {

        // для примера - как использовать RestTemplate (уже deprecated)
        val restTemplate = RestTemplate()
        val request: HttpEntity<Long> = HttpEntity<Long>(userId)
        var response: ResponseEntity<User?>? = null

        // если нужно получить объект - вызываем response.getBody()
        // в текущем вызове не нужен объект, т.к. просто проверяем, есть ли такой пользователь
        try {
            response = restTemplate.exchange("$baseUrl/id", HttpMethod.POST, request, User::class.java)
            if (response.statusCode == HttpStatus.OK) { // если статус был 200
                return true
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return false // если статус не был 200
    }

}
