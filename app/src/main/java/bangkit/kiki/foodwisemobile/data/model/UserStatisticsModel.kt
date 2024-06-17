package bangkit.kiki.foodwisemobile.data.model

import com.google.gson.annotations.SerializedName

data class UserInventoryModel(
    @SerializedName("consumed_count") val consumedCount: Int,
    @SerializedName("in_stock_count") val inStockCount: Int,
    @SerializedName("expired_count") val expiredCount: Int,
    @SerializedName("current_progress") val currentProgress: Int, // current progress in percentage
    @SerializedName("remaining_days") val remainingDays: Int, // remaining days in the current month cycle
    @SerializedName("history_progress") val historyProgress: Int? // history progress in percentage, nullable
)

data class ExpiringFood(
    val name: String,
    val remainingDays: Int
)

data class ExpiringFoodResponse(
    val foods: List<ExpiringFood>
)