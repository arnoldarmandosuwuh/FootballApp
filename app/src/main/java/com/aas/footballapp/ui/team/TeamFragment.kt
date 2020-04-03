package com.aas.footballapp.ui.team


import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.aas.footballapp.R
import com.aas.footballapp.data.remote.api.ApiRepository
import com.aas.footballapp.ui.detail.league.DetailLeagueActivity
import com.aas.footballapp.ui.detail.team.DetailTeamActivity
import com.aas.footballapp.data.model.Team
import com.aas.footballapp.util.invisible
import com.aas.footballapp.util.visible
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_team.*

/**
 * A simple [Fragment] subclass.
 */
class TeamFragment : Fragment(), TeamInterface {
    private lateinit var teamPresenter: TeamPresenter
    private lateinit var teamAdapter: TeamAdapter
    private lateinit var viewModel: TeamViewModel
    private lateinit var rvTeam: RecyclerView

    private var teams = mutableListOf<Team>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_team, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        rvTeam = view.findViewById(R.id.rv_team)
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)

        viewModel = ViewModelProviders.of(this).get(TeamViewModel::class.java)
        viewModel.getTeam().observe(viewLifecycleOwner, getTeam)

        val sharedPreferences = activity!!.getPreferences(MODE_PRIVATE)
        val idLeague = sharedPreferences.getString("idLeague", "")

        val service = ApiRepository()
        val gson = Gson()
        teamPresenter = TeamPresenter(this, service, gson)

        rvTeam.addItemDecoration(
            DividerItemDecoration(
                rvTeam.context,
                DividerItemDecoration.VERTICAL
            )
        )
        rvTeam.layoutManager = LinearLayoutManager(requireContext())
        teamAdapter = TeamAdapter(requireContext(), teams) {
            val intent = Intent(context, DetailTeamActivity::class.java)
            intent.putExtra(DetailLeagueActivity.DATA_EXTRA, it.idTeam)
            intent.putExtra("name", it.strTeam)
            startActivity(intent)
        }
        rvTeam.adapter = teamAdapter

        if (savedInstanceState == null) {
            teamPresenter.loadTeam(idLeague)
        } else {
            hideLoading()
        }
        super.onActivityCreated(savedInstanceState)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString(DetailLeagueActivity.INSTANCE, TeamFragment::class.java.simpleName)
        super.onSaveInstanceState(outState)
    }

    private val getTeam = Observer<List<Team>> {
        teamAdapter.setTeam(it)
    }

    override fun showLoading() {
        pb_team.visible()
    }

    override fun hideLoading() {
        pb_team.invisible()
    }

    override fun teamData(team: List<Team>) {
        viewModel.setTeam(team)
    }
}
