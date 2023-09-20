package com.example.shoppinglist

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import com.example.shoppinglist.R

class AccountFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_account, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //navigation to FamilyMembers activity
        val familyMembers = view.findViewById<LinearLayout>(R.id.familyMembers)
        familyMembers.setOnClickListener {
            val intent = Intent(requireActivity(), FamilyMembers::class.java)
            startActivity(intent)
        }
}}