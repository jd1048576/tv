package jdr.tvtracker.data.entities;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.net.Uri;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Comparator;
import java.util.Date;
import java.util.List;

import jdr.tvtracker.data.local.converter.CastConverter;
import jdr.tvtracker.data.local.converter.ContentRatingConverter;
import jdr.tvtracker.data.local.converter.CreatedByConverter;
import jdr.tvtracker.data.local.converter.DateConverter;
import jdr.tvtracker.data.local.converter.GenresConverter;
import jdr.tvtracker.data.local.converter.NetworkConverter;
import jdr.tvtracker.data.local.converter.ProductionCompanyConverter;
import jdr.tvtracker.data.local.converter.ShowBasicConverter;
import jdr.tvtracker.data.local.converter.VideoConverter;
import jdr.tvtracker.data.remote.entities.ContentRatingsResponse;
import jdr.tvtracker.data.remote.entities.CreditsResponse;
import jdr.tvtracker.data.remote.entities.TVResultsBasicResponse;
import jdr.tvtracker.data.remote.entities.VideosResponse;

@Entity
public class Show {
    @SerializedName("backdrop_path")
    @Expose
    private String backdropPath;
    @TypeConverters({CastConverter.class})
    private List<Cast> cast;
    @TypeConverters({ContentRatingConverter.class})
    private List<ContentRating> contentRatingList;
    @SerializedName("content_ratings")
    @Ignore
    @Expose
    private ContentRatingsResponse contentRatingsResponse;
    @SerializedName("created_by")
    @Expose
    @TypeConverters({CreatedByConverter.class})
    private List<CreatedBy> createdByList;
    @SerializedName("credits")
    @Ignore
    @Expose
    private CreditsResponse creditsResponse;
    private int episodeRuntime;
    @Ignore
    @SerializedName("episode_run_time")
    @Expose
    private final List<Integer> episodeRuntimeList = null;
    @SerializedName("external_ids")
    @Expose
    @Embedded
    private ExternalIds externalIds;
    @SerializedName("first_air_date")
    @Expose
    @TypeConverters({DateConverter.class})
    private Date firstAirDate;
    @Ignore
    private boolean fromDB;
    @SerializedName("genres")
    @Expose
    @TypeConverters({GenresConverter.class})
    private List<Genres> genresList;
    @SerializedName("homepage")
    @Expose
    private String homepage;
    @PrimaryKey
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("in_production")
    @Expose
    private boolean inProduction;
    @Ignore
    @SerializedName("languages")
    @Expose
    private List<String> languagesList;
    @SerializedName("last_air_date")
    @Expose
    @TypeConverters({DateConverter.class})
    private Date lastAirDate;
    private String launchId;
    private int launchSource;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("networks")
    @Expose
    @TypeConverters({NetworkConverter.class})
    private List<Network> networkList;
    @SerializedName("number_of_episodes")
    @Expose
    private int numberOfEpisodes;
    @SerializedName("number_of_seasons")
    @Expose
    private int numberOfSeasons;
    private String originCountry;
    @Ignore
    @SerializedName("origin_country")
    @Expose
    private final List<String> originCountryList = null;
    @SerializedName("original_language")
    @Expose
    private String originalLanguage;
    @SerializedName("original_name")
    @Expose
    private String originalName;
    @SerializedName("overview")
    @Expose
    private String overview;
    @SerializedName("popularity")
    @Expose
    private double popularity;
    @SerializedName("poster_path")
    @Expose
    private String posterPath;
    @SerializedName("production_companies")
    @Expose
    @TypeConverters({ProductionCompanyConverter.class})
    private List<ProductionCompany> productionCompanyList;
    @TypeConverters({ShowBasicConverter.class})
    private List<ShowBasic> recommendationList;
    @SerializedName("recommendations")
    @Ignore
    @Expose
    private TVResultsBasicResponse recommendationsResponse;
    @TypeConverters({ShowBasicConverter.class})
    private List<ShowBasic> similarList;
    @SerializedName("similar")
    @Ignore
    @Expose
    private TVResultsBasicResponse similarResponse;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("type")
    @Expose
    private String type;
    @TypeConverters({VideoConverter.class})
    private List<Video> videoList;
    @SerializedName("videos")
    @Ignore
    @Expose
    private VideosResponse videosResponse;
    @SerializedName("vote_average")
    @Expose
    private double voteAverage;
    @SerializedName("vote_count")
    @Expose
    private int voteCount;

