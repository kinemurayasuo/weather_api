// internal/weather-adapter/src/main/kotlin/com/example/weatheradapter/WeatherApiClient.kt
package com.example.internal

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate
import java.net.URI

@Component
class WeatherApiClient(
    private val restTemplate: RestTemplate,
    @Value("\${weather.api.url}")
    private val apiUrl: String,
    @Value("\${weather.api.key}")
    private val apiKey: String
) {
    fun fetchWeatherData(baseDate: String, baseTime: String, nx: Int, ny: Int): WeatherDataResponse {
        val url = "$apiUrl?key=$apiKey&base_date=$baseDate&base_time=$baseTime&nx=$nx&ny=$ny"
        val uri = URI(url)
        return restTemplate.getForObject(uri, WeatherDataResponse::class.java)
            ?: throw IllegalStateException("Failed to fetch weather data from the API")
    }
}

data class WeatherDataResponse(val items: List<WeatherItem>)
data class WeatherItem(val baseDate: String, val baseTime: String, val category: String, val fcstDate: String, val fcstTime: String, val fcstValue: String, val nx: Int, val ny: Int)
