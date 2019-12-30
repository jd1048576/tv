package jdr.tv.details.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import jdr.tv.details.R

class SeasonsFragment : Fragment(R.layout.fragment_base) {

    private lateinit var viewModel: DetailsViewModel

    private lateinit var recyclerView: RecyclerView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(parentFragment!!, ViewModelProvider.NewInstanceFactory()).get(DetailsViewModel::class.java)
        bindResources()
    }

    private fun bindResources() = with(view!!) {
        recyclerView = findViewById(R.id.recycler_view)
    }
}
