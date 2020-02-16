package jdr.tv.feature.details.ui

import android.animation.ObjectAnimator
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.View.GONE
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.core.graphics.ColorUtils
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.tabs.TabLayoutMediator
import jdr.tv.common.log.Log
import jdr.tv.common.ui.collectResource
import jdr.tv.common.ui.extensions.dpToPixels
import jdr.tv.common.ui.extensions.loadBackdrop
import jdr.tv.common.ui.extensions.loadPoster
import jdr.tv.common.ui.extensions.resolveAttribute
import jdr.tv.common.ui.extensions.setupToolbar
import jdr.tv.common.ui.onFailure
import jdr.tv.common.ui.onLoading
import jdr.tv.common.ui.onSuccess
import jdr.tv.data.core.di.DataComponent
import jdr.tv.data.local.entities.Show
import jdr.tv.feature.details.R
import jdr.tv.feature.details.databinding.FragmentDetailsBinding
import jdr.tv.feature.details.di.inject
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.max

class DetailsFragment @Inject constructor(private val component: DataComponent) : Fragment() {

    companion object {
        const val SLIDE_TRANSLATION = 144
        const val SLIDE_DURATION = 240L
        const val GRADIENT = 4
        const val OFFSET = 0.75F
    }

    private var binding: FragmentDetailsBinding? = null

    @Inject
    lateinit var viewModelProviderFactory: ViewModelProvider.Factory
    private val viewModel: DetailsViewModel by viewModels { viewModelProviderFactory }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        inject(component)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_details, menu)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        menu.findItem(R.id.fragment_details_menu_remove_show)?.isVisible = binding?.floatingActionButton?.translationY != 0.0F
        super.onPrepareOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == R.id.fragment_details_menu_remove_show) {
            viewModel.updateAdded(false)
            return true
        } else {
            super.onOptionsItemSelected(item)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return FragmentDetailsBinding.inflate(inflater, container, false).run {
            binding = this
            root
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupToolbar(R.id.toolbar, displayHomeAsUp = true)
        setupAppBarLayout()
        setupViewPager()
        setupFloatingActionButton()
        observe()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private fun setupAppBarLayout() = binding?.apply {
        val colorControlNormal = requireContext().getColor(requireContext().resolveAttribute(jdr.tv.app.R.attr.colorControlNormal).resourceId)
        appBarLayout.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { v, verticalOffset ->
            val progress = -verticalOffset / v.totalScrollRange.toFloat()
            val color = ColorUtils.blendARGB(-1, colorControlNormal, max(0F, GRADIENT * (progress - OFFSET)))
            binding?.motionLayout?.progress = progress
            toolbar.navigationIcon?.setTint(color)
            toolbar.overflowIcon?.setTint(color)
        })
    }

    private fun setupViewPager() = binding?.apply {
        viewPager.adapter = DetailsViewPagerAdapter(this@DetailsFragment)
        TabLayoutMediator(tabLayout, viewPager, false) { tab, position ->
            tab.text = when (position) {
                0 -> "Details"
                1 -> "Seasons"
                else -> throw IllegalStateException("Illegal position $position")
            }
        }.attach()
    }

    private fun setupFloatingActionButton() = binding?.floatingActionButton?.setOnClickListener {
        viewModel.updateAdded(true)
    }

    private fun observe() {
        lifecycleScope.launch {
            viewModel.show.collectResource {
                onLoading {
                    render(true, it)
                }
                onSuccess {
                    render(false, it)
                }
                onFailure { Log.e(it, "Details Fragment Load Failure") }
            }
        }
        lifecycleScope.launch {
            viewModel.added.collect {
                onAddedChanged(it)
            }
        }
    }

    private fun render(loading: Boolean, show: Show?) = binding?.apply {
        progressBar.visibility = if (loading) VISIBLE else GONE
        viewPager.visibility = if (loading) INVISIBLE else VISIBLE
        floatingActionButton.visibility = if (loading) GONE else VISIBLE
        show?.apply {
            backdropImageView.loadBackdrop(backdropPath)
            posterImageView.loadPoster(posterPath)
            nameTextView.text = name
        }
    }

    private fun onAddedChanged(added: Boolean) = binding?.floatingActionButton?.apply {
        val translation = if (added) requireContext().dpToPixels(SLIDE_TRANSLATION).toFloat() else 0.0F
        if (translationY != translation) {
            ObjectAnimator.ofFloat(this, "translationY", translation).apply {
                duration = SLIDE_DURATION
                interpolator = AccelerateDecelerateInterpolator()
                start()
            }
        }
    }
}
