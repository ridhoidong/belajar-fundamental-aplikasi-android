package com.application.idong.aplikasigithubuser3.viewstate

import com.application.idong.aplikasigithubuser3.model.User

/**
 * Created by ridhopratama on 13,August,2020
 */

data class UserFavoriteViewState(
    var type: Int = 1,
    var loading: Boolean = false,
    var error: String? = null,
    var data: User? = null
)