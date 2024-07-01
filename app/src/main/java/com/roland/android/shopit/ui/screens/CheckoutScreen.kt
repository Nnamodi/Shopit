package com.roland.android.shopit.ui.screens

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.RemoveCircleOutline
import androidx.compose.material.icons.rounded.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.roland.android.shopit.R
import com.roland.android.shopit.data.Item
import com.roland.android.shopit.data.products
import com.roland.android.shopit.ui.TopBar
import com.roland.android.shopit.ui.screens.viewmodel.Actions
import com.roland.android.shopit.ui.theme.ShopitTheme

@Composable
fun CheckoutScreen(
	cartItems: List<Item>,
	actions: (Actions) -> Unit,
	navigateToOrderCompleteScreen: () -> Unit
) {
	val layoutDirection = LocalLayoutDirection.current

	Scaffold(
		topBar = { TopBar(stringResource(R.string.checkout), cartItems.size) }
	) { paddingValues ->
		Box(
			modifier = Modifier.fillMaxSize(),
			contentAlignment = Alignment.BottomCenter
		) {
			LazyColumn(
				modifier = Modifier.fillMaxSize(),
				contentPadding = PaddingValues(
					start = paddingValues.calculateStartPadding(layoutDirection),
					top = paddingValues.calculateTopPadding(),
					end = paddingValues.calculateEndPadding(layoutDirection),
					bottom = paddingValues.calculateBottomPadding() + 100.dp
				)
			) {
				items(cartItems.size) { index ->
					val item = cartItems[index]

					CartItem(
						item = item,
						modifier = Modifier.animateContentSize(),
						removeFromCart = { actions(Actions.RemoveFromCart(item)) }
					)
				}
				if (cartItems.isNotEmpty()) {
					item {
						OrderInfo(cartItems)
					}
				}
			}
			if (cartItems.isNotEmpty()) {
				Button(
					onClick = {
						navigateToOrderCompleteScreen()
						actions(Actions.Checkout)
					},
					modifier = Modifier
						.fillMaxWidth()
						.padding(20.dp, 10.dp),
					shape = RoundedCornerShape(6.dp)
				) {
					Text(stringResource(R.string.place_order))
				}
			}


			if (cartItems.isEmpty()) { EmptyCart(paddingValues) }
		}
	}
}

@Composable
private fun CartItem(
	item: Item,
	modifier: Modifier = Modifier,
	removeFromCart: () -> Unit
) {
	Column(modifier.fillMaxWidth()) {
		Row(verticalAlignment = Alignment.CenterVertically) {
			IconButton(
				onClick = removeFromCart,
				modifier = Modifier.padding(horizontal = 10.dp)
			) {
				Icon(Icons.Rounded.RemoveCircleOutline, stringResource(R.string.remove_from_cart))
			}
			Image(
				painter = painterResource(item.image),
				contentDescription = item.name,
				modifier = Modifier
					.padding(vertical = 20.dp)
					.clip(MaterialTheme.shapes.medium)
					.size(100.dp)
			)
			Column(Modifier.padding(horizontal = 20.dp)) {
				Text(text = item.name, fontSize = 16.sp)
				Text(text = "$${item.price}", modifier = Modifier.alpha(0.7f))
			}
		}
		HorizontalDivider(Modifier.padding(start = 40.dp))
	}
}

@Composable
private fun OrderInfo(
	cartItems: List<Item>,
	modifier: Modifier = Modifier
) {
	val subTotal = cartItems.sumOf { it.price }
	val shipping = ((5 / 100.0) * subTotal).toInt()
	val tax = ((3 / 100.0) * subTotal).toFloat()
	val total = subTotal + shipping + tax

	Column(modifier.fillMaxWidth()) {
		Detail(
			title = stringResource(R.string.total),
			value = "$$total",
			modifier = Modifier.padding(vertical = 30.dp),
			fontSize = 24.sp,
			fontWeight = FontWeight.Bold
		)
		Detail(title = stringResource(R.string.subtotal), value = "$$subTotal")
		Detail(title = stringResource(R.string.shipping), value = "$$shipping")
		Detail(title = stringResource(R.string.tax), value = "$$tax")
		HorizontalDivider(Modifier.padding(top = 30.dp))
	}
}

@Composable
private fun Detail(
	title: String,
	value: String,
	modifier: Modifier = Modifier,
	fontSize: TextUnit = LocalTextStyle.current.fontSize,
	fontWeight: FontWeight? = LocalTextStyle.current.fontWeight
) {
	Row(
		modifier = modifier.padding(start = 40.dp, end = 16.dp),
		verticalAlignment = Alignment.CenterVertically
	) {
		Text(text = title)
		Spacer(Modifier.weight(1f))
		Text(text = value, fontSize = fontSize, fontWeight = fontWeight)
	}
}

@Composable
private fun EmptyCart(
	paddingValues: PaddingValues,
	modifier: Modifier = Modifier,
) {
	Column(
		modifier = modifier
			.fillMaxSize()
			.padding(paddingValues)
			.verticalScroll(rememberScrollState())
		,
		verticalArrangement = Arrangement.Center,
		horizontalAlignment = Alignment.CenterHorizontally
	) {
		Icon(
			imageVector = Icons.Rounded.ShoppingCart,
			contentDescription = stringResource(R.string.cart_is_empty),
			modifier = Modifier
				.padding(bottom = 20.dp)
				.size(100.dp)
				.rotate(-45f)
				.alpha(0.7f)
		)
		Text(
			text = stringResource(R.string.cart_is_empty),
			fontSize = 22.sp,
			fontWeight = FontWeight.Medium
		)
	}
}

@Preview
@Composable
private fun CheckoutScreenPreview() {
	ShopitTheme {
		CheckoutScreen(
			cartItems = products.take(4),
			actions = {}
		) {}
	}
}