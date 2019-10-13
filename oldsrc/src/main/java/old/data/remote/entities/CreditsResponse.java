package jdr.tvtracker.data.remote.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import jdr.tvtracker.data.entities.Cast;
import jdr.tvtracker.data.entities.Crew;

public class CreditsResponse {
    @SerializedName("cast")
    @Expose
    private List<Cast> cast = null;
    @SerializedName("crew")
    @Expose
    private List<Crew> crew = null;

    public List<Cast> getCast() {
        return this.cast;
    }

    public void setCast(List<Cast> cast) {
        this.cast = cast;
    }

    public List<Crew> getCrew() {
        return this.crew;
    }

    public void setCrew(List<Crew> crew) {
        this.crew = crew;
    }
}
