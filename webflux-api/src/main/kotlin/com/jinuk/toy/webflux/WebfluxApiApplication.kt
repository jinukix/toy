package com.jinuk.toy.webflux

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
import org.springframework.boot.runApplication

@SpringBootApplication(
    scanBasePackages = ["com.jinuk.toy"],
    exclude = [
        DataSourceAutoConfiguration::class,
    ],
)
class WebfluxApiApplication

fun main(args: Array<String>) {
    runApplication<WebfluxApiApplication>(*args)
}
