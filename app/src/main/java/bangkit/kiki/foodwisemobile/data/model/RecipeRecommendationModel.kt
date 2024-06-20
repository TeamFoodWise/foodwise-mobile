package bangkit.kiki.foodwisemobile.data.model

data class RecipeRecommendationModel (
    val id: Int,
    val name: String,
    val ingredients: List<String>
)

data class RecipeRecommendationCardModel (
    val recipe: RecipeRecommendationModel,
    val position: Int,
    val totalRecommendation: Int
)