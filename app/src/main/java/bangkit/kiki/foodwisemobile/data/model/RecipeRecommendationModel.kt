package bangkit.kiki.foodwisemobile.data.model

data class RecipeRecommendationModel (
    val id: Int,
    val name: String,
    val ingredients: List<String>
)

data class RecipeRecommendationDetailModel (
    val id: Int,
    val name: String,
    val ingredients: List<String>,
    val steps: List<String>
)