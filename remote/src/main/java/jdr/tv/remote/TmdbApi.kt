package jdr.tv.remote

import jdr.tv.remote.entities.RemoteDetailedShow
import jdr.tv.remote.entities.RemoteSeason
import jdr.tv.remote.entities.RemoteShowList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface TmdbApi {

    @GET("search/tv")
    suspend fun search(@Query("page") page: Int, @Query("query") query: String): Response<RemoteShowList>

    @GET("discover/tv")
    suspend fun discover(@Query("page") page: Int, @QueryMap query: Map<String, String>): Response<RemoteShowList>

    @GET("tv/{tv_id}")
    suspend fun show(@Path("tv_id") id: Long, @QueryMap query: Map<String, String>): Response<RemoteDetailedShow>

    @GET("tv/{tv_id}")
    suspend fun season(@Path("tv_id") id: Long, @Query("append_to_response") seasonList: String): Response<List<RemoteSeason>>
}
