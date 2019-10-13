package jdr.tvtracker.data.local;

import android.arch.lifecycle.ComputableLiveData;
import android.arch.lifecycle.LiveData;
import android.arch.persistence.db.SupportSQLiteStatement;
import android.arch.persistence.room.EntityDeletionOrUpdateAdapter;
import android.arch.persistence.room.EntityInsertionAdapter;
import android.arch.persistence.room.InvalidationTracker.Observer;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.RoomSQLiteQuery;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import jdr.tvtracker.data.entities.Cast;
import jdr.tvtracker.data.entities.ContentRating;
import jdr.tvtracker.data.entities.CreatedBy;
import jdr.tvtracker.data.entities.ExternalIds;
import jdr.tvtracker.data.entities.Genres;
import jdr.tvtracker.data.entities.Network;
import jdr.tvtracker.data.entities.ProductionCompany;
import jdr.tvtracker.data.entities.Show;
import jdr.tvtracker.data.entities.ShowBasic;
import jdr.tvtracker.data.entities.Video;
import jdr.tvtracker.data.local.converter.CastConverter;
import jdr.tvtracker.data.local.converter.ContentRatingConverter;
import jdr.tvtracker.data.local.converter.CreatedByConverter;
import jdr.tvtracker.data.local.converter.DateConverter;
import jdr.tvtracker.data.local.converter.GenresConverter;
import jdr.tvtracker.data.local.converter.NetworkConverter;
import jdr.tvtracker.data.local.converter.ProductionCompanyConverter;
import jdr.tvtracker.data.local.converter.ShowBasicConverter;
import jdr.tvtracker.data.local.converter.VideoConverter;

public class ShowDao_Impl implements ShowDao {
    private final RoomDatabase __db;
    private final EntityDeletionOrUpdateAdapter __deletionAdapterOfShow;
    private final EntityInsertionAdapter __insertionAdapterOfShow;
    private final EntityDeletionOrUpdateAdapter __updateAdapterOfShow;

