package com.kevcordova.jobsitychallenge.presenter

/**
 * Event to management all cycle of a api request
 */
class Event <out T>(private val content: T) {
    private var hasBeenHandle = false

    fun getContentIfNotHandle(): T? = if (hasBeenHandle) {
        null
    } else {
        hasBeenHandle = true
        content
    }
}