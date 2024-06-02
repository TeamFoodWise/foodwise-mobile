package bangkit.kiki.foodwisemobile.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import bangkit.kiki.foodwisemobile.ui.theme.FoodwiseMobileTheme
import bangkit.kiki.foodwisemobile.ui.theme.LightGrey
import androidx.compose.ui.text.*
import bangkit.kiki.foodwisemobile.ui.element.CustomButton
import bangkit.kiki.foodwisemobile.ui.theme.Black


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FoodwiseMobileTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Greeting("Android")
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Composable
fun WelcomeHeader() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Home Page", fontSize = 18.sp)
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            Spacer(modifier = Modifier.height(10.dp))


            Text(
                text = "Welcome!",
                style = TextStyle(
                    fontSize = 20.sp,
                    color = LightGrey,
                    fontWeight = FontWeight.Bold
                ),
            )

            Text(
                text = "Ravandra Rifaqinara!",
                style = TextStyle(
                    fontSize = 24.sp,
                    color = Black,
                    fontWeight = FontWeight.Bold
                ),
            )

            Text(
                text = "ravandra@mail.com",
                style = TextStyle(
                    fontSize = 16.sp,
                    color = LightGrey,
                    fontWeight = FontWeight.Bold
                ),
            )

            LineSpacer()

            Text(
                text = "Expiring soon...",
                style = TextStyle(
                    fontSize = 20.sp,
                    color = Black,
                    fontWeight = FontWeight.Bold
                ),
            )

            Spacer(modifier = Modifier.height(7.dp))
            Text(
                text = "Eat your food before it expires in",
                fontSize = 16.sp,
                color = LightGrey
            )

            Spacer(modifier = Modifier.height(16.dp))

            val expiringItems = listOf(
                "Meat" to "2 days",
                "Ayam Kampung" to "2 days",
                "Lasagna" to "2 days",
                "Bawang Merah" to "2 days",
                "Lasagna" to "2 days",
                "Bawang Merah" to "2 days",
            )

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.height(270.dp)
            ) {
                items(expiringItems.take(6)) { item ->
                    ExpiringItemCard(name = item.first, daysLeft = item.second)
                }
            }

            CustomButton(text = "Go to inventory") {
//                TODO:
            }

            LineSpacer()
            Text(
                text = "Inventory Statistics",
                fontSize = 20.sp,
                color = Black,
                fontWeight = FontWeight.Bold
            )

            val statisticItems = listOf(
                "Consumed" to 3,
                "In Stock" to 14,
                "Expired" to 7,
            )

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                statisticItems.forEach { item ->
                    StatisticCard(title = item.first, count = item.second, modifier = Modifier.weight(1f))
                }
            }

        }
    }
}

@Composable
fun ExpiringItemCard(name: String, daysLeft: String) {
    Card(
        shape = RoundedCornerShape(8.dp),
        elevation = 4.dp,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
        ) {
            Text(text = name, fontSize = 16.sp)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = daysLeft, fontSize = 16.sp, color = LightGrey)
        }
    }
}
@Composable
fun StatisticCard(title: String, count: Int, modifier: Modifier = Modifier) {
    Card(
        shape = RoundedCornerShape(8.dp),
        elevation = 4.dp,
        modifier = modifier
            .height(100.dp) // Adjust height as needed
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = title, fontSize = 16.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = count.toString(),
                fontSize = 24.sp,
                color = if (title == "Expired") Color.Red else Color.Green,
                fontWeight = FontWeight.Bold
            )
        }
    }
}


@Composable
fun LineSpacer() {
    Spacer(modifier = Modifier.height(24.dp))

    Divider(color = LightGrey, thickness = 1.dp)

    Spacer(modifier = Modifier.height(24.dp))
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    FoodwiseMobileTheme {
        WelcomeHeader()
    }
}