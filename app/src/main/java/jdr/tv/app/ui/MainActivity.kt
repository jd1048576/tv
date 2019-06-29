package jdr.tv.app.ui

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView
import jdr.tv.app.NavGraphActivityMainDirections
import jdr.tv.app.R

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private lateinit var controller: NavController
    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        controller = Navigation.findNavController(this, R.id.activity_main_host_fragment)
        bottomNavigationView = findViewById(R.id.activity_main_bottom_navigation)
        bottomNavigationView.setOnNavigationItemSelectedListener {
            if (it.itemId != controller.currentDestination?.id) {
                NavigationUI.onNavDestinationSelected(it, controller)
                true
            } else false
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return controller.navigateUp() || super.onSupportNavigateUp()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_main_search -> {
                controller.navigate(NavGraphActivityMainDirections.actionSearch())
                true
            }
            R.id.menu_main_settings -> {
                controller.navigate(NavGraphActivityMainDirections.actionSettings())
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}