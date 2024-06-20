package bangkit.kiki.foodwisemobile.ui.register

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import bangkit.kiki.foodwisemobile.R
import bangkit.kiki.foodwisemobile.ui.ViewModelFactory
import bangkit.kiki.foodwisemobile.ui.element.CustomButton
import bangkit.kiki.foodwisemobile.ui.element.CustomTextInput
import bangkit.kiki.foodwisemobile.ui.login.LoginActivity
import bangkit.kiki.foodwisemobile.ui.main.MainActivity
import bangkit.kiki.foodwisemobile.ui.theme.Black
import bangkit.kiki.foodwisemobile.ui.theme.FoodwiseMobileTheme
import bangkit.kiki.foodwisemobile.ui.theme.Green
import bangkit.kiki.foodwisemobile.util.isEmailValid
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RegisterActivity : ComponentActivity() {
    private val viewModel by viewModels<RegisterViewModel> {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.getInsetsController(window, window.decorView).isAppearanceLightStatusBars = true
        setContent {
            FoodwiseMobileTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    RegisterScreen(viewModel = viewModel, activity = this)
                }
            }
        }
    }
}

@Composable
fun RegisterScreen(viewModel: RegisterViewModel, activity: ComponentActivity) {
    var fullName by remember { mutableStateOf("") }
    var fullNameErrorMessage by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var emailErrorMessage by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordErrorMessage by remember { mutableStateOf("") }
    var confirmationPassword by remember { mutableStateOf("") }
    var confirmationPasswordErrorMessage by remember { mutableStateOf("") }
    val isLoading by viewModel.isLoading.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(viewModel.isError) {
        viewModel.isError.observe(activity) { isError ->
            if (isError) {
                Toast.makeText(activity, viewModel.errorMessage.value, Toast.LENGTH_SHORT).show()
            }
        }
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        content = { innerPadding ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                item {
                    val imageResource = painterResource(id = R.drawable.foodwise)
                    Image(
                        painter = imageResource,
                        contentDescription = "FoodWise",
                        modifier = Modifier
                            .width(300.dp)
                            .height(300.dp)
                    )

                    Text(
                        text = "Register",
                        style = TextStyle(
                            color = Black,
                            fontSize = 32.sp,
                            fontWeight = FontWeight.Bold
                        )
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    CustomTextInput(
                        value = fullName,
                        onValueChange = { fullName = it },
                        title = "Full Name",
                        errorMessage = fullNameErrorMessage
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    CustomTextInput(
                        value = email,
                        onValueChange = { email = it },
                        title = "Email",
                        errorMessage = emailErrorMessage
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    CustomTextInput(
                        value = password,
                        onValueChange = { password = it },
                        title = "Password",
                        type = "password",
                        errorMessage = passwordErrorMessage
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    CustomTextInput(
                        value = confirmationPassword,
                        onValueChange = { confirmationPassword = it },
                        title = "Confirmation Password",
                        type = "password",
                        errorMessage = confirmationPasswordErrorMessage
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    CustomButton(
                        isLoading = isLoading,
                        text = "Create Account",
                        onClick = {
                            if (
                                fullName.isEmpty() ||
                                email.isEmpty() ||
                                password.isEmpty() ||
                                confirmationPassword.isEmpty()
                            ) {
                                fullNameErrorMessage = if (fullName.isEmpty()) {
                                    "Full name cannot be empty"
                                } else {
                                    ""
                                }

                                if (email.isEmpty()) {
                                    emailErrorMessage = "Email cannot be empty"
                                }

                                if (password.isEmpty()) {
                                    passwordErrorMessage = "Password cannot be empty"
                                }

                                if (confirmationPassword.isEmpty()) {
                                    confirmationPasswordErrorMessage = "Confirmation password cannot be empty"
                                }
                            }

                            if (isEmailValid(email)) {
                                emailErrorMessage = ""
                            } else if (email.isNotEmpty()) {
                                emailErrorMessage = "Invalid email format"
                            }

                            if (password.trim().length >= 8) {
                                passwordErrorMessage = ""
                            } else if (password.isNotEmpty()) {
                                passwordErrorMessage = "Password must be at least 8 characters"
                            }

                            if (confirmationPassword == password && confirmationPassword.trim().length >= 8) {
                                confirmationPasswordErrorMessage = ""
                            } else if (confirmationPassword.isNotEmpty() && confirmationPassword.trim().length < 8) {
                                confirmationPasswordErrorMessage = "Confirmation password must be at least 8 characters"
                            } else if (confirmationPassword.isNotEmpty() && confirmationPassword != password) {
                                confirmationPasswordErrorMessage = "Password does not match confirmation password"
                            }

                            if (
                                fullNameErrorMessage == "" &&
                                emailErrorMessage == "" &&
                                passwordErrorMessage == "" &&
                                confirmationPasswordErrorMessage == ""
                            ) {
                                CoroutineScope(Dispatchers.Main).launch {
                                    val success = viewModel.register(
                                        fullName,
                                        email,
                                        password,
                                        confirmationPassword
                                    )

                                    if (success) {
                                        Toast.makeText(activity, "Welcome to FoodWise!", Toast.LENGTH_SHORT).show()
                                        context.startActivity(Intent(context, MainActivity::class.java))
                                        activity.finish()
                                        fullName = ""
                                        email = ""
                                        password = ""
                                        confirmationPassword = ""
                                    }
                                }
                            }
                        }
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "Already have account?")
                        Button(
                            onClick = {
                                context.startActivity(Intent(context, LoginActivity::class.java))
                                fullName = ""
                                fullNameErrorMessage = ""
                                email = ""
                                emailErrorMessage = ""
                                password = ""
                                passwordErrorMessage = ""
                                confirmationPassword = ""
                                confirmationPasswordErrorMessage = ""
                            },
                            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
                            modifier = Modifier.padding(0.dp),
                            elevation = ButtonDefaults.elevation(0.dp, 0.dp, 0.dp, 0.dp, 0.dp)
                        ) {
                            Text(
                                text = "Login",
                                color = Green
                            )
                        }
                    }
                }
            }
        }
    )
}