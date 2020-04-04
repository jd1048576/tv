package jdr.tv.feature.schedule.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView.Adapter.StateRestorationPolicy
import com.squareup.cycler.Recycler
import com.squareup.cycler.toDataSource
import jdr.tv.common.navigation.GlobalActions
import jdr.tv.common.ui.extensions.bind
import jdr.tv.common.ui.extensions.dpToPixels
import jdr.tv.common.ui.extensions.loadPoster
import jdr.tv.common.ui.extensions.setupToolbar
import jdr.tv.common.ui.utils.TopBottomItemDecoration
import jdr.tv.data.core.di.DataComponent
import jdr.tv.feature.schedule.R
import jdr.tv.feature.schedule.databinding.FragmentScheduleBinding
import jdr.tv.feature.schedule.databinding.ItemScheduleEpisodeBinding
import jdr.tv.feature.schedule.databinding.ItemTitleBinding
import jdr.tv.feature.schedule.di.inject
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class ScheduleFragment @Inject constructor(private val component: DataComponent) : Fragment() {

    companion object {
        private const val SPACING = 8
    }

    private var binding: FragmentScheduleBinding? = null
    private var recycler: Recycler<Model>? = null

    @Inject
    lateinit var viewModelProviderFactory: ViewModelProvider.Factory
    private val viewModel: ScheduleViewModel by viewModels { viewModelProviderFactory }

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
        return FragmentScheduleBinding.inflate(inflater, container, false).run {
            binding = this
            root
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupToolbar(jdr.tv.common.ui.R.id.toolbar, jdr.tv.common.navigation.R.string.schedule)
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
            stableId(Model::id)
            row<TitleModel, View> {
                bind(ItemTitleBinding::inflate) { item ->
                    title.text = item.title
                }
            }
            row<EpisodeModel, View> {
                bind(ItemScheduleEpisodeBinding::inflate) { item ->
                    posterImageView.loadPoster(item.episode.posterPath)
                    showNameTextView.text = item.episode.showName
                    detailsTextView.text = context.getString(R.string.episode_details_format, item.episode.seasonNumber, item.episode.episodeNumber)
                    root.setOnClickListener {
                        navigate(item.episode.showId)
                    }
                }
            }
        }
        adapter?.stateRestorationPolicy = StateRestorationPolicy.PREVENT_WHEN_EMPTY
        addItemDecoration(TopBottomItemDecoration(requireContext().dpToPixels(SPACING)))
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
