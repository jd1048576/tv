package jdr.tv.shows.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.fragment.app.Fragment
import jdr.tv.base.extensions.setupToolbar
import jdr.tv.shows.R

class ShowsFragment : Fragment(R.layout.fragment_shows) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupToolbar(jdr.tv.ui.R.id.toolbar, jdr.tv.ui.R.string.shows)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(jdr.tv.ui.R.menu.menu_main, menu)
    }
}