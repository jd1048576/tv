package jdr.tvtracker.data.remote;

import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.io.IOException;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map.Entry;
import java.util.TimeZone;

import jdr.tvtracker.data.entities.Episode;
import jdr.tvtracker.data.entities.Season;
import jdr.tvtracker.data.remote.entities.SeasonsResponse;
import okhttp3.Interceptor;
import okhttp3.Interceptor.Chain;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.Retrofit.Builder;
import retrofit2.converter.gson.GsonConverterFactory;

public class TMDbClient {
    private static final String API_KEY = "fb0c0db1084075f2555d0598a7bbd032";
    private static final String BASE_URL = "http://api.themoviedb.org/3/";
    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Builder().baseUrl(BASE_URL).client(getOkHttpClient()).addConverterFactory(GsonConverterFactory.create(getGson())).build();
        }
        return retrofit;
    }

    private static OkHttpClient getOkHttpClient() {
        return new OkHttpClient.Builder().addInterceptor(new Interceptor() {
            public Response intercept(@NonNull Chain chain) throws IOException {
                Request request = chain.request();
                return chain.proceed(request.newBuilder().url(request.url().newBuilder().addQueryParameter("api_key", TMDbClient.API_KEY).build()).build());
            }
        }).build();
    }

    private static Gson getGson() {
        return getDateGsonBuilder().registerTypeAdapter(SeasonsResponse.class, new JsonDeserializer<SeasonsResponse>() {
            public SeasonsResponse deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                JsonObject jsonObject;
                Gson gson = TMDbClient.getDateGsonBuilder().create();
                JsonObject jsonObject2 = json.getAsJsonObject();
                int id = jsonObject2.get("id").getAsInt();
                long showFirstAirDate = ((Date) gson.fromJson(jsonObject2.get("first_air_date"), Date.class)).getTime() + 86400000;
                List<Season> seasons = new ArrayList();
                for (Entry<String, JsonElement> entry : jsonObject2.entrySet()) {
                    Gson gson2;
                    if (((String) entry.getKey()).contains("season/")) {
                        Season season = (Season) gson.fromJson((JsonElement) entry.getValue(), Season.class);
                        List<Episode> episodeList = season.getEpisodeList();
                        for (Episode episode : episodeList) {
                            episode.setShowId(id);
                            if (episode.getAirDate() != null) {
                                gson2 = gson;
                                jsonObject = jsonObject2;
                                episode.setAirDate(new Date(episode.getAirDate().getTime() + 86400000));
                            } else {
                                gson2 = gson;
                                jsonObject = jsonObject2;
                                episode.setAirDate(new Date(showFirstAirDate));
                            }
                            gson = gson2;
                            jsonObject2 = jsonObject;
                        }
                        gson2 = gson;
                        jsonObject = jsonObject2;
                        season.setEpisodeList(episodeList);
                        seasons.add(season);
                    } else {
                        gson2 = gson;
                        jsonObject = jsonObject2;
                    }
                    gson = gson2;
                    jsonObject2 = jsonObject;
                }
                jsonObject = jsonObject2;
                return new SeasonsResponse(seasons);
            }
        }).create();
    }

    private static GsonBuilder getDateGsonBuilder() {
        final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        return new GsonBuilder().excludeFieldsWithoutExposeAnnotation().registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {
            public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                try {
                    return sdf.parse(json.getAsString());
                } catch (ParseException e) {
                    return null;
                }
            }
        });
    }
}
