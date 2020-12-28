package com.application.idong.aplikasigithubuser3.ui

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.application.idong.aplikasigithubuser3.R
import com.application.idong.aplikasigithubuser3.adapter.UserAdapter
import com.application.idong.aplikasigithubuser3.database.DatabaseContract.UserColumns.Companion.CONTENT_URI
import com.application.idong.aplikasigithubuser3.factory.UserFactory
import com.application.idong.aplikasigithubuser3.listener.ActionUserListener
import com.application.idong.aplikasigithubuser3.model.User
import com.application.idong.aplikasigithubuser3.repository.UserRepository
import com.application.idong.aplikasigithubuser3.utils.ViewUtil
import com.application.idong.aplikasigithubuser3.viewmodel.UserViewModel
import com.application.idong.aplikasigithubuser3.viewstate.UserListViewState
import kotlinx.android.synthetic.main.fragment_list_favoriteuser.*
import kotlinx.android.synthetic.main.partial_empty_data.*
import kotlinx.android.synthetic.main.partial_loading.*
import kotlinx.android.synthetic.main.partial_toolbar.*

/**
 * Created by ridhopratama on 24,October,2020
 */

class ListFavoriteUserFragment : Fragment(), ActionUserListener {

    private lateinit var userViewModel: UserViewModel
    private lateinit var userAdapter: UserAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_list_favoriteuser, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toolbarSetup()
        initialize()
        userViewModel = ViewModelProvider(this,
            UserFactory(
                UserRepository.instance
            )
        ).get(UserViewModel::class.java).apply {
            userListViewState.observe(viewLifecycleOwner, Observer(this@ListFavoriteUserFragment::handleState))
        }
    }

    override fun onResume() {
        super.onResume()
        userViewModel.getAllFavoriteUser(Uri.parse(CONTENT_URI.toString()))
    }

    private fun toolbarSetup() {
        activity?.tvTitle?.text = getString(R.string.tb_list_favorite_user)
        activity?.ivBack?.visibility = View.VISIBLE
        activity?.llRightIcon?.visibility = View.INVISIBLE
    }

    private fun initialize() {
        userAdapter = UserAdapter()
        userAdapter.actionUserListener = this
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
            toggleEmptyData(true)
        }
        else {
            toggleEmptyData(false)
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

    private fun toggleEmptyData(state: Boolean) {
        if (state) {
            rlEmptyData.visibility = View.VISIBLE
        }
        else {
            rlEmptyData.visibility = View.GONE
        }
    }

    override fun onSelectUser(user: User) {
        val detailFavoriteUserFragment = DetailFavoriteUserFragment()
        detailFavoriteUserFragment.arguments = bundleOf(DetailFavoriteUserFragment.EXTRA_USER to user)
        parentFragmentManager.commit {
            addToBackStack(null)
            replace(R.id.frame_container, detailFavoriteUserFragment, DetailFavoriteUserFragment::class.java.simpleName)
        }
    }
}