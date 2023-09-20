package com.example.shoppinglist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import java.util.UUID

class CreateList : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_list)

        val savelist = findViewById<Button>(R.id.savelist)
        val createtitle = findViewById<EditText>(R.id.createlist)

        val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
        val firebaseFirestore: FirebaseFirestore = FirebaseFirestore.getInstance()
        val firebaseUser: FirebaseUser? = FirebaseAuth.getInstance().currentUser

        savelist.setOnClickListener {

            val title: String = createtitle.getText().toString()

            if (title.isEmpty()) {
                Toast.makeText(applicationContext, "Please enter the list name", Toast.LENGTH_SHORT)
                    .show()
            } else {
                val listId = UUID.randomUUID().toString()
                val documentReference: DocumentReference =
                    firebaseFirestore.collection("lists").document(firebaseUser!!.uid)
                        .collection("myLists").document(listId)

                val list: MutableMap<String, Any> = mutableMapOf()
                list.put("title", title)

                documentReference.set(list).addOnSuccessListener {

                    val intent = Intent(this, DynamicList::class.java)
                    intent.putExtra("listId", listId)
                    intent.putExtra("title", title)
                    startActivity(intent)
                }.addOnFailureListener {
                    Toast.makeText(applicationContext, "List is not created", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }
}