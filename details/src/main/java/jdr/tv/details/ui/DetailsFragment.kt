package jdr.tv.details.ui

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import jdr.tv.base.Log
import jdr.tv.data.collectResource
import jdr.tv.data.onFailure
import jdr.tv.data.onLoading
import jdr.tv.data.onSuccess
import jdr.tv.details.R
import jdr.tv.details.di.inject
import jdr.tv.navigation.GlobalActions
import jdr.tv.ui.extensions.setupToolbar
import kotlinx.coroutines.launch
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
        lifecycleScope.launch {
            viewModel.id = GlobalActions.ActionDetails.fromBundle(arguments).showId
            viewModel.show.collectResource {
                onLoading { Log.i("LOADING") }
                onSuccess { Log.i("$it") }
                onFailure { Log.i("FAILURE $it") }
            }
        }
    }
}
