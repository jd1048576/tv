package jdr.tv.base.di

interface BaseComponent<T> {
    fun inject(target: T)
}
