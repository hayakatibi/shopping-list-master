package com.example.shoppinglist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView

class searchItemDetails : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.search_item_details)

        val imageView = findViewById<ImageView>(R.id.imageView2)
        val imageResource = intent.getIntExtra("imageResource", R.drawable.img)
// Replace with your actual key
        imageView.setImageResource(imageResource)

        val itemName = intent.getStringExtra("Name")
        val brandName = intent.getStringExtra("Brand")
        val price = intent.getStringExtra("Price")
        val stock = intent.getStringExtra("Stock")    // Retrieve stock information
        val color = intent.getStringExtra("Color")    // Retrieve color information
        val shopName = intent.getStringExtra("ShopName")    // Retrieve shop name information

        // Use the retrieved information to populate your UI components
        val nameTextView = findViewById<TextView>(R.id.Name)
        val brandTextView = findViewById<TextView>(R.id.Brand)
        val priceTextView = findViewById<TextView>(R.id.Price)
        val stockTextView = findViewById<TextView>(R.id.Stock)
        val colorTextView = findViewById<TextView>(R.id.Color)
        val shopNameTextView = findViewById<TextView>(R.id.ShopName)

        nameTextView.text = itemName
        brandTextView.text = brandName
        priceTextView.text = price
        stockTextView.text = stock
        colorTextView.text = color
        shopNameTextView.text = shopName

        //Back button navigation
        val btnBack = findViewById<ImageView>(R.id.arrow)

        btnBack.setOnClickListener {
            onBackPressed()
        }
    }

}
