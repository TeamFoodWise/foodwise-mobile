package bangkit.kiki.foodwisemobile.ui.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import bangkit.kiki.foodwisemobile.data.api.ApiConfig
import bangkit.kiki.foodwisemobile.data.model.UserModel
import bangkit.kiki.foodwisemobile.data.repository.UserRepository
import com.google.gson.Gson
import retrofit2.HttpException

class LoginViewModel(private val repository: UserRepository): ViewModel() {
    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isError = MutableLiveData(false)
    val isError: LiveData<Boolean> = _isError

    private val _errorMessage = MutableLiveData("")
    val errorMessage: LiveData<String> = _errorMessage

    suspend fun login(email: String, password: String): Boolean {
        _isLoading.value = true
        _isError.value = false

        try {
            val response = ApiConfig.getApiService().login(email, password)

            if (
                (response.accessToken != null) &&
                (response.refreshToken != null) &&
                (response.user?.email != null) &&
                (response.user.fullName != null)
            ) {
                val user = UserModel(
                    email = response.user.email,
                    fullName = response.user.fullName,
                    accessToken = response.accessToken,
                    refreshToken = response.refreshToken,
                    isLogin = true
                )
                Log.e("LOGIN_VIEW_MODEL_SUCC", user.accessToken)

                repository.saveSession(user)

                return true
            }

            _errorMessage.value = "Something went wrong in the application, please wait for a moment or contact us"
            _isError.value = true

            return false
        } catch (error: HttpException) {
            val jsonInString = error.response()?.errorBody()?.string()
            _errorMessage.value = jsonInString
            _isError.value = true
            if (jsonInString != null) {
                Log.e("LOGIN_VIEW_MODEL_ERR", jsonInString)
            }
        } finally {
            _isLoading.value = false
        }

        return false
    }
}