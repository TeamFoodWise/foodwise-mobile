package bangkit.kiki.foodwisemobile.data.api

import bangkit.kiki.foodwisemobile.BuildConfig
import okhttp3.MultipartBody
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface SupabaseApiService {
    @Multipart
    @POST("storage/v1/object/buckets/profile-pictures/")
    suspend fun uploadFile(
        @Header("Authorization") token: String? = "Bearer ${BuildConfig.SUPABASE_API_KEY}",
        @Part file: MultipartBody.Part,
    )
}