package bangkit.kiki.foodwisemobile.data.model

data class UserModel(
    val email: String,
    val fullName: String,
    val accessToken: String,
    val refreshToken: String,
    val isLogin: Boolean = false
)