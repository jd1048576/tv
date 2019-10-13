package jdr.tvtracker.data;

import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.Transformations;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.SparseArray;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import jdr.tvtracker.activities.main.fragments.episodeItem.EpisodeItem;
import jdr.tvtracker.activities.main.fragments.episodeItem.HeaderItem;
import jdr.tvtracker.activities.main.fragments.episodeItem.Item;
import jdr.tvtracker.data.entities.Episode;
import jdr.tvtracker.data.entities.Season;
import jdr.tvtracker.data.entities.Show;
import jdr.tvtracker.data.entities.ShowBasic;
import jdr.tvtracker.data.local.DatabaseCreator;
import jdr.tvtracker.data.local.EpisodeDao;
import jdr.tvtracker.data.local.ShowDao;
import jdr.tvtracker.data.remote.TMDbClient;
import jdr.tvtracker.data.remote.TMDbInterface;
import jdr.tvtracker.data.remote.entities.SeasonsResponse;
import jdr.tvtracker.data.remote.entities.TVResultsResponse;
import jdr.tvtracker.utils.DateUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShowRepository {
    private final EpisodeDao episodeDao;
    private final Executor executor = Executors.newFixedThreadPool(2);
    private final ShowDao showDao;
    private final TMDbInterface tmDbInterface = ((TMDbInterface) TMDbClient.getClient().create(TMDbInterface.class));

    public ShowRepository(Context context) {
        this.showDao = DatabaseCreator.getShowDatabase(context).ShowDao();
        this.episodeDao = DatabaseCreator.getShowDatabase(context).episodeDao();
    }

    TMDbInterface getTMDbInterface() {
        return this.tmDbInterface;
    }

    public LiveData<TVResultsResponse> getSearch(String query, int pageNumber) {
        final MutableLiveData<TVResultsResponse> mutableLiveDataTVResultsResponse = new MutableLiveData();
        this.tmDbInterface.getTVSearch(query, pageNumber).enqueue(new Callback<TVResultsResponse>() {
            public void onResponse(@NonNull Call<TVResultsResponse> call, @NonNull Response<TVResultsResponse> response) {
                mutableLiveDataTVResultsResponse.postValue(response.body());
            }

            public void onFailure(@NonNull Call<TVResultsResponse> call, @NonNull Throwable t) {
                t.printStackTrace();
            }
        });
        return mutableLiveDataTVResultsResponse;
    }

    public LiveData<TVResultsResponse> getTopRated(int pageNumber) {
        final MutableLiveData<TVResultsResponse> mutableLiveDataTVResultsResponse = new MutableLiveData();
        this.tmDbInterface.getDiscoverTVTopRated(pageNumber).enqueue(new Callback<TVResultsResponse>() {
            public void onResponse(@NonNull Call<TVResultsResponse> call, @NonNull Response<TVResultsResponse> response) {
                mutableLiveDataTVResultsResponse.postValue(response.body());
            }

            public void onFailure(@NonNull Call<TVResultsResponse> call, @NonNull Throwable t) {
                t.printStackTrace();
            }
        });
        return mutableLiveDataTVResultsResponse;
    }

    public LiveData<TVResultsResponse> getTrending(int pageNumber) {
        final MutableLiveData<TVResultsResponse> mutableLiveDataTVResultsResponse = new MutableLiveData();
        this.tmDbInterface.getDiscoverTVTrending(pageNumber).enqueue(new Callback<TVResultsResponse>() {
            public void onResponse(@NonNull Call<TVResultsResponse> call, @NonNull Response<TVResultsResponse> response) {
                mutableLiveDataTVResultsResponse.postValue(response.body());
            }

            public void onFailure(@NonNull Call<TVResultsResponse> call, @NonNull Throwable t) {
                t.printStackTrace();
            }
        });
        return mutableLiveDataTVResultsResponse;
    }

    public LiveData<TVResultsResponse> getUpcoming(int pageNumber) {
        final MutableLiveData<TVResultsResponse> mutableLiveDataTVResultsResponse = new MutableLiveData();
        this.tmDbInterface.getDiscoverTVUpcoming(pageNumber, new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Calendar.getInstance().getTime())).enqueue(new Callback<TVResultsResponse>() {
            public void onResponse(@NonNull Call<TVResultsResponse> call, @NonNull Response<TVResultsResponse> response) {
                mutableLiveDataTVResultsResponse.postValue(response.body());
            }

            public void onFailure(@NonNull Call<TVResultsResponse> call, @NonNull Throwable t) {
                t.printStackTrace();
            }
        });
        return mutableLiveDataTVResultsResponse;
    }

    public LiveData<List<Item>> getScheduleList() {
        return Transformations.map(this.episodeDao.getScheduleList(), new Function<List<EpisodeItem>, List<Item>>() {
            public List<Item> apply(List<EpisodeItem> input) {
                DateUtil dateUtil = new DateUtil();
                Date dateInOneWeek = new Date(Calendar.getInstance().getTimeInMillis() + 604800000);
                Map<Date, List<EpisodeItem>> map = new TreeMap();
                for (EpisodeItem episodeItem : input) {
                    List<EpisodeItem> value;
                    boolean isInLastWeek = dateUtil.isInLastWeek(episodeItem.getAirDate());
                    if (isInLastWeek) {
                        value = (List) map.get(episodeItem.getAirDate());
                    } else {
                        value = (List) map.get(dateInOneWeek);
                    }
                    if (value == null) {
                        value = new ArrayList();
                        if (isInLastWeek) {
                            map.put(episodeItem.getAirDate(), value);
                        } else {
                            map.put(dateInOneWeek, value);
                        }
                    }
                    value.add(episodeItem);
                }
                List<Item> itemList = new ArrayList();
                for (Date date : map.keySet()) {
                    itemList.add(new HeaderItem(date));
                    itemList.addAll((Collection) map.get(date));
                }
                return itemList;
            }
        });
    }

    public LiveData<List<Item>> getWatchList() {
        return Transformations.map(this.episodeDao.getWatchList(), new Function<List<EpisodeItem>, List<Item>>() {
            public List<Item> apply(List<EpisodeItem> input) {
                long timeInMillisOneWeekAgo = Calendar.getInstance().getTimeInMillis() - 604800000;
                List<String> headerList = new ArrayList();
                headerList.add("Recently Aired");
                headerList.add("Continue Watching");
                headerList.add("Season Not Started");
                headerList.add("Show Not Started");
                Map<String, List<EpisodeItem>> map = new LinkedHashMap();
                for (String header : headerList) {
                    map.put(header, new ArrayList());
                }
                for (EpisodeItem episodeItem : input) {
                    if (episodeItem.getAirDate().getTime() > timeInMillisOneWeekAgo) {
                        ((List) map.get(headerList.get(0))).add(episodeItem);
                    } else if (episodeItem.getEpisodeNumber() > 1) {
                        ((List) map.get(headerList.get(1))).add(episodeItem);
                    } else if (episodeItem.getSeasonNumber() <= 1 || episodeItem.getEpisodeNumber() != 1) {
                        ((List) map.get(headerList.get(3))).add(episodeItem);
                    } else {
                        ((List) map.get(headerList.get(2))).add(episodeItem);
                    }
                }
                List<Item> itemList = new ArrayList();
                for (String header2 : headerList) {
                    List<EpisodeItem> value = (List) map.get(header2);
                    if (value != null && value.size() > 0) {
                        itemList.add(new HeaderItem(header2));
                        itemList.addAll(value);
                    }
                }
                return itemList;
            }
        });
    }

    public LiveData<List<ShowBasic>> getShowList() {
        return this.showDao.getShowList();
    }

    public LiveData<Show> getShow(int id) {
        final MediatorLiveData<Show> mediatorLiveDataShow = new MediatorLiveData();
        LiveData<Show> liveDataShowFromDB = getShowByIdFromDB(id);
        final LiveData<Show> liveDataShowFromAPI = getShowByIdFromAPI(id);
        mediatorLiveDataShow.addSource(liveDataShowFromDB, new Observer<Show>() {
            public void onChanged(@Nullable Show show) {
                if (show != null) {
                    show.setFromDB(true);
                    mediatorLiveDataShow.postValue(show);
                    mediatorLiveDataShow.removeSource(liveDataShowFromAPI);
                }
            }
        });
        mediatorLiveDataShow.addSource(liveDataShowFromAPI, new Observer<Show>() {
            public void onChanged(@Nullable Show show) {
                if (show != null) {
                    mediatorLiveDataShow.postValue(show);
                }
            }
        });
        return mediatorLiveDataShow;
    }

    private LiveData<Show> getShowByIdFromDB(int id) {
        return this.showDao.getShowById(id);
    }

    private LiveData<Show> getShowByIdFromAPI(int id) {
        final MutableLiveData<Show> mutableLiveDataShow = new MutableLiveData();
        this.tmDbInterface.getShow(id).enqueue(new Callback<Show>() {
            public void onResponse(@NonNull Call<Show> call, @NonNull Response<Show> response) {
                Show show = (Show) response.body();
                if (show != null) {
                    show.setFields();
                    mutableLiveDataShow.postValue(show);
                }
            }

            public void onFailure(@NonNull Call<Show> call, @NonNull Throwable t) {
                t.printStackTrace();
            }
        });
        return mutableLiveDataShow;
    }

    public LiveData<List<Season>> getAllSeasons(int id, int numberOfSeasons) {
        final MediatorLiveData<List<Season>> mediatorLiveDataSeasons = new MediatorLiveData();
        LiveData<List<Season>> listLiveDataSeasonsFromDB = getAllSeasonsByShowIdFromDB(id);
        final LiveData<List<Season>> listLiveDataSeasonsFromAPI = getAllSeasonsByShowIdFromAPI(id, numberOfSeasons);
        mediatorLiveDataSeasons.addSource(listLiveDataSeasonsFromDB, new Observer<List<Season>>() {
            public void onChanged(@Nullable List<Season> seasons) {
                if (seasons != null && seasons.size() > 0) {
                    mediatorLiveDataSeasons.postValue(seasons);
                    mediatorLiveDataSeasons.removeSource(listLiveDataSeasonsFromAPI);
                }
            }
        });
        mediatorLiveDataSeasons.addSource(listLiveDataSeasonsFromAPI, new Observer<List<Season>>() {
            public void onChanged(@Nullable List<Season> seasons) {
                if (seasons != null && seasons.size() > 0) {
                    mediatorLiveDataSeasons.postValue(seasons);
                }
            }
        });
        return mediatorLiveDataSeasons;
    }

    private LiveData<List<Season>> getAllSeasonsByShowIdFromDB(int id) {
        return Transformations.map(this.episodeDao.getEpisodeListByShowId(id), new Function<List<Episode>, List<Season>>() {
            public List<Season> apply(List<Episode> input) {
                Map<Integer, Season> map = new TreeMap();
                for (Episode episode : input) {
                    Season value = (Season) map.get(Integer.valueOf(episode.getSeasonNumber()));
                    if (value == null) {
                        value = new Season(episode.getSeasonNumber(), new ArrayList());
                        map.put(Integer.valueOf(episode.getSeasonNumber()), value);
                    }
                    value.addEpisode(episode);
                }
                return new ArrayList(map.values());
            }
        });
    }

    private LiveData<List<Season>> getAllSeasonsByShowIdFromAPI(int id, int numberOfSeasons) {
        final MutableLiveData<List<Season>> mutableLiveDataSeasons = new MutableLiveData();
        final List<Season> seasons = new ArrayList();
        for (int i = 1; i < numberOfSeasons + 1; i += 20) {
            int max = i + 20;
            if (numberOfSeasons < max) {
                max = numberOfSeasons + 1;
            }
            StringBuilder stringBuilder = new StringBuilder();
            for (int j = i; j < max; j++) {
                stringBuilder.append("season/");
                stringBuilder.append(j);
                stringBuilder.append(",");
            }
            this.tmDbInterface.getSeasons(id, stringBuilder.substring(0, stringBuilder.length() - 1)).enqueue(new Callback<SeasonsResponse>() {
                public void onResponse(@NonNull Call<SeasonsResponse> call, @NonNull Response<SeasonsResponse> response) {
                    SeasonsResponse seasonsResponse = (SeasonsResponse) response.body();
                    if (seasonsResponse != null && seasonsResponse.getSeasons() != null && seasonsResponse.getSeasons().size() > 0) {
                        if (seasons.size() > 0) {
                            int seasonNumber = ((Season) seasonsResponse.getSeasons().get(0)).getSeasonNumber();
                            List list = seasons;
                            if (seasonNumber < ((Season) list.get(list.size() - 1)).getSeasonNumber()) {
                                seasons.addAll(((Season) seasonsResponse.getSeasons().get(0)).getSeasonNumber() - 1, seasonsResponse.getSeasons());
                                mutableLiveDataSeasons.postValue(seasons);
                            }
                        }
                        seasons.addAll(seasonsResponse.getSeasons());
                        mutableLiveDataSeasons.postValue(seasons);
                    }
                }

                public void onFailure(@NonNull Call<SeasonsResponse> call, @NonNull Throwable t) {
                    t.printStackTrace();
                }
            });
        }
        return mutableLiveDataSeasons;
    }

    public void addShowAndEpisodeList(final Show show, final List<Season> seasons) {
        this.executor.execute(new Runnable() {
            public void run() {
                ShowRepository.this.showDao.addShow(show);
                List<Episode> episodeList = new ArrayList();
                List<Season> list = seasons;
                if (list != null) {
                    for (Season season : list) {
                        episodeList.addAll(season.getEpisodeList());
                    }
                    ShowRepository.this.episodeDao.addEpisodeList(episodeList);
                }
            }
        });
    }

    public void updateShow(final Show show) {
        this.executor.execute(new Runnable() {
            public void run() {
                ShowRepository.this.showDao.updateShow(show);
            }
        });
    }

    public void deleteShow(final Show show) {
        this.executor.execute(new Runnable() {
            public void run() {
                ShowRepository.this.showDao.deleteShow(show);
            }
        });
    }

    void addEpisode(final Episode episode) {
        this.executor.execute(new Runnable() {
            public void run() {
                ShowRepository.this.episodeDao.addEpisode(episode);
            }
        });
    }

    public void updateEpisode(final Episode episode) {
        this.executor.execute(new Runnable() {
            public void run() {
                ShowRepository.this.episodeDao.updateEpisode(episode);
            }
        });
    }

    public void updateEpisodeList(final List<Season> seasons) {
        this.executor.execute(new Runnable() {
            public void run() {
                List<Episode> episodeList = new ArrayList();
                List<Season> list = seasons;
                if (list != null) {
                    for (Season season : list) {
                        episodeList.addAll(season.getEpisodeList());
                    }
                    ShowRepository.this.episodeDao.updateEpisodeList(episodeList);
                }
            }
        });
    }

    void deleteEpisode(final Episode episode) {
        this.executor.execute(new Runnable() {
            public void run() {
                ShowRepository.this.episodeDao.deleteEpisode(episode);
            }
        });
    }

    List<Show> getShowListToUpdate() {
        return this.showDao.getShowListToUpdate();
    }

    Map<Integer, SparseArray<Episode>> getEpisodeSparseArrayMapToUpdate() {
        List<Episode> episodeList = this.episodeDao.getEpisodeListToUpdate();
        Map<Integer, SparseArray<Episode>> map = new TreeMap();
        for (Episode episode : episodeList) {
            SparseArray<Episode> value = (SparseArray) map.get(Integer.valueOf(episode.getShowId()));
            if (value == null) {
                value = new SparseArray();
                map.put(Integer.valueOf(episode.getShowId()), value);
            }
            value.put(episode.getId(), episode);
        }
        return map;
    }
}
