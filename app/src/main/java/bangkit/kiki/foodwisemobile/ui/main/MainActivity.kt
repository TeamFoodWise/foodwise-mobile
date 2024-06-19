package bangkit.kiki.foodwisemobile.ui.main

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import bangkit.kiki.foodwisemobile.ui.element.BottomBar
import bangkit.kiki.foodwisemobile.ui.main.component.LineSpacer
import bangkit.kiki.foodwisemobile.ui.main.section.ExpiringSection
import bangkit.kiki.foodwisemobile.ui.main.section.HeaderSection
import bangkit.kiki.foodwisemobile.ui.main.section.InventoryStatisticsSection
import bangkit.kiki.foodwisemobile.ui.theme.FoodwiseMobileTheme


class MainActivity : ComponentActivity() {
    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.getInsetsController(window, window.decorView).isAppearanceLightStatusBars =
            true
        setContent {
            FoodwiseMobileTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    HomePage(viewModel = mainViewModel, activity = this)
                }
            }
        }
    }
}

@Composable
fun HomePage(viewModel: MainViewModel, activity: ComponentActivity) {
    val isLoadingStatistics by viewModel.isLoadingStatistics.collectAsState()
    val isLoadingExpiring by viewModel.isLoadingExpiring.collectAsState()

    LaunchedEffect(viewModel.isError) {
        viewModel.isError.observe(activity) { isError ->
            if (isError) {
                Toast.makeText(activity, viewModel.errorMessage.value, Toast.LENGTH_SHORT).show()
            }
        }
    }

    val userInventory = viewModel.userInventory.collectAsState()
    val expiringFoodResponse = viewModel.expiringFoodResponse.collectAsState()

    val statisticItems = listOf(
        "Consumed" to (userInventory.value?.consumedCount ?: 0),
        "In Stock" to (userInventory.value?.inStockCount ?: 0),
        "Expired" to (userInventory.value?.expiredCount ?: 0)
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

            InventoryStatisticsSection(
                userInventory = userInventory.value,
                statisticItems = statisticItems,
                isLoading = isLoadingStatistics
            )

            LineSpacer()

            ExpiringSection(
                expiringFoodResponse = expiringFoodResponse.value,
                isLoading = isLoadingExpiring
            )
        }
    }
}


//@Preview(showBackground = true)
//@Composable
//fun DefaultPreview() {
//    FoodwiseMobileTheme {
//        HomePage()
//    }
//}