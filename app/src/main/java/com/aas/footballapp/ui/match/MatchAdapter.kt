package com.aas.footballapp.ui.match

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aas.footballapp.R
import com.aas.footballapp.data.model.Event
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.list_event.view.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class MatchAdapter(
    private val context: Context,
    private val events: MutableList<Event>,
    private val onCLickListener: (Event) -> Unit
) : RecyclerView.Adapter<MatchAdapter.ViewHolder>(){

    fun setEvent(event: List<Event>){
        events.clear()
        events.addAll(event)
        notifyDataSetChanged()
    }

    class ViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView),
            LayoutContainer{
        fun bindData(context: Context, event: Event, onCLickListener: (Event) -> Unit){
            val date = LocalDate.parse(event.dateEvent)
            val formatter = DateTimeFormatter.ofPattern("EEEE, dd MMMM yyyy")
            val formattedDate = date.format(formatter)

            itemView.tv_event_name.text = event.strEvent
            itemView.tv_date.text = formattedDate
            itemView.tv_home.text = event.strHomeTeam
            if (event.intHomeScore != null){
                itemView.tv_home_score.text = event.intHomeScore
            } else {
                itemView.tv_home_score.text = "-"
            }
            itemView.tv_away.text = event.strAwayTeam
            if (event.intAwayScore != null){
                itemView.tv_away_score.text = event.intAwayScore
            } else {
                itemView.tv_away_score.text = "-"
            }
            containerView.setOnClickListener { onCLickListener(event) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(context).inflate(R.layout.list_event, parent, false)
    )

    override fun getItemCount(): Int = events.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindData(context, events[position], onCLickListener)
    }
}