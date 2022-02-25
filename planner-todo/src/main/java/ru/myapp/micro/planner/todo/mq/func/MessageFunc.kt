package ru.myapp.micro.planner.todo.mq.func

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.messaging.Message
import ru.myapp.micro.planner.todo.service.TestDataService
import java.util.function.Consumer

@Configuration
class MessageFunc(
    private val testDataService: TestDataService
) {

    // получает id пользователя и запускает создание тестовых данных
    @Bean
    fun newUserActionConsume(): Consumer<Message<Long>> {
        return Consumer { message: Message<Long> -> testDataService.initTestData(message.payload) }
    }

}
