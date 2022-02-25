package ru.myapp.micro.planner.config

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.config.server.EnableConfigServer

@SpringBootApplication
@EnableConfigServer
class PlannerConfigApplication

fun main(args: Array<String>) {
    runApplication<PlannerConfigApplication>(*args)
}
