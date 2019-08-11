package jdr.tv.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ViewModelProviderFactory(private val provider: () -> ViewModel) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val viewModel: ViewModel = provider()
        if (!modelClass.isAssignableFrom(viewModel::class.java)) {
            throw IllegalArgumentException("Unknown ViewModel $modelClass")
        }
        return modelClass.cast(viewModel)!!
    }
}