    public ShowDao_Impl(RoomDatabase __db) {
        this.__db = __db;
        this.__insertionAdapterOfShow = new EntityInsertionAdapter<Show>(__db) {
            public String createQuery() {
                return "INSERT OR REPLACE INTO `Show`(`id`,`name`,`originalName`,`numberOfSeasons`,`numberOfEpisodes`,`episodeRuntime`,`inProduction`,`status`,`type`,`firstAirDate`,`lastAirDate`,`popularity`,`voteAverage`,`voteCount`,`overview`,`posterPath`,`backdropPath`,`homepage`,`originCountry`,`originalLanguage`,`createdByList`,`genresList`,`networkList`,`productionCompanyList`,`cast`,`contentRatingList`,`recommendationList`,`similarList`,`videoList`,`launchId`,`launchSource`,`imdbId`,`tvdbId`,`facebookId`,`instagramId`,`twitterId`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            }

            public void bind(SupportSQLiteStatement stmt, Show value) {
                SupportSQLiteStatement supportSQLiteStatement = stmt;
                supportSQLiteStatement.bindLong(1, (long) value.getId());
                if (value.getName() == null) {
                    supportSQLiteStatement.bindNull(2);
                } else {
                    supportSQLiteStatement.bindString(2, value.getName());
                }
                if (value.getOriginalName() == null) {
                    supportSQLiteStatement.bindNull(3);
                } else {
                    supportSQLiteStatement.bindString(3, value.getOriginalName());
                }
                supportSQLiteStatement.bindLong(4, (long) value.getNumberOfSeasons());
                supportSQLiteStatement.bindLong(5, (long) value.getNumberOfEpisodes());
                supportSQLiteStatement.bindLong(6, (long) value.getEpisodeRuntime());
                int _tmp = value.isInProduction();
                supportSQLiteStatement.bindLong(7, (long) _tmp);
                if (value.getStatus() == null) {
                    supportSQLiteStatement.bindNull(8);
                } else {
                    supportSQLiteStatement.bindString(8, value.getStatus());
                }
                if (value.getType() == null) {
                    supportSQLiteStatement.bindNull(9);
                } else {
                    supportSQLiteStatement.bindString(9, value.getType());
                }
                Long _tmp_1 = DateConverter.toTimestamp(value.getFirstAirDate());
                if (_tmp_1 == null) {
                    supportSQLiteStatement.bindNull(10);
                } else {
                    supportSQLiteStatement.bindLong(10, _tmp_1.longValue());
                }
                Long _tmp_2 = DateConverter.toTimestamp(value.getLastAirDate());
                if (_tmp_2 == null) {
                    supportSQLiteStatement.bindNull(11);
                } else {
                    supportSQLiteStatement.bindLong(11, _tmp_2.longValue());
                }
                supportSQLiteStatement.bindDouble(12, value.getPopularity());
                supportSQLiteStatement.bindDouble(13, value.getVoteAverage());
                supportSQLiteStatement.bindLong(14, (long) value.getVoteCount());
                if (value.getOverview() == null) {
                    supportSQLiteStatement.bindNull(15);
                } else {
                    supportSQLiteStatement.bindString(15, value.getOverview());
                }
                if (value.getPosterPath() == null) {
                    supportSQLiteStatement.bindNull(16);
                } else {
                    supportSQLiteStatement.bindString(16, value.getPosterPath());
                }
                if (value.getBackdropPath() == null) {
                    supportSQLiteStatement.bindNull(17);
                } else {
                    supportSQLiteStatement.bindString(17, value.getBackdropPath());
                }
                if (value.getHomepage() == null) {
                    supportSQLiteStatement.bindNull(18);
                } else {
                    supportSQLiteStatement.bindString(18, value.getHomepage());
                }
                if (value.getOriginCountry() == null) {
                    supportSQLiteStatement.bindNull(19);
                } else {
                    supportSQLiteStatement.bindString(19, value.getOriginCountry());
                }
                if (value.getOriginalLanguage() == null) {
                    supportSQLiteStatement.bindNull(20);
                } else {
                    supportSQLiteStatement.bindString(20, value.getOriginalLanguage());
                }
                String _tmp_3 = CreatedByConverter.toString(value.getCreatedByList());
                if (_tmp_3 == null) {
                    supportSQLiteStatement.bindNull(21);
                } else {
                    supportSQLiteStatement.bindString(21, _tmp_3);
                }
                String _tmp_4 = GenresConverter.toString(value.getGenresList());
                if (_tmp_4 == null) {
                    supportSQLiteStatement.bindNull(22);
                } else {
                    supportSQLiteStatement.bindString(22, _tmp_4);
                }
                String _tmp_5 = NetworkConverter.toString(value.getNetworkList());
                if (_tmp_5 == null) {
                    supportSQLiteStatement.bindNull(23);
                } else {
                    supportSQLiteStatement.bindString(23, _tmp_5);
                }
                String _tmp_6 = ProductionCompanyConverter.toString(value.getProductionCompanyList());
                if (_tmp_6 == null) {
                    supportSQLiteStatement.bindNull(24);
                } else {
                    supportSQLiteStatement.bindString(24, _tmp_6);
                }
                String _tmp_7 = CastConverter.toString(value.getCast());
                if (_tmp_7 == null) {
                    supportSQLiteStatement.bindNull(25);
                } else {
                    supportSQLiteStatement.bindString(25, _tmp_7);
                }
                String _tmp_8 = ContentRatingConverter.toString(value.getContentRatingList());
                if (_tmp_8 == null) {
                    supportSQLiteStatement.bindNull(26);
                } else {
                    supportSQLiteStatement.bindString(26, _tmp_8);
                }
                String _tmp_9 = ShowBasicConverter.toString(value.getRecommendationList());
                if (_tmp_9 == null) {
                    supportSQLiteStatement.bindNull(27);
                } else {
                    supportSQLiteStatement.bindString(27, _tmp_9);
                }
                String _tmp_10 = ShowBasicConverter.toString(value.getSimilarList());
                if (_tmp_10 == null) {
                    supportSQLiteStatement.bindNull(28);
                } else {
                    supportSQLiteStatement.bindString(28, _tmp_10);
                }
                String _tmp_11 = VideoConverter.toString(value.getVideoList());
                if (_tmp_11 == null) {
                    supportSQLiteStatement.bindNull(29);
                } else {
                    supportSQLiteStatement.bindString(29, _tmp_11);
                }
                if (value.getLaunchId() == null) {
                    supportSQLiteStatement.bindNull(30);
                } else {
                    supportSQLiteStatement.bindString(30, value.getLaunchId());
                }
                supportSQLiteStatement.bindLong(31, (long) value.getLaunchSource());
                ExternalIds _tmpExternalIds = value.getExternalIds();
                if (_tmpExternalIds != null) {
                    if (_tmpExternalIds.getImdbId() == null) {
                        supportSQLiteStatement.bindNull(32);
                    } else {
                        supportSQLiteStatement.bindString(32, _tmpExternalIds.getImdbId());
                    }
                    supportSQLiteStatement.bindLong(33, (long) _tmpExternalIds.getTvdbId());
                    if (_tmpExternalIds.getFacebookId() == null) {
                        supportSQLiteStatement.bindNull(34);
                    } else {
                        supportSQLiteStatement.bindString(34, _tmpExternalIds.getFacebookId());
                    }
                    if (_tmpExternalIds.getInstagramId() == null) {
                        supportSQLiteStatement.bindNull(35);
                    } else {
                        supportSQLiteStatement.bindString(35, _tmpExternalIds.getInstagramId());
                    }
                    if (_tmpExternalIds.getTwitterId() == null) {
                        supportSQLiteStatement.bindNull(36);
                        return;
                    } else {
                        supportSQLiteStatement.bindString(36, _tmpExternalIds.getTwitterId());
                        return;
                    }
                }
                Long l = _tmp_1;
                supportSQLiteStatement.bindNull(32);
                supportSQLiteStatement.bindNull(33);
                supportSQLiteStatement.bindNull(34);
                supportSQLiteStatement.bindNull(35);
                supportSQLiteStatement.bindNull(36);
            }
        };
        this.__deletionAdapterOfShow = new EntityDeletionOrUpdateAdapter<Show>(__db) {
            public String createQuery() {
                return "DELETE FROM `Show` WHERE `id` = ?";
            }

            public void bind(SupportSQLiteStatement stmt, Show value) {
                stmt.bindLong(1, (long) value.getId());
            }
        };
        this.__updateAdapterOfShow = new EntityDeletionOrUpdateAdapter<Show>(__db) {
            public String createQuery() {
                return "UPDATE OR ABORT `Show` SET `id` = ?,`name` = ?,`originalName` = ?,`numberOfSeasons` = ?,`numberOfEpisodes` = ?,`episodeRuntime` = ?,`inProduction` = ?,`status` = ?,`type` = ?,`firstAirDate` = ?,`lastAirDate` = ?,`popularity` = ?,`voteAverage` = ?,`voteCount` = ?,`overview` = ?,`posterPath` = ?,`backdropPath` = ?,`homepage` = ?,`originCountry` = ?,`originalLanguage` = ?,`createdByList` = ?,`genresList` = ?,`networkList` = ?,`productionCompanyList` = ?,`cast` = ?,`contentRatingList` = ?,`recommendationList` = ?,`similarList` = ?,`videoList` = ?,`launchId` = ?,`launchSource` = ?,`imdbId` = ?,`tvdbId` = ?,`facebookId` = ?,`instagramId` = ?,`twitterId` = ? WHERE `id` = ?";
            }

            public void bind(SupportSQLiteStatement stmt, Show value) {
                SupportSQLiteStatement supportSQLiteStatement = stmt;
                supportSQLiteStatement.bindLong(1, (long) value.getId());
                if (value.getName() == null) {
                    supportSQLiteStatement.bindNull(2);
                } else {
                    supportSQLiteStatement.bindString(2, value.getName());
                }
                if (value.getOriginalName() == null) {
                    supportSQLiteStatement.bindNull(3);
                } else {
                    supportSQLiteStatement.bindString(3, value.getOriginalName());
                }
                supportSQLiteStatement.bindLong(4, (long) value.getNumberOfSeasons());
                supportSQLiteStatement.bindLong(5, (long) value.getNumberOfEpisodes());
                supportSQLiteStatement.bindLong(6, (long) value.getEpisodeRuntime());
                int _tmp = value.isInProduction();
                supportSQLiteStatement.bindLong(7, (long) _tmp);
                if (value.getStatus() == null) {
                    supportSQLiteStatement.bindNull(8);
                } else {
                    supportSQLiteStatement.bindString(8, value.getStatus());
                }
                if (value.getType() == null) {
                    supportSQLiteStatement.bindNull(9);
                } else {
                    supportSQLiteStatement.bindString(9, value.getType());
                }
                Long _tmp_1 = DateConverter.toTimestamp(value.getFirstAirDate());
                if (_tmp_1 == null) {
                    supportSQLiteStatement.bindNull(10);
                } else {
                    supportSQLiteStatement.bindLong(10, _tmp_1.longValue());
                }
                Long _tmp_2 = DateConverter.toTimestamp(value.getLastAirDate());
                if (_tmp_2 == null) {
                    supportSQLiteStatement.bindNull(11);
                } else {
                    supportSQLiteStatement.bindLong(11, _tmp_2.longValue());
                }
                supportSQLiteStatement.bindDouble(12, value.getPopularity());
                supportSQLiteStatement.bindDouble(13, value.getVoteAverage());
                supportSQLiteStatement.bindLong(14, (long) value.getVoteCount());
                if (value.getOverview() == null) {
                    supportSQLiteStatement.bindNull(15);
                } else {
                    supportSQLiteStatement.bindString(15, value.getOverview());
                }
                if (value.getPosterPath() == null) {
                    supportSQLiteStatement.bindNull(16);
                } else {
                    supportSQLiteStatement.bindString(16, value.getPosterPath());
                }
                if (value.getBackdropPath() == null) {
                    supportSQLiteStatement.bindNull(17);
                } else {
                    supportSQLiteStatement.bindString(17, value.getBackdropPath());
                }
                if (value.getHomepage() == null) {
                    supportSQLiteStatement.bindNull(18);
                } else {
                    supportSQLiteStatement.bindString(18, value.getHomepage());
                }
                if (value.getOriginCountry() == null) {
                    supportSQLiteStatement.bindNull(19);
                } else {
                    supportSQLiteStatement.bindString(19, value.getOriginCountry());
                }
                if (value.getOriginalLanguage() == null) {
                    supportSQLiteStatement.bindNull(20);
                } else {
                    supportSQLiteStatement.bindString(20, value.getOriginalLanguage());
                }
                String _tmp_3 = CreatedByConverter.toString(value.getCreatedByList());
                if (_tmp_3 == null) {
                    supportSQLiteStatement.bindNull(21);
                } else {
                    supportSQLiteStatement.bindString(21, _tmp_3);
                }
                String _tmp_4 = GenresConverter.toString(value.getGenresList());
                if (_tmp_4 == null) {
                    supportSQLiteStatement.bindNull(22);
                } else {
                    supportSQLiteStatement.bindString(22, _tmp_4);
                }
                String _tmp_5 = NetworkConverter.toString(value.getNetworkList());
                if (_tmp_5 == null) {
                    supportSQLiteStatement.bindNull(23);
                } else {
                    supportSQLiteStatement.bindString(23, _tmp_5);
                }
                String _tmp_6 = ProductionCompanyConverter.toString(value.getProductionCompanyList());
                if (_tmp_6 == null) {
                    supportSQLiteStatement.bindNull(24);
                } else {
                    supportSQLiteStatement.bindString(24, _tmp_6);
                }
                String _tmp_7 = CastConverter.toString(value.getCast());
                if (_tmp_7 == null) {
                    supportSQLiteStatement.bindNull(25);
                } else {
                    supportSQLiteStatement.bindString(25, _tmp_7);
                }
                String _tmp_8 = ContentRatingConverter.toString(value.getContentRatingList());
                if (_tmp_8 == null) {
                    supportSQLiteStatement.bindNull(26);
                } else {
                    supportSQLiteStatement.bindString(26, _tmp_8);
                }
                String _tmp_9 = ShowBasicConverter.toString(value.getRecommendationList());
                if (_tmp_9 == null) {
                    supportSQLiteStatement.bindNull(27);
                } else {
                    supportSQLiteStatement.bindString(27, _tmp_9);
                }
                String _tmp_10 = ShowBasicConverter.toString(value.getSimilarList());
                if (_tmp_10 == null) {
                    supportSQLiteStatement.bindNull(28);
                } else {
                    supportSQLiteStatement.bindString(28, _tmp_10);
                }
                String _tmp_11 = VideoConverter.toString(value.getVideoList());
                if (_tmp_11 == null) {
                    supportSQLiteStatement.bindNull(29);
                } else {
                    supportSQLiteStatement.bindString(29, _tmp_11);
                }
                if (value.getLaunchId() == null) {
                    supportSQLiteStatement.bindNull(30);
                } else {
                    supportSQLiteStatement.bindString(30, value.getLaunchId());
                }
                supportSQLiteStatement.bindLong(31, (long) value.getLaunchSource());
                ExternalIds _tmpExternalIds = value.getExternalIds();
                if (_tmpExternalIds != null) {
                    if (_tmpExternalIds.getImdbId() == null) {
                        supportSQLiteStatement.bindNull(32);
                    } else {
                        supportSQLiteStatement.bindString(32, _tmpExternalIds.getImdbId());
                    }
                    supportSQLiteStatement.bindLong(33, (long) _tmpExternalIds.getTvdbId());
                    if (_tmpExternalIds.getFacebookId() == null) {
                        supportSQLiteStatement.bindNull(34);
                    } else {
                        supportSQLiteStatement.bindString(34, _tmpExternalIds.getFacebookId());
                    }
                    if (_tmpExternalIds.getInstagramId() == null) {
                        supportSQLiteStatement.bindNull(35);
                    } else {
                        supportSQLiteStatement.bindString(35, _tmpExternalIds.getInstagramId());
                    }
                    if (_tmpExternalIds.getTwitterId() == null) {
                        supportSQLiteStatement.bindNull(36);
                    } else {
                        supportSQLiteStatement.bindString(36, _tmpExternalIds.getTwitterId());
                    }
                } else {
                    Long l = _tmp_1;
                    supportSQLiteStatement.bindNull(32);
                    supportSQLiteStatement.bindNull(33);
                    supportSQLiteStatement.bindNull(34);
                    supportSQLiteStatement.bindNull(35);
                    supportSQLiteStatement.bindNull(36);
                }
                supportSQLiteStatement.bindLong(37, (long) value.getId());
            }
        };
    }

    public void addShow(Show show) {
        this.__db.beginTransaction();
        try {
            this.__insertionAdapterOfShow.insert(show);
            this.__db.setTransactionSuccessful();
        } finally {
            this.__db.endTransaction();
        }
    }

    public void deleteShow(Show show) {
        this.__db.beginTransaction();
        try {
            this.__deletionAdapterOfShow.handle(show);
            this.__db.setTransactionSuccessful();
        } finally {
            this.__db.endTransaction();
        }
    }

    public void updateShow(Show show) {
        this.__db.beginTransaction();
        try {
            this.__updateAdapterOfShow.handle(show);
            this.__db.setTransactionSuccessful();
        } finally {
            this.__db.endTransaction();
        }
    }

    public LiveData<List<ShowBasic>> getShowList() {
        String _sql = "SELECT id, name, posterPath FROM Show ORDER BY name ASC";
        final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire("SELECT id, name, posterPath FROM Show ORDER BY name ASC", 0);
        return new ComputableLiveData<List<ShowBasic>>() {
            private Observer _observer;

            protected List<ShowBasic> compute() {
                if (this._observer == null) {
                    this._observer = new Observer("Show", new String[0]) {
                        public void onInvalidated(@NonNull Set<String> set) {
                            AnonymousClass4.this.invalidate();
                        }
                    };
                    ShowDao_Impl.this.__db.getInvalidationTracker().addWeakObserver(this._observer);
                }
                Cursor _cursor = ShowDao_Impl.this.__db.query(_statement);
                try {
                    int _cursorIndexOfId = _cursor.getColumnIndexOrThrow("id");
                    int _cursorIndexOfName = _cursor.getColumnIndexOrThrow("name");
                    int _cursorIndexOfPosterPath = _cursor.getColumnIndexOrThrow("posterPath");
                    List<ShowBasic> _result = new ArrayList(_cursor.getCount());
                    while (_cursor.moveToNext()) {
                        _result.add(new ShowBasic(_cursor.getInt(_cursorIndexOfId), _cursor.getString(_cursorIndexOfName), _cursor.getString(_cursorIndexOfPosterPath)));
                    }
                    return _result;
                } finally {
                    _cursor.close();
                }
            }

            protected void finalize() {
                _statement.release();
            }
        }.getLiveData();
    }

    public List<Show> getShowListToUpdate() {
        Throwable th;
        String _sql = "SELECT * FROM Show";
        RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire("SELECT * FROM Show", 0);
        Cursor _cursor = this.__db.query(_statement);
        RoomSQLiteQuery _statement2;
        try {
            int _cursorIndexOfId = _cursor.getColumnIndexOrThrow("id");
            int _cursorIndexOfName = _cursor.getColumnIndexOrThrow("name");
            int _cursorIndexOfOriginalName = _cursor.getColumnIndexOrThrow("originalName");
            int _cursorIndexOfNumberOfSeasons = _cursor.getColumnIndexOrThrow("numberOfSeasons");
            int _cursorIndexOfNumberOfEpisodes = _cursor.getColumnIndexOrThrow("numberOfEpisodes");
            int _cursorIndexOfEpisodeRuntime = _cursor.getColumnIndexOrThrow("episodeRuntime");
            int _cursorIndexOfInProduction = _cursor.getColumnIndexOrThrow("inProduction");
            int _cursorIndexOfStatus = _cursor.getColumnIndexOrThrow(NotificationCompat.CATEGORY_STATUS);
            int _cursorIndexOfType = _cursor.getColumnIndexOrThrow("type");
            int _cursorIndexOfFirstAirDate = _cursor.getColumnIndexOrThrow("firstAirDate");
            int _cursorIndexOfLastAirDate = _cursor.getColumnIndexOrThrow("lastAirDate");
            int _cursorIndexOfPopularity = _cursor.getColumnIndexOrThrow("popularity");
            try {
                _sql = _cursor.getColumnIndexOrThrow("voteAverage");
                int _cursorIndexOfVoteCount = _cursor.getColumnIndexOrThrow("voteCount");
                _statement2 = _statement;
                try {
                    int _cursorIndexOfVoteCount2;
                    int _cursorIndexOfOverview;
                    int _cursorIndexOfPosterPath;
                    int _cursorIndexOfBackdropPath;
                    int _cursorIndexOfHomepage;
                    int _cursorIndexOfOriginCountry;
                    int _cursorIndexOfOriginalLanguage;
                    int _cursorIndexOfCreatedByList;
                    int _cursorIndexOfGenresList;
                    int _cursorIndexOfNetworkList;
                    int _cursorIndexOfProductionCompanyList;
                    int _cursorIndexOfCast;
                    int _cursorIndexOfContentRatingList;
                    int _cursorIndexOfRecommendationList;
                    int _cursorIndexOfSimilarList;
                    int _cursorIndexOfVideoList;
                    int _cursorIndexOfLaunchId;
                    int _cursorIndexOfLaunchSource;
                    int _cursorIndexOfPopularity2;
                    int _cursorIndexOfName2;
                    int _cursorIndexOfImdbId;
                    int _cursorIndexOfTvdbId;
                    int _cursorIndexOfOriginalName2;
                    int _cursorIndexOfOverview2 = _cursor.getColumnIndexOrThrow("overview");
                    int _cursorIndexOfPosterPath2 = _cursor.getColumnIndexOrThrow("posterPath");
                    int _cursorIndexOfBackdropPath2 = _cursor.getColumnIndexOrThrow("backdropPath");
                    int _cursorIndexOfHomepage2 = _cursor.getColumnIndexOrThrow("homepage");
                    int _cursorIndexOfOriginCountry2 = _cursor.getColumnIndexOrThrow("originCountry");
                    int _cursorIndexOfOriginalLanguage2 = _cursor.getColumnIndexOrThrow("originalLanguage");
                    int _cursorIndexOfCreatedByList2 = _cursor.getColumnIndexOrThrow("createdByList");
                    int _cursorIndexOfGenresList2 = _cursor.getColumnIndexOrThrow("genresList");
                    int _cursorIndexOfNetworkList2 = _cursor.getColumnIndexOrThrow("networkList");
                    int _cursorIndexOfProductionCompanyList2 = _cursor.getColumnIndexOrThrow("productionCompanyList");
                    int _cursorIndexOfCast2 = _cursor.getColumnIndexOrThrow("cast");
                    int _cursorIndexOfContentRatingList2 = _cursor.getColumnIndexOrThrow("contentRatingList");
                    int _cursorIndexOfRecommendationList2 = _cursor.getColumnIndexOrThrow("recommendationList");
                    int _cursorIndexOfSimilarList2 = _cursor.getColumnIndexOrThrow("similarList");
                    int _cursorIndexOfVideoList2 = _cursor.getColumnIndexOrThrow("videoList");
                    int _cursorIndexOfLaunchId2 = _cursor.getColumnIndexOrThrow("launchId");
                    int _cursorIndexOfLaunchSource2 = _cursor.getColumnIndexOrThrow("launchSource");
                    int _cursorIndexOfImdbId2 = _cursor.getColumnIndexOrThrow("imdbId");
                    int _cursorIndexOfTvdbId2 = _cursor.getColumnIndexOrThrow("tvdbId");
                    int _cursorIndexOfFacebookId = _cursor.getColumnIndexOrThrow("facebookId");
                    int _cursorIndexOfInstagramId = _cursor.getColumnIndexOrThrow("instagramId");
                    int _cursorIndexOfTwitterId = _cursor.getColumnIndexOrThrow("twitterId");
                    int _cursorIndexOfVoteCount3 = _cursorIndexOfVoteCount;
                    _statement = new ArrayList(_cursor.getCount());
                    while (_cursor.moveToNext()) {
                        Long _tmp_1;
                        int _cursorIndexOfVoteAverage;
                        ExternalIds _tmpExternalIds;
                        int _tmpId = _cursor.getInt(_cursorIndexOfId);
                        String _tmpName = _cursor.getString(_cursorIndexOfName);
                        String _tmpOriginalName = _cursor.getString(_cursorIndexOfOriginalName);
                        int _tmpNumberOfSeasons = _cursor.getInt(_cursorIndexOfNumberOfSeasons);
                        int _tmpNumberOfEpisodes = _cursor.getInt(_cursorIndexOfNumberOfEpisodes);
                        int _tmpEpisodeRuntime = _cursor.getInt(_cursorIndexOfEpisodeRuntime);
                        _cursorIndexOfVoteCount = _cursor.getInt(_cursorIndexOfInProduction);
                        boolean _tmpInProduction = _cursorIndexOfVoteCount != 0;
                        String _tmpStatus = _cursor.getString(_cursorIndexOfStatus);
                        String _tmpType = _cursor.getString(_cursorIndexOfType);
                        if (_cursor.isNull(_cursorIndexOfFirstAirDate)) {
                            _tmp_1 = null;
                        } else {
                            _tmp_1 = Long.valueOf(_cursor.getLong(_cursorIndexOfFirstAirDate));
                        }
                        Date _tmpFirstAirDate = DateConverter.toDate(_tmp_1);
                        if (_cursor.isNull(_cursorIndexOfLastAirDate)) {
                            _tmp_1 = null;
                        } else {
                            _tmp_1 = Long.valueOf(_cursor.getLong(_cursorIndexOfLastAirDate));
                        }
                        Date _tmpLastAirDate = DateConverter.toDate(_tmp_1);
                        double _tmpPopularity = _cursor.getDouble(_cursorIndexOfPopularity);
                        double _tmpVoteAverage = _cursor.getDouble(_sql);
                        int _cursorIndexOfId2 = _cursorIndexOfId;
                        _cursorIndexOfId = _cursorIndexOfVoteCount3;
                        int _tmpVoteCount = _cursor.getInt(_cursorIndexOfId);
                        _cursorIndexOfVoteCount2 = _cursorIndexOfId;
                        _cursorIndexOfId = _cursorIndexOfOverview2;
                        String _tmpOverview = _cursor.getString(_cursorIndexOfId);
                        _cursorIndexOfOverview = _cursorIndexOfId;
                        _cursorIndexOfId = _cursorIndexOfPosterPath2;
                        String _tmpPosterPath = _cursor.getString(_cursorIndexOfId);
                        _cursorIndexOfPosterPath = _cursorIndexOfId;
                        _cursorIndexOfId = _cursorIndexOfBackdropPath2;
                        String _tmpBackdropPath = _cursor.getString(_cursorIndexOfId);
                        _cursorIndexOfBackdropPath = _cursorIndexOfId;
                        _cursorIndexOfId = _cursorIndexOfHomepage2;
                        String _tmpHomepage = _cursor.getString(_cursorIndexOfId);
                        _cursorIndexOfHomepage = _cursorIndexOfId;
                        _cursorIndexOfId = _cursorIndexOfOriginCountry2;
                        String _tmpOriginCountry = _cursor.getString(_cursorIndexOfId);
                        _cursorIndexOfOriginCountry = _cursorIndexOfId;
                        _cursorIndexOfId = _cursorIndexOfOriginalLanguage2;
                        String _tmpOriginalLanguage = _cursor.getString(_cursorIndexOfId);
                        _cursorIndexOfOriginalLanguage = _cursorIndexOfId;
                        _cursorIndexOfId = _cursorIndexOfCreatedByList2;
                        List<CreatedBy> _tmpCreatedByList = CreatedByConverter.toList(_cursor.getString(_cursorIndexOfId));
                        _cursorIndexOfCreatedByList = _cursorIndexOfId;
                        _cursorIndexOfId = _cursorIndexOfGenresList2;
                        List<Genres> _tmpGenresList = GenresConverter.toList(_cursor.getString(_cursorIndexOfId));
                        _cursorIndexOfGenresList = _cursorIndexOfId;
                        _cursorIndexOfId = _cursorIndexOfNetworkList2;
                        List<Network> _tmpNetworkList = NetworkConverter.toList(_cursor.getString(_cursorIndexOfId));
                        _cursorIndexOfNetworkList = _cursorIndexOfId;
                        _cursorIndexOfId = _cursorIndexOfProductionCompanyList2;
                        List<ProductionCompany> _tmpProductionCompanyList = ProductionCompanyConverter.toList(_cursor.getString(_cursorIndexOfId));
                        _cursorIndexOfProductionCompanyList = _cursorIndexOfId;
                        _cursorIndexOfId = _cursorIndexOfCast2;
                        List<Cast> _tmpCast = CastConverter.toList(_cursor.getString(_cursorIndexOfId));
                        _cursorIndexOfCast = _cursorIndexOfId;
                        _cursorIndexOfId = _cursorIndexOfContentRatingList2;
                        List<ContentRating> _tmpContentRatingList = ContentRatingConverter.toList(_cursor.getString(_cursorIndexOfId));
                        _cursorIndexOfContentRatingList = _cursorIndexOfId;
                        _cursorIndexOfId = _cursorIndexOfRecommendationList2;
                        List<ShowBasic> _tmpRecommendationList = ShowBasicConverter.toList(_cursor.getString(_cursorIndexOfId));
                        _cursorIndexOfRecommendationList = _cursorIndexOfId;
                        _cursorIndexOfId = _cursorIndexOfSimilarList2;
                        List<ShowBasic> _tmpSimilarList = ShowBasicConverter.toList(_cursor.getString(_cursorIndexOfId));
                        _cursorIndexOfSimilarList = _cursorIndexOfId;
                        _cursorIndexOfId = _cursorIndexOfVideoList2;
                        List<Video> _tmpVideoList = VideoConverter.toList(_cursor.getString(_cursorIndexOfId));
                        _cursorIndexOfVideoList = _cursorIndexOfId;
                        _cursorIndexOfId = _cursorIndexOfLaunchId2;
                        String _tmpLaunchId = _cursor.getString(_cursorIndexOfId);
                        _cursorIndexOfLaunchId = _cursorIndexOfId;
                        _cursorIndexOfId = _cursorIndexOfLaunchSource2;
                        int _tmpLaunchSource = _cursor.getInt(_cursorIndexOfId);
                        _cursorIndexOfLaunchSource = _cursorIndexOfId;
                        _cursorIndexOfId = _cursorIndexOfImdbId2;
                        int i;
                        if (_cursor.isNull(_cursorIndexOfId)) {
                            _cursorIndexOfVoteAverage = _sql;
                            _sql = _cursorIndexOfTvdbId2;
                            if (_cursor.isNull(_sql)) {
                                _cursorIndexOfPopularity2 = _cursorIndexOfPopularity;
                                _cursorIndexOfPopularity = _cursorIndexOfFacebookId;
                                if (_cursor.isNull(_cursorIndexOfPopularity)) {
                                    _cursorIndexOfVoteCount = _cursorIndexOfInstagramId;
                                    if (_cursor.isNull(_cursorIndexOfVoteCount)) {
                                        _cursorIndexOfName2 = _cursorIndexOfName;
                                        _cursorIndexOfName = _cursorIndexOfTwitterId;
                                        if (_cursor.isNull(_cursorIndexOfName)) {
                                            _cursorIndexOfImdbId = _cursorIndexOfId;
                                            _cursorIndexOfTvdbId = _sql;
                                            _cursorIndexOfOriginalName2 = _cursorIndexOfOriginalName;
                                            _tmpExternalIds = null;
                                            _statement.add(new Show(_tmpId, _tmpName, _tmpOriginalName, _tmpNumberOfSeasons, _tmpNumberOfEpisodes, _tmpEpisodeRuntime, _tmpInProduction, _tmpStatus, _tmpType, _tmpFirstAirDate, _tmpLastAirDate, _tmpPopularity, _tmpVoteAverage, _tmpVoteCount, _tmpOverview, _tmpPosterPath, _tmpBackdropPath, _tmpHomepage, _tmpOriginCountry, _tmpOriginalLanguage, _tmpCreatedByList, _tmpGenresList, _tmpNetworkList, _tmpProductionCompanyList, _tmpCast, _tmpContentRatingList, _tmpExternalIds, _tmpRecommendationList, _tmpSimilarList, _tmpVideoList, _tmpLaunchId, _tmpLaunchSource));
                                            _cursorIndexOfFacebookId = _cursorIndexOfPopularity;
                                            _cursorIndexOfInstagramId = _cursorIndexOfVoteCount;
                                            _cursorIndexOfTwitterId = _cursorIndexOfName;
                                            _cursorIndexOfId = _cursorIndexOfId2;
                                            _cursorIndexOfVoteCount3 = _cursorIndexOfVoteCount2;
                                            _cursorIndexOfOverview2 = _cursorIndexOfOverview;
                                            _cursorIndexOfPosterPath2 = _cursorIndexOfPosterPath;
                                            _cursorIndexOfBackdropPath2 = _cursorIndexOfBackdropPath;
                                            _cursorIndexOfHomepage2 = _cursorIndexOfHomepage;
                                            _cursorIndexOfOriginCountry2 = _cursorIndexOfOriginCountry;
                                            _cursorIndexOfOriginalLanguage2 = _cursorIndexOfOriginalLanguage;
                                            _cursorIndexOfCreatedByList2 = _cursorIndexOfCreatedByList;
                                            _cursorIndexOfGenresList2 = _cursorIndexOfGenresList;
                                            _cursorIndexOfNetworkList2 = _cursorIndexOfNetworkList;
                                            _cursorIndexOfProductionCompanyList2 = _cursorIndexOfProductionCompanyList;
                                            _cursorIndexOfCast2 = _cursorIndexOfCast;
                                            _cursorIndexOfContentRatingList2 = _cursorIndexOfContentRatingList;
                                            _cursorIndexOfRecommendationList2 = _cursorIndexOfRecommendationList;
                                            _cursorIndexOfSimilarList2 = _cursorIndexOfSimilarList;
                                            _cursorIndexOfVideoList2 = _cursorIndexOfVideoList;
                                            _cursorIndexOfLaunchId2 = _cursorIndexOfLaunchId;
                                            _cursorIndexOfLaunchSource2 = _cursorIndexOfLaunchSource;
                                            _sql = _cursorIndexOfVoteAverage;
                                            _cursorIndexOfPopularity = _cursorIndexOfPopularity2;
                                            _cursorIndexOfName = _cursorIndexOfName2;
                                            _cursorIndexOfImdbId2 = _cursorIndexOfImdbId;
                                            _cursorIndexOfOriginalName = _cursorIndexOfOriginalName2;
                                            _cursorIndexOfTvdbId2 = _cursorIndexOfTvdbId;
                                        }
                                    } else {
                                        _cursorIndexOfName2 = _cursorIndexOfName;
                                        _cursorIndexOfName = _cursorIndexOfTwitterId;
                                    }
                                } else {
                                    _cursorIndexOfName2 = _cursorIndexOfName;
                                    _cursorIndexOfVoteCount = _cursorIndexOfInstagramId;
                                    _cursorIndexOfName = _cursorIndexOfTwitterId;
                                }
                            } else {
                                _cursorIndexOfPopularity2 = _cursorIndexOfPopularity;
                                i = _cursorIndexOfVoteCount;
                                _cursorIndexOfName2 = _cursorIndexOfName;
                                _cursorIndexOfPopularity = _cursorIndexOfFacebookId;
                                _cursorIndexOfVoteCount = _cursorIndexOfInstagramId;
                                _cursorIndexOfName = _cursorIndexOfTwitterId;
                            }
                        } else {
                            _cursorIndexOfVoteAverage = _sql;
                            _cursorIndexOfPopularity2 = _cursorIndexOfPopularity;
                            i = _cursorIndexOfVoteCount;
                            _cursorIndexOfName2 = _cursorIndexOfName;
                            _sql = _cursorIndexOfTvdbId2;
                            _cursorIndexOfPopularity = _cursorIndexOfFacebookId;
                            _cursorIndexOfVoteCount = _cursorIndexOfInstagramId;
                            _cursorIndexOfName = _cursorIndexOfTwitterId;
                        }
                        _cursorIndexOfImdbId = _cursorIndexOfId;
                        _cursorIndexOfOriginalName2 = _cursorIndexOfOriginalName;
                        _cursorIndexOfId = new ExternalIds();
                        _cursorIndexOfId.setImdbId(_cursor.getString(_cursorIndexOfId));
                        _cursorIndexOfTvdbId = _sql;
                        _sql = _cursor.getInt(_sql);
                        _cursorIndexOfId.setTvdbId(_sql);
                        int _tmpTvdbId = _sql;
                        _sql = _cursor.getString(_cursorIndexOfPopularity);
                        _cursorIndexOfId.setFacebookId(_sql);
                        String _tmpFacebookId = _sql;
                        _sql = _cursor.getString(_cursorIndexOfVoteCount);
                        _cursorIndexOfId.setInstagramId(_sql);
                        String _tmpInstagramId = _sql;
                        _cursorIndexOfId.setTwitterId(_cursor.getString(_cursorIndexOfName));
                        _tmpExternalIds = _cursorIndexOfId;
                        _statement.add(new Show(_tmpId, _tmpName, _tmpOriginalName, _tmpNumberOfSeasons, _tmpNumberOfEpisodes, _tmpEpisodeRuntime, _tmpInProduction, _tmpStatus, _tmpType, _tmpFirstAirDate, _tmpLastAirDate, _tmpPopularity, _tmpVoteAverage, _tmpVoteCount, _tmpOverview, _tmpPosterPath, _tmpBackdropPath, _tmpHomepage, _tmpOriginCountry, _tmpOriginalLanguage, _tmpCreatedByList, _tmpGenresList, _tmpNetworkList, _tmpProductionCompanyList, _tmpCast, _tmpContentRatingList, _tmpExternalIds, _tmpRecommendationList, _tmpSimilarList, _tmpVideoList, _tmpLaunchId, _tmpLaunchSource));
                        _cursorIndexOfFacebookId = _cursorIndexOfPopularity;
                        _cursorIndexOfInstagramId = _cursorIndexOfVoteCount;
                        _cursorIndexOfTwitterId = _cursorIndexOfName;
                        _cursorIndexOfId = _cursorIndexOfId2;
                        _cursorIndexOfVoteCount3 = _cursorIndexOfVoteCount2;
                        _cursorIndexOfOverview2 = _cursorIndexOfOverview;
                        _cursorIndexOfPosterPath2 = _cursorIndexOfPosterPath;
                        _cursorIndexOfBackdropPath2 = _cursorIndexOfBackdropPath;
                        _cursorIndexOfHomepage2 = _cursorIndexOfHomepage;
                        _cursorIndexOfOriginCountry2 = _cursorIndexOfOriginCountry;
                        _cursorIndexOfOriginalLanguage2 = _cursorIndexOfOriginalLanguage;
                        _cursorIndexOfCreatedByList2 = _cursorIndexOfCreatedByList;
                        _cursorIndexOfGenresList2 = _cursorIndexOfGenresList;
                        _cursorIndexOfNetworkList2 = _cursorIndexOfNetworkList;
                        _cursorIndexOfProductionCompanyList2 = _cursorIndexOfProductionCompanyList;
                        _cursorIndexOfCast2 = _cursorIndexOfCast;
                        _cursorIndexOfContentRatingList2 = _cursorIndexOfContentRatingList;
                        _cursorIndexOfRecommendationList2 = _cursorIndexOfRecommendationList;
                        _cursorIndexOfSimilarList2 = _cursorIndexOfSimilarList;
                        _cursorIndexOfVideoList2 = _cursorIndexOfVideoList;
                        _cursorIndexOfLaunchId2 = _cursorIndexOfLaunchId;
                        _cursorIndexOfLaunchSource2 = _cursorIndexOfLaunchSource;
                        _sql = _cursorIndexOfVoteAverage;
                        _cursorIndexOfPopularity = _cursorIndexOfPopularity2;
                        _cursorIndexOfName = _cursorIndexOfName2;
                        _cursorIndexOfImdbId2 = _cursorIndexOfImdbId;
                        _cursorIndexOfOriginalName = _cursorIndexOfOriginalName2;
                        _cursorIndexOfTvdbId2 = _cursorIndexOfTvdbId;
                    }
                    String str = _sql;
                    _cursorIndexOfPopularity2 = _cursorIndexOfPopularity;
                    _cursorIndexOfName2 = _cursorIndexOfName;
                    _cursorIndexOfOriginalName2 = _cursorIndexOfOriginalName;
                    _cursorIndexOfOverview = _cursorIndexOfOverview2;
                    _cursorIndexOfPosterPath = _cursorIndexOfPosterPath2;
                    _cursorIndexOfBackdropPath = _cursorIndexOfBackdropPath2;
                    _cursorIndexOfHomepage = _cursorIndexOfHomepage2;
                    _cursorIndexOfOriginCountry = _cursorIndexOfOriginCountry2;
                    _cursorIndexOfOriginalLanguage = _cursorIndexOfOriginalLanguage2;
                    _cursorIndexOfCreatedByList = _cursorIndexOfCreatedByList2;
                    _cursorIndexOfGenresList = _cursorIndexOfGenresList2;
                    _cursorIndexOfNetworkList = _cursorIndexOfNetworkList2;
                    _cursorIndexOfProductionCompanyList = _cursorIndexOfProductionCompanyList2;
                    _cursorIndexOfCast = _cursorIndexOfCast2;
                    _cursorIndexOfContentRatingList = _cursorIndexOfContentRatingList2;
                    _cursorIndexOfRecommendationList = _cursorIndexOfRecommendationList2;
                    _cursorIndexOfSimilarList = _cursorIndexOfSimilarList2;
                    _cursorIndexOfVideoList = _cursorIndexOfVideoList2;
                    _cursorIndexOfLaunchId = _cursorIndexOfLaunchId2;
                    _cursorIndexOfLaunchSource = _cursorIndexOfLaunchSource2;
                    _cursorIndexOfImdbId = _cursorIndexOfImdbId2;
                    _cursorIndexOfTvdbId = _cursorIndexOfTvdbId2;
                    _cursorIndexOfVoteCount = _cursorIndexOfInstagramId;
                    _cursorIndexOfVoteCount2 = _cursorIndexOfVoteCount3;
                    _cursor.close();
                    _statement2.release();
                    return _statement;
                } catch (Throwable th2) {
                    th = th2;
                    _cursor.close();
                    _statement2.release();
                    throw th;
                }
            } catch (Throwable th3) {
                th = th3;
                _statement2 = _statement;
                _cursor.close();
                _statement2.release();
                throw th;
            }
        } catch (Throwable th4) {
            th = th4;
            String str2 = _sql;
            _statement2 = _statement;
            _cursor.close();
            _statement2.release();
            throw th;
        }
    }

    public LiveData<Show> getShowById(int id) {
        String _sql = "SELECT * FROM Show WHERE id = ?";
        final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire("SELECT * FROM Show WHERE id = ?", 1);
        _statement.bindLong(1, (long) id);
        return new ComputableLiveData<Show>() {
            private Observer _observer;

            protected Show compute() {
                if (this._observer == null) {
                    r1._observer = new Observer("Show", new String[0]) {
                        public void onInvalidated(@NonNull Set<String> set) {
                            AnonymousClass5.this.invalidate();
                        }
                    };
                    ShowDao_Impl.this.__db.getInvalidationTracker().addWeakObserver(r1._observer);
                }
                Cursor _cursor = ShowDao_Impl.this.__db.query(_statement);
                try {
                    int _cursorIndexOfId = _cursor.getColumnIndexOrThrow("id");
                    int _cursorIndexOfName = _cursor.getColumnIndexOrThrow("name");
                    int _cursorIndexOfOriginalName = _cursor.getColumnIndexOrThrow("originalName");
                    int _cursorIndexOfNumberOfSeasons = _cursor.getColumnIndexOrThrow("numberOfSeasons");
                    int _cursorIndexOfNumberOfEpisodes = _cursor.getColumnIndexOrThrow("numberOfEpisodes");
                    int _cursorIndexOfEpisodeRuntime = _cursor.getColumnIndexOrThrow("episodeRuntime");
                    int _cursorIndexOfInProduction = _cursor.getColumnIndexOrThrow("inProduction");
                    int _cursorIndexOfStatus = _cursor.getColumnIndexOrThrow(NotificationCompat.CATEGORY_STATUS);
                    int _cursorIndexOfType = _cursor.getColumnIndexOrThrow("type");
                    int _cursorIndexOfFirstAirDate = _cursor.getColumnIndexOrThrow("firstAirDate");
                    int _cursorIndexOfLastAirDate = _cursor.getColumnIndexOrThrow("lastAirDate");
                    int _cursorIndexOfPopularity = _cursor.getColumnIndexOrThrow("popularity");
                    int _cursorIndexOfVoteAverage = _cursor.getColumnIndexOrThrow("voteAverage");
                    int _cursorIndexOfVoteCount = _cursor.getColumnIndexOrThrow("voteCount");
                    int _cursorIndexOfOverview = _cursor.getColumnIndexOrThrow("overview");
                    int _cursorIndexOfPosterPath = _cursor.getColumnIndexOrThrow("posterPath");
                    int _cursorIndexOfBackdropPath = _cursor.getColumnIndexOrThrow("backdropPath");
                    int _cursorIndexOfHomepage = _cursor.getColumnIndexOrThrow("homepage");
                    int _cursorIndexOfOriginCountry = _cursor.getColumnIndexOrThrow("originCountry");
                    int _cursorIndexOfOriginalLanguage = _cursor.getColumnIndexOrThrow("originalLanguage");
                    int _cursorIndexOfCreatedByList = _cursor.getColumnIndexOrThrow("createdByList");
                    int _cursorIndexOfGenresList = _cursor.getColumnIndexOrThrow("genresList");
                    int _cursorIndexOfNetworkList = _cursor.getColumnIndexOrThrow("networkList");
                    int _cursorIndexOfProductionCompanyList = _cursor.getColumnIndexOrThrow("productionCompanyList");
                    int _cursorIndexOfCast = _cursor.getColumnIndexOrThrow("cast");
                    int _cursorIndexOfContentRatingList = _cursor.getColumnIndexOrThrow("contentRatingList");
                    int _cursorIndexOfRecommendationList = _cursor.getColumnIndexOrThrow("recommendationList");
                    int _cursorIndexOfSimilarList = _cursor.getColumnIndexOrThrow("similarList");
                    int _cursorIndexOfVideoList = _cursor.getColumnIndexOrThrow("videoList");
                    int _cursorIndexOfLaunchId = _cursor.getColumnIndexOrThrow("launchId");
                    int _cursorIndexOfLaunchSource = _cursor.getColumnIndexOrThrow("launchSource");
                    int _cursorIndexOfImdbId = _cursor.getColumnIndexOrThrow("imdbId");
                    int _cursorIndexOfTvdbId = _cursor.getColumnIndexOrThrow("tvdbId");
                    int _cursorIndexOfFacebookId = _cursor.getColumnIndexOrThrow("facebookId");
                    int _cursorIndexOfInstagramId = _cursor.getColumnIndexOrThrow("instagramId");
                    int _cursorIndexOfTwitterId = _cursor.getColumnIndexOrThrow("twitterId");
                    Show _result = null;
                    int _cursorIndexOfOverview2;
                    int _cursorIndexOfPosterPath2;
                    int _cursorIndexOfBackdropPath2;
                    int _cursorIndexOfHomepage2;
                    int _cursorIndexOfOriginCountry2;
                    int _cursorIndexOfOriginalLanguage2;
                    int _cursorIndexOfCreatedByList2;
                    int _cursorIndexOfGenresList2;
                    int _cursorIndexOfNetworkList2;
                    int _cursorIndexOfProductionCompanyList2;
                    int _cursorIndexOfCast2;
                    int _cursorIndexOfContentRatingList2;
                    int _cursorIndexOfRecommendationList2;
                    int _cursorIndexOfSimilarList2;
                    int _cursorIndexOfVideoList2;
                    int _cursorIndexOfLaunchId2;
                    int _cursorIndexOfLaunchSource2;
                    int i;
                    int i2;
                    int i3;
                    int i4;
                    int i5;
                    int i6;
                    if (_cursor.moveToFirst()) {
                        Long _tmp_1;
                        Long _tmp_2;
                        ExternalIds _tmpExternalIds;
                        int _tmpId = _cursor.getInt(_cursorIndexOfId);
                        String _tmpName = _cursor.getString(_cursorIndexOfName);
                        String _tmpOriginalName = _cursor.getString(_cursorIndexOfOriginalName);
                        int _tmpNumberOfSeasons = _cursor.getInt(_cursorIndexOfNumberOfSeasons);
                        int _tmpNumberOfEpisodes = _cursor.getInt(_cursorIndexOfNumberOfEpisodes);
                        int _tmpEpisodeRuntime = _cursor.getInt(_cursorIndexOfEpisodeRuntime);
                        boolean _tmpInProduction = _cursor.getInt(_cursorIndexOfInProduction) != 0;
                        String _tmpStatus = _cursor.getString(_cursorIndexOfStatus);
                        String _tmpType = _cursor.getString(_cursorIndexOfType);
                        if (_cursor.isNull(_cursorIndexOfFirstAirDate)) {
                            _tmp_1 = null;
                        } else {
                            _tmp_1 = Long.valueOf(_cursor.getLong(_cursorIndexOfFirstAirDate));
                        }
                        Date _tmpFirstAirDate = DateConverter.toDate(_tmp_1);
                        if (_cursor.isNull(_cursorIndexOfLastAirDate)) {
                            _tmp_2 = null;
                        } else {
                            _tmp_2 = Long.valueOf(_cursor.getLong(_cursorIndexOfLastAirDate));
                        }
                        Date _tmpLastAirDate = DateConverter.toDate(_tmp_2);
                        double _tmpPopularity = _cursor.getDouble(_cursorIndexOfPopularity);
                        double _tmpVoteAverage = _cursor.getDouble(_cursorIndexOfVoteAverage);
                        int _tmpVoteCount = _cursor.getInt(_cursorIndexOfVoteCount);
                        _cursorIndexOfId = _cursorIndexOfOverview;
                        String _tmpOverview = _cursor.getString(_cursorIndexOfId);
                        _cursorIndexOfOverview2 = _cursorIndexOfId;
                        _cursorIndexOfId = _cursorIndexOfPosterPath;
                        String _tmpPosterPath = _cursor.getString(_cursorIndexOfId);
                        _cursorIndexOfPosterPath2 = _cursorIndexOfId;
                        _cursorIndexOfId = _cursorIndexOfBackdropPath;
                        String _tmpBackdropPath = _cursor.getString(_cursorIndexOfId);
                        _cursorIndexOfBackdropPath2 = _cursorIndexOfId;
                        _cursorIndexOfId = _cursorIndexOfHomepage;
                        String _tmpHomepage = _cursor.getString(_cursorIndexOfId);
                        _cursorIndexOfHomepage2 = _cursorIndexOfId;
                        _cursorIndexOfId = _cursorIndexOfOriginCountry;
                        String _tmpOriginCountry = _cursor.getString(_cursorIndexOfId);
                        _cursorIndexOfOriginCountry2 = _cursorIndexOfId;
                        _cursorIndexOfId = _cursorIndexOfOriginalLanguage;
                        String _tmpOriginalLanguage = _cursor.getString(_cursorIndexOfId);
                        _cursorIndexOfOriginalLanguage2 = _cursorIndexOfId;
                        _cursorIndexOfId = _cursorIndexOfCreatedByList;
                        List<CreatedBy> _tmpCreatedByList = CreatedByConverter.toList(_cursor.getString(_cursorIndexOfId));
                        _cursorIndexOfCreatedByList2 = _cursorIndexOfId;
                        _cursorIndexOfId = _cursorIndexOfGenresList;
                        List<Genres> _tmpGenresList = GenresConverter.toList(_cursor.getString(_cursorIndexOfId));
                        _cursorIndexOfGenresList2 = _cursorIndexOfId;
                        _cursorIndexOfId = _cursorIndexOfNetworkList;
                        List<Network> _tmpNetworkList = NetworkConverter.toList(_cursor.getString(_cursorIndexOfId));
                        _cursorIndexOfNetworkList2 = _cursorIndexOfId;
                        _cursorIndexOfId = _cursorIndexOfProductionCompanyList;
                        List<ProductionCompany> _tmpProductionCompanyList = ProductionCompanyConverter.toList(_cursor.getString(_cursorIndexOfId));
                        _cursorIndexOfProductionCompanyList2 = _cursorIndexOfId;
                        _cursorIndexOfId = _cursorIndexOfCast;
                        List<Cast> _tmpCast = CastConverter.toList(_cursor.getString(_cursorIndexOfId));
                        _cursorIndexOfCast2 = _cursorIndexOfId;
                        _cursorIndexOfId = _cursorIndexOfContentRatingList;
                        List<ContentRating> _tmpContentRatingList = ContentRatingConverter.toList(_cursor.getString(_cursorIndexOfId));
                        _cursorIndexOfContentRatingList2 = _cursorIndexOfId;
                        _cursorIndexOfId = _cursorIndexOfRecommendationList;
                        List<ShowBasic> _tmpRecommendationList = ShowBasicConverter.toList(_cursor.getString(_cursorIndexOfId));
                        _cursorIndexOfRecommendationList2 = _cursorIndexOfId;
                        _cursorIndexOfId = _cursorIndexOfSimilarList;
                        List<ShowBasic> _tmpSimilarList = ShowBasicConverter.toList(_cursor.getString(_cursorIndexOfId));
                        _cursorIndexOfSimilarList2 = _cursorIndexOfId;
                        _cursorIndexOfId = _cursorIndexOfVideoList;
                        List<Video> _tmpVideoList = VideoConverter.toList(_cursor.getString(_cursorIndexOfId));
                        _cursorIndexOfVideoList2 = _cursorIndexOfId;
                        _cursorIndexOfId = _cursorIndexOfLaunchId;
                        String _tmpLaunchId = _cursor.getString(_cursorIndexOfId);
                        _cursorIndexOfLaunchId2 = _cursorIndexOfId;
                        _cursorIndexOfId = _cursorIndexOfLaunchSource;
                        int _tmpLaunchSource = _cursor.getInt(_cursorIndexOfId);
                        _cursorIndexOfLaunchSource2 = _cursorIndexOfId;
                        _cursorIndexOfId = _cursorIndexOfImdbId;
                        if (_cursor.isNull(_cursorIndexOfId)) {
                            _cursorIndexOfVoteCount = _cursorIndexOfTvdbId;
                            if (_cursor.isNull(_cursorIndexOfVoteCount)) {
                                _cursorIndexOfName = _cursorIndexOfFacebookId;
                                if (_cursor.isNull(_cursorIndexOfName)) {
                                    _cursorIndexOfOriginalName = _cursorIndexOfInstagramId;
                                    if (_cursor.isNull(_cursorIndexOfOriginalName)) {
                                        if (_cursor.isNull(_cursorIndexOfTwitterId)) {
                                            i = _cursorIndexOfId;
                                            i2 = _cursorIndexOfTwitterId;
                                            i3 = _cursorIndexOfVoteCount;
                                            i4 = _cursorIndexOfNumberOfSeasons;
                                            _tmpExternalIds = null;
                                            _result = new Show(_tmpId, _tmpName, _tmpOriginalName, _tmpNumberOfSeasons, _tmpNumberOfEpisodes, _tmpEpisodeRuntime, _tmpInProduction, _tmpStatus, _tmpType, _tmpFirstAirDate, _tmpLastAirDate, _tmpPopularity, _tmpVoteAverage, _tmpVoteCount, _tmpOverview, _tmpPosterPath, _tmpBackdropPath, _tmpHomepage, _tmpOriginCountry, _tmpOriginalLanguage, _tmpCreatedByList, _tmpGenresList, _tmpNetworkList, _tmpProductionCompanyList, _tmpCast, _tmpContentRatingList, _tmpExternalIds, _tmpRecommendationList, _tmpSimilarList, _tmpVideoList, _tmpLaunchId, _tmpLaunchSource);
                                        }
                                    }
                                } else {
                                    _cursorIndexOfOriginalName = _cursorIndexOfInstagramId;
                                }
                            } else {
                                i5 = _cursorIndexOfOriginalName;
                                _cursorIndexOfName = _cursorIndexOfFacebookId;
                                _cursorIndexOfOriginalName = _cursorIndexOfInstagramId;
                            }
                        } else {
                            i6 = _cursorIndexOfName;
                            i5 = _cursorIndexOfOriginalName;
                            _cursorIndexOfVoteCount = _cursorIndexOfTvdbId;
                            _cursorIndexOfName = _cursorIndexOfFacebookId;
                            _cursorIndexOfOriginalName = _cursorIndexOfInstagramId;
                        }
                        i = _cursorIndexOfId;
                        _cursorIndexOfId = new ExternalIds();
                        _cursorIndexOfId.setImdbId(_cursor.getString(_cursorIndexOfId));
                        _cursorIndexOfVoteCount = _cursor.getInt(_cursorIndexOfVoteCount);
                        _cursorIndexOfId.setTvdbId(_cursorIndexOfVoteCount);
                        String _tmpFacebookId = _cursor.getString(_cursorIndexOfName);
                        _cursorIndexOfId.setFacebookId(_tmpFacebookId);
                        _cursorIndexOfId.setInstagramId(_cursor.getString(_cursorIndexOfOriginalName));
                        _cursorIndexOfId.setTwitterId(_cursor.getString(_cursorIndexOfTwitterId));
                        _tmpExternalIds = _cursorIndexOfId;
                        _result = new Show(_tmpId, _tmpName, _tmpOriginalName, _tmpNumberOfSeasons, _tmpNumberOfEpisodes, _tmpEpisodeRuntime, _tmpInProduction, _tmpStatus, _tmpType, _tmpFirstAirDate, _tmpLastAirDate, _tmpPopularity, _tmpVoteAverage, _tmpVoteCount, _tmpOverview, _tmpPosterPath, _tmpBackdropPath, _tmpHomepage, _tmpOriginCountry, _tmpOriginalLanguage, _tmpCreatedByList, _tmpGenresList, _tmpNetworkList, _tmpProductionCompanyList, _tmpCast, _tmpContentRatingList, _tmpExternalIds, _tmpRecommendationList, _tmpSimilarList, _tmpVideoList, _tmpLaunchId, _tmpLaunchSource);
                    } else {
                        i2 = _cursorIndexOfTwitterId;
                        int i7 = _cursorIndexOfVoteCount;
                        i6 = _cursorIndexOfName;
                        i5 = _cursorIndexOfOriginalName;
                        i4 = _cursorIndexOfNumberOfSeasons;
                        _cursorIndexOfOverview2 = _cursorIndexOfOverview;
                        _cursorIndexOfPosterPath2 = _cursorIndexOfPosterPath;
                        _cursorIndexOfBackdropPath2 = _cursorIndexOfBackdropPath;
                        _cursorIndexOfHomepage2 = _cursorIndexOfHomepage;
                        _cursorIndexOfOriginCountry2 = _cursorIndexOfOriginCountry;
                        _cursorIndexOfOriginalLanguage2 = _cursorIndexOfOriginalLanguage;
                        _cursorIndexOfCreatedByList2 = _cursorIndexOfCreatedByList;
                        _cursorIndexOfGenresList2 = _cursorIndexOfGenresList;
                        _cursorIndexOfNetworkList2 = _cursorIndexOfNetworkList;
                        _cursorIndexOfProductionCompanyList2 = _cursorIndexOfProductionCompanyList;
                        _cursorIndexOfCast2 = _cursorIndexOfCast;
                        _cursorIndexOfContentRatingList2 = _cursorIndexOfContentRatingList;
                        _cursorIndexOfRecommendationList2 = _cursorIndexOfRecommendationList;
                        _cursorIndexOfSimilarList2 = _cursorIndexOfSimilarList;
                        _cursorIndexOfVideoList2 = _cursorIndexOfVideoList;
                        _cursorIndexOfLaunchId2 = _cursorIndexOfLaunchId;
                        _cursorIndexOfLaunchSource2 = _cursorIndexOfLaunchSource;
                        i = _cursorIndexOfImdbId;
                        i3 = _cursorIndexOfTvdbId;
                    }
                    Show _result2 = _result;
                    _cursor.close();
                    return _result2;
                } catch (Throwable th) {
                    _cursor.close();
                }
            }

            protected void finalize() {
                _statement.release();
            }
        }.getLiveData();
    }
}
