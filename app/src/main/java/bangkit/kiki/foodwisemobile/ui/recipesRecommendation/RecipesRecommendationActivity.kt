package bangkit.kiki.foodwisemobile.ui.recipesRecommendation

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import bangkit.kiki.foodwisemobile.data.model.RecipeRecommendationCardModel
import bangkit.kiki.foodwisemobile.ui.element.BottomBar
import bangkit.kiki.foodwisemobile.ui.main.MainActivity
import bangkit.kiki.foodwisemobile.ui.theme.Black
import bangkit.kiki.foodwisemobile.ui.theme.FoodwiseMobileTheme
import bangkit.kiki.foodwisemobile.ui.theme.Green
import bangkit.kiki.foodwisemobile.ui.theme.White

class RecipesRecommendationActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FoodwiseMobileTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    RecipesRecommendationPage()
                }
            }
        }
    }
}

@Composable
fun RecipeRecommendationCard(item: RecipeRecommendationCardModel) {
    val context = LocalContext.current

    val ingredientsString = item.ingredients.joinToString(
        separator = ", ",
        limit = item.ingredients.size,
        truncated = ""
    ) { ingredient ->
        if (ingredient == item.ingredients.first()) ingredient else if (ingredient == item.ingredients.last()) "and ${ingredient.lowercase()}" else ingredient.lowercase()
    }

    Column {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .shadow(
                    elevation = 2.dp,
                    shape = RoundedCornerShape(8.dp),
                    clip = false
                )
                .background(
                    White,
                    RoundedCornerShape(8.dp)
                )
                .border(
                    width = 1.dp,
                    color = Color(android.graphics.Color.parseColor("#D9D9D9")),
                    shape = RoundedCornerShape(8.dp)
                )
                .padding(16.dp)
                .clickable {
                    context.startActivity(Intent(context, MainActivity::class.java))
                }
        ) {
            Column {
                Box(
                    modifier = Modifier
                        .background(
                            Green, RoundedCornerShape(8.dp)
                        )
                        .padding(8.dp)
                ) {
                    Text(text = item.category, color = White)
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = item.name,
                    color = Black,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = ingredientsString,
                    color = Black
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
fun RecipesRecommendationPage() {
    Scaffold(bottomBar = { BottomBar(currentPage = "recipes") }) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Spacer(modifier = Modifier.height(16.dp))

                RecipeRecommendationCard(
                    item = RecipeRecommendationCardModel(
                        name = "Chicken Curry",
                        category = "Makanan",
                        ingredients = listOf("Chicken", "Coconut milk", "Shallots", "Garlic")
                    )
                )

                RecipeRecommendationCard(
                    item = RecipeRecommendationCardModel(
                        name = "Chicken Curry",
                        category = "Makanan",
                        ingredients = listOf("Chicken", "Coconut milk", "Shallots", "Garlic")
                    )
                )

                RecipeRecommendationCard(
                    item = RecipeRecommendationCardModel(
                        name = "Chicken Curry",
                        category = "Makanan",
                        ingredients = listOf("Chicken", "Coconut milk", "Shallots", "Garlic")
                    )
                )

                RecipeRecommendationCard(
                    item = RecipeRecommendationCardModel(
                        name = "Chicken Curry",
                        category = "Makanan",
                        ingredients = listOf("Chicken", "Coconut milk", "Shallots", "Garlic")
                    )
                )

                RecipeRecommendationCard(
                    item = RecipeRecommendationCardModel(
                        name = "Chicken Curry",
                        category = "Makanan",
                        ingredients = listOf("Chicken", "Coconut milk", "Shallots", "Garlic")
                    )
                )

                RecipeRecommendationCard(
                    item = RecipeRecommendationCardModel(
                        name = "Chicken Curry",
                        category = "Makanan",
                        ingredients = listOf("Chicken", "Coconut milk", "Shallots", "Garlic")
                    )
                )

                RecipeRecommendationCard(
                    item = RecipeRecommendationCardModel(
                        name = "Chicken Curry",
                        category = "Makanan",
                        ingredients = listOf("Chicken", "Coconut milk", "Shallots", "Garlic")
                    )
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview7() {
    FoodwiseMobileTheme {
        RecipesRecommendationPage()
    }
}