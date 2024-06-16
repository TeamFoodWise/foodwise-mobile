package bangkit.kiki.foodwisemobile.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import bangkit.kiki.foodwisemobile.data.model.UserInventoryModel
import bangkit.kiki.foodwisemobile.ui.element.BottomBar
import bangkit.kiki.foodwisemobile.ui.theme.FoodwiseMobileTheme
import bangkit.kiki.foodwisemobile.ui.main.component.LineSpacer
import bangkit.kiki.foodwisemobile.ui.main.section.ExpiringSection
import bangkit.kiki.foodwisemobile.ui.main.section.HeaderSection
import bangkit.kiki.foodwisemobile.ui.main.section.InventoryStatisticsSection


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.getInsetsController(window, window.decorView).isAppearanceLightStatusBars = true
        setContent {
            FoodwiseMobileTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    HomePage()
                }
            }
        }
    }
}

@Composable
fun HomePage() {
    val userInventory = UserInventoryModel(
        consumedCount = 0,
        inStockCount = 0,
        expiredCount = 0,
        currentProgress = 0,
        remainingDays = 5,
        historyProgress = null
    )

    val statisticItems = listOf(
        "Consumed" to userInventory.consumedCount,
        "In Stock" to userInventory.inStockCount,
        "Expired" to userInventory.expiredCount
    )

    Scaffold(bottomBar = { BottomBar(currentPage = "home") }) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(10.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            HeaderSection()
            InventoryStatisticsSection(userInventory = userInventory, statisticItems = statisticItems)
            LineSpacer()
            ExpiringSection()

        }
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    FoodwiseMobileTheme {
        HomePage()
    }
}

@Preview(showBackground = true)
@Composable
fun ProgressPreview() {
    FoodwiseMobileTheme {
        ExpiringSection()
    }
}