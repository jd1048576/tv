package jdr.tv.feature.search.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.squareup.cycler.Recycler
import com.squareup.cycler.toDataSource
import jdr.tv.common.log.Log
import jdr.tv.common.navigation.GlobalActions
import jdr.tv.common.ui.collectResource
import jdr.tv.common.ui.extensions.bind
import jdr.tv.common.ui.extensions.closeIconVisible
import jdr.tv.common.ui.extensions.dpToPixels
import jdr.tv.common.ui.extensions.loadPoster
import jdr.tv.common.ui.extensions.setupToolbar
import jdr.tv.common.ui.extensions.systemService
import jdr.tv.common.ui.onFailure
import jdr.tv.common.ui.onLoading
import jdr.tv.common.ui.onSuccess
import jdr.tv.data.core.di.DataComponent
import jdr.tv.data.local.entities.Show
import jdr.tv.feature.search.R
import jdr.tv.feature.search.databinding.FragmentSearchBinding
import jdr.tv.feature.search.databinding.ItemSearchBinding
import jdr.tv.feature.search.di.inject
import kotlinx.coroutines.launch
import javax.inject.Inject

class SearchFragment @Inject constructor(private val component: DataComponent) : Fragment() {

    companion object {
        private const val SPACING = 8
    }

    private var binding: FragmentSearchBinding? = null
    private var recycler: Recycler<Show>? = null

    @Inject
    lateinit var viewModelProviderFactory: ViewModelProvider.Factory
    private val viewModel: SearchViewModel by viewModels { viewModelProviderFactory }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        inject(component)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return FragmentSearchBinding.inflate(inflater, container, false).run {
            binding = this
            root
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.restore(savedInstanceState)
        setupToolbar(R.id.toolbar, displayHomeAsUp = true)
        setupRecyclerView()
        setupSearch()
        observe()
    }

    override fun onResume() {
        super.onResume()
        toggleSoftInput(viewModel.focus)
    }

    override fun onPause() {
        super.onPause()
        viewModel.focus = binding?.searchView?.hasFocus() ?: false
        toggleSoftInput(false)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        viewModel.save(outState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
        recycler = null
    }

    private fun setupRecyclerView() = binding?.recyclerView?.apply {
        recycler = Recycler.adopt(this) {
            stableId(Show::id)
            row<Show, View> {
                bind(ItemSearchBinding::inflate) { show ->
                    posterImageView.loadPoster(show.posterPath)
                    nameTextView.text = show.name
                    detailsTextView.text = details(show)
                    root.setOnClickListener {
                        navigate(show.id)
                    }
                }
            }
        }
        addItemDecoration(SearchItemDecoration(requireContext().dpToPixels(SPACING)))
    }

    private fun setupSearch() = binding?.searchView?.apply {
        setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                viewModel.onQueryTextSubmit(query)
                toggleSoftInput(false)
                return true
            }

            override fun onQueryTextChange(query: String): Boolean {
                viewModel.onQueryTextChange(query)
                return true
            }
        })
    }

    private fun observe() {
        lifecycleScope.launch {
            viewModel.search.collectResource {
                onLoading {
                    render(loading = true, empty = false)
                }
                onSuccess {
                    render(false, it.isEmpty() && !binding?.searchView?.query.isNullOrBlank())
                    recycler?.update { data = it.toDataSource() }
                }
                onFailure {
                    render(loading = false, empty = false)
                    Log.e(it, "Search Fragment Load Failure")
                }
            }
        }
    }

    private fun render(loading: Boolean, empty: Boolean) = binding?.apply {
        searchView.closeIconVisible(!loading)
        loadingProgressBar.visibility = if (loading) VISIBLE else GONE
        noResultsTextView.visibility = if (empty) VISIBLE else GONE
    }

    private fun navigate(showId: Long) {
        findNavController().navigate(GlobalActions.actionDetails(showId))
    }

    private fun toggleSoftInput(focus: Boolean) = binding?.searchView?.apply {
        val inputMethodManager = requireContext().systemService<InputMethodManager>()
        if (focus) {
            requestFocus()
            inputMethodManager.showSoftInput(findFocus(), 0)
        } else {
            clearFocus()
            inputMethodManager.hideSoftInputFromWindow(windowToken, 0)
        }
    }
}
