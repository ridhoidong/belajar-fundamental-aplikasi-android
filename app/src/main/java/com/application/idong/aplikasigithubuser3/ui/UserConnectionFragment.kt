package com.application.idong.aplikasigithubuser3.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.application.idong.aplikasigithubuser3.factory.UserFactory
import com.application.idong.aplikasigithubuser3.model.User
import com.application.idong.aplikasigithubuser3.R
import com.application.idong.aplikasigithubuser3.repository.UserRepository
import com.application.idong.aplikasigithubuser3.adapter.UserAdapter
import com.application.idong.aplikasigithubuser3.utils.ViewUtil
import com.application.idong.aplikasigithubuser3.viewmodel.UserViewModel
import com.application.idong.aplikasigithubuser3.viewstate.UserListViewState
import kotlinx.android.synthetic.main.fragment_user_connection.*
import kotlinx.android.synthetic.main.partial_empty_data.*
import kotlinx.android.synthetic.main.partial_loading.*

/**
 * Created by ridhopratama on 18,October,2020
 */

class UserConnectionFragment : Fragment() {

    private lateinit var userViewModel: UserViewModel
    private lateinit var userAdapter: UserAdapter

    companion object {

        const val ARG_SECTION_NUMBER = "section_number"

        fun newInstance(index: Int, username: String) : UserConnectionFragment {
            val fragment =
                UserConnectionFragment()
            fragment.arguments = bundleOf(ARG_SECTION_NUMBER to index, WrapUserConnectionFragment.TAG_USERNAME to username)
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_user_connection, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initialize()

        var index = 0
        var username = ""
        if (arguments != null) {
            index = arguments?.getInt(ARG_SECTION_NUMBER, 0) as Int
            username = arguments?.getString(WrapUserConnectionFragment.TAG_USERNAME).toString()
        }

        userViewModel = ViewModelProvider(this,
            UserFactory(
                UserRepository.instance
            )
        ).get(UserViewModel::class.java).apply {
            rlEmptyData.visibility = View.GONE
            if (index == 0) {
                userFollowerViewState.observe(viewLifecycleOwner, Observer(this@UserConnectionFragment::handleState))
                getListFollower(username)
            }
            else {
                userFollowingViewState.observe(viewLifecycleOwner, Observer(this@UserConnectionFragment::handleState))
                getListFollowing(username)
            }
        }
    }

    private fun initialize() {
        userAdapter =
            UserAdapter()
        rvUser.apply {
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
            adapter = userAdapter
        }
    }

    private fun handleState(viewState: UserListViewState?) {
        viewState?.let {
            toggleLoading(it.loading)
            it.data?.let { data -> showData(data) }
            it.error?.let { error -> showError(error) }
        }
    }

    private fun showData(data: MutableList<User>) {
        userAdapter.updateData(data)
        if (data.isNullOrEmpty()) {
            rlEmptyData.visibility = View.VISIBLE
        }
        else {
            rlEmptyData.visibility = View.GONE
        }
    }

    private fun showError(error: String) {
        ViewUtil.showToast(requireContext(), error)
    }

    private fun  toggleLoading(loading: Boolean) {
        if (loading) {
            llLoading.visibility = View.VISIBLE
        }
        else {
            llLoading.visibility = View.GONE
        }
    }
}