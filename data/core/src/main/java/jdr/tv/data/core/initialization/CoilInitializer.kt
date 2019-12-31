package jdr.tv.data.core.initialization

import android.content.Context
import coil.Coil
import coil.ImageLoader
import javax.inject.Inject
import javax.inject.Named
import okhttp3.OkHttpClient

class CoilInitializer @Inject constructor(private val context: Context, @Named("IMAGE") private val client: OkHttpClient) : Initializer {
    override fun initialize() {
        Coil.setDefaultImageLoader {
            ImageLoader(context) {
                okHttpClient(client)
                crossfade(true)
            }
        }
    }
}
