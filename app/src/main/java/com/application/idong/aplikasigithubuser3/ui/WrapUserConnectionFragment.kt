package com.application.idong.aplikasigithubuser3.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.application.idong.aplikasigithubuser3.R
import com.application.idong.aplikasigithubuser3.adapter.WrapUserConnectionPagerAdapter
import kotlinx.android.synthetic.main.fragment_wrap_user_connection.*
import kotlinx.android.synthetic.main.partial_toolbar.*

/**
 * Created by ridhopratama on 18,October,2020
 */

class WrapUserConnectionFragment : Fragment() {

    companion object {
        const val TAG_USERNAME = "username"
    }

    private var username: String = ""
    private var index: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_wrap_user_connection, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (arguments != null) {
            username = arguments?.getString(TAG_USERNAME).toString()
            index = arguments?.getInt(UserConnectionFragment.ARG_SECTION_NUMBER, 0) as Int
        }
        toolbarSetup()

        val pagerAdapter =
            WrapUserConnectionPagerAdapter(
                requireContext(),
                childFragmentManager
            )
        pagerAdapter.username = username
        vpWrapUserConnection.adapter = pagerAdapter
        tlWrapUserConnection.setupWithViewPager(vpWrapUserConnection)
        tlWrapUserConnection.getTabAt(index)?.select()
    }

    private fun toolbarSetup() {
        activity?.tvTitle?.text = username
        activity?.llRightIcon?.visibility = View.INVISIBLE
    }
}