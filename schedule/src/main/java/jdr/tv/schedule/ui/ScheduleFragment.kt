package jdr.tv.schedule.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.fragment.app.Fragment
import jdr.tv.base.extensions.setupToolbar
import jdr.tv.schedule.R

class ScheduleFragment : Fragment(R.layout.fragment_schedule) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupToolbar(R.id.fragment_schedule_toolbar, jdr.tv.ui.R.string.schedule)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(jdr.tv.ui.R.menu.menu_main, menu)
    }
}