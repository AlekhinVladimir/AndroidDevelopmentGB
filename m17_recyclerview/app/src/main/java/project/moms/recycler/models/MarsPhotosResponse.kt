package project.moms.recycler.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MarsPhotosResponse(
    val photos: List<MarsPhoto>
) : Parcelable