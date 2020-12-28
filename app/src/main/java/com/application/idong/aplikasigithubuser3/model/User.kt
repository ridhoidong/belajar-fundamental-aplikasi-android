package com.application.idong.aplikasigithubuser3.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by ridhopratama on 17,October,2020
 */

@Parcelize
data class User(
    var login: String = "",
    var id: Long = 0,
    var node_id: String = "",
    var avatar_url: String? = "",
    var gravatar_id: String? = "",
    var url: String = "",
    var html_url: String = "",
    var followers_url: String? = "",
    var following_url: String? = "",
    var gists_url: String? = "",
    var starred_url: String? = "",
    var subscriptions_url: String? = "",
    var organizations_url: String? = "",
    var repos_url: String? = "",
    var events_url: String? = "",
    var received_events_url: String? = "",
    var type: String? = "",
    var site_admin: Boolean? = false,
    var name: String? = "",
    var company: String? = "",
    var blog: String? = "",
    var location: String? = "",
    var email: String? = "",
    var hireable: Boolean? = false,
    var bio: String? = "",
    var twitter_username: String? = "",
    var public_repos: Int? = 0,
    var public_gists: Int? = 0,
    var followers: Int? = 0,
    var following: Int? = 0,
    var created_at: String? = "",
    var updated_at: String? = ""
) : Parcelable