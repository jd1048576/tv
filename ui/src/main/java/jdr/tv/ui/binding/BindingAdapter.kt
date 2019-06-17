package jdr.tv.ui.binding

import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.databinding.BindingAdapter
import com.bumptech.glide.RequestManager
import jdr.tv.ui.R

@BindingAdapter("double")
fun setDouble(textView: TextView, double: Double) {
    textView.text = "%.1f".format(double)
}

private const val IMAGE_URL = "https://image.tmdb.org/t/p/"

fun RequestManager.loadImage(view: ImageView, path: String?, @DrawableRes placeHolder: Int, lowRes: String, highRes: String) {
    load("$IMAGE_URL$highRes$path")
        .placeholder(placeHolder)
        .thumbnail(load("$IMAGE_URL$lowRes$path"))
        .centerCrop()
        .into(view)
}

@BindingAdapter(value = ["glide", "backdropPath"], requireAll = true)
fun setBackdropPath(view: ImageView, glide: RequestManager, backdropPath: String?) {
    glide.loadImage(view, backdropPath, R.drawable.ic_backdrop, "w300", "w1280")
}