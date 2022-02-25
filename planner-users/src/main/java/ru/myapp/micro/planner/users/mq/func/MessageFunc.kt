package ru.myapp.micro.planner.users.mq.func

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.messaging.Message
import reactor.core.publisher.Flux
import reactor.core.publisher.Sinks
import reactor.util.concurrent.Queues
import java.util.function.Supplier

// все каналы

@Configuration
class MessageFunc {

    // для того, чтобы считывать данные по требованию (а не постоянно) - создаём поток,
    // откуда данные будут отправляться уже в канал SCS
    // будем использовать встроенную шину, из которой будут отправляться сообщения в канал SCS (по требованию)
    val innerBus: Sinks.Many<Message<Long>> = Sinks
        .many()
        .multicast()
        .onBackpressureBuffer<Message<Long>>(Queues.SMALL_BUFFER_SIZE, false)

    @Bean
    fun newUserActionProduce(): Supplier<Flux<Message<Long>>> {
        // будет считывать данные из потока Flux (как только туда попадают новые сообщения)
        return Supplier { innerBus.asFlux() }
    }

}
