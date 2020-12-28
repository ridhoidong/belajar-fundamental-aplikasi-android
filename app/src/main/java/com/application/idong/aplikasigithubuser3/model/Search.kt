package com.application.idong.aplikasigithubuser3.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by ridhopratama on 17,October,2020
 */

@Parcelize
data class Search(
    var total_count: Int?,
    var incomplete_results: Boolean?,
    var items: MutableList<User>?
) : Parcelable