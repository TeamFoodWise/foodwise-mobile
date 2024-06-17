package bangkit.kiki.foodwisemobile.ui.element

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CustomButton(text: String, onClick: () -> Unit, isLoading: Boolean? = false) {
    Button(
        shape = RoundedCornerShape(8.dp),
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        enabled = !isLoading!!,
        elevation = ButtonDefaults.elevation(defaultElevation = 8.dp)
    ) {
        if (isLoading == true) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .size(16.dp),
                strokeWidth = 2.dp
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "Processing", modifier = Modifier.padding(vertical = 2.dp))
            }
        } else {
            Text(text = text, modifier = Modifier.padding(vertical = 2.dp))
        }
    }
}