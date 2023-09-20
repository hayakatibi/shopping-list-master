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

class NewItemSheet : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_new_item_sheet)


        val addItem = findViewById<Button>(R.id.savelist)
        val item = findViewById<EditText>(R.id.createtitle)

        val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
        val firebaseFirestore: FirebaseFirestore = FirebaseFirestore.getInstance()
        val firebaseUser: FirebaseUser? = FirebaseAuth.getInstance().currentUser

        addItem.setOnClickListener{

            val item : String = item.getText().toString()

            if(item.isEmpty())
            {
                Toast.makeText(applicationContext, "Please enter the item name", Toast.LENGTH_SHORT).show()
            }
            else
            {
                val listId = intent.getStringExtra("listId")

                val documentReference: DocumentReference =
                    firebaseFirestore.collection("lists")
                        .document(firebaseUser!!.uid)
                        .collection("myLists")
                        .document(listId!!)
                        .collection("items")
                        .document()

                val list: MutableMap<String, Any> = mutableMapOf()
                list.put("item",item)
                list["isChecked"] = false
                list["isFavorite"] = false


                documentReference.set(list).addOnSuccessListener {

                    val intent = Intent(this,MyLists::class.java)
                   startActivity(intent)

                }.addOnFailureListener{
                    Toast.makeText(applicationContext, "item is not created", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}