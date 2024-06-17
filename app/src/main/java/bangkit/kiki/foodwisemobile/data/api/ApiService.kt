package bangkit.kiki.foodwisemobile.data.api

import bangkit.kiki.foodwisemobile.data.api.ApiService.Companion.BASE_USER_URL
import bangkit.kiki.foodwisemobile.data.dataClass.LoginResponse
import bangkit.kiki.foodwisemobile.data.model.UserInventoryModel
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
    ): LoginResponse

    @GET("${BASE_USER_URL}statistics")
    suspend fun getStatistic() : UserInventoryModel

    companion object {
        const val BASE_AUTH_URL = "api/auth/"
        const val BASE_USER_URL = "api/user/"
    }
}