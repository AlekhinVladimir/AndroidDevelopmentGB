package project.moms.recycler.data

import project.moms.recycler.data.api.RetrofitObj
import project.moms.recycler.models.MarsPhotosResponse
import javax.inject.Inject

class Repository @Inject constructor() {
    suspend fun loadPhoto(date: String) : MarsPhotosResponse {
        return RetrofitObj.networkApi.getMarsPhotos(date)
    }
}