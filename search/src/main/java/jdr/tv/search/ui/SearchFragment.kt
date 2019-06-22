package jdr.tv.search.ui

import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import jdr.tv.app.GlideApp
import jdr.tv.base.extensions.dpToPixels
import jdr.tv.base.extensions.setupToolbar
import jdr.tv.base.extensions.systemService
import jdr.tv.search.R
import jdr.tv.search.di.inject
import jdr.tv.ui.utils.SpacingItemDecoration
import timber.log.Timber
import javax.inject.Inject

class SearchFragment : Fragment(R.layout.fragment_search) {

    private lateinit var adapter: SearchAdapter
    private lateinit var layoutManager: LinearLayoutManager

    private lateinit var searchView: SearchView
    private lateinit var recyclerView: RecyclerView

    @Inject
    lateinit var viewModel: SearchViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        inject()
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

    override fun onDestroyView() {
        super.onDestroyView()
        if (isRemoving) viewModel.invalidate()
    }

    private fun bindResources() = with(view!!) {
        searchView = findViewById(R.id.fragment_search_search_view)
        recyclerView = findViewById(R.id.fragment_search_recycler_view)
    }

    private fun setupRecyclerView() {
        layoutManager = LinearLayoutManager(context!!)
        adapter = SearchAdapter(GlideApp.with(this))
        adapter.registerAdapterDataObserver(createAdapterDataObserver())
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.addItemDecoration(SpacingItemDecoration.LinearLayout(context!!.dpToPixels(16)))
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
        viewModel.search
            .onLoading(viewLifecycleOwner) { Timber.e("LOADING") }
            .onSuccess(viewLifecycleOwner) { adapter.submitList(it) }
            .onFailure(viewLifecycleOwner) { Timber.e("FAILURE $it") }
    }

    private fun createAdapterDataObserver(): RecyclerView.AdapterDataObserver {
        return object : RecyclerView.AdapterDataObserver() {

            override fun onItemRangeMoved(fromPosition: Int, toPosition: Int, itemCount: Int) {
                if (fromPosition == 0 || toPosition == 0) scrollToTop()
            }

            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                if (positionStart == 0 && adapter.itemCount <= 20) scrollToTop()
            }
        }
    }

    private fun scrollToTop() {
        layoutManager.scrollToPositionWithOffset(0, 0)
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
