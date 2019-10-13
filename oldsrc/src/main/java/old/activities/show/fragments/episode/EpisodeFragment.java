package jdr.tvtracker.activities.show.fragments.episode;

import android.app.AlertDialog.Builder;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import jdr.tvtracker.R;
import jdr.tvtracker.activities.show.ShowActivityViewModel;
import jdr.tvtracker.activities.show.fragments.episode.EpisodeFragmentAdapter.OnItemClickListener;
import jdr.tvtracker.data.entities.Episode;
import jdr.tvtracker.data.entities.Season;
import jdr.tvtracker.data.entities.Show;
import jdr.tvtracker.utils.SpacingDecoration;

public class EpisodeFragment extends Fragment {
    private EpisodeFragmentAdapter episodeFragmentAdapter;
    private int seasonNumberIndex = 0;
    private List<Season> seasons = new ArrayList();
    private Show show;
    private ShowActivityViewModel showActivityViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.default_fragment, container, false);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getArguments() != null) {
            this.seasonNumberIndex = getArguments().getInt("seasonNumber", 0) - 1;
        }
        setupToolbar();
        if (getActivity() != null) {
            this.showActivityViewModel = (ShowActivityViewModel) ViewModelProviders.of(getActivity()).get(ShowActivityViewModel.class);
        }
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        this.episodeFragmentAdapter = new EpisodeFragmentAdapter(view.getContext(), listener());
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        int spacing = getResources().getDimensionPixelSize(R.dimen.margin);
        recyclerView.addItemDecoration(new SpacingDecoration(spacing, spacing));
        recyclerView.setAdapter(this.episodeFragmentAdapter);
        observeLiveData();
    }

    private OnItemClickListener listener() {
        return new OnItemClickListener() {
            public void onEpisodeClick(int position) {
                EpisodeFragment.this.updateButtons(position);
            }
        };
    }

    private void updateButtons(int position) {
        if (this.show.isFromDB()) {
            if (position != 0 && ((Season) this.seasons.get(this.seasonNumberIndex)).getWatchedEpisodeCount() == 0) {
                showEpisodeAlert(position);
            } else if (((Episode) ((Season) this.seasons.get(this.seasonNumberIndex)).getEpisodeList().get(position)).isWatched()) {
                ((Episode) ((Season) this.seasons.get(this.seasonNumberIndex)).getEpisodeList().get(position)).setWatched(false);
            } else {
                ((Episode) ((Season) this.seasons.get(this.seasonNumberIndex)).getEpisodeList().get(position)).setWatched(true);
            }
            this.showActivityViewModel.updateEpisodeList();
        } else if (getActivity() != null) {
            View findViewById = getActivity().findViewById(R.id.coordinatorLayout);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Add ");
            stringBuilder.append(this.show.getName());
            stringBuilder.append(" to Mark as Watched");
            Snackbar.make(findViewById, stringBuilder.toString(), 0).show();
        }
    }

    private void showEpisodeAlert(final int position) {
        new Builder(getActivity()).setTitle("Mark Previous Episodes?").setMessage("Do You Want to Mark All Previous Episodes as Watched?").setPositiveButton("Yes", new OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int id) {
                ((Season) EpisodeFragment.this.seasons.get(EpisodeFragment.this.seasonNumberIndex)).setWatchedEpisodeCount(position + 1);
                EpisodeFragment.this.showActivityViewModel.updateEpisodeList();
            }
        }).setNegativeButton("No", new OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int id) {
                ((Episode) ((Season) EpisodeFragment.this.seasons.get(EpisodeFragment.this.seasonNumberIndex)).getEpisodeList().get(position)).setWatched(true);
                EpisodeFragment.this.showActivityViewModel.updateEpisodeList();
            }
        }).create().show();
    }

    private void observeLiveData() {
        this.showActivityViewModel.getShow().observe(this, new Observer<Show>() {
            public void onChanged(@Nullable Show showObserved) {
                if (showObserved != null) {
                    EpisodeFragment.this.show = showObserved;
                    EpisodeFragment.this.episodeFragmentAdapter.updateShow(EpisodeFragment.this.show);
                }
            }
        });
        this.showActivityViewModel.getSeasons().observe(this, new Observer<List<Season>>() {
            public void onChanged(@Nullable List<Season> seasonsObserved) {
                if (seasonsObserved != null) {
                    EpisodeFragment.this.seasons = seasonsObserved;
                    EpisodeFragment.this.episodeFragmentAdapter.updateEpisodeList(((Season) EpisodeFragment.this.seasons.get(EpisodeFragment.this.seasonNumberIndex)).getEpisodeList());
                    EpisodeFragment.this.setupToolbar();
                }
            }
        });
    }

    private void setupToolbar() {
        if (getActivity() != null && this.show != null && this.seasons != null) {
            Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
            if (toolbar != null) {
                toolbar.setTitle(this.show.getName());
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Season ");
                stringBuilder.append(String.format(Locale.getDefault(), "%02d", new Object[]{Integer.valueOf(((Season) this.seasons.get(this.seasonNumberIndex)).getSeasonNumber())}));
                toolbar.setSubtitle(stringBuilder.toString());
            }
        }
    }
}
