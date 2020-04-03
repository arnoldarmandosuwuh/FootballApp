package com.aas.footballapp.ui.league


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.aas.footballapp.R
import com.aas.footballapp.data.model.League
import com.aas.footballapp.ui.detail.league.DetailLeagueActivity

/**
 * A simple [Fragment] subclass.
 */
class LeagueFragment : Fragment() {

    private var listLeague: MutableList<League> = mutableListOf()
    private lateinit var adapter: LeagueAdapter
    private lateinit var rvLeague: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_league, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        rvLeague = view.findViewById(R.id.rvLeague)
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        initData()

        rvLeague.addItemDecoration(
            DividerItemDecoration(
                rvLeague.context, DividerItemDecoration.VERTICAL
            )
        )
        rvLeague.layoutManager = LinearLayoutManager(requireContext())
        adapter = LeagueAdapter(requireContext(), listLeague){
            val intent = Intent(context, DetailLeagueActivity::class.java)
            intent.putExtra("id", it.id)
            intent.putExtra("name", it.name)
            startActivity(intent)
        }

        rvLeague.adapter = adapter


        super.onActivityCreated(savedInstanceState)
    }

    private fun initData(){
        val id = resources.getStringArray(R.array.league_id)
        val name = resources.getStringArray(R.array.league_name)
        val image = resources.obtainTypedArray(R.array.league_badge)
        listLeague.clear()
        for(i in name.indices){
            listLeague.add(League(id[i],name[i],image.getResourceId(i, 0)))
        }
        image.recycle()
    }


}
