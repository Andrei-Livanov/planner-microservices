package ru.myapp.micro.planner.server

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer

@SpringBootApplication
@EnableEurekaServer
class PlannerServerApplication

fun main(args: Array<String>) {
    runApplication<PlannerServerApplication>(*args)
}
