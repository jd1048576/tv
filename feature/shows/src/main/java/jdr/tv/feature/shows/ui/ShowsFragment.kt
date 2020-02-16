package jdr.tv.feature.shows.ui

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
import com.squareup.cycler.Recycler
import com.squareup.cycler.toDataSource
import jdr.tv.common.navigation.GlobalActions
import jdr.tv.common.ui.extensions.bind
import jdr.tv.common.ui.extensions.displayMetrics
import jdr.tv.common.ui.extensions.dpToPixels
import jdr.tv.common.ui.extensions.loadPoster
import jdr.tv.common.ui.extensions.setupToolbar
import jdr.tv.common.ui.extensions.span
import jdr.tv.common.ui.utils.SpacingItemDecoration
import jdr.tv.data.core.di.DataComponent
import jdr.tv.data.local.entities.Show
import jdr.tv.feature.shows.databinding.FragmentShowsBinding
import jdr.tv.feature.shows.databinding.ItemShowPosterBinding
import jdr.tv.feature.shows.di.inject
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.roundToInt

class ShowsFragment @Inject constructor(private val component: DataComponent) : Fragment() {

    companion object {
        private const val SPACING = 16
        private const val SPAN = 200
    }

    private var binding: FragmentShowsBinding? = null
    private var recycler: Recycler<Show>? = null

    @Inject
    lateinit var viewModel: ShowsViewModel

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
        inflater.inflate(jdr.tv.common.navigation.R.menu.menu_main, menu)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return FragmentShowsBinding.inflate(inflater, container, false).run {
            binding = this
            recyclerView.span = calculateSpan()
            root
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        setupToolbar(jdr.tv.common.ui.R.id.toolbar, jdr.tv.common.navigation.R.string.shows)
        setupRecyclerView()
        observe()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
        recycler = null
    }

    private fun calculateSpan(): Int {
        return (requireContext().displayMetrics().widthPixels.toFloat() / requireContext().dpToPixels(SPAN)).roundToInt()
    }

    private fun setupRecyclerView() = binding?.recyclerView?.apply {
        recycler = Recycler.adopt(this) {
            stableId(Show::id)
            row<Show, View> {
                bind(ItemShowPosterBinding::inflate) { show ->
                    posterImageView.loadPoster(show.posterPath)
                    posterImageView.setOnClickListener {
                        navigate(show.id)
                    }
                }
            }
        }
        addItemDecoration(SpacingItemDecoration.GridLayout(requireContext().dpToPixels(SPACING)))
    }

    private fun observe() {
        lifecycleScope.launch {
            viewModel.addedShowList.collect {
                recycler?.update { data = it.toDataSource() }
            }
        }
    }

    private fun navigate(showId: Long) {
        findNavController().navigate(GlobalActions.actionDetails(showId))
    }
}
