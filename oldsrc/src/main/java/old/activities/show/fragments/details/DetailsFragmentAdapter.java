package jdr.tvtracker.activities.show.fragments.details;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.card.MaterialCardView;
import android.support.design.chip.Chip;
import android.support.design.chip.ChipGroup;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import jdr.tvtracker.R;
import jdr.tvtracker.data.entities.Episode;
import jdr.tvtracker.data.entities.Genres;
import jdr.tvtracker.data.entities.Network;
import jdr.tvtracker.data.entities.Season;
import jdr.tvtracker.data.entities.Show;
import jdr.tvtracker.data.entities.ShowBasic;
import jdr.tvtracker.utils.CircularProgress;
import jdr.tvtracker.utils.DateUtil;
import jdr.tvtracker.utils.GlideApp;
import jdr.tvtracker.utils.GlideRequest;
import jdr.tvtracker.utils.GlideRequests;
import jdr.tvtracker.utils.IntentSender;
import jdr.tvtracker.utils.SpacingDecoration;

class DetailsFragmentAdapter extends Adapter<ViewHolder> {
    private static final int TYPE_FOOTER = 2;
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_SEASON = 1;
    private boolean added = true;
    private final Context context;
    private final OnItemClickListener listener;
    private List<Season> seasons = new ArrayList();
    private Show show;

    class DetailsViewHolder extends ViewHolder {
        final ImageView add;
        final ImageView backdrop;
        final CircularProgress circularProgress;
        final TextView date;
        final ConstraintLayout detailsConstraintLayout;
        final ImageView expand;
        final TextView facebook;
        final ChipGroup genresChipGroup;
        final TextView instagram;
        final TextView network;
        final TextView overview;
        final RecyclerView recyclerView;
        final TextView runtime;
        final TextView status;
        final TextView tmdb;
        final TextView twitter;
        final TextView voteCount;
        final TextView website;

        DetailsViewHolder(View view) {
            super(view);
            this.backdrop = (ImageView) view.findViewById(R.id.detailsFragmentHeaderBackdropImageView);
            this.add = (ImageView) view.findViewById(R.id.detailsFragmentHeaderAddImageView);
            this.expand = (ImageView) view.findViewById(R.id.detailsFragmentHeaderExpandImageView);
            this.detailsConstraintLayout = (ConstraintLayout) view.findViewById(R.id.detailsFragmentHeaderConstraintLayout);
            this.overview = (TextView) view.findViewById(R.id.detailsFragmentHeaderOverviewTextView);
            this.circularProgress = (CircularProgress) view.findViewById(R.id.detailsFragmentHeaderCircularProgress);
            this.voteCount = (TextView) view.findViewById(R.id.detailsFragmentHeaderVoteCountTextView);
            this.date = (TextView) view.findViewById(R.id.detailsFragmentHeaderDateTextView);
            this.status = (TextView) view.findViewById(R.id.detailsFragmentHeaderStatusTextView);
            this.runtime = (TextView) view.findViewById(R.id.detailsFragmentHeaderRuntimeTextView);
            this.network = (TextView) view.findViewById(R.id.detailsFragmentHeaderNetworkTextView);
            this.genresChipGroup = (ChipGroup) view.findViewById(R.id.detailsFragmentHeaderGenreChipGroup);
            this.recyclerView = (RecyclerView) view.findViewById(R.id.detailsFragmentHeaderRecyclerView);
            this.tmdb = (TextView) view.findViewById(R.id.detailsFragmentHeaderTMDbTextView);
            this.website = (TextView) view.findViewById(R.id.detailsFragmentHeaderWebsiteTextView);
            this.facebook = (TextView) view.findViewById(R.id.detailsFragmentHeaderFacebookTextView);
            this.twitter = (TextView) view.findViewById(R.id.detailsFragmentHeaderTwitterTextView);
            this.instagram = (TextView) view.findViewById(R.id.detailsFragmentHeaderInstagramTextView);
        }
    }

