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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import jdr.tvtracker.activities.main.fragments.episodeItem.EpisodeItem;
import jdr.tvtracker.data.entities.Episode;
import jdr.tvtracker.data.local.converter.DateConverter;

public class EpisodeDao_Impl implements EpisodeDao {
    private final RoomDatabase __db;
    private final EntityDeletionOrUpdateAdapter __deletionAdapterOfEpisode;
    private final EntityInsertionAdapter __insertionAdapterOfEpisode;
    private final EntityDeletionOrUpdateAdapter __updateAdapterOfEpisode;

    public EpisodeDao_Impl(RoomDatabase __db) {
        this.__db = __db;
        this.__insertionAdapterOfEpisode = new EntityInsertionAdapter<Episode>(__db) {
            public String createQuery() {
                return "INSERT OR REPLACE INTO `Episode`(`id`,`showId`,`episodeNumber`,`seasonNumber`,`productionCode`,`name`,`airDate`,`overview`,`stillPath`,`voteAverage`,`voteCount`,`watched`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
            }

            public void bind(SupportSQLiteStatement stmt, Episode value) {
                stmt.bindLong(1, (long) value.getId());
                stmt.bindLong(2, (long) value.getShowId());
                stmt.bindLong(3, (long) value.getEpisodeNumber());
                stmt.bindLong(4, (long) value.getSeasonNumber());
                if (value.getProductionCode() == null) {
                    stmt.bindNull(5);
                } else {
                    stmt.bindString(5, value.getProductionCode());
                }
                if (value.getName() == null) {
                    stmt.bindNull(6);
                } else {
                    stmt.bindString(6, value.getName());
                }
                Long _tmp = DateConverter.toTimestamp(value.getAirDate());
                if (_tmp == null) {
                    stmt.bindNull(7);
                } else {
                    stmt.bindLong(7, _tmp.longValue());
                }
                if (value.getOverview() == null) {
                    stmt.bindNull(8);
                } else {
                    stmt.bindString(8, value.getOverview());
                }
                if (value.getStillPath() == null) {
                    stmt.bindNull(9);
                } else {
                    stmt.bindString(9, value.getStillPath());
                }
                stmt.bindDouble(10, value.getVoteAverage());
                stmt.bindLong(11, (long) value.getVoteCount());
                stmt.bindLong(12, (long) value.isWatched());
            }
        };
        this.__deletionAdapterOfEpisode = new EntityDeletionOrUpdateAdapter<Episode>(__db) {
            public String createQuery() {
                return "DELETE FROM `Episode` WHERE `id` = ?";
            }

            public void bind(SupportSQLiteStatement stmt, Episode value) {
                stmt.bindLong(1, (long) value.getId());
            }
        };
        this.__updateAdapterOfEpisode = new EntityDeletionOrUpdateAdapter<Episode>(__db) {
            public String createQuery() {
                return "UPDATE OR ABORT `Episode` SET `id` = ?,`showId` = ?,`episodeNumber` = ?,`seasonNumber` = ?,`productionCode` = ?,`name` = ?,`airDate` = ?,`overview` = ?,`stillPath` = ?,`voteAverage` = ?,`voteCount` = ?,`watched` = ? WHERE `id` = ?";
            }

            public void bind(SupportSQLiteStatement stmt, Episode value) {
                stmt.bindLong(1, (long) value.getId());
                stmt.bindLong(2, (long) value.getShowId());
                stmt.bindLong(3, (long) value.getEpisodeNumber());
                stmt.bindLong(4, (long) value.getSeasonNumber());
                if (value.getProductionCode() == null) {
                    stmt.bindNull(5);
                } else {
                    stmt.bindString(5, value.getProductionCode());
                }
                if (value.getName() == null) {
                    stmt.bindNull(6);
                } else {
                    stmt.bindString(6, value.getName());
                }
                Long _tmp = DateConverter.toTimestamp(value.getAirDate());
                if (_tmp == null) {
                    stmt.bindNull(7);
                } else {
                    stmt.bindLong(7, _tmp.longValue());
                }
                if (value.getOverview() == null) {
                    stmt.bindNull(8);
                } else {
                    stmt.bindString(8, value.getOverview());
                }
                if (value.getStillPath() == null) {
                    stmt.bindNull(9);
                } else {
                    stmt.bindString(9, value.getStillPath());
                }
                stmt.bindDouble(10, value.getVoteAverage());
                stmt.bindLong(11, (long) value.getVoteCount());
                stmt.bindLong(12, (long) value.isWatched());
                stmt.bindLong(13, (long) value.getId());
            }
        };
    }

