package com.aas.footballapp.ui.detail.league

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.viewpager.widget.ViewPager
import com.aas.footballapp.R
import com.aas.footballapp.data.remote.api.ApiRepository
import com.aas.footballapp.ui.match.lastleaguematch.LastMatchFragment
import com.aas.footballapp.ui.match.nextleaguematch.NextMatchFragment
import com.aas.footballapp.ui.table.TableFragment
import com.aas.footballapp.ui.team.TeamFragment
import com.aas.footballapp.util.ViewPagerAdapter
import com.aas.footballapp.util.invisible
import com.aas.footballapp.util.visible
import com.google.android.material.tabs.TabLayout
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail_league.*

class DetailLeagueActivity : AppCompatActivity(),
    DetailLeagueInterface {

    private lateinit var detailLeagueLeaguePresenter: DetailLeaguePresenter
    private var id: String = "0"
    private var name: String = "name"
    private lateinit var listLeague: DetailLeague
    private lateinit var pagerAdapter: ViewPagerAdapter
    private lateinit var viewPager: ViewPager
    private lateinit var tabLayout: TabLayout

    companion object{
        const val DATA_EXTRA = "data_extra"
        const val INSTANCE = "instance"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_league)

        viewPager = findViewById(R.id.vp_detail_league)
        tabLayout = findViewById(R.id.tl_detail_league)

        pagerAdapter = ViewPagerAdapter(supportFragmentManager)
        pagerAdapter.addFragment(NextMatchFragment())
        pagerAdapter.addFragment(LastMatchFragment())
        pagerAdapter.addFragment(TeamFragment())
        pagerAdapter.addFragment(TableFragment())
        viewPager.adapter = pagerAdapter

        viewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))
        tabLayout.addOnTabSelectedListener(tabLayoutListener)

        id = intent.getStringExtra("id")
        name = intent.getStringExtra("name")
        saveLeagueId(id)

        val service = ApiRepository()
        val gson = Gson()
        detailLeagueLeaguePresenter =
            DetailLeaguePresenter(
                this,
                service, gson
            )
        detailLeagueLeaguePresenter.loadLeagueDetail(id)

        setSupportActionBar(toolbar)
        supportActionBar?.title = name
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

    }

    private val tabLayoutListener = object : TabLayout.OnTabSelectedListener {
        override fun onTabReselected(tab: TabLayout.Tab?) {

        }

        override fun onTabUnselected(tab: TabLayout.Tab?) {

        }

        override fun onTabSelected(tab: TabLayout.Tab) {
            viewPager.currentItem = tab.position
        }

    }

    override fun showLoading() {
        pb_detail_league.visible()
    }

    override fun hideLoading() {
        pb_detail_league.invisible()
    }

    override fun showLeague(league: List<DetailLeague>) {
        listLeague = DetailLeague(
            league[0].idLeague,
            league[0].strLeague,
            league[0].strDescriptionEN,
            league[0].strBadge
        )

        tv_league_name.text = league[0].strLeague
        tv_league_description.text = league[0].strDescriptionEN
        Picasso.get().load(league[0].strBadge).into(iv_league_badge)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun saveLeagueId (idLeague: String){
        val sharedPreferences = this.getPreferences(Context.MODE_PRIVATE) ?:
                return
        with(sharedPreferences.edit()){
            putString("idLeague", idLeague)
            commit()
        }
    }
}
