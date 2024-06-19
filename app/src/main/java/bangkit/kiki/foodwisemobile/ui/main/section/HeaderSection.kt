package bangkit.kiki.foodwisemobile.ui.main.section

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import bangkit.kiki.foodwisemobile.ui.theme.Black
import bangkit.kiki.foodwisemobile.ui.theme.Green

@Composable
fun HeaderSection(userFullName: String, userEmail: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Text(
            text = "Welcome!",
            style = TextStyle(
                fontSize = 20.sp,
                color = Black,
                fontWeight = FontWeight.SemiBold
            ),
        )

        Text(
            text = userFullName,
            style = TextStyle(
                fontSize = 24.sp,
                color = Green,
                fontWeight = FontWeight.Bold
            ),
        )

        Text(
            text = userEmail,
            style = TextStyle(
                fontSize = 16.sp,
                color = Black,
                fontWeight = FontWeight.Normal
            ),
        )
    }
}