    public Show(int id, String name, String originalName, int numberOfSeasons, int numberOfEpisodes, int episodeRuntime, boolean inProduction, String status, String type, Date firstAirDate, Date lastAirDate, double popularity, double voteAverage, int voteCount, String overview, String posterPath, String backdropPath, String homepage, String originCountry, String originalLanguage, List<CreatedBy> createdByList, List<Genres> genresList, List<Network> networkList, List<ProductionCompany> productionCompanyList, List<Cast> cast, List<ContentRating> contentRatingList, ExternalIds externalIds, List<ShowBasic> recommendationList, List<ShowBasic> similarList, List<Video> videoList, String launchId, int launchSource) {
        this.id = id;
        this.name = name;
        this.originalName = originalName;
        this.numberOfSeasons = numberOfSeasons;
        this.numberOfEpisodes = numberOfEpisodes;
        this.episodeRuntime = episodeRuntime;
        this.inProduction = inProduction;
        this.status = status;
        this.type = type;
        this.firstAirDate = firstAirDate;
        this.lastAirDate = lastAirDate;
        this.popularity = popularity;
        this.voteAverage = voteAverage;
        this.voteCount = voteCount;
        this.overview = overview;
        this.posterPath = posterPath;
        this.backdropPath = backdropPath;
        this.homepage = homepage;
        this.originCountry = originCountry;
        this.originalLanguage = originalLanguage;
        this.createdByList = createdByList;
        this.genresList = genresList;
        this.networkList = networkList;
        this.productionCompanyList = productionCompanyList;
        this.cast = cast;
        this.contentRatingList = contentRatingList;
        this.externalIds = externalIds;
        this.recommendationList = recommendationList;
        this.similarList = similarList;
        this.videoList = videoList;
        this.launchId = launchId;
        this.launchSource = launchSource;
    }

    public void setFields() {
        List list = this.episodeRuntimeList;
        if (list != null && list.size() > 0) {
            this.episodeRuntime = ((Integer) this.episodeRuntimeList.get(0)).intValue();
        }
        list = this.originCountryList;
        if (list != null && list.size() > 0) {
            this.originCountry = (String) this.originCountryList.get(0);
        }
        CreditsResponse creditsResponse = this.creditsResponse;
        if (!(creditsResponse == null || creditsResponse.getCast() == null || this.creditsResponse.getCast().size() <= 0)) {
            this.creditsResponse.getCast().sort(new Comparator<Cast>() {
                public int compare(Cast cast1, Cast cast2) {
                    return Integer.compare(cast1.getOrder(), cast2.getOrder());
                }
            });
            this.cast = this.creditsResponse.getCast();
        }
        ContentRatingsResponse contentRatingsResponse = this.contentRatingsResponse;
        if (!(contentRatingsResponse == null || contentRatingsResponse.getContentRatingList() == null || this.contentRatingsResponse.getContentRatingList().size() <= 0)) {
            this.contentRatingList = this.contentRatingsResponse.getContentRatingList();
        }
        TVResultsBasicResponse tVResultsBasicResponse = this.recommendationsResponse;
        if (!(tVResultsBasicResponse == null || tVResultsBasicResponse.getResults() == null || this.recommendationsResponse.getResults().size() <= 0)) {
            this.recommendationList = this.recommendationsResponse.getResults();
        }
        tVResultsBasicResponse = this.similarResponse;
        if (!(tVResultsBasicResponse == null || tVResultsBasicResponse.getResults() == null || this.similarResponse.getResults().size() <= 0)) {
            this.similarList = this.similarResponse.getResults();
        }
        VideosResponse videosResponse = this.videosResponse;
        if (!(videosResponse == null || videosResponse.getVideoList() == null || this.videosResponse.getVideoList().size() <= 0)) {
            this.videoList = this.videosResponse.getVideoList();
        }
        String str = this.homepage;
        if (str != null) {
            if (str.toLowerCase().contains("netflix")) {
                this.launchId = Uri.parse(this.homepage).getLastPathSegment();
                this.launchSource = 1;
            } else if (this.homepage.toLowerCase().contains("amazon")) {
                this.launchId = Uri.parse(this.homepage).getLastPathSegment();
                this.launchSource = 2;
            } else {
                this.launchSource = 0;
            }
        }
        this.fromDB = false;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOriginalName() {
        return this.originalName;
    }

    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }

    public int getNumberOfSeasons() {
        return this.numberOfSeasons;
    }

    public void setNumberOfSeasons(int numberOfSeasons) {
        this.numberOfSeasons = numberOfSeasons;
    }

    public int getNumberOfEpisodes() {
        return this.numberOfEpisodes;
    }

    public void setNumberOfEpisodes(int numberOfEpisodes) {
        this.numberOfEpisodes = numberOfEpisodes;
    }

    public int getEpisodeRuntime() {
        return this.episodeRuntime;
    }

    public void setEpisodeRuntime(int episodeRuntime) {
        this.episodeRuntime = episodeRuntime;
    }

    public boolean isInProduction() {
        return this.inProduction;
    }

