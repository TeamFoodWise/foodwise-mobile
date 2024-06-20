package bangkit.kiki.foodwisemobile.data.dataClass

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class RecipesRecommendationResponse(

	@field:SerializedName("recipes")
	val recipes: List<RecipesItem>? = null
) : Parcelable

@Parcelize
data class RecipesItem(

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("ingredients")
	val ingredients: List<String?>? = null,

	@field:SerializedName("id")
	val id: Int? = null
) : Parcelable