    public void addEpisode(Episode episode) {
        this.__db.beginTransaction();
        try {
            this.__insertionAdapterOfEpisode.insert(episode);
            this.__db.setTransactionSuccessful();
        } finally {
            this.__db.endTransaction();
        }
    }

    public void addEpisodeList(List<Episode> episodeList) {
        this.__db.beginTransaction();
        try {
            this.__insertionAdapterOfEpisode.insert(episodeList);
            this.__db.setTransactionSuccessful();
        } finally {
            this.__db.endTransaction();
        }
    }

    public void deleteEpisode(Episode episode) {
        this.__db.beginTransaction();
        try {
            this.__deletionAdapterOfEpisode.handle(episode);
            this.__db.setTransactionSuccessful();
        } finally {
            this.__db.endTransaction();
        }
    }

    public void updateEpisode(Episode episode) {
        this.__db.beginTransaction();
        try {
            this.__updateAdapterOfEpisode.handle(episode);
            this.__db.setTransactionSuccessful();
        } finally {
            this.__db.endTransaction();
        }
    }

    public void updateEpisodeList(List<Episode> episodeList) {
        this.__db.beginTransaction();
        try {
            this.__updateAdapterOfEpisode.handleMultiple(episodeList);
            this.__db.setTransactionSuccessful();
        } finally {
            this.__db.endTransaction();
        }
    }

