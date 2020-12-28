package com.application.idong.aplikasigithubuser3.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.Context
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import com.application.idong.aplikasigithubuser3.database.DatabaseContract.AUTHORITY
import com.application.idong.aplikasigithubuser3.database.DatabaseContract.UserColumns.Companion.CONTENT_URI
import com.application.idong.aplikasigithubuser3.database.DatabaseContract.UserColumns.Companion.TABLE_NAME
import com.application.idong.aplikasigithubuser3.database.DatabaseUserHelper

/**
 * Created by ridhopratama on 24,October,2020
 */

class UserProvider : ContentProvider() {

    companion object {
        private const val USER = 1
        private const val USER_ID = 2
        private lateinit var databaseUserHelper: DatabaseUserHelper

        private val sUriMatcher = UriMatcher(UriMatcher.NO_MATCH)
    }

    init {
        sUriMatcher.addURI(AUTHORITY, TABLE_NAME, USER)
        sUriMatcher.addURI(AUTHORITY,"$TABLE_NAME/#", USER_ID)
    }

    override fun onCreate(): Boolean {
        databaseUserHelper = DatabaseUserHelper.getInstance(context as Context)
        databaseUserHelper.open()
        return true
    }

    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor? {
        return when (sUriMatcher.match(uri)) {
            USER -> databaseUserHelper.queryAll()
            USER_ID -> databaseUserHelper.queryById(uri.lastPathSegment.toString())
            else -> null
        }
    }

    override fun getType(uri: Uri): String? {
        return null
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        val added: Long = when (USER) {
            sUriMatcher.match(uri) -> databaseUserHelper.insert(values)
            else -> 0
        }
        context?.contentResolver?.notifyChange(CONTENT_URI, null)
        return Uri.parse("$CONTENT_URI/$added")
    }

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<out String>?
    ): Int {
        val updated: Int = when (USER_ID) {
            sUriMatcher.match(uri) -> databaseUserHelper.update(uri.lastPathSegment.toString(), values)
            else -> 0
        }
        context?.contentResolver?.notifyChange(CONTENT_URI, null)
        return updated
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        val deleted: Int = when (USER_ID) {
            sUriMatcher.match(uri) -> databaseUserHelper.deleteById(uri.lastPathSegment.toString())
            else -> 0
        }
        context?.contentResolver?.notifyChange(CONTENT_URI, null)
        return deleted
    }
}