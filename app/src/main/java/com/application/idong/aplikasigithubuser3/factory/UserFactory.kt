package com.application.idong.aplikasigithubuser3.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.application.idong.aplikasigithubuser3.repository.UserRepository
import com.application.idong.aplikasigithubuser3.viewmodel.UserViewModel

/**
 * Created by ridhopratama on 17,October,2020
 */

class UserFactory (private val userRepository: UserRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserViewModel::class.java))
            return UserViewModel(
                userRepository
            ) as T
        throw IllegalArgumentException()
    }

}