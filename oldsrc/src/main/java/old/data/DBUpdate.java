package jdr.tvtracker.data;

import android.app.IntentService;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Build.VERSION;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat.Builder;
import android.support.v7.preference.PreferenceManager;
import android.util.SparseArray;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import jdr.tvtracker.R;
import jdr.tvtracker.data.entities.Episode;
import jdr.tvtracker.data.entities.Season;
import jdr.tvtracker.data.entities.Show;
import jdr.tvtracker.data.remote.TMDbInterface;
import jdr.tvtracker.data.remote.entities.SeasonsResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DBUpdate extends IntentService {
    private static final String ACTION_DB_UPDATE = "jdr.tvtracker.action.UPDATE";
    private Map<Integer, SparseArray<Episode>> episodeSparseArrayMapToUpdate;
    private final StringBuilder log = new StringBuilder();
    private final SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
    private int showIndex = 0;
    private List<Show> showListToUpdate;
    private jdr.tvtracker.data.ShowRepository showRepository;
    private boolean showUpdated;
    private long startTime;
    private TMDbInterface tmDbInterface;

    public DBUpdate() {
        super("DBUpdate");
    }

    public static void startActionDBUpdate(Context context) {
        Intent intent = new Intent(context, DBUpdate.class);
        intent.setAction(ACTION_DB_UPDATE);
        context.startService(intent);
    }

    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            String action = intent.getAction();
            ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService("connectivity");
            SharedPreferences SP = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
            boolean mobileData = SP.getBoolean("mobile_data", false);
            if (ACTION_DB_UPDATE.equals(action) && connectivityManager != null && connectivityManager.getActiveNetwork() != null) {
                if (connectivityManager.getNetworkCapabilities(connectivityManager.getActiveNetwork()).hasTransport(1) || mobileData) {
                    SP.edit().putLong("last_updated", Calendar.getInstance().getTimeInMillis()).apply();
                    handleActionDBUpdate();
                }
            }
        }
    }

    private void handleActionDBUpdate() {
        this.showRepository = new jdr.tvtracker.data.ShowRepository(getApplicationContext());
        this.tmDbInterface = this.showRepository.getTMDbInterface();
        this.showListToUpdate = this.showRepository.getShowListToUpdate();
        this.episodeSparseArrayMapToUpdate = this.showRepository.getEpisodeSparseArrayMapToUpdate();
        this.startTime = System.currentTimeMillis();
        this.log.append("LAST UPDATED ");
        this.log.append(new SimpleDateFormat("dd-MM-yyyy 'AT' HH:mm:ss", Locale.getDefault()).format(Calendar.getInstance().getTime()));
        this.log.append("\n\n");
        getShowToCheck();
    }

    private void getShowToCheck() {
        notification();
        if (this.showIndex < this.showListToUpdate.size()) {
            this.tmDbInterface.getShow(((Show) this.showListToUpdate.get(this.showIndex)).getId()).enqueue(new Callback<Show>() {
                public void onResponse(@NonNull Call<Show> call, @NonNull Response<Show> response) {
                    Show show = (Show) response.body();
                    if (show != null) {
                        show.setFields();
                        DBUpdate.this.updateShow(show);
                        return;
                    }
                    DBUpdate.this.updateShow(null);
                }

                public void onFailure(@NonNull Call<Show> call, @NonNull Throwable t) {
                    t.printStackTrace();
                    DBUpdate.this.updateShow(null);
                }
            });
        } else {
            PreferenceManager.getDefaultSharedPreferences(this).edit().putString("log", this.log.toString()).apply();
        }
    }

    private void updateShow(Show show) {
        Show showToUpdate = (Show) this.showListToUpdate.get(this.showIndex);
        this.showUpdated = false;
        this.log.append(showToUpdate.getName());
        if (!(show == null || show.equals(showToUpdate))) {
            this.log.append("\n  Details");
            if (!(show.getName() == null || show.getName().equals(showToUpdate.getName()))) {
                this.log.append("\n    Name: ");
                this.log.append(showToUpdate.getName());
                this.log.append(" ➔ ");
                this.log.append(show.getName());
                showToUpdate.setName(show.getName());
                this.showUpdated = true;
            }
            if (show.getNumberOfSeasons() != showToUpdate.getNumberOfSeasons()) {
                this.log.append("\n    Number of Seasons: ");
                this.log.append(showToUpdate.getNumberOfSeasons());
                this.log.append(" ➔ ");
                this.log.append(show.getNumberOfSeasons());
                showToUpdate.setNumberOfSeasons(show.getNumberOfSeasons());
                this.showUpdated = true;
            }
            if (show.getNumberOfEpisodes() != showToUpdate.getNumberOfEpisodes()) {
                this.log.append("\n    Number of Episodes: ");
                this.log.append(showToUpdate.getNumberOfEpisodes());
                this.log.append(" ➔ ");
                this.log.append(show.getNumberOfEpisodes());
                showToUpdate.setNumberOfEpisodes(show.getNumberOfEpisodes());
                this.showUpdated = true;
            }
            if (show.getEpisodeRuntime() != showToUpdate.getEpisodeRuntime()) {
                this.log.append("\n    Episode Runtime: ");
                this.log.append(showToUpdate.getEpisodeRuntime());
                this.log.append(" ➔ ");
                this.log.append(show.getEpisodeRuntime());
                showToUpdate.setEpisodeRuntime(show.getEpisodeRuntime());
                this.showUpdated = true;
            }
            if (show.isInProduction() != showToUpdate.isInProduction()) {
                this.log.append("\n    In Production: ");
                this.log.append(showToUpdate.isInProduction());
                this.log.append(" ➔ ");
                this.log.append(show.isInProduction());
                showToUpdate.setInProduction(show.isInProduction());
                this.showUpdated = true;
            }
            if (!(show.getStatus() == null || show.getStatus().equals(showToUpdate.getStatus()))) {
                this.log.append("\n    Status: ");
                this.log.append(showToUpdate.getStatus());
                this.log.append(" ➔ ");
                this.log.append(show.getStatus());
                showToUpdate.setStatus(show.getStatus());
                this.showUpdated = true;
            }
            if (!(show.getType() == null || show.getType().equals(showToUpdate.getType()))) {
                this.log.append("\n    Type: ");
                this.log.append(showToUpdate.getType());
                this.log.append(" ➔ ");
                this.log.append(show.getType());
                showToUpdate.setType(show.getType());
                this.showUpdated = true;
            }
            if (!(show.getFirstAirDate() == null || show.getFirstAirDate().equals(showToUpdate.getFirstAirDate()))) {
                this.log.append("\n    First Air Date: ");
                this.log.append(this.sdf.format(showToUpdate.getFirstAirDate()));
                this.log.append(" ➔ ");
                this.log.append(this.sdf.format(show.getFirstAirDate()));
                showToUpdate.setFirstAirDate(show.getFirstAirDate());
                this.showUpdated = true;
            }
            if (!(show.getLastAirDate() == null || show.getLastAirDate().equals(showToUpdate.getLastAirDate()))) {
                this.log.append("\n    Last Air Date: ");
                this.log.append(this.sdf.format(showToUpdate.getLastAirDate()));
                this.log.append(" ➔ ");
                this.log.append(this.sdf.format(show.getLastAirDate()));
                showToUpdate.setLastAirDate(show.getLastAirDate());
                this.showUpdated = true;
            }
            if (Double.compare(show.getPopularity(), showToUpdate.getPopularity()) != 0) {
                this.log.append("\n    Popularity: ");
                this.log.append(showToUpdate.getPopularity());
                this.log.append(" ➔ ");
                this.log.append(show.getPopularity());
                showToUpdate.setPopularity(show.getPopularity());
                this.showUpdated = true;
            }
            if (Double.compare(show.getVoteAverage(), showToUpdate.getVoteAverage()) != 0) {
                this.log.append("\n    Vote Average: ");
                this.log.append(showToUpdate.getVoteAverage());
                this.log.append(" ➔ ");
                this.log.append(show.getVoteAverage());
                showToUpdate.setVoteAverage(show.getVoteAverage());
                this.showUpdated = true;
            }
            if (show.getVoteCount() != showToUpdate.getVoteCount()) {
                this.log.append("\n    Vote Count: ");
                this.log.append(showToUpdate.getVoteCount());
                this.log.append(" ➔ ");
                this.log.append(show.getVoteCount());
                showToUpdate.setVoteCount(show.getVoteCount());
                this.showUpdated = true;
            }
            if (!(show.getOverview() == null || show.getOverview().equals(showToUpdate.getOverview()))) {
                this.log.append("\n    Overview Updated");
                showToUpdate.setOverview(show.getOverview());
                this.showUpdated = true;
            }
            if (!(show.getPosterPath() == null || !show.getPosterPath().contains(".jpg") || show.getPosterPath().equals(showToUpdate.getPosterPath()))) {
                this.log.append("\n    Poster Updated");
                showToUpdate.setPosterPath(show.getPosterPath());
                this.showUpdated = true;
            }
            if (!(show.getBackdropPath() == null || !show.getBackdropPath().contains(".jpg") || show.getBackdropPath().equals(showToUpdate.getBackdropPath()))) {
                this.log.append("\n    Backdrop Updated");
                showToUpdate.setBackdropPath(show.getBackdropPath());
                this.showUpdated = true;
            }
            if (!(show.getHomepage() == null || show.getHomepage().equals(showToUpdate.getHomepage()))) {
                this.log.append("\n    Homepage: ");
                this.log.append(showToUpdate.getHomepage());
                this.log.append(" ➔ ");
                this.log.append(show.getHomepage());
                showToUpdate.setHomepage(show.getHomepage());
                this.showUpdated = true;
            }
            if (!(show.getOriginCountry() == null || show.getOriginCountry().equals(showToUpdate.getOriginCountry()))) {
                this.log.append("\n    Origin Country: ");
                this.log.append(showToUpdate.getOriginCountry());
                this.log.append(" ➔ ");
                this.log.append(show.getOriginCountry());
                showToUpdate.setOriginCountry(show.getOriginCountry());
                this.showUpdated = true;
            }
            if (!(show.getOriginalLanguage() == null || show.getOriginalLanguage().equals(showToUpdate.getOriginalLanguage()))) {
                this.log.append("\n    Original Language: ");
                this.log.append(showToUpdate.getOriginalLanguage());
                this.log.append(" ➔ ");
                this.log.append(show.getOriginalLanguage());
                showToUpdate.setOriginalLanguage(show.getOriginalLanguage());
                this.showUpdated = true;
            }
            if (show.getCreatedByList() != null && (showToUpdate.getCreatedByList() == null || !show.getCreatedByList().equals(showToUpdate.getCreatedByList()))) {
                this.log.append("\n    Created By List Updated");
                showToUpdate.setCreatedByList(show.getCreatedByList());
                this.showUpdated = true;
            }
            if (show.getGenresList() != null && (showToUpdate.getGenresList() == null || !show.getGenresList().equals(showToUpdate.getGenresList()))) {
                this.log.append("\n    Genres Updated");
                showToUpdate.setGenresList(show.getGenresList());
                this.showUpdated = true;
            }
            if (show.getNetworkList() != null && (showToUpdate.getNetworkList() == null || !show.getNetworkList().equals(showToUpdate.getNetworkList()))) {
                this.log.append("\n    Network List Updated");
                showToUpdate.setNetworkList(show.getNetworkList());
                this.showUpdated = true;
            }
            if (show.getProductionCompanyList() != null && (showToUpdate.getProductionCompanyList() == null || !show.getProductionCompanyList().equals(showToUpdate.getProductionCompanyList()))) {
                this.log.append("\n    Production Company List Updated");
                showToUpdate.setProductionCompanyList(show.getProductionCompanyList());
                this.showUpdated = true;
            }
            if (show.getCast() != null && (showToUpdate.getCast() == null || !show.getCast().equals(showToUpdate.getCast()))) {
                this.log.append("\n    Cast Updated");
                showToUpdate.setCast(show.getCast());
                this.showUpdated = true;
            }
            if (show.getContentRatingList() != null && (showToUpdate.getContentRatingList() == null || !show.getContentRatingList().equals(showToUpdate.getContentRatingList()))) {
                this.log.append("\n    Content Rating List Updated");
                showToUpdate.setContentRatingList(show.getContentRatingList());
                this.showUpdated = true;
            }
            if (show.getExternalIds() != null && (showToUpdate.getExternalIds() == null || !show.getExternalIds().equals(showToUpdate.getExternalIds()))) {
                this.log.append("\n    External ID Updated");
                this.log.append("\n      IMDB - ");
                this.log.append(showToUpdate.getExternalIds().getImdbId());
                this.log.append(" ➔ ");
                this.log.append(show.getExternalIds().getImdbId());
                this.log.append("\n      Tvdb - ");
                this.log.append(showToUpdate.getExternalIds().getTvdbId());
                this.log.append(" ➔ ");
                this.log.append(show.getExternalIds().getTvdbId());
                this.log.append("\n      Facebook - ");
                this.log.append(showToUpdate.getExternalIds().getFacebookId());
                this.log.append(" ➔ ");
                this.log.append(show.getExternalIds().getFacebookId());
                this.log.append("\n      Twitter - ");
                this.log.append(showToUpdate.getExternalIds().getTwitterId());
                this.log.append(" ➔ ");
                this.log.append(show.getExternalIds().getTwitterId());
                this.log.append("\n      Instagram - ");
                this.log.append(showToUpdate.getExternalIds().getInstagramId());
                this.log.append(" ➔ ");
                this.log.append(show.getExternalIds().getInstagramId());
                showToUpdate.setExternalIds(show.getExternalIds());
                this.showUpdated = true;
            }
            if (show.getRecommendationList() != null && (showToUpdate.getRecommendationList() == null || !show.getRecommendationList().equals(showToUpdate.getRecommendationList()))) {
                this.log.append("\n    Recommendation List Updated");
                showToUpdate.setRecommendationList(show.getRecommendationList());
                this.showUpdated = true;
            }
            if (show.getSimilarList() != null && (showToUpdate.getSimilarList() == null || !show.getSimilarList().equals(showToUpdate.getSimilarList()))) {
                this.log.append("\n    Similar List Updated");
                showToUpdate.setSimilarList(show.getSimilarList());
                this.showUpdated = true;
            }
            if (show.getVideoList() != null && (showToUpdate.getVideoList() == null || !show.getVideoList().equals(showToUpdate.getVideoList()))) {
                this.log.append("\n    Video List Updated");
                showToUpdate.setVideoList(show.getVideoList());
                this.showUpdated = true;
            }
            if (this.showUpdated) {
                this.showRepository.updateShow(showToUpdate);
            }
        }
        getEpisodeListToCheck(showToUpdate.getId(), showToUpdate.getNumberOfSeasons());
    }

    private void getEpisodeListToCheck(final int showId, int numberOfSeasons) {
        final SparseArray<Episode> episodeSparseArray = new SparseArray();
        final int minSeasonNumber = numberOfSeasons > 20 ? numberOfSeasons - 19 : 1;
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = minSeasonNumber; i < numberOfSeasons + 1; i++) {
            stringBuilder.append("season/");
            stringBuilder.append(i);
            stringBuilder.append(",");
        }
        this.tmDbInterface.getSeasons(showId, stringBuilder.substring(0, stringBuilder.length() - 1)).enqueue(new Callback<SeasonsResponse>() {
            public void onResponse(@NonNull Call<SeasonsResponse> call, @NonNull Response<SeasonsResponse> response) {
                SeasonsResponse seasonsResponse = (SeasonsResponse) response.body();
                if (seasonsResponse != null) {
                    for (Season season : seasonsResponse.getSeasons()) {
                        for (Episode episode : season.getEpisodeList()) {
                            episodeSparseArray.put(episode.getId(), episode);
                        }
                    }
                    DBUpdate.this.updateEpisodeList(showId, minSeasonNumber, episodeSparseArray);
                    return;
                }
                DBUpdate.this.updateEpisodeList(showId, minSeasonNumber, null);
            }

            public void onFailure(@NonNull Call<SeasonsResponse> call, @NonNull Throwable t) {
                t.printStackTrace();
                DBUpdate.this.updateEpisodeList(showId, minSeasonNumber, null);
            }
        });
    }

    private void updateEpisodeList(int showId, int minSeasonNumber, SparseArray<Episode> episodeSparseArray) {
        String str;
        long diff;
        boolean episodeListUpdated = false;
        if (episodeSparseArray != null && episodeSparseArray.size() > 0) {
            Episode episodeToUpdate;
            SparseArray<Episode> episodeSparseArrayToUpdate = (SparseArray) this.episodeSparseArrayMapToUpdate.get(Integer.valueOf(showId));
            int i = 0;
            boolean episodeListUpdated2 = false;
            for (int i2 = 0; i2 < episodeSparseArrayToUpdate.size(); i2++) {
                episodeToUpdate = (Episode) episodeSparseArrayToUpdate.valueAt(i2);
                if (((Episode) episodeSparseArray.get(episodeToUpdate.getId())) == null && episodeToUpdate.getSeasonNumber() >= minSeasonNumber) {
                    if (!episodeListUpdated2) {
                        this.log.append("\n  Episode List");
                        episodeListUpdated2 = true;
                    }
                    this.log.append("\n    ⨯ S");
                    this.log.append(episodeToUpdate.getSeasonNumber());
                    this.log.append("E");
                    this.log.append(episodeToUpdate.getEpisodeNumber());
                    this.showRepository.deleteEpisode(episodeToUpdate);
                }
            }
            episodeListUpdated = episodeListUpdated2;
            while (i < episodeSparseArray.size()) {
                Episode episode = (Episode) episodeSparseArray.valueAt(i);
                episodeToUpdate = (Episode) episodeSparseArrayToUpdate.get(episode.getId());
                if (episodeToUpdate == null) {
                    if (!episodeListUpdated) {
                        this.log.append("\n  Episode List");
                        episodeListUpdated = true;
                    }
                    this.log.append("\n    + S");
                    this.log.append(episode.getSeasonNumber());
                    this.log.append("E");
                    this.log.append(episode.getEpisodeNumber());
                    this.showRepository.addEpisode(episode);
                } else if (!episode.equals(episodeToUpdate)) {
                    StringBuilder episodeLog = new StringBuilder();
                    if (!episodeListUpdated) {
                        episodeLog.append("\n  Episode List");
                        episodeListUpdated = true;
                    }
                    episodeLog.append("\n    S");
                    episodeLog.append(episodeToUpdate.getSeasonNumber());
                    episodeLog.append("E");
                    episodeLog.append(episodeToUpdate.getEpisodeNumber());
                    boolean episodeUpdated = false;
                    if (!(episode.getName() == null || episode.getName().equals(episodeToUpdate.getName()))) {
                        episodeLog.append("\n      Name: ");
                        episodeLog.append(episodeToUpdate.getName());
                        episodeLog.append(" ➔ ");
                        episodeLog.append(episode.getName());
                        episodeToUpdate.setName(episode.getName());
                        episodeUpdated = true;
                    }
                    if (!(episode.getAirDate() == null || episode.getAirDate().equals(episodeToUpdate.getAirDate()))) {
                        episodeLog.append("\n      Air Date: ");
                        episodeLog.append(this.sdf.format(episodeToUpdate.getAirDate()));
                        episodeLog.append(" ➔ ");
                        episodeLog.append(this.sdf.format(episode.getAirDate()));
                        episodeToUpdate.setAirDate(episode.getAirDate());
                        episodeUpdated = true;
                    }
                    if (!(episode.getOverview() == null || episode.getOverview().equals(episodeToUpdate.getOverview()))) {
                        episodeLog.append("\n      Overview Updated");
                        episodeToUpdate.setOverview(episode.getOverview());
                        episodeUpdated = true;
                    }
                    if (!(episode.getStillPath() == null || !episode.getStillPath().contains(".jpg") || episode.getStillPath().equals(episodeToUpdate.getStillPath()))) {
                        episodeLog.append("\n      Still Updated");
                        episodeToUpdate.setStillPath(episode.getStillPath());
                        episodeUpdated = true;
                    }
                    if (Double.compare(episode.getVoteAverage(), episodeToUpdate.getVoteAverage()) != 0) {
                        episodeLog.append("\n      Vote Average: ");
                        episodeLog.append(episodeToUpdate.getVoteAverage());
                        episodeLog.append(" ➔ ");
                        episodeLog.append(episode.getVoteAverage());
                        episodeToUpdate.setVoteAverage(episode.getVoteAverage());
                        episodeUpdated = true;
                    }
                    if (episode.getVoteCount() != episodeToUpdate.getVoteCount()) {
                        episodeLog.append("\n      Vote Count: ");
                        episodeLog.append(episodeToUpdate.getVoteCount());
                        episodeLog.append(" ➔ ");
                        episodeLog.append(episode.getVoteCount());
                        episodeToUpdate.setVoteCount(episode.getVoteCount());
                        episodeUpdated = true;
                    }
                    if (!(episode.getProductionCode() == null || episode.getProductionCode().equals(episodeToUpdate.getProductionCode()))) {
                        episodeLog.append("\n      Production Code: ");
                        episodeLog.append(episodeToUpdate.getProductionCode());
                        episodeLog.append(" ➔ ");
                        episodeLog.append(episode.getProductionCode());
                        episodeToUpdate.setProductionCode(episode.getProductionCode());
                        episodeUpdated = true;
                    }
                    if (episodeUpdated) {
                        this.showRepository.updateEpisode(episodeToUpdate);
                        this.log.append(episodeLog);
                    } else {
                        episodeListUpdated = false;
                    }
                }
                i++;
            }
        }
        StringBuilder stringBuilder = this.log;
        if (!episodeListUpdated) {
            if (!this.showUpdated) {
                str = " - No Updates\n\n";
                stringBuilder.append(str);
                this.showIndex++;
                diff = System.currentTimeMillis() - this.startTime;
                if (diff >= 500) {
                    new Handler().postDelayed(new Runnable() {
                        public void run() {
                            DBUpdate.this.startTime = System.currentTimeMillis();
                            DBUpdate.this.getShowToCheck();
                        }
                    }, 500 - diff);
                }
                this.startTime = System.currentTimeMillis();
                getShowToCheck();
                return;
            }
        }
        str = "\n\n";
        stringBuilder.append(str);
        this.showIndex++;
        diff = System.currentTimeMillis() - this.startTime;
        if (diff >= 500) {
            this.startTime = System.currentTimeMillis();
            getShowToCheck();
            return;
        }
        new Handler().postDelayed(/* anonymous class already generated */, 500 - diff);
    }

    private void notification() {
        NotificationManager notificationManager = (NotificationManager) getSystemService("notification");
        String CHANNEL_ID = "NOTIFICATION_CHANNEL_JDR_TVTRACKER_SHOW_UPDATES";
        if (notificationManager != null) {
            if (VERSION.SDK_INT >= 26) {
                notificationManager.createNotificationChannel(new NotificationChannel(CHANNEL_ID, "Show Updates", 3));
            }
            if (this.showIndex < this.showListToUpdate.size()) {
                Builder notification = new Builder(this, CHANNEL_ID).setSmallIcon(R.drawable.ic_tv_update).setColor(getResources().getColor(R.color.colorPrimary, null)).setContentTitle("Updating Shows").setPriority(0).setVisibility(1).setProgress(this.showListToUpdate.size(), this.showIndex, false);
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(String.valueOf(this.showIndex));
                stringBuilder.append("/");
                stringBuilder.append(String.valueOf(this.showListToUpdate.size()));
                notificationManager.notify(1, notification.setContentText(stringBuilder.toString()).build());
                return;
            }
            notificationManager.cancel(1);
        }
    }
}
