package com.application.idong.aplikasigithubuser3.ui

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import androidx.core.content.ContextCompat
import com.application.idong.aplikasigithubuser3.R
import kotlinx.android.synthetic.main.partial_toolbar.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setStatusBar()
        setFragmentUser()
    }

    private fun setFragmentUser() {
        val listUserFragment =
            ListUserFragment()
        val fragment = supportFragmentManager.findFragmentByTag(ListUserFragment::class.java.simpleName)
        if (fragment !is ListUserFragment) {
            supportFragmentManager
                .beginTransaction()
                .add(R.id.frame_container, listUserFragment, ListUserFragment::class.java.simpleName)
                .commit()
        }
    }


    private fun setStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = ContextCompat.getColor(this, android.R.color.white)
        }
        tvTitle.text = getString(R.string.tb_search_user)
        ivBack.setOnClickListener {
            onBackPressed()
        }
    }
}