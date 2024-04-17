import com.example.domain.*
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.client.RestTemplate

@Service
@Transactional
class WeatherServiceImpl(
    private val restTemplate: RestTemplate,
    private val forecastRepository: ForecastRepository,
    private val objectMapper: ObjectMapper
) : WeatherService {

    override fun fetchWeatherData(baseDate: String, baseTime: String, nx: Int, ny: Int): List<WeatherForecast> {
        val apiUrl = "http://apis.data.go.kr/B552584/EvCharger/getChargerInfo?" +
                "serviceKey=fSsfUAxiS4e6yY6SVpigTNjiWcSKsC9iO1BPjGAMuXvOkAos8ioWlylPmeuXE%2F5BW3Du3zi7LczTEV3mjodn4A%3D%3D" +
                "&numOfRows=10" +
                "&pageNo=4" +
                "&base_date=$baseDate" +
                "&base_time=$baseTime" +
                "&nx=$nx" +
                "&ny=$ny"

        val response = restTemplate.getForObject(apiUrl, String::class.java)
        val weatherData: List<WeatherForecast>? = response?.let { parseApiResponse(it) }

        return weatherData!!
    }

    override fun saveWeatherData(weatherData: List<WeatherForecast>) {
        val weatherEntities = weatherData.map {
            ForecastEntity(
                baseDate = it.baseDate,
                baseTime = it.baseTime,
                category = it.category,
                fcstDate = it.fcstDate,
                fcstTime = it.fcstTime,
                fcstValue = it.fcstValue,
                nx = it.nx,
                ny = it.ny
            )
        }
        forecastRepository.saveAll(weatherEntities)
    }
    override fun getWeatherDataFromDatabase(baseDate: String, baseTime: String, nx: Int, ny: Int): List<WeatherForecast> {
        val weatherEntities = forecastRepository.findAllByBaseDateAndBaseTimeAndNxAndNy(baseDate, baseTime, nx, ny)
        return weatherEntities.map {
            WeatherForecast(
                baseDate = it.baseDate,
                baseTime = it.baseTime,
                category = it.category,
                fcstDate = it.fcstDate,
                fcstTime = it.fcstTime,
                fcstValue = it.fcstValue,
                nx = it.nx,
                ny = it.ny
            )
        }
    }




    private fun parseApiResponse(response: String): List<WeatherForecast> {
        return objectMapper.readValue(response)
    }
}
