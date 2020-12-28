package com.application.idong.aplikasigithubuser3.ui

import android.graphics.PorterDuff
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.application.idong.aplikasigithubuser3.R
import com.application.idong.aplikasigithubuser3.adapter.UserAdapter
import com.application.idong.aplikasigithubuser3.factory.UserFactory
import com.application.idong.aplikasigithubuser3.listener.ActionUserListener
import com.application.idong.aplikasigithubuser3.model.User
import com.application.idong.aplikasigithubuser3.repository.UserRepository
import com.application.idong.aplikasigithubuser3.utils.ViewUtil
import com.application.idong.aplikasigithubuser3.viewmodel.UserViewModel
import com.application.idong.aplikasigithubuser3.viewstate.UserListViewState
import kotlinx.android.synthetic.main.fragment_list_user.*
import kotlinx.android.synthetic.main.partial_empty_data.*
import kotlinx.android.synthetic.main.partial_loading.*
import kotlinx.android.synthetic.main.partial_toolbar.*

/**
 * Created by ridhopratama on 05,October,2020
 */

class ListUserFragment : Fragment(),
    ActionUserListener {

    private lateinit var userViewModel: UserViewModel
    private lateinit var userAdapter: UserAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_list_user, container, false)
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
            userListViewState.observe(viewLifecycleOwner, Observer(this@ListUserFragment::handleState))
        }
    }

    private fun toolbarSetup() {
        activity?.tvTitle?.text = getString(R.string.tb_search_user)
        activity?.ivBack?.visibility = View.GONE
        activity?.llRightIcon?.visibility = View.VISIBLE
        activity?.ivSetting?.apply {
            visibility = View.VISIBLE
            setOnClickListener {
                parentFragmentManager.commit {
                    addToBackStack(null)
                    replace(R.id.frame_container, SettingFragment(), SettingFragment::class.java.simpleName)
                }
            }
        }

        activity?.ivFavorite?.apply {
            visibility = View.VISIBLE
            setImageResource(R.drawable.ic_favorite)
            setColorFilter(ContextCompat.getColor(requireContext(), R.color.colorBlackSoft), PorterDuff.Mode.MULTIPLY)
            setOnClickListener {
                parentFragmentManager.commit {
                    addToBackStack(null)
                    replace( R.id.frame_container, ListFavoriteUserFragment(), ListFavoriteUserFragment::class.java.simpleName)
                }
            }
        }
    }

    private fun initialize() {
        userAdapter =
            UserAdapter()
        userAdapter.actionUserListener = this
        rvUser.apply {
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
            adapter = userAdapter
        }

        val searchPlate: View = svUsername.findViewById(androidx.appcompat.R.id.search_plate)
        searchPlate.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.colorTransparent))
        svUsername.onActionViewExpanded()
        svUsername.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (!query.isNullOrEmpty()) {
                    toggleEmptyData(false)
                    userViewModel.getSearchUser(query)
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText.isNullOrEmpty()) {
                    toggleEmptyData(true)
                    userAdapter.updateData(mutableListOf())
                }
                return false
            }

        })
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
        val detailUserFragment = DetailUserFragment()
        detailUserFragment.arguments = bundleOf(DetailUserFragment.EXTRA_USER to user.login, DetailUserFragment.ID to user.id)
        parentFragmentManager.commit {
            addToBackStack(null)
            replace(R.id.frame_container, detailUserFragment, DetailUserFragment::class.java.simpleName)
        }
    }
}