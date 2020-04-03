package com.aas.footballapp.ui.favorite.favoriteteam


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

import com.aas.footballapp.R
import com.aas.footballapp.data.model.Team
import com.aas.footballapp.ui.detail.league.DetailLeagueActivity
import com.aas.footballapp.ui.detail.match.DetailMatchActivity.Companion.INTENT_RESULT_CODE
import com.aas.footballapp.ui.detail.team.DetailTeamActivity
import com.aas.footballapp.ui.favorite.FavoriteFragment.Companion.INTENT_REQUEST_CODE
import com.aas.footballapp.ui.match.MatchAdapter
import com.aas.footballapp.ui.team.TeamAdapter
import com.aas.footballapp.util.invisible
import com.aas.footballapp.util.visible
import kotlinx.android.synthetic.main.fragment_favorite_team.*

/**
 * A simple [Fragment] subclass.
 */
class FavoriteTeamFragment : Fragment(), FavoriteTeamInterface {

    private var listTeam = mutableListOf<Team>()
    private lateinit var viewModel: FavoriteTeamViewModel
    private lateinit var mPresenter: FavoriteTeamPresenter
    private lateinit var mAdapter: TeamAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite_team, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        viewModel = ViewModelProviders.of(this).get(FavoriteTeamViewModel::class.java)
        viewModel.getTeam().observe(viewLifecycleOwner, getFavTeam)

        mPresenter = FavoriteTeamPresenter(this)
        rv_fav_team.addItemDecoration(
            DividerItemDecoration(
                rv_fav_team.context,
                DividerItemDecoration.VERTICAL
            )
        )
        rv_fav_team.layoutManager = LinearLayoutManager(context)

        mAdapter = TeamAdapter(requireContext(), listTeam){
            val intent = Intent(context, DetailTeamActivity::class.java)
            intent.putExtra(DetailLeagueActivity.DATA_EXTRA, it.idTeam)
            intent.putExtra("name", it.strTeam)
            startActivity(intent)
        }
        rv_fav_team.adapter = mAdapter

        if(savedInstanceState == null){
            mPresenter.getFavoriteTeam(requireContext())
        } else {
            hideLoading()
        }

        super.onActivityCreated(savedInstanceState)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == INTENT_REQUEST_CODE) {
            if (resultCode == INTENT_RESULT_CODE) {
                mPresenter.getFavoriteTeam(requireContext())
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    private val getFavTeam = Observer<List<Team>> {
        if (it != null) {
            mAdapter.setTeam(it)
        }
    }

    override fun showLoading() {
        pb_fav_team.visible()
    }

    override fun hideLoading() {
        pb_fav_team.invisible()
    }

    override fun favoriteTeam(team: List<Team>) {
        viewModel.setTeam(team)
        if(team.isEmpty()){
            tv_no_data.visibility = View.VISIBLE
        }
    }

}
