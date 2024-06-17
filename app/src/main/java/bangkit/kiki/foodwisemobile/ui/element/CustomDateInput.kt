package bangkit.kiki.foodwisemobile.ui.element

import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import bangkit.kiki.foodwisemobile.ui.theme.DarkGrey
import bangkit.kiki.foodwisemobile.ui.theme.Green
import bangkit.kiki.foodwisemobile.ui.theme.LightGrey
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun CustomDateInput(
    value: String,
    onValueChange: (String) -> Unit,
    title: String,
    errorMessage: String? = ""
) {
    val context = LocalContext.current
    val dateFormatter = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
    val calendar = Calendar.getInstance()

    var isFormatError by remember { mutableStateOf(false) }
    var formatErrorMessage by remember { mutableStateOf("") }

    fun showDatePicker() {
        val datePickerDialog = DatePickerDialog(
            context,
            { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
                calendar.set(year, month, dayOfMonth)
                onValueChange(dateFormatter.format(calendar.time))
                isFormatError = false
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        datePickerDialog.datePicker.minDate = calendar.timeInMillis
        datePickerDialog.show()
    }

    fun validateDateFormat(date: String): Boolean {
        return try {
            dateFormatter.parse(date)
            true
        } catch (e: ParseException) {
            false
        }
    }

    Column {
        OutlinedTextField(
            value = value,
            onValueChange = {
                onValueChange(it)
                isFormatError = !validateDateFormat(it)
                formatErrorMessage = if (isFormatError) "Invalid date format. Use DD-MM-YYYY" else ""
            },
            label = { Text(text = title, style = TextStyle(color = DarkGrey)) },
            placeholder = { Text(text = "In DD-MM-YYYY Format", style = TextStyle(color = DarkGrey)) },
            trailingIcon = {
                IconButton(onClick = { showDatePicker() }) {
                    Icon(Icons.Default.DateRange, contentDescription = "Choose date", tint = if (isFormatError) Color.Red else Color.Gray)
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .clickable { showDatePicker() },
            readOnly = true,
            isError = errorMessage?.isNotEmpty() == true || isFormatError,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Green,
                unfocusedBorderColor = LightGrey,
                errorBorderColor = Color.Red,
                errorLabelColor = Color.Red
            )
        )

        if (errorMessage?.isNotEmpty() == true || isFormatError) {
            Text(
                text = errorMessage ?: formatErrorMessage,
                color = Color.Red,
                style = TextStyle(fontSize = 12.sp),
                modifier = Modifier.align(Alignment.Start)
            )
        }
    }
}
