package com.example.shoppinglist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Splash screen
        Thread.sleep(3000)
        installSplashScreen()
        setContentView(R.layout.activity_main)

        //navigation to login activity
        var btn_login =findViewById<Button>(R.id.btn_login)
        btn_login.setOnClickListener {
            val intent = Intent(this,Login::class.java)
            startActivity(intent)
        }
        //navigation to signup activity
        var btn_signup =findViewById<Button>(R.id.btn_signup)
        btn_signup.setOnClickListener {
            val intent = Intent(this,Signup::class.java)
            startActivity(intent)
        }

        //navigation to adminpage activity (Only to see the activity on emulator)
        var btn_admin =findViewById<Button>(R.id.btn_admin)
        btn_admin.setOnClickListener {
            val intent = Intent(this,AdminPage::class.java)
            startActivity(intent)
        }






    }
}