package com.application.idong.aplikasigithubuser3.ui

import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreference
import com.application.idong.aplikasigithubuser3.R
import com.application.idong.aplikasigithubuser3.receiver.AlarmReceiver
import com.application.idong.aplikasigithubuser3.utils.ViewUtil
import kotlinx.android.synthetic.main.partial_toolbar.*

/**
 * Created by ridhopratama on 24,October,2020
 */

class SettingFragment : PreferenceFragmentCompat(), SharedPreferences.OnSharedPreferenceChangeListener {

    private lateinit var REMINDER: String
    private lateinit var isReminderPreference: SwitchPreference
    private lateinit var alarmReceiver: AlarmReceiver

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.preferences)
        toolbarSetup()
        init()
        setSummaries()
        alarmReceiver = AlarmReceiver()
    }

    override fun onResume() {
        super.onResume()
        preferenceScreen.sharedPreferences.registerOnSharedPreferenceChangeListener(this)
    }

    override fun onPause() {
        super.onPause()
        preferenceScreen.sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
    }

    private fun toolbarSetup() {
        activity?.tvTitle?.text = getString(R.string.tb_setting)
        activity?.ivBack?.visibility = View.VISIBLE
        activity?.llRightIcon?.visibility = View.INVISIBLE
    }

    private fun init() {
        REMINDER = getString(R.string.key_reminder)
        isReminderPreference = findPreference<SwitchPreference> (REMINDER) as SwitchPreference
        isReminderPreference.title = getString(R.string.name_reminder).replace("[time]", getString(R.string.time_reminder))
    }

    private fun setSummaries() {
        val sh = preferenceManager.sharedPreferences
        isReminderPreference.isChecked = sh.getBoolean(REMINDER, false)
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences, key: String?) {
        if (key == REMINDER) {
            isReminderPreference.isChecked = sharedPreferences.getBoolean(REMINDER, false)
            if (isReminderPreference.isChecked) {
                val repeatTime = getString(R.string.time_reminder)
                val repeatTitle =  getString(R.string.key_reminder).capitalize()
                val repeatMessage = getString(R.string.message_reminder)
                alarmReceiver.setRepeatingAlarm(requireContext(), repeatTime, repeatTitle, repeatMessage)
                ViewUtil.showToast(requireContext(), getString(R.string.message_reminder_set).replace("[time]", repeatTime))
            }
            else {
                alarmReceiver.cancelAlarm(requireContext())
                ViewUtil.showToast(requireContext(), getString(R.string.message_reminder_cancel))
            }
        }
    }

}