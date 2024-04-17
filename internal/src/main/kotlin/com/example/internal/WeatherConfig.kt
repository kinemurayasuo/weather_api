package com.example.internal

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestTemplate

@Configuration
@ComponentScan
class WeatherConfig {

    @Bean
    fun restTemplate(): RestTemplate {
        return RestTemplate()
    }



    @Configuration
    @ConfigurationProperties(prefix = "weather.api")
    class WeatherApiProperties {
        lateinit var url: String
        lateinit var key: String
    }
}