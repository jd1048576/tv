package jdr.tv.discover.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import jdr.tv.base.android.extensions.setupToolbar
import jdr.tv.discover.R
import jdr.tv.app.R as app

class DiscoverFragment : Fragment(R.layout.fragment_discover) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupToolbar(R.id.fragment_discover_toolbar, app.string.discover)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == R.id.settings) {
            NavHostFragment.findNavController(this).navigate(app.id.action_settings)
            true
        } else {
            super.onOptionsItemSelected(item)
        }
    }
}