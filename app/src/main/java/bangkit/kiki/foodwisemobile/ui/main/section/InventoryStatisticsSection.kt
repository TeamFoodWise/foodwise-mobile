package bangkit.kiki.foodwisemobile.ui.main.section

import android.content.Intent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import bangkit.kiki.foodwisemobile.data.model.UserInventoryResponse
import bangkit.kiki.foodwisemobile.ui.element.CustomButton
import bangkit.kiki.foodwisemobile.ui.freshness.FreshnessActivity
import bangkit.kiki.foodwisemobile.ui.main.component.CircularProgressBar
import bangkit.kiki.foodwisemobile.ui.main.component.CustomLinearProgressIndicator
import bangkit.kiki.foodwisemobile.ui.main.component.LineSpacer
import bangkit.kiki.foodwisemobile.ui.main.component.StatisticCard
import bangkit.kiki.foodwisemobile.ui.theme.Black

@Composable
fun InventoryStatisticsSection(
    userInventory: UserInventoryResponse?,
    statisticItems: List<Pair<String, Int>>,
    isLoading: Boolean
) {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
    ) {
        LineSpacer()

        CustomButton(
            text = "Check item freshness",
            onClick = { context.startActivity(Intent(context, FreshnessActivity::class.java)) }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Inventory Statistics",
            fontSize = 20.sp,
            color = Black,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            userInventory?.let {
                // Circular Progress
                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CircularProgressBar(percentage = it.currentProgress)
                }

                Spacer(modifier = Modifier.height(16.dp))

                if (statisticItems.any { item -> item.second > 0 }) {
                    Text(
                        text = "Achieve 100% progress in ${it.remainingDays} days to ensure no food or drink is wasted.",
                        fontSize = 14.sp,
                    )
                }

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

                // LineProgress
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "History Progress",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))

                if (it.historyProgress == null) {
                    Text(
                        text = "You don't have any progress data for the previous month.",
                        fontSize = 14.sp,
                        color = Black
                    )
                } else {
                    CustomLinearProgressIndicator(
                        progress = it.historyProgress / 100f,
                        text = "Previous month"
                    )
                }
            }
        }
    }
}