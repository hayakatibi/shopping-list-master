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


class EditNoteActivity : AppCompatActivity() {

    lateinit var data: Intent
    lateinit var editTitle: EditText
    lateinit var save: Button

    lateinit private var firebaseAuth: FirebaseAuth
    lateinit var firebaseFirestore: FirebaseFirestore
    var firebaseUser: FirebaseUser? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_note)


        editTitle = findViewById(R.id.createtitle)
        save = findViewById(R.id.saveEdit)
        data = intent

        firebaseFirestore = FirebaseFirestore.getInstance()
        firebaseAuth = FirebaseAuth.getInstance()
        firebaseUser = FirebaseAuth.getInstance().currentUser

        save.setOnClickListener{
            val newTitle : String = editTitle.text.toString()
            if(newTitle.isEmpty())
            {
                Toast.makeText(applicationContext, "Please enter the list name", Toast.LENGTH_SHORT).show()
            }
            else
            {
                val documentReference: DocumentReference = firebaseFirestore.collection("lists").document(firebaseUser!!.uid).collection("myLists").document(data.getStringExtra("listId")!!)
                val list: MutableMap<String, Any> = mutableMapOf()
                list.put("title",newTitle)

                documentReference.set(list).addOnSuccessListener {

                     val intent = Intent(this,MyLists::class.java)
                    startActivity(intent)
                }.addOnFailureListener{
                    Toast.makeText(applicationContext, "List is not updated", Toast.LENGTH_SHORT).show()
                }
            }
        }

        val listTitle:String=data.getStringExtra("title")!!
        editTitle.setText(listTitle)
    }
}