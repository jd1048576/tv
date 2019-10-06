package jdr.tv.shows.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import jdr.tv.navigation.GlobalActions
import jdr.tv.shows.R
import jdr.tv.shows.databinding.FragmentShowsBinding
import jdr.tv.shows.di.inject
import jdr.tv.ui.extensions.displayMetrics
import jdr.tv.ui.extensions.dpToPixels
import jdr.tv.ui.extensions.setupToolbar
import jdr.tv.ui.utils.SpacingItemDecoration
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.roundToInt

class ShowsFragment : Fragment(R.layout.fragment_shows) {

    companion object {
        private const val SPACING = 16
        private const val SPAN = 200
    }

    private lateinit var binding: FragmentShowsBinding

    private lateinit var adapter: ShowsAdapter

    @Inject
    lateinit var viewModel: ShowsViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
        inject()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentShowsBinding.inflate(inflater, container, false)
        binding.span = calculateSpan()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        setupToolbar(jdr.tv.ui.R.id.toolbar, jdr.tv.navigation.R.string.shows)
        setupRecyclerView()
        observe()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(jdr.tv.navigation.R.menu.menu_main, menu)
    }

    private fun calculateSpan(): Int {
        return (context!!.displayMetrics().widthPixels.toFloat() / context!!.dpToPixels(SPAN)).roundToInt()
    }

    private fun setupRecyclerView() = with(binding.fragmentSearchRecyclerView) {
        this@ShowsFragment.adapter = ShowsAdapter(this@ShowsFragment::navigate)
        adapter = this@ShowsFragment.adapter
        addItemDecoration(SpacingItemDecoration.GridLayout(context!!.dpToPixels(SPACING)))
    }

    private fun observe() {
        lifecycleScope.launch {
            viewModel.addedShowList.collect {
                adapter.submitList(it)
            }
        }
    }

    private fun navigate(showId: Long) {
        findNavController().navigate(GlobalActions.actionDetails(showId))
    }
}
