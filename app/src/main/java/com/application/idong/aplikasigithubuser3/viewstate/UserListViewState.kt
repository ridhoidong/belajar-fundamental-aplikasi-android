package com.application.idong.aplikasigithubuser3.viewstate

import com.application.idong.aplikasigithubuser3.model.User

/**
 * Created by ridhopratama on 18,Oktober,2020
 */

data class UserListViewState(
    var loading: Boolean = false,
    var error: String? = null,
    var data: MutableList<User>? = null
)