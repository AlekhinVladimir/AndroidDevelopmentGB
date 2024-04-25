package project.gb.cleanarchitecture.domain

import project.gb.cleanarchitecture.data.UsefulActivitiesRepository
import project.gb.cleanarchitecture.data.UsefulActivityDto
import javax.inject.Inject

class GetUsefulActivityUseCase @Inject constructor(
    private val repository: UsefulActivitiesRepository
) {
    suspend fun execute() : UsefulActivityDto {
        return repository.getUsefulActivity()
    }
}