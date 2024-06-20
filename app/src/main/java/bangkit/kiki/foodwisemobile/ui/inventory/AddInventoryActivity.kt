package bangkit.kiki.foodwisemobile.ui.inventory

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import bangkit.kiki.foodwisemobile.ui.ViewModelFactory
import bangkit.kiki.foodwisemobile.ui.element.CustomButton
import bangkit.kiki.foodwisemobile.ui.element.CustomDateInput
import bangkit.kiki.foodwisemobile.ui.element.CustomTextInput
import bangkit.kiki.foodwisemobile.ui.element.CustomTextInputWithUnitDropdown
import bangkit.kiki.foodwisemobile.ui.theme.DarkGreen
import bangkit.kiki.foodwisemobile.ui.theme.FoodwiseMobileTheme
import bangkit.kiki.foodwisemobile.ui.theme.Green

class AddInventoryActivity : ComponentActivity() {
    private val inventoryViewModel by viewModels<AddInventoryViewModel> {
        ViewModelFactory.getInstance(this)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FoodwiseMobileTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    AddInventoryItemScreen(inventoryViewModel)
                }
            }
        }
    }
}

@Composable
fun AddInventoryItemScreen(viewModel: AddInventoryViewModel) {
    var name by remember { mutableStateOf("") }
    var quantity by remember { mutableStateOf("") }
    var measure by remember { mutableStateOf("") }
    var unit by remember { mutableStateOf("Gr") }
    var expiryDate by remember { mutableStateOf("") }
    var nameError by remember { mutableStateOf("") }
    var quantityError by remember { mutableStateOf("") }
    var measureError by remember { mutableStateOf("") }
    var expiryDateError by remember { mutableStateOf("") }
    var showDialog by remember { mutableStateOf(false) }
    var dialogMessage by remember { mutableStateOf("") }

    val isCreating by viewModel.isCreating.collectAsState()
    val createItemResult by viewModel.createItemResult.collectAsState()
    val isError by viewModel.isError.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()

    LaunchedEffect(createItemResult) {
        createItemResult?.let {
            dialogMessage = "Item Added: ${it.item.name}"
            showDialog = true
        }
    }

    LaunchedEffect(isError) {
        if (isError) {
            dialogMessage = errorMessage
            showDialog = true
        }
    }
    fun validateFields() {
        nameError = if (name.isEmpty()) "Food Name cannot be empty" else ""
        quantityError = if (quantity.isEmpty()) "Quantity cannot be empty" else ""
        measureError = if (measure.isEmpty()) "Measure per Quantity cannot be empty" else ""
        expiryDateError = if (expiryDate.isEmpty()) "Expired Date cannot be empty" else ""

        if (nameError.isEmpty() && quantityError.isEmpty() && measureError.isEmpty() && expiryDateError.isEmpty()) {
            // Handle the add to inventory action
            viewModel.createItem(name, quantity.toInt(), unit, measure, expiryDate)
        }
    }

    val density = LocalDensity.current
    val context = LocalContext.current


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
            Form(
                name, quantity, measure, expiryDate,
                nameError, quantityError, measureError, expiryDateError,
                { name = it }, { quantity = it }, { measure = it }, { expiryDate = it },
                unit, { unit = it },
                onAddInventoryClick = { validateFields() },
                isCreating
            )

            if (showDialog) {
                AlertDialog(
                    onDismissRequest = {
                        showDialog = false
                        val intent = Intent(context, InventoryActivity::class.java).apply {
                            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                        }
                        context.startActivity(intent)
                    },
                    confirmButton = {
                        TextButton(onClick = {
                            showDialog = false
                            val intent = Intent(context, InventoryActivity::class.java).apply {
                                flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                            }
                            context.startActivity(intent)
                        }) {
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

@Composable
fun Form(
    name: String, quantity: String, measure: String, expiryDate: String,
    nameError: String, quantityError: String, measureError: String, expiryDateError: String,
    onNameChange: (String) -> Unit, onQuantityChange: (String) -> Unit,
    onMeasureChange: (String) -> Unit, onExpiryDateChange: (String) -> Unit,
    selectedUnit: String, onUnitChange: (String) -> Unit,
    onAddInventoryClick: () -> Unit,
    isCreating: Boolean
) {
    Column(modifier = Modifier.padding(16.dp).fillMaxSize()) {
        CustomTextInput(value = name, onValueChange = onNameChange, title = "Food Name", errorMessage = nameError)
        CustomTextInput(value = quantity, onValueChange = onQuantityChange, title = "Quantity", errorMessage = quantityError, type = "number")
        CustomTextInputWithUnitDropdown(value = measure, onValueChange = onMeasureChange, title = "Measure per Quantity", type = "number", errorMessage = measureError, selectedUnit = selectedUnit, onUnitChange = onUnitChange)
        CustomDateInput(value = expiryDate, onValueChange = onExpiryDateChange, title = "Expired Date", errorMessage = expiryDateError)
        Spacer(modifier = Modifier.height(16.dp))
        CustomButton(text = "Add to Inventory", onClick = onAddInventoryClick, isLoading = isCreating)
    }
}