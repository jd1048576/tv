package jdr.tv.feature.search.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import javax.inject.Inject
import jdr.tv.common.log.Log
import jdr.tv.common.navigation.GlobalActions
import jdr.tv.common.ui.collectResource
import jdr.tv.common.ui.extensions.dpToPixels
import jdr.tv.common.ui.extensions.linearLayoutManager
import jdr.tv.common.ui.extensions.setupToolbar
import jdr.tv.common.ui.extensions.systemService
import jdr.tv.common.ui.onFailure
import jdr.tv.common.ui.onLoading
import jdr.tv.common.ui.onSuccess
import jdr.tv.data.core.di.DataComponent
import jdr.tv.feature.search.R
import jdr.tv.feature.search.databinding.FragmentSearchBinding
import jdr.tv.feature.search.di.inject
import kotlinx.coroutines.launch

class SearchFragment @Inject constructor(private val component: DataComponent) : Fragment(R.layout.fragment_search) {

    companion object {
        private const val SPACING = 8
    }

    private lateinit var binding: FragmentSearchBinding

    private lateinit var adapter: SearchAdapter

    private lateinit var searchView: SearchView
    private lateinit var recyclerView: RecyclerView

    @Inject
    lateinit var viewModel: SearchViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
        inject(component)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        bindResources()
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
        viewModel.focus = searchView.hasFocus()
        toggleSoftInput(false)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        viewModel.save(outState)
    }

    private fun bindResources() = with(view!!) {
        searchView = findViewById(R.id.fragment_search_search_view)
        recyclerView = findViewById(R.id.fragment_search_recycler_view)
    }

    private fun setupRecyclerView() {
        adapter = SearchAdapter(this::scrollToTop, this::navigate)
        recyclerView.adapter = adapter
        recyclerView.addItemDecoration(SearchItemDecoration(context!!.dpToPixels(SPACING)))
    }

    private fun setupSearch() = with(searchView) {
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
                    binding.loading = true
                    binding.empty = false
                }
                onSuccess {
                    adapter.submitList(it)
                    binding.loading = false
                    binding.empty = it.isEmpty() && !searchView.query.isNullOrBlank()
                }
                onFailure {
                    binding.loading = false
                    binding.empty = false
                    Log.i("FAILURE $it")
                }
            }
        }
    }

    private fun scrollToTop() {
        recyclerView.linearLayoutManager.scrollToPositionWithOffset(0, 0)
    }

    private fun navigate(showId: Long) {
        findNavController().navigate(GlobalActions.actionDetails(showId))
    }

    private fun toggleSoftInput(focus: Boolean) = with(searchView) {
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
