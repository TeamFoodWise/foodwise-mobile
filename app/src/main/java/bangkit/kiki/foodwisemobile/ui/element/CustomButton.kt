package bangkit.kiki.foodwisemobile.ui.element

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import bangkit.kiki.foodwisemobile.ui.theme.Green
import androidx.compose.ui.graphics.Color

@Composable
fun CustomButton(
    text: String,
    onClick: () -> Unit,
    isLoading: Boolean? = false,
    modifier: Modifier = Modifier,
    buttonColor: Color = Green
) {
    Button(
        shape = RoundedCornerShape(8.dp),
        onClick = onClick,
        modifier = modifier.fillMaxWidth(),
        enabled = !isLoading!!,
        elevation = ButtonDefaults.elevation(defaultElevation = 8.dp),
        colors = ButtonDefaults.buttonColors(backgroundColor = buttonColor)
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