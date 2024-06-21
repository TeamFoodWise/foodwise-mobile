package bangkit.kiki.foodwisemobile.ui.freshness

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import bangkit.kiki.foodwisemobile.ui.element.BottomBar
import bangkit.kiki.foodwisemobile.R
import bangkit.kiki.foodwisemobile.ui.element.CustomButton
import bangkit.kiki.foodwisemobile.ui.theme.*
import coil.compose.rememberAsyncImagePainter
import org.tensorflow.lite.task.vision.classifier.Classifications
import java.text.NumberFormat

class FreshnessActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FoodwiseMobileTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    FreshnessCalculatorPage(activity = this)
                }
            }
        }
    }
}

@Composable
fun FreshnessCalculatorPage(activity: ComponentActivity) {
    var isLoading by remember { mutableStateOf(false) }
    var result by remember { mutableStateOf("0%") }
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    val context = LocalContext.current

    val pickImageLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        selectedImageUri = uri
    }

    val imageClassifierHelper = ImageClassifierHelper(
        context = context,
        classifierListener = object : ImageClassifierHelper.ClassifierListener{
            override fun onError(error: String) {
                Toast.makeText(activity, error, Toast.LENGTH_SHORT).show()
            }

            override fun onResults(results: List<Classifications>?, inferenceTime: Long) {
                results?.let { it ->
                    if (it.isNotEmpty() && it[0].categories.isNotEmpty()) {
                        val sortedCategories = it[0].categories.sortedByDescending { it?.score }
                        val topCategory = sortedCategories[0]
                        result = NumberFormat.getPercentInstance().format(topCategory.score)
                        Log.e("FRESHNESS_ACTIVITY", topCategory.score.toString())
                        Log.e("FRESHNESS_ACTIVITY", topCategory.label)
                        Log.e("FRESHNESS_ACTIVITY", topCategory.displayName)
                    } else {
                        Toast.makeText(activity, "Failed to predict", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    )

    Scaffold(bottomBar = { BottomBar(currentPage = "home") }) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.Center
        ) {
            if (isLoading) {
                CircularProgressIndicator()
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    item {
                        val painter = if (selectedImageUri != null) {
                            rememberAsyncImagePainter(model = selectedImageUri)
                        } else {
                            painterResource(id = R.drawable.ic_image_placeholder)
                        }

                        Image(
                            painter = painter,
                            contentDescription = "Item Image",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(350.dp)
                        )

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            if (result != "0%") {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(
                                        text = "Freshness:",
                                        color = Black,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 36.sp
                                    )

                                    Spacer(modifier = Modifier.height(8.dp))

                                    Text(
                                        text = result,
                                        color = Black,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 52.sp
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        CustomButton(
                            text = "Gallery",
                            buttonColor = DarkGreen,
                            onClick = { pickImageLauncher.launch("image/*") }
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        CustomButton(
                            isLoading = isLoading,
                            text = "Analyze",
                            onClick = {
                                if (selectedImageUri != null) {
                                    isLoading = true
                                    imageClassifierHelper.classifyStaticImage(selectedImageUri!!)
                                    isLoading = false
                                } else {
                                    Toast.makeText(activity, "Please select a photo to be analyzed first", Toast.LENGTH_SHORT).show()
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}