package bangkit.kiki.foodwisemobile.data.dataClass

import com.google.gson.annotations.SerializedName

data class UpdateProfileRequest(

	@field:SerializedName("full_name")
	val fullName: String,

	@field:SerializedName("new_password")
	val newPassword: String,

	@field:SerializedName("confirmation_new_password")
	val confirmationNewPassword: String
)
