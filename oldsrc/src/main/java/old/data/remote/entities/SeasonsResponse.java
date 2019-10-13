package jdr.tvtracker.data.remote.entities;

import java.util.List;

import jdr.tvtracker.data.entities.Season;

public class SeasonsResponse {
    private List<Season> seasons;

    public SeasonsResponse(List<Season> seasons) {
        this.seasons = seasons;
    }

    public List<Season> getSeasons() {
        return this.seasons;
    }

    public void setSeasons(List<Season> seasons) {
        this.seasons = seasons;
    }
}
