package bangkit.kiki.foodwisemobile.ui.editProfile

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import bangkit.kiki.foodwisemobile.R
import bangkit.kiki.foodwisemobile.ui.element.BottomBar
import bangkit.kiki.foodwisemobile.ui.element.CustomButton
import bangkit.kiki.foodwisemobile.ui.element.CustomTextInput
import bangkit.kiki.foodwisemobile.ui.profile.ProfileActivity
import bangkit.kiki.foodwisemobile.ui.theme.Black
import bangkit.kiki.foodwisemobile.ui.theme.FoodwiseMobileTheme
import bangkit.kiki.foodwisemobile.ui.theme.Green
import coil.compose.rememberAsyncImagePainter

class EditProfileActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.getInsetsController(window, window.decorView).isAppearanceLightStatusBars = true
        setContent {
            FoodwiseMobileTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    EditProfilePage()
                }
            }
        }
    }
}

@Composable
fun EditProfilePage() {
    var fullName by remember { mutableStateOf("Ravandra Rifaqinara!") }
    var fullNameErrorMessage by remember { mutableStateOf("") }
    var newPassword by remember { mutableStateOf("") }
    var newPasswordErrorMessage by remember { mutableStateOf("") }
    var confirmationNewPassword by remember { mutableStateOf("") }
    var confirmationNewPasswordErrorMessage by remember { mutableStateOf("") }
    val context = LocalContext.current
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }

    val pickImageLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        selectedImageUri = uri
    }

    Scaffold(bottomBar = { BottomBar(currentPage = "profile") }) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .clickable { pickImageLauncher.launch("image/*") }
            ) {
                val painter = if (selectedImageUri != null) {
                    rememberAsyncImagePainter(model = selectedImageUri)
                } else {
                    rememberAsyncImagePainter(
                        "https://t3.ftcdn.net/jpg/03/46/83/96/360_F_346839683_6nAPzbhpSkIpb8pmAwufkC7c5eD7wYws.jpg"
                    )
                }

                Image(
                    painter = painter,
                    contentDescription = "Profile Picture",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(150.dp)
                        .clip(CircleShape)
                )

                Box(
                    modifier = Modifier
                        .size(28.dp)
                        .background(Green, CircleShape)
                        .align(Alignment.BottomEnd)
                        .clickable{ pickImageLauncher.launch("image/*") },
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_baseline_edit_24),
                        contentDescription = "Edit Icon",
                        modifier = Modifier.size(16.dp)
                    )
                }
            }

            Text(
                text = "Full Name",
                style = TextStyle(
                    color = Black,
                    fontSize = 16.sp
                ),
                modifier = Modifier.align(Alignment.Start)
            )
            Spacer(modifier = Modifier.height(8.dp))
            CustomTextInput(
                value = fullName,
                onValueChange = { fullName = it },
                errorMessage = fullNameErrorMessage
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "New Password",
                style = TextStyle(
                    color = Black,
                    fontSize = 16.sp
                ),
                modifier = Modifier.align(Alignment.Start)
            )
            Spacer(modifier = Modifier.height(8.dp))
            CustomTextInput(
                value = newPassword,
                onValueChange = { newPassword = it },
                errorMessage = newPasswordErrorMessage,
                type = "password"
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Confirmation New Password",
                style = TextStyle(
                    color = Black,
                    fontSize = 16.sp
                ),
                modifier = Modifier.align(Alignment.Start)
            )
            Spacer(modifier = Modifier.height(8.dp))
            CustomTextInput(
                value = confirmationNewPassword,
                onValueChange = { confirmationNewPassword = it },
                errorMessage = confirmationNewPasswordErrorMessage,
                type = "password"
            )

            Spacer(modifier = Modifier.weight(1f))

            CustomButton(
                text = "Save Changes",
                onClick = {
                    fullNameErrorMessage = if (fullName.isEmpty()) {
                        "Full name cannot be empty"
                    } else {
                        ""
                    }

                    if (newPassword.isNotEmpty() || confirmationNewPassword.isNotEmpty()) {
                        if (newPassword.isEmpty()) {
                            newPasswordErrorMessage = "New password cannot be empty"
                        }

                        if (confirmationNewPassword.isEmpty()) {
                            confirmationNewPasswordErrorMessage = "Confirmation new password cannot be empty"
                        }

                        if (newPassword.trim().length >= 8) {
                            newPasswordErrorMessage = ""
                        } else if (newPassword.isNotEmpty()) {
                            newPasswordErrorMessage = "New password must be at least 8 characters"
                        }

                        if (confirmationNewPassword == newPassword && confirmationNewPassword.trim().length >= 8) {
                            confirmationNewPasswordErrorMessage = ""
                        } else if (confirmationNewPassword.isNotEmpty() && confirmationNewPassword.trim().length < 8) {
                            confirmationNewPasswordErrorMessage = "Confirmation new password must be at least 8 characters"
                        } else if (confirmationNewPassword.isNotEmpty() && confirmationNewPassword != newPassword) {
                            confirmationNewPasswordErrorMessage = "New Password does not match confirmation new password"
                        }

                        if (
                            fullNameErrorMessage == "" &&
                            newPasswordErrorMessage == "" &&
                            confirmationNewPasswordErrorMessage == ""
                        ) {
                            context.startActivity(Intent(context, ProfileActivity::class.java))
                            fullName = ""
                            newPassword = ""
                            confirmationNewPassword = ""
                        }
                    }
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview6() {
    FoodwiseMobileTheme {
        EditProfilePage()
    }
}