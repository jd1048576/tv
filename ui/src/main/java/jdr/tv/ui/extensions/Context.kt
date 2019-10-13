package jdr.tv.ui.extensions

import android.content.Context
import android.util.DisplayMetrics
import android.util.TypedValue
import androidx.annotation.AttrRes

inline fun <reified T> Context.systemService(): T = getSystemService(T::class.java)!!

fun Context.dpToPixels(dp: Int): Int {
    return ((dp * resources.displayMetrics.densityDpi).toFloat() / DisplayMetrics.DENSITY_DEFAULT).toInt()
}

fun Context.pixelsToDp(pixels: Int): Int {
    return ((pixels * DisplayMetrics.DENSITY_DEFAULT).toFloat() / resources.displayMetrics.densityDpi).toInt()
}

fun Context.displayMetrics(): DisplayMetrics {
    return resources.displayMetrics
}

fun Context.resolveAttribute(@AttrRes attr: Int): TypedValue {
    val typedValue = TypedValue()
    theme.resolveAttribute(attr, typedValue, true)
    return typedValue
}
