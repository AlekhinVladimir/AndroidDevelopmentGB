package com.example.m16_architecture.domain

import com.example.m16_architecture.data.UsefulActivitiesRepository
import com.example.m16_architecture.entity.UsefulActivity
import javax.inject.Inject

class GetUsefulActivityUseCase @Inject constructor(private val repository: UsefulActivitiesRepository) {
    suspend fun execute(): UsefulActivity? {
        return repository.getUsefulActivity()
    }
}
