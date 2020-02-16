package jdr.tv.feature.watchlist.ui

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
import jdr.tv.common.ui.extensions.dpToPixels
import jdr.tv.common.ui.extensions.loadPoster
import jdr.tv.common.ui.extensions.setupToolbar
import jdr.tv.data.core.di.DataComponent
import jdr.tv.data.local.entities.EpisodeItem
import jdr.tv.feature.watchlist.R
import jdr.tv.feature.watchlist.databinding.FragmentWatchListBinding
import jdr.tv.feature.watchlist.databinding.ItemEpisodeBinding
import jdr.tv.feature.watchlist.di.inject
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class WatchListFragment @Inject constructor(private val component: DataComponent) : Fragment() {

    companion object {
        private const val SPACING = 8
    }

    private var binding: FragmentWatchListBinding? = null
    private var recycler: Recycler<EpisodeItem>? = null

    @Inject
    lateinit var viewModel: WatchListViewModel

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
        return FragmentWatchListBinding.inflate(inflater, container, false).run {
            binding = this
            root
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupToolbar(jdr.tv.common.ui.R.id.toolbar, jdr.tv.common.navigation.R.string.watch_list)
        setupRecyclerView()
        observe()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
        recycler = null
    }

    private fun setupRecyclerView() = binding?.recyclerView?.apply {
        recycler = Recycler.adopt(this) {
            stableId(EpisodeItem::id)
            row<EpisodeItem, View> {
                create { context ->
                    val binding = ItemEpisodeBinding.inflate(LayoutInflater.from(context))
                    view = binding.root
                    bind { episode ->
                        binding.posterImageView.loadPoster(episode.posterPath)
                        binding.showNameTextView.text = episode.showName
                        binding.detailsTextView.text = context.getString(R.string.episode_details_format, episode.seasonNumber, episode.episodeNumber)
                        binding.root.setOnClickListener {
                            navigate(episode.showId)
                        }
                    }
                }
            }
        }
        addItemDecoration(EpisodeItemDecoration(requireContext().dpToPixels(SPACING)))
    }

    private fun observe() {
        lifecycleScope.launch {
            viewModel.watchList.collect {
                recycler?.update { data = it.toDataSource() }
            }
        }
    }

    private fun navigate(showId: Long) {
        findNavController().navigate(GlobalActions.actionDetails(showId))
    }
}
