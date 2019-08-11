package jdr.tv.app

import android.content.Context
import android.util.Log
import com.bumptech.glide.Glide
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.Registry
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.module.AppGlideModule
import java.io.InputStream

@GlideModule
class GlideAppConfiguration : AppGlideModule() {

    companion object {
        const val DISK_CACHE_SIZE_BYTES: Long = 1024 * 1024 * 50 // 50MB
    }

    override fun applyOptions(context: Context, builder: GlideBuilder) {
        builder.setDiskCache(InternalCacheDiskCacheFactory(context, DISK_CACHE_SIZE_BYTES))
        builder.setLogLevel(Log.ERROR)
    }

    override fun registerComponents(context: Context, glide: Glide, registry: Registry) {
        super.registerComponents(context, glide, registry)
        registry.replace(GlideUrl::class.java, InputStream::class.java, OkHttpUrlLoader.Factory(App.dataComponent(context).client()))
    }

    override fun isManifestParsingEnabled(): Boolean {
        return false
    }
}
