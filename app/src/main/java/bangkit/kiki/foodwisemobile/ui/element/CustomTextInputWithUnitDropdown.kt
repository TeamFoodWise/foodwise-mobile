package bangkit.kiki.foodwisemobile.ui.element

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import bangkit.kiki.foodwisemobile.ui.theme.DarkGrey
import bangkit.kiki.foodwisemobile.ui.theme.FoodwiseMobileTheme
import bangkit.kiki.foodwisemobile.ui.theme.Green
import bangkit.kiki.foodwisemobile.ui.theme.LightGrey

@Composable
fun CustomTextInputWithUnitDropdown(
    value: String,
    onValueChange: (String) -> Unit,
    title: String? = "",
    errorMessage: String? = "",
    type: String? = "",
    placeholder: String? = "",
    selectedUnit: String,
    onUnitChange: (String) -> Unit
) {
    val units = listOf("Gr", "mL")
    var expanded by remember { mutableStateOf(false) }

    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
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
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = if (type == "number") KeyboardType.Number else KeyboardType.Text),
                modifier = Modifier.weight(1f),

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
                )
            )

            Box(modifier = Modifier
                .padding(start = 8.dp)
                .align(Alignment.Bottom)) {
                Text(
                    text = selectedUnit,
                    modifier = Modifier
                        .clickable(onClick = { expanded = true })
                        .padding(horizontal = 8.dp, vertical = 12.dp),
                    color = DarkGrey
                )
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    modifier = Modifier
                        .background(LightGrey)
                        .width(160.dp)

                ) {
                    units.forEach { unit ->
                        DropdownMenuItem(onClick = {
                            onUnitChange(unit)
                            expanded = false
                        }) {
                            Text(text = unit)
                        }
                    }
                }
                Icon(
                    Icons.Filled.ArrowDropDown,
                    contentDescription = "Unit dropdown",
                    modifier = Modifier.clickable(onClick = { expanded = !expanded })
                )
            }
        }
        errorMessage?.let { error ->
            Text(
                text = error,
                color = Color.Red,
                style = TextStyle(fontSize = 12.sp),
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(start = 16.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewTestTopAppBar() {
    FoodwiseMobileTheme {
        CustomTextInputWithUnitDropdown(
            value = "500",
            onValueChange = {},
            title = "Measure per Quantity",
            errorMessage = "",
            type = "number",
            placeholder = "e.g., 500 gr, 200 mL",
            selectedUnit = "mL",
            onUnitChange = {}
        )
    }
}