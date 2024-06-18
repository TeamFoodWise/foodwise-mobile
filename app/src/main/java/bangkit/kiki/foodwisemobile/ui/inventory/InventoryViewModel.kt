package bangkit.kiki.foodwisemobile.ui.inventory

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import bangkit.kiki.foodwisemobile.data.api.ApiConfig
import bangkit.kiki.foodwisemobile.data.model.InventorySummaryModel
import bangkit.kiki.foodwisemobile.data.model.FoodItemModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.net.SocketTimeoutException

class InventoryViewModel : ViewModel() {
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading

    private val _isError = MutableLiveData(false)
    val isError: LiveData<Boolean> = _isError

    private val _errorMessage = MutableLiveData("")
    val errorMessage: LiveData<String> = _errorMessage

    private val _inventorySummary = MutableStateFlow<InventorySummaryModel?>(
        InventorySummaryModel(
            inStockCount = 0,
            consumedCount = 0,
            expiredCount = 0
        )
    )
    val inventorySummary: MutableStateFlow<InventorySummaryModel?> = _inventorySummary

    private val _foodItems = MutableLiveData<List<FoodItemModel>?>(emptyList())
    val foodItems: MutableLiveData<List<FoodItemModel>?> = _foodItems

    init {
        fetchInventorySummary()
//        fetchFoodItems()
    }

    private fun fetchInventorySummary() {
        _isLoading.value = true
        _isError.value = false
        viewModelScope.launch {
            try {
                val response = ApiConfig.getApiService().getStatistic()
                _inventorySummary.value = InventorySummaryModel(
                    inStockCount = response.inStockCount,
                    consumedCount = response.consumedCount,
                    expiredCount = response.expiredCount
                )
            } catch (error: SocketTimeoutException) {
                _errorMessage.value = "Request timed out. Please try again later."
                _isError.value = true
            } catch (error: HttpException) {
                _errorMessage.value = error.response()?.errorBody()?.string() ?: "An error occurred"
                _isError.value = true
            } finally {
                _isLoading.value = false
            }
        }
    }

//    private fun fetchFoodItems() {
//        _isLoading.value = true
//        _isError.value = false
//        viewModelScope.launch {
//            try {
//                val response = ApiConfig.getApiService().getInventoryItems()
//                _foodItems.value = response.foods
//            } catch (error: SocketTimeoutException) {
//                _errorMessage.value = "Request timed out. Please try again later."
//                _isError.value = true
//            } catch (error: HttpException) {
//                _errorMessage.value = error.response()?.errorBody()?.string() ?: "An error occurred"
//                _isError.value = true
//            } finally {
//                _isLoading.value = false
//            }
//        }
//    }

    fun deleteFoodItem(itemId: Int) {
        _isLoading.value = true
        _isError.value = false
        viewModelScope.launch {
            try {
                ApiConfig.getApiService().deleteItem(itemId)
                val updatedList = _foodItems.value?.filter { it.id != itemId }
                _foodItems.value = updatedList
            } catch (error: SocketTimeoutException) {
                _errorMessage.value = "Request timed out. Please try again later."
                _isError.value = true
            } catch (error: HttpException) {
                _errorMessage.value = error.response()?.errorBody()?.string() ?: "An error occurred"
                _isError.value = true
            } finally {
                _isLoading.value = false
            }
        }
    }
}
