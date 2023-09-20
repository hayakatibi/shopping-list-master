package com.example.shoppinglist

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
class SharedLists:AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.shared_lists)


        //Back button navigation
        val btnBack = findViewById<ImageView>(R.id.arrow)

        btnBack.setOnClickListener {
            onBackPressed()
        }
    }
}