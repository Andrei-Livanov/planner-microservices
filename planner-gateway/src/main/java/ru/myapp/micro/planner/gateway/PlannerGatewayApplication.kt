package ru.myapp.micro.planner.gateway

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.netflix.eureka.EnableEurekaClient

@SpringBootApplication
@EnableEurekaClient
class PlannerGatewayApplication

fun main(args: Array<String>) {
    runApplication<PlannerGatewayApplication>(*args)
}
