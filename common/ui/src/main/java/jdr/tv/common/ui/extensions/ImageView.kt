package jdr.tv.common.ui.extensions

import android.widget.ImageView
import androidx.annotation.DrawableRes
import coil.api.load
import jdr.tv.common.ui.R

private const val IMAGE_URL = "https://image.tmdb.org/t/p/"

private fun ImageView.loadTmdbImage(path: String?, @DrawableRes errorDrawable: Int, res: String) {
    load("$IMAGE_URL$res$path") {
        error(errorDrawable)
    }
}

fun ImageView.loadBackdrop(backdropPath: String?) {
    loadTmdbImage(backdropPath, R.drawable.ic_backdrop, "w1280")
}

fun ImageView.loadPoster(posterPath: String?) {
    loadTmdbImage(posterPath, R.drawable.ic_poster, "w500")
}
