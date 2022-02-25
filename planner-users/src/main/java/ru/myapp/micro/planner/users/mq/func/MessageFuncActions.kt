package ru.myapp.micro.planner.users.mq.func

import org.springframework.messaging.support.MessageBuilder
import org.springframework.stereotype.Service
import reactor.core.publisher.Sinks

// работа с каналами
// помогает реализовать отправку сообщения с помощью функционального кода - по требованию

@Service
class MessageFuncActions(
    private val messageFunc: MessageFunc
) {

    // отправка сообщения
    fun sendNewUserMessage(id: Long) {
        // добавляем в слушатель новое сообщение
        messageFunc
            .innerBus
            .emitNext(MessageBuilder.withPayload(id).build(), Sinks.EmitFailureHandler.FAIL_FAST)
        println("Message sent: $id")
    }

}
