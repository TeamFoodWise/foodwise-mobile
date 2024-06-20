package bangkit.kiki.foodwisemobile.ui.recipesRecommendation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import bangkit.kiki.foodwisemobile.data.api.ApiConfig
import bangkit.kiki.foodwisemobile.data.dataClass.ErrorResponse
import bangkit.kiki.foodwisemobile.data.dataClass.RecipesItem
import bangkit.kiki.foodwisemobile.data.repository.UserRepository
import com.google.gson.Gson
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import retrofit2.HttpException

class RecipesRecommendationViewModel(private val repository: UserRepository): ViewModel() {
    private val _listRecipes = MutableLiveData<List<RecipesItem>?>()
    val listRecipes: LiveData<List<RecipesItem>?> = _listRecipes

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading

    private val _isError = MutableLiveData(false)
    val isError: LiveData<Boolean> = _isError

    private val _errorMessage = MutableLiveData("")
    val errorMessage: LiveData<String> = _errorMessage

    suspend fun getRecipesRecommendation() {
        _isLoading.value = true
        _isError.value = false

        try {
            val response = ApiConfig.getApiService().recipesRecommendation(
                "Bearer ${repository.getAccessToken()}"
            )

            _listRecipes.value = response.recipes
        } catch (error: HttpException) {
            val jsonInString = error.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, ErrorResponse::class.java)

            _errorMessage.value = errorBody.error
            _isError.value = true
        } finally {
            _isLoading.value = false
        }
    }
}