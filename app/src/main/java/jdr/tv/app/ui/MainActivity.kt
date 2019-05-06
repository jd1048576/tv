package jdr.tv.app.ui

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView
import jdr.tv.R

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private lateinit var controller: NavController
    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        controller = findNavController(R.id.activity_main_host_fragment)
        bottomNavigationView = findViewById(R.id.activity_main_bottom_navigation)
        bottomNavigationView.setOnNavigationItemSelectedListener {
            if (it.itemId != controller.currentDestination?.id) {
                NavigationUI.onNavDestinationSelected(it, controller)
                true
            } else false
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                controller.navigateUp()
                true
            }
            /*   R.id.activity_main_menu_search -> {
                   controller.navigate(R.id.action_search)
                   true
               }
               R.id.activity_main_menu_settings -> {
                   controller.navigate(R.id.action_settings)
                   true
               }*/
            else -> super.onOptionsItemSelected(item)
        }
    }
}