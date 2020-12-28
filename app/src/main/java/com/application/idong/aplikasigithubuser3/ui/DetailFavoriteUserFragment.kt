package com.application.idong.aplikasigithubuser3.ui

import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.PorterDuff
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.content.contentValuesOf
import androidx.core.os.bundleOf
import androidx.fragment.app.commit
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.application.idong.aplikasigithubuser3.factory.UserFactory
import com.application.idong.aplikasigithubuser3.model.User
import com.application.idong.aplikasigithubuser3.R
import com.application.idong.aplikasigithubuser3.database.DatabaseContract.UserColumns.Companion.AVATAR_URL
import com.application.idong.aplikasigithubuser3.database.DatabaseContract.UserColumns.Companion.BLOG
import com.application.idong.aplikasigithubuser3.database.DatabaseContract.UserColumns.Companion.COMPANY
import com.application.idong.aplikasigithubuser3.database.DatabaseContract.UserColumns.Companion.CONTENT_URI
import com.application.idong.aplikasigithubuser3.database.DatabaseContract.UserColumns.Companion.EMAIL
import com.application.idong.aplikasigithubuser3.database.DatabaseContract.UserColumns.Companion.FOLLOWERS
import com.application.idong.aplikasigithubuser3.database.DatabaseContract.UserColumns.Companion.FOLLOWERS_URL
import com.application.idong.aplikasigithubuser3.database.DatabaseContract.UserColumns.Companion.FOLLOWING
import com.application.idong.aplikasigithubuser3.database.DatabaseContract.UserColumns.Companion.FOLLOWING_URL
import com.application.idong.aplikasigithubuser3.database.DatabaseContract.UserColumns.Companion.HTML_URL
import com.application.idong.aplikasigithubuser3.database.DatabaseContract.UserColumns.Companion.LOCATION
import com.application.idong.aplikasigithubuser3.database.DatabaseContract.UserColumns.Companion.LOGIN
import com.application.idong.aplikasigithubuser3.database.DatabaseContract.UserColumns.Companion.NAME
import com.application.idong.aplikasigithubuser3.database.DatabaseContract.UserColumns.Companion.NODE_ID
import com.application.idong.aplikasigithubuser3.database.DatabaseContract.UserColumns.Companion.PUBLIC_REPOS
import com.application.idong.aplikasigithubuser3.database.DatabaseContract.UserColumns.Companion.URL
import com.application.idong.aplikasigithubuser3.database.DatabaseContract.UserColumns.Companion._ID
import com.application.idong.aplikasigithubuser3.repository.UserRepository
import com.application.idong.aplikasigithubuser3.utils.ViewUtil
import com.application.idong.aplikasigithubuser3.viewmodel.UserViewModel
import com.application.idong.aplikasigithubuser3.viewstate.UserFavoriteViewState
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import kotlinx.android.synthetic.main.fragment_detail_user.*
import kotlinx.android.synthetic.main.partial_toolbar.*

/**
 * Created by ridhopratama on 05,October,2020
 */

class DetailFavoriteUserFragment : Fragment() {

    private var data: User? = null
    private lateinit var uriWithId: Uri
    private lateinit var userViewModel: UserViewModel

    companion object {
        const val EXTRA_USER = "user"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_detail_user, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (arguments != null) {
            data = arguments?.getParcelable(EXTRA_USER)
        }
        toolbarSetup()
        uriWithId = Uri.parse("$CONTENT_URI/${data?.id}")
        userViewModel = ViewModelProvider(this,
            UserFactory(
                UserRepository.instance
            )
        ).get(UserViewModel::class.java).apply {
            userFavoriteViewState.observe(viewLifecycleOwner, Observer(this@DetailFavoriteUserFragment::handleStateFavorite))
        }
        showData()
    }

    private fun toolbarSetup() {
        activity?.tvTitle?.text = getString(R.string.tb_detail_favorite_user)
        activity?.ivBack?.visibility = View.VISIBLE
        activity?.llRightIcon?.visibility = View.VISIBLE
        activity?.ivSetting?.visibility = View.INVISIBLE
    }

