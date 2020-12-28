package com.application.idong.aplikasigithubuser3.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.application.idong.aplikasigithubuser3.database.DatabaseContract.UserColumns.Companion.TABLE_NAME

/**
 * Created by ridhopratama on 24,October,2020
 */

internal class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "dbgithubuser"
        private const val DATABASE_VERSION = 1
        private val SQL_CREATE_TABLE_USER = "CREATE TABLE $TABLE_NAME" +
                " (${DatabaseContract.UserColumns._ID} INTEGER PRIMARY KEY," +
                " ${DatabaseContract.UserColumns.NODE_ID} TEXT NOT NULL," +
                " ${DatabaseContract.UserColumns.LOGIN} TEXT NOT NULL," +
                " ${DatabaseContract.UserColumns.AVATAR_URL} TEXT," +
                " ${DatabaseContract.UserColumns.URL} TEXT NOT NULL," +
                " ${DatabaseContract.UserColumns.HTML_URL} TEXT NOT NULL," +
                " ${DatabaseContract.UserColumns.FOLLOWERS_URL} TEXT," +
                " ${DatabaseContract.UserColumns.FOLLOWING_URL} TEXT," +
                " ${DatabaseContract.UserColumns.NAME} TEXT," +
                " ${DatabaseContract.UserColumns.COMPANY} TEXT," +
                " ${DatabaseContract.UserColumns.BLOG} TEXT," +
                " ${DatabaseContract.UserColumns.LOCATION} TEXT," +
                " ${DatabaseContract.UserColumns.EMAIL} TEXT," +
                " ${DatabaseContract.UserColumns.PUBLIC_REPOS} INTEGER," +
                " ${DatabaseContract.UserColumns.FOLLOWERS} INTEGER," +
                " ${DatabaseContract.UserColumns.FOLLOWING} INTEGER)"
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_TABLE_USER)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

}