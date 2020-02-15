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
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.squareup.cycler.Recycler
import com.squareup.cycler.toDataSource
import jdr.tv.common.log.Log
import jdr.tv.common.navigation.GlobalActions
import jdr.tv.common.ui.collectResource
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
    lateinit var viewModel: SearchViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
        inject(component)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupToolbar(R.id.toolbar, displayHomeAsUp = true)
        setupRecyclerView()
        setupSearch()
        observe()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.restore(savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        toggleSoftInput(viewModel.focus)
    }

    override fun onPause() {
        super.onPause()
        viewModel.focus = binding!!.fragmentSearchSearchView.hasFocus()
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

    private fun setupRecyclerView() = binding?.fragmentSearchRecyclerView?.apply {
        recycler = Recycler.adopt(this) {
            stableId(Show::id)
            row<Show, View> {
                create { context ->
                    val binding = ItemSearchBinding.inflate(LayoutInflater.from(context))
                    view = binding.root
                    bind { show ->
                        binding.itemSearchPosterImageView.loadPoster(show.posterPath)
                        binding.itemSearchNameTextView.text = show.name
                        binding.itemSearchDetailsTextView.text = details(show)
                        binding.root.setOnClickListener {
                            navigate(show.id)
                        }
                    }
                }
            }
        }
        addItemDecoration(SearchItemDecoration(context!!.dpToPixels(SPACING)))
    }

    private fun setupSearch() = binding?.fragmentSearchSearchView?.apply {
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
                    recycler?.data = it.toDataSource()
                    render(false, it.isEmpty() && !binding!!.fragmentSearchSearchView.query.isNullOrBlank())
                }
                onFailure {
                    render(loading = false, empty = false)
                    Log.i("FAILURE $it")
                }
            }
        }
    }

    private fun render(loading: Boolean, empty: Boolean) = binding?.apply {
        fragmentSearchSearchView.closeIconVisible(!loading)
        fragmentSearchProgressBar.visibility = if (loading) VISIBLE else GONE
        fragmentSearchEmptyTextView.visibility = if (empty) VISIBLE else GONE
    }

    private fun navigate(showId: Long) {
        findNavController().navigate(GlobalActions.actionDetails(showId))
    }

    private fun toggleSoftInput(focus: Boolean) = binding?.fragmentSearchSearchView?.apply {
        val inputMethodManager = context!!.systemService<InputMethodManager>()
        if (focus) {
            requestFocus()
            inputMethodManager.showSoftInput(findFocus(), 0)
        } else {
            clearFocus()
            inputMethodManager.hideSoftInputFromWindow(windowToken, 0)
        }
    }
}