    public LiveData<List<EpisodeItem>> getScheduleList() {
        String _sql = "SELECT Episode.*, Show.name AS showName, Show.posterPath AS showPosterPath, Show.launchId, Show.launchSource FROM Episode, Show WHERE Episode.showId = Show.id AND DATE(DATETIME(Episode.airDate / 1000 , 'unixepoch', 'localtime')) >= DATE(DATETIME('now', 'localtime')) GROUP BY Episode.showId HAVING MIN(Episode.airDate + Episode.episodeNumber) ORDER BY Episode.airDate ASC";
        final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire("SELECT Episode.*, Show.name AS showName, Show.posterPath AS showPosterPath, Show.launchId, Show.launchSource FROM Episode, Show WHERE Episode.showId = Show.id AND DATE(DATETIME(Episode.airDate / 1000 , 'unixepoch', 'localtime')) >= DATE(DATETIME('now', 'localtime')) GROUP BY Episode.showId HAVING MIN(Episode.airDate + Episode.episodeNumber) ORDER BY Episode.airDate ASC", 0);
        return new ComputableLiveData<List<EpisodeItem>>() {
            private Observer _observer;

            protected List<EpisodeItem> compute() {
                List<EpisodeItem> arrayList;
                if (this._observer == null) {
                    arrayList._observer = new Observer("Episode", "Show") {
                        public void onInvalidated(@NonNull Set<String> set) {
                            AnonymousClass4.this.invalidate();
                        }
                    };
                    EpisodeDao_Impl.this.__db.getInvalidationTracker().addWeakObserver(arrayList._observer);
                }
                Cursor _cursor = EpisodeDao_Impl.this.__db.query(_statement);
                try {
                    int _cursorIndexOfShowPosterPath;
                    int _cursorIndexOfLaunchId;
                    int _cursorIndexOfLaunchSource;
                    int _cursorIndexOfId = _cursor.getColumnIndexOrThrow("id");
                    int _cursorIndexOfShowId = _cursor.getColumnIndexOrThrow("showId");
                    int _cursorIndexOfEpisodeNumber = _cursor.getColumnIndexOrThrow("episodeNumber");
                    int _cursorIndexOfSeasonNumber = _cursor.getColumnIndexOrThrow("seasonNumber");
                    int _cursorIndexOfProductionCode = _cursor.getColumnIndexOrThrow("productionCode");
                    int _cursorIndexOfName = _cursor.getColumnIndexOrThrow("name");
                    int _cursorIndexOfAirDate = _cursor.getColumnIndexOrThrow("airDate");
                    int _cursorIndexOfOverview = _cursor.getColumnIndexOrThrow("overview");
                    int _cursorIndexOfStillPath = _cursor.getColumnIndexOrThrow("stillPath");
                    int _cursorIndexOfVoteAverage = _cursor.getColumnIndexOrThrow("voteAverage");
                    int _cursorIndexOfVoteCount = _cursor.getColumnIndexOrThrow("voteCount");
                    int _cursorIndexOfWatched = _cursor.getColumnIndexOrThrow("watched");
                    int _cursorIndexOfShowName = _cursor.getColumnIndexOrThrow("showName");
                    int _cursorIndexOfShowPosterPath2 = _cursor.getColumnIndexOrThrow("showPosterPath");
                    int _cursorIndexOfLaunchId2 = _cursor.getColumnIndexOrThrow("launchId");
                    int _cursorIndexOfLaunchSource2 = _cursor.getColumnIndexOrThrow("launchSource");
                    int _cursorIndexOfShowPosterPath3 = _cursorIndexOfShowPosterPath2;
                    arrayList = new ArrayList(_cursor.getCount());
                    while (_cursor.moveToNext()) {
                        Long _tmp;
                        int _tmpId = _cursor.getInt(_cursorIndexOfId);
                        int _tmpShowId = _cursor.getInt(_cursorIndexOfShowId);
                        int _tmpEpisodeNumber = _cursor.getInt(_cursorIndexOfEpisodeNumber);
                        int _tmpSeasonNumber = _cursor.getInt(_cursorIndexOfSeasonNumber);
                        String _tmpProductionCode = _cursor.getString(_cursorIndexOfProductionCode);
                        String _tmpName = _cursor.getString(_cursorIndexOfName);
                        if (_cursor.isNull(_cursorIndexOfAirDate)) {
                            _tmp = null;
                        } else {
                            _tmp = Long.valueOf(_cursor.getLong(_cursorIndexOfAirDate));
                        }
                        Date _tmpAirDate = DateConverter.toDate(_tmp);
                        String _tmpOverview = _cursor.getString(_cursorIndexOfOverview);
                        String _tmpStillPath = _cursor.getString(_cursorIndexOfStillPath);
                        double _tmpVoteAverage = _cursor.getDouble(_cursorIndexOfVoteAverage);
                        int _tmpVoteCount = _cursor.getInt(_cursorIndexOfVoteCount);
                        boolean _tmpWatched = _cursor.getInt(_cursorIndexOfWatched) != 0;
                        String _tmpShowName = _cursor.getString(_cursorIndexOfShowName);
                        int _cursorIndexOfId2 = _cursorIndexOfId;
                        _cursorIndexOfId = _cursorIndexOfShowPosterPath3;
                        String _tmpShowPosterPath = _cursor.getString(_cursorIndexOfId);
                        _cursorIndexOfShowPosterPath = _cursorIndexOfId;
                        _cursorIndexOfId = _cursorIndexOfLaunchId2;
                        String _tmpLaunchId = _cursor.getString(_cursorIndexOfId);
                        _cursorIndexOfLaunchId = _cursorIndexOfId;
                        _cursorIndexOfId = _cursorIndexOfLaunchSource2;
                        _cursorIndexOfLaunchSource = _cursorIndexOfId;
                        arrayList.add(new EpisodeItem(_tmpShowId, _tmpShowName, _tmpShowPosterPath, _tmpLaunchId, _cursor.getInt(_cursorIndexOfId), _tmpId, _tmpEpisodeNumber, _tmpSeasonNumber, _tmpProductionCode, _tmpName, _tmpAirDate, _tmpOverview, _tmpStillPath, _tmpVoteAverage, _tmpVoteCount, _tmpWatched));
                        _cursorIndexOfId = _cursorIndexOfId2;
                        _cursorIndexOfShowPosterPath3 = _cursorIndexOfShowPosterPath;
                        _cursorIndexOfLaunchId2 = _cursorIndexOfLaunchId;
                        _cursorIndexOfLaunchSource2 = _cursorIndexOfLaunchSource;
                    }
                    _cursorIndexOfLaunchId = _cursorIndexOfLaunchId2;
                    _cursorIndexOfLaunchSource = _cursorIndexOfLaunchSource2;
                    _cursorIndexOfShowPosterPath = _cursorIndexOfShowPosterPath3;
                    return arrayList;
                } finally {
                    _cursor.close();
                }
            }

            protected void finalize() {
                _statement.release();
            }
        }.getLiveData();
    }

