package com.roland.android.shopit.ui.screens.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.roland.android.shopit.data.Item
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SharedViewModel : ViewModel() {

	var cartItems by mutableStateOf<List<Item>>(emptyList()); private set

	fun actions(action: Actions) {
		when (action) {
			is Actions.AddToCart -> addToCart(action.item)
			is Actions.RemoveFromCart -> removeFromCart(action.item)
			Actions.Checkout -> emptyCart()
		}
	}

	private fun addToCart(item: Item) {
		cartItems = cartItems + item
	}

	private fun removeFromCart(item: Item) {
		cartItems = cartItems - item
	}

	private fun emptyCart() {
		viewModelScope.launch {
			delay(700)
			cartItems = emptyList()
		}
	}

}

sealed class Actions {

	data class AddToCart(val item: Item) : Actions()

	data class RemoveFromCart(val item: Item) : Actions()

	data object Checkout : Actions()

}