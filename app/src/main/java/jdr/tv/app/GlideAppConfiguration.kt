package jdr.tv.app

import android.content.Context
import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.bumptech.glide.Glide
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.Registry
import com.bumptech.glide.annotation.Excludes
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.integration.okhttp3.OkHttpLibraryGlideModule
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.module.AppGlideModule
import java.io.InputStream

@Excludes(OkHttpLibraryGlideModule::class)
@GlideModule
class GlideAppConfiguration : AppGlideModule() {

    override fun applyOptions(context: Context, builder: GlideBuilder) {
        val diskCacheSizeBytes = 1024 * 1024 * 50 // 50 MB
        builder.setDiskCache(InternalCacheDiskCacheFactory(context, diskCacheSizeBytes.toLong()))
    }

    override fun registerComponents(context: Context, glide: Glide, registry: Registry) {
        super.registerComponents(context, glide, registry)
        registry.replace(GlideUrl::class.java, InputStream::class.java, OkHttpUrlLoader.Factory(App.dataComponent(context).client()))
    }
}

private const val IMAGE_URL = "https://image.tmdb.org/t/p/"

fun ImageView.loadImage(path: String?, @DrawableRes placeHolder: Int, lowRes: String, highRes: String) {
    GlideApp.with(this).apply {
        load("$IMAGE_URL$highRes$path")
            .placeholder(placeHolder)
            .thumbnail(load("$IMAGE_URL$lowRes$path"))
            .centerCrop()
            .into(this@loadImage)
    }
}