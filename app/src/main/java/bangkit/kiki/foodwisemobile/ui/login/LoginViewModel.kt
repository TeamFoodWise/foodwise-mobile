package bangkit.kiki.foodwisemobile.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import bangkit.kiki.foodwisemobile.data.api.ApiConfig
import bangkit.kiki.foodwisemobile.data.dataClass.ErrorResponse
import bangkit.kiki.foodwisemobile.data.dataClass.LoginRequest
import bangkit.kiki.foodwisemobile.data.model.UserModel
import bangkit.kiki.foodwisemobile.data.repository.UserRepository
import com.google.gson.Gson
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import retrofit2.HttpException

class LoginViewModel(private val repository: UserRepository): ViewModel() {
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading

    private val _isError = MutableLiveData(false)
    val isError: LiveData<Boolean> = _isError

    private val _errorMessage = MutableLiveData("")
    val errorMessage: LiveData<String> = _errorMessage

    suspend fun login(email: String, password: String): Boolean {
        _isLoading.value = true
        _isError.value = false

        try {
            val response = ApiConfig.getApiService().login(
                LoginRequest(email, password))

            if (
                (response.accessToken != null) &&
                (response.refreshToken != null) &&
                (response.user?.email != null) &&
                (response.user.fullName != null)
            ) {
                val user = UserModel(
                    email = response.user.email,
                    fullName = response.user.fullName,
                    profileUrl = response.user.profileUrl ?: "",
                    accessToken = response.accessToken,
                    refreshToken = response.refreshToken,
                    isLogin = true
                )

                repository.saveSession(user)

                return true
            }

            _errorMessage.value = "Something went wrong in the application, please wait for a moment or contact us"
            _isError.value = true

            return false
        } catch (error: HttpException) {
            val jsonInString = error.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, ErrorResponse::class.java)

            _errorMessage.value = errorBody.error
            _isError.value = true
        } finally {
            _isLoading.value = false
        }

        return false
    }
}