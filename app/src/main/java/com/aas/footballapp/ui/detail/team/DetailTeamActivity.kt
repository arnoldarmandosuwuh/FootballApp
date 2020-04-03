package com.aas.footballapp.ui.detail.team

import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.viewpager.widget.ViewPager
import com.aas.footballapp.R
import com.aas.footballapp.data.remote.api.ApiRepository
import com.aas.footballapp.ui.match.lastteammatch.LastTeamMatchFragment
import com.aas.footballapp.ui.match.nextteammatch.NextTeamMatchFragment
import com.aas.footballapp.data.model.Team
import com.aas.footballapp.ui.detail.league.DetailLeagueActivity
import com.aas.footballapp.util.ViewPagerAdapter
import com.aas.footballapp.util.invisible
import com.aas.footballapp.util.visible
import com.google.android.material.tabs.TabLayout
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail_team.*
import org.jetbrains.anko.toast

class DetailTeamActivity : AppCompatActivity(), DetailTeamInterface {

    private lateinit var detailTeamPresenter: DetailTeamPresenter
    private var id: String = "0"
    private var name: String = "name"
    private var strTeam: String = "strTeam"
    private var strDescriptionEN: String = "strDescriptionEN"
    private var strTeamBadge: String = "strTeamBadge"
    private lateinit var listTeam: Team
    private lateinit var pagerAdapter: ViewPagerAdapter
    private lateinit var viewPager: ViewPager
    private lateinit var tabLayout: TabLayout
    private var menuItem: Menu? = null
    private var isFav: Boolean = false

    companion object {
        const val DATA_EXTRA = "data_extra"
        const val INSTANCE = "instance"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_team)

        viewPager = findViewById(R.id.vp_detail_team)
        tabLayout = findViewById(R.id.tl_detail_team)

        pagerAdapter = ViewPagerAdapter(supportFragmentManager)
        pagerAdapter.addFragment(NextTeamMatchFragment())
        pagerAdapter.addFragment(LastTeamMatchFragment())
        viewPager.adapter = pagerAdapter

        viewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))
        tabLayout.addOnTabSelectedListener(tabLayoutListener)

        id = intent.getStringExtra(DetailLeagueActivity.DATA_EXTRA)
        name = intent.getStringExtra("name")
        saveTeamId(id)

        val service = ApiRepository()
        val gson = Gson()
        detailTeamPresenter = DetailTeamPresenter(this, service, gson)
        detailTeamPresenter.loadTeamDetail(id)

        setSupportActionBar(toolbar)
        supportActionBar?.title = name
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        isFav = detailTeamPresenter.setFavorite(this, id)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_detail, menu)
        menuItem = menu
        setFavorite()
        return super.onCreateOptionsMenu(menu)
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                finish()
                true
            }
            R.id.fav -> {
                if (isFav) {
                    detailTeamPresenter.removeFavoriteTeam(this, id)
                    isFav = !isFav
                    setFavorite()
                } else {
                    if (id != "0" && strTeam != "strTeam" && strDescriptionEN !=
                        "strDescriptionEN" && strTeamBadge != "strTeamBadge"
                    ) {
                        detailTeamPresenter.addFavoriteTeam(
                            this,
                            id,
                            strTeam,
                            strDescriptionEN,
                            strTeamBadge
                        )
                        isFav = !isFav
                        setFavorite()
                    } else {
                        toast("Not Available").show()
                    }
                }
                true
            }
            else -> super.onOptionsItemSelected(item)

        }
    }

    override fun showLoading() {
        pb_detail_team.visible()
    }

    override fun hideLoading() {
        pb_detail_team.invisible()
    }

    override fun loadTeam(team: List<Team>) {
        listTeam = Team(
            team[0].idTeam,
            team[0].strTeam,
            team[0].strDescriptionEN,
            team[0].strTeamBadge
        )

        tv_team_name.text = team[0].strTeam
        tv_team_description.text = team[0].strDescriptionEN
        Picasso.get().load(team[0].strTeamBadge).into(iv_team_badge)

        strTeam = team[0].strTeam
        strDescriptionEN = team[0].strDescriptionEN
        strTeamBadge = team[0].strTeamBadge
    }

    fun saveTeamId(idTeam: String) {
        val sharedPreferences = this.getPreferences(Context.MODE_PRIVATE) ?: return
        with(sharedPreferences.edit()) {
            putString("idTeam", idTeam)
            commit()
        }
    }

    private fun setFavorite() {
        if (isFav) {
            menuItem?.getItem(0)?.icon =
                ContextCompat.getDrawable(this, R.drawable.ic_favorite_black_24dp)
        } else {
            menuItem?.getItem(0)?.icon =
                ContextCompat.getDrawable(this, R.drawable.ic_favorite_border_black_24dp)
        }
    }

}
