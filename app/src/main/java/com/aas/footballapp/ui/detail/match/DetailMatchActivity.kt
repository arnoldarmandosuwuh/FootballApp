package com.aas.footballapp.ui.detail.match

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.aas.footballapp.R
import com.aas.footballapp.data.remote.api.ApiRepository
import com.aas.footballapp.ui.detail.league.DetailLeagueActivity
import com.aas.footballapp.data.model.Team
import com.aas.footballapp.util.invisible
import com.aas.footballapp.util.visible
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail_match.*
import org.jetbrains.anko.toast
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class DetailMatchActivity : AppCompatActivity(), DetailMatchInterface {

    private lateinit var detailMatchPresenter: DetailMatchPresenter
    private var id: String = "0"
    private var strEvent: String = "strEvent"
    private var strHomeTeam: String = "strHomeTeam"
    private var strAwayTeam: String = "strAwayTeam"
    private var intHomeScore: String = "intHomeScore"
    private var intAwayScore: String = "intAwayScore"
    private var dateEvent: String = "dateEvent"
    private var idHomeTeam: String = "idHomeTeam"
    private var idAwayTeam: String = "idAwayTeam"
    private lateinit var listMatch: DetailMatch
    private var menuItem: Menu? = null
    private var isFav: Boolean = false

    companion object {
        const val INTENT_RESULT_CODE = 200
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_match)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        id = intent.getStringExtra(DetailLeagueActivity.DATA_EXTRA)

        val service = ApiRepository()
        val gson = Gson()
        detailMatchPresenter = DetailMatchPresenter(this, service, gson)
        detailMatchPresenter.loadMatchDetail(id)

        isFav = detailMatchPresenter.setFavorite(this, id)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_detail, menu)
        menuItem = menu
        setFavorite()
        return super.onCreateOptionsMenu(menu)
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
                    detailMatchPresenter.removeFavorite(this, id)
                    isFav = !isFav
                    setFavorite()
                } else {
                    if (id != "0" && strEvent != "strEvent" && strHomeTeam != "strHomeTeam" && strAwayTeam != "strAwayTeam" && intHomeScore != "intHomeScore" && intAwayScore != "intAwayScore" && dateEvent != "dateEvent" && idHomeTeam != "idHomeTeam" && idAwayTeam != "idAwayTeam") {
                        detailMatchPresenter.addFavoriteEvent(
                            this,
                            id,
                            strEvent,
                            strHomeTeam,
                            strAwayTeam,
                            intHomeScore,
                            intAwayScore,
                            dateEvent,
                            idHomeTeam,
                            idAwayTeam
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

    private fun setFavorite() {
        if (isFav) {
            menuItem?.getItem(0)?.icon =
                ContextCompat.getDrawable(this, R.drawable.ic_favorite_black_24dp)
        } else {
            menuItem?.getItem(0)?.icon =
                ContextCompat.getDrawable(this, R.drawable.ic_favorite_border_black_24dp)
        }
    }

    override fun showLoading() {
        pb_detail_match.visible()
    }

    override fun hideLoading() {
        pb_detail_match.invisible()
    }

    override fun showEvent(event: List<DetailMatch>) {
        val homeScore: String
        val awayScore: String

        if (event[0].intHomeScore != null) {
            homeScore = event[0].intHomeScore.toString()
        } else {
            homeScore = "-"
        }

        if (event[0].intAwayScore != null) {
            awayScore = event[0].intAwayScore.toString()
        } else {
            awayScore = "-"
        }
        listMatch = DetailMatch(
            event[0].idEvent,
            event[0].strEvent,
            event[0].strHomeTeam,
            event[0].strAwayTeam,
            homeScore,
            awayScore,
            event[0].strHomeLineupGoalkeeper,
            event[0].strHomeLineupDefense,
            event[0].strHomeLineupMidfield,
            event[0].strHomeLineupForward,
            event[0].strHomeLineupSubstitutes,
            event[0].strAwayLineupGoalkeeper,
            event[0].strAwayLineupDefense,
            event[0].strAwayLineupMidfield,
            event[0].strAwayLineupForward,
            event[0].strAwayLineupSubstitutes,
            event[0].strHomeGoalDetails,
            event[0].strAwayGoalDetails,
            event[0].dateEvent,
            event[0].idHomeTeam,
            event[0].idAwayTeam
        )

        supportActionBar?.title = event[0].strEvent

        event[0].idHomeTeam?.let { detailMatchPresenter.loadHomeBadge(it) }
        event[0].idAwayTeam?.let { detailMatchPresenter.loadAwayBadge(it) }

        val date = LocalDate.parse(event[0].dateEvent)
        val formatter = DateTimeFormatter.ofPattern("EEEE, dd MMMM yyyy")
        val formattedDate = date.format(formatter)

        tvEventName.text = event[0].strEvent
        tvHomeClub.text = event[0].strHomeTeam
        tvAwayClub.text = event[0].strAwayTeam
        tvHomeScore.text = homeScore
        tvAwayScore.text = awayScore
        tvHomeGk.text = event[0].strHomeLineupGoalkeeper
        tvHomeDef.text = event[0].strHomeLineupDefense
        tvHomeMid.text = event[0].strHomeLineupMidfield
        tvHomeFor.text = event[0].strHomeLineupForward
        tvHomeSub.text = event[0].strHomeLineupSubstitutes
        tvAwayGk.text = event[0].strAwayLineupGoalkeeper
        tvAwayDef.text = event[0].strAwayLineupDefense
        tvAwayMid.text = event[0].strAwayLineupMidfield
        tvAwayFor.text = event[0].strAwayLineupForward
        tvAwaySub.text = event[0].strAwayLineupSubstitutes
        tvHomeScorer.text = event[0].strHomeGoalDetails
        tvAwayScorer.text = event[0].strAwayGoalDetails
        tvEventDate.text = formattedDate

        id = event[0].idEvent.toString()
        strEvent = event[0].strEvent.toString()
        strHomeTeam = event[0].strHomeTeam.toString()
        strAwayTeam = event[0].strAwayTeam.toString()
        intHomeScore = homeScore
        intAwayScore = awayScore
        dateEvent = event[0].dateEvent.toString()
        idHomeTeam = event[0].idHomeTeam.toString()
        idAwayTeam = event[0].idAwayTeam.toString()
    }

    override fun showHomeBadge(team: List<Team>) {
        Picasso.get().load(team[0].strTeamBadge).into(ivHomeBadge)
    }

    override fun showAwayBadge(team: List<Team>) {
        Picasso.get().load(team[0].strTeamBadge).into(ivAwayBadge)
    }

    override fun onBackPressed() {
        setResult(INTENT_RESULT_CODE, Intent())
        super.onBackPressed()
    }
}
