package bangkit.kiki.foodwisemobile.ui.inventory.component

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import bangkit.kiki.foodwisemobile.ui.theme.Black
import kotlinx.coroutines.launch

import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ProgressIndicatorDefaults
import bangkit.kiki.foodwisemobile.ui.theme.Green

@Composable
fun HeaderCard(
    inStockCount: Number,
    consumedCount: Number,
    expiredCount: Number,
    isLoading: Boolean
) {
    val animatedInStockCount = remember { Animatable(0f) }
    val animatedConsumedCount = remember { Animatable(0f) }
    val animatedExpiredCount = remember { Animatable(0f) }

    LaunchedEffect(inStockCount, consumedCount, expiredCount) {
        launch {
            animatedInStockCount.animateTo(
                targetValue = inStockCount.toFloat(),
                animationSpec = tween(durationMillis = 3000)
            )
        }
        launch {
            animatedConsumedCount.animateTo(
                targetValue = consumedCount.toFloat(),
                animationSpec = tween(durationMillis = 3000)
            )
        }
        launch {
            animatedExpiredCount.animateTo(
                targetValue = expiredCount.toFloat(),
                animationSpec = tween(durationMillis = 3000)
            )
        }
    }

    Card(
        shape = RoundedCornerShape(8.dp),
        elevation = 4.dp,
        modifier = Modifier.fillMaxWidth(),
        backgroundColor = Color.White
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            // Create a column for each count, using a loading indicator if isLoading is true
            CountColumn("In Stock", animatedInStockCount.value.toInt(), isLoading)
            CountColumn("Consumed", animatedConsumedCount.value.toInt(), isLoading)
            CountColumn("Expired", animatedExpiredCount.value.toInt(), isLoading)
        }
    }
}

@Composable
fun CountColumn(label: String, count: Number?, isLoading: Boolean) {
    Column(
        modifier = Modifier.padding(3.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (isLoading) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.size(24.dp)
            ) {
                CircularProgressIndicator(
                    color = Green,
                    strokeWidth = ProgressIndicatorDefaults.StrokeWidth
                )
            }
        } else {
            Text(
                text = count?.toString() ?: "0",
                style = TextStyle(
                    fontSize = 20.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold
                ),
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = label,
            style = TextStyle(
                fontSize = 16.sp,
                color = Color.Black,
                fontWeight = FontWeight.Normal
            ),
        )
    }
}