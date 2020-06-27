package br.com.tsmweb.presentation.livedata

import androidx.annotation.MainThread
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Observer

class SingleLiveEvent<T>: MediatorLiveData<T>() {

    private val observers = HashSet<ObserverWrapper<T>>()

    @MainThread
    override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
        val wrapper = ObserverWrapper(observer)
        observers.add(wrapper)

        super.observe(owner, observer)
    }

    @MainThread
    override fun removeObserver(observer: Observer<in T>) {
        if (observers.remove(observer)) {
            super.removeObserver(observer)
            return
        }

        observers.
            filter {
                it.observer == observer
            }.
            forEach {
                observers.remove(it)
                super.removeObserver(it)
            }

        super.removeObserver(observer)
    }

    @MainThread
    override fun setValue(value: T) {
        observers.forEach {
            it.newValue()
        }

        super.setValue(value)
    }

    inner class ObserverWrapper<T>(
        val observer: Observer<in T>
    ) : Observer<T> {

        private var pending: Boolean = false

        override fun onChanged(t: T) {
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