package jdr.tvtracker.activities.show;

import android.app.Application;
import android.arch.core.util.Function;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.Transformations;

import java.util.List;

import jdr.tvtracker.data.ShowRepository;
import jdr.tvtracker.data.entities.Season;
import jdr.tvtracker.data.entities.Show;

public class ShowActivityViewModel extends AndroidViewModel {
    private final MediatorLiveData<Integer> id = new MediatorLiveData();
    private final LiveData<List<Season>> seasons = Transformations.switchMap(this.show, new Function<Show, LiveData<List<Season>>>() {
        public LiveData<List<Season>> apply(Show input) {
            return ShowActivityViewModel.this.showRepository.getAllSeasons(input.getId(), input.getNumberOfSeasons());
        }
    });
    private final LiveData<Show> show = Transformations.switchMap(this.id, new Function<Integer, LiveData<Show>>() {
        public LiveData<Show> apply(Integer input) {
            return ShowActivityViewModel.this.showRepository.getShow(input.intValue());
        }
    });
    private final ShowRepository showRepository = new ShowRepository(getApplication());

    public ShowActivityViewModel(Application application) {
        super(application);
    }

    public void setId(int id) {
        this.id.setValue(Integer.valueOf(id));
    }

    public LiveData<Show> getShow() {
        return this.show;
    }

    public LiveData<List<Season>> getSeasons() {
        return this.seasons;
    }

    public void addShowAndEpisodeList() {
        this.showRepository.addShowAndEpisodeList((Show) this.show.getValue(), (List) this.seasons.getValue());
    }

    public void updateShow() {
        this.showRepository.updateShow((Show) this.show.getValue());
    }

    public void deleteShow() {
        this.showRepository.deleteShow((Show) this.show.getValue());
    }

    public void updateEpisodeList() {
        this.showRepository.updateEpisodeList((List) this.seasons.getValue());
    }
}
