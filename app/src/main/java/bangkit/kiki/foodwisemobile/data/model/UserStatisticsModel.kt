package bangkit.kiki.foodwisemobile.data.model

import com.google.gson.annotations.SerializedName

data class UserInventoryResponse(
    @SerializedName("consumed_count") val consumedCount: Int,
    @SerializedName("in_stock_count") val inStockCount: Int,
    @SerializedName("expired_count") val expiredCount: Int,
    @SerializedName("current_progress") val currentProgress: Float, // current progress in percentage
    @SerializedName("remaining_days") val remainingDays: Int, // remaining days in the current month cycle
    @SerializedName("history_progress") val historyProgress: Float? // history progress in percentage, nullable
)

data class ExpiringFood(
    val name: String,
    @SerializedName("remaining_days") val remainingDays: Int
)

data class ExpiringFoodResponse(
    @SerializedName("food_item") val foods: List<ExpiringFood>
)