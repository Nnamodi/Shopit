package com.roland.android.shopit.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.roland.android.shopit.R
import com.roland.android.shopit.ui.TopBar
import com.roland.android.shopit.ui.theme.ShopitTheme

@Composable
fun OrderCompleteScreen(navigateUp: () -> Unit) {
	Scaffold(
		topBar = {
			TopBar(
				title = stringResource(R.string.order_complete),
				showBackButton = true,
				navigateUp = navigateUp
			)
		}
	) { paddingValues ->
		Column(
			modifier = Modifier
				.fillMaxSize()
				.padding(paddingValues),
			verticalArrangement = Arrangement.Center,
			horizontalAlignment = Alignment.CenterHorizontally
		) {
			Icon(
				imageVector = Icons.Rounded.CheckCircle,
				contentDescription = stringResource(R.string.order_complete),
				modifier = Modifier
					.padding(bottom = 20.dp)
					.size(100.dp),
				tint = Color.Green
			)
			Text(
				text = stringResource(R.string.order_successful),
				fontSize = 22.sp,
				fontWeight = FontWeight.Medium
			)
		}
	}
}

@Preview
@Composable
private fun OrderCompleteScreenPreview() {
	ShopitTheme {
		OrderCompleteScreen {}
	}
}