    public LiveData<List<EpisodeItem>> getWatchList() {
        String _sql = "SELECT  Episode.*, Show.name AS showName, Show.posterPath AS showPosterPath, Show.launchId, Show.launchSource FROM Episode, Show WHERE Episode.showId = Show.id AND Episode.watched = 0 AND DATETIME(Episode.airDate / 1000 , 'unixepoch', 'localtime') < DATETIME('now', 'localtime') GROUP BY Episode.showId HAVING MIN(Episode.airDate + Episode.episodeNumber) ORDER BY Episode.airDate DESC";
        final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire("SELECT  Episode.*, Show.name AS showName, Show.posterPath AS showPosterPath, Show.launchId, Show.launchSource FROM Episode, Show WHERE Episode.showId = Show.id AND Episode.watched = 0 AND DATETIME(Episode.airDate / 1000 , 'unixepoch', 'localtime') < DATETIME('now', 'localtime') GROUP BY Episode.showId HAVING MIN(Episode.airDate + Episode.episodeNumber) ORDER BY Episode.airDate DESC", 0);
        return new ComputableLiveData<List<EpisodeItem>>() {
            private Observer _observer;

            protected List<EpisodeItem> compute() {
                List<EpisodeItem> arrayList;
                if (this._observer == null) {
                    arrayList._observer = new Observer("Episode", "Show") {
                        public void onInvalidated(@NonNull Set<String> set) {
                            AnonymousClass5.this.invalidate();
                        }
                    };
                    EpisodeDao_Impl.this.__db.getInvalidationTracker().addWeakObserver(arrayList._observer);
                }
                Cursor _cursor = EpisodeDao_Impl.this.__db.query(_statement);
                try {
                    int _cursorIndexOfShowPosterPath;
                    int _cursorIndexOfLaunchId;
                    int _cursorIndexOfLaunchSource;
                    int _cursorIndexOfId = _cursor.getColumnIndexOrThrow("id");
                    int _cursorIndexOfShowId = _cursor.getColumnIndexOrThrow("showId");
                    int _cursorIndexOfEpisodeNumber = _cursor.getColumnIndexOrThrow("episodeNumber");
                    int _cursorIndexOfSeasonNumber = _cursor.getColumnIndexOrThrow("seasonNumber");
                    int _cursorIndexOfProductionCode = _cursor.getColumnIndexOrThrow("productionCode");
                    int _cursorIndexOfName = _cursor.getColumnIndexOrThrow("name");
                    int _cursorIndexOfAirDate = _cursor.getColumnIndexOrThrow("airDate");
                    int _cursorIndexOfOverview = _cursor.getColumnIndexOrThrow("overview");
                    int _cursorIndexOfStillPath = _cursor.getColumnIndexOrThrow("stillPath");
                    int _cursorIndexOfVoteAverage = _cursor.getColumnIndexOrThrow("voteAverage");
                    int _cursorIndexOfVoteCount = _cursor.getColumnIndexOrThrow("voteCount");
                    int _cursorIndexOfWatched = _cursor.getColumnIndexOrThrow("watched");
                    int _cursorIndexOfShowName = _cursor.getColumnIndexOrThrow("showName");
                    int _cursorIndexOfShowPosterPath2 = _cursor.getColumnIndexOrThrow("showPosterPath");
                    int _cursorIndexOfLaunchId2 = _cursor.getColumnIndexOrThrow("launchId");
                    int _cursorIndexOfLaunchSource2 = _cursor.getColumnIndexOrThrow("launchSource");
                    int _cursorIndexOfShowPosterPath3 = _cursorIndexOfShowPosterPath2;
                    arrayList = new ArrayList(_cursor.getCount());
                    while (_cursor.moveToNext()) {
                        Long _tmp;
                        int _tmpId = _cursor.getInt(_cursorIndexOfId);
                        int _tmpShowId = _cursor.getInt(_cursorIndexOfShowId);
                        int _tmpEpisodeNumber = _cursor.getInt(_cursorIndexOfEpisodeNumber);
                        int _tmpSeasonNumber = _cursor.getInt(_cursorIndexOfSeasonNumber);
                        String _tmpProductionCode = _cursor.getString(_cursorIndexOfProductionCode);
                        String _tmpName = _cursor.getString(_cursorIndexOfName);
                        if (_cursor.isNull(_cursorIndexOfAirDate)) {
                            _tmp = null;
                        } else {
                            _tmp = Long.valueOf(_cursor.getLong(_cursorIndexOfAirDate));
                        }
                        Date _tmpAirDate = DateConverter.toDate(_tmp);
                        String _tmpOverview = _cursor.getString(_cursorIndexOfOverview);
                        String _tmpStillPath = _cursor.getString(_cursorIndexOfStillPath);
                        double _tmpVoteAverage = _cursor.getDouble(_cursorIndexOfVoteAverage);
                        int _tmpVoteCount = _cursor.getInt(_cursorIndexOfVoteCount);
                        boolean _tmpWatched = _cursor.getInt(_cursorIndexOfWatched) != 0;
                        String _tmpShowName = _cursor.getString(_cursorIndexOfShowName);
                        int _cursorIndexOfId2 = _cursorIndexOfId;
                        _cursorIndexOfId = _cursorIndexOfShowPosterPath3;
                        String _tmpShowPosterPath = _cursor.getString(_cursorIndexOfId);
                        _cursorIndexOfShowPosterPath = _cursorIndexOfId;
                        _cursorIndexOfId = _cursorIndexOfLaunchId2;
                        String _tmpLaunchId = _cursor.getString(_cursorIndexOfId);
                        _cursorIndexOfLaunchId = _cursorIndexOfId;
                        _cursorIndexOfId = _cursorIndexOfLaunchSource2;
                        _cursorIndexOfLaunchSource = _cursorIndexOfId;
                        arrayList.add(new EpisodeItem(_tmpShowId, _tmpShowName, _tmpShowPosterPath, _tmpLaunchId, _cursor.getInt(_cursorIndexOfId), _tmpId, _tmpEpisodeNumber, _tmpSeasonNumber, _tmpProductionCode, _tmpName, _tmpAirDate, _tmpOverview, _tmpStillPath, _tmpVoteAverage, _tmpVoteCount, _tmpWatched));
                        _cursorIndexOfId = _cursorIndexOfId2;
                        _cursorIndexOfShowPosterPath3 = _cursorIndexOfShowPosterPath;
                        _cursorIndexOfLaunchId2 = _cursorIndexOfLaunchId;
                        _cursorIndexOfLaunchSource2 = _cursorIndexOfLaunchSource;
                    }
                    _cursorIndexOfLaunchId = _cursorIndexOfLaunchId2;
                    _cursorIndexOfLaunchSource = _cursorIndexOfLaunchSource2;
                    _cursorIndexOfShowPosterPath = _cursorIndexOfShowPosterPath3;
                    return arrayList;
                } finally {
                    _cursor.close();
                }
            }

            protected void finalize() {
                _statement.release();
            }
        }.getLiveData();
    }

