package com.gdk.arnold.footballapp.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import org.jetbrains.anko.db.*

class DatabaseOpenHelper (context: Context) : ManagedSQLiteOpenHelper(context, "FavoriteMatch.db", null, 1){
    companion object {
        private var instance: DatabaseOpenHelper? = null

        @Synchronized
        fun getInstance (context: Context): DatabaseOpenHelper {
            if (instance ==null){
                instance =
                        DatabaseOpenHelper(context.applicationContext)
            }
            return instance as DatabaseOpenHelper
        }

    }
    override fun onCreate(db: SQLiteDatabase) {
        db.createTable(
            Favorite.TABLE_FAVORITE, true,
            Favorite.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
            Favorite.EVENT_ID to TEXT + UNIQUE,
            Favorite.EVENT_NAME to TEXT,
            Favorite.DATE_EVENT to TEXT,
            Favorite.HOME_ID to TEXT,
            Favorite.HOME_NAME to TEXT,
            Favorite.HOME_SCORE to TEXT,
            Favorite.HOME_SCORER to TEXT,
            Favorite.AWAY_ID to TEXT,
            Favorite.AWAY_NAME to TEXT,
            Favorite.AWAY_SCORE to TEXT,
            Favorite.AWAY_SCORER to TEXT,
            Favorite.HOME_GK to TEXT,
            Favorite.HOME_DEF to TEXT,
            Favorite.HOME_MID to TEXT,
            Favorite.HOME_FOR to TEXT,
            Favorite.HOME_SUB to TEXT,
            Favorite.AWAY_GK to TEXT,
            Favorite.AWAY_DEF to TEXT,
            Favorite.AWAY_MID to TEXT,
            Favorite.AWAY_FOR to TEXT,
            Favorite.AWAY_SUB to TEXT,
            Favorite.MATCH_TYPE to TEXT
            )
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.dropTable(Favorite.TABLE_FAVORITE, true)
    }
}
val Context.database: DatabaseOpenHelper
get() = DatabaseOpenHelper.getInstance(applicationContext)