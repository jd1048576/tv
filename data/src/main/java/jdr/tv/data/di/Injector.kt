package jdr.tv.data.di

interface Injector<T> {
    fun inject(target: T)
}
