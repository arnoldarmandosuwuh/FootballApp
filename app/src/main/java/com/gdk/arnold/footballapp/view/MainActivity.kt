package com.gdk.arnold.footballapp.view

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.gdk.arnold.footballapp.R
import com.gdk.arnold.footballapp.R.id.*
import com.gdk.arnold.footballapp.view.favoritematch.FavMatchFragment
import com.gdk.arnold.footballapp.view.listmatch.LastMatchFragment
import com.gdk.arnold.footballapp.view.listmatch.NextMatchFragment
import kotlinx.android.synthetic.main.activity_home.*
import org.jetbrains.anko.AnkoLogger

class MainActivity : AppCompatActivity(), AnkoLogger {




    private fun loadLastFragment(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.main_container, LastMatchFragment(), LastMatchFragment::class.java.simpleName)
                .commit()
        }
    }
    private fun loadFavFragment(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.main_container, FavMatchFragment(), FavMatchFragment::class.java.simpleName)
                .commit()
        }
    }

    private fun loadNextFragment(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.main_container, NextMatchFragment(), NextMatchFragment::class.java.simpleName)
                .commit()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        navigation.setOnNavigationItemSelectedListener  { item ->
            when (item.itemId) {
                navigation_upcoming -> {
                    loadNextFragment(savedInstanceState)
                    supportActionBar?.title = "Upcoming Match"
                }
                navigation_last -> {
                    loadLastFragment(savedInstanceState)
                    supportActionBar?.title = "Last Match"
                }
                navigation_fav -> {
                    loadFavFragment(savedInstanceState)
                    supportActionBar?.title = "Favorite Match"
                }
            }
            true
        }
        navigation.selectedItemId = navigation_upcoming
    }

    }
