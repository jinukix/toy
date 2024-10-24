package com.jinuk.toy.webflux.view.health

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
class HealthCheckApi {
    @GetMapping("/v1/health")
    fun healthCheck(): Mono<String> {
        return Mono.just("Hi! Webflux")
    }
}
