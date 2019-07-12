package jdr.tv.settings.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import jdr.tv.base.extensions.setupToolbar
import jdr.tv.settings.R

class SettingsFragment : Fragment(R.layout.fragment_settings) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupToolbar(jdr.tv.ui.R.id.toolbar, jdr.tv.navigation.R.string.settings, true)
        if (savedInstanceState == null) {
            childFragmentManager.beginTransaction().replace(R.id.fragment_settings_frame_layout, PreferenceFragment()).commit()
        }
    }

    class PreferenceFragment : PreferenceFragmentCompat() {

        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.preferences, rootKey)
        }

        override fun onDisplayPreferenceDialog(preference: Preference) {
            super.onDisplayPreferenceDialog(preference)
            if ("THEME" == preference.key) {
                preference.setOnPreferenceChangeListener { _, value ->
                    AppCompatDelegate.setDefaultNightMode((value as? String)?.toInt() ?: -1)
                    true
                }
            }
        }
    }
}
