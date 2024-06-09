package bangkit.kiki.foodwisemobile.ui.inventory

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import androidx.compose.material.AlertDialog
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.ui.window.Dialog
import bangkit.kiki.foodwisemobile.ui.theme.LightGrey
import androidx.compose.ui.platform.LocalContext

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
                "In Stock",
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
fun InventoryItem(
    id: Int,
    name: String,
    expiryDate: String,
    quantity: Number,
    measure: String,
    showIconButton: Boolean,
    onDeleteClick: (Int) -> Unit
) {
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
                Text(name, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                Text(measure, fontSize = 16.sp)
                Text("Expired at $expiryDate", fontSize = 14.sp, color = Color.Gray)
            }
            if (showIconButton) {
                Box(modifier = Modifier.padding(end = 8.dp)) {
                    IconButton(onClick = { onDeleteClick(id) }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_eat),
                            contentDescription = "Delete",
                            tint = Color.Red
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun TestTopAppBar() {
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

    var selectedTabIndex by remember { mutableStateOf(0) }
    var showDialog by remember { mutableStateOf(false) }
    var itemIdToDelete by remember { mutableStateOf<Int?>(null) }
    val dataToShow = if (selectedTabIndex == 0) data1 else data2
    val context = LocalContext.current


    Scaffold (
        floatingActionButton = {
            FloatingActionButton(onClick = {
                val intent = Intent(context, AddInventoryActivity::class.java)
                context.startActivity(intent)
            }) {
                Icon(Icons.Filled.Add, contentDescription = "Add Item")
            }
        }
    ) { contentPadding ->
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
            onDismissRequest = { showDialog = false },
            onConfirm = {
                // Call API or handle the deletion logic here
                // For example: deleteItem(itemIdToDelete)
                showDialog = false
            },
            onCancel = { showDialog = false }
        )
    }
}


@Composable
fun ConsumeAlertDialog(
    onDismissRequest: () -> Unit,
    onConfirm: () -> Unit,
    onCancel: () -> Unit
) {
    Dialog(onDismissRequest = onDismissRequest) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = Color.White
        ) {
            Column(
                modifier = Modifier
                    .padding(20.dp)
                    .background(Color.White),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Confirm Consume Item",
                    style = TextStyle(
                        fontSize = 20.sp,
                        color = DarkGreen,
                        fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier.padding(vertical = 16.dp)
                )
                Text(
                    text = "Are you sure you want to consume this item?",
                    style = TextStyle(
                        fontSize = 16.sp,
                        color = Color.Gray
                    ),
                    modifier = Modifier.padding(bottom = 30.dp)
                )
                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    FilledTonalButton(
                        onClick = onCancel,
                        colors = ButtonDefaults.filledTonalButtonColors(
                            containerColor = LightGrey,
                            contentColor = Color.Black
                        ),
                        modifier = Modifier
                            .weight(1f)
                            .padding(4.dp)
                    ) {
                        Text(text = "Cancel")
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(
                        onClick = onConfirm,
                        colors = ButtonDefaults.buttonColors(Color.Red),
                        modifier = Modifier
                            .weight(1f)
                            .padding(4.dp)
                    ) {
                        Text(text = "Consume", color = Color.White)
                    }
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

@Preview(showBackground = true)
@Composable
fun PreviewAlertDialog() {
    ConsumeAlertDialog(
        onDismissRequest = { false },
        onConfirm = {
            // Call API or handle the deletion logic here
            // For example: deleteItem(itemIdToDelete)
            false
        },
        onCancel = { false }
    )
}