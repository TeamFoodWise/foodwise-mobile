package bangkit.kiki.foodwisemobile.data.dataClass

import com.google.gson.annotations.SerializedName

data class RegisterRequest(

	@field:SerializedName("full_name")
	val fullName: String,

	@field:SerializedName("email")
	val email: String,

	@field:SerializedName("password")
	val password: String,

	@field:SerializedName("confirm_password")
	val confirmPassword: String
)
