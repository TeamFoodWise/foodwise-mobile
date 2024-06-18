package bangkit.kiki.foodwisemobile.data.model

import bangkit.kiki.foodwisemobile.data.dataClass.User
import com.google.gson.annotations.SerializedName

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

data class CreateItemResponse(
    @field:SerializedName("message")
    val message: String? = null,

    val food: FoodItemModel
)

data class DeleteItemResponse(
    @SerializedName("message") val message: String,
    @SerializedName("item") val item: DeletedItemModel
)

data class DeletedItemModel(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("quantity") val quantity: Int,
    @SerializedName("category") val category: String,
    @SerializedName("purchase_date") val purchaseDate: String,
    @SerializedName("expiration_date") val expirationDate: String,
    @SerializedName("inventory_id") val inventoryId: Int,
    @SerializedName("measure") val measure: String
)
