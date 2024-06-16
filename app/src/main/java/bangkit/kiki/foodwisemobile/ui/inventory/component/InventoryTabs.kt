package bangkit.kiki.foodwisemobile.ui.inventory.component

import androidx.compose.foundation.layout.padding
import androidx.compose.material.TabRowDefaults
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import bangkit.kiki.foodwisemobile.ui.theme.Green

@Composable
fun InventoryTabs(selectedTabIndex: Int, onTabSelected: (Int) -> Unit) {
    TabRow(
        selectedTabIndex = selectedTabIndex,
        containerColor = Color.Transparent,
        contentColor = Green,
        indicator = { tabPositions ->
            TabRowDefaults.Indicator(
                Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex]),
                color = Green,

                )
        },
        modifier = Modifier
            .padding(horizontal = 16.dp)
    ) {
        Tab(
            selected = selectedTabIndex == 0,
            onClick = { onTabSelected(0) }
        ) {
            Text(
                "In Stock",
                modifier = Modifier.padding(16.dp),
                color = if (selectedTabIndex == 0) Green else Color.Gray
            )
        }
        Tab(
            selected = selectedTabIndex == 1,
            onClick = { onTabSelected(1) }
        ) {
            Text(
                "Consumed",
                modifier = Modifier.padding(16.dp),
                color = if (selectedTabIndex == 1) Green else Color.Gray
            )
        }
        Tab(
            selected = selectedTabIndex == 2,
            onClick = { onTabSelected(2) }
        ) {
            Text(
                "Expired",
                modifier = Modifier.padding(16.dp),
                color = if (selectedTabIndex == 2) Green else Color.Gray
            )
        }
    }
}
