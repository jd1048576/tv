package jdr.tv.ui.binding

import androidx.databinding.BindingAdapter
import com.google.android.material.appbar.AppBarLayout

@BindingAdapter("offsetChangedListener")
fun offsetChangedListener(view: AppBarLayout, listener: AppBarLayout.OnOffsetChangedListener) {
    view.addOnOffsetChangedListener(listener)
}
