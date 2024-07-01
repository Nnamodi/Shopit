package com.roland.android.shopit.data

import com.roland.android.shopit.R

data class Item(
	val id: Int,
	val name: String,
	val price: Int,
	val image: Int
)

val products = listOf(
	Item(
		id = 0,
		name = "Classy socks",
		price = 11,
		image = R.drawable.photo_1
	),
	Item(
		id = 1,
		name = "Weave key ring",
		price = 8,
		image = R.drawable.photo_2
	),
	Item(
		id = 2,
		name = "Fibre hat",
		price = 35,
		image = R.drawable.photo_3
	),
	Item(
		id = 3,
		name = "Kitchen bundle",
		price = 142,
		image = R.drawable.photo_4
	),
	Item(
		id = 4,
		name = "Office desk trio",
		price = 66,
		image = R.drawable.photo_5
	),
	Item(
		id = 5,
		name = "Copper wire rack",
		price = 17,
		image = R.drawable.photo_6
	),
	Item(
		id = 6,
		name = "Ceramic set",
		price = 34,
		image = R.drawable.photo_7
	),
	Item(
		id = 7,
		name = "Tea set",
		price = 53,
		image = R.drawable.photo_8
	),
	Item(
		id = 8,
		name = "Grey stone mug",
		price = 18,
		image = R.drawable.photo_9
	),
	Item(
		id = 9,
		name = "Gold sunglasses",
		price = 40,
		image = R.drawable.photo_10
	),
	Item(
		id = 10,
		name = "Chambray napkin",
		price = 27,
		image = R.drawable.photo_11
	),
	Item(
		id = 11,
		name = "Aesthetic planters",
		price = 16,
		image = R.drawable.photo_12
	),
	Item(
		id = 12,
		name = "Wooden table",
		price = 175,
		image = R.drawable.photo_13
	)
)
