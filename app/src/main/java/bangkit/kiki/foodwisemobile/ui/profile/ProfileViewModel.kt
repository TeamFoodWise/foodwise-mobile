package bangkit.kiki.foodwisemobile.ui.profile

import androidx.lifecycle.ViewModel
import bangkit.kiki.foodwisemobile.data.repository.UserRepository

class ProfileViewModel(private val repository: UserRepository): ViewModel() {
    suspend fun logout() {
        repository.logout()
    }
}