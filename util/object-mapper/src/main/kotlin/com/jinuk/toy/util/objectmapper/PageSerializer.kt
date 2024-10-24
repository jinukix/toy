package com.jinuk.toy.util.objectmapper

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.JsonSerializer
import com.fasterxml.jackson.databind.SerializerProvider
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest

class PageSerializer : JsonSerializer<Page<*>>() {
    override fun serialize(
        page: Page<*>,
        gen: JsonGenerator,
        serializers: SerializerProvider,
    ) {
        gen.writeStartObject()
        gen.writeObjectField("content", page.content)
        gen.writeNumberField("pageNumber", page.number)
        gen.writeNumberField("pageSize", page.size)
        gen.writeNumberField("totalElements", page.totalElements)
        gen.writeNumberField("totalPages", page.totalPages)
        gen.writeEndObject()
    }
}

class PageDeserializer : JsonDeserializer<PageImpl<*>>() {
    override fun deserialize(
        p: JsonParser,
        ctxt: DeserializationContext,
    ): PageImpl<*> {
        val node: JsonNode = p.codec.readTree(p)
        val content = node.get("content").traverse(p.codec).readValueAs(List::class.java)
        val pageNumber = node.get("pageNumber").asInt()
        val pageSize = node.get("pageSize").asInt()
        val pageable = PageRequest.of(pageNumber, pageSize)
        val totalElements = node.get("totalElements").asLong()
        return PageImpl(content, pageable, totalElements)
    }
}
