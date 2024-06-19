package bangkit.kiki.foodwisemobile.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import bangkit.kiki.foodwisemobile.data.api.ApiConfig
import bangkit.kiki.foodwisemobile.data.model.ExpiringFoodResponse
import bangkit.kiki.foodwisemobile.data.model.UserInventoryResponse
import bangkit.kiki.foodwisemobile.data.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.net.SocketTimeoutException

class MainViewModel(private val repository: UserRepository) : ViewModel() {

    private val _isLoadingStatistics = MutableStateFlow(false)
    val isLoadingStatistics: StateFlow<Boolean> get() = _isLoadingStatistics

    private val _isLoadingExpiring = MutableStateFlow(false)
    val isLoadingExpiring: StateFlow<Boolean> get() = _isLoadingExpiring

    private val _isError = MutableLiveData(false)
    val isError: LiveData<Boolean> = _isError

    private val _errorMessage = MutableLiveData("")
    val errorMessage: LiveData<String> = _errorMessage

    private val _userInventory = MutableStateFlow<UserInventoryResponse?>(null)
    val userInventory: StateFlow<UserInventoryResponse?> = _userInventory

    private val _expiringFoodResponse = MutableStateFlow<ExpiringFoodResponse?>(null)
    val expiringFoodResponse: StateFlow<ExpiringFoodResponse?> = _expiringFoodResponse

    init {
        fetchStatistics()
        fetchExpiringSoon()
    }

    private fun fetchStatistics() {
        _isLoadingStatistics.value = true
        _isError.value = false
        viewModelScope.launch {
            try {
                val response = ApiConfig.getApiService().getStatistic(token = "Bearer ${repository.getAccessToken()}")
                _userInventory.value = response
            } catch (error: SocketTimeoutException) {
                _errorMessage.value = "Request timed out. Please try again later."
                _isError.value = true
            } catch (error: HttpException) {
                _errorMessage.value = error.response()?.errorBody()?.string()
                _isError.value = true
            } finally {
                _isLoadingStatistics.value = false
            }
        }
    }

    private fun fetchExpiringSoon() {
        _isLoadingExpiring.value = true
        _isError.value = false
        viewModelScope.launch {
            try {
                val response = ApiConfig.getApiService().getExpiringSoon(token = "Bearer ${repository.getAccessToken()}")
                _expiringFoodResponse.value = response
            } catch (error: SocketTimeoutException) {
                _errorMessage.value = "Request timed out. Please try again later."
                _isError.value = true
            } catch (error: HttpException) {
                _errorMessage.value = error.response()?.errorBody()?.string()
                _isError.value = true
            } finally {
                _isLoadingExpiring.value = false
            }
        }
    }
}
