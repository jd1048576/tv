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
import jdr.tv.common.extensions.toFormattedString
import jdr.tv.common.ui.extensions.dpToPixels
import jdr.tv.common.ui.utils.SpacingItemDecoration
import jdr.tv.data.local.entities.DetailedShow
import jdr.tv.feature.details.R
import jdr.tv.feature.details.databinding.FragmentBaseBinding
import jdr.tv.feature.details.databinding.ItemShowDetailsOverviewBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class ShowDetailsFragment : Fragment() {

    companion object {
        const val SPACING = 16
    }

    private var binding: FragmentBaseBinding? = null
    private var recycler: Recycler<DetailedShow>? = null

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
            row<DetailedShow, View> {
                create { context ->
                    val binding = ItemShowDetailsOverviewBinding.inflate(LayoutInflater.from(context))
                    view = binding.root
                    bind { detailedShow ->
                        binding.overviewTextView.text =
                            if (detailedShow.show.overview.isEmpty()) context.getString(R.string.overview_unavailable) else detailedShow.show.overview
                        binding.lastUpdatedTextView.text = detailedShow.details.detailsUpdatedAt.toFormattedString()
                    }
                }
            }
        }
        addItemDecoration(SpacingItemDecoration.LinearLayout(requireContext().dpToPixels(SPACING)))
    }

    private fun observe() {
        lifecycleScope.launch {
            viewModel.detailedShow.collect {
                recycler?.update { data = listOf(it).toDataSource() }
            }
        }
    }
}
