package jdr.tvtracker.activities.show.fragments.details;

import android.app.AlertDialog.Builder;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;

import jdr.tvtracker.R;
import jdr.tvtracker.activities.main.MainActivity;
import jdr.tvtracker.activities.show.ShowActivityViewModel;
import jdr.tvtracker.activities.show.fragments.details.DetailsFragmentAdapter.OnItemClickListener;
import jdr.tvtracker.activities.show.fragments.episode.EpisodeFragment;
import jdr.tvtracker.data.entities.Season;
import jdr.tvtracker.data.entities.Show;
import jdr.tvtracker.utils.SpacingDecoration;

public class DetailsFragment extends Fragment {
    private DetailsFragmentAdapter detailsFragmentAdapter;
    private ProgressBar progressBar;
    private RecyclerView recyclerView;
    private List<Season> seasons = new ArrayList();
    private Show show;
    private ShowActivityViewModel showActivityViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.default_fragment, container, false);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getActivity() != null) {
            this.showActivityViewModel = (ShowActivityViewModel) ViewModelProviders.of(getActivity()).get(ShowActivityViewModel.class);
            this.progressBar = (ProgressBar) getActivity().findViewById(R.id.progressBar);
            this.progressBar.setVisibility(0);
        }
        this.recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        this.detailsFragmentAdapter = new DetailsFragmentAdapter(view.getContext(), listener());
        this.recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        this.recyclerView.setItemAnimator(new DefaultItemAnimator());
        int spacing = getResources().getDimensionPixelSize(R.dimen.margin);
        this.recyclerView.addItemDecoration(new SpacingDecoration(spacing, spacing));
        this.recyclerView.setAdapter(this.detailsFragmentAdapter);
        this.recyclerView.setVisibility(4);
        observeLiveData();
    }

    private OnItemClickListener listener() {
        return new OnItemClickListener() {
            public void onShowButtonClick() {
                if (DetailsFragment.this.show.isFromDB()) {
                    DetailsFragment.this.showActivityViewModel.deleteShow();
                    DetailsFragment detailsFragment = DetailsFragment.this;
                    detailsFragment.startActivity(new Intent(detailsFragment.getActivity(), MainActivity.class));
                    return;
                }
                DetailsFragment.this.showActivityViewModel.addShowAndEpisodeList();
                if (DetailsFragment.this.getActivity() != null) {
                    View findViewById = DetailsFragment.this.getActivity().findViewById(R.id.coordinatorLayout);
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append(DetailsFragment.this.show.getName());
                    stringBuilder.append(" Added");
                    Snackbar.make(findViewById, stringBuilder.toString(), 0).show();
                }
            }

            public void onSeasonButtonClick(int position) {
                DetailsFragment.this.updateButtons(position);
            }

            public void onSeasonClick(int position) {
                DetailsFragment.this.displayEpisodeList(position);
            }
        };
    }

    private void updateButtons(int position) {
        if (this.show.isFromDB()) {
            boolean showWatched = false;
            for (Season season : this.seasons) {
                if (season.getWatchedEpisodeCount() != 0) {
                    showWatched = true;
                }
            }
            if (position == 0 || showWatched) {
                if (((Season) this.seasons.get(position)).getWatchedEpisodeCount() != 0) {
                    ((Season) this.seasons.get(position)).setWatchedEpisodeCount(0);
                } else if (((Season) this.seasons.get(position)).getSeasonNumber() == this.seasons.size() && this.show.isInProduction()) {
                    ((Season) this.seasons.get(position)).setWatchedEpisodeCount(((Season) this.seasons.get(position)).getAiredEpisodeCount());
                } else {
                    ((Season) this.seasons.get(position)).setWatchedEpisodeCount(((Season) this.seasons.get(position)).getEpisodeCount());
                }
                this.showActivityViewModel.updateEpisodeList();
            } else {
                showSeasonAlert(position);
            }
        } else if (getActivity() != null) {
            View findViewById = getActivity().findViewById(R.id.coordinatorLayout);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Add ");
            stringBuilder.append(this.show.getName());
            stringBuilder.append(" to Mark as Watched");
            Snackbar.make(findViewById, stringBuilder.toString(), 0).show();
        }
    }

    private void showSeasonAlert(final int position) {
        new Builder(getActivity()).setTitle("Mark Previous Seasons?").setMessage("Do You Want to Mark All Previous Seasons as Watched?").setPositiveButton("Yes", new OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int id) {
                for (int i = 0; i < position + 1; i++) {
                    if (((Season) DetailsFragment.this.seasons.get(i)).getSeasonNumber() == DetailsFragment.this.seasons.size() && DetailsFragment.this.show.isInProduction()) {
                        ((Season) DetailsFragment.this.seasons.get(i)).setWatchedEpisodeCount(((Season) DetailsFragment.this.seasons.get(i)).getAiredEpisodeCount());
                    } else {
                        ((Season) DetailsFragment.this.seasons.get(i)).setWatchedEpisodeCount(((Season) DetailsFragment.this.seasons.get(i)).getEpisodeCount());
                    }
                }
                DetailsFragment.this.showActivityViewModel.updateEpisodeList();
            }
        }).setNegativeButton("No", new OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int id) {
                ((Season) DetailsFragment.this.seasons.get(position)).setWatchedEpisodeCount(((Season) DetailsFragment.this.seasons.get(position)).getAiredEpisodeCount());
                DetailsFragment.this.showActivityViewModel.updateEpisodeList();
            }
        }).create().show();
    }

    private void displayEpisodeList(int position) {
        if (getActivity() != null) {
            Fragment fragment = new EpisodeFragment();
            Bundle bundle = new Bundle(1);
            bundle.putInt("seasonNumber", position + 1);
            fragment.setArguments(bundle);
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            transaction.setCustomAnimations(R.anim.fragment_enter_from_right, R.anim.fragment_exit_to_left, R.anim.fragment_enter_from_left, R.anim.fragment_exit_to_right);
            transaction.replace(R.id.frameLayout, fragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }
    }

    private void observeLiveData() {
        this.showActivityViewModel.getShow().observe(this, new Observer<Show>() {
            public void onChanged(@Nullable Show showObserved) {
                if (showObserved != null) {
                    DetailsFragment.this.show = showObserved;
                    DetailsFragment.this.detailsFragmentAdapter.updateShow(DetailsFragment.this.show);
                }
            }
        });
        this.showActivityViewModel.getSeasons().observe(this, new Observer<List<Season>>() {
            public void onChanged(@Nullable List<Season> seasonsObserved) {
                if (DetailsFragment.this.progressBar != null) {
                    DetailsFragment.this.progressBar.setVisibility(8);
                }
                if (seasonsObserved != null && seasonsObserved.size() > 0) {
                    DetailsFragment.this.seasons = seasonsObserved;
                    DetailsFragment.this.detailsFragmentAdapter.updateSeasons(DetailsFragment.this.seasons);
                    DetailsFragment.this.setupToolbar();
                    DetailsFragment.this.recyclerView.setVisibility(0);
                }
            }
        });
    }

    private void setupToolbar() {
        if (getActivity() != null && this.show != null) {
            Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
            if (toolbar != null) {
                toolbar.setTitle(this.show.getName());
                toolbar.setSubtitle("");
            }
        }
    }
}
