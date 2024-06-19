package bangkit.kiki.foodwisemobile.ui.element

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import bangkit.kiki.foodwisemobile.ui.theme.DarkGrey
import bangkit.kiki.foodwisemobile.ui.theme.Green
import bangkit.kiki.foodwisemobile.ui.theme.LightGrey

@Composable
fun CustomTextInput(
    value: String,
    onValueChange: (String) -> Unit,
    title: String? = "",
    errorMessage: String? = "",
    type: String? = "",
    placeholder: String? = ""
) {
    var passwordVisible by remember { mutableStateOf(false) }

    Column {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            label = {
                if (!title.isNullOrEmpty()) {
                    Text(
                        text = title,
                        style = TextStyle(color = DarkGrey),
                        modifier = Modifier.padding(vertical = 3.dp)
                    )
                }
            },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = if (type == "number") KeyboardType.Number else if (type == "password") KeyboardType.Password else KeyboardType.Text),
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = if (passwordVisible || type != "password") VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                val image =
                    if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff

                if (type == "password") {
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(
                            imageVector = image,
                            tint = DarkGrey,
                            contentDescription = "Toggle password visibility"
                        )
                    }
                }
            },
            placeholder = {
                if (!placeholder.isNullOrEmpty()) {
                    Text(text = placeholder)
                }
            },
            isError = errorMessage != "",
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Green,
                unfocusedBorderColor = LightGrey,
                errorLabelColor = Color.Red
            ),
        )

        errorMessage?.let { error ->
            Text(
                text = error,
                color = Color.Red,
                style = TextStyle(fontSize = 12.sp),
                modifier = Modifier.align(Alignment.Start)
            )
        }
    }
}