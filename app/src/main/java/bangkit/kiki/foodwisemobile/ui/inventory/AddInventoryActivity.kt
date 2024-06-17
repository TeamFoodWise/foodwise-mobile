package bangkit.kiki.foodwisemobile.ui.inventory

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import bangkit.kiki.foodwisemobile.ui.element.CustomButton
import bangkit.kiki.foodwisemobile.ui.element.CustomDateInput
import bangkit.kiki.foodwisemobile.ui.element.CustomTextInput
import bangkit.kiki.foodwisemobile.ui.theme.DarkGreen
import bangkit.kiki.foodwisemobile.ui.theme.FoodwiseMobileTheme
import bangkit.kiki.foodwisemobile.ui.theme.Green

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
    var showDialog by remember { mutableStateOf(false) }
    var dialogMessage by remember { mutableStateOf("") }

    fun validateFields() {
        nameError = if (name.isEmpty()) "Food Name cannot be empty" else ""
        quantityError = if (quantity.isEmpty()) "Quantity cannot be empty" else ""
        measureError = if (measure.isEmpty()) "Measure per Quantity cannot be empty" else ""
        expiryDateError = if (expiryDate.isEmpty()) "Expired Date cannot be empty" else ""

        if (nameError.isEmpty() && quantityError.isEmpty() && measureError.isEmpty() && expiryDateError.isEmpty()) {
            // Handle the add to inventory action
            dialogMessage =
                "Food Name: $name\nQuantity: $quantity\nMeasure: $measure\nExpired Date: $expiryDate"
            showDialog = true
        }
    }

    val density = LocalDensity.current

    Scaffold { contentPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(210.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(DarkGreen, Green),
                                startY = 0f,
                                endY = density.run { 200.dp.toPx() }
                            ),
                            shape = RoundedCornerShape(bottomStart = 30.dp, bottomEnd = 30.dp)
                        )
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 30.dp),
                        contentAlignment = Alignment.Center,
                    ) {
                        Text(
                            text = "Add Inventory Item",
                            style = TextStyle(
                                fontSize = 20.sp,
                                color = Color.White,
                                fontWeight = FontWeight.SemiBold
                            ),
                        )
                    }
                }
            }
            Column(modifier = Modifier.padding(16.dp).fillMaxSize()) {
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

            if (showDialog) {
                AlertDialog(
                    onDismissRequest = { showDialog = false },
                    confirmButton = {
                        TextButton(onClick = { showDialog = false }) {
                            Text("OK")
                        }
                    },
                    title = { Text("Inventory Item Added") },
                    text = { Text(dialogMessage) }
                )
            }
        }
    }
}

