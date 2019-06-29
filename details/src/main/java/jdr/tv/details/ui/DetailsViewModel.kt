package jdr.tv.details.ui

import jdr.tv.viewmodel.StateViewModel

class DetailsViewModel(private val repository: DetailsRepository) : StateViewModel<DetailsViewState>(DetailsViewState())