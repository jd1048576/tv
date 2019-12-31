package jdr.tv.common.viewmodel

import android.os.Bundle
import androidx.lifecycle.ViewModel

interface State<T> {
    fun restore(bundle: Bundle): T
    fun save(bundle: Bundle)
}

abstract class StateViewModel<T : State<T>>(protected var state: T) : ViewModel() {

    fun restore(bundle: Bundle?) {
        if (bundle != null) state = state.restore(bundle)
    }

    fun save(bundle: Bundle) {
        state.save(bundle)
    }
}
