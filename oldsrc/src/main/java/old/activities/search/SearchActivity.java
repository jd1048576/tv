package jdr.tvtracker.activities.search;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import jdr.tvtracker.R;
import jdr.tvtracker.activities.search.fragment.SearchFragment;

public class SearchActivity extends AppCompatActivity {
    public static final String ACTION_TOP_RATED = "jdr.tvtracker.TOP_RATED";
    public static final String ACTION_TRENDING = "jdr.tvtracker.TRENDING";
    public static final String ACTION_UPCOMING = "jdr.tvtracker.UPCOMING";
    private SearchActivityViewModel searchActivityViewModel;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.default_activity);
        setupToolbar();
        this.searchActivityViewModel = (SearchActivityViewModel) ViewModelProviders.of(this).get(SearchActivityViewModel.class);
        if (savedInstanceState == null) {
            setupFragment(getIntent());
        }
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() != 16908332) {
            return super.onOptionsItemSelected(item);
        }
        onBackPressed();
        return true;
    }

    private void setupToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    private void setupFragment(Intent intent) {
        if ("android.intent.action.SEARCH".equals(intent.getAction()) || ACTION_TOP_RATED.equals(intent.getAction()) || ACTION_TRENDING.equals(intent.getAction()) || ACTION_UPCOMING.equals(intent.getAction())) {
            if ("android.intent.action.SEARCH".equals(intent.getAction())) {
                this.searchActivityViewModel.setMode(intent.getStringExtra("query"));
            } else if (ACTION_TOP_RATED.equals(intent.getAction())) {
                this.searchActivityViewModel.setMode(1);
            } else if (ACTION_TRENDING.equals(intent.getAction())) {
                this.searchActivityViewModel.setMode(2);
            } else {
                this.searchActivityViewModel.setMode(3);
            }
        }
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameLayout, new SearchFragment());
        transaction.commit();
    }
}
