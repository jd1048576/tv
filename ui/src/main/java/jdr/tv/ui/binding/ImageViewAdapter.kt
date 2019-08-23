package jdr.tv.ui.binding

import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.databinding.BindingAdapter
import com.bumptech.glide.RequestManager
import jdr.tv.ui.R

private const val IMAGE_URL = "https://image.tmdb.org/t/p/"

fun RequestManager.loadImage(view: ImageView, path: String?, @DrawableRes placeHolder: Int, lowRes: String, highRes: String) {
    load("$IMAGE_URL$highRes$path")
        .placeholder(placeHolder)
        .thumbnail(load("$IMAGE_URL$lowRes$path"))
        .into(view)
}

@BindingAdapter(value = ["requestManager", "backdropPath"], requireAll = true)
fun setBackdropPath(view: ImageView, requestManager: RequestManager, backdropPath: String?) {
    requestManager.loadImage(view, backdropPath, R.drawable.ic_backdrop, "w300", "w1280")
}

@BindingAdapter(value = ["requestManager", "posterPath"], requireAll = true)
fun setPosterPath(view: ImageView, requestManager: RequestManager, posterPath: String?) {
    requestManager.loadImage(view, posterPath, R.drawable.ic_poster, "w92", "w500")
}

@BindingAdapter("selected")
fun setChecked(view: ImageView, selected: Boolean) {
    view.isSelected = selected
}
