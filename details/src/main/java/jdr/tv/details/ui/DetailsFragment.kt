package jdr.tv.details.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import jdr.tv.base.extensions.setupToolbar
import jdr.tv.data.onFailure
import jdr.tv.data.onLoading
import jdr.tv.data.onSuccess
import jdr.tv.details.R
import jdr.tv.details.di.inject
import jdr.tv.navigation.GlobalActions
import timber.log.Timber
import javax.inject.Inject

class DetailsFragment : Fragment(R.layout.fragment_details) {

    @Inject
    lateinit var viewModel: DetailsViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        inject()
        setupToolbar(R.id.toolbar, displayHomeAsUp = true)
        observe()
    }

    private fun observe() {
        viewModel.id = GlobalActions.ActionDetails.fromBundle(arguments).showId
        viewModel.selectDetailedShow().observe(viewLifecycleOwner, Observer { result ->
            result.onLoading { Timber.e("LOADING") }
                .onSuccess { Timber.e("$it") }
                .onFailure { Timber.e("FAILURE $it") }
        })
    }
}
