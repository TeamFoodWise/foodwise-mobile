package bangkit.kiki.foodwisemobile.ui.inventory

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Scaffold
import androidx.compose.material.TabRowDefaults
import androidx.compose.material.TopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import bangkit.kiki.foodwisemobile.R
import bangkit.kiki.foodwisemobile.ui.inventory.component.HeaderCard
import bangkit.kiki.foodwisemobile.ui.theme.DarkGreen
import bangkit.kiki.foodwisemobile.ui.theme.FoodwiseMobileTheme
import bangkit.kiki.foodwisemobile.ui.theme.Green

class InventoryActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FoodwiseMobileTheme {
                TestTopAppBar()
            }
        }
    }
}

@Composable
fun InventoryScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Green)
            .padding(16.dp)
    ) {
        InventoryHeader()
        Spacer(modifier = Modifier.height(16.dp))
        InventoryTabs(0, {})
        Spacer(modifier = Modifier.height(16.dp))
        InventoryList()
    }
}

@Composable
fun InventoryHeader() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF4CAF50), RoundedCornerShape(8.dp))
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        InventoryStat("In Stock", 90)
        InventoryStat("Consumed", 90)
        InventoryStat("Expired", 90)
    }
}

@Composable
fun InventoryStat(label: String, count: Int) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "$count", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color.White)
        Text(text = label, fontSize = 16.sp, color = Color.White)
    }
}

@Composable
fun InventoryTabs(selectedTabIndex: Int, onTabSelected: (Int) -> Unit) {
    TabRow(
        selectedTabIndex = selectedTabIndex,
        containerColor = Color.Transparent,
        contentColor = Green,
        indicator = { tabPositions ->
            TabRowDefaults.Indicator(
                Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex]),
                color = Green,

                )
        },
        modifier = Modifier
            .padding(horizontal = 16.dp)
    ) {
        Tab(
            selected = selectedTabIndex == 0,
            onClick = { onTabSelected(0) }
        ) {
            Text(
                "Available",
                modifier = Modifier.padding(16.dp),
                color = if (selectedTabIndex == 0) Green else Color.Gray
            )
        }
        Tab(
            selected = selectedTabIndex == 1,
            onClick = { onTabSelected(1) }
        ) {
            Text(
                "Consumed",
                modifier = Modifier.padding(16.dp),
                color = if (selectedTabIndex == 1) Green else Color.Gray
            )
        }
        Tab(
            selected = selectedTabIndex == 2,
            onClick = { onTabSelected(2) }
        ) {
            Text(
                "Expired",
                modifier = Modifier.padding(16.dp),
                color = if (selectedTabIndex == 2) Green else Color.Gray
            )
        }
    }
}


@Composable
fun InventoryList() {
    LazyColumn {
        items(3) {
            InventoryItem("Chicken", "23-10-2024", 10, "500gr")
        }
    }
}

@Composable
fun InventoryItem(name: String, expiryDate: String, quantity: Number, measure: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 5.dp),
        shape = RoundedCornerShape(8.dp),
        elevation = 8.dp,
        backgroundColor = Color.White
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Card(
                modifier = Modifier.size(90.dp),
                shape = RoundedCornerShape(8.dp),
                elevation = 8.dp,
                backgroundColor = Color.White
            ) {
                Box(
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "x$quantity",
                        fontSize = 30.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text("$name", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                Text(measure, fontSize = 16.sp)
                Text("Expired at $expiryDate", fontSize = 14.sp, color = Color.Gray)
            }
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_eat),
                    contentDescription = "Delete",
                    tint = Color.Red
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun InventoryPreview() {
    FoodwiseMobileTheme {
        InventoryScreen()
    }
}

@Composable
fun TestTopAppBar() {
    val data = listOf(
        mapOf(
            "name" to "Chicken",
            "expiryDate" to "23-10-2024",
            "quantity" to 10,
            "measure" to "500gr"
        ),
        mapOf(
            "name" to "Beef",
            "expiryDate" to "24-11-2024",
            "quantity" to 15,
            "measure" to "1000gr"
        ),
        mapOf(
            "name" to "Fish",
            "expiryDate" to "25-12-2024",
            "quantity" to 20,
            "measure" to "750gr"
        ),
        mapOf(
            "name" to "Pork",
            "expiryDate" to "26-01-2025",
            "quantity" to 25,
            "measure" to "200gr"
        ),
        mapOf(
            "name" to "Lamb",
            "expiryDate" to "27-02-2025",
            "quantity" to 30,
            "measure" to "450gr"
        ),
        mapOf(
            "name" to "Turkey",
            "expiryDate" to "28-03-2025",
            "quantity" to 12,
            "measure" to "500gr"
        ),
        mapOf(
            "name" to "Duck",
            "expiryDate" to "29-04-2025",
            "quantity" to 14,
            "measure" to "1000gr"
        ),
        mapOf(
            "name" to "Goat",
            "expiryDate" to "30-05-2025",
            "quantity" to 16,
            "measure" to "750gr"
        ),
        mapOf(
            "name" to "Venison",
            "expiryDate" to "31-06-2025",
            "quantity" to 18,
            "measure" to "200gr"
        ),
        mapOf(
            "name" to "Rabbit",
            "expiryDate" to "01-07-2025",
            "quantity" to 20,
            "measure" to "450gr"
        )
    )

    val density = LocalDensity.current

    Scaffold { contentPadding ->
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(contentPadding)) {
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
                    HeaderCard(inStockCount = 10, consumedCount = 101, expiredCount = 9)
                }
            }
            InventoryTabs(0, {})

            Spacer(modifier = Modifier.height(11.dp))

            LazyColumn(modifier = Modifier.weight(1f)) {
                items(data) { item ->
                    InventoryItem(
                        name = item["name"] as String,
                        expiryDate = item["expiryDate"] as String,
                        quantity = item["quantity"] as Int,
                        measure = item["measure"] as String
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewTestTopAppBar() {
    TestTopAppBar()

}