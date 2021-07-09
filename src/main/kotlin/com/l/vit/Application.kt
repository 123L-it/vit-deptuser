package com.l.vit

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.MapperFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.l.vit.filter.ApiLog
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Primary
import org.springframework.core.Ordered
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder

@SpringBootApplication
@ConfigurationPropertiesScan
class Application {
    @Bean
    fun apiLogRegistrationBean(): FilterRegistrationBean<ApiLog> {
        val bean = FilterRegistrationBean(ApiLog())
        bean.order = Ordered.HIGHEST_PRECEDENCE
        return bean
    }

    @Bean
    @Primary
    fun configureJackson(builder: Jackson2ObjectMapperBuilder): ObjectMapper {
        val objectMapper: ObjectMapper = builder.defaultViewInclusion(true)
            .serializationInclusion(JsonInclude.Include.NON_NULL)
            .indentOutput(true).build()
        objectMapper
            .configure(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS, true)
            .enable(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY)
        return objectMapper
    }
}

fun main(args: Array<String>) {
    runApplication<Application>(*args)
}
