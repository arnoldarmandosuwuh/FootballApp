package com.gdk.arnold.footballapp.view

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.gdk.arnold.footballapp.R
import com.gdk.arnold.footballapp.adapter.EventAdapter
import com.gdk.arnold.footballapp.data.ApiRepository
import com.gdk.arnold.footballapp.data.model.matchlist.EventsItem
import com.gdk.arnold.footballapp.util.invisible
import com.gdk.arnold.footballapp.util.visible
import com.gdk.arnold.footballapp.view.listmatch.ListMatchPresenter
import com.gdk.arnold.footballapp.view.listmatch.ListMatchView
import kotlinx.android.synthetic.main.activity_main.*
import com.google.gson.Gson
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

class MainActivity : AppCompatActivity(), ListMatchView, AnkoLogger {

    lateinit var ePresenter: ListMatchPresenter
    private var eventLists: MutableList<EventsItem> = mutableListOf()


    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_upcoming -> {
                getDataNextMatch()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_last -> {
                getDataLastMatch()
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    fun getDataNextMatch() {
        ePresenter.getNextMatch("4328")
        supportActionBar?.title = "Upcoming Match"
    }

    fun getDataLastMatch() {
        ePresenter.getLastMatch("4328")
        supportActionBar?.title = "Last Match"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        ePresenter = ListMatchPresenter(this, ApiRepository(), Gson())
        getDataNextMatch()

    }

    override fun hideLoading() {
        pbMatch.invisible()
        rvEvent.visible()
    }

    override fun showLoading() {
        pbMatch.visible()
        rvEvent.invisible()
    }

    override fun displayFootballMatch(matchList: List<EventsItem>) {
        info(matchList.toString())
        eventLists.clear()
        eventLists.addAll(matchList)
        val layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)
        rvEvent.layoutManager = layoutManager
        rvEvent.adapter = EventAdapter(matchList, applicationContext)
    }
}
