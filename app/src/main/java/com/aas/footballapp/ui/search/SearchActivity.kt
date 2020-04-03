package com.aas.footballapp.ui.search

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aas.footballapp.R
import com.aas.footballapp.data.model.Event
import com.aas.footballapp.data.model.Team
import com.aas.footballapp.data.remote.api.ApiRepository
import com.aas.footballapp.ui.detail.league.DetailLeagueActivity
import com.aas.footballapp.ui.detail.match.DetailMatchActivity
import com.aas.footballapp.ui.detail.team.DetailTeamActivity
import com.aas.footballapp.ui.match.MatchAdapter
import com.aas.footballapp.ui.team.TeamAdapter
import com.aas.footballapp.util.invisible
import com.aas.footballapp.util.visible
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_search.*
import org.jetbrains.anko.intentFor

class SearchActivity : AppCompatActivity(), SearchInterface {

    private lateinit var searchView: SearchView
    private lateinit var mPresenter: SearchPresenter
    private lateinit var mAdapter: MatchAdapter
    private lateinit var tAdapter: TeamAdapter
    private lateinit var viewModel: SearchViewModel
    private lateinit var rvSearch: RecyclerView
    private var events = mutableListOf<Event>()
    private var teams = mutableListOf<Team>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        rvSearch = findViewById(R.id.rv_search)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setTitle("Search")

        viewModel = ViewModelProviders.of(this).get(SearchViewModel::class.java)
        viewModel.getEvent().observe(this, getEvent)
        viewModel.getTeam().observe(this, getTeam)

        val service = ApiRepository()
        val gson = Gson()
        mPresenter = SearchPresenter(this, service, gson)

        rvSearch.addItemDecoration(
            DividerItemDecoration(rvSearch.context, DividerItemDecoration.VERTICAL)
        )

        rvSearch.layoutManager = LinearLayoutManager(this)
        mAdapter = MatchAdapter(this, events) {
            val intent = Intent(this, DetailMatchActivity::class.java)
            intent.putExtra(DetailLeagueActivity.DATA_EXTRA, it.idEvent)
            startActivity(intent)
        }
        tAdapter = TeamAdapter(this, teams) {
            val intent = Intent(this, DetailTeamActivity::class.java)
            intent.putExtra(DetailLeagueActivity.DATA_EXTRA, it.idTeam)
            intent.putExtra("name", it.strTeam)
            startActivity(intent)
        }

        rvSearch.adapter = mAdapter

        search_toggle.setOnToggleSwitchChangeListener { position, _ ->
            when (position) {
                0 -> {
                    rvSearch.adapter = mAdapter
                }
                1 -> {
                    rvSearch.adapter = tAdapter

                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.event_menu_search, menu)
        searchView = menu?.findItem(R.id.event_search)?.actionView as SearchView
        searchView.isIconified = false
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let { mPresenter.searchEvent(it) }
                query?.let { mPresenter.searchTeam(it) }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

        })
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                finish()
                true
            }
            R.id.event_search -> {
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    override fun showLoading() {
        pb_search.visible()
    }

    override fun hideLoading() {
        pb_search.invisible()
    }

    override fun searchMatchData(event: List<Event>) {
        viewModel.setEvent(event)
    }

    override fun searchTeamData(team: List<Team>) {
        viewModel.setTeam(team)
    }

    private val getEvent = Observer<List<Event>> {
        mAdapter.setEvent(it)
    }

    private val getTeam = Observer<List<Team>> {
        tAdapter.setTeam(it)
    }
}