    public void setInProduction(boolean inProduction) {
        this.inProduction = inProduction;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getFirstAirDate() {
        return this.firstAirDate;
    }

    public void setFirstAirDate(Date firstAirDate) {
        this.firstAirDate = firstAirDate;
    }

    public Date getLastAirDate() {
        return this.lastAirDate;
    }

    public void setLastAirDate(Date lastAirDate) {
        this.lastAirDate = lastAirDate;
    }

    public double getPopularity() {
        return this.popularity;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    public double getVoteAverage() {
        return this.voteAverage;
    }

    public void setVoteAverage(double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public int getVoteCount() {
        return this.voteCount;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }

    public String getOverview() {
        return this.overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getPosterPath() {
        return this.posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getBackdropPath() {
        return this.backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public String getHomepage() {
        return this.homepage;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    public String getOriginCountry() {
        return this.originCountry;
    }

    public void setOriginCountry(String originCountry) {
        this.originCountry = originCountry;
    }

    public String getOriginalLanguage() {
        return this.originalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public List<CreatedBy> getCreatedByList() {
        return this.createdByList;
    }

    public void setCreatedByList(List<CreatedBy> createdByList) {
        this.createdByList = createdByList;
    }

    public List<Genres> getGenresList() {
        return this.genresList;
    }

    public void setGenresList(List<Genres> genresList) {
        this.genresList = genresList;
    }

    public List<Network> getNetworkList() {
        return this.networkList;
    }

    public void setNetworkList(List<Network> networkList) {
        this.networkList = networkList;
    }

    public List<ProductionCompany> getProductionCompanyList() {
        return this.productionCompanyList;
    }

    public void setProductionCompanyList(List<ProductionCompany> productionCompanyList) {
        this.productionCompanyList = productionCompanyList;
    }

    public List<Cast> getCast() {
        return this.cast;
    }

    public void setCast(List<Cast> cast) {
        this.cast = cast;
    }

    public List<ContentRating> getContentRatingList() {
        return this.contentRatingList;
    }

    public void setContentRatingList(List<ContentRating> contentRatingList) {
        this.contentRatingList = contentRatingList;
    }

    public ExternalIds getExternalIds() {
        return this.externalIds;
    }

    public void setExternalIds(ExternalIds externalIds) {
        this.externalIds = externalIds;
    }

    public List<ShowBasic> getRecommendationList() {
        return this.recommendationList;
    }

    public void setRecommendationList(List<ShowBasic> recommendationList) {
        this.recommendationList = recommendationList;
    }

    public List<ShowBasic> getSimilarList() {
        return this.similarList;
    }

    public void setSimilarList(List<ShowBasic> similarList) {
        this.similarList = similarList;
    }

    public List<Video> getVideoList() {
        return this.videoList;
    }

    public void setVideoList(List<Video> videoList) {
        this.videoList = videoList;
    }

    public String getLaunchId() {
        return this.launchId;
    }

    public void setLaunchId(String launchId) {
        this.launchId = launchId;
    }

    public int getLaunchSource() {
        return this.launchSource;
    }

    public void setLaunchSource(int launchSource) {
        this.launchSource = launchSource;
    }

    public boolean isFromDB() {
        return this.fromDB;
    }

    public void setFromDB(boolean fromDB) {
        this.fromDB = fromDB;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Show{id=");
        stringBuilder.append(this.id);
        stringBuilder.append(", name='");
        stringBuilder.append(this.name);
        stringBuilder.append('\'');
        stringBuilder.append(", originalName='");
        stringBuilder.append(this.originalName);
        stringBuilder.append('\'');
        stringBuilder.append(", numberOfSeasons=");
        stringBuilder.append(this.numberOfSeasons);
        stringBuilder.append(", numberOfEpisodes=");
        stringBuilder.append(this.numberOfEpisodes);
        stringBuilder.append(", episodeRuntime=");
        stringBuilder.append(this.episodeRuntime);
        stringBuilder.append(", inProduction=");
        stringBuilder.append(this.inProduction);
        stringBuilder.append(", status='");
        stringBuilder.append(this.status);
        stringBuilder.append('\'');
        stringBuilder.append(", type='");
        stringBuilder.append(this.type);
        stringBuilder.append('\'');
        stringBuilder.append(", firstAirDate=");
        stringBuilder.append(this.firstAirDate);
        stringBuilder.append(", lastAirDate=");
        stringBuilder.append(this.lastAirDate);
        stringBuilder.append(", popularity=");
        stringBuilder.append(this.popularity);
        stringBuilder.append(", voteAverage=");
        stringBuilder.append(this.voteAverage);
        stringBuilder.append(", voteCount=");
        stringBuilder.append(this.voteCount);
        stringBuilder.append(", overview='");
        stringBuilder.append(this.overview);
        stringBuilder.append('\'');
        stringBuilder.append(", posterPath='");
        stringBuilder.append(this.posterPath);
        stringBuilder.append('\'');
        stringBuilder.append(", backdropPath='");
        stringBuilder.append(this.backdropPath);
        stringBuilder.append('\'');
        stringBuilder.append(", homepage='");
        stringBuilder.append(this.homepage);
        stringBuilder.append('\'');
        stringBuilder.append(", originCountry='");
        stringBuilder.append(this.originCountry);
        stringBuilder.append('\'');
        stringBuilder.append(", originalLanguage='");
        stringBuilder.append(this.originalLanguage);
        stringBuilder.append('\'');
        stringBuilder.append(", createdByList=");
        stringBuilder.append(this.createdByList);
        stringBuilder.append(", genresList=");
        stringBuilder.append(this.genresList);
        stringBuilder.append(", networkList=");
        stringBuilder.append(this.networkList);
        stringBuilder.append(", productionCompanyList=");
        stringBuilder.append(this.productionCompanyList);
        stringBuilder.append(", cast=");
        stringBuilder.append(this.cast);
        stringBuilder.append(", contentRatingList=");
        stringBuilder.append(this.contentRatingList);
        stringBuilder.append(", externalIds=");
        stringBuilder.append(this.externalIds);
        stringBuilder.append(", recommendationList=");
        stringBuilder.append(this.recommendationList);
        stringBuilder.append(", similarList=");
        stringBuilder.append(this.similarList);
        stringBuilder.append(", videoList=");
        stringBuilder.append(this.videoList);
        stringBuilder.append(", launchId='");
        stringBuilder.append(this.launchId);
        stringBuilder.append('\'');
        stringBuilder.append(", launchSource=");
        stringBuilder.append(this.launchSource);
        stringBuilder.append(", fromDB=");
        stringBuilder.append(this.fromDB);
        stringBuilder.append('}');
        return stringBuilder.toString();
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean equals(Object o) {
        boolean z = true;
        if (this == o) {
            return true;
        }
        if (o != null) {
            if (getClass() == o.getClass()) {
                Show show = (Show) o;
                if (this.id != show.id || this.numberOfSeasons != show.numberOfSeasons || this.numberOfEpisodes != show.numberOfEpisodes || this.episodeRuntime != show.episodeRuntime || this.inProduction != show.inProduction || Double.compare(show.voteAverage, this.voteAverage) != 0) {
                    return false;
                }
                String str = this.name;
                if (str != null) {
                    if (!str.equals(show.name)) {
                    }
                    str = this.status;
                    if (str != null) {
                        if (!str.equals(show.status)) {
                        }
                        Date date = this.lastAirDate;
                        if (date != null) {
                            if (!date.equals(show.lastAirDate)) {
                            }
                            str = this.overview;
                            if (str != null) {
                                if (!str.equals(show.overview)) {
                                }
                                str = this.posterPath;
                                if (str != null) {
                                    if (!str.equals(show.posterPath)) {
                                    }
                                    str = this.backdropPath;
                                    if (str != null) {
                                        if (!str.equals(show.backdropPath)) {
                                        }
                                        str = this.homepage;
                                        if (str != null) {
                                            if (!str.equals(show.homepage)) {
                                            }
                                            List list = this.createdByList;
                                            if (list != null) {
                                                if (!list.equals(show.createdByList)) {
                                                }
                                                list = this.genresList;
                                                if (list != null) {
                                                    if (!list.equals(show.genresList)) {
                                                    }
                                                    list = this.networkList;
                                                    if (list != null) {
                                                        if (!list.equals(show.networkList)) {
                                                        }
                                                        list = this.productionCompanyList;
                                                        if (list != null) {
                                                            if (!list.equals(show.productionCompanyList)) {
                                                            }
                                                            list = this.cast;
                                                            if (list != null) {
                                                                if (!list.equals(show.cast)) {
                                                                }
                                                                list = this.contentRatingList;
                                                                if (list != null) {
                                                                    if (!list.equals(show.contentRatingList)) {
                                                                    }
                                                                    ExternalIds externalIds = this.externalIds;
                                                                    if (externalIds != null) {
                                                                        if (!externalIds.equals(show.externalIds)) {
                                                                        }
                                                                        list = this.videoList;
                                                                        if (list != null) {
                                                                            z = list.equals(show.videoList);
                                                                        } else if (show.videoList != null) {
                                                                            z = false;
                                                                        }
                                                                        return z;
                                                                    }
                                                                    return false;
                                                                }
                                                                return false;
                                                            }
                                                            return false;
                                                        }
                                                        return false;
                                                    }
                                                    return false;
                                                }
                                                return false;
                                            }
                                            return false;
                                        }
                                        return false;
                                    }
                                    return false;
                                }
                                return false;
                            }
                            return false;
                        }
                        return false;
                    }
                    return false;
                }
                return false;
            }
        }
        return false;
    }

    public int hashCode() {
        return this.id;
    }
}
