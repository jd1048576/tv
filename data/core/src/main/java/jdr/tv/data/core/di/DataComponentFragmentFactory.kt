package jdr.tv.data.core.di

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import java.lang.reflect.Constructor
import javax.inject.Inject

class DataComponentFragmentFactory(private val component: DataComponent) : FragmentFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        val cls = loadFragmentClass(classLoader, className)
        val constructor = cls.constructors.map { it as Constructor<out Fragment> }.firstOrNull(CONSTRUCTOR_PREDICATE)
        return constructor?.newInstance(component) ?: super.instantiate(classLoader, className)
    }

    companion object {
        private val PARAMETER_TYPE: Array<Class<*>> = arrayOf(DataComponent::class.java)
        private val CONSTRUCTOR_PREDICATE: (Constructor<out Fragment>) -> Boolean =
            { it.isAnnotationPresent(Inject::class.java) && PARAMETER_TYPE.contentEquals(it.parameterTypes) }
    }
}
