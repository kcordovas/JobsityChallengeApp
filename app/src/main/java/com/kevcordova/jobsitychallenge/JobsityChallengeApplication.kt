package com.kevcordova.jobsitychallenge

import android.app.Application
import com.example.baseandroidmodulekevcordova.storage.KSharedPreferences
import com.kevcordova.jobsitychallenge.dependencyinyection.DaggerJobsityChallengeComponent
import com.kevcordova.jobsitychallenge.dependencyinyection.JobsityChallengeComponent
import com.kevcordova.jobsitychallenge.dependencyinyection.UseCaseComponent
import com.kevcordova.jobsitychallenge.usescases.UseCaseModule

class JobsityChallengeApplication : Application() {

    lateinit var mainComponent: JobsityChallengeComponent
    lateinit var useCaseComponent: UseCaseComponent

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
        mainComponent = initAppComponent()
        useCaseComponent = mainComponent.inject(
            UseCaseModule()
        )
    }

    private fun initAppComponent() = DaggerJobsityChallengeComponent.factory().create(this)


}