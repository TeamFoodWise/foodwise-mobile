package bangkit.kiki.foodwisemobile.data.model

data class RecipeRecommendationModel (
    val id: String,
    val name: String,
    val category: String,
    val ingredients: List<String>
)

data class RecipeRecommendationDetailModel (
    val id: String,
    val name: String,
    val ingredients: List<String>,
    val steps: List<String>
)