package com.gdk.arnold.footballapp.view.favoritematch


import android.content.Context
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
import com.gdk.arnold.footballapp.adapter.FavoriteMatchAdapter
import com.gdk.arnold.footballapp.db.Favorite
import com.gdk.arnold.footballapp.db.database
import com.gdk.arnold.footballapp.util.invisible
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select
import org.jetbrains.anko.support.v4.onRefresh


class FavMatchFragment : Fragment(), AnkoLogger {

    private var favorites: MutableList<Favorite> = mutableListOf()
    private lateinit var rvEvent: RecyclerView
    private lateinit var pbMatch: ProgressBar
    private lateinit var adapter: FavoriteMatchAdapter
    private lateinit var swipeRefresh: SwipeRefreshLayout

    private fun showFavorite() {
        favorites.clear()
        context?.database?.use {
            swipeRefresh.isRefreshing = false
            val result = select(Favorite.TABLE_FAVORITE)
            val favorite = result.parseList(classParser<Favorite>())
            favorites.addAll(favorite)
            adapter.notifyDataSetChanged()
            pbMatch.invisible()
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val inflater = inflater.inflate(R.layout.activity_main, container, false)
        rvEvent = inflater.findViewById(R.id.rvEvent)
        pbMatch = inflater.findViewById(R.id.pbMatch)
        swipeRefresh = inflater.findViewById(R.id.swipeRefresh)
        adapter = FavoriteMatchAdapter(favorites, context as Context)
        rvEvent.layoutManager = LinearLayoutManager(context)
        rvEvent.adapter = adapter
        swipeRefresh.onRefresh {
            showFavorite()
        }
        // Inflate the layout for this fragment
        return inflater
    }

    override fun onResume() {
        super.onResume()
        showFavorite()
    }


}
