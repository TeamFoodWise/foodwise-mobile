package bangkit.kiki.foodwisemobile.data.dataClass

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ErrorResponse(
    @field:SerializedName("error")
    val error: String? = null
) : Parcelable
