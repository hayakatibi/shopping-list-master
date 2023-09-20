package com.example.shoppinglist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import com.example.shoppinglist.databinding.ActivityLoginBinding
import com.example.shoppinglist.databinding.ActivitySignupBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth

class Login : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)


        firebaseAuth = FirebaseAuth.getInstance()

        binding.btnLogin2.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {

                firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
                    if (it.isSuccessful) {
                        val intent = Intent(this, HomePage::class.java)
                        Toast.makeText(this, "SignUn was Successful", Toast.LENGTH_SHORT).show()
                        startActivity(intent)
                    } else {
                        Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()
                    }
                }
            }else {
                Toast.makeText(this,"Please enter email and password", Toast.LENGTH_SHORT).show()
            }
        }


/*
        //navigation to signup activity
        var sign =findViewById<TextView>(R.id.tv_signup)
        sign.setOnClickListener {
            val intent = Intent(this,Signup::class.java)
            startActivity(intent)
        }

        //navigation to MenuFragment activity
        var menu =findViewById<Button>(R.id.btn_login2)
        menu.setOnClickListener {
            val intent = Intent(this,HomePage::class.java)
            startActivity(intent)
        }
*/

        //Back button navigation
        val btnBack = findViewById<ImageView>(R.id.arrow)

        btnBack.setOnClickListener{
                onBackPressed()
        }

    }
}