package bangkit.kiki.foodwisemobile.data.dataClass

import com.google.gson.annotations.SerializedName

data class CreateItemRequest(
	@SerializedName("name") val name: String,
	@SerializedName("quantity") val quantity: String,
	@SerializedName("type") val type: String,
	@SerializedName("measure") val measure: String,
	@SerializedName("expiration_date") val expirationDate: String
)
