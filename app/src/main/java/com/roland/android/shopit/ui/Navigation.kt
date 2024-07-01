package com.roland.android.shopit.ui

import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.roland.android.shopit.ui.screens.CheckoutScreen
import com.roland.android.shopit.ui.screens.OrderCompleteScreen
import com.roland.android.shopit.ui.screens.ProductsScreen
import com.roland.android.shopit.ui.screens.viewmodel.SharedViewModel

@Composable
fun AppRoute(
	sharedViewModel: SharedViewModel = viewModel(),
	navController: NavHostController = rememberNavController(),
) {
	Scaffold(
		bottomBar = { NavBar(navController) }
	) { paddingValues ->
		NavHost(
			navController = navController,
			startDestination = AppRoute.ProductsScreen.route,
			modifier = Modifier.padding(PaddingValues(bottom = paddingValues.calculateBottomPadding()))
		) {
			composable(AppRoute.ProductsScreen.route) {
				ProductsScreen(sharedViewModel::actions)
			}
			composable(AppRoute.CheckoutScreen.route) {
				CheckoutScreen(
					cartItems = sharedViewModel.cartItems,
					actions = sharedViewModel::actions,
					navigateToOrderCompleteScreen = {
						navController.navigate(AppRoute.OrderCompleteScreen.route)
					}
				)
			}
			composable(
				route = AppRoute.OrderCompleteScreen.route,
				enterTransition = { slideInHorizontally(tween(600)) { it } },
				popExitTransition = { slideOutHorizontally(tween(600)) { it } }
			) {
				OrderCompleteScreen { navController.navigateUp() }
			}
		}
	}
}

sealed class AppRoute(val route: String) {
	data object ProductsScreen : AppRoute("products_screen")
	data object CheckoutScreen : AppRoute("checkout_screen")
	data object OrderCompleteScreen : AppRoute("order_complete_screen")
}