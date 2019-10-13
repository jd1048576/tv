package jdr.tvtracker.activities.search;

import android.app.Application;
import android.arch.core.util.Function;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.Transformations;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import jdr.tvtracker.data.ShowRepository;
import jdr.tvtracker.data.entities.Show;
import jdr.tvtracker.data.remote.entities.TVResultsResponse;

public class SearchActivityViewModel extends AndroidViewModel {
    public static final int MODE_SEARCH = 0;
    public static final int MODE_TOP_RATED = 1;
    public static final int MODE_TRENDING = 2;
    public static final int MODE_UPCOMING = 3;
    private int MODE;
    private final LiveData<List<Show>> liveDataShowList = Transformations.map(this.mediatorLiveDataTVResultsResponse, new Function<TVResultsResponse, List<Show>>() {
        public List<Show> apply(TVResultsResponse input) {
            SearchActivityViewModel.this.handleResponse(input);
            return SearchActivityViewModel.this.showList;
        }
    });
    private final MediatorLiveData<TVResultsResponse> mediatorLiveDataTVResultsResponse = new MediatorLiveData();
    private int numberOfPages = 1;
    private int pageNumber = 0;
    private String query;
    private final List<Show> showList = new ArrayList();
    private final ShowRepository showRepository = new ShowRepository(getApplication());

    public SearchActivityViewModel(Application application) {
        super(application);
    }

    void setMode(int MODE) {
        this.MODE = MODE;
        get();
    }

    void setMode(String query) {
        this.MODE = 0;
        this.query = query;
        get();
    }

    public int getMODE() {
        return this.MODE;
    }

    public LiveData<List<Show>> getShowList() {
        return this.liveDataShowList;
    }

    public void get() {
        int i = this.pageNumber;
        if (i < this.numberOfPages) {
            this.pageNumber = i + 1;
            switch (this.MODE) {
                case 0:
                    getResponse(this.showRepository.getSearch(this.query, this.pageNumber));
                    return;
                case 1:
                    getResponse(this.showRepository.getTopRated(this.pageNumber));
                    return;
                case 2:
                    getResponse(this.showRepository.getTrending(this.pageNumber));
                    return;
                case 3:
                    getResponse(this.showRepository.getUpcoming(this.pageNumber));
                    return;
                default:
                    return;
            }
        }
    }

    private void getResponse(final LiveData<TVResultsResponse> source) {
        this.mediatorLiveDataTVResultsResponse.addSource(source, new Observer<TVResultsResponse>() {
            public void onChanged(@Nullable TVResultsResponse tvResultsResponse) {
                SearchActivityViewModel.this.mediatorLiveDataTVResultsResponse.postValue(tvResultsResponse);
                SearchActivityViewModel.this.mediatorLiveDataTVResultsResponse.removeSource(source);
            }
        });
    }

    private void handleResponse(TVResultsResponse response) {
        if (response != null) {
            this.showList.addAll(response.getResults());
            this.numberOfPages = response.getTotalPages();
        }
    }
}
