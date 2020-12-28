package com.application.idong.aplikasigithubuser3.repository

import android.content.ContentValues
import android.net.Uri
import com.application.idong.aplikasigithubuser3.model.User
import com.application.idong.aplikasigithubuser3.serviceimpl.UserServiceImpl

/**
 * Created by ridhopratama on 17,October,2020
 */

class UserRepository private constructor(){

    private var userServiceImpl: UserServiceImpl? = null

    fun init(userServiceImpl: UserServiceImpl) {
        this.userServiceImpl = userServiceImpl
    }

    suspend fun getSearchUser(username: String): MutableList<User>? {
        return userServiceImpl?.getSearchUser(username)
    }

    suspend fun getDetailUser(username: String): User? {
        return userServiceImpl?.getDetailUser(username)
    }

    suspend fun getListFollower(username: String): MutableList<User>? {
        return userServiceImpl?.getListFollower(username)
    }

    suspend fun getListFollowing(username: String): MutableList<User>? {
        return userServiceImpl?.getListFollowing(username)
    }

    fun addFavoriteUser(values: ContentValues) : User? {
        return userServiceImpl?.addFavoriteUser(values)
    }

    fun deleteFavoriteUser(uri: Uri) : User? {
        return userServiceImpl?.deleteFavoriteUser(uri)
    }

    fun findFavoriteUser(uri: Uri) : User? {
        return userServiceImpl?.findFavoriteUser(uri)
    }

    fun getAllFavoriteUser(uri: Uri) : MutableList<User>? {
        return userServiceImpl?.getAllFavoriteUser(uri)
    }

    companion object {
        val instance by lazy { UserRepository() }
    }
}