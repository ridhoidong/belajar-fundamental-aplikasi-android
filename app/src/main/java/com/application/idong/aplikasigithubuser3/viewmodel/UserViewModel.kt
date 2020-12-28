package com.application.idong.aplikasigithubuser3.viewmodel

import android.content.ContentValues
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.application.idong.aplikasigithubuser3.repository.UserRepository
import com.application.idong.aplikasigithubuser3.viewstate.UserFavoriteViewState
import com.application.idong.aplikasigithubuser3.viewstate.UserListViewState
import com.application.idong.aplikasigithubuser3.viewstate.UserViewState
import kotlinx.coroutines.launch
import java.lang.Exception

/**
 * Created by ridhopratama on 17,October,2020
 */

class UserViewModel(private val userRepository: UserRepository) : ViewModel() {

    private val mUserListViewState = MutableLiveData<UserListViewState>().apply {
        value =
            UserListViewState()
    }

    private val mUserViewState = MutableLiveData<UserViewState>().apply {
        value =
            UserViewState()
    }

    private val mUserFollowerViewState = MutableLiveData<UserListViewState>().apply {
        value =
            UserListViewState()
    }

    private val mUserFollowingViewState = MutableLiveData<UserListViewState>().apply {
        value =
            UserListViewState()
    }

    private val mUserFavorite = MutableLiveData<UserFavoriteViewState>().apply {
        value =
            UserFavoriteViewState()
    }

    val userListViewState : LiveData<UserListViewState>
        get() = mUserListViewState

    val userViewState : LiveData<UserViewState>
        get() = mUserViewState

    val userFollowerViewState : LiveData<UserListViewState>
        get() = mUserFollowerViewState

    val userFollowingViewState : LiveData<UserListViewState>
        get() = mUserFollowingViewState

    val userFavoriteViewState : LiveData<UserFavoriteViewState>
        get() = mUserFavorite

    fun getSearchUser(username: String) = viewModelScope.launch {
        mUserListViewState.value = mUserListViewState.value?.copy(true)
        try {
            val data = userRepository.getSearchUser(username)
            mUserListViewState.value = mUserListViewState.value?.copy(loading = false, error = null, data = data)
        }
        catch (ex: Exception) {
            mUserListViewState.value = mUserListViewState.value?.copy(loading = false, error = ex.message, data = null)
        }
    }

    fun getDetailUser(username: String) = viewModelScope.launch {
        mUserViewState.value = mUserViewState.value?.copy(true)
        try {
            val data = userRepository.getDetailUser(username)
            mUserViewState.value = mUserViewState.value?.copy(loading = false, error = null, data = data)
        }
        catch (ex: Exception) {
            mUserViewState.value = mUserViewState.value?.copy(loading = false, error = ex.message, data = null)
        }
    }

    fun getListFollower(username: String) = viewModelScope.launch {
        mUserFollowerViewState.value = mUserFollowerViewState.value?.copy(true)
        try {
            val data = userRepository.getListFollower(username)
            mUserFollowerViewState.value = mUserFollowerViewState.value?.copy(loading = false, error = null, data = data)
        }
        catch (ex: Exception) {
            mUserFollowerViewState.value = mUserFollowerViewState.value?.copy(loading = false, error = ex.message, data = null)
        }
    }

    fun getListFollowing(username: String) = viewModelScope.launch {
        mUserFollowingViewState.value = mUserFollowingViewState.value?.copy(true)
        try {
            val data = userRepository.getListFollowing(username)
            mUserFollowingViewState.value = mUserFollowingViewState.value?.copy(loading = false, error = null, data = data)
        }
        catch (ex: Exception) {
            mUserFollowingViewState.value = mUserFollowingViewState.value?.copy(loading = false, error = ex.message, data = null)
        }
    }

    fun findFavoriteUser(uri: Uri) = viewModelScope.launch {
        mUserFavorite.value = mUserFavorite.value?.copy(type = 0, loading = true)
        try {
            val data = userRepository.findFavoriteUser(uri)
            mUserFavorite.value = mUserFavorite.value?.copy(type = 0, loading = false, error = null, data = data)
        }
        catch (ex: Exception) {
            mUserFavorite.value = mUserFavorite.value?.copy(type = 0, loading = false, error = null, data = null)
        }
    }

    fun addFavoriteUser(values: ContentValues) = viewModelScope.launch {
        mUserFavorite.value = mUserFavorite.value?.copy(type = 1, loading = true)
        try {
            val data = userRepository.addFavoriteUser(values)
            mUserFavorite.value = mUserFavorite.value?.copy(type = 1, loading = false, error = null, data = data)
        }
        catch (ex: Exception) {
            mUserFavorite.value = mUserFavorite.value?.copy(type = 1, loading = false, error = ex.message, data = null)
        }
    }

    fun deleteFavoriteUser(uri: Uri) = viewModelScope.launch {
        mUserFavorite.value = mUserFavorite.value?.copy(type = -1, loading = true)
        try {
            val data = userRepository.deleteFavoriteUser(uri)
            mUserFavorite.value = mUserFavorite.value?.copy(type = -1, loading = false, error = null, data = data)
        }
        catch (ex: Exception) {
            mUserFavorite.value = mUserFavorite.value?.copy(type = -1, loading = false, error = ex.message, data = null)
        }
    }

    fun getAllFavoriteUser(uri: Uri) = viewModelScope.launch {
        mUserListViewState.value = mUserListViewState.value?.copy(loading = true)
        try {
            val data = userRepository.getAllFavoriteUser(uri)
            mUserListViewState.value = mUserListViewState.value?.copy(loading = false, error = null, data = data)
        }
        catch (ex: Exception) {
            mUserListViewState.value = mUserListViewState.value?.copy(loading = false, error = null, data = null)
        }
    }
}