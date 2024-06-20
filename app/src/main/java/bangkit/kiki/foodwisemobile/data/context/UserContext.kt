package bangkit.kiki.foodwisemobile.data.context

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import bangkit.kiki.foodwisemobile.data.model.UserModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "session")

class UserContext private constructor(private val dataStore: DataStore<Preferences>) {
    suspend fun saveSession(user: UserModel) {
        dataStore.edit { preferences ->
            preferences[EMAIL_KEY] = user.email
            preferences[FULL_NAME_KEY] = user.fullName
            preferences[PROFILE_URL_KEY] = user.profileUrl
            preferences[ACCESS_TOKEN_KEY] = user.accessToken
            preferences[REFRESH_TOKEN_KEY] = user.refreshToken
            preferences[IS_LOGIN_KEY] = true
        }
    }

    fun getSession(): Flow<UserModel> {
        return dataStore.data.map { preferences ->
            UserModel(
                preferences[EMAIL_KEY] ?: "",
                preferences[FULL_NAME_KEY] ?: "",
                preferences[PROFILE_URL_KEY] ?: "",
                preferences[ACCESS_TOKEN_KEY] ?: "",
                preferences[REFRESH_TOKEN_KEY] ?: "",
                preferences[IS_LOGIN_KEY] ?: false
            )
        }
    }

    suspend fun logout() {
        dataStore.edit { preferences ->
            preferences.clear()
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: UserContext? = null

        private val EMAIL_KEY = stringPreferencesKey("email")
        private val FULL_NAME_KEY = stringPreferencesKey("fullName")
        private val PROFILE_URL_KEY = stringPreferencesKey("profileUrl")
        private val ACCESS_TOKEN_KEY = stringPreferencesKey("accessToken")
        private val REFRESH_TOKEN_KEY = stringPreferencesKey("refreshToken")
        private val IS_LOGIN_KEY = booleanPreferencesKey("isLogin")

        fun getInstance(dataStore: DataStore<Preferences>): UserContext {
            return INSTANCE ?: synchronized(this) {
                val instance = UserContext(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}