package bangkit.kiki.foodwisemobile.ui.recipeRecommendationDetail

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import bangkit.kiki.foodwisemobile.ui.ViewModelFactory
import bangkit.kiki.foodwisemobile.ui.element.BottomBar
import bangkit.kiki.foodwisemobile.ui.theme.Black
import bangkit.kiki.foodwisemobile.ui.theme.FoodwiseMobileTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RecipeRecommendationDetailActivity : ComponentActivity() {
    private val viewModel by viewModels<RecipeRecommendationDetailViewModel> {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.getInsetsController(window, window.decorView).isAppearanceLightStatusBars = true

        val recipeId = intent.getIntExtra(EXTRA_RECIPE_ID, 0)

        CoroutineScope(Dispatchers.Main).launch {
            viewModel.getRecipeRecommendationDetail(recipeId)
        }

        setContent {
            FoodwiseMobileTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    RecipeRecommendationDetailPage(viewModel = viewModel, activity = this)
                }
            }
        }
    }

    companion object {
        const val EXTRA_RECIPE_ID = "extra_recipe_id"
    }
}

@Composable
fun RecipeRecommendationDetailPage(
    viewModel: RecipeRecommendationDetailViewModel,
    activity: ComponentActivity
) {
    val isLoading by viewModel.isLoading.collectAsState()
    val recipe by viewModel.recipe.observeAsState()

    fun String.capitalizeFirst(): String {
        return this.replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }
    }

    val ingredientsString = recipe?.ingredients?.joinToString("\n") {
        "• ${it?.capitalizeFirst()}"
    }
    val stepsString = recipe?.steps?.joinToString("\n") {
        "• ${it?.capitalizeFirst()}"
    }


    LaunchedEffect(viewModel.isError) {
        viewModel.isError.observe(activity) { isError ->
            if (isError) {
                Toast.makeText(activity, viewModel.errorMessage.value, Toast.LENGTH_SHORT).show()
            }
        }
    }

    Scaffold(bottomBar = { BottomBar(currentPage = "recipes") }) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.Center
        ) {
            if (isLoading) {
                CircularProgressIndicator()
            } else {
                recipe?.let {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 16.dp)
                    ) {
                        item {
                            Spacer(modifier = Modifier.height(16.dp))

                            Text(
                                text = it.name ?: "",
                                color = Black,
                                fontWeight = FontWeight.Bold,
                                fontSize = 24.sp
                            )

                            Spacer(modifier = Modifier.height(16.dp))

                            Text(
                                text = "Ingredients:",
                                color = Black,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 16.sp
                            )

                            Spacer(modifier = Modifier.height(4.dp))

                            ingredientsString?.let { ingredients ->
                                Text(
                                    text = ingredients,
                                    color = Black,
                                    fontSize = 16.sp,
                                    modifier = Modifier.padding(horizontal = 8.dp)
                                )
                            }

                            Spacer(modifier = Modifier.height(16.dp))

                            Text(
                                text = "How to make:",
                                color = Black,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 16.sp
                            )

                            Spacer(modifier = Modifier.height(4.dp))

                            stepsString?.let { steps ->
                                Text(
                                    text = steps,
                                    color = Black,
                                    fontSize = 16.sp,
                                    modifier = Modifier.padding(horizontal = 8.dp)
                                )
                            }

                            Spacer(modifier = Modifier.height(16.dp))
                        }
                    }
                }
            }
        }
    }
}