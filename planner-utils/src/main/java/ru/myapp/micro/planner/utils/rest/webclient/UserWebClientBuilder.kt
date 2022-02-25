package ru.myapp.micro.planner.utils.rest.webclient

import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Flux
import ru.myapp.micro.planner.entity.User
import java.lang.Exception

// специальный класс для вызовов микросервисов пользователей с помощью WebClient

@Component
class UserWebClientBuilder {

    companion object {
        private const val baseUrlUser = "http://localhost:8765/planner-users/user/"
        private const val baseUrlData = "http://localhost:8765/planner-todo/data/"
    }

    fun userExists(userId: Long): Boolean {

        try {
            val user = WebClient.create(baseUrlUser)
                .post()
                .uri("id")
                .bodyValue(userId)
                .retrieve()
                .bodyToFlux(User::class.java)
                .blockFirst()
            if (user != null) {
                return true
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return false
    }

    // асинхронный вызов
    fun userExistsAsync(userId: Long): Flux<User> {
        return WebClient.create(baseUrlUser)
            .post()
            .uri("id")
            .bodyValue(userId)
            .retrieve()
            .bodyToFlux(User::class.java)
    }

    // инициализация начальных данных
    fun initUserData(userId: Long): Flux<Boolean> {
        return WebClient.create(baseUrlData)
            .post()
            .uri("init")
            .bodyValue(userId)
            .retrieve()
            .bodyToFlux<Boolean>(Boolean::class.java)
    }

}
