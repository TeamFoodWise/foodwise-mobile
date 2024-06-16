package bangkit.kiki.foodwisemobile.data.model

data class InventorySummaryModel (
    val inStockCount: Number,
    val consumedCount: Number,
    val expiredCount: Number,
)

data class FoodItemModel(
    val id: Int,
    val name: String,
    val quantity: Int,
    val measure: String,
    val expiredAt: String
)

data class FoodResponse(
    val foods: List<FoodItemModel>
)