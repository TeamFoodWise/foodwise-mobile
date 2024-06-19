package bangkit.kiki.foodwisemobile.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import bangkit.kiki.foodwisemobile.config.Injection
import bangkit.kiki.foodwisemobile.data.repository.UserRepository
import bangkit.kiki.foodwisemobile.ui.editProfile.EditProfileViewModel
import bangkit.kiki.foodwisemobile.ui.inventory.AddInventoryViewModel
import bangkit.kiki.foodwisemobile.ui.inventory.InventoryViewModel
import bangkit.kiki.foodwisemobile.ui.login.LoginViewModel
import bangkit.kiki.foodwisemobile.ui.main.MainViewModel
import bangkit.kiki.foodwisemobile.ui.profile.ProfileViewModel
import bangkit.kiki.foodwisemobile.ui.recipeRecommendationDetail.RecipeRecommendationDetailViewModel
import bangkit.kiki.foodwisemobile.ui.recipesRecommendation.RecipesRecommendationViewModel
import bangkit.kiki.foodwisemobile.ui.register.RegisterViewModel
import bangkit.kiki.foodwisemobile.ui.splash.SplashViewModel

class ViewModelFactory(private val repository: UserRepository): ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T: ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(SplashViewModel::class.java) -> {
                SplashViewModel(repository) as T
            }
            modelClass.isAssignableFrom(ProfileViewModel::class.java) -> {
                ProfileViewModel(repository) as T
            }
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(repository) as T
            }
            modelClass.isAssignableFrom(RegisterViewModel::class.java) -> {
                RegisterViewModel(repository) as T
            }
            modelClass.isAssignableFrom(EditProfileViewModel::class.java) -> {
                EditProfileViewModel(repository) as T
            }
            modelClass.isAssignableFrom(RecipesRecommendationViewModel::class.java) -> {
                RecipesRecommendationViewModel(repository) as T
            }
            modelClass.isAssignableFrom(RecipeRecommendationDetailViewModel::class.java) -> {
                RecipeRecommendationDetailViewModel(repository) as T
            }
            modelClass.isAssignableFrom(MainViewModel::class.java) -> {
                MainViewModel(repository) as T
            }
            modelClass.isAssignableFrom(InventoryViewModel::class.java) -> {
                InventoryViewModel(repository) as T
            }
            modelClass.isAssignableFrom(AddInventoryViewModel::class.java) -> {
                AddInventoryViewModel(repository) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: ViewModelFactory? = null

        @JvmStatic
        fun getInstance(context: Context): ViewModelFactory {
            if (INSTANCE == null) {
                synchronized(ViewModelFactory::class.java) {
                    INSTANCE = ViewModelFactory(Injection.provideRepository(context))
                }
            }
            return INSTANCE as ViewModelFactory
        }
    }
}