package bangkit.kiki.foodwisemobile.ui.editProfile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import bangkit.kiki.foodwisemobile.data.api.ApiConfig
import bangkit.kiki.foodwisemobile.data.dataClass.ErrorResponse
import bangkit.kiki.foodwisemobile.data.dataClass.UpdateProfileRequest
import bangkit.kiki.foodwisemobile.data.model.UserModel
import bangkit.kiki.foodwisemobile.data.repository.UserRepository
import com.google.gson.Gson
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import retrofit2.HttpException

class EditProfileViewModel(private val repository: UserRepository): ViewModel() {
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading

    private val _isError = MutableLiveData(false)
    val isError: LiveData<Boolean> = _isError

    private val _errorMessage = MutableLiveData("")
    val errorMessage: LiveData<String> = _errorMessage

    private val _userSession = repository.getSession().asLiveData()
    val userSession: LiveData<UserModel> get() = _userSession

    suspend fun updateProfile(
        fullName: String,
        newPassword: String,
        confirmationNewPassword: String
    ): Boolean {
        val currentUser = _userSession.value
        _isLoading.value = true
        _isError.value = false

        try {
            val response = ApiConfig.getApiService().updateProfile(
                token = "Bearer ${repository.getAccessToken()}",
                UpdateProfileRequest(
                    fullName,
                    newPassword,
                    confirmationNewPassword
                )
            )

            if (
                response.message !== null &&
                response.user?.fullName != null &&
                response.user.email != null &&
                currentUser != null
            ) {
                val updatedUser = UserModel(
                    email = currentUser.email,
                    fullName = fullName,
                    accessToken = currentUser.accessToken,
                    refreshToken = currentUser.refreshToken,
                    isLogin = true
                )

                repository.saveSession(updatedUser)

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