package com.aas.footballapp.data.local

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.aas.footballapp.data.local.event.EventDb
import com.aas.footballapp.data.local.team.TeamDb
import org.jetbrains.anko.db.*

class DBHelper(context: Context, dbName: String = "dbFavorite.db") :
    ManagedSQLiteOpenHelper(context, dbName, null, 1) {
    companion object {
        private var instance: DBHelper? = null

        @Synchronized
        fun getInstance(context: Context): DBHelper {
            if (instance == null) {
                instance = DBHelper(context.applicationContext)
            }
            return instance as DBHelper
        }
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.createTable(
            EventDb.TABLE_EVENT, true,
            EventDb.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
            EventDb.EVENT_ID to TEXT,
            EventDb.EVENT_NAME to TEXT,
            EventDb.HOME_TEAM to TEXT,
            EventDb.AWAY_TEAM to TEXT,
            EventDb.HOME_SCORE to TEXT,
            EventDb.AWAY_SCORE to TEXT,
            EventDb.EVENT_DATE to TEXT,
            EventDb.HOME_ID to TEXT,
            EventDb.AWAY_ID to TEXT
        )
        db.createTable(
            TeamDb.TABLE_TEAM, true,
            TeamDb.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
            TeamDb.TEAM_ID to TEXT,
            TeamDb.TEAM_NAME to TEXT,
            TeamDb.TEAM_DESCRIPTION to TEXT,
            TeamDb.TEAM_BADGGE to TEXT
        )
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.dropTable(EventDb.TABLE_EVENT, false)
        db.dropTable(TeamDb.TABLE_TEAM, false)
    }
}

val Context.database: DBHelper
    get() = DBHelper.getInstance(applicationContext)