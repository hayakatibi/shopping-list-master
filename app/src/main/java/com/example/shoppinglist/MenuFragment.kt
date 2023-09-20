package com.example.shoppinglist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.shoppinglist.R
import android.content.Intent
import android.widget.Button
import android.util.Log


class MenuFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_menu, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //navigation to MyLists activity
        val btnMyLists = view.findViewById<Button>(R.id.btnMyLists)
        btnMyLists.setOnClickListener {
            val intent = Intent(requireActivity(), MyLists::class.java)
            startActivity(intent)
        }

        //navigation to SharedLists activity
        val btnSharedLists = view.findViewById<Button>(R.id.btnSharedLists)
        btnSharedLists.setOnClickListener {
            val intent = Intent(requireActivity(), SharedLists::class.java)
            startActivity(intent)
        }

           }

}