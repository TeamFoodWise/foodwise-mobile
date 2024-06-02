package bangkit.kiki.foodwisemobile.ui.element

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CustomButton(text: String, onClick: () -> Unit) {
    Button(
        shape = RoundedCornerShape(8.dp),
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        elevation = ButtonDefaults.elevation(defaultElevation = 8.dp)
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(vertical = 2.dp)
        )
    }
}