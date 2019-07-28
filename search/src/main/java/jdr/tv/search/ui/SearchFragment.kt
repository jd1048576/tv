package jdr.tv.search.ui

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import jdr.tv.app.GlideApp
import jdr.tv.base.Log
import jdr.tv.navigation.GlobalActions
import jdr.tv.search.R
import jdr.tv.search.di.inject
import jdr.tv.ui.extensions.dpToPixels
import jdr.tv.ui.extensions.setupToolbar
import jdr.tv.ui.extensions.systemService
import jdr.tv.ui.utils.SpacingItemDecoration
import javax.inject.Inject

class SearchFragment : Fragment(R.layout.fragment_search) {

    companion object {
        private const val SPACING = 16
    }

    private lateinit var adapter: SearchAdapter

    private lateinit var searchView: SearchView
    private lateinit var recyclerView: RecyclerView

    @Inject
    lateinit var viewModel: SearchViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
        inject()
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

    override fun onDestroy() {
        super.onDestroy()
        if (isRemoving) viewModel.invalidate()
    }

    private fun bindResources() = with(view!!) {
        searchView = findViewById(R.id.fragment_search_search_view)
        recyclerView = findViewById(R.id.fragment_search_recycler_view)
    }

    private fun setupRecyclerView() {
        adapter = SearchAdapter(GlideApp.with(this), this::scrollToTop, this::navigate)
        recyclerView.adapter = adapter
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.addItemDecoration(SpacingItemDecoration.LinearLayout(context!!.dpToPixels(SPACING)))
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
        viewModel.search.withLifecycleOwner(viewLifecycleOwner)
            .onLoading { Log.e("LOADING") }
            .onSuccess { adapter.submitList(it) }
            .onFailure { Log.e("FAILURE $it") }
    }

    private fun scrollToTop() {
        (recyclerView.layoutManager as? LinearLayoutManager)?.scrollToPositionWithOffset(0, 0)
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
