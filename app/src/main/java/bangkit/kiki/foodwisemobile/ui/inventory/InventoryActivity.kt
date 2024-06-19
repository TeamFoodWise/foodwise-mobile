package bangkit.kiki.foodwisemobile.ui.inventory

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemsIndexed
import bangkit.kiki.foodwisemobile.data.model.FoodItemModel
import bangkit.kiki.foodwisemobile.ui.ViewModelFactory
import bangkit.kiki.foodwisemobile.ui.element.BottomBar
import bangkit.kiki.foodwisemobile.ui.element.CustomButton
import bangkit.kiki.foodwisemobile.ui.inventory.component.ConsumeAlertDialog
import bangkit.kiki.foodwisemobile.ui.inventory.component.HeaderCard
import bangkit.kiki.foodwisemobile.ui.inventory.component.InventoryItem
import bangkit.kiki.foodwisemobile.ui.inventory.component.InventoryTabs
import bangkit.kiki.foodwisemobile.ui.theme.DarkGreen
import bangkit.kiki.foodwisemobile.ui.theme.FoodwiseMobileTheme
import bangkit.kiki.foodwisemobile.ui.theme.Green

class InventoryActivity : ComponentActivity() {
    private val inventoryViewModel by viewModels<InventoryViewModel> {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FoodwiseMobileTheme {
                InventoryPage(inventoryViewModel, this)
            }
        }
    }
}


@Composable
fun InventoryPage(viewModel: InventoryViewModel, activity: ComponentActivity) {

    val density = LocalDensity.current

    var selectedTabIndex by remember { mutableIntStateOf(0) }
    var showDialog by remember { mutableStateOf(false) }
    val context = LocalContext.current

    var foodToDelete by remember { mutableStateOf<FoodItemModel?>(null) }

    val foodItemsFlow = viewModel.fetchFoodItems(selectedTabIndex + 1)
    val foodItems = foodItemsFlow.collectAsLazyPagingItems()


    val inventorySummary by viewModel.inventorySummary.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState(initial = true)
    val isDeleting by viewModel.isDeleting.collectAsState()

    LaunchedEffect(viewModel.isError) {
        viewModel.isError.observe(activity) { isError ->
            if (isError) {
                Toast.makeText(activity, viewModel.errorMessage.value, Toast.LENGTH_SHORT).show()
            }
        }
    }


    Scaffold(bottomBar = { BottomBar(currentPage = "inventory") }) { contentPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
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
                            text = "Inventory",
                            style = TextStyle(
                                fontSize = 20.sp,
                                color = Color.White,
                                fontWeight = FontWeight.SemiBold
                            ),
                        )
                    }
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .offset(y = 150.dp)
                        .padding(horizontal = 30.dp)
                ) {
                    inventorySummary?.let { summary ->
                        HeaderCard(
                            inStockCount = summary.inStockCount,
                            consumedCount = summary.consumedCount,
                            expiredCount = summary.expiredCount,
                            isLoading = isLoading
                        )
                    }
                }
            }

            CustomButton(
                text = "Add Item",
                onClick = {
                    val intent = Intent(context, AddInventoryActivity::class.java)
                    context.startActivity(intent)
                },
                modifier = Modifier.padding(horizontal = 30.dp)
            )

            InventoryTabs(selectedTabIndex) { selectedTabIndex = it }

            Spacer(modifier = Modifier.height(11.dp))


            LazyColumn{
                itemsIndexed(foodItems) { _, foodItem ->
                    Log.d("foodItem", foodItem.toString())
                    foodItem?.let {
                        InventoryItem(
                            id = it.id,
                            name = it.name,
                            expiryDate = it.expiredAt,
                            quantity = it.quantity,
                            measure = it.measure,
                            unit = it.unit,
                            showIconButton = selectedTabIndex == 0,
                            onDeleteClick = {
                                showDialog = true
                                foodToDelete = foodItem
                            }
                        )
                    }
                }

                foodItems.apply {
                    when {
//                        loadState.refresh is LoadState.Loading -> {
//                            // Initial load
//                            item { CircularProgressIndicator(modifier = Modifier.fillMaxWidth()) }
//                        }
                        loadState.refresh is LoadState.Loading -> {
                            items(4) {
                                LoadingInventoryItem()
                            }
                        }

                        loadState.append is LoadState.Loading -> {
                            // Loading more
                            items(3) {
                                LoadingInventoryItem()
                            }
//                            item { CircularProgressIndicator(modifier = Modifier.fillMaxWidth()) }
                        }

                        loadState.append is LoadState.Error -> {
                            // Error loading more items
                            item {
                                Text(
                                    text = "Error loading more items",
                                    modifier = Modifier.fillMaxWidth(),
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    }
                }
            }
        }

        if (showDialog) {
            ConsumeAlertDialog(
                onDismissRequest = {
                    if (!isDeleting) showDialog = false
                },
                onConfirm = {
                    foodToDelete?.let {
                        viewModel.deleteFoodItem(it.id, it.quantity)
                    }
                },
                onCancel = {
                    if (!isDeleting) showDialog = false
                },
                isDeleting = isDeleting
            )
        }

        LaunchedEffect(isDeleting) {
            if (!isDeleting && showDialog) {
                showDialog = false
            }
        }
    }
}


@Composable
fun LoadingInventoryItem() {
    val shimmerColors = listOf(
        Color.Gray.copy(alpha = 0.3f),
        Color.White.copy(alpha = 0.4f),
        Color.Gray.copy(alpha = 0.3f)
    )

    val transition = rememberInfiniteTransition(label = "transition")
    val translateAnim by transition.animateFloat(
        initialValue = -1000f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            tween(durationMillis = 1300, easing = LinearOutSlowInEasing),
            RepeatMode.Restart
        ), label = "anim"
    )

    val brush = Brush.horizontalGradient(
        colors = shimmerColors,
        startX = translateAnim - 400f,
        endX = translateAnim + 400f
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 30.dp, vertical = 10.dp)
            .background(
                color = Color.Gray.copy(alpha = 0.1f),
                shape = RoundedCornerShape(10.dp)
            )
            .padding(16.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(20.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(brush)
        )
        Spacer(modifier = Modifier.height(10.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(15.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(brush)
        )
        Spacer(modifier = Modifier.height(10.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth(0.5f)
                .height(15.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(brush)
        )
    }
}