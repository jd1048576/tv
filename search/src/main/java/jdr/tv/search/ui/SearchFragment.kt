package jdr.tv.search.ui

import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import jdr.tv.base.extensions.setupToolbar
import jdr.tv.base.extensions.systemService
import jdr.tv.search.R
import jdr.tv.search.di.inject
import javax.inject.Inject

class SearchFragment : Fragment(R.layout.fragment_search) {

    private lateinit var searchView: SearchView

    @Inject
    lateinit var viewModel: SearchViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        inject()
        bindResources()
        setupToolbar(R.id.toolbar, displayHomeAsUp = true)
        setupSearch()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.state = SearchViewState(savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        toggleSoftInput(viewModel.state.focus)
    }

    override fun onPause() {
        super.onPause()
        viewModel.updateFocus(searchView.hasFocus())
        toggleSoftInput(false)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        viewModel.state.save(outState)
    }

    private fun bindResources() = with(view!!) {
        searchView = findViewById(R.id.fragment_search_search_view)
    }

    private fun setupSearch() = with(searchView) {
        setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                viewModel.submitQuery(query)
                toggleSoftInput(false)
                return true
            }

            override fun onQueryTextChange(query: String): Boolean {
                viewModel.onQueryTextChange(query)
                return true
            }
        })
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
