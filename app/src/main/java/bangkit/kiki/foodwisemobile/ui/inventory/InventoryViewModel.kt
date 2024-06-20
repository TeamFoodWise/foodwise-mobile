package bangkit.kiki.foodwisemobile.ui.inventory

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import bangkit.kiki.foodwisemobile.data.api.ApiConfig
import bangkit.kiki.foodwisemobile.data.dataClass.DeleteItemRequest
import bangkit.kiki.foodwisemobile.data.model.FoodItemModel
import bangkit.kiki.foodwisemobile.data.model.InventorySummaryModel
import bangkit.kiki.foodwisemobile.data.paging.FoodItemsPagingSource
import bangkit.kiki.foodwisemobile.data.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.net.SocketTimeoutException

class InventoryViewModel(private val repository: UserRepository) : ViewModel() {

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading

    private val _isError = MutableLiveData(false)
    val isError: LiveData<Boolean> = _isError

    private val _errorMessage = MutableLiveData("")
    val errorMessage: LiveData<String> = _errorMessage

    private val _isDeleting = MutableStateFlow(false)
    val isDeleting: StateFlow<Boolean> = _isDeleting

    private val _inventorySummary = MutableStateFlow<InventorySummaryModel?>(
        InventorySummaryModel(
            inStockCount = 0,
            consumedCount = 0,
            expiredCount = 0
        )
    )
    val inventorySummary: MutableStateFlow<InventorySummaryModel?> = _inventorySummary

    init {
        fetchInventorySummary()
    }

    private fun fetchInventorySummary() {
        _isLoading.value = true
        _isError.value = false
        viewModelScope.launch {
            try {

                val response = ApiConfig.getApiService()
                    .getStatistic(token = "Bearer ${repository.getAccessToken()}")
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


    private var currentPagingSource: FoodItemsPagingSource? = null
    fun fetchFoodItems(type: Int): Flow<PagingData<FoodItemModel>> {
        return Pager(
            config = PagingConfig(pageSize = 20, enablePlaceholders = false),
            pagingSourceFactory = {
                FoodItemsPagingSource(
                    ApiConfig.getApiService(),
                    "Bearer ${repository.getAccessToken()}",
                    type
                ).also {
                    currentPagingSource = it
                }
            }
        ).flow
            .cachedIn(viewModelScope)
            .distinctUntilChanged()
    }


    private fun updateInventory(updatedSummary: InventorySummaryModel) {
        _inventorySummary.value = updatedSummary
    }

    fun deleteFoodItem(itemId: Int, quantity: Int) {
        val token = "Bearer ${repository.getAccessToken()}"
        _isDeleting.value = true
        _isError.value = false
        viewModelScope.launch {
            try {
                ApiConfig.getApiService().deleteItem(token, DeleteItemRequest(itemId, quantity))
                val currentSummary = _inventorySummary.value
                if (currentSummary != null) {
                    val newSummary = currentSummary.copy(
                        inStockCount = currentSummary.inStockCount.toInt() - quantity,
                        consumedCount = currentSummary.consumedCount.toInt() + quantity
                    )

                    _inventorySummary.value = newSummary
                }

                currentPagingSource?.invalidate()
                updateInventory(_inventorySummary.value!!)
            } catch (error: SocketTimeoutException) {
                _errorMessage.value = "Request timed out. Please try again later."
                _isError.value = true
            } catch (error: HttpException) {
                _errorMessage.value = error.response()?.errorBody()?.string() ?: "An error occurred"
                _isError.value = true
            } finally {
                _isDeleting.value = false
            }
        }
    }
}