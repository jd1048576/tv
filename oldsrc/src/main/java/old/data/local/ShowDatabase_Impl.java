package jdr.tvtracker.data.local;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.db.SupportSQLiteOpenHelper;
import android.arch.persistence.db.SupportSQLiteOpenHelper.Configuration;
import android.arch.persistence.room.DatabaseConfiguration;
import android.arch.persistence.room.InvalidationTracker;
import android.arch.persistence.room.RoomDatabase.Callback;
import android.arch.persistence.room.RoomMasterTable;
import android.arch.persistence.room.RoomOpenHelper;
import android.arch.persistence.room.RoomOpenHelper.Delegate;
import android.arch.persistence.room.util.TableInfo;
import android.arch.persistence.room.util.TableInfo.Column;
import android.arch.persistence.room.util.TableInfo.ForeignKey;
import android.arch.persistence.room.util.TableInfo.Index;
import android.os.Build.VERSION;
import android.support.v4.app.NotificationCompat;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

public class ShowDatabase_Impl extends ShowDatabase {
    private volatile EpisodeDao _episodeDao;
    private volatile ShowDao _showDao;

    protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration configuration) {
        return configuration.sqliteOpenHelperFactory.create(Configuration.builder(configuration.context).name(configuration.name).callback(new RoomOpenHelper(configuration, new Delegate(1) {
            public void createAllTables(SupportSQLiteDatabase _db) {
                _db.execSQL("CREATE TABLE IF NOT EXISTS `Show` (`id` INTEGER NOT NULL, `name` TEXT, `originalName` TEXT, `numberOfSeasons` INTEGER NOT NULL, `numberOfEpisodes` INTEGER NOT NULL, `episodeRuntime` INTEGER NOT NULL, `inProduction` INTEGER NOT NULL, `status` TEXT, `type` TEXT, `firstAirDate` INTEGER, `lastAirDate` INTEGER, `popularity` REAL NOT NULL, `voteAverage` REAL NOT NULL, `voteCount` INTEGER NOT NULL, `overview` TEXT, `posterPath` TEXT, `backdropPath` TEXT, `homepage` TEXT, `originCountry` TEXT, `originalLanguage` TEXT, `createdByList` TEXT, `genresList` TEXT, `networkList` TEXT, `productionCompanyList` TEXT, `cast` TEXT, `contentRatingList` TEXT, `recommendationList` TEXT, `similarList` TEXT, `videoList` TEXT, `launchId` TEXT, `launchSource` INTEGER NOT NULL, `imdbId` TEXT, `tvdbId` INTEGER, `facebookId` TEXT, `instagramId` TEXT, `twitterId` TEXT, PRIMARY KEY(`id`))");
                _db.execSQL("CREATE TABLE IF NOT EXISTS `Episode` (`id` INTEGER NOT NULL, `showId` INTEGER NOT NULL, `episodeNumber` INTEGER NOT NULL, `seasonNumber` INTEGER NOT NULL, `productionCode` TEXT, `name` TEXT, `airDate` INTEGER, `overview` TEXT, `stillPath` TEXT, `voteAverage` REAL NOT NULL, `voteCount` INTEGER NOT NULL, `watched` INTEGER NOT NULL, PRIMARY KEY(`id`), FOREIGN KEY(`showId`) REFERENCES `Show`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE )");
                _db.execSQL("CREATE  INDEX `index_Episode_showId` ON `Episode` (`showId`)");
                _db.execSQL(RoomMasterTable.CREATE_QUERY);
                _db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, \"0c30d14550302a6fa1d0910617876891\")");
            }

            public void dropAllTables(SupportSQLiteDatabase _db) {
                _db.execSQL("DROP TABLE IF EXISTS `Show`");
                _db.execSQL("DROP TABLE IF EXISTS `Episode`");
            }

            protected void onCreate(SupportSQLiteDatabase _db) {
                if (ShowDatabase_Impl.this.mCallbacks != null) {
                    int _size = ShowDatabase_Impl.this.mCallbacks.size();
                    for (int _i = 0; _i < _size; _i++) {
                        ((Callback) ShowDatabase_Impl.this.mCallbacks.get(_i)).onCreate(_db);
                    }
                }
            }

            public void onOpen(SupportSQLiteDatabase _db) {
                ShowDatabase_Impl.this.mDatabase = _db;
                _db.execSQL("PRAGMA foreign_keys = ON");
                ShowDatabase_Impl.this.internalInitInvalidationTracker(_db);
                if (ShowDatabase_Impl.this.mCallbacks != null) {
                    int _size = ShowDatabase_Impl.this.mCallbacks.size();
                    for (int _i = 0; _i < _size; _i++) {
                        ((Callback) ShowDatabase_Impl.this.mCallbacks.get(_i)).onOpen(_db);
                    }
                }
            }

            protected void validateMigration(SupportSQLiteDatabase _db) {
                SupportSQLiteDatabase supportSQLiteDatabase = _db;
                HashMap<String, Column> _columnsShow = new HashMap(36);
                _columnsShow.put("id", new Column("id", "INTEGER", true, 1));
                _columnsShow.put("name", new Column("name", "TEXT", false, 0));
                _columnsShow.put("originalName", new Column("originalName", "TEXT", false, 0));
                _columnsShow.put("numberOfSeasons", new Column("numberOfSeasons", "INTEGER", true, 0));
                _columnsShow.put("numberOfEpisodes", new Column("numberOfEpisodes", "INTEGER", true, 0));
                _columnsShow.put("episodeRuntime", new Column("episodeRuntime", "INTEGER", true, 0));
                _columnsShow.put("inProduction", new Column("inProduction", "INTEGER", true, 0));
                _columnsShow.put(NotificationCompat.CATEGORY_STATUS, new Column(NotificationCompat.CATEGORY_STATUS, "TEXT", false, 0));
                _columnsShow.put("type", new Column("type", "TEXT", false, 0));
                _columnsShow.put("firstAirDate", new Column("firstAirDate", "INTEGER", false, 0));
                _columnsShow.put("lastAirDate", new Column("lastAirDate", "INTEGER", false, 0));
                _columnsShow.put("popularity", new Column("popularity", "REAL", true, 0));
                _columnsShow.put("voteAverage", new Column("voteAverage", "REAL", true, 0));
                _columnsShow.put("voteCount", new Column("voteCount", "INTEGER", true, 0));
                _columnsShow.put("overview", new Column("overview", "TEXT", false, 0));
                _columnsShow.put("posterPath", new Column("posterPath", "TEXT", false, 0));
                _columnsShow.put("backdropPath", new Column("backdropPath", "TEXT", false, 0));
                _columnsShow.put("homepage", new Column("homepage", "TEXT", false, 0));
                _columnsShow.put("originCountry", new Column("originCountry", "TEXT", false, 0));
                _columnsShow.put("originalLanguage", new Column("originalLanguage", "TEXT", false, 0));
                _columnsShow.put("createdByList", new Column("createdByList", "TEXT", false, 0));
                _columnsShow.put("genresList", new Column("genresList", "TEXT", false, 0));
                _columnsShow.put("networkList", new Column("networkList", "TEXT", false, 0));
                _columnsShow.put("productionCompanyList", new Column("productionCompanyList", "TEXT", false, 0));
                _columnsShow.put("cast", new Column("cast", "TEXT", false, 0));
                _columnsShow.put("contentRatingList", new Column("contentRatingList", "TEXT", false, 0));
                _columnsShow.put("recommendationList", new Column("recommendationList", "TEXT", false, 0));
                _columnsShow.put("similarList", new Column("similarList", "TEXT", false, 0));
                _columnsShow.put("videoList", new Column("videoList", "TEXT", false, 0));
                _columnsShow.put("launchId", new Column("launchId", "TEXT", false, 0));
                _columnsShow.put("launchSource", new Column("launchSource", "INTEGER", true, 0));
                _columnsShow.put("imdbId", new Column("imdbId", "TEXT", false, 0));
                _columnsShow.put("tvdbId", new Column("tvdbId", "INTEGER", false, 0));
                _columnsShow.put("facebookId", new Column("facebookId", "TEXT", false, 0));
                _columnsShow.put("instagramId", new Column("instagramId", "TEXT", false, 0));
                _columnsShow.put("twitterId", new Column("twitterId", "TEXT", false, 0));
                TableInfo _infoShow = new TableInfo("Show", _columnsShow, new HashSet(0), new HashSet(0));
                TableInfo _existingShow = TableInfo.read(supportSQLiteDatabase, "Show");
                if (_infoShow.equals(_existingShow)) {
                    HashMap<String, Column> _columnsEpisode = new HashMap(12);
                    _columnsEpisode.put("id", new Column("id", "INTEGER", true, 1));
                    _columnsEpisode.put("showId", new Column("showId", "INTEGER", true, 0));
                    _columnsEpisode.put("episodeNumber", new Column("episodeNumber", "INTEGER", true, 0));
                    _columnsEpisode.put("seasonNumber", new Column("seasonNumber", "INTEGER", true, 0));
                    _columnsEpisode.put("productionCode", new Column("productionCode", "TEXT", false, 0));
                    _columnsEpisode.put("name", new Column("name", "TEXT", false, 0));
                    _columnsEpisode.put("airDate", new Column("airDate", "INTEGER", false, 0));
                    _columnsEpisode.put("overview", new Column("overview", "TEXT", false, 0));
                    _columnsEpisode.put("stillPath", new Column("stillPath", "TEXT", false, 0));
                    _columnsEpisode.put("voteAverage", new Column("voteAverage", "REAL", true, 0));
                    _columnsEpisode.put("voteCount", new Column("voteCount", "INTEGER", true, 0));
                    _columnsEpisode.put("watched", new Column("watched", "INTEGER", true, 0));
                    HashSet<ForeignKey> _foreignKeysEpisode = new HashSet(1);
                    ForeignKey foreignKey = r10;
                    ForeignKey foreignKey2 = new ForeignKey("Show", "CASCADE", "NO ACTION", Arrays.asList(new String[]{"showId"}), Arrays.asList(new String[]{"id"}));
                    _foreignKeysEpisode.add(foreignKey);
                    HashSet<Index> _indicesEpisode = new HashSet(1);
                    _indicesEpisode.add(new Index("index_Episode_showId", false, Arrays.asList(new String[]{"showId"})));
                    TableInfo _infoEpisode = new TableInfo("Episode", _columnsEpisode, _foreignKeysEpisode, _indicesEpisode);
                    TableInfo _existingEpisode = TableInfo.read(supportSQLiteDatabase, "Episode");
                    if (!_infoEpisode.equals(_existingEpisode)) {
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("Migration didn't properly handle Episode(jdr.tvtracker.data.entities.Episode).\n Expected:\n");
                        stringBuilder.append(_infoEpisode);
                        stringBuilder.append("\n Found:\n");
                        stringBuilder.append(_existingEpisode);
                        throw new IllegalStateException(stringBuilder.toString());
                    }
                    return;
                }
                StringBuilder stringBuilder2 = new StringBuilder();
                stringBuilder2.append("Migration didn't properly handle Show(jdr.tvtracker.data.entities.Show).\n Expected:\n");
                stringBuilder2.append(_infoShow);
                stringBuilder2.append("\n Found:\n");
                stringBuilder2.append(_existingShow);
                throw new IllegalStateException(stringBuilder2.toString());
            }
        }, "0c30d14550302a6fa1d0910617876891", "95c6f2baab9bcf8a7df91367beabd1f8")).build());
    }

    protected InvalidationTracker createInvalidationTracker() {
        return new InvalidationTracker(this, new String[]{"Show", "Episode"});
    }

    public void clearAllTables() {
        super.assertNotMainThread();
        SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
        boolean _supportsDeferForeignKeys = VERSION.SDK_INT >= 21;
        if (!_supportsDeferForeignKeys) {
            try {
                _db.execSQL("PRAGMA foreign_keys = FALSE");
            } catch (Throwable th) {
                super.endTransaction();
                if (!_supportsDeferForeignKeys) {
                    _db.execSQL("PRAGMA foreign_keys = TRUE");
                }
                _db.query("PRAGMA wal_checkpoint(FULL)").close();
                if (!_db.inTransaction()) {
                    _db.execSQL("VACUUM");
                }
            }
        }
        super.beginTransaction();
        if (_supportsDeferForeignKeys) {
            _db.execSQL("PRAGMA defer_foreign_keys = TRUE");
        }
        _db.execSQL("DELETE FROM `Show`");
        _db.execSQL("DELETE FROM `Episode`");
        super.setTransactionSuccessful();
        super.endTransaction();
        if (!_supportsDeferForeignKeys) {
            _db.execSQL("PRAGMA foreign_keys = TRUE");
        }
        _db.query("PRAGMA wal_checkpoint(FULL)").close();
        if (!_db.inTransaction()) {
            _db.execSQL("VACUUM");
        }
    }

    public ShowDao ShowDao() {
        ShowDao showDao = this._showDao;
        if (showDao != null) {
            return showDao;
        }
        synchronized (this) {
            if (this._showDao == null) {
                this._showDao = new ShowDao_Impl(this);
            }
            showDao = this._showDao;
        }
        return showDao;
    }

    public EpisodeDao episodeDao() {
        EpisodeDao episodeDao = this._episodeDao;
        if (episodeDao != null) {
            return episodeDao;
        }
        synchronized (this) {
            if (this._episodeDao == null) {
                this._episodeDao = new EpisodeDao_Impl(this);
            }
            episodeDao = this._episodeDao;
        }
        return episodeDao;
    }
}
