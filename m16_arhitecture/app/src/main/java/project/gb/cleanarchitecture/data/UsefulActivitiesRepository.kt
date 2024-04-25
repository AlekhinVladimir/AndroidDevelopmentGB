package project.gb.cleanarchitecture.data

import project.gb.cleanarchitecture.di.NetworkService
import javax.inject.Inject

class UsefulActivitiesRepository @Inject constructor() {
    suspend fun getUsefulActivity() : UsefulActivityDto {
        return NetworkService.boredApiService.getUsefulActivity()
    }
}