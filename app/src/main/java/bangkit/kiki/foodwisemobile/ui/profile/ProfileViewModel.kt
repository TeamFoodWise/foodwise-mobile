package bangkit.kiki.foodwisemobile.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import bangkit.kiki.foodwisemobile.data.model.UserModel
import bangkit.kiki.foodwisemobile.data.repository.UserRepository

class ProfileViewModel(private val repository: UserRepository): ViewModel() {
    suspend fun logout() {
        repository.logout()
    }

    fun getSession(): LiveData<UserModel> {
        return repository.getSession().asLiveData()
    }
}