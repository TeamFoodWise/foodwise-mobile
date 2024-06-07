package bangkit.kiki.foodwisemobile.ui.main.component

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import bangkit.kiki.foodwisemobile.ui.theme.Green

@Composable
fun CircularProgressBar(percentage: Float, modifier: Modifier = Modifier) {
    var animatedProgress by remember { mutableFloatStateOf(0f) }
    LaunchedEffect(percentage) {
        animatedProgress = 0f
        animatedProgress = percentage
    }

    val progress by animateFloatAsState(
        targetValue = animatedProgress,
        animationSpec = tween(
            durationMillis = 3000,
            easing = FastOutSlowInEasing
        ), label = "animation"
    )
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .size(150.dp)
            .padding(16.dp)
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val strokeWidth = 20f
            drawArc(
                color = Color.Gray,
                startAngle = -90f,
                sweepAngle = 360f,
                useCenter = false,
                style = Stroke(strokeWidth, cap = StrokeCap.Round)
            )
            drawArc(
                color = Green,
                startAngle = -90f,
                sweepAngle = (360 * progress / 100),
                useCenter = false,
                style = Stroke(strokeWidth, cap = StrokeCap.Round)
            )
        }
        Text(
            text = "${progress.toInt()}%",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
    }
}
