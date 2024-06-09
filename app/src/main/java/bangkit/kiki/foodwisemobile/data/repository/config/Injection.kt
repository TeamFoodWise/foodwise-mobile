package bangkit.kiki.foodwisemobile.data.repository.config

import android.content.Context
import bangkit.kiki.foodwisemobile.data.context.UserContext
import bangkit.kiki.foodwisemobile.data.context.dataStore
import bangkit.kiki.foodwisemobile.data.repository.UserRepository

object Injection {
    fun provideRepository(context: Context): UserRepository {
        val userContext = UserContext.getInstance(context.dataStore)
        return UserRepository.getInstance(userContext)
    }
}