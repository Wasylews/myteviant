package com.genius.wasylews.myteviant.common.ui

import androidx.annotation.MainThread
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Observer
import java.util.*

class SingleLiveEvent<T> : MediatorLiveData<T>() {

    private val observers = LinkedList<ObserverWrapper<in T>>()

    @MainThread
    override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
        val wrapper = ObserverWrapper(observer)
        observers.add(wrapper)
        super.observe(owner, wrapper)
    }

    @MainThread
    override fun observeForever(observer: Observer<in T>) {
        val wrapper = ObserverWrapper(observer)
        observers.add(wrapper)
        super.observeForever(wrapper)
    }

    @MainThread
    override fun removeObserver(observer: Observer<in T>) {
        if (observer is ObserverWrapper && observers.remove(observer)) {
            super.removeObserver(observer)
            return
        }
        val iterator = observers.iterator()
        while (iterator.hasNext()) {
            val wrapper = iterator.next()
            if (wrapper.observer == observer) {
                iterator.remove()
                super.removeObserver(wrapper)
                break
            }
        }
    }

    @MainThread
    override fun setValue(t: T?) {
        if (observers.isNotEmpty()) {
            observers.last().newValue()
        }
        super.setValue(t)
    }

    private class ObserverWrapper<T>(val observer: Observer<T>) : Observer<T> {

        private var pending = false

        override fun onChanged(t: T?) {
            if (pending) {
                pending = false
                observer.onChanged(t)
            }
        }

        fun newValue() {
            pending = true
        }
    }
}