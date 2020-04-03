package com.aas.footballapp.ui.match.nextteammatch

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aas.footballapp.R
import com.aas.footballapp.data.remote.api.ApiRepository
import com.aas.footballapp.ui.detail.match.DetailMatchActivity
import com.aas.footballapp.ui.detail.team.DetailTeamActivity
import com.aas.footballapp.ui.match.MatchAdapter
import com.aas.footballapp.ui.match.nextleaguematch.NextMatchInterface
import com.aas.footballapp.ui.match.nextleaguematch.NextMatchResponseModel
import com.aas.footballapp.ui.match.nextleaguematch.NextMatchViewModel
import com.aas.footballapp.data.model.Event
import com.aas.footballapp.util.invisible
import com.aas.footballapp.util.visible
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_next_match.*

class NextTeamMatchFragment : Fragment(), NextMatchInterface {

    private lateinit var nextTeamMatchPresenter: NextTeamMatchPresenter
    private lateinit var matchAdapter: MatchAdapter
    private lateinit var viewModel: NextMatchViewModel
    private lateinit var rvEvent: RecyclerView
    private var events = mutableListOf<Event>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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
        val idTeam = sharedPreferences.getString("idTeam", "")

        val service = ApiRepository()
        val gson = Gson()
        nextTeamMatchPresenter = NextTeamMatchPresenter(this, service, gson)

        rvEvent.addItemDecoration(
            DividerItemDecoration(
                rvEvent.context,
                DividerItemDecoration.VERTICAL
            )
        )

        rvEvent.layoutManager = LinearLayoutManager(requireContext())

        matchAdapter = MatchAdapter(requireContext(), events) {
            val intent = Intent(context, DetailMatchActivity::class.java)
            intent.putExtra(DetailTeamActivity.DATA_EXTRA, it.idEvent)
            startActivity(intent)
        }

        rvEvent.adapter = matchAdapter

        if (savedInstanceState == null) {
            nextTeamMatchPresenter.loadNextMatch(idTeam)
        } else {
            hideLoading()
        }

        super.onActivityCreated(savedInstanceState)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString(
            DetailTeamActivity.INSTANCE,
            NextTeamMatchFragment::class.java.simpleName
        )
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