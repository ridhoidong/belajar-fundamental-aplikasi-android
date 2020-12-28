package com.application.idong.aplikasigithubuser3

import android.app.Application
import com.application.idong.aplikasigithubuser3.api.ApiClientAdapter
import com.application.idong.aplikasigithubuser3.repository.UserRepository
import com.application.idong.aplikasigithubuser3.serviceimpl.UserServiceImpl

/**
 * Created by ridhopratama on 11,August,2020
 */

class BaseApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        val apiClientAdapter = ApiClientAdapter(this)

        UserRepository.instance.apply {
            init(
                UserServiceImpl(apiClientAdapter, this@BaseApplication.contentResolver)
            )
        }
    }
}