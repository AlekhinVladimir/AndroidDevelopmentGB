package com.example.m16_architecture.data

import android.util.Log
import com.example.m16_architecture.entity.UsefulActivity
import retrofit2.Response
import javax.inject.Inject

private const val MAIN_LOG_TAG = "REP_LOG"

class UsefulActivitiesRepository @Inject constructor() {

    suspend fun getUsefulActivity(): UsefulActivity? {
        lateinit var response: Response<UsefulActivityDto>
        var usefulActivity: UsefulActivity? = null

        try {
            response = RetrofitInstance.usefulActivityDataSource.getUsefulActivity()
            if (response.isSuccessful && response.body() != null) {
                usefulActivity = response.body()
            }
        } catch (e: Throwable) {
            Log.e(
                MAIN_LOG_TAG,
                "${e.toString()} \r\n cause: ${e.cause.toString()} \r\n suppressed by: ${e.suppressed.toString()}"
            )
        } finally {
            return usefulActivity
        }
    }
}
