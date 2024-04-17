package com.example.domain

interface WeatherService {
    fun fetchWeatherData(baseDate: String, baseTime: String, nx: Int, ny: Int): List<WeatherForecast>
    fun saveWeatherData(weatherData: List<WeatherForecast>)
    fun getWeatherDataFromDatabase(baseDate: String, baseTime: String, nx: Int, ny: Int): List<WeatherForecast>
}