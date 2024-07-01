package com.roland.android.shopit.ui

import android.annotation.SuppressLint
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBackIosNew
import androidx.compose.material.icons.rounded.Shop
import androidx.compose.material.icons.rounded.ShoppingCart
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.roland.android.shopit.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
	title: String,
	subTitle: Int = 0,
	showBackButton: Boolean = false,
	navigateUp: () -> Unit = {}
) {
	TopAppBar(
		title = {
			Row(verticalAlignment = Alignment.CenterVertically) {
				Text(title, fontWeight = FontWeight.Bold)
				if (subTitle > 0) {
					Box(
						Modifier
							.padding(horizontal = 10.dp)
							.size(6.dp)
							.clip(CircleShape)
							.background(MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f))
					)
					Text(
						text = pluralStringResource(R.plurals.item_count, subTitle, subTitle),
						modifier = Modifier.alpha(0.7f)
					)
				}
			}
		},
		navigationIcon = {
			if (showBackButton) {
				IconButton(onClick = navigateUp) {
					Icon(Icons.Rounded.ArrowBackIosNew, stringResource(R.string.back))
				}
			}
		}
	)
}

@SuppressLint("RestrictedApi")
@Composable
fun NavBar(navController: NavHostController) {
	val navBackStackEntry = navController.currentBackStackEntryAsState()
	val currentRoute = navBackStackEntry.value?.destination?.route
	val backStack = navController.currentBackStack.collectAsState().value.map { it.destination.route }

	NavigationBar {
		NavBarItems.entries.forEach { item ->
			NavigationBarItem(
				selected = when (item) {
					NavBarItems.Products -> item.route == currentRoute
					NavBarItems.Checkout -> item.route == currentRoute || item.route in backStack
				},
				onClick = {
					navController.navigate(item.route) {
						popUpTo(navController.graph.findStartDestination().id) {
							saveState = item.route != currentRoute
						}
						launchSingleTop = true
						restoreState = true
					}
				},
				icon = { Icon(item.icon, null) },
				label = { Text(stringResource(item.title)) }
			)
		}
	}
}

private enum class NavBarItems(
	@StringRes val title: Int,
	val icon: ImageVector,
	val route: String
) {
	Products(
		title = R.string.products,
		icon = Icons.Rounded.Shop,
		route = AppRoute.ProductsScreen.route
	),
	Checkout(
		title = R.string.checkout,
		icon = Icons.Rounded.ShoppingCart,
		route = AppRoute.CheckoutScreen.route
	)
}