    public LiveData<List<Episode>> getEpisodeListByShowId(int showId) {
        String _sql = "SELECT * FROM Episode WHERE showId = ? ORDER BY seasonNumber ASC, episodeNumber ASC";
        final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire("SELECT * FROM Episode WHERE showId = ? ORDER BY seasonNumber ASC, episodeNumber ASC", 1);
        _statement.bindLong(1, (long) showId);
        return new ComputableLiveData<List<Episode>>() {
            private Observer _observer;

            protected List<Episode> compute() {
                if (this._observer == null) {
                    r1._observer = new Observer("Episode", new String[0]) {
                        public void onInvalidated(@NonNull Set<String> set) {
                            AnonymousClass6.this.invalidate();
                        }
                    };
                    EpisodeDao_Impl.this.__db.getInvalidationTracker().addWeakObserver(r1._observer);
                }
                Cursor _cursor = EpisodeDao_Impl.this.__db.query(_statement);
                try {
                    int _cursorIndexOfId = _cursor.getColumnIndexOrThrow("id");
                    int _cursorIndexOfShowId = _cursor.getColumnIndexOrThrow("showId");
                    int _cursorIndexOfEpisodeNumber = _cursor.getColumnIndexOrThrow("episodeNumber");
                    int _cursorIndexOfSeasonNumber = _cursor.getColumnIndexOrThrow("seasonNumber");
                    int _cursorIndexOfProductionCode = _cursor.getColumnIndexOrThrow("productionCode");
                    int _cursorIndexOfName = _cursor.getColumnIndexOrThrow("name");
                    int _cursorIndexOfAirDate = _cursor.getColumnIndexOrThrow("airDate");
                    int _cursorIndexOfOverview = _cursor.getColumnIndexOrThrow("overview");
                    int _cursorIndexOfStillPath = _cursor.getColumnIndexOrThrow("stillPath");
                    int _cursorIndexOfVoteAverage = _cursor.getColumnIndexOrThrow("voteAverage");
                    int _cursorIndexOfVoteCount = _cursor.getColumnIndexOrThrow("voteCount");
                    int _cursorIndexOfWatched = _cursor.getColumnIndexOrThrow("watched");
                    List<Episode> _result = new ArrayList(_cursor.getCount());
                    while (_cursor.moveToNext()) {
                        Long _tmp;
                        int _tmpId = _cursor.getInt(_cursorIndexOfId);
                        int _tmpShowId = _cursor.getInt(_cursorIndexOfShowId);
                        int _tmpEpisodeNumber = _cursor.getInt(_cursorIndexOfEpisodeNumber);
                        int _tmpSeasonNumber = _cursor.getInt(_cursorIndexOfSeasonNumber);
                        String _tmpProductionCode = _cursor.getString(_cursorIndexOfProductionCode);
                        String _tmpName = _cursor.getString(_cursorIndexOfName);
                        if (_cursor.isNull(_cursorIndexOfAirDate)) {
                            _tmp = null;
                        } else {
                            _tmp = Long.valueOf(_cursor.getLong(_cursorIndexOfAirDate));
                        }
                        int _cursorIndexOfId2 = _cursorIndexOfId;
                        _result.add(new Episode(_tmpId, _tmpShowId, _tmpEpisodeNumber, _tmpSeasonNumber, _tmpProductionCode, _tmpName, DateConverter.toDate(_tmp), _cursor.getString(_cursorIndexOfOverview), _cursor.getString(_cursorIndexOfStillPath), _cursor.getDouble(_cursorIndexOfVoteAverage), _cursor.getInt(_cursorIndexOfVoteCount), _cursor.getInt(_cursorIndexOfWatched) != 0));
                        _cursorIndexOfId = _cursorIndexOfId2;
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

    public List<Episode> getEpisodeListToUpdate() {
        Throwable th;
        String _sql = "SELECT * FROM Episode ORDER BY showId ASC, seasonNumber ASC, episodeNumber ASC";
        RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire("SELECT * FROM Episode ORDER BY showId ASC, seasonNumber ASC, episodeNumber ASC", 0);
        Cursor _cursor = this.__db.query(_statement);
        try {
            int _cursorIndexOfId = _cursor.getColumnIndexOrThrow("id");
            int _cursorIndexOfShowId = _cursor.getColumnIndexOrThrow("showId");
            int _cursorIndexOfEpisodeNumber = _cursor.getColumnIndexOrThrow("episodeNumber");
            int _cursorIndexOfSeasonNumber = _cursor.getColumnIndexOrThrow("seasonNumber");
            int _cursorIndexOfProductionCode = _cursor.getColumnIndexOrThrow("productionCode");
            int _cursorIndexOfName = _cursor.getColumnIndexOrThrow("name");
            int _cursorIndexOfAirDate = _cursor.getColumnIndexOrThrow("airDate");
            int _cursorIndexOfOverview = _cursor.getColumnIndexOrThrow("overview");
            int _cursorIndexOfStillPath = _cursor.getColumnIndexOrThrow("stillPath");
            int _cursorIndexOfVoteAverage = _cursor.getColumnIndexOrThrow("voteAverage");
            int _cursorIndexOfVoteCount = _cursor.getColumnIndexOrThrow("voteCount");
            int _cursorIndexOfWatched = _cursor.getColumnIndexOrThrow("watched");
            try {
                _sql = new ArrayList(_cursor.getCount());
                while (_cursor.moveToNext()) {
                    Long _tmp;
                    int _tmpId = _cursor.getInt(_cursorIndexOfId);
                    int _tmpShowId = _cursor.getInt(_cursorIndexOfShowId);
                    int _tmpEpisodeNumber = _cursor.getInt(_cursorIndexOfEpisodeNumber);
                    int _tmpSeasonNumber = _cursor.getInt(_cursorIndexOfSeasonNumber);
                    String _tmpProductionCode = _cursor.getString(_cursorIndexOfProductionCode);
                    String _tmpName = _cursor.getString(_cursorIndexOfName);
                    if (_cursor.isNull(_cursorIndexOfAirDate)) {
                        _tmp = null;
                    } else {
                        _tmp = Long.valueOf(_cursor.getLong(_cursorIndexOfAirDate));
                    }
                    int _cursorIndexOfId2 = _cursorIndexOfId;
                    _sql.add(new Episode(_tmpId, _tmpShowId, _tmpEpisodeNumber, _tmpSeasonNumber, _tmpProductionCode, _tmpName, DateConverter.toDate(_tmp), _cursor.getString(_cursorIndexOfOverview), _cursor.getString(_cursorIndexOfStillPath), _cursor.getDouble(_cursorIndexOfVoteAverage), _cursor.getInt(_cursorIndexOfVoteCount), _cursor.getInt(_cursorIndexOfWatched) != 0));
                    _cursorIndexOfId = _cursorIndexOfId2;
                }
                _cursor.close();
                _statement.release();
                return _sql;
            } catch (Throwable th2) {
                th = th2;
                _cursor.close();
                _statement.release();
                throw th;
            }
        } catch (Throwable th3) {
            th = th3;
            String str = _sql;
            _cursor.close();
            _statement.release();
            throw th;
        }
    }
}
