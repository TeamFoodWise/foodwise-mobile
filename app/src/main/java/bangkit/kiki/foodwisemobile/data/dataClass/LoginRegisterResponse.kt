package bangkit.kiki.foodwisemobile.data.dataClass

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class LoginRegisterResponse(

	@field:SerializedName("access_token")
	val accessToken: String? = null,

	@field:SerializedName("refresh_token")
	val refreshToken: String? = null,

	@field:SerializedName("user")
	val user: User? = null
) : Parcelable

@Parcelize
data class User(

	@field:SerializedName("full_name")
	val fullName: String? = null,

	@field:SerializedName("email")
	val email: String? = null,

	@field:SerializedName("profile_url")
	val profileUrl: String? = null
) : Parcelable
