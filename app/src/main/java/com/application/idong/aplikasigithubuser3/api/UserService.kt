package com.application.idong.aplikasigithubuser3.api

import com.application.idong.aplikasigithubuser3.model.Search
import com.application.idong.aplikasigithubuser3.model.User
import retrofit2.Response
import retrofit2.http.*

/**
 * Created by ridhopratama on 17,October,2020
 */

interface UserService {

    @GET(UriService.User.SEARCH)
    suspend fun search(@Query("q") username: String): Response<Search>

    @GET(UriService.User.DETAIL)
    suspend fun detail(@Path("username") username: String): Response<User>

    @GET(UriService.User.LIST_FOLLOWER)
    suspend fun listFollower(@Path("username") username: String): Response<MutableList<User>>

    @GET(UriService.User.LIST_FOLLOWING)
    suspend fun listFollowing(@Path("username") username: String): Response<MutableList<User>>
}