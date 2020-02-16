package jdr.tv.feature.details.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.squareup.cycler.Recycler
import com.squareup.cycler.toDataSource
import jdr.tv.common.ui.extensions.bind
import jdr.tv.common.ui.extensions.dpToPixels
import jdr.tv.common.ui.utils.SpacingItemDecoration
import jdr.tv.data.local.entities.DetailedSeason
import jdr.tv.feature.details.R
import jdr.tv.feature.details.databinding.FragmentBaseBinding
import jdr.tv.feature.details.databinding.ItemSeasonBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class SeasonsFragment : Fragment() {

    companion object {
        const val SPACING = 16
    }

    private var binding: FragmentBaseBinding? = null
    private var recycler: Recycler<DetailedSeason>? = null

    private val viewModel: DetailsViewModel by viewModels(::requireParentFragment)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return FragmentBaseBinding.inflate(inflater, container, false).run {
            binding = this
            root
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
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
            stableId { s -> s.season.id }
            row<DetailedSeason, View> {
                bind(ItemSeasonBinding::inflate) { season ->
                    val watchCount = season.watchCount
                    val episodeCount = season.episodeList.size
                    seasonNumberTextView.text = context.getString(R.string.season_format, season.season.seasonNumber)
                    watchedTextView.text = context.getString(R.string.season_count_format, watchCount, episodeCount)
                    checkImageView.isSelected = watchCount == episodeCount
                    checkImageView.setOnClickListener {
                        viewModel.updateSeasonWatched(season)
                    }
                    seasonProgressBar.progress = watchCount
                    seasonProgressBar.max = episodeCount
                }
            }
        }
        addItemDecoration(SpacingItemDecoration.LinearLayout(requireContext().dpToPixels(SPACING)))
    }

    private fun observe() {
        lifecycleScope.launch {
            viewModel.detailedSeasonList.collect {
                recycler?.update { data = it.toDataSource() }
            }
        }
    }
}
