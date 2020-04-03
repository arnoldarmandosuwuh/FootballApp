package com.aas.footballapp.ui.table

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aas.footballapp.R
import com.aas.footballapp.data.model.Table
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.list_table.view.*

class TableAdapter(
    private val context: Context,
    private val tables: MutableList<Table>,
    private val onClickListener: (Table) -> Unit
) : RecyclerView.Adapter<TableAdapter.ViewHolder>() {

    fun setTable(table: List<Table>) {
        tables.clear()
        tables.addAll(table)
        notifyDataSetChanged()
    }

    class ViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView),
        LayoutContainer {
        fun bind(table: Table, onClickListener: (Table) -> Unit) {
            itemView.tv_team_table.text = table.name
            itemView.tv_played_table.text = table.played
            itemView.tv_win_table.text = table.win
            itemView.tv_draw_table.text = table.draw
            itemView.tv_loss_table.text = table.loss
            itemView.tv_point_table.text = table.total
            itemView.tv_gf_table.text =  table.goalsfor
            itemView.tv_ga_table.text =  table.goalsagainst
            itemView.tv_gd_table.text =  table.goalsdifference
            containerView.setOnClickListener { onClickListener(table) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(context).inflate(R.layout.list_table, parent, false)
    )

    override fun getItemCount(): Int = tables.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(tables[position], onClickListener)
    }
}