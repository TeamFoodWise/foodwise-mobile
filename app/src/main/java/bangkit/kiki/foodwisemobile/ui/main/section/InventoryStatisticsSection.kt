package bangkit.kiki.foodwisemobile.ui.main.section

import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import bangkit.kiki.foodwisemobile.ui.main.component.CircularProgressBar
import bangkit.kiki.foodwisemobile.ui.main.component.CustomLinearProgressIndicator
import bangkit.kiki.foodwisemobile.ui.main.component.LineSpacer
import bangkit.kiki.foodwisemobile.ui.main.component.StatisticCard
import bangkit.kiki.foodwisemobile.ui.theme.Black

@Composable
fun InventoryStatisticsSection() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
    ) {
        LineSpacer()

        Text(
            text = "Inventory Statistics",
            fontSize = 20.sp,
            color = Black,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Circular Progress
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressBar(percentage = 66f)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Capai progress 100% dalam 2 hari ke depan agar tidak ada makanan/minuman yang terbuang.",
            fontSize = 14.sp,
            textAlign = TextAlign.Center
        )

        // Stock Card
        val statisticItems = listOf(
            "Consumed" to 3,
            "In Stock" to 14,
            "Expired" to 7,
        )

        Spacer(modifier = Modifier.height(20.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            statisticItems.forEach { item ->
                StatisticCard(
                    title = item.first,
                    count = item.second,
                    modifier = Modifier.weight(1f)
                )
            }
        }

        //  LineProgress
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "History Progress",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))
        CustomLinearProgressIndicator(progress = 1f, text = "Bulan lalu")
    }
}
