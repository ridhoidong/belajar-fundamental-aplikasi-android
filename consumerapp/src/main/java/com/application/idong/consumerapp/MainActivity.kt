package com.application.idong.consumerapp

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.partial_toolbar.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setStatusBar()
        setFragmentUser()
    }

    private fun setFragmentUser() {
        val listFavoriteUserFragment = ListFavoriteUserFragment()
        val fragment = supportFragmentManager.findFragmentByTag(ListFavoriteUserFragment::class.java.simpleName)
        if (fragment !is ListFavoriteUserFragment) {
            supportFragmentManager
                .beginTransaction()
                .add(R.id.frame_container, listFavoriteUserFragment, ListFavoriteUserFragment::class.java.simpleName)
                .commit()
        }
    }


    private fun setStatusBar() {
        tvTitle.text = getString(R.string.tb_search_user)
        ivBack.setOnClickListener {
            onBackPressed()
        }
    }
}