package com.aas.footballapp.ui.table


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
import com.aas.footballapp.data.model.Table
import com.aas.footballapp.ui.detail.team.DetailTeamActivity
import com.aas.footballapp.util.invisible
import com.aas.footballapp.util.visible
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_table.*

/**
 * A simple [Fragment] subclass.
 */
class TableFragment : Fragment(), TableInterface {

    private lateinit var tablePresenter: TablePresenter
    private lateinit var tableAdapter: TableAdapter
    private lateinit var viewModel: TableViewModel
    private lateinit var rvTable: RecyclerView
    private var tables = mutableListOf<Table>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_table, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        rvTable = view.findViewById(R.id.rv_table)
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)

        viewModel = ViewModelProviders.of(this).get(TableViewModel::class.java)
        viewModel.getTable().observe(viewLifecycleOwner, getTable)

        val sharedPreferences = activity!!.getPreferences(Context.MODE_PRIVATE)
        val idLeague = sharedPreferences.getString("idLeague", "")
        val season = "1920"

        val service = ApiRepository()
        val gson = Gson()
        tablePresenter = TablePresenter(this, service, gson)

        rvTable.addItemDecoration(
            DividerItemDecoration(
                rvTable.context, DividerItemDecoration.VERTICAL
            )
        )

        rvTable.layoutManager = LinearLayoutManager(requireContext())
        tableAdapter = TableAdapter(requireContext(), tables) {
            val intent = Intent(context, DetailTeamActivity::class.java)
            intent.putExtra(DetailLeagueActivity.DATA_EXTRA, it.teamid)
            intent.putExtra("name", it.name)
            startActivity(intent)
        }
        rvTable.adapter = tableAdapter

        if (savedInstanceState == null) {
            tablePresenter.loadTable(idLeague, season)
        } else {
            hideLoading()
        }

        super.onActivityCreated(savedInstanceState)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString(DetailLeagueActivity.INSTANCE, TableFragment::class.java.simpleName)
        super.onSaveInstanceState(outState)
    }

    private val getTable = Observer<List<Table>> {
        tableAdapter.setTable(it)
    }

    override fun showLoading() {
        pb_table.visible()
    }

    override fun hideLoading() {
        pb_table.invisible()
    }

    override fun tableData(table: List<Table>) {
        viewModel.setTable(table)
    }


}
