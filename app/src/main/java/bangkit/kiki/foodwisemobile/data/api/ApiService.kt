package bangkit.kiki.foodwisemobile.data.api

import bangkit.kiki.foodwisemobile.data.dataClass.LoginRegisterResponse
import bangkit.kiki.foodwisemobile.data.model.ExpiringFoodResponse
import bangkit.kiki.foodwisemobile.data.model.UserInventoryResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    // Authentication
    @FormUrlEncoded
    @POST("${BASE_AUTH_URL}login")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): LoginRegisterResponse

    @FormUrlEncoded
    @POST("${BASE_AUTH_URL}register")
    suspend fun register(
        @Field("full_name") fullName: String,
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("confirm_password") confirmPassword: String,
    ): LoginRegisterResponse

    // Homepage
    @GET("${BASE_USER_URL}statistics")
    suspend fun getStatistic() : UserInventoryResponse

    @GET("${BASE_ITEM_URL}expiring-soon")
    suspend fun getExpiringSoon() : ExpiringFoodResponse

    companion object {
        const val BASE_AUTH_URL = "api/auth/"
        const val BASE_USER_URL = "api/user/"
        const val BASE_ITEM_URL = "api/items/"
    }
}