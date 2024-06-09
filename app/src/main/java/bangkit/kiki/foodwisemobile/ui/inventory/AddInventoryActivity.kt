package bangkit.kiki.foodwisemobile.ui.inventory

import android.os.Bundle
import android.widget.DatePicker
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import bangkit.kiki.foodwisemobile.ui.element.CustomButton
import bangkit.kiki.foodwisemobile.ui.element.CustomDateInput
import bangkit.kiki.foodwisemobile.ui.element.CustomTextInput
import bangkit.kiki.foodwisemobile.ui.theme.FoodwiseMobileTheme
import bangkit.kiki.foodwisemobile.util.isEmailValid
import java.text.SimpleDateFormat
import java.util.*

class AddInventoryActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FoodwiseMobileTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    AddInventoryItemScreen()
                }
            }
        }
    }
}

@Composable
fun AddInventoryItemScreen() {
    var name by remember { mutableStateOf("") }
    var quantity by remember { mutableStateOf("") }
    var measure by remember { mutableStateOf("") }
    var expiryDate by remember { mutableStateOf("") }
    var nameError by remember { mutableStateOf("") }
    var quantityError by remember { mutableStateOf("") }
    var measureError by remember { mutableStateOf("") }
    var expiryDateError by remember { mutableStateOf("") }

    fun validateFields() {
        nameError = if (name.isEmpty()) "Food Name cannot be empty" else ""
        quantityError = if (quantity.isEmpty()) "Quantity cannot be empty" else ""
        measureError = if (measure.isEmpty()) "Measure per Quantity cannot be empty" else ""
        expiryDateError = if (expiryDate.isEmpty()) "Expired Date cannot be empty" else ""

        if (nameError.isEmpty() && quantityError.isEmpty() && measureError.isEmpty() && expiryDateError.isEmpty()) {
            // Handle the add to inventory action
        }
    }

    Column(modifier = Modifier.padding(16.dp).fillMaxSize()) {
        Text(text = "Add Inventory Item", modifier = Modifier.padding(bottom = 16.dp))

        CustomTextInput(
            value = name,
            onValueChange = { name = it },
            title = "Food Name",
            errorMessage = nameError
        )

        CustomTextInput(
            value = quantity,
            onValueChange = { quantity = it },
            title = "Quantity",
            errorMessage = quantityError,
            type = "number"
        )

        CustomTextInput(
            value = measure,
            onValueChange = { measure = it },
            title = "Measure per Quantity",
            placeholder = "e.g. 500 gr, 200 ml",
            errorMessage = measureError
        )

        CustomDateInput(
            value = expiryDate,
            onValueChange = { expiryDate = it },
            title = "Expired Date",
            errorMessage = expiryDateError
        )


        Spacer(modifier = Modifier.height(16.dp))

        CustomButton(
            text = "Add to Inventory",
            onClick = { validateFields() }
        )
    }
}

