package com.application.idong.aplikasigithubuser3.api

/**
 * Created by ridhopratama on 17,October,2020
 */

object UriService {

    const val BASEURL = " https://api.github.com/"

    class User {
        companion object {
            const val SEARCH = "search/users"
            const val DETAIL = "users/{username}"
            const val LIST_FOLLOWER = "users/{username}/followers"
            const val LIST_FOLLOWING = "users/{username}/following"
        }
    }
}