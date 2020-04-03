package com.gdk.arnold.footballapp.view.listmatch

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import com.gdk.arnold.footballapp.R
import com.gdk.arnold.footballapp.adapter.EventAdapter
import com.gdk.arnold.footballapp.data.ApiRepository
import com.gdk.arnold.footballapp.data.model.matchlist.EventsItem
import com.gdk.arnold.footballapp.util.invisible
import com.gdk.arnold.footballapp.util.visible
import com.google.gson.Gson
import org.jetbrains.anko.support.v4.onRefresh

class NextMatchFragment : Fragment(), ListMatchView {

    lateinit var ePresenter: ListMatchPresenter
    private var eventLists: MutableList<EventsItem> = mutableListOf()
    lateinit var pbMatch: ProgressBar
    lateinit var rvEvent: RecyclerView
    lateinit var swipeRefresh: SwipeRefreshLayout


    override fun hideLoading() {
        pbMatch.invisible()
        rvEvent.visible()
    }

    override fun showLoading() {
        pbMatch.visible()
        rvEvent.invisible()
    }

    fun getData(){
        swipeRefresh.isRefreshing = false
        ePresenter.getNextMatch("4328")
    }

    override fun displayFootballMatch(matchList: List<EventsItem>) {
        eventLists.clear()
        eventLists.addAll(matchList)
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        rvEvent.layoutManager = layoutManager
        rvEvent.adapter = EventAdapter(matchList, context!!)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val inflater = inflater.inflate(R.layout.activity_main, container, false)
        pbMatch = inflater.findViewById(R.id.pbMatch)
        rvEvent = inflater.findViewById(R.id.rvEvent)
        swipeRefresh = inflater.findViewById(R.id.swipeRefresh)
        ePresenter = ListMatchPresenter(this, ApiRepository(), Gson())
        getData()
        swipeRefresh.onRefresh {
            getData()
        }
        // Inflate the layout for this fragment
        return inflater
    }


}
