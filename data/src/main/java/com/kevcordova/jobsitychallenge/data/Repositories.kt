package com.kevcordova.jobsitychallenge.data

class ShowRepository(
    private val remoteShowDataSource: RemoteShowDataSource
) {
    suspend fun getShowById(idShow: Int) = remoteShowDataSource.getShow(idShow)
    suspend fun getAll() = remoteShowDataSource.getAllShows()
    suspend fun searchByName(title: String) = remoteShowDataSource.searchShowByName(title)
}

class EpisodeRepository(
    private val remoteEpisodeDataSource: RemoteEpisodeDataSource
) {
    suspend fun getEpisodeByShowId(idShow: Int) = remoteEpisodeDataSource.getEpisodesByShow(idShow)
    suspend fun getEpisodeBySeasonAndChapter(idShow: Int, seasonNumber: Int, chapterNumber: Int) = remoteEpisodeDataSource.getEpisodeBySeasonAndChapter(idShow, seasonNumber, chapterNumber)
}