package com.example.shoppinglist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.shoppinglist.AccountFragment
import com.example.shoppinglist.MenuFragment
import com.example.shoppinglist.NotificationFragment
import com.example.shoppinglist.SearchFragment
import com.example.shoppinglist.SettingFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomePage : AppCompatActivity() {
    val tag = "HomePage"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_page)

        val accountFragment = AccountFragment()
        val searchFragment = SearchFragment()
        val notificationFragment = NotificationFragment()
        val menuFragment = MenuFragment()
        val settingFragment = SettingFragment()

        setCurrentFragment(menuFragment)



        findViewById<BottomNavigationView>(R.id.bottom_navbar)
            .setOnNavigationItemSelectedListener {
                when (it.itemId) {
                    R.id.nav_account -> {
                        setCurrentFragment(accountFragment)
                        Log.i(tag, "Account Selected")
                        badgeClear(R.id.nav_account)
                    }

                    R.id.nav_notification -> {
                        setCurrentFragment(notificationFragment)
                        Log.i(tag, "Notification Selected")
                        badgeClear(R.id.nav_notification)
                    }

                    R.id.nav_menu -> {
                        setCurrentFragment(menuFragment)
                        Log.i(tag, "Menu Selected")
                        badgeClear(R.id.nav_menu)

                    }

                    R.id.nav_search -> {
                        setCurrentFragment(searchFragment)
                        Log.i(tag, "search Selected")
                        badgeClear(R.id.nav_search)
                    }

                    R.id.nav_setting -> {
                        setCurrentFragment(settingFragment)
                        Log.i(tag, "setting Selected")
                        badgeClear(R.id.nav_setting)
                    }
                }
                true
            }
    }


    private fun setCurrentFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.f1_wrapper, fragment)
            commit()
        }

    private fun badgeClear(id: Int) {
        val badgeDrawable = findViewById<BottomNavigationView>(R.id.bottom_navbar).getBadge(id)
        if (badgeDrawable != null) {
            badgeDrawable.isVisible = false
            badgeDrawable.clearNumber()
        }
    }


}

