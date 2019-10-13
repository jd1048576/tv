package jdr.tvtracker.activities.main.fragments.episodeItem;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout.LayoutParams;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.support.v7.widget.helper.ItemTouchHelper.SimpleCallback;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import jdr.tvtracker.R;
import jdr.tvtracker.activities.main.MainActivityViewModel;
import jdr.tvtracker.activities.main.fragments.episodeItem.EpisodeItemAdapter.OnItemClickListener;
import jdr.tvtracker.data.entities.Episode;
import jdr.tvtracker.utils.SpacingDecoration;

public class EpisodeItemFragment extends Fragment {
    public static final int MODE_SCHEDULE = 0;
    public static final int MODE_WATCH_LIST = 1;
    private int MODE;
    private EpisodeItemAdapter episodeItemAdapter;
    private MainActivityViewModel mainActivityViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.default_fragment, container, false);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getActivity() != null) {
            this.mainActivityViewModel = (MainActivityViewModel) ViewModelProviders.of(getActivity()).get(MainActivityViewModel.class);
        }
        if (getArguments() != null) {
            this.MODE = getArguments().getInt("MODE", 0);
        }
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        this.episodeItemAdapter = new EpisodeItemAdapter(view.getContext(), this.MODE, onItemClickListener());
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        int spacing = getResources().getDimensionPixelSize(R.dimen.margin);
        recyclerView.addItemDecoration(new SpacingDecoration(spacing, spacing));
        recyclerView.setAdapter(this.episodeItemAdapter);
        itemTouchHelper().attachToRecyclerView(recyclerView);
        observeLiveData();
    }

    public void onStart() {
        super.onStart();
        if (this.MODE == 0) {
            this.mainActivityViewModel.addScheduleListSource();
        } else {
            this.mainActivityViewModel.addWatchListSource();
        }
    }

    private OnItemClickListener onItemClickListener() {
        return new OnItemClickListener() {
            public void onWatched(Episode episode) {
                EpisodeItemFragment.this.updateEpisode(episode);
            }
        };
    }

    private ItemTouchHelper itemTouchHelper() {
        return new ItemTouchHelper(new SimpleCallback(0, 12) {
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull ViewHolder viewHolder, @NonNull ViewHolder target) {
                return false;
            }

            public void onSwiped(@NonNull ViewHolder viewHolder, int direction) {
                if (viewHolder instanceof EpisodeItemViewHolder) {
                    EpisodeItemFragment.this.episodeItemAdapter.updateEpisode(viewHolder.getAdapterPosition());
                }
            }

            public int getSwipeDirs(@NonNull RecyclerView recyclerView, @NonNull ViewHolder viewHolder) {
                if ((viewHolder instanceof EpisodeItemViewHolder) && ((EpisodeItemViewHolder) viewHolder).swipe) {
                    return super.getSwipeDirs(recyclerView, viewHolder);
                }
                return 0;
            }
        });
    }

    private void observeLiveData() {
        if (this.MODE == 0) {
            this.mainActivityViewModel.getScheduleList().observe(this, new Observer<List<Item>>() {
                public void onChanged(@Nullable List<Item> itemList) {
                    if (itemList != null && itemList.size() > 0) {
                        EpisodeItemFragment.this.episodeItemAdapter.updateItemList(itemList);
                    }
                }
            });
        } else {
            this.mainActivityViewModel.getWatchList().observe(this, new Observer<List<Item>>() {
                public void onChanged(@Nullable List<Item> itemList) {
                    if (itemList != null && itemList.size() > 0) {
                        EpisodeItemFragment.this.episodeItemAdapter.updateItemList(itemList);
                    }
                }
            });
        }
    }

    private void updateEpisode(Episode episode) {
        episode.setWatched(episode.isWatched() ^ 1);
        this.mainActivityViewModel.updateEpisode(episode);
        if (getActivity() != null && episode.isWatched()) {
            Snackbar snackbar = Snackbar.make(getActivity().findViewById(R.id.coordinatorLayout), "Episode Marked as Watched", 0);
            LayoutParams params = (LayoutParams) snackbar.getView().getLayoutParams();
            params.setAnchorId(R.id.bottomNavigationView);
            params.anchorGravity = 49;
            params.gravity = 49;
            snackbar.getView().setLayoutParams(params);
            snackbar.show();
        }
    }
}
