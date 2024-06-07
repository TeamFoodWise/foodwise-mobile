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
import bangkit.kiki.foodwisemobile.ui.element.BottomBar
import bangkit.kiki.foodwisemobile.ui.theme.FoodwiseMobileTheme
import bangkit.kiki.foodwisemobile.ui.main.component.LineSpacer
import bangkit.kiki.foodwisemobile.ui.main.section.ExpiringSection
import bangkit.kiki.foodwisemobile.ui.main.section.HeaderSection
import bangkit.kiki.foodwisemobile.ui.main.section.InventoryStatisticsSection


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
            InventoryStatisticsSection()
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
        InventoryStatisticsSection()
    }
}