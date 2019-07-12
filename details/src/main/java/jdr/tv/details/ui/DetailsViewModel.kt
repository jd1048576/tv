package jdr.tv.details.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DetailsViewModel(private val repository: DetailsRepository) : ViewModel() {

    private val _id = MutableLiveData<Long>()

    var id: Long
        get() = _id.value ?: 0
        set(value) {
            _id.value = value
        }
}