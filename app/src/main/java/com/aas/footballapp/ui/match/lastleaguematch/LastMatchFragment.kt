package com.aas.footballapp.ui.match.lastleaguematch


import android.content.Context
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
import com.aas.footballapp.ui.detail.match.DetailMatchActivity
import com.aas.footballapp.ui.match.MatchAdapter
import com.aas.footballapp.data.model.Event
import com.aas.footballapp.util.invisible
import com.aas.footballapp.util.visible
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_last_match.*

/**
 * A simple [Fragment] subclass.
 */
class LastMatchFragment : Fragment(), LastMatchInterface {

    private lateinit var lastMatchPresenter: LastMatchPresenter
    private lateinit var matchAdapter: MatchAdapter
    private lateinit var viewModel: LastMatchViewModel
    private lateinit var rvEvent: RecyclerView

    private var events = mutableListOf<Event>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_last_match, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        rvEvent = view.findViewById(R.id.rv_last_match)
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)

        viewModel = ViewModelProviders.of(this).get(LastMatchViewModel::class.java)
        viewModel.getLastMatch().observe(viewLifecycleOwner, getMatch)

        val sharedPreferences = activity!!.getPreferences(Context.MODE_PRIVATE)
        val idLeague = sharedPreferences.getString("idLeague", "")

        val service = ApiRepository()
        val gson = Gson()
        lastMatchPresenter = LastMatchPresenter(this, service, gson)

        rvEvent.addItemDecoration(
            DividerItemDecoration(
                rvEvent.context,
                DividerItemDecoration.VERTICAL
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
            lastMatchPresenter.loadLastMatch(idLeague)
        } else {
            hideLoading()
        }

        super.onActivityCreated(savedInstanceState)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString(DetailLeagueActivity.INSTANCE, LastMatchFragment::class.java.simpleName)
        super.onSaveInstanceState(outState)
    }

    private val getMatch = Observer<List<Event>> {
            matchAdapter.setEvent(it)
    }

    override fun showLoading() {
        pb_last_match.visible()
    }

    override fun hideLoading() {
        pb_last_match.invisible()
    }

    override fun lastMatchData(event: List<Event>) {
        viewModel.setLastMatch(event)
    }
}
