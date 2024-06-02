package bangkit.kiki.foodwisemobile.util

fun isEmailValid(email: String): Boolean {
    val emailRegex = Regex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}\$")
    return emailRegex.matches(email)
}