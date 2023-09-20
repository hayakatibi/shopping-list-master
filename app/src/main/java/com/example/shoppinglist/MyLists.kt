package com.example.shoppinglist

import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.PopupMenu
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class MyLists:AppCompatActivity() {

    lateinit private var firebaseAuth: FirebaseAuth

    lateinit var mrecyclerView: RecyclerView
    lateinit var staggeredGridLayoutManager : StaggeredGridLayoutManager

    lateinit var firebaseFirestore: FirebaseFirestore
    var firebaseUser: FirebaseUser? = null

    var noteAdapter: FirestoreRecyclerAdapter<firebasemodel, ListViewHolder>? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.my_lists)
        firebaseAuth = FirebaseAuth.getInstance()
        firebaseUser = FirebaseAuth.getInstance().currentUser
        firebaseFirestore = FirebaseFirestore.getInstance()

        //Back button navigation
        val btnBack = findViewById<ImageView>(R.id.arrow)

        btnBack.setOnClickListener {

            val intent = Intent(this, HomePage::class.java)
            startActivity(intent)
        }


        var createlists = findViewById<ImageButton>(R.id.createlists)

        createlists.setOnClickListener {
            val intent = Intent(this,CreateList::class.java)
            startActivity(intent)
        }

        val query: Query =firebaseFirestore.collection("lists").document(firebaseUser!!.uid).collection("myLists").orderBy("title",Query.Direction.ASCENDING)

        val alluserlists: FirestoreRecyclerOptions<firebasemodel> = FirestoreRecyclerOptions.Builder<firebasemodel>()
            .setQuery(query, firebasemodel::class.java)
            .build()

        noteAdapter = object : FirestoreRecyclerAdapter<firebasemodel, ListViewHolder>(alluserlists) {
            override fun onBindViewHolder(holder: ListViewHolder, position: Int, model: firebasemodel) {


                val popupbutton: ImageView = holder.itemView.findViewById(R.id.menupopbutton)
                holder.listtitle.setText(model.getTitle())

                val docId: String = noteAdapter!!.snapshots.getSnapshot(position).id


                holder.itemView.setOnClickListener { v ->
                    val intent = Intent(v.context, DynamicList::class.java)
                    intent.putExtra("listId", docId)
                    intent.putExtra("title", model.getTitle())
                    v.context.startActivity(intent)
                }

                popupbutton.setOnClickListener { v ->
                    val popupMenu = PopupMenu(v.context, v)
                    popupMenu.gravity = Gravity.END
                    val editMenuItem = popupMenu.menu.add("Edit")
                    editMenuItem.setOnMenuItemClickListener {
                        val intent = Intent(v.context, EditNoteActivity::class.java)
                        intent.putExtra("listId", docId)
                        intent.putExtra("title", model.getTitle())
                        v.context.startActivity(intent)

                        false
                    }

                    val deleteMenuItem = popupMenu.menu.add("Delete")
                    deleteMenuItem.setOnMenuItemClickListener {

                        val itemsCollectionRef = firebaseFirestore.collection("lists")
                            .document(firebaseUser!!.uid)
                            .collection("myLists")
                            .document(docId)
                            .collection("items")

                        itemsCollectionRef.get().addOnSuccessListener { itemsQuerySnapshot ->
                            val batch = firebaseFirestore.batch()



                            for (itemDocumentSnapshot in itemsQuerySnapshot.documents) {
                                batch.delete(itemDocumentSnapshot.reference)


                            }

                            // Commit the batch to delete items
                            batch.commit().addOnSuccessListener {
                                // Once items are deleted, delete the main list
                                val documentReference: DocumentReference =
                                    firebaseFirestore.collection("lists").document(firebaseUser!!.uid)
                                        .collection("myLists").document(docId)
                                documentReference.delete().addOnSuccessListener {
                                    // Main list and items deleted successfully
                                }
                            }
                        }
                        false
                    }
                    popupMenu.show()
                }
            }

            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
                val view:View = LayoutInflater.from(parent.context).inflate(R.layout.list_cell,parent,false)
                return ListViewHolder(view)
            }
        }
        mrecyclerView = findViewById(R.id.shoppingListRecyclerView)
        mrecyclerView.setHasFixedSize(true)
        staggeredGridLayoutManager = StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL)

        mrecyclerView.layoutManager = staggeredGridLayoutManager
        mrecyclerView.adapter = noteAdapter


    }

    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val listtitle: TextView= itemView.findViewById(R.id.btnMyLists1)
        val mnote: LinearLayout = itemView.findViewById(R.id.note)

    }

    override fun onStart() {
        super.onStart()
        noteAdapter!!.startListening()
    }

    override fun onStop() {
        super.onStop()
        if(noteAdapter!=null)
        {
            noteAdapter!!.stopListening()
        }
    }
}


