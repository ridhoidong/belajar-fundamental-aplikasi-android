package com.application.idong.consumerapp

import android.database.ContentObserver
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.recyclerview.widget.LinearLayoutManager
import com.application.idong.aplikasigithubuser3.database.DatabaseContract.UserColumns.Companion.CONTENT_URI
import com.application.idong.aplikasigithubuser3.helper.UserMappingHelper
import com.application.idong.aplikasigithubuser3.model.User
import kotlinx.android.synthetic.main.fragment_list_favoriteuser.*
import kotlinx.android.synthetic.main.partial_empty_data.*
import kotlinx.android.synthetic.main.partial_loading.*
import kotlinx.android.synthetic.main.partial_toolbar.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

/**
 * Created by ridhopratama on 24,October,2020
 */

class ListFavoriteUserFragment : Fragment(), ActionUserListener {

    private lateinit var userAdapter: UserAdapter

    companion object {
        private const val EXTRA_STATE = "extra_state"
    }

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
        initialize(savedInstanceState)
    }

    private fun toolbarSetup() {
        activity?.tvTitle?.text = getString(R.string.tb_list_favorite_user)
        activity?.ivBack?.visibility = View.INVISIBLE
        activity?.llRightIcon?.visibility = View.INVISIBLE
    }

    private fun initialize(savedInstanceState: Bundle?) {
        userAdapter = UserAdapter()
        userAdapter.actionUserListener = this
        rvUser.apply {
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
            adapter = userAdapter
        }

        val handlerThread = HandlerThread("DataObserver")
        handlerThread.start()
        val handler = Handler(handlerThread.looper)
        val myObserver = object : ContentObserver(handler) {
            override fun onChange(self: Boolean) {
                loadNotesAsync()
            }
        }

        requireContext()?.contentResolver.registerContentObserver(CONTENT_URI, true, myObserver)

        if (savedInstanceState == null) {
            loadNotesAsync()
        } else {
            val list = savedInstanceState.getParcelableArrayList<User>(EXTRA_STATE)
            if (list != null) {
                userAdapter.updateData(list)
            }
        }
    }

    private fun loadNotesAsync() {
        GlobalScope.launch(Dispatchers.Main) {
            llLoading.visibility = View.VISIBLE
            val deferredNotes = async(Dispatchers.IO) {
                val cursor = requireContext().contentResolver?.query(CONTENT_URI, null, null, null, null)
                UserMappingHelper.mapCursorToArrayList(cursor)
            }
            val notes = deferredNotes.await()
            llLoading.visibility = View.GONE
            if (notes.size > 0) {
                userAdapter.updateData(notes)
                toggleEmptyData(false)
            } else {
                userAdapter.updateData(ArrayList())
                toggleEmptyData(true)
            }
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