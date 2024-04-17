package com.example.domain

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ForecastRepository : JpaRepository<ForecastEntity, Long> {
    fun findAllByBaseDateAndBaseTimeAndNxAndNy(baseDate: String, baseTime: String, nx: Int, ny: Int): List<ForecastEntity>
}
