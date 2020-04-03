package com.aas.footballapp.ui.favorite.favoriteevent


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
import com.aas.footballapp.data.model.Event
import com.aas.footballapp.ui.detail.league.DetailLeagueActivity
import com.aas.footballapp.ui.detail.match.DetailMatchActivity
import com.aas.footballapp.ui.detail.match.DetailMatchActivity.Companion.INTENT_RESULT_CODE
import com.aas.footballapp.ui.favorite.FavoriteFragment.Companion.INTENT_REQUEST_CODE
import com.aas.footballapp.ui.match.MatchAdapter
import com.aas.footballapp.util.invisible
import com.aas.footballapp.util.visible
import kotlinx.android.synthetic.main.fragment_favorite_event.*

/**
 * A simple [Fragment] subclass.
 */
class FavoriteEventFragment : Fragment(), FavoriteEventInterface {

    private var listEvent = mutableListOf<Event>()
    private lateinit var viewModel: FavoriteEventViewModel
    private lateinit var mPresenter: FavoriteEventPresenter
    private lateinit var mAdapter: MatchAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite_event, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        viewModel = ViewModelProviders.of(this).get(FavoriteEventViewModel::class.java)
        viewModel.getEvent().observe(viewLifecycleOwner, getFavEvent)

        mPresenter = FavoriteEventPresenter(this)
        rv_fav_match.addItemDecoration(
            DividerItemDecoration(
                rv_fav_match.context,
                DividerItemDecoration.VERTICAL
            )
        )
        rv_fav_match.layoutManager = LinearLayoutManager(context)

        mAdapter = MatchAdapter(requireContext(), listEvent) {
            val intent = Intent(context, DetailMatchActivity::class.java)
            intent.putExtra(DetailLeagueActivity.DATA_EXTRA, it.idEvent)
            startActivity(intent)
        }
        rv_fav_match.adapter = mAdapter

        if (savedInstanceState == null) {
            mPresenter.getFavoriteEvent(requireContext())
        } else {
            hideLoading()
        }

        super.onActivityCreated(savedInstanceState)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == INTENT_REQUEST_CODE) {
            if (resultCode == INTENT_RESULT_CODE) {
                mPresenter.getFavoriteEvent(requireContext())
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    private val getFavEvent = Observer<List<Event>> {
        if (it != null) {
            mAdapter.setEvent(it)
        }
    }

    override fun showLoading() {
        pb_fav_match.visible()
    }

    override fun hideLoading() {
        pb_fav_match.invisible()
    }

    override fun favoriteEvent(event: List<Event>) {
        viewModel.setEvent(event)
        if (event.isEmpty()) {
            tv_no_data.visibility = View.VISIBLE
        }
    }


}
