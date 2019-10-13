package jdr.tvtracker.activities.search.fragment;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import jdr.tvtracker.R;
import jdr.tvtracker.activities.search.SearchActivityViewModel;
import jdr.tvtracker.data.entities.Show;
import jdr.tvtracker.utils.SpacingDecoration;

public class SearchFragment extends Fragment {
    private ProgressBar progressBar;
    private SearchActivityViewModel searchActivityViewModel;
    private SearchFragmentAdapter searchFragmentAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.default_fragment, container, false);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getActivity() != null) {
            this.searchActivityViewModel = (SearchActivityViewModel) ViewModelProviders.of(getActivity()).get(SearchActivityViewModel.class);
            setupToolbar(this.searchActivityViewModel.getMODE());
            this.progressBar = (ProgressBar) getActivity().findViewById(R.id.progressBar);
            this.progressBar.setVisibility(0);
        }
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        this.searchFragmentAdapter = new SearchFragmentAdapter(view.getContext());
        GridLayoutManager gridLayoutManager = new GridLayoutManager(view.getContext(), calculateNumberOfColumns(view.getContext()));
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        int spacing = getResources().getDimensionPixelSize(R.dimen.margin);
        recyclerView.addItemDecoration(new SpacingDecoration(spacing, spacing));
        recyclerView.setAdapter(this.searchFragmentAdapter);
        recyclerView.addOnScrollListener(scrollListener(gridLayoutManager));
        observeLiveData();
    }

    private void setupToolbar(int MODE) {
        if (getActivity() != null) {
            Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
            if (toolbar != null) {
                int title = 0;
                switch (MODE) {
                    case 0:
                        title = R.string.search_results;
                        break;
                    case 1:
                        title = R.string.top_rated;
                        break;
                    case 2:
                        title = R.string.trending;
                        break;
                    case 3:
                        title = R.string.upcoming;
                        break;
                    default:
                        break;
                }
                if (title != 0) {
                    toolbar.setTitle(title);
                }
            }
        }
    }

    private int calculateNumberOfColumns(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return ((int) ((((float) displayMetrics.widthPixels) / displayMetrics.density) / ((float) 320))) > 1 ? 2 : 1;
    }

    private SearchFragmentEndlessRecyclerViewScrollListener scrollListener(GridLayoutManager gridLayoutManager) {
        return new SearchFragmentEndlessRecyclerViewScrollListener(gridLayoutManager) {
            public void onLoadMore(int totalItemsCount, RecyclerView view) {
                SearchFragment.this.searchActivityViewModel.get();
            }
        };
    }

    private void observeLiveData() {
        this.searchActivityViewModel.getShowList().observe(this, new Observer<List<Show>>() {
            public void onChanged(@Nullable List<Show> showList) {
                if (SearchFragment.this.progressBar != null) {
                    SearchFragment.this.progressBar.setVisibility(8);
                }
                if (showList != null && showList.size() > 0) {
                    SearchFragment.this.searchFragmentAdapter.updateShowList(new ArrayList(showList));
                } else if (SearchFragment.this.getActivity() != null) {
                    Toolbar toolbar = (Toolbar) SearchFragment.this.getActivity().findViewById(R.id.toolbar);
                    TextView textView = (TextView) SearchFragment.this.getActivity().findViewById(R.id.textView);
                    if (toolbar != null && toolbar.getTitle() != null && toolbar.getTitle().equals(SearchFragment.this.getString(R.string.search_results)) && textView != null) {
                        textView.setText(R.string.no_search_results);
                        textView.setVisibility(0);
                    }
                }
            }
        });
    }
}
