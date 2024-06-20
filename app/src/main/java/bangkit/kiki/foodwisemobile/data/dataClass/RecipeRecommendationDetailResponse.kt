package bangkit.kiki.foodwisemobile.data.dataClass

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class RecipeRecommendationDetailResponse(

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("ingredients")
	val ingredients: List<String?>? = null,

	@field:SerializedName("steps")
	val steps: List<String?>? = null
) : Parcelable
