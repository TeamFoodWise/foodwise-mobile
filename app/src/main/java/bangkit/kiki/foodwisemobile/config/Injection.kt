package bangkit.kiki.foodwisemobile.config

import android.content.Context
import bangkit.kiki.foodwisemobile.data.context.UserContext
import bangkit.kiki.foodwisemobile.data.context.dataStore
import bangkit.kiki.foodwisemobile.data.repository.UserRepository

object Injection {
    fun provideRepository(context: Context): UserRepository {
        val context = UserContext.getInstance(context.dataStore)
        return UserRepository.getInstance(context)
    }
}