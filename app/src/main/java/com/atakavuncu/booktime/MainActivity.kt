package com.atakavuncu.booktime

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.atakavuncu.booktime.view.books.BooksFragment
import com.atakavuncu.booktime.view.menu.MenuFragment
import com.atakavuncu.booktime.view.profile.ProfileFragment
import com.atakavuncu.booktime.view.search.SearchFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.hide()
        val window = window
        window.statusBarColor = Color.parseColor("#D11E2F")

        val username = intent.getStringExtra("EXTRA_DATA")

        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottomNavigationView)
        bottomNavigationView.selectedItemId = R.id.profileButton
        bottomNavigationView.setOnItemSelectedListener { item ->
            when(item.itemId){
                R.id.menuButton -> {
                    val fragment = MenuFragment()
                    supportFragmentManager.beginTransaction().replace(R.id.fragmentContainerView, fragment).commit()
                    return@setOnItemSelectedListener true
                }
                R.id.booksButton -> {
                    val fragment = BooksFragment()
                    supportFragmentManager.beginTransaction().replace(R.id.fragmentContainerView, fragment).commit()
                    return@setOnItemSelectedListener true
                }
                R.id.searchButton -> {
                    val fragment = SearchFragment()
                    supportFragmentManager.beginTransaction().replace(R.id.fragmentContainerView, fragment).commit()
                    return@setOnItemSelectedListener true
                }
                R.id.profileButton -> {
                    val fragment = ProfileFragment()
                    val bundle = Bundle()
                    bundle.putString("username", username)
                    fragment.arguments = bundle
                    supportFragmentManager.beginTransaction().replace(R.id.fragmentContainerView, fragment).commit()
                    return@setOnItemSelectedListener true
                }
                else -> return@setOnItemSelectedListener false
            }
        }
    }
}