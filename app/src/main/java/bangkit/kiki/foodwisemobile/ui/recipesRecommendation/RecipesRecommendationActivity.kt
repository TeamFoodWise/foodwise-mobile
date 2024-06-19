package bangkit.kiki.foodwisemobile.ui.recipesRecommendation

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import bangkit.kiki.foodwisemobile.R
import bangkit.kiki.foodwisemobile.data.model.RecipeRecommendationModel
import bangkit.kiki.foodwisemobile.ui.ViewModelFactory
import bangkit.kiki.foodwisemobile.ui.element.BottomBar
import bangkit.kiki.foodwisemobile.ui.element.CustomButton
import bangkit.kiki.foodwisemobile.ui.inventory.InventoryActivity
import bangkit.kiki.foodwisemobile.ui.recipesRecommendation.component.RecipeRecommendationCard
import bangkit.kiki.foodwisemobile.ui.theme.FoodwiseMobileTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RecipesRecommendationActivity : ComponentActivity() {
    private val viewModel by viewModels<RecipesRecommendationViewModel> {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.getInsetsController(window, window.decorView).isAppearanceLightStatusBars = true

        CoroutineScope(Dispatchers.Main).launch {
            viewModel.getRecipesRecommendation()
        }

        setContent {
            FoodwiseMobileTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    RecipesRecommendationPage(viewModel)
                }
            }
        }
    }
}

@Composable
fun RecipesRecommendationPage(viewModel: RecipesRecommendationViewModel) {
    val isLoading by viewModel.isLoading.collectAsState()
    val listRecipes by viewModel.listRecipes.observeAsState()
    val context = LocalContext.current

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
                listRecipes?.let { recipes ->
                    if (recipes.isNotEmpty()) {
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(horizontal = 16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            items(recipes.size) { index ->
                                val recipe = recipes[index]
                                Spacer(modifier = Modifier.height(16.dp))
                                recipe.id?.let {
                                    recipe.name?.let { it1 ->
                                        RecipeRecommendationModel(
                                            id = it,
                                            name = it1,
                                            ingredients = recipe.ingredients as List<String>
                                        )
                                    }
                                }?.let {
                                    RecipeRecommendationCard(
                                        item = it
                                    )
                                }
                            }
                        }
                    } else {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(280.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.empty_item),
                                    contentDescription = "Empty Item",
                                    modifier = Modifier.size(220.dp)
                                )
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "You don't have any items in your inventory, let's update your inventory!",
                                fontSize = 16.sp,
                                textAlign = TextAlign.Center
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            CustomButton(
                                text = "Go to inventory",
                                onClick = { context.startActivity(Intent(context, InventoryActivity::class.java)) }
                            )
                        }
                    }
                }
            }
        }
    }
}