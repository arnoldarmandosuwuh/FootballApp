package com.aas.footballapp.ui

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aas.footballapp.R
import com.aas.footballapp.ui.detail.league.DetailLeagueActivity
import com.aas.footballapp.ui.league.LeagueAdapter
import com.aas.footballapp.data.model.League
import com.aas.footballapp.ui.favorite.FavoriteFragment
import com.aas.footballapp.ui.league.LeagueFragment
import com.aas.footballapp.ui.search.SearchActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.startActivity

class MainActivity : AppCompatActivity() {

    companion object {
        const val INSTANCE = "instance"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        }
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        nav_view.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    val fragment = LeagueFragment()
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.flContent, fragment, LeagueFragment::class.java.simpleName)
                        .commit()

                    supportActionBar?.setTitle("League")
                }
                R.id.nav_fav -> {
                    val fragment = FavoriteFragment()
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.flContent, fragment, FavoriteFragment::class.java.simpleName)
                        .commit()

                    supportActionBar?.setTitle("Favorite")
                }
            }
            true
        }
        if (savedInstanceState == null) {
            nav_view.selectedItemId = R.id.nav_home
        } else {
            when (savedInstanceState.getString(INSTANCE)) {
                LeagueFragment::class.java.simpleName -> {
                    nav_view.selectedItemId = R.id.nav_home
                }
                FavoriteFragment::class.java.simpleName -> {
                    nav_view.selectedItemId = R.id.nav_fav
                }
            }
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
       return when (item.itemId){
            R.id.action_search -> {
                startActivity(intentFor<SearchActivity>())
                true
            }
           else -> {
               super.onOptionsItemSelected(item)
           }
        }
    }
}
