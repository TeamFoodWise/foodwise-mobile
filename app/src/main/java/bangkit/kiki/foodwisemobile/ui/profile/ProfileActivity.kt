package bangkit.kiki.foodwisemobile.ui.profile

import android.content.Intent
import androidx.compose.foundation.Image
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import bangkit.kiki.foodwisemobile.ui.ViewModelFactory
import bangkit.kiki.foodwisemobile.ui.element.BottomBar
import bangkit.kiki.foodwisemobile.ui.element.CustomButton
import bangkit.kiki.foodwisemobile.ui.theme.Black
import bangkit.kiki.foodwisemobile.ui.theme.FoodwiseMobileTheme
import bangkit.kiki.foodwisemobile.ui.theme.Red
import coil.compose.rememberAsyncImagePainter
import kotlinx.coroutines.launch
import bangkit.kiki.foodwisemobile.R
import bangkit.kiki.foodwisemobile.ui.editProfile.EditProfileActivity
import bangkit.kiki.foodwisemobile.ui.login.LoginActivity

class ProfileActivity : ComponentActivity() {
    private val viewModel by viewModels<ProfileViewModel> {
        ViewModelFactory.getInstance(this)
    }
    private var userFullName: String = ""
    private var userEmail: String = ""
    private var userAvatar: String  = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.getInsetsController(window, window.decorView).isAppearanceLightStatusBars = true

        viewModel.getSession().observe(this) { user ->
            userFullName = user.fullName
            userEmail = user.email

            userAvatar = if (user.profileUrl != "") {
                user.profileUrl
            } else {
                bangkit.kiki.foodwisemobile.util.AVATAR_DEFAULT
            }
        }

        setContent {
            FoodwiseMobileTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    ProfilePage(
                        userFullName = userFullName,
                        userEmail = userEmail,
                        userAvatar = userAvatar,
                        logoutOnClick = { viewModel.logout() }
                    )
                }
            }
        }
    }
}

@Composable
fun ProfilePage(
    userFullName: String,
    userEmail: String,
    userAvatar: String,
    logoutOnClick: suspend () -> Unit
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    Scaffold(bottomBar = { BottomBar(currentPage = "profile") }) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = rememberAsyncImagePainter(userAvatar),
                contentDescription = "Profile Picture",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(150.dp)
                    .clip(CircleShape)
            )

            Spacer(modifier = Modifier.height(16.dp))

            CustomButton(
                text = "Edit Profile",
                onClick = {
                    context.startActivity(Intent(context, EditProfileActivity::class.java))
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(android.graphics.Color.parseColor("#F5F5F5")), RoundedCornerShape(16.dp))
                    .padding(16.dp)
            ) {
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Name",
                            style = TextStyle(
                                color = Black,
                                fontSize = 16.sp
                            )
                        )
                        Text(
                            text = userFullName,
                            style = TextStyle(
                                color = Black,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Email",
                            style = TextStyle(
                                color = Black,
                                fontSize = 16.sp
                            )
                        )
                        Text(
                            text = userEmail,
                            style = TextStyle(
                                color = Black,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(android.graphics.Color.parseColor("#F5F5F5")), RoundedCornerShape(16.dp))
                    .padding(8.dp)
            ) {
                Button(
                    onClick = {
                        coroutineScope.launch {
                            logoutOnClick()
                            context.startActivity(Intent(context, LoginActivity::class.java))
                        }
                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
                    modifier = Modifier.padding(0.dp),
                    elevation = ButtonDefaults.elevation(0.dp, 0.dp, 0.dp, 0.dp, 0.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_baseline_logout_24), // Replace with your icon
                        contentDescription = "Logout",
                        tint = Red
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Logout",
                        color = Red,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }


            Spacer(modifier = Modifier.height(16.dp))


        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview5() {
    FoodwiseMobileTheme {
        ProfilePage(
            userFullName = "Test User",
            userEmail = "testuser@gmail.com",
            userAvatar = "",
            logoutOnClick = {  }
        )
    }
}