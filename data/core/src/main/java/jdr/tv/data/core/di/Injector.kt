package jdr.tv.data.core.di

interface Injector<T> {
    fun inject(target: T)
}
