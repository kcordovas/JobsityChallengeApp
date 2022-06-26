package com.kevcordova.jobsitychallenge.api

abstract class BaseRequest<T: Any> {
    inline fun <reified T: Any> getService(): T =
        RetrofitClientApi.instance.run {
            create(T::class.java)
        }
}

object ShowRequest: BaseRequest<ShowService>()
object EpisodeRequest: BaseRequest<EpisodeService>()