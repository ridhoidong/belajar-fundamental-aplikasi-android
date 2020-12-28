package com.application.idong.consumerapp

import android.graphics.PorterDuff
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.core.content.contentValuesOf
import com.application.idong.aplikasigithubuser3.model.User
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
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import kotlinx.android.synthetic.main.fragment_detail_user.*
import kotlinx.android.synthetic.main.partial_toolbar.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Created by ridhopratama on 05,October,2020
 */

class DetailFavoriteUserFragment : Fragment() {

    private var data: User? = null
    private lateinit var uriWithId: Uri

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
        showData()
    }

    private fun toolbarSetup() {
        activity?.tvTitle?.text = getString(R.string.tb_detail_favorite_user)
        activity?.ivBack?.visibility = View.VISIBLE
        activity?.llRightIcon?.visibility = View.VISIBLE
        activity?.ivFavorite?.visibility = View.VISIBLE

        var isFavorite = true
        toggleFavorite(isFavorite)
        activity?.ivFavorite?.setOnClickListener {
            if (isFavorite) {
                deleteFavorite()
            }
            isFavorite != isFavorite
            toggleFavorite(isFavorite)
        }

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
    }


    private fun  toggleFavorite(isFavorite: Boolean) {
        if (isFavorite) {
            activity?.ivFavorite?.apply {
                setImageResource(R.drawable.ic_favorite)
                setColorFilter(
                    ContextCompat.getColor(requireContext(), R.color.colorWhite),
                    PorterDuff.Mode.MULTIPLY
                )
            }
        }
        else {
            activity?.ivFavorite?.apply {
                setImageResource(R.drawable.ic_favorite_outline)
                setColorFilter(
                    ContextCompat.getColor(requireContext(), R.color.colorWhite),
                    PorterDuff.Mode.MULTIPLY
                )
            }
        }
    }

    private fun deleteFavorite() {
        val alertDialogBuilder = AlertDialog.Builder(requireContext())
        alertDialogBuilder.setTitle("Hapus User")
        alertDialogBuilder
            .setMessage("Apakah anda yakin ingin menghapus user ini dari favorit?")
            .setCancelable(false)
            .setPositiveButton("Ya") { dialog, id ->
                context?.contentResolver?.delete(uriWithId, null, null)
                activity?.onBackPressed()
            }
            .setNegativeButton("Tidak") { dialog, id -> dialog.cancel() }
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }
}