    class FooterViewHolder extends ViewHolder {
        final MaterialCardView cardView;
        final RecyclerView recyclerView;
        final TextView textView1;
        final TextView textView2;

        FooterViewHolder(View view) {
            super(view);
            this.cardView = (MaterialCardView) view.findViewById(R.id.detailsFragmentFooterCardView);
            this.textView1 = (TextView) view.findViewById(R.id.detailsFragmentFooterTextView1);
            this.textView2 = (TextView) view.findViewById(R.id.detailsFragmentFooterTextView2);
            this.recyclerView = (RecyclerView) view.findViewById(R.id.detailsFragmentFooterRecyclerView);
        }
    }

    public interface OnItemClickListener {
        void onSeasonButtonClick(int i);

        void onSeasonClick(int i);

        void onShowButtonClick();
    }

    class SeasonViewHolder extends ViewHolder {
        final MaterialCardView cardView;
        final ImageView check;
        final TextView days;
        final TextView episodeCount;
        final ProgressBar progressBar;
        final TextView seasonNumber;

        SeasonViewHolder(View view) {
            super(view);
            this.cardView = (MaterialCardView) view.findViewById(R.id.detailsFragmentSeasonViewHolderCardView);
            this.seasonNumber = (TextView) view.findViewById(R.id.detailsFragmentSeasonViewHolderSeasonNumberTextView);
            this.episodeCount = (TextView) view.findViewById(R.id.detailsFragmentSeasonViewHolderEpisodeCountTextView);
            this.days = (TextView) view.findViewById(R.id.detailsFragmentSeasonViewHolderDaysTextView);
            this.progressBar = (ProgressBar) view.findViewById(R.id.detailsFragmentSeasonViewHolderEpisodeProgressBar);
            this.check = (ImageView) view.findViewById(R.id.detailsFragmentSeasonViewHolderCheckImageView);
        }
    }

    DetailsFragmentAdapter(Context context, OnItemClickListener listener) {
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == 0) {
            return new DetailsViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_show_fragment_details_header_update, parent, false));
        }
        if (viewType == 1) {
            return new SeasonViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_show_fragment_details_season_view_holder, parent, false));
        }
        return new FooterViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_show_fragment_details_footer_update, parent, false));
    }

    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DetailsFragmentAdapter detailsFragmentAdapter = this;
        ViewHolder viewHolder = holder;
        if (viewHolder instanceof DetailsViewHolder) {
            final DetailsViewHolder detailsViewHolder = (DetailsViewHolder) viewHolder;
            if (detailsFragmentAdapter.show != null) {
                StringBuilder date;
                String str;
                GlideRequests with = GlideApp.with(detailsFragmentAdapter.context);
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(detailsFragmentAdapter.context.getString(R.string.high_res_backdrop_url));
                stringBuilder.append(detailsFragmentAdapter.show.getBackdropPath());
                GlideRequest placeholder = with.load(stringBuilder.toString()).placeholder((int) R.drawable.ic_placeholder_landscape);
                GlideRequests with2 = GlideApp.with(detailsFragmentAdapter.context);
                StringBuilder stringBuilder2 = new StringBuilder();
                stringBuilder2.append(detailsFragmentAdapter.context.getString(R.string.low_res_backdrop_url));
                stringBuilder2.append(detailsFragmentAdapter.show.getBackdropPath());
                placeholder.thumbnail(with2.load(stringBuilder2.toString())).into(detailsViewHolder.backdrop);
                detailsViewHolder.add.setOnClickListener(new OnClickListener() {
                    public void onClick(View view) {
                        DetailsFragmentAdapter.this.listener.onShowButtonClick();
                    }
                });
                if (detailsFragmentAdapter.show.isFromDB()) {
                    detailsViewHolder.add.setImageDrawable(detailsFragmentAdapter.context.getDrawable(R.drawable.ic_add));
                    if (detailsFragmentAdapter.added) {
                        detailsViewHolder.add.setRotation(45.0f);
                        detailsViewHolder.expand.setRotation(90.0f);
                        detailsViewHolder.detailsConstraintLayout.setVisibility(8);
                    } else {
                        detailsFragmentAdapter.added = true;
                        detailsViewHolder.add.animate().rotation(45.0f).setDuration(320);
                        detailsViewHolder.expand.animate().rotation(90.0f).setDuration(320);
                        collapse(detailsViewHolder.detailsConstraintLayout);
                    }
                } else {
                    detailsFragmentAdapter.added = false;
                    detailsViewHolder.add.setImageDrawable(detailsFragmentAdapter.context.getDrawable(R.drawable.ic_add));
                    detailsViewHolder.expand.setRotation(-90.0f);
                    detailsViewHolder.detailsConstraintLayout.setVisibility(0);
                }
                detailsViewHolder.expand.setOnClickListener(new OnClickListener() {
                    public void onClick(View view) {
                        if (detailsViewHolder.detailsConstraintLayout.getVisibility() == 0) {
                            detailsViewHolder.expand.animate().rotation(90.0f).setDuration(320);
                            DetailsFragmentAdapter.this.collapse(detailsViewHolder.detailsConstraintLayout);
                            return;
                        }
                        detailsViewHolder.expand.animate().rotation(-90.0f).setDuration(320);
                        DetailsFragmentAdapter.this.expand(detailsViewHolder.detailsConstraintLayout);
                    }
                });
                detailsViewHolder.overview.setText(detailsFragmentAdapter.show.getOverview());
                detailsViewHolder.circularProgress.setValue(detailsFragmentAdapter.show.getVoteAverage());
                String voteCount = new StringBuilder();
                voteCount.append(detailsFragmentAdapter.show.getVoteCount());
                voteCount.append(" Votes");
                detailsViewHolder.voteCount.setText(voteCount.toString());
                detailsViewHolder.status.setText(detailsFragmentAdapter.show.getStatus());
                String runtime = new StringBuilder();
                runtime.append(detailsFragmentAdapter.show.getEpisodeRuntime());
                runtime.append("m");
                detailsViewHolder.runtime.setText(runtime.toString());
                if (detailsFragmentAdapter.show.getNetworkList() != null && detailsFragmentAdapter.show.getNetworkList().size() > 0) {
                    detailsViewHolder.network.setText(((Network) detailsFragmentAdapter.show.getNetworkList().get(0)).getName());
                }
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy", Locale.getDefault());
                if (detailsFragmentAdapter.show.isInProduction()) {
                    date = new StringBuilder();
                    date.append(sdf.format(detailsFragmentAdapter.show.getFirstAirDate()));
                    str = " - ";
                } else {
                    date = new StringBuilder();
                    date.append(sdf.format(detailsFragmentAdapter.show.getFirstAirDate()));
                    date.append(" - ");
                    str = sdf.format(detailsFragmentAdapter.show.getLastAirDate());
                }
                date.append(str);
                detailsViewHolder.date.setText(date.toString());
                if (detailsViewHolder.genresChipGroup.getChildCount() == 0) {
                    List<Genres> genresList = detailsFragmentAdapter.show.getGenresList();
                    if (genresList != null && genresList.size() > 0) {
                        for (int i = 0; i < genresList.size(); i++) {
                            Chip genreChip = (Chip) View.inflate(detailsFragmentAdapter.context, R.layout.chip, null);
                            genreChip.setChipText(((Genres) genresList.get(i)).getName());
                            detailsViewHolder.genresChipGroup.addView(genreChip, i);
                        }
                    }
                }
                if (detailsViewHolder.recyclerView.getAdapter() == null && detailsFragmentAdapter.show.getCast() != null && detailsFragmentAdapter.show.getCast().size() > 0) {
                    DetailsFragmentHeaderAdapter detailsFragmentHeaderAdapter = new DetailsFragmentHeaderAdapter(detailsFragmentAdapter.context, detailsFragmentAdapter.show.getCast());
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(detailsFragmentAdapter.context, 0, false);
                    linearLayoutManager.setInitialPrefetchItemCount(8);
                    detailsViewHolder.recyclerView.setLayoutManager(linearLayoutManager);
                    detailsViewHolder.recyclerView.setHasFixedSize(true);
                    detailsViewHolder.recyclerView.setItemAnimator(new DefaultItemAnimator());
                    detailsViewHolder.recyclerView.addItemDecoration(new SpacingDecoration(detailsFragmentAdapter.context.getResources().getDimensionPixelSize(R.dimen.margin), 0));
                    detailsViewHolder.recyclerView.setAdapter(detailsFragmentHeaderAdapter);
                }
                final IntentSender intentSender = new IntentSender(detailsFragmentAdapter.context);
                detailsViewHolder.tmdb.setOnClickListener(new OnClickListener() {
                    public void onClick(View view) {
                        IntentSender intentSender = intentSender;
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("https://www.themoviedb.org/tv/");
                        stringBuilder.append(DetailsFragmentAdapter.this.show.getId());
                        intentSender.openWebPage(stringBuilder.toString());
                    }
                });
                if (detailsFragmentAdapter.show.getHomepage() != null) {
                    detailsViewHolder.website.setVisibility(0);
                    detailsViewHolder.website.setOnClickListener(new OnClickListener() {
                        public void onClick(View view) {
                            intentSender.openWebPage(DetailsFragmentAdapter.this.show.getHomepage());
                        }
                    });
                }
                if (detailsFragmentAdapter.show.getExternalIds() != null) {
                    if (detailsFragmentAdapter.show.getExternalIds().getFacebookId() != null) {
                        detailsViewHolder.facebook.setVisibility(0);
                        detailsViewHolder.facebook.setOnClickListener(new OnClickListener() {
                            public void onClick(View view) {
                                IntentSender intentSender = intentSender;
                                StringBuilder stringBuilder = new StringBuilder();
                                stringBuilder.append("https://www.facebook.com/");
                                stringBuilder.append(DetailsFragmentAdapter.this.show.getExternalIds().getFacebookId());
                                intentSender.openWebPage(stringBuilder.toString());
                            }
                        });
                    }
                    if (detailsFragmentAdapter.show.getExternalIds().getTwitterId() != null) {
                        detailsViewHolder.twitter.setVisibility(0);
                        detailsViewHolder.twitter.setOnClickListener(new OnClickListener() {
                            public void onClick(View view) {
                                IntentSender intentSender = intentSender;
                                StringBuilder stringBuilder = new StringBuilder();
                                stringBuilder.append("https://www.twitter.com/");
                                stringBuilder.append(DetailsFragmentAdapter.this.show.getExternalIds().getTwitterId());
                                intentSender.openWebPage(stringBuilder.toString());
                            }
                        });
                    }
                    if (detailsFragmentAdapter.show.getExternalIds().getInstagramId() != null) {
                        detailsViewHolder.instagram.setVisibility(0);
                        detailsViewHolder.instagram.setOnClickListener(new OnClickListener() {
                            public void onClick(View view) {
                                IntentSender intentSender = intentSender;
                                StringBuilder stringBuilder = new StringBuilder();
                                stringBuilder.append("https://www.instagram.com/");
                                stringBuilder.append(DetailsFragmentAdapter.this.show.getExternalIds().getInstagramId());
                                intentSender.openWebPage(stringBuilder.toString());
                            }
                        });
                    }
                }
            }
        } else if (viewHolder instanceof SeasonViewHolder) {
            final SeasonViewHolder seasonViewHolder = (SeasonViewHolder) viewHolder;
            Season season = (Season) detailsFragmentAdapter.seasons.get(position - 1);
            if (season.getSeasonNumber() == detailsFragmentAdapter.show.getNumberOfSeasons() && season.getEpisodeCount() == 0) {
                seasonViewHolder.progressBar.setVisibility(8);
                seasonViewHolder.episodeCount.setVisibility(8);
                seasonViewHolder.check.setVisibility(8);
                seasonViewHolder.days.setText(new DateUtil().getNumberOfDaysBetweenString(detailsFragmentAdapter.show.getFirstAirDate()));
                seasonViewHolder.days.setVisibility(0);
            } else {
                seasonViewHolder.cardView.setOnClickListener(new OnClickListener() {
                    public void onClick(View view) {
                        DetailsFragmentAdapter.this.listener.onSeasonClick(seasonViewHolder.getAdapterPosition() - 1);
                    }
                });
                seasonViewHolder.progressBar.setVisibility(0);
                seasonViewHolder.episodeCount.setVisibility(0);
                if (season.getEpisodeList() == null || season.getEpisodeList().size() <= 0 || ((Episode) season.getEpisodeList().get(0)).getAirDate().getTime() <= Calendar.getInstance().getTimeInMillis()) {
                    seasonViewHolder.check.setVisibility(0);
                    seasonViewHolder.days.setVisibility(8);
                    if (season.getEpisodeCount() != season.getWatchedEpisodeCount()) {
                        if (season.getSeasonNumber() != detailsFragmentAdapter.seasons.size() || !detailsFragmentAdapter.show.isInProduction() || season.getAiredEpisodeCount() != season.getWatchedEpisodeCount()) {
                            seasonViewHolder.check.setSelected(false);
                        }
                    }
                    seasonViewHolder.check.setSelected(true);
                } else {
                    seasonViewHolder.check.setVisibility(8);
                    seasonViewHolder.days.setText(new DateUtil().getNumberOfDaysBetweenString(((Episode) season.getEpisodeList().get(0)).getAirDate()));
                    seasonViewHolder.days.setVisibility(0);
                }
                seasonViewHolder.check.setOnClickListener(new OnClickListener() {
                    public void onClick(View view) {
                        DetailsFragmentAdapter.this.listener.onSeasonButtonClick(seasonViewHolder.getAdapterPosition() - 1);
                    }
                });
            }
            String seasonNumberString = new StringBuilder();
            seasonNumberString.append("Season ");
            seasonNumberString.append(season.getSeasonNumber());
            seasonViewHolder.seasonNumber.setText(seasonNumberString.toString());
            String episodeCountString = new StringBuilder();
            episodeCountString.append(season.getWatchedEpisodeCount());
            episodeCountString.append("/");
            episodeCountString.append(season.getEpisodeCount());
            seasonViewHolder.episodeCount.setText(episodeCountString.toString());
            seasonViewHolder.progressBar.setMax(season.getEpisodeCount());
            seasonViewHolder.progressBar.setProgress(season.getWatchedEpisodeCount());
        } else if (viewHolder instanceof FooterViewHolder) {
            final FooterViewHolder footerViewHolder = (FooterViewHolder) viewHolder;
            if (footerViewHolder.recyclerView.getAdapter() == null) {
                List<ShowBasic> recommendationList = new ArrayList();
                List<ShowBasic> similarList = new ArrayList();
                Show show = detailsFragmentAdapter.show;
                if (show != null) {
                    if (show.getRecommendationList() != null && detailsFragmentAdapter.show.getSimilarList() != null) {
                        recommendationList = detailsFragmentAdapter.show.getRecommendationList();
                        footerViewHolder.textView1.setText(R.string.recommended);
                        footerViewHolder.textView1.setSelected(true);
                        similarList = detailsFragmentAdapter.show.getSimilarList();
                        footerViewHolder.textView2.setText(R.string.similar);
                        footerViewHolder.textView2.setSelected(false);
                    } else if (detailsFragmentAdapter.show.getSimilarList() != null) {
                        similarList = detailsFragmentAdapter.show.getSimilarList();
                        footerViewHolder.textView1.setText(R.string.similar);
                        footerViewHolder.textView1.setSelected(true);
                    } else {
                        footerViewHolder.cardView.setVisibility(8);
                    }
                }
                final DetailsFragmentFooterAdapter detailsFragmentFooterAdapter = new DetailsFragmentFooterAdapter(detailsFragmentAdapter.context, recommendationList, similarList);
                LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(detailsFragmentAdapter.context, 0, false);
                linearLayoutManager2.setInitialPrefetchItemCount(8);
                footerViewHolder.recyclerView.setLayoutManager(linearLayoutManager2);
                footerViewHolder.recyclerView.setItemAnimator(new DefaultItemAnimator());
                footerViewHolder.recyclerView.addItemDecoration(new SpacingDecoration(detailsFragmentAdapter.context.getResources().getDimensionPixelSize(R.dimen.margin), 0));
                footerViewHolder.recyclerView.setAdapter(detailsFragmentFooterAdapter);
                if (recommendationList.size() > 0) {
                    footerViewHolder.textView1.setOnClickListener(new OnClickListener() {
                        public void onClick(View view) {
                            if (detailsFragmentFooterAdapter.getMode() == 2) {
                                footerViewHolder.textView1.setSelected(true);
                                footerViewHolder.textView2.setSelected(false);
                                detailsFragmentFooterAdapter.switchMode(1);
                                footerViewHolder.recyclerView.scrollToPosition(0);
                            }
                        }
                    });
                    footerViewHolder.textView2.setOnClickListener(new OnClickListener() {
                        public void onClick(View view) {
                            if (detailsFragmentFooterAdapter.getMode() == 1) {
                                footerViewHolder.textView2.setSelected(true);
                                footerViewHolder.textView1.setSelected(false);
                                detailsFragmentFooterAdapter.switchMode(2);
                                footerViewHolder.recyclerView.scrollToPosition(0);
                            }
                        }
                    });
                }
            }
        }
    }

    public int getItemViewType(int position) {
        if (position == 0) {
            return 0;
        }
        if (position == this.seasons.size() + 1) {
            return 2;
        }
        return 1;
    }

    public int getItemCount() {
        return this.seasons.size() == 0 ? 1 : this.seasons.size() + 2;
    }

    void updateShow(Show show) {
        this.show = show;
    }

    void updateSeasons(List<Season> seasons) {
        this.seasons = seasons;
        notifyDataSetChanged();
    }

    private void expand(final View view) {
        view.measure(MeasureSpec.makeMeasureSpec(((View) view.getParent()).getWidth(), 1073741824), MeasureSpec.makeMeasureSpec(0, 0));
        final int targetHeight = view.getMeasuredHeight();
        view.getLayoutParams().height = 1;
        view.setVisibility(0);
        Animation animation = new Animation() {
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                if (interpolatedTime == 1.0f) {
                    view.getLayoutParams().height = -2;
                } else {
                    view.getLayoutParams().height = (int) (((float) targetHeight) * interpolatedTime);
                }
                view.requestLayout();
            }

            public boolean willChangeBounds() {
                return true;
            }
        };
        animation.setInterpolator(new AccelerateDecelerateInterpolator());
        animation.setDuration(320);
        view.startAnimation(animation);
    }

    private void collapse(final View view) {
        final int initialHeight = view.getMeasuredHeight();
        Animation animation = new Animation() {
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                if (interpolatedTime == 1.0f) {
                    view.setVisibility(8);
                    return;
                }
                LayoutParams layoutParams = view.getLayoutParams();
                int i = initialHeight;
                layoutParams.height = i - ((int) (((float) i) * interpolatedTime));
                view.requestLayout();
            }

            public boolean willChangeBounds() {
                return true;
            }
        };
        animation.setInterpolator(new AccelerateDecelerateInterpolator());
        animation.setDuration(320);
        view.startAnimation(animation);
    }
}
