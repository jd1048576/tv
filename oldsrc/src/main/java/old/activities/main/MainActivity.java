package jdr.tvtracker.activities.main;

import android.app.SearchManager;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.BottomNavigationView.OnNavigationItemSelectedListener;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnFocusChangeListener;

import java.util.Calendar;

import jdr.tvtracker.R;
import jdr.tvtracker.activities.main.fragments.episodeItem.EpisodeItemFragment;
import jdr.tvtracker.activities.main.fragments.shows.ShowsFragment;
import jdr.tvtracker.activities.search.SearchActivity;
import jdr.tvtracker.activities.settings.SettingsActivity;
import jdr.tvtracker.data.DBUpdate;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView navigation;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        ViewModelProviders.of(this).get(MainActivityViewModel.class);
        this.navigation = (BottomNavigationView) findViewById(R.id.bottomNavigationView);
        this.navigation.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener());
        if (savedInstanceState == null) {
            Fragment fragment = new EpisodeItemFragment();
            Bundle bundle = new Bundle(1);
            bundle.putInt("MODE", 0);
            fragment.setArguments(bundle);
            loadFragment(fragment);
        }
    }

    protected void onStart() {
        super.onStart();
        SharedPreferences SP = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        if (Calendar.getInstance().getTimeInMillis() - SP.getLong("last_updated", 0) > Long.parseLong(SP.getString("update_interval", "24")) * 3600000) {
            DBUpdate.startActionDBUpdate(this);
        }
    }

    public boolean onCreateOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main_menu, menu);
        SearchManager searchManager = (SearchManager) getSystemService("search");
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        final MenuItem searchItem = menu.findItem(R.id.search);
        if (searchManager != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
            searchView.setBackground(getDrawable(R.drawable.style_search_view_background));
            searchView.setMaxWidth(Integer.MAX_VALUE);
            searchView.setOnQueryTextFocusChangeListener(new OnFocusChangeListener() {
                public void onFocusChange(View view, boolean focused) {
                    if (focused) {
                        MainActivity.this.setItemsVisibility(menu, searchItem, false);
                        return;
                    }
                    MainActivity.this.setItemsVisibility(menu, searchItem, true);
                    searchItem.collapseActionView();
                }
            });
        }
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        int itemId = item.getItemId();
        if (itemId == R.id.settings) {
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        } else if (itemId == R.id.topRated) {
            topRatedIntent = new Intent(this, SearchActivity.class);
            topRatedIntent.setAction(SearchActivity.ACTION_TOP_RATED);
            startActivity(topRatedIntent);
            return true;
        } else if (itemId == R.id.trending) {
            topRatedIntent = new Intent(this, SearchActivity.class);
            topRatedIntent.setAction(SearchActivity.ACTION_TRENDING);
            startActivity(topRatedIntent);
            return true;
        } else if (itemId != R.id.upcoming) {
            return false;
        } else {
            topRatedIntent = new Intent(this, SearchActivity.class);
            topRatedIntent.setAction(SearchActivity.ACTION_UPCOMING);
            startActivity(topRatedIntent);
            return true;
        }
    }

    private OnNavigationItemSelectedListener onNavigationItemSelectedListener() {
        return new OnNavigationItemSelectedListener() {
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (MainActivity.this.navigation.getSelectedItemId() != item.getItemId()) {
                    Bundle bundle = new Bundle(1);
                    switch (item.getItemId()) {
                        case R.id.navigation_schedule:
                            Fragment fragment = new EpisodeItemFragment();
                            bundle.putInt("MODE", 0);
                            fragment.setArguments(bundle);
                            MainActivity.this.loadFragment(fragment);
                            return true;
                        case R.id.navigation_shows:
                            MainActivity.this.loadFragment(new ShowsFragment());
                            return true;
                        case R.id.navigation_watch_list:
                            Fragment fragment2 = new EpisodeItemFragment();
                            bundle.putInt("MODE", 1);
                            fragment2.setArguments(bundle);
                            MainActivity.this.loadFragment(fragment2);
                            return true;
                        default:
                            break;
                    }
                }
                return false;
            }
        };
    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameLayout, fragment);
        transaction.commit();
    }

    private void setItemsVisibility(Menu menu, MenuItem exception, boolean visible) {
        for (int i = 0; i < menu.size(); i++) {
            MenuItem item = menu.getItem(i);
            if (item != exception) {
                item.setVisible(visible);
            }
        }
    }
}
