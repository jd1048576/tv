package jdr.tv.data.initialization

import android.content.Context
import coil.Coil
import coil.ImageLoader
import okhttp3.OkHttpClient
import javax.inject.Inject
import javax.inject.Named

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
