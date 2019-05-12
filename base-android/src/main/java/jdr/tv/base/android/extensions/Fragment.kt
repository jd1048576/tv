package jdr.tv.base.android.extensions

import androidx.annotation.IdRes
import androidx.annotation.StringRes
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import timber.log.Timber

inline val Fragment.appCompatActivity: AppCompatActivity get() = (activity as AppCompatActivity)

inline val Fragment.supportActionBar: ActionBar get() = appCompatActivity.supportActionBar!!
fun Fragment.setSupportActionBar(toolbar: Toolbar) = appCompatActivity.setSupportActionBar(toolbar)

fun Fragment.setupToolbar(@IdRes id: Int, @StringRes title: Int = 0, displayHomeAsUp: Boolean = false) {
    Timber.e(id.toString())
    view?.findViewById<Toolbar>(id)?.also {
        Timber.e("Title ${it.title}")
        setSupportActionBar(it)
        supportActionBar.apply {
            if (title == 0) this.title = "" else setTitle(title)
            setDisplayHomeAsUpEnabled(displayHomeAsUp)
        }
    }
}