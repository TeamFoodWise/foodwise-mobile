package bangkit.kiki.foodwisemobile.ui.inventory.component

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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

@Composable
fun HeaderCard(inStockCount: Number, consumedCount: Number, expiredCount: Number) {
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
            Column (
                modifier = Modifier
                    .padding(3.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = animatedInStockCount.value.toInt().toString(),
                    style = TextStyle(
                        fontSize = 20.sp,
                        color = Black,
                        fontWeight = FontWeight.Bold
                    ),
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "In Stock",
                    style = TextStyle(
                        fontSize = 16.sp,
                        color = Black,
                        fontWeight = FontWeight.Normal
                    ),
                )
            }
            Column (
                modifier = Modifier
                    .padding(3.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = animatedConsumedCount.value.toInt().toString(),
                    style = TextStyle(
                        fontSize = 20.sp,
                        color = Black,
                        fontWeight = FontWeight.Bold
                    ),
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Consumed",
                    style = TextStyle(
                        fontSize = 16.sp,
                        color = Black,
                        fontWeight = FontWeight.Normal
                    ),
                )
            }
            Column (
                modifier = Modifier
                    .padding(3.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = animatedExpiredCount.value.toInt().toString(),
                    style = TextStyle(
                        fontSize = 20.sp,
                        color = Black,
                        fontWeight = FontWeight.Bold
                    ),
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Expired",
                    style = TextStyle(
                        fontSize = 16.sp,
                        color = Black,
                        fontWeight = FontWeight.Normal
                    ),
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewHeaderCard() {
    HeaderCard(10, 101, 9)
}
