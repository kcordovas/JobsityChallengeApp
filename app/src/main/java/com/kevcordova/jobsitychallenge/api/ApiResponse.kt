package com.kevcordova.jobsitychallenge.api

import com.google.gson.annotations.SerializedName

data class ShowResponseServer(
    @SerializedName("id") var id: Int? = null,
    @SerializedName("url") var url: String? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("type") var type: String? = null,
    @SerializedName("language") var language: String? = null,
    @SerializedName("genres") var genres: ArrayList<String> = arrayListOf(),
    @SerializedName("status") var status: String? = null,
    @SerializedName("runtime") var runtime: Int? = null,
    @SerializedName("averageRuntime") var averageRuntime: Int? = null,
    @SerializedName("premiered") var premiered: String? = null,
    @SerializedName("ended") var ended: String? = null,
    @SerializedName("officialSite") var officialSite: String? = null,
    @SerializedName("schedule") var schedule: ScheduleServer? = ScheduleServer(),
    @SerializedName("rating") var rating: RatingServer? = RatingServer(),
    @SerializedName("weight") var weight: Int? = null,
    @SerializedName("network") var network: NetworkServer? = NetworkServer(),
    @SerializedName("webChannel") var webChannel: String? = null,
    @SerializedName("dvdCountry") var dvdCountry: String? = null,
    @SerializedName("externals") var externals: ExternalsServer? = ExternalsServer(),
    @SerializedName("image") var image: ImageServer? = ImageServer(),
    @SerializedName("summary") var summary: String? = null,
    @SerializedName("updated") var updated: Int? = null,
    @SerializedName("_links") var Links: LinksServer? = LinksServer()
)

data class EpisodeResponseServer(
    @SerializedName("id") var id: Int? = null,
    @SerializedName("url") var url: String? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("season") var season: Int? = null,
    @SerializedName("number") var number: Int? = null,
    @SerializedName("type") var type: String? = null,
    @SerializedName("airdate") var airdate: String? = null,
    @SerializedName("airtime") var airtime: String? = null,
    @SerializedName("airstamp") var airstamp: String? = null,
    @SerializedName("runtime") var runtime: Int? = null,
    @SerializedName("rating") var rating: RatingServer? = RatingServer(),
    @SerializedName("image") var image: ImageServer? = ImageServer(),
    @SerializedName("summary") var summary: String? = null,
    @SerializedName("_links") var Links: EpisodeLinksServer? = EpisodeLinksServer()
)

data class ScheduleServer(

    @SerializedName("time") var time: String? = null,
    @SerializedName("days") var days: ArrayList<String> = arrayListOf()

)

data class RatingServer(

    @SerializedName("average") var average: Double? = null

)

data class CountryServer(

    @SerializedName("name") var name: String? = null,
    @SerializedName("code") var code: String? = null,
    @SerializedName("timezone") var timezone: String? = null

)

data class NetworkServer(

    @SerializedName("id") var id: Int? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("country") var country: CountryServer? = CountryServer(),
    @SerializedName("officialSite") var officialSite: String? = null

)

data class ExternalsServer(

    @SerializedName("tvrage") var tvrage: Int? = null,
    @SerializedName("thetvdb") var thetvdb: Int? = null,
    @SerializedName("imdb") var imdb: String? = null

)

data class ImageServer(

    @SerializedName("medium") var medium: String? = null,
    @SerializedName("original") var original: String? = null

)

data class SelfServer(

    @SerializedName("href") var href: String? = null

)

data class PreviousEpisodeServer(

    @SerializedName("href") var href: String? = null

)

data class LinksServer(

    @SerializedName("self") var self: SelfServer? = SelfServer(),
    @SerializedName("previousepisode") var previousepisode: PreviousEpisodeServer? = PreviousEpisodeServer()

)

data class EpisodeLinksServer(

    @SerializedName("self") var self: SelfServer? = SelfServer()

)