package com.example.shoppinglist

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class FamilyMembers : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.family_members)

        //Back button navigation
        val btnBack = findViewById<ImageView>(R.id.arrow)

        btnBack.setOnClickListener{
            onBackPressed()
        }
    }
}