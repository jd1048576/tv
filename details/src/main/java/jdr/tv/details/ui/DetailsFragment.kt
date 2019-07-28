package jdr.tv.details.ui

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import jdr.tv.base.Log
import jdr.tv.data.onFailure
import jdr.tv.data.onLoading
import jdr.tv.data.onSuccess
import jdr.tv.details.R
import jdr.tv.details.di.inject
import jdr.tv.navigation.GlobalActions
import jdr.tv.ui.extensions.setupToolbar
import javax.inject.Inject

class DetailsFragment : Fragment(R.layout.fragment_details) {

    @Inject
    lateinit var viewModel: DetailsViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
        inject()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupToolbar(R.id.toolbar, displayHomeAsUp = true)
        observe()
    }

    private fun observe() {
        viewModel.id = GlobalActions.ActionDetails.fromBundle(arguments).showId
        viewModel.selectDetailedShow().observe(viewLifecycleOwner, Observer { result ->
            result.onLoading { Log.e("LOADING") }
                .onSuccess { Log.e("$it") }
                .onFailure { Log.e("FAILURE $it") }
        })
    }
}
