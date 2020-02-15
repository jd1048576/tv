package jdr.tv.feature.details.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import jdr.tv.feature.details.databinding.FragmentBaseBinding

class SeasonsFragment : Fragment() {

    private var binding: FragmentBaseBinding? = null

    private val viewModel: DetailsViewModel by viewModels(::requireParentFragment)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentBaseBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}
