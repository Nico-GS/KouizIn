package com.app.quiz.config

import org.springframework.context.annotation.Configuration
import org.springframework.http.CacheControl
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import java.time.Duration

@Configuration
open class WebMvcConfig : WebMvcConfigurer
{

    override fun addResourceHandlers(registry: ResourceHandlerRegistry)
    {
        registry.addResourceHandler("static/uploads/pp/**")
            .addResourceLocations("file:resources/static/uploads")
            .setCacheControl(CacheControl.maxAge(Duration.ofDays(365)))
    }
}