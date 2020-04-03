package com.gdk.arnold.footballapp.view.detailmatch

import android.database.sqlite.SQLiteConstraintException
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.gdk.arnold.footballapp.R
import com.gdk.arnold.footballapp.R.drawable.ic_add_to_favorite
import com.gdk.arnold.footballapp.R.drawable.ic_added_to_favorites
import com.gdk.arnold.footballapp.R.id.add_to_favorite
import com.gdk.arnold.footballapp.R.menu.*
import com.gdk.arnold.footballapp.data.ApiRepository
import com.gdk.arnold.footballapp.data.model.matchlist.EventsItem
import com.gdk.arnold.footballapp.db.Favorite
import com.gdk.arnold.footballapp.db.database
import com.gdk.arnold.footballapp.util.invisible
import com.gdk.arnold.footballapp.util.visible
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_detail.*
import org.jetbrains.anko.db.*
import org.jetbrains.anko.design.snackbar
import org.jetbrains.anko.find
import org.jetbrains.anko.support.v4.onRefresh
import java.text.SimpleDateFormat

class DetailActivity : AppCompatActivity(), DetailMatchView {

    private var menuItem: Menu? = null
    private var isFavorite: Boolean = false
    private lateinit var dPrenseter: DetailMatchPresenter
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var id: String

    //Match Data
    private var idEvent: String? = ""
    private var nameEvent: String? = ""
    private var matchDate: String? = ""
    private var homeId: String? = ""
    private var homeName: String? = ""
    private var homeScore: String? = ""
    private var homeGoalie: String? = ""
    private var awayId: String? = ""
    private var awayName: String? = ""
    private var awayScore: String? = ""
    private var awayGoalie: String? = ""
    private var matchType: String = ""

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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(detail_menu, menu)
        menuItem = menu
        setFavorite()
        return true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        swipeRefresh = findViewById(R.id.swipeRefresh)
        val detail = intent.getParcelableExtra<EventsItem>("match")
        dPrenseter = DetailMatchPresenter(this, ApiRepository(), Gson())
        dPrenseter.getDetailHome(detail.idHomeTeam as String)
        dPrenseter.getDetailAway(detail.idAwayTeam as String)
        getData(detail)
        favoriteState()
        swipeRefresh.onRefresh {
            getData(detail)
        }
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = detail.strEvent


    }

    private fun favoriteState() {
        database.use {
            var result = select(Favorite.TABLE_FAVORITE)
                .whereArgs(
                    "(EVENT_ID = {id})",
                    "id" to idEvent as String
                )
            val favorite = result.parseList(classParser<Favorite>())
            if (!favorite.isEmpty()) isFavorite = true
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            add_to_favorite -> {
                if (isFavorite)
                    removeFromFavorite()
                else
                    addToFavorite()

                isFavorite = !isFavorite
                setFavorite()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun addToFavorite() {
        try {
            database.use {
                insert(
                    Favorite.TABLE_FAVORITE,
                    Favorite.EVENT_ID to idEvent,
                    Favorite.EVENT_NAME to nameEvent,
                    Favorite.DATE_EVENT to matchDate,
                    Favorite.HOME_ID to homeId,
                    Favorite.HOME_NAME to homeName,
                    Favorite.HOME_SCORE to homeScore,
                    Favorite.HOME_SCORER to homeGoalie,
                    Favorite.AWAY_ID to awayId,
                    Favorite.AWAY_NAME to awayName,
                    Favorite.AWAY_SCORE to awayScore,
                    Favorite.AWAY_SCORER to awayGoalie,
                    Favorite.HOME_GK to homeKeeper,
                    Favorite.HOME_DEF to homeDefender,
                    Favorite.HOME_MID to homeMidfielder,
                    Favorite.HOME_FOR to homeForward,
                    Favorite.HOME_SUB to homeSubs,
                    Favorite.AWAY_GK to awayKeeper,
                    Favorite.AWAY_DEF to awayDefender,
                    Favorite.AWAY_MID to awayMidfielder,
                    Favorite.AWAY_FOR to awayForward,
                    Favorite.AWAY_SUB to awaySubs,
                    Favorite.MATCH_TYPE to matchType
                )
            }
            swipeRefresh.snackbar("Added to favorite").show()
        } catch (e: SQLiteConstraintException) {
            swipeRefresh.snackbar(e.localizedMessage).show()
        }
    }

    private fun removeFromFavorite() {
        try {
            database.use {
                delete(
                    Favorite.TABLE_FAVORITE, "(EVENT_ID = {id})",
                    "id" to idEvent as String
                )
            }
            swipeRefresh.snackbar("Removed from favorite").show()
        } catch (e: SQLiteConstraintException) {
            swipeRefresh.snackbar(e.localizedMessage).show()
        }
    }

    private fun setFavorite() {
        if (isFavorite)
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, ic_added_to_favorites)
        else
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, ic_add_to_favorite)
    }

    override fun hideLoading() {
        pbDetail.invisible()
    }

    override fun showLoading() {
        pbDetail.visible()
    }

    override fun showBadgeHome(homeTeam: String) {
        val homeBadge = find<ImageView>(R.id.homeImg)
        Glide.with(this).load(homeTeam).into(homeBadge)
    }
    override fun showBadgeAway(awayTeam: String) {
        val awayBadge = find<ImageView>(R.id.awayImg)
        Glide.with(this).load(awayTeam).into(awayBadge)
    }

    private fun getData(detail: EventsItem) {
        swipeRefresh.isRefreshing = false

        val formatDate = SimpleDateFormat("EEE, dd MMM yyyy")

        //Match Data
        idEvent = detail.idEvent
        nameEvent = detail.strEvent
        matchDate = formatDate.format(detail.dateEvent).toString()
        homeId = detail.idHomeTeam
        homeName = detail.strHomeTeam
        homeGoalie = detail.strHomeGoalDetails
        awayId = detail.idAwayTeam
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
            matchType = "Last Match"
        } else {
            tvDate.setTextColor(applicationContext.getColor(R.color.next_match))
            homeScore = ""
            awayScore = ""
            matchType = "Upcoming Match"
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