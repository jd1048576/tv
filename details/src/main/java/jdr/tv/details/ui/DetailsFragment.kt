package jdr.tv.details.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import jdr.tv.base.extensions.setupToolbar
import jdr.tv.details.R
import jdr.tv.details.di.inject
import javax.inject.Inject

class DetailsFragment : Fragment(R.layout.fragment_details) {

    @Inject
    lateinit var viewModel: DetailsViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        inject()
        setupToolbar(R.id.toolbar, displayHomeAsUp = true)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.restore(savedInstanceState)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        viewModel.save(outState)
    }
}
