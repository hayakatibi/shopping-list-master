package com.example.shoppinglist
data class ItemsData(
    val item: String,
    val brand: String,
    val imageResource: Int,
    val price: String,
    val stock: String,    // Add stock field
    val color: String,    // Add color field
    val shopName: String  // Add shop name field
)