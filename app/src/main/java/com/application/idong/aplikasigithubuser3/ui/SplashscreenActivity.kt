package com.application.idong.aplikasigithubuser3.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.application.idong.aplikasigithubuser3.R

/**
 * Created by ridhopratama on 05,October,2020
 */

class SplashscreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splashscreen)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        gotoNextActivity()
    }

    private fun gotoNextActivity() {
        Handler().postDelayed({
            openActivityAndClearPrevious()
        }, 2000)
    }

    private fun openActivityAndClearPrevious() {
        try {
            intent = Intent(this, MainActivity::class.java)
            intent!!.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(intent)
            finish()
        }
        catch (ex: Exception) {
            ex.printStackTrace()
        }
    }
}