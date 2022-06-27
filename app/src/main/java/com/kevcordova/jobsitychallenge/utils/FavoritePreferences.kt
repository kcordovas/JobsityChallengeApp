package com.kevcordova.jobsitychallenge.utils

import com.google.gson.Gson
import com.kevcordova.jobsitychallenge.JobsityChallengeApplication

object FavoritePreferences {
    private const val PREF_TAG_FAVORITE_LIST = "PREFERENCE_TAG_FAVORITE_LIST"
    private val gson by lazy {
        Gson()
    }
    private val sharedPref by lazy {
        JobsityChallengeApplication.getApplication().sharedPref
    }

    fun saveFavoriteShow(idShow: Int) {
        val lisOfFavoriteJson = sharedPref.getString(PREF_TAG_FAVORITE_LIST)
        var lisOfFavorite = gson.fromJson(lisOfFavoriteJson, FavoriteListClass::class.java)
        if (lisOfFavorite == null) {
            lisOfFavorite = FavoriteListClass()
        }
        lisOfFavorite.addFavorite(idShow)

        sharedPref.edit(
            Pair(PREF_TAG_FAVORITE_LIST, gson.toJson(lisOfFavorite))
        )
    }

    fun getFavoriteListShow() : FavoriteListClass {
        val lisOfFavoriteJson = sharedPref.getString(PREF_TAG_FAVORITE_LIST)
        return gson.fromJson(lisOfFavoriteJson, FavoriteListClass::class.java)
    }

    fun isShowSavedInFavorite(idShow: Int) = getFavoriteListShow().favoriteList.contains(idShow)

    fun deleteFavorite(idShow: Int) {
        val favoriteListClassWithDeletedElement = getFavoriteListShow()
        favoriteListClassWithDeletedElement.favoriteList.remove(idShow)
        sharedPref.edit(
            Pair(PREF_TAG_FAVORITE_LIST, gson.toJson(favoriteListClassWithDeletedElement))
        )
    }

    class FavoriteListClass {
        val favoriteList = mutableListOf<Int>()

        fun addFavorite(idShow: Int) {
            favoriteList.add(idShow)
        }
    }
}