package bangkit.kiki.foodwisemobile.data.repository

import bangkit.kiki.foodwisemobile.data.context.UserContext
import bangkit.kiki.foodwisemobile.data.model.UserModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

class UserRepository private constructor(private val userContext: UserContext){
    suspend fun saveSession(user: UserModel) {
        userContext.saveSession(user)
    }

    fun getSession(): Flow<UserModel> {
        return userContext.getSession()
    }

    suspend fun logout() {
        userContext.logout()
    }

    fun getAccessToken(): String {
        return runBlocking {
            userContext.getSession().first().accessToken
        }
    }

    companion object {
        @Volatile
        private var instance: UserRepository? = null

        fun getInstance(
            userContext: UserContext
        ): UserRepository =
            instance ?: synchronized(this) {
                instance ?: UserRepository(userContext)
            }.also { instance = it }
    }
}