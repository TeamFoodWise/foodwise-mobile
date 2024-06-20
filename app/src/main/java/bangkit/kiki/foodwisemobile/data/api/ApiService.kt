package bangkit.kiki.foodwisemobile.data.api

import bangkit.kiki.foodwisemobile.data.model.*
import retrofit2.http.Body
import retrofit2.http.GET
import bangkit.kiki.foodwisemobile.data.dataClass.*
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    // Authentication
    @POST("${BASE_AUTH_URL}login")
    suspend fun login(
        @Body request: LoginRequest
    ): LoginRegisterResponse

    @POST("${BASE_AUTH_URL}register")
    suspend fun register(
        @Body request: RegisterRequest
    ): LoginRegisterResponse

    // Homepage
    @GET("${BASE_USER_URL}statistics")
    suspend fun getStatistic(@Header("Authorization") token: String): UserInventoryResponse

    @GET("${BASE_ITEM_URL}expiring-soon")
    suspend fun getExpiringSoon(@Header("Authorization") token: String): ExpiringFoodResponse

    //  Profile
    @PUT("${BASE_AUTH_URL}update-profile")
    suspend fun updateProfile(
        @Header("Authorization") token: String,
        @Body request: UpdateProfileRequest
    ): UpdateProfileResponse

    // Recipe Recommendation
    @GET("${BASE_RECIPES_URL}recommendation")
    suspend fun recipesRecommendation(
        @Header("Authorization") token: String
    ): RecipesRecommendationResponse

    @GET("${BASE_RECIPES_URL}recommendation/{id}")
    suspend fun recipeRecommendationDetail(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ): RecipeRecommendationDetailResponse

    // Inventory
    @POST(BASE_ITEM_URL)
    suspend fun createItem(
        @Header("Authorization") token: String,
        @Body request: CreateItemRequest
    ): CreateItemResponse

    @POST(BASE_INVENTORY_URL)
    suspend fun deleteItem(
        @Header("Authorization") token: String,
        @Body requestBody: DeleteItemRequest
    ): DeleteItemResponse

    @GET(BASE_ITEM_URL)
    suspend fun getItems(
        @Query("page") page: Int,
        @Query("size") size: Int,
        @Query("type") type: Int,
        @Header("Authorization") token: String
    ): FoodResponse

    companion object {
        const val BASE_AUTH_URL = "api/auth/"
        const val BASE_USER_URL = "api/users/"
        const val BASE_ITEM_URL = "api/items/"
        const val BASE_INVENTORY_URL = "api/consumptions/"
        const val BASE_RECIPES_URL = "api/recipes/"
    }
}