package bangkit.kiki.foodwisemobile.data.model

data class UserModel(
    val email: String,
    val fullName: String,
    val token: String,
    val isLogin: Boolean = false
)