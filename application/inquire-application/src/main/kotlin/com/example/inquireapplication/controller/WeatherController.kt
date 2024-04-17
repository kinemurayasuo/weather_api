package com.example.inquireapplication.controller

import com.example.domain.WeatherForecast
import com.example.domain.WeatherService
import lombok.RequiredArgsConstructor
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequiredArgsConstructor
@RequestMapping("weather")
class WeatherController(private val weatherService: WeatherService) {

    @GetMapping
    fun getWeatherData(
        @RequestParam("baseDate") baseDate: String,
        @RequestParam("baseTime") baseTime: String,
        @RequestParam("nx") nx: Int,
        @RequestParam("ny") ny: Int
    ): ResponseEntity<List<WeatherResponse>> {
        val weatherData = weatherService.fetchWeatherData(baseDate, baseTime, nx, ny)
        return if (weatherData.isEmpty()) {
            ResponseEntity.status(HttpStatus.NO_CONTENT).build()
        } else {
            ResponseEntity.ok(weatherData.map { it.toResponse() })
        }
    }

    @PostMapping
    fun saveWeatherData(@RequestBody weatherData: List<WeatherForecast>): ResponseEntity<String> {
        weatherService.saveWeatherData(weatherData)
        return ResponseEntity.status(HttpStatus.CREATED).body("Weather data saved successfully.")
    }
}

data class WeatherResponse(val baseDate: String, val baseTime: String, val category: String, val fcstDate: String, val fcstTime: String, val fcstValue: String, val nx: Int, val ny: Int)

fun WeatherForecast.toResponse() = WeatherResponse(baseDate, baseTime, category, fcstDate, fcstTime, fcstValue, nx, ny)
