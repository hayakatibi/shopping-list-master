package com.example.shoppinglist

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ItemsAdapter(
    private var mList: List<ItemsData>,
    private val listener: OnItemClickListener
) : RecyclerView.Adapter<ItemsAdapter.ItemViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val logo: ImageView = itemView.findViewById(R.id.logoIv)
        val itemsName: TextView = itemView.findViewById(R.id.itemsName)
        val brandsName: TextView = itemView.findViewById(R.id.brandsName)
        val price: TextView = itemView.findViewById(R.id.Price)

        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(position)
                }
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setFilteredList(mList: List<ItemsData>) {
        this.mList = mList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.logo.setImageResource(mList[position].imageResource)
        holder.itemsName.text = mList[position].item
        holder.brandsName.text = mList[position].brand
        holder.price.text = mList[position].price
    }

    override fun getItemCount(): Int {
        return mList.size
    }
}
