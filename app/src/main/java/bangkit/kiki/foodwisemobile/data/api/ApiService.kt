package bangkit.kiki.foodwisemobile.data.api

import bangkit.kiki.foodwisemobile.data.dataClass.LoginRegisterResponse
import bangkit.kiki.foodwisemobile.data.dataClass.LoginRequest
import bangkit.kiki.foodwisemobile.data.dataClass.RegisterRequest
import bangkit.kiki.foodwisemobile.data.dataClass.UpdateProfileResponse
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
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

    @FormUrlEncoded
    @PUT("${BASE_AUTH_URL}update-profile")
    suspend fun updateProfile(
        @Field("full_name") fullName: String,
        @Field("password") password: String,
        @Field("confirm_password") confirmPassword: String,
    ): UpdateProfileResponse

    companion object {
        const val BASE_AUTH_URL = "api/auth/"
    }
}