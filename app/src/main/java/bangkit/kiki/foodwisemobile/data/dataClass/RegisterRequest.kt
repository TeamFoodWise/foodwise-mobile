package bangkit.kiki.foodwisemobile.data.dataClass

import com.google.gson.annotations.SerializedName

data class RegisterRequest(

	@field:SerializedName("full_name")
	val fullName: String? = null,

	@field:SerializedName("email")
	val email: String? = null,

	@field:SerializedName("password")
	val password: String? = null,

	@field:SerializedName("confirm_password")
	val confirmPassword: String? = null
)
