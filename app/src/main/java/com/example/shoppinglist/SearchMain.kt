package com.example.shoppinglist

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.*

class SearchMain : AppCompatActivity(), ItemsAdapter.OnItemClickListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var searchView: SearchView
    private var mList = ArrayList<ItemsData>()
    private lateinit var adapter: ItemsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_search)

        recyclerView = findViewById(R.id.recyclerView)
        searchView = findViewById(R.id.searchView)

        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)
        addDataToList()
        adapter = ItemsAdapter(mList, this)
        recyclerView.adapter = adapter

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterList(newText)
                return true
            }

        })
    }
    override fun onItemClick(position: Int) {
        val selectedItem = mList[position]
        val intent = Intent(this, searchItemDetails::class.java).apply {
            putExtra("Name", selectedItem.item)
            putExtra("Brand", selectedItem.brand)
            putExtra("Price", selectedItem.price)
            putExtra("Stock", selectedItem.stock)   // Add stock information
            putExtra("Color", selectedItem.color)   // Add color information
            putExtra("ShopName", selectedItem.shopName)   // Add shop name information
            putExtra("imageResource", selectedItem.imageResource)
        // Add more extras for other details
        }
        startActivity(intent)
    }

    private fun filterList(query: String?) {
        if (!query.isNullOrBlank()) {
            val trimmedQuery = query.trim().lowercase(Locale.ROOT)
            val filteredList = ArrayList<ItemsData>()

            for (item in mList) {
                val lowercaseItemName = item.item.lowercase(Locale.ROOT)
                val lowercaseBrandName = item.brand.lowercase(Locale.ROOT)

                if (lowercaseItemName.contains(trimmedQuery) || lowercaseBrandName.contains(trimmedQuery)) {
                    filteredList.add(item)
                }
            }

            if (filteredList.isEmpty()) {
                Toast.makeText(this, "No Data found", Toast.LENGTH_SHORT).show()
            } else {
                adapter.setFilteredList(filteredList)
            }
        } else {
            adapter.setFilteredList(mList)
        }
    }

    private fun addDataToList() {
        mList.add(ItemsData("Pen","xxx", R.drawable.pen, "5$", "In Stock", "Blue", "Stationary Shop"))
        mList.add(ItemsData("bracelet","Harper", R.drawable.bracelet, "20$", "In Stock", "Silver", "Fashion Store"))
        mList.add(ItemsData("shoes","Nike", R.drawable.shoes, "50$", "In Stock", "Black", "Sportswear Store"))
        mList.add(ItemsData("mouse","Philips", R.drawable.mouse, "40$", "Out of Stock", "White", "Electronics Store"))
    }

}