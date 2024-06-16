package bangkit.kiki.foodwisemobile.ui.main.section

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import bangkit.kiki.foodwisemobile.R
import bangkit.kiki.foodwisemobile.data.model.ExpiringFoodResponse
import bangkit.kiki.foodwisemobile.ui.theme.LightGrey
import bangkit.kiki.foodwisemobile.ui.theme.Black
import bangkit.kiki.foodwisemobile.ui.element.CustomButton
import bangkit.kiki.foodwisemobile.ui.inventory.InventoryActivity
import bangkit.kiki.foodwisemobile.ui.main.component.ExpiringItemCard

@Composable
fun ExpiringSection(expiringFoodResponse: ExpiringFoodResponse) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
    ) {
        Text(
            text = "Expiring soon...",
            style = TextStyle(
                fontSize = 20.sp,
                color = Black,
                fontWeight = FontWeight.Bold
            ),
        )


        Spacer(modifier = Modifier.height(7.dp))

        if (expiringFoodResponse.foods.isEmpty()) {
            Text(
                text = "You don't have any food in your current inventory",
                style = TextStyle(
                    fontSize = 16.sp,
                    color = LightGrey,
                ),
            )
        } else {
            Text(
                text = "Eat your food before it expires in",
                fontSize = 16.sp,
                color = LightGrey
            )
        }
        Spacer(modifier = Modifier.height(16.dp))

        val expiringItems = expiringFoodResponse.foods

        val gridHeight = 280.dp

        if (expiringFoodResponse.foods.isEmpty()) {
            // Display placeholder image if foods list is empty
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(280.dp),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.empty_item),
                    contentDescription = "Empty Item",
                    modifier = Modifier.size(220.dp)
                )
            }
        } else {
            Box(modifier = Modifier.height(gridHeight)) {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    contentPadding = PaddingValues(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(expiringItems.take(6)) { item ->
                        ExpiringItemCard(name = item.name, daysLeft = "${item.remainingDays} days")
                    }
                }
            }
        }


        val context = LocalContext.current

        CustomButton(
            text = "Go to inventory",
            onClick = {
                val intent = Intent(context, InventoryActivity::class.java)
                context.startActivity(intent)
            }
        )
    }
}
