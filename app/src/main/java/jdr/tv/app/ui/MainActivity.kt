package jdr.tv.app.ui

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView
import jdr.tv.app.R
import jdr.tv.common.navigation.GlobalActions
import jdr.tv.data.di.DataComponentFragmentFactory
import jdr.tv.data.di.dataComponent

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private lateinit var controller: NavController
    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        supportFragmentManager.fragmentFactory = DataComponentFragmentFactory(dataComponent())
        super.onCreate(savedInstanceState)
        controller = Navigation.findNavController(this, R.id.activity_main_host_fragment)
        bottomNavigationView = findViewById(R.id.activity_main_bottom_navigation)
        NavigationUI.setupWithNavController(bottomNavigationView, controller)
    }

    override fun onSupportNavigateUp(): Boolean {
        return controller.navigateUp() || super.onSupportNavigateUp()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_main_search -> {
                controller.navigate(GlobalActions.actionSearch())
                true
            }
            R.id.menu_main_settings -> {
                controller.navigate(GlobalActions.actionSettings())
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
