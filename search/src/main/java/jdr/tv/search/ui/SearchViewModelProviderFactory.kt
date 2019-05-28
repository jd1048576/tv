package jdr.tv.search.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject

class SearchViewModelProviderFactory @Inject constructor(private val repository: SearchRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (!modelClass.isAssignableFrom(SearchViewModel::class.java)) {
            throw IllegalArgumentException("Unknown ViewModel $modelClass")
        }
        return modelClass.cast(SearchViewModel(repository))!!
    }
}