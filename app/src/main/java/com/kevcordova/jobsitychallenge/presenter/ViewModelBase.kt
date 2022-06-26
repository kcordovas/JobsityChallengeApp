package com.kevcordova.jobsitychallenge.presenter

import androidx.lifecycle.ViewModel

abstract class ViewModelBase : ViewModel() {
    protected fun <T> generateEvent(nav: T) : Event<T> = Event(nav)
}