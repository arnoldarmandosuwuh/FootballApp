package com.gdk.arnold.footballapp.adapter

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.gdk.arnold.footballapp.R
import com.gdk.arnold.footballapp.db.Favorite
import com.gdk.arnold.footballapp.view.detailmatch.DetailActivity
import com.gdk.arnold.footballapp.view.favoritematch.FavMatchDetailActivity
import kotlinx.android.synthetic.main.list_match_view.view.*
import java.text.SimpleDateFormat
import java.util.*

class FavoriteMatchAdapter(
    private val favorite: List<Favorite>,
    private val context: Context
) : RecyclerView.Adapter<FavoriteMatchAdapter.FavoriteViewHolder>() {

    private var homeName: String? = ""
    private var homeScore: String? = ""
    private var awayName: String? = ""
    private var awayScore: String? = ""
    private var matchDate: String? = ""

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        return FavoriteViewHolder(LayoutInflater.from(context).inflate(R.layout.list_match_view, parent, false))
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.bindItem(favorite[position])
    }

    override fun getItemCount(): Int = favorite.size

    inner class FavoriteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItem(favorite: Favorite) {

            homeName = favorite.strHomeTeam
            awayName = favorite.strAwayTeam
            matchDate = favorite.dateEvent


            if (favorite.matchType == "Last Match") {
                itemView.tvDate.setTextColor(context.getColor(R.color.last_match))
                homeScore = favorite.intHomeScore
                awayScore = favorite.intAwayScore
            } else {
                itemView.tvDate.setTextColor(context.getColor(R.color.next_match))
                homeScore = ""
                awayScore = ""
            }
            itemView.tvDate.text = matchDate

            itemView.tvHomeName.text = homeName
            itemView.tvHomeScore.text = homeScore

            itemView.tvAwayName.text = awayName
            itemView.tvAwayScore.text = awayScore

            itemView.setOnClickListener {
                val intent = Intent(itemView.context, FavMatchDetailActivity::class.java)
                intent.putExtra("match", favorite)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                itemView.context.startActivity(intent)
            }

        }

    }


}

