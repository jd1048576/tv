package jdr.tvtracker.activities.main;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;

import java.util.List;

import jdr.tvtracker.activities.main.fragments.episodeItem.Item;
import jdr.tvtracker.data.ShowRepository;
import jdr.tvtracker.data.entities.Episode;
import jdr.tvtracker.data.entities.ShowBasic;

public class MainActivityViewModel extends AndroidViewModel {
    private LiveData<List<Item>> liveDataScheduleList;
    private LiveData<List<Item>> liveDataWatchList;
    private final MediatorLiveData<List<Item>> mediatorLiveDataScheduleList = new MediatorLiveData();
    private final MediatorLiveData<List<Item>> mediatorLiveDataWatchList = new MediatorLiveData();
    private final LiveData<List<ShowBasic>> showList = this.showRepository.getShowList();
    private final ShowRepository showRepository = new ShowRepository(getApplication());

    public MainActivityViewModel(Application application) {
        super(application);
    }

    public LiveData<List<Item>> getScheduleList() {
        return this.mediatorLiveDataScheduleList;
    }

    public void addScheduleListSource() {
        this.mediatorLiveDataScheduleList.removeSource(this.liveDataScheduleList);
        this.liveDataScheduleList = this.showRepository.getScheduleList();
        this.mediatorLiveDataScheduleList.addSource(this.liveDataScheduleList, new Observer<List<Item>>() {
            public void onChanged(@Nullable List<Item> items) {
                if (items != null && items.size() > 0) {
                    MainActivityViewModel.this.mediatorLiveDataScheduleList.postValue(items);
                }
            }
        });
    }

    public LiveData<List<Item>> getWatchList() {
        return this.mediatorLiveDataWatchList;
    }

    public void addWatchListSource() {
        this.mediatorLiveDataWatchList.removeSource(this.liveDataWatchList);
        this.liveDataWatchList = this.showRepository.getWatchList();
        this.mediatorLiveDataWatchList.addSource(this.liveDataWatchList, new Observer<List<Item>>() {
            public void onChanged(@Nullable List<Item> items) {
                MainActivityViewModel.this.mediatorLiveDataWatchList.postValue(items);
            }
        });
    }

    public LiveData<List<ShowBasic>> getShowList() {
        return this.showList;
    }

    public void updateEpisode(Episode episode) {
        this.showRepository.updateEpisode(episode);
    }
}
