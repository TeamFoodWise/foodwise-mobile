package bangkit.kiki.foodwisemobile.data.api

import bangkit.kiki.foodwisemobile.data.dataClass.LoginRegisterResponse
import bangkit.kiki.foodwisemobile.data.dataClass.LoginRequest
import bangkit.kiki.foodwisemobile.data.dataClass.RegisterRequest
import bangkit.kiki.foodwisemobile.data.dataClass.UpdateProfileRequest
import bangkit.kiki.foodwisemobile.data.dataClass.UpdateProfileResponse
import bangkit.kiki.foodwisemobile.data.dataClass.DeleteItemRequest
import retrofit2.http.Body
import bangkit.kiki.foodwisemobile.data.model.ExpiringFoodResponse
import bangkit.kiki.foodwisemobile.data.model.UserInventoryResponse
import bangkit.kiki.foodwisemobile.data.model.CreateItemResponse
import bangkit.kiki.foodwisemobile.data.model.DeleteItemResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.HTTP
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT

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
    suspend fun getStatistic(): UserInventoryResponse

    @GET("${BASE_ITEM_URL}expiring-soon")
    suspend fun getExpiringSoon(): ExpiringFoodResponse

    //  Profile
    @PUT("${BASE_AUTH_URL}update-profile")
    suspend fun updateProfile(
        @Header("Authorization") token: String,
        @Body request: UpdateProfileRequest
    ): UpdateProfileResponse

    // Inventory
    @FormUrlEncoded
    @POST(BASE_ITEM_URL)
    suspend fun createItem(
        @Field("name") name: String,
        @Field("quantity") quantity: Int,
        @Field("type") type: String,
        @Field("measure") measure: String,
        @Field("expiredAt") expirationDate: String
    ): CreateItemResponse

    @HTTP(method = "DELETE", path = BASE_ITEM_URL, hasBody = true)
    suspend fun deleteItem(@Body requestBody: DeleteItemRequest): DeleteItemResponse

    companion object {
        const val BASE_AUTH_URL = "api/auth/"
        const val BASE_USER_URL = "api/user/"
        const val BASE_ITEM_URL = "api/items/"
    }
}