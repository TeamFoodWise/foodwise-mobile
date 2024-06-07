package bangkit.kiki.foodwisemobile.ui.main.component

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import bangkit.kiki.foodwisemobile.ui.theme.LightGrey

@Composable
fun LineSpacer() {
    Spacer(modifier = Modifier.height(24.dp))
    Divider(color = LightGrey, thickness = 1.dp)
    Spacer(modifier = Modifier.height(24.dp))
}