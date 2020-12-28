package com.application.idong.aplikasigithubuser3.helper

import android.database.Cursor
import com.application.idong.aplikasigithubuser3.database.DatabaseContract
import com.application.idong.aplikasigithubuser3.model.User

/**
 * Created by ridhopratama on 24,October,2020
 */
object UserMappingHelper {

    fun mapCursorToArrayList(notesCursor: Cursor?): ArrayList<User> {
        val usersList = ArrayList<User>()
        notesCursor?.apply {
            while (moveToNext()) {
                val id = getInt(getColumnIndexOrThrow(DatabaseContract.UserColumns._ID))
                val node_id = getString(getColumnIndexOrThrow(DatabaseContract.UserColumns.NODE_ID))
                val login = getString(getColumnIndexOrThrow(DatabaseContract.UserColumns.LOGIN))
                val avatar_url = getString(getColumnIndexOrThrow(DatabaseContract.UserColumns.AVATAR_URL))
                val url = getString(getColumnIndexOrThrow(DatabaseContract.UserColumns.URL))
                val html_url = getString(getColumnIndexOrThrow(DatabaseContract.UserColumns.HTML_URL))
                val followers_url = getString(getColumnIndexOrThrow(DatabaseContract.UserColumns.FOLLOWERS_URL))
                val following_url = getString(getColumnIndexOrThrow(DatabaseContract.UserColumns.FOLLOWING_URL))
                val name = getString(getColumnIndexOrThrow(DatabaseContract.UserColumns.NAME))
                val company = getString(getColumnIndexOrThrow(DatabaseContract.UserColumns.COMPANY))
                val blog = getString(getColumnIndexOrThrow(DatabaseContract.UserColumns.BLOG))
                val location = getString(getColumnIndexOrThrow(DatabaseContract.UserColumns.LOCATION))
                val email = getString(getColumnIndexOrThrow(DatabaseContract.UserColumns.EMAIL))
                val public_repos = getInt(getColumnIndexOrThrow(DatabaseContract.UserColumns.PUBLIC_REPOS))
                val followers = getInt(getColumnIndexOrThrow(DatabaseContract.UserColumns.FOLLOWERS))
                val following = getInt(getColumnIndexOrThrow(DatabaseContract.UserColumns.FOLLOWING))

                usersList.add(User(
                    login = login,
                    id = id.toLong(),
                    node_id = node_id,
                    avatar_url = avatar_url,
                    url = url,
                    html_url = html_url,
                    followers_url = followers_url,
                    following_url = following_url,
                    name = name,
                    company = company,
                    blog = blog,
                    location = location,
                    email = email,
                    public_repos = public_repos,
                    followers = followers,
                    following = following
                ))
            }
        }
        return usersList
    }

    fun mapCursorToObject(notesCursor: Cursor?): User {

        var user = User()
        notesCursor?.apply {
            moveToFirst()
            val id = getInt(getColumnIndexOrThrow(DatabaseContract.UserColumns._ID))
            val node_id = getString(getColumnIndexOrThrow(DatabaseContract.UserColumns.NODE_ID))
            val login = getString(getColumnIndexOrThrow(DatabaseContract.UserColumns.LOGIN))
            val avatar_url = getString(getColumnIndexOrThrow(DatabaseContract.UserColumns.AVATAR_URL))
            val url = getString(getColumnIndexOrThrow(DatabaseContract.UserColumns.URL))
            val html_url = getString(getColumnIndexOrThrow(DatabaseContract.UserColumns.HTML_URL))
            val followers_url = getString(getColumnIndexOrThrow(DatabaseContract.UserColumns.FOLLOWERS_URL))
            val following_url = getString(getColumnIndexOrThrow(DatabaseContract.UserColumns.FOLLOWING_URL))
            val name = getString(getColumnIndexOrThrow(DatabaseContract.UserColumns.NAME))
            val company = getString(getColumnIndexOrThrow(DatabaseContract.UserColumns.COMPANY))
            val blog = getString(getColumnIndexOrThrow(DatabaseContract.UserColumns.BLOG))
            val location = getString(getColumnIndexOrThrow(DatabaseContract.UserColumns.LOCATION))
            val email = getString(getColumnIndexOrThrow(DatabaseContract.UserColumns.EMAIL))
            val public_repos = getInt(getColumnIndexOrThrow(DatabaseContract.UserColumns.PUBLIC_REPOS))
            val followers = getInt(getColumnIndexOrThrow(DatabaseContract.UserColumns.FOLLOWERS))
            val following = getInt(getColumnIndexOrThrow(DatabaseContract.UserColumns.FOLLOWING))

            user =  User(
                    login = login,
                    id = id.toLong(),
                    node_id = node_id,
                    avatar_url = avatar_url,
                    url = url,
                    html_url = html_url,
                    followers_url = followers_url,
                    following_url = following_url,
                    name = name,
                    company = company,
                    blog = blog,
                    location = location,
                    email = email,
                    public_repos = public_repos,
                    followers = followers,
                    following = following
            )
        }
        return user
    }
}