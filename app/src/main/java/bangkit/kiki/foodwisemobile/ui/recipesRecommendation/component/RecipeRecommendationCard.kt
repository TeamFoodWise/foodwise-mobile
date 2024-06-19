package bangkit.kiki.foodwisemobile.ui.recipesRecommendation.component

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import bangkit.kiki.foodwisemobile.data.model.RecipeRecommendationModel
import bangkit.kiki.foodwisemobile.ui.recipeRecommendationDetail.RecipeRecommendationDetailActivity
import bangkit.kiki.foodwisemobile.ui.theme.Black
import bangkit.kiki.foodwisemobile.ui.theme.FoodwiseMobileTheme
import bangkit.kiki.foodwisemobile.ui.theme.Green
import bangkit.kiki.foodwisemobile.ui.theme.White

@Composable
fun RecipeRecommendationCard(item: RecipeRecommendationModel) {
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
                .clickable {
                    val intent = Intent(context, RecipeRecommendationDetailActivity::class.java)
                    intent.putExtra(RecipeRecommendationDetailActivity.EXTRA_RECIPE_ID, item.id)
                    context.startActivity(
                        Intent(
                            context,
                            RecipeRecommendationDetailActivity::class.java
                        )
                    )
                }
        ) {
            Column {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(35.dp)
                        .background(
                            color = Green,
                            shape = RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp, bottomStart = 0.dp, bottomEnd = 0.dp)
                        )
                )

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
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
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview7() {
    FoodwiseMobileTheme {
        RecipeRecommendationCard(
            item = RecipeRecommendationModel(
                id = 123,
                name = "Chicken Curry",
                ingredients = listOf("Chicken", "Coconut milk", "Shallots", "Garlic")
            )
        )
    }
}