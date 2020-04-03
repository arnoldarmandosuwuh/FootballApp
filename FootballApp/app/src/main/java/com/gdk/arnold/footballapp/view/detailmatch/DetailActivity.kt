package com.gdk.arnold.footballapp.view.detailmatch

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.gdk.arnold.footballapp.R
import com.gdk.arnold.footballapp.data.ApiRepository
import com.gdk.arnold.footballapp.data.model.matchlist.EventsItem
import com.gdk.arnold.footballapp.util.invisible
import com.gdk.arnold.footballapp.util.visible
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_detail.*
import org.jetbrains.anko.find
import java.text.SimpleDateFormat

class DetailActivity : AppCompatActivity(), DetailMatchView {

    private lateinit var dPrenseter: DetailMatchPresenter

    //Match Data
    private var matchDate: String? = ""
    private var homeName: String? = ""
    private var homeScore: String? = ""
    private var homeGoalie: String? = ""
    private var awayName: String? = ""
    private var awayScore: String? = ""
    private var awayGoalie: String? = ""

    //Home Player
    private var homeKeeper: String? = ""
    private var homeDefender: String? = ""
    private var homeMidfielder: String? = ""
    private var homeForward: String? = ""
    private var homeSubs: String? = ""

    //Away Player
    private var awayKeeper: String? = ""
    private var awayDefender: String? = ""
    private var awayMidfielder: String? = ""
    private var awayForward: String? = ""
    private var awaySubs: String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        dPrenseter = DetailMatchPresenter(this, ApiRepository(), Gson())
        val detail = intent.getParcelableExtra<EventsItem>("match")
        dPrenseter.getDetailMatch(detail.idHomeTeam!!, detail.idAwayTeam!!)

        getData(detail)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = detail.strEvent


    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return false
    }

    override fun hideLoading() {
        pbDetail.invisible()
    }

    override fun showLoading() {
        pbDetail.visible()
    }

    override fun showMatchBadge(homeTeam: String, awayTeam: String) {
        val homeBadge = find<ImageView>(R.id.homeImg)
        val awayBadge = find<ImageView>(R.id.awayImg)
        Glide.with(this).load(homeTeam).into(homeBadge)
        Glide.with(this).load(awayTeam).into(awayBadge)
    }

    fun getData(detail: EventsItem) {

        val formatDate = SimpleDateFormat("dd-MM-yyyy")

        //Match Data
        matchDate = formatDate.format(detail.dateEvent).toString()
        homeName = detail.strHomeTeam
        homeGoalie = detail.strHomeGoalDetails
        awayName = detail.strAwayTeam
        awayGoalie = detail.strAwayGoalDetails

        //Home Player
        homeKeeper = detail.strHomeLineupGoalkeeper
        homeDefender = detail.strHomeLineupDefense
        homeMidfielder = detail.strHomeLineupMidfield
        homeForward = detail.strHomeLineupForward
        homeSubs = detail.strHomeLineupSubstitutes

        //Away Player
        awayKeeper = detail.strAwayLineupGoalkeeper
        awayDefender = detail.strAwayLineupDefense
        awayMidfielder = detail.strAwayLineupMidfield
        awayForward = detail.strAwayLineupForward
        awaySubs = detail.strAwayLineupSubstitutes

        if (detail.intHomeScore != null && detail.intAwayScore != null) {
            tvDate.setTextColor(applicationContext.getColor(R.color.last_match))
            homeScore = detail.intHomeScore
            awayScore = detail.intAwayScore
            if (detail.intHomeScore > detail.intAwayScore) {
                tvHomeScore.setTextColor(getColor(R.color.win_match))
                tvAwayScore.setTextColor(getColor(R.color.lose_match))
            } else if (detail.intHomeScore < detail.intAwayScore) {
                tvHomeScore.setTextColor(getColor(R.color.lose_match))
                tvAwayScore.setTextColor(getColor(R.color.win_match))
            }
        }

        tvDate.text = matchDate

        tvHomeName.text = homeName
        tvAwayName.text = awayName

        tvHomeScore.text = homeScore
        tvAwayScore.text = awayScore

        tvHomeGoalScore.text = homeGoalie
        tvAwayGoalScorer.text = awayGoalie

        tvGKHome.text = homeKeeper
        tvGKAway.text = awayKeeper

        tvDefHome.text = homeDefender
        tvDefAway.text = awayDefender

        tvMidHome.text = homeMidfielder
        tvMidAway.text = awayMidfielder

        tvForwardHome.text = homeForward
        tvForwardAway.text = awayForward

        tvSubHome.text = homeSubs
        tvSubAway.text = awaySubs

    }

}

