package bangkit.kiki.foodwisemobile.ui.main.section

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import bangkit.kiki.foodwisemobile.ui.theme.LightGrey
import bangkit.kiki.foodwisemobile.ui.theme.Black
import bangkit.kiki.foodwisemobile.ui.element.CustomButton
import bangkit.kiki.foodwisemobile.ui.main.component.ExpiringItemCard

@Composable
fun ExpiringSection() {
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
            // TODO: Handle button click
        }
    }
}
