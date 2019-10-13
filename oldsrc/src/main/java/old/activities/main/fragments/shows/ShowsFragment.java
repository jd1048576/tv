package jdr.tvtracker.activities.main.fragments.shows;

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
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import jdr.tvtracker.R;
import jdr.tvtracker.activities.main.MainActivityViewModel;
import jdr.tvtracker.data.entities.ShowBasic;
import jdr.tvtracker.utils.SpacingDecoration;

public class ShowsFragment extends Fragment {
    private MainActivityViewModel mainActivityViewModel;
    private ShowsFragmentAdapter showsFragmentAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.default_fragment, container, false);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getActivity() != null) {
            this.mainActivityViewModel = (MainActivityViewModel) ViewModelProviders.of(getActivity()).get(MainActivityViewModel.class);
        }
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        this.showsFragmentAdapter = new ShowsFragmentAdapter(view.getContext());
        recyclerView.setLayoutManager(new GridLayoutManager(view.getContext(), calculateNumberOfColumns(view.getContext())));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        int spacing = getResources().getDimensionPixelSize(R.dimen.margin);
        recyclerView.addItemDecoration(new SpacingDecoration(spacing, spacing));
        recyclerView.setAdapter(this.showsFragmentAdapter);
        observeLiveData();
    }

    private int calculateNumberOfColumns(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        int columnCount = (int) ((((float) displayMetrics.widthPixels) / displayMetrics.density) / ((float) 160));
        return columnCount > 2 ? columnCount : 2;
    }

    private void observeLiveData() {
        this.mainActivityViewModel.getShowList().observe(this, new Observer<List<ShowBasic>>() {
            public void onChanged(@Nullable List<ShowBasic> showList) {
                if (showList != null && showList.size() > 0) {
                    ShowsFragment.this.showsFragmentAdapter.updateShowsList(showList);
                }
            }
        });
    }
}
