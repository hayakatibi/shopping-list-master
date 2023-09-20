package com.example.shoppinglist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import com.example.shoppinglist.databinding.ActivitySignupBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.FirebaseAuthKtxRegistrar
import com.google.firebase.ktx.Firebase

class Signup : AppCompatActivity() {

    private lateinit var binding: ActivitySignupBinding
    private lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        binding.btnSignup2.setOnClickListener {
            val email = binding.etEmail2.text.toString()
            val password = binding.etPassword2.text.toString()
            val confirmPassword = binding.etPassword3.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty() && confirmPassword.isNotEmpty()) {
                if (password == confirmPassword) {

                    firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
                        if (it.isSuccessful) {
                            val intent = Intent(this, Login::class.java)
                            startActivity(intent)
                        } else {
                            Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()

                        }
                    }
                } else {
                    Toast.makeText(this, "Password is not matching", Toast.LENGTH_SHORT).show()
                }
            }else {
                Toast.makeText(this,"Enter email and password", Toast.LENGTH_SHORT).show()
            }
        }

        //the text that take users to login in case already having an account
        /*binding.tvOr.setOnClickListener{
            startActivity(Intent(this, Login::class.java))
            finish()
        }*/

        //Back button navigation
        val btnBack = findViewById<ImageView>(R.id.arrow)

        btnBack.setOnClickListener {
            onBackPressed()
        }

    }
}