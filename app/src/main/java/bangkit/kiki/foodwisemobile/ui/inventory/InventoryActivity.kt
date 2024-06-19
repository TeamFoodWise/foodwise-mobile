package bangkit.kiki.foodwisemobile.ui.inventory

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import bangkit.kiki.foodwisemobile.ui.element.BottomBar
import bangkit.kiki.foodwisemobile.ui.element.CustomButton
import bangkit.kiki.foodwisemobile.ui.inventory.component.ConsumeAlertDialog
import bangkit.kiki.foodwisemobile.ui.inventory.component.HeaderCard
import bangkit.kiki.foodwisemobile.ui.inventory.component.InventoryItem
import bangkit.kiki.foodwisemobile.ui.inventory.component.InventoryTabs
import bangkit.kiki.foodwisemobile.ui.main.MainViewModel
import bangkit.kiki.foodwisemobile.ui.theme.DarkGreen
import bangkit.kiki.foodwisemobile.ui.theme.FoodwiseMobileTheme
import bangkit.kiki.foodwisemobile.ui.theme.Green

class InventoryActivity : ComponentActivity() {
    private val inventoryViewModel: InventoryViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FoodwiseMobileTheme {
                InventoryPage(inventoryViewModel, this)
            }
        }
    }
}


@Composable
fun InventoryPage(viewModel: InventoryViewModel, activity: ComponentActivity) {
    val data1 = listOf(
        mapOf(
            "id" to 1,
            "name" to "Chicken",
            "expiryDate" to "23-10-2024",
            "quantity" to 10,
            "measure" to "500gr"
        ),
        mapOf(
            "id" to 2,
            "name" to "Beef",
            "expiryDate" to "24-11-2024",
            "quantity" to 15,
            "measure" to "1000gr"
        ),
        mapOf(
            "id" to 3,
            "name" to "Fish",
            "expiryDate" to "25-12-2024",
            "quantity" to 20,
            "measure" to "750gr"
        ),
        mapOf(
            "id" to 4,
            "name" to "Pork",
            "expiryDate" to "26-01-2025",
            "quantity" to 25,
            "measure" to "200gr"
        ),
        mapOf(
            "id" to 5,
            "name" to "Rabbit",
            "expiryDate" to "01-07-2025",
            "quantity" to 20,
            "measure" to "450gr"
        )
    )
    val data2 = listOf(
        mapOf(
            "id" to 6,
            "name" to "Lamb",
            "expiryDate" to "27-02-2025",
            "quantity" to 30,
            "measure" to "450gr"
        ),
        mapOf(
            "id" to 7,
            "name" to "Turkey",
            "expiryDate" to "28-03-2025",
            "quantity" to 12,
            "measure" to "500gr"
        ),
        mapOf(
            "id" to 8,
            "name" to "Duck",
            "expiryDate" to "29-04-2025",
            "quantity" to 14,
            "measure" to "1000gr"
        ),
        mapOf(
            "id" to 9,
            "name" to "Goat",
            "expiryDate" to "30-05-2025",
            "quantity" to 16,
            "measure" to "750gr"
        ),
        mapOf(
            "id" to 10,
            "name" to "Venison",
            "expiryDate" to "31-06-2025",
            "quantity" to 18,
            "measure" to "200gr"
        ),
        mapOf(
            "id" to 11,
            "name" to "Venison",
            "expiryDate" to "31-06-2025",
            "quantity" to 18,
            "measure" to "200gr"
        ),
        mapOf(
            "id" to 12,
            "name" to "Venison",
            "expiryDate" to "31-06-2025",
            "quantity" to 18,
            "measure" to "200gr"
        ),
    )

    val density = LocalDensity.current

    var selectedTabIndex by remember { mutableIntStateOf(0) }
    var showDialog by remember { mutableStateOf(false) }
    var itemIdToDelete by remember { mutableStateOf<Int?>(null) }
    val dataToShow = if (selectedTabIndex == 0) data1 else data2
    val context = LocalContext.current


    val inventorySummary by viewModel.inventorySummary.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState(initial = true)
    val isDeleting by viewModel.isDeleting.collectAsState()

    LaunchedEffect(viewModel.isError) {
        viewModel.isError.observe(activity) { isError ->
            if (isError) {
                Toast.makeText(activity, viewModel.errorMessage.value, Toast.LENGTH_SHORT).show()
            }
        }
    }


    Scaffold(bottomBar = { BottomBar(currentPage = "inventory") }) { contentPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(DarkGreen, Green),
                                startY = 0f,
                                endY = density.run { 200.dp.toPx() }
                            ),
                            shape = RoundedCornerShape(bottomStart = 30.dp, bottomEnd = 30.dp)
                        )
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 30.dp),
                        contentAlignment = Alignment.Center,
                    ) {
                        Text(
                            text = "Inventory",
                            style = TextStyle(
                                fontSize = 20.sp,
                                color = Color.White,
                                fontWeight = FontWeight.SemiBold
                            ),
                        )
                    }
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .offset(y = 150.dp)
                        .padding(horizontal = 30.dp)
                ) {
                    inventorySummary?.let { summary ->
                        HeaderCard(
                            inStockCount = summary.inStockCount,
                            consumedCount = summary.consumedCount,
                            expiredCount = summary.expiredCount,
                            isLoading = isLoading
                        )
                    }
                }
            }

            CustomButton(
                text = "Add Item",
                onClick = {
                    val intent = Intent(context, AddInventoryActivity::class.java)
                    context.startActivity(intent)
                },
                modifier = Modifier.padding(horizontal = 30.dp)
            )

            InventoryTabs(selectedTabIndex) { selectedTabIndex = it }

            Spacer(modifier = Modifier.height(11.dp))

            LazyColumn(modifier = Modifier.weight(1f)) {
                items(dataToShow) { item ->
                    InventoryItem(
                        id = item["id"] as Int,
                        name = item["name"] as String,
                        expiryDate = item["expiryDate"] as String,
                        quantity = item["quantity"] as Int,
                        measure = item["measure"] as String,
                        showIconButton = selectedTabIndex == 0,
                        onDeleteClick = {
                            itemIdToDelete = it
                            showDialog = true
                        }
                    )
                }
            }
        }
    }

    if (showDialog) {
        ConsumeAlertDialog(
            onDismissRequest = {
                if (!isDeleting) showDialog = false
            },
            onConfirm = {
                itemIdToDelete?.let {
                    viewModel.deleteFoodItem(it)
                }
            },
            onCancel = {
                if (!isDeleting) showDialog = false
            },
            isDeleting = isDeleting
        )
    }

    LaunchedEffect(isDeleting) {
        if (!isDeleting && showDialog) {
            showDialog = false
        }
    }
}


//@Preview(showBackground = true)
//@Composable
//fun PreviewTestTopAppBar() {
//    FoodwiseMobileTheme {
//        InventoryPage()
//    }
//}