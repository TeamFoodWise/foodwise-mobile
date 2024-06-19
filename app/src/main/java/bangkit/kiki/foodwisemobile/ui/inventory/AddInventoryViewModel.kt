package bangkit.kiki.foodwisemobile.ui.inventory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import bangkit.kiki.foodwisemobile.data.api.ApiConfig
import bangkit.kiki.foodwisemobile.data.model.CreateItemResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AddInventoryViewModel : ViewModel() {
    private val _isCreating = MutableStateFlow(false)
    val isCreating: StateFlow<Boolean> = _isCreating

    private val _createItemResult = MutableStateFlow<CreateItemResponse?>(null)
    val createItemResult: StateFlow<CreateItemResponse?> = _createItemResult

    private val _isError = MutableStateFlow(false)
    val isError: StateFlow<Boolean> = _isError

    private val _errorMessage = MutableStateFlow("")
    val errorMessage: StateFlow<String> = _errorMessage

    fun createItem(name: String, quantity: Int, type: String, measure: String, expirationDate: String) {
        _isCreating.value = true
        viewModelScope.launch {
            try {
                val response = ApiConfig.getApiService().createItem(name, quantity, type, measure, expirationDate)
                _createItemResult.value = response
            } catch (error: Exception) {
                _errorMessage.value = error.message ?: "An error occurred"
                _isError.value = true
            } finally {
                _isCreating.value = false
            }
        }
    }
}