    private fun showData() {
        rlDetailUser.visibility = View.VISIBLE
        Glide.with(requireContext())
            .load(data?.avatar_url)
            .skipMemoryCache(true)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .override(150, 150)
            .into(ivImage)
        tvUsername.text = data?.login
        tvName.text = data?.name
        tvUrl.text = data?.html_url
        tvFollower.text = data?.followers.toString()
        tvFollowing.text = data?.following.toString()
        tvRepository.text = data?.public_repos.toString()
        tvLocation.text = data?.location
        tvCompany.text = data?.company

        tvUrl.setOnClickListener {
            data?.html_url?.let { it1 -> openGithubPage(it1) }
        }

        llFollower.setOnClickListener {
            fragmentTransaction(0, data?.login.toString())
        }

        llFollowing.setOnClickListener {
            fragmentTransaction(1, data?.login.toString())
        }

        userViewModel.findFavoriteUser(uriWithId)
    }

    private fun handleStateFavorite(viewState: UserFavoriteViewState?) {
        viewState?.let {
            toggleFavorite(false)
            it.data?.let { data -> showDataFavorite(it.type, data) }
            it.error?.let { error -> showError(error) }
        }
    }

    private fun showDataFavorite(type: Int, data: User) {
        when (type) {
            0 -> toggleFavorite(true)
            1 -> {
                toggleFavorite(true)
                ViewUtil.showToast(requireContext(), "User ${data.login} sukses disimpan ke favorit")
            }
            -1 -> {
                toggleFavorite(false)
                ViewUtil.showToast(requireContext(), "User ${data.login} sukses dihapus dari favorit")
            }
        }
    }

    private fun showError(error: String) {
        ViewUtil.showToast(requireContext(), error)
    }

    private fun  toggleFavorite(isFav: Boolean) {
        if (isFav) {
            activity?.ivFavorite?.apply {
                setImageResource(R.drawable.ic_favorite)
                setColorFilter(ContextCompat.getColor(requireContext(), R.color.colorPrimary), PorterDuff.Mode.MULTIPLY)
                setOnClickListener {
                    userViewModel.deleteFavoriteUser(uriWithId)
                }
            }
        }
        else {
            activity?.ivFavorite?.apply {
                setImageResource(R.drawable.ic_favorite_outline)
                setColorFilter(ContextCompat.getColor(requireContext(), R.color.colorBlackSoft), PorterDuff.Mode.MULTIPLY)
                setOnClickListener {
                    val values = contentValuesOf(
                        _ID to data?.id,
                        NODE_ID to data?.node_id,
                        LOGIN to data?.login,
                        URL to data?.url,
                        AVATAR_URL to data?.avatar_url,
                        HTML_URL to data?.html_url,
                        FOLLOWERS_URL to data?.followers_url,
                        FOLLOWING_URL to data?.following_url,
                        NAME to data?.name,
                        COMPANY to data?.company,
                        BLOG to data?.blog,
                        LOCATION to data?.location,
                        EMAIL to data?.email,
                        PUBLIC_REPOS to data?.public_repos,
                        FOLLOWERS to data?.followers,
                        FOLLOWING to data?.following
                    )
                    userViewModel.addFavoriteUser(values)
                }
            }
        }
    }

    private fun fragmentTransaction(index: Int, username: String) {
        val wrapUserConnectionFragment = WrapUserConnectionFragment()
        wrapUserConnectionFragment.arguments = bundleOf(UserConnectionFragment.ARG_SECTION_NUMBER to index, WrapUserConnectionFragment.TAG_USERNAME to username)
        parentFragmentManager.commit {
            addToBackStack(null)
            replace(R.id.frame_container, wrapUserConnectionFragment, WrapUserConnectionFragment::class.java.simpleName)
        }
    }

    private fun openGithubPage(url: String) {
        try {
            val urlPage = Uri.parse(url)
            val intent = Intent(Intent.ACTION_VIEW, urlPage)
            startActivity(intent)
        }
        catch (ex: ActivityNotFoundException) {
            ViewUtil.showToast(requireContext(), ex.message.toString())
        }
    }
}