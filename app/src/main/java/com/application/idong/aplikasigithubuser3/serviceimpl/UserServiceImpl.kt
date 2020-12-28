package com.application.idong.aplikasigithubuser3.serviceimpl

import android.content.ContentResolver
import android.content.ContentValues
import android.net.Uri
import com.application.idong.aplikasigithubuser3.api.ApiClientAdapter
import com.application.idong.aplikasigithubuser3.database.DatabaseContract.UserColumns.Companion.CONTENT_URI
import com.application.idong.aplikasigithubuser3.helper.UserMappingHelper
import com.application.idong.aplikasigithubuser3.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async

/**
 * Created by ridhopratama on 17,October,2020
 */

class UserServiceImpl(private val apiClientAdapter: ApiClientAdapter, private val contentResolver: ContentResolver) {

    suspend fun getSearchUser(username: String): MutableList<User>? {
        val response = apiClientAdapter.getUserService().search(username)
        if (response.isSuccessful) {
            return response.body()?.items
        }
        throw Exception("Terjadi kesalahan saat melakukan request data, status error ${response.code()}")
    }

    suspend fun getDetailUser(username: String): User? {
        val response = apiClientAdapter.getUserService().detail(username)
        if (response.isSuccessful) {
            return response.body()
        }
        throw Exception("Terjadi kesalahan saat melakukan request data, status error ${response.code()}")
    }

    suspend fun getListFollower(username: String): MutableList<User>? {
        val response = apiClientAdapter.getUserService().listFollower(username)
        if (response.isSuccessful) {
            return response.body()
        }
        throw Exception("Terjadi kesalahan saat melakukan request data, status error ${response.code()}")
    }

    suspend fun getListFollowing(username: String): MutableList<User>? {
        val response = apiClientAdapter.getUserService().listFollowing(username)
        if (response.isSuccessful) {
            return response.body()
        }
        throw Exception("Terjadi kesalahan saat melakukan request data, status error ${response.code()}")
    }

    fun findFavoriteUser(uri: Uri) : User? {
        val cursor = uri?.let { contentResolver.query(it, null, null, null, null) }
        var user: User
        if (cursor != null) {
            user = UserMappingHelper.mapCursorToObject(cursor)
            cursor.close()
            return user
        }
        throw Exception ("Terjadi kesalahan saat mencari data")
    }

    fun addFavoriteUser(values: ContentValues) : User? {
        val uri: Uri? = contentResolver?.insert(CONTENT_URI, values)
        val user = uri?.let { findFavoriteUser(it) }
        if (user != null) {
            return user
        }
        throw Exception ("Terjadi kesalahan saat menyimpan data")
    }

    fun deleteFavoriteUser(uri: Uri) : User? {
        val user = uri?.let { findFavoriteUser(it) }
        apiClientAdapter.context.contentResolver
        val successId = contentResolver?.delete(uri, null, null)
        if (successId > 0 && user != null) {
            return user
        }
        throw Exception ("Terjadi kesalahan saat menghapus data")
    }

    fun getAllFavoriteUser(uri: Uri) : MutableList<User>? {
        val cursor = uri?.let { contentResolver.query(it, null, null, null, null) }
        var user: ArrayList<User>
        if (cursor != null) {
            user = UserMappingHelper.mapCursorToArrayList(cursor)
            cursor.close()
            return user
        }
        throw Exception ("Terjadi kesalahan saat mencari data")
    }
}