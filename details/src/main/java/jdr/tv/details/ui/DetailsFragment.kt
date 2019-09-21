package jdr.tv.details.ui

import android.animation.ObjectAnimator
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import jdr.tv.app.GlideApp
import jdr.tv.base.Log
import jdr.tv.details.R
import jdr.tv.details.databinding.FragmentDetailsBinding
import jdr.tv.details.di.inject
import jdr.tv.ui.collectResource
import jdr.tv.ui.extensions.dpToPixels
import jdr.tv.ui.extensions.setupToolbar
import jdr.tv.ui.onFailure
import jdr.tv.ui.onLoading
import jdr.tv.ui.onSuccess
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class DetailsFragment : Fragment(R.layout.fragment_details) {

    companion object {
        const val SLIDE_TRANSLATION = 144
        const val SLIDE_DURATION = 240L
    }

    private lateinit var binding: FragmentDetailsBinding

    @Inject
    lateinit var viewModel: DetailsViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
        inject()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentDetailsBinding.inflate(inflater, container, false)
        binding.requestManager = GlideApp.with(binding.root)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        setupToolbar(R.id.toolbar, displayHomeAsUp = true)
        setupAppBarLayout()
        setupViewPager()
        setupFloatingActionButton()
        observe()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_details, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        menu.findItem(R.id.fragment_details_menu_remove_show).isVisible = binding.fragmentDetailsFloatingActionButton.translationY != 0.0F
        super.onPrepareOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.fragment_details_menu_remove_show -> {
                viewModel.updateAdded(false)
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setupAppBarLayout() {
        binding.listener = AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
            val progress = -verticalOffset / appBarLayout.totalScrollRange.toFloat()
            binding.progress = progress
        }
    }

    private fun setupViewPager() = with(binding.fragmentDetailsViewPager) {
        val tabLayout = view!!.findViewById<TabLayout>(R.id.fragment_details_tab_layout)
        adapter = DetailsViewPagerAdapter(this@DetailsFragment)
        TabLayoutMediator(tabLayout, this, false) { tab, position ->
            tab.text = when (position) {
                0 -> "Details"
                1 -> "Seasons"
                else -> throw IllegalStateException("Illegal position $position")
            }
        }.attach()
    }

    private fun setupFloatingActionButton() = with(binding.fragmentDetailsFloatingActionButton) {
        setOnClickListener {
            viewModel.updateAdded(true)
        }
    }

    private fun observe() {
        lifecycleScope.launch {
            viewModel.show.collectResource {
                onLoading {
                    binding.loading = true
                    if (it != null) binding.show = it
                }
                onSuccess {
                    binding.loading = false
                    binding.show = it
                }
                onFailure { Log.e("FAILURE $it") }
            }
        }
        lifecycleScope.launch {
            viewModel.added.collect {
                onAddedChanged(it)
            }
        }
    }

    private fun onAddedChanged(added: Boolean) = with(binding.fragmentDetailsFloatingActionButton) {
        val translation = if (added) context!!.dpToPixels(SLIDE_TRANSLATION).toFloat() else 0.0F
        if (translationY != translation) {
            ObjectAnimator.ofFloat(this, "translationY", translation).apply {
                duration = SLIDE_DURATION
                interpolator = AccelerateDecelerateInterpolator()
                start()
            }
        }
    }
}
