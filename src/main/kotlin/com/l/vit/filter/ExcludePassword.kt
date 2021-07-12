package com.l.vit.filter

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.ser.PropertyWriter
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider
import org.springframework.stereotype.Component

@Component
final class ExcludePassword(objectMapper: ObjectMapper) : SimpleBeanPropertyFilter() {
    companion object {
        const val USER_PASSWORD: String = "password"
    }

    init {
        val simpleFilterProvider = SimpleFilterProvider().setFailOnUnknownId(true)
        simpleFilterProvider.addFilter("excludePassword", this)
        objectMapper.setFilterProvider(simpleFilterProvider)
    }

    override fun serializeAsField(
        pojo: Any?,
        jgen: JsonGenerator?,
        provider: SerializerProvider?,
        writer: PropertyWriter?
    ) {
        if (writer?.name == USER_PASSWORD && jgen?.canOmitFields() == true) {
            writer.serializeAsOmittedField(pojo, jgen, provider)
            return
        }
        super.serializeAsField(pojo, jgen, provider, writer)
    }

    override fun include(writer: PropertyWriter?) = true

    override fun includeElement(elementValue: Any?) = true
}
