package com.aas.footballapp.ui.match.nextleaguematch


import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.aas.footballapp.R
import com.aas.footballapp.data.remote.api.ApiRepository
import com.aas.footballapp.ui.detail.league.DetailLeagueActivity
import com.aas.footballapp.ui.detail.match.DetailMatchActivity
import com.aas.footballapp.ui.match.MatchAdapter
import com.aas.footballapp.data.model.Event
import com.aas.footballapp.util.invisible
import com.aas.footballapp.util.visible
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_next_match.*

/**
 * A simple [Fragment] subclass.
 */
class NextMatchFragment : Fragment(), NextMatchInterface {

    private lateinit var nextMatchPresenter: NextMatchPresenter
    private lateinit var matchAdapter: MatchAdapter
    private lateinit var viewModel: NextMatchViewModel
    private lateinit var rvEvent: RecyclerView

    private var events = mutableListOf<Event>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_next_match, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        rvEvent = view.findViewById(R.id.rv_next_match)
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)

        viewModel = ViewModelProviders.of(this).get(NextMatchViewModel::class.java)
        viewModel.getNextMatch().observe(viewLifecycleOwner, getMatch)

        val sharedPreferences = activity!!.getPreferences(Context.MODE_PRIVATE)
        val idLeague = sharedPreferences.getString("idLeague", "")

        val service = ApiRepository()
        val gson = Gson()
        nextMatchPresenter = NextMatchPresenter(this, service, gson)

        rvEvent.addItemDecoration(
            DividerItemDecoration(
                rvEvent.context, DividerItemDecoration.VERTICAL
            )
        )

        rvEvent.layoutManager = LinearLayoutManager(requireContext())

        matchAdapter = MatchAdapter(requireContext(), events) {
            val intent = Intent(context, DetailMatchActivity::class.java)
            intent.putExtra(DetailLeagueActivity.DATA_EXTRA, it.idEvent)
            startActivity(intent)
        }
        rvEvent.adapter = matchAdapter

        if (savedInstanceState == null) {
            nextMatchPresenter.loadNextMatch(idLeague)
        } else {
            hideLoading()
        }

        super.onActivityCreated(savedInstanceState)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString(DetailLeagueActivity.INSTANCE, NextMatchFragment::class.java.simpleName)
        super.onSaveInstanceState(outState)
    }

    private val getMatch = Observer<List<Event>> {
        matchAdapter.setEvent(it)
    }

    override fun showLoading() {
        pb_next_match.visible()
    }

    override fun hideLoading() {
        pb_next_match.invisible()
    }

    override fun nextMatchData(event: List<Event>) {
        viewModel.setNextMatch(event)
    }


}
