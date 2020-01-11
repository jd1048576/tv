package jdr.tv.feature.details.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import jdr.tv.feature.details.R

class SeasonsFragment : Fragment(R.layout.fragment_base) {

    private val viewModel: DetailsViewModel by viewModels(::requireParentFragment)

    private lateinit var recyclerView: RecyclerView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        bindResources()
    }

    private fun bindResources() = with(view!!) {
        recyclerView = findViewById(R.id.recycler_view)
    }
}
