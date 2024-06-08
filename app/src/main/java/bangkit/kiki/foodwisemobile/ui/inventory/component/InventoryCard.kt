package bangkit.kiki.foodwisemobile.ui.inventory.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import bangkit.kiki.foodwisemobile.ui.theme.LightGrey

@Composable
fun InventoryCard(name: String, daysLeft: String) {
    Card(
        shape = RoundedCornerShape(8.dp),
        elevation = 4.dp,
        modifier = Modifier.fillMaxWidth(),
        backgroundColor = Color.White
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