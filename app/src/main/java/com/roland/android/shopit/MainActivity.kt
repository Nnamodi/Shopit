package com.roland.android.shopit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.roland.android.shopit.ui.AppRoute
import com.roland.android.shopit.ui.theme.ShopitTheme

class MainActivity : ComponentActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		enableEdgeToEdge()
		setContent {
			ShopitTheme {
				AppRoute()
			}
		}
	}
}