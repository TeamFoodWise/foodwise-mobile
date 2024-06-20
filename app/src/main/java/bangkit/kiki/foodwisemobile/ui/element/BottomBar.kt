package bangkit.kiki.foodwisemobile.ui.element

import android.content.Intent
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Fastfood
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import bangkit.kiki.foodwisemobile.ui.theme.White
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocalDining
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import bangkit.kiki.foodwisemobile.ui.inventory.InventoryActivity
import bangkit.kiki.foodwisemobile.ui.main.MainActivity
import bangkit.kiki.foodwisemobile.ui.profile.ProfileActivity
import bangkit.kiki.foodwisemobile.ui.recipesRecommendation.RecipesRecommendationActivity
import bangkit.kiki.foodwisemobile.ui.theme.DarkGrey
import bangkit.kiki.foodwisemobile.ui.theme.Green

@Composable
fun BottomBar(currentPage: String) {
    val context = LocalContext.current

    BottomNavigation(
        backgroundColor = White,
        contentColor = DarkGrey,
        elevation = 5.dp
    ) {
        BottomNavigationItem(
            selected = currentPage.lowercase() == "home",
            label = {
                Text(
                    "Home",
                    style = TextStyle(color = if (currentPage.lowercase() == "home") Green else DarkGrey)
                )
            },
            icon = {
                Icon(
                    Icons.Filled.Home,
                    contentDescription = "Home",
                    tint = if (currentPage.lowercase() == "home") Green else DarkGrey
                )
            },
            onClick = { context.startActivity(Intent(context, MainActivity::class.java)) }
        )

        BottomNavigationItem(
            selected = currentPage.lowercase() == "inventory",
            label = {
                Text(
                    "Inventory",
                    style = TextStyle(color = if (currentPage.lowercase() == "inventory") Green else DarkGrey)
                )
            },
            icon = {
                Icon(
                    Icons.Filled.Fastfood,
                    contentDescription = "Inventory",
                    tint = if (currentPage.lowercase() == "inventory") Green else DarkGrey
                )
            },
            onClick = { context.startActivity(Intent(context, InventoryActivity::class.java)) }
        )

        BottomNavigationItem(
            selected = currentPage.lowercase() == "recipes",
            label = {
                Text(
                    "Recipes",
                    style = TextStyle(color = if (currentPage.lowercase() == "recipes") Green else DarkGrey)
                )
            },
            icon = {
                Icon(
                    Icons.Filled.LocalDining,
                    contentDescription = "Recipes",
                    tint = if (currentPage.lowercase() == "recipes") Green else DarkGrey
                )
            },
            onClick = { context.startActivity(Intent(context, RecipesRecommendationActivity::class.java)) }
        )

        BottomNavigationItem(
            selected = currentPage.lowercase() == "profile",
            label = {
                Text(
                    "Profile",
                    style = TextStyle(color = if (currentPage.lowercase() == "profile") Green else DarkGrey)
                )
            },
            icon = {
                Icon(
                    Icons.Filled.Person,
                    contentDescription = "Profile",
                    tint = if (currentPage.lowercase() == "profile") Green else DarkGrey
                )
            },
            onClick = { context.startActivity(Intent(context, ProfileActivity::class.java)) }
        )
    }
}