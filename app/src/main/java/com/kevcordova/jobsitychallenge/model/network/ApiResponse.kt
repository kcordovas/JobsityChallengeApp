package com.kevcordova.jobsitychallenge.model.network

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
    @SerializedName("schedule") var schedule: Schedule? = Schedule(),
    @SerializedName("rating") var rating: Rating? = Rating(),
    @SerializedName("weight") var weight: Int? = null,
    @SerializedName("network") var network: Network? = Network(),
    @SerializedName("webChannel") var webChannel: WebChannel? = WebChannel(),
    @SerializedName("dvdCountry") var dvdCountry: DvdCountry? = DvdCountry(),
    @SerializedName("externals") var externals: Externals? = Externals(),
    @SerializedName("image") var image: Image? = Image(),
    @SerializedName("summary") var summary: String? = null,
    @SerializedName("updated") var updated: Int? = null,
    @SerializedName("_links") var _links: Links? = Links()
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
    @SerializedName("rating") var rating: Rating? = Rating(),
    @SerializedName("image") var image: Image? = Image(),
    @SerializedName("summary") var summary: String? = null,
    @SerializedName("_links") var _links: EpisodeLinks? = EpisodeLinks()
)

data class ShowSearchResponseServer(
    @SerializedName("score") var score: Double? = null,
    @SerializedName("show") var show: ShowResponseServer? = ShowResponseServer()
)

data class Schedule(

    @SerializedName("time") var time: String? = null,
    @SerializedName("days") var days: ArrayList<String> = arrayListOf()

)

data class Rating(

    @SerializedName("average") var average: Double? = null

)

data class Country(

    @SerializedName("name") var name: String? = null,
    @SerializedName("code") var code: String? = null,
    @SerializedName("timezone") var timezone: String? = null

)

data class Network(

    @SerializedName("id") var id: Int? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("country") var country: Country? = Country(),
    @SerializedName("officialSite") var officialSite: String? = null

)

data class Externals(

    @SerializedName("tvrage") var tvrage: Int? = null,
    @SerializedName("thetvdb") var thetvdb: Int? = null,
    @SerializedName("imdb") var imdb: String? = null

)

data class Image(

    @SerializedName("medium") var medium: String? = null,
    @SerializedName("original") var original: String? = null

)

data class Self(

    @SerializedName("href") var href: String? = null

)

data class PreviousEpisode(

    @SerializedName("href") var href: String? = null

)

data class Links(

    @SerializedName("self") var self: Self? = Self(),
    @SerializedName("previousepisode") var previousepisode: PreviousEpisode? = PreviousEpisode()

)

/**
 * Example of a show that don't show null on [ShowResponseServer.webChannel]
 * {"id":2,"name":"Hulu","country":{"name":"United States","code":"US","timezone":"America/New_York"}
 */
data class WebChannel(
    @SerializedName("id") var id: Int? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("country") var country: Country? = Country()
)

/**
 * Example of dvd Country that don't show null on [ShowResponseServer.dvdCountry]
 * {"name":"United States","code":"US","timezone":"America/New_York"}
 */
data class DvdCountry(
    @SerializedName("name") var name: String? = null,
    @SerializedName("code") var code: String? = null,
    @SerializedName("timezone") var timezone: String? = null
)

data class EpisodeLinks(

    @SerializedName("self") var self: Self? = Self()

)