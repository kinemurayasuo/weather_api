package com.example.syncapplication.controller

import com.example.domain.WeatherService
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class SyncController(private val weatherService: WeatherService) {
    @Scheduled(fixedRate = 600000)
    fun syncWeatherData() {
        val baseDate = "20240417"
        val baseTime = "12"
        val nx = 62
        val ny = 130

        val weatherData = weatherService.fetchWeatherData(baseDate, baseTime, nx, ny)
        weatherService.saveWeatherData(weatherData)
    }
}
