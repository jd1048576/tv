package jdr.tv.base.di

interface Injector<T> {
    fun inject(target: T)
}
