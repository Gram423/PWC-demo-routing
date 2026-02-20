package com.example.pwcdemorouting

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication(scanBasePackages = ["com.example.pwcdemorouting"])
class PwcDemoRoutingApplication

fun main(args: Array<String>) {
    runApplication<PwcDemoRoutingApplication>(*args)
}
