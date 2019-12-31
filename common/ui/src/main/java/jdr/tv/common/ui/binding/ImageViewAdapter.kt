package jdr.tv.common.ui.binding

import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.databinding.BindingAdapter
import coil.api.load
import jdr.tv.common.ui.R

private const val IMAGE_URL = "https://image.tmdb.org/t/p/"

fun load(view: ImageView, path: String?, @DrawableRes errorDrawable: Int, res: String) {
    view.load("$IMAGE_URL$res$path") {
        error(errorDrawable)
    }
}

@BindingAdapter("backdropPath")
fun setBackdropPath(view: ImageView, backdropPath: String?) {
    load(view, backdropPath, R.drawable.ic_backdrop, "w1280")
}

@BindingAdapter("posterPath")
fun setPosterPath(view: ImageView, posterPath: String?) {
    load(view, posterPath, R.drawable.ic_poster, "w500")
}

@BindingAdapter("selected")
fun setChecked(view: ImageView, selected: Boolean) {
    view.isSelected = selected
}
