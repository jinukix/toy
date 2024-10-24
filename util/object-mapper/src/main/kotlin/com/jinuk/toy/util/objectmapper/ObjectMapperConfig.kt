package com.jinuk.toy.util.objectmapper

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.module.SimpleModule
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl

@Configuration
class ObjectMapperConfig {
    @Bean
    fun objectMapper(): ObjectMapper {
        val module = SimpleModule()
        module.addSerializer(Page::class.java, PageSerializer())
        module.addDeserializer(PageImpl::class.java, PageDeserializer())

        return ObjectMapper().apply {
            registerModule(module)
            findAndRegisterModules()
        }
    }
}
