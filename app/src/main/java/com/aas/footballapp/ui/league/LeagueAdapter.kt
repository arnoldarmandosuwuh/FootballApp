package com.aas.footballapp.ui.league

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.aas.footballapp.R
import com.aas.footballapp.data.model.League
import com.squareup.picasso.Picasso

class LeagueAdapter(
    private val context: Context,
    private val listLeague: List<League>,
    private val listener: (League) -> Unit
) : RecyclerView.Adapter<LeagueAdapter.ViewHolder>(){

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        private val name = view.findViewById<TextView>(R.id.tvName)
        private val image = view.findViewById<ImageView>(R.id.ivGambar)

        fun bind(league: League, listener: (League) -> Unit){
            name.text = league.name

            Picasso.get().load(league.image).fit().into(image)
            itemView.setOnClickListener { listener(league) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(LayoutInflater.from(context).inflate(R.layout.list_item, parent, false))


    override fun getItemCount(): Int = listLeague.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listLeague[position], listener)
    }
}