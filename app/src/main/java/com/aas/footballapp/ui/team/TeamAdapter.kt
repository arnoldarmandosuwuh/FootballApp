package com.aas.footballapp.ui.team

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aas.footballapp.R
import com.aas.footballapp.data.model.Team
import com.squareup.picasso.Picasso
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.list_item.view.*

class TeamAdapter (
    private val context: Context,
    private val teams: MutableList<Team>,
    private val onClickListener: (Team) -> Unit
) : RecyclerView.Adapter<TeamAdapter.ViewHolder>(){

    fun setTeam(team: List<Team>){
        teams.clear()
        teams.addAll(team)
        notifyDataSetChanged()
    }

    class ViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView),
            LayoutContainer{
        fun bind(context: Context, team: Team, onClickListener: (Team) -> Unit){
            itemView.tvName.text = team.strTeam
            Picasso.get().load(team.strTeamBadge).into(itemView.ivGambar)
            containerView.setOnClickListener { onClickListener(team) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(context).inflate(R.layout.list_item, parent, false)
    )

    override fun getItemCount(): Int = teams.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(context, teams[position], onClickListener)
    }
}