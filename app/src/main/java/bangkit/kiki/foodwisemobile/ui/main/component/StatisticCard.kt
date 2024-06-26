package bangkit.kiki.foodwisemobile.ui.main.component

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import bangkit.kiki.foodwisemobile.ui.theme.Green

@Composable
fun StatisticCard(title: String, count: Int, modifier: Modifier = Modifier) {
    val animatedCount = remember { Animatable(0f) }

    LaunchedEffect(count) {
        animatedCount.animateTo(
            targetValue = count.toFloat(),
            animationSpec = tween(durationMillis = 3000)
        )
    }

    Card(
        shape = RoundedCornerShape(8.dp),
        elevation = 4.dp,
        modifier = modifier
            .height(100.dp),
        backgroundColor = Color.White
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = title, fontSize = 14.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = animatedCount.value.toInt().toString(),
                fontSize = 24.sp,
                color = if (title == "Expired") Color.Red else Green,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
