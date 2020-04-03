package com.gdk.arnold.footballapp.adapter

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.gdk.arnold.footballapp.R
import com.gdk.arnold.footballapp.data.model.matchlist.EventsItem
import com.gdk.arnold.footballapp.view.detailmatch.DetailActivity
import kotlinx.android.synthetic.main.list_match_view.view.*
import org.jetbrains.anko.startActivity
import java.text.SimpleDateFormat

class EventAdapter(
    val eventList: List<EventsItem>,
    val context: Context
) : RecyclerView.Adapter<EventAdapter.EventViewHolder>() {

    private var homeName: String? = ""
    private var homeScore: String? = ""
    private var awayName: String? = ""
    private var awayScore: String? = ""
    private var matchDate: String? = ""

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        return EventViewHolder(LayoutInflater.from(context).inflate(R.layout.list_match_view, parent, false))
    }

    override fun getItemCount(): Int = eventList.size


    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        holder.bind(eventList[position])
    }

    inner class EventViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(data: EventsItem) {
            val formatDate = SimpleDateFormat("dd-MM-yyyy")

            homeName = data.strHomeTeam
            awayName = data.strAwayTeam
            matchDate = formatDate.format(data.dateEvent).toString()

            if (data.intHomeScore != null && data.intAwayScore != null) {
                itemView.tvDate.setTextColor(context.getColor(R.color.last_match))
                homeScore = data.intHomeScore
                awayScore = data.intAwayScore

                if (data.intHomeScore > data.intAwayScore) {
                    itemView.tvHomeScore.setTextColor(context.getColor(R.color.win_match))
                    itemView.tvAwayScore.setTextColor(context.getColor(R.color.lose_match))
                } else if (data.intHomeScore < data.intAwayScore) {
                    itemView.tvHomeScore.setTextColor(context.getColor(R.color.lose_match))
                    itemView.tvAwayScore.setTextColor(context.getColor(R.color.win_match))
                }
            }

            itemView.tvDate.text = matchDate

            itemView.tvHomeName.text = homeName
            itemView.tvHomeScore.text = homeScore

            itemView.tvAwayName.text = awayName
            itemView.tvAwayScore.text = awayScore

            itemView.setOnClickListener {
                //itemView.context.startActivity<DetailActivity>("match" to data)
                val intent = Intent(itemView.context, DetailActivity::class.java)
                intent.putExtra("match", data)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                itemView.context.startActivity(intent)
            }
        }
    }

}


