package com.roland.android.shopit.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AddShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.roland.android.shopit.R
import com.roland.android.shopit.data.Item
import com.roland.android.shopit.data.products
import com.roland.android.shopit.ui.TopBar
import com.roland.android.shopit.ui.screens.viewmodel.Actions
import com.roland.android.shopit.ui.theme.ShopitTheme
import kotlinx.coroutines.launch

@Composable
fun ProductsScreen(action: (Actions) -> Unit) {
	val snackbarHostState = remember { SnackbarHostState() }
	val layoutDirection = LocalLayoutDirection.current
	val scope = rememberCoroutineScope()
	val context = LocalContext.current

	Scaffold(
		topBar = { TopBar(stringResource(R.string.products)) },
		snackbarHost = { SnackbarHost(snackbarHostState) }
	) { paddingValues ->
		LazyVerticalGrid(
			columns = GridCells.Adaptive(150.dp),
			modifier = Modifier.padding(start = 10.dp),
			contentPadding = PaddingValues(
				start = paddingValues.calculateStartPadding(layoutDirection),
				top = paddingValues.calculateTopPadding(),
				end = paddingValues.calculateEndPadding(layoutDirection),
				bottom = 50.dp
			)
		) {
			items(products.size) { index ->
				val item = products[index]

				Product(
					item = item,
					addToCart = { scope.launch {
						action(Actions.AddToCart(item))
						snackbarHostState.showSnackbar(
							context.getString(R.string.item_added_to_cart, item.name)
						)
					} }
				)
			}
		}
	}
}

@Composable
private fun Product(
	item: Item,
	modifier: Modifier = Modifier,
	addToCart: () -> Unit
) {
	Column(
		modifier = modifier
			.padding(end = 10.dp, bottom = 10.dp)
			.clip(MaterialTheme.shapes.large)
			.background(Color.LightGray.copy(alpha = 0.2f)),
		horizontalAlignment = Alignment.CenterHorizontally
	) {
		Box(Modifier.padding(bottom = 20.dp)) {
			Image(
				painterResource(id = item.image),
				contentDescription = item.name,
				modifier = Modifier
					.fillMaxWidth()
					.height(150.dp),
				contentScale = ContentScale.Crop
			)
			IconButton(onClick = addToCart) {
				Icon(
					imageVector = Icons.Rounded.AddShoppingCart,
					contentDescription = stringResource(R.string.add_to_cart),
					tint = Color.Black
				)
			}
		}
		Text(
			text = item.name,
			fontSize = 18.sp,
			fontWeight = FontWeight.Medium
		)
		Text(
			text = "$${item.price}",
			modifier = Modifier
				.padding(bottom = 20.dp)
				.alpha(0.7f)
		)
	}
}

@Preview
@Composable
private fun ProductsScreenPreview() {
	ShopitTheme {
		ProductsScreen {}
	}
}