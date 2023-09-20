package com.example.shoppinglist

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Build
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
import java.util.Calendar


class DynamicList:AppCompatActivity() {

    lateinit private var pendingIntent: PendingIntent
    lateinit private var alarmManager: AlarmManager

    lateinit private var firebaseAuth: FirebaseAuth

    lateinit var mrecyclerView: RecyclerView
    lateinit var staggeredGridLayoutManager : StaggeredGridLayoutManager

    lateinit var firebaseFirestore: FirebaseFirestore
    var firebaseUser: FirebaseUser? = null

    var noteAdapter: FirestoreRecyclerAdapter<firebasemodel2, ItemViewHolder>? = null

    lateinit private var titleList: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dynamic_list)

        titleList = findViewById(R.id.titleList)

        val data: Intent = intent
        intent.putExtra("title",data.getStringExtra("title"))
        intent.putExtra("listId",data.getStringExtra("listId"))
        titleList.setText(data.getStringExtra("title"))

        notification_channel()

        pendingIntent = PendingIntent.getBroadcast(this, 0, Intent(this,AlarmReceiver::class.java), PendingIntent.FLAG_IMMUTABLE)
        alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager

        firebaseAuth = FirebaseAuth.getInstance()
        firebaseUser = FirebaseAuth.getInstance().currentUser
        firebaseFirestore = FirebaseFirestore.getInstance()

        //Back button navigation
        val btnBack = findViewById<ImageView>(R.id.arrow)

        btnBack.setOnClickListener {

            val intent = Intent(this,MyLists::class.java)
            startActivity(intent)
        }

        val listId = intent.getStringExtra("listId")
        var createitems = findViewById<ImageButton>(R.id.addnew)

        createitems.setOnClickListener {
            set_notification_alarm()
            val intent = Intent(this,NewItemSheet::class.java)
            intent.putExtra("listId", listId)
            startActivity(intent)

        }

        val query: Query = firebaseFirestore.collection("lists")
            .document(firebaseUser!!.uid)
            .collection("myLists")
            .document(listId!!)
            .collection("items")
            .orderBy("item", Query.Direction.ASCENDING)

        val alluseritems: FirestoreRecyclerOptions<firebasemodel2> = FirestoreRecyclerOptions.Builder<firebasemodel2>()
            .setQuery(query, firebasemodel2::class.java)
            .build()

        noteAdapter = object : FirestoreRecyclerAdapter<firebasemodel2, ItemViewHolder>(alluseritems) {
            override fun onBindViewHolder(holder: ItemViewHolder, position: Int, model: firebasemodel2) {

                val popupbutton: TextView = holder.itemView.findViewById(R.id.btnMyLists1)
                holder.itemtitle.text = model.getItem()

                val docId: String = noteAdapter!!.snapshots.getSnapshot(position).id

                popupbutton.setOnClickListener { v ->
                    val popupMenu = PopupMenu(v.context, v)
                    popupMenu.gravity = Gravity.END
                    val editMenuItem = popupMenu.menu.add("Edit")
                    editMenuItem.setOnMenuItemClickListener {
                        val intent = Intent(v.context, EditItemActivity::class.java)
                        intent.putExtra("listId", listId)
                        intent.putExtra("itemId", docId)
                        intent.putExtra("item", model.getItem())
                        v.context.startActivity(intent)

                        false // Return true if the event is handled, false otherwise
                    }

                    val deleteMenuItem = popupMenu.menu.add("Delete")
                    deleteMenuItem.setOnMenuItemClickListener {


                        val documentReference: DocumentReference =
                            firebaseFirestore.collection("lists")
                                .document(firebaseUser!!.uid)
                                .collection("myLists")
                                .document(listId!!)
                                .collection("items")
                                .document(docId)
                        documentReference.delete().addOnSuccessListener {

                            cancel_notification_alarm()

                        }
                        false
                    }
                    popupMenu.show()
                }

                 var lastClickTime: Long = 0
                 val debounceInterval: Long = 500


                if (model.getIsChecked()!!) {
                    holder.checkboxImageView.setImageResource(R.drawable.checked_box)
                } else {
                    holder.checkboxImageView.setImageResource(R.drawable.uncheck_box)
                }

                holder.checkboxImageView.setOnClickListener {

                    val currentTime = System.currentTimeMillis()

                    if (currentTime - lastClickTime >= debounceInterval) {
                        lastClickTime = currentTime}

                    model.setIsChecked(!model.getIsChecked()!!)
                    val docId: String = noteAdapter!!.snapshots.getSnapshot(position).id
                    val documentReference: DocumentReference =
                        firebaseFirestore.collection("lists")
                            .document(firebaseUser!!.uid)
                            .collection("myLists")
                            .document(listId!!)
                            .collection("items")
                            .document(docId)
                    documentReference.update("isChecked", model.getIsChecked())

                    if (model.getIsChecked()!!) {
                        holder.checkboxImageView.setImageResource(R.drawable.checked_box)
                    } else {
                        holder.checkboxImageView.setImageResource(R.drawable.uncheck_box)
                    }
                }

                  if (model.getIsFavorite()!!) {
                      holder.starImageView.setImageResource(R.drawable.baseline_star_24)
                  } else {
                      holder.starImageView.setImageResource(R.drawable.star_outline)
                  }

                  holder.starImageView.setOnClickListener {

                      val currentTime = System.currentTimeMillis()

                      if (currentTime - lastClickTime >= debounceInterval) {
                          lastClickTime = currentTime}


                      model.setIsFavorite(!model.getIsFavorite()!!)
                      val docId: String = noteAdapter!!.snapshots.getSnapshot(position).id
                      val documentReference: DocumentReference =
                          firebaseFirestore.collection("lists")
                              .document(firebaseUser!!.uid)
                              .collection("myLists")
                              .document(listId!!)
                              .collection("items")
                              .document(docId)
                      documentReference.update("isFavorite", model.getIsFavorite())

                      if (model.getIsFavorite()!!) {
                          holder.starImageView.setImageResource(R.drawable.baseline_star_24)
                      } else {
                          holder.starImageView.setImageResource(R.drawable.star_outline)
                      }
                  }
            }

            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
                val view:View = LayoutInflater.from(parent.context).inflate(R.layout.task_item_cell,parent,false)
                return ItemViewHolder(view)
            }
        }
        mrecyclerView = findViewById(R.id.shoppingListRecyclerView)
        mrecyclerView.setHasFixedSize(true)
        staggeredGridLayoutManager = StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL)

        mrecyclerView.layoutManager = staggeredGridLayoutManager
        mrecyclerView.adapter = noteAdapter

    }

    public fun set_notification_alarm(){


        val calendar: Calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY,10)
        calendar.set(Calendar.MINUTE,0)

        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.timeInMillis,AlarmManager.INTERVAL_DAY,pendingIntent)


    }

    public fun cancel_notification_alarm(){
        alarmManager.cancel(pendingIntent)

    }

    private fun notification_channel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name: CharSequence = "Family Shopping List Reminder Channel"
            val description = "Channel to remind the items need to be bought"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel("Channel1", name, importance)
            channel.description = description
            val notificationManager = getSystemService(
                NotificationManager::class.java
            )
            notificationManager.createNotificationChannel(channel)

        }
    }

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemtitle: TextView= itemView.findViewById(R.id.btnMyLists1)
        val mitem: LinearLayout = itemView.findViewById(R.id.mitem)
        val checkboxImageView: ImageButton = itemView.findViewById(R.id.completeButton1)
        val starImageView: ImageButton = itemView.findViewById(R.id.comment1)

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
