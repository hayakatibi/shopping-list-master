package com.example.shoppinglist

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.*

class SearchFragment : Fragment() , ItemsAdapter.OnItemClickListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var searchView: SearchView
    private var mList = ArrayList<ItemsData>()
    private lateinit var adapter: ItemsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_search, container, false)

        recyclerView = rootView.findViewById(R.id.recyclerView)
        searchView = rootView.findViewById(R.id.searchView)

        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        addDataToList()
        adapter = ItemsAdapter(mList,this)
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

        return rootView
    }
    override fun onItemClick(position: Int) {
        val selectedItem = mList[position]
        val intent = Intent(requireContext(), searchItemDetails::class.java).apply {
            putExtra("Name", selectedItem.item)
            putExtra("Brand", selectedItem.brand)
            putExtra("Price", selectedItem.price)
            putExtra("Stock", selectedItem.stock)
            putExtra("Color", selectedItem.color)
            putExtra("ShopName", selectedItem.shopName)
            putExtra("imageResource", selectedItem.imageResource)

            // Add more extras for other details
        }
        startActivity(intent)
    }


    private fun filterList(query: String?) {
        if (!query.isNullOrBlank()) {
            val filteredList = ArrayList<ItemsData>()
            for (item in mList) {
                if (item.item.lowercase(Locale.ROOT).contains(query.lowercase(Locale.ROOT))) {
                    filteredList.add(item)
                }
            }

            if (filteredList.isEmpty()) {
                Toast.makeText(requireContext(), "No Data found", Toast.LENGTH_SHORT).show()
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
