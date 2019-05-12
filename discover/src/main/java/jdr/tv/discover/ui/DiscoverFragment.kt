package jdr.tv.discover.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import jdr.tv.app.R.string
import jdr.tv.base.android.extensions.setupToolbar
import jdr.tv.discover.R

class DiscoverFragment : Fragment(R.layout.fragment_discover) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupToolbar(R.id.toolbar, string.discover)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == R.id.settings) {
            NavHostFragment.findNavController(this).navigate(jdr.tv.app.R.id.action_settings)
            true
        } else {
            super.onOptionsItemSelected(item)
        }
    }
}