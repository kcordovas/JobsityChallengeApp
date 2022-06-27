package com.kevcordova.jobsitychallenge.dependencyinyection

import android.app.Application
import com.kevcordova.jobsitychallenge.api.ApiModule
import com.kevcordova.jobsitychallenge.data.RepositoryModule
import com.kevcordova.jobsitychallenge.usescases.UseCaseModule
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApiModule::class, RepositoryModule::class])
interface JobsityChallengeComponent {
    fun inject(module: UseCaseModule) : UseCaseComponent

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance app: Application) : JobsityChallengeComponent
    }
}