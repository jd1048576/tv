package jdr.tv.details.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import jdr.tv.data.Result
import jdr.tv.local.entities.DetailedShow

class DetailsViewModel(private val repository: DetailsRepository) : ViewModel() {

    private val _id = MutableLiveData<Long>()

    var id: Long
        get() = _id.value ?: 0
        set(value) {
            _id.value = value
        }

    fun selectDetailedShow(): LiveData<Result<DetailedShow>> = _id.switchMap { repository.selectDetailedShow(it) }
}