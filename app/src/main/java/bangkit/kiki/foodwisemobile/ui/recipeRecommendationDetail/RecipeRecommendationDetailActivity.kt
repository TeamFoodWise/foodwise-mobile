package bangkit.kiki.foodwisemobile.ui.recipeRecommendationDetail

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import bangkit.kiki.foodwisemobile.data.model.RecipeRecommendationDetailModel
import bangkit.kiki.foodwisemobile.ui.element.BottomBar
import bangkit.kiki.foodwisemobile.ui.theme.Black
import bangkit.kiki.foodwisemobile.ui.theme.FoodwiseMobileTheme

class RecipeRecommendationDetailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.getInsetsController(window, window.decorView).isAppearanceLightStatusBars = true
        setContent {
            FoodwiseMobileTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    RecipeRecommendationDetailPage()
                }
            }
        }
    }

    companion object {
        const val EXTRA_RECIPE_ID = "extra_recipe_id"
    }
}

@Composable
fun RecipeRecommendationDetailPage() {
    val item = RecipeRecommendationDetailModel(
        id = "item-1",
        name = "Chicken Curry",
        ingredients = listOf("Chicken", "Coconut milk", "Shallots", "Garlic"),
        steps = listOf("Put the chicken", "Put the chicken", "Put the chicken", "Put the chicken")
    )

    val ingredientsString = item.ingredients.joinToString("\n") { "• $it" }
    val stepsString = item.steps.joinToString("\n") { "• $it" }

    Scaffold(bottomBar = { BottomBar(currentPage = "recipes") }) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            Text(
                text = item.name,
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

            Text(
                text = ingredientsString,
                color = Black,
                fontSize = 16.sp,
                modifier = Modifier
                    .padding(horizontal = 8.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Column {
                Text(
                    text = "How to make:",
                    color = Black,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = stepsString,
                color = Black,
                fontSize = 16.sp,
                modifier = Modifier
                    .padding(horizontal = 8.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview8() {
    FoodwiseMobileTheme {
        RecipeRecommendationDetailPage()
    }
}