package bangkit.kiki.foodwisemobile.data.dataClass

import com.google.gson.annotations.SerializedName

data class DeleteItemRequest(
	@field:SerializedName("item_id")
	val id: Int,

	val quantity: Int
)
