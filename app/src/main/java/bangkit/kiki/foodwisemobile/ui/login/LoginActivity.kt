package bangkit.kiki.foodwisemobile.ui.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import bangkit.kiki.foodwisemobile.R
import bangkit.kiki.foodwisemobile.ui.element.CustomButton
import bangkit.kiki.foodwisemobile.ui.element.CustomTextInput
import bangkit.kiki.foodwisemobile.ui.register.RegisterActivity
import bangkit.kiki.foodwisemobile.ui.theme.*
import bangkit.kiki.foodwisemobile.util.isEmailValid

class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.getInsetsController(window, window.decorView).isAppearanceLightStatusBars = true
        setContent {
            FoodwiseMobileTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    LoginScreen(onLoginButtonClicked = { email, password ->
                        Log.e("LOGIN_SCREEN_EMAIL", email)
                        Log.e("LOGIN_SCREEN_PASSWORD", password)
                    })
                }
            }
        }
    }
}

@Composable
fun LoginScreen(onLoginButtonClicked: (String, String) -> Unit) {
    var email by remember { mutableStateOf("") }
    var emailErrorMessage by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordErrorMessage by remember { mutableStateOf("") }
    val context = LocalContext.current

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
                        text = "Login",
                        style = TextStyle(
                            color = Black,
                            fontSize = 32.sp,
                            fontWeight = FontWeight.Bold
                        )
                    )

                    Spacer(modifier = Modifier.height(24.dp))

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

                    Spacer(modifier = Modifier.height(24.dp))

                    CustomButton(
                        text = "Login",
                        onClick = {
                            if (email.isEmpty() || password.isEmpty()) {
                                if (email.isEmpty()) {
                                    emailErrorMessage = "Email cannot be empty"
                                }

                                if (password.isEmpty()) {
                                    passwordErrorMessage = "Password cannot be empty"
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

                            if (emailErrorMessage == "" && passwordErrorMessage == "") {
                                onLoginButtonClicked(email, password)
                                email = ""
                                password = ""
                            }
                        }
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "Don’t have account?")
                        Button(
                            onClick = {
                                context.startActivity(Intent(context, RegisterActivity::class.java))
                            },
                            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
                            modifier = Modifier.padding(0.dp),
                            elevation = ButtonDefaults.elevation(0.dp, 0.dp, 0.dp, 0.dp, 0.dp)
                        ) {
                            Text(
                                text = "Register",
                                color = Green
                            )
                        }
                    }
                }
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview3() {
    FoodwiseMobileTheme {
        LoginScreen(onLoginButtonClicked = { email, password ->
            Log.e("LOGIN_SCREEN_EMAIL", email)
            Log.e("LOGIN_SCREEN_PASSWORD", password)
        })
    }
}