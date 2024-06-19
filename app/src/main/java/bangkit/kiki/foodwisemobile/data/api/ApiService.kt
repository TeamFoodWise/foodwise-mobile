package bangkit.kiki.foodwisemobile.data.api

import bangkit.kiki.foodwisemobile.data.dataClass.*
import retrofit2.http.Body
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

    @PUT("${BASE_AUTH_URL}update-profile")
    suspend fun updateProfile(
        @Header("Authorization") token: String,
        @Body request: UpdateProfileRequest
    ): UpdateProfileResponse

    companion object {
        const val BASE_AUTH_URL = "api/auth/"
    }
}