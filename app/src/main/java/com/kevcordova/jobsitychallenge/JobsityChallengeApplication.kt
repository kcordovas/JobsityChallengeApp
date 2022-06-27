package com.kevcordova.jobsitychallenge

import android.app.Application
import com.example.baseandroidmodulekevcordova.storage.KSharedPreferences

class JobsityChallengeApplication : Application() {
    companion object {
        private const val PREFERENCE_NAME = "JobsityChallengeApplication-SharedPreferences"

        private lateinit var instance: JobsityChallengeApplication
        fun getApplication(): JobsityChallengeApplication = instance

        private lateinit var preferences: KSharedPreferences
    }

    val sharedPref: KSharedPreferences get() = preferences

    override fun onCreate() {
        super.onCreate()
        instance = this
        preferences = KSharedPreferences(this, PREFERENCE_NAME)
    }
}