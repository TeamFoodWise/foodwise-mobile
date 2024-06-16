package bangkit.kiki.foodwisemobile.data.model

data class UserInventoryModel(
    val consumedCount: Int,
    val inStockCount: Int,
    val expiredCount: Int,
    val currentProgress: Int, // current progress in percentage
    val remainingDays: Int, // remaining days in the current month cycle
    val historyProgress: Int? // history progress in percentage, nullable
)

data class ExpiringFood(
    val name: String,
    val remainingDays: Int
)

data class ExpiringFoodResponse(
    val foods: List<ExpiringFood>
)