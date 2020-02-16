package jdr.tv.common.ui.extensions

import android.view.LayoutInflater
import android.view.View
import androidx.viewbinding.ViewBinding
import com.squareup.cycler.StandardRowSpec

inline fun <I : Any, S : I, B : ViewBinding> StandardRowSpec<I, S, View>.bind(
    crossinline create: (LayoutInflater) -> B,
    crossinline block: B.(S) -> Unit
) {
    create { context ->
        val binding = create(LayoutInflater.from(context))
        view = binding.root
        bind { item -> binding.block(item) }
    }
}
