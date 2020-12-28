package com.application.idong.aplikasigithubuser3.database

import android.net.Uri
import android.provider.BaseColumns

/**
 * Created by ridhopratama on 24,October,2020
 */
object DatabaseContract {

    const val AUTHORITY = "com.application.idong.aplikasigithubuser3"
    const val SCHEME = "content"

    class UserColumns : BaseColumns {
        companion object {
            const val TABLE_NAME = "user"
            const val _ID  = "_id"
            const val NODE_ID  = "node_id"
            const val LOGIN = "login"
            const val URL = "url"
            const val AVATAR_URL = "avatar_url"
            const val HTML_URL = "html_url"
            const val FOLLOWERS_URL = "followers_url"
            const val FOLLOWING_URL = "following_url"
            const val NAME = "name"
            const val COMPANY = "company"
            const val BLOG = "blog"
            const val LOCATION = "location"
            const val EMAIL = "email"
            const val PUBLIC_REPOS = "public_repos"
            const val FOLLOWERS = "followers"
            const val FOLLOWING = "following"

            val CONTENT_URI : Uri = Uri.Builder().scheme(SCHEME)
                                                .authority(AUTHORITY)
                                                .appendPath(TABLE_NAME)
                                                .build()
        }
    }
}