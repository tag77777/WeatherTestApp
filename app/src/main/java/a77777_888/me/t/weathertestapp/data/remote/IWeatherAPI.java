package a77777_888.me.t.weathertestapp.data.remote;

import a77777_888.me.t.weathertestapp.data.remote.entities.citiesentities.CitiesResponse;
import a77777_888.me.t.weathertestapp.data.remote.entities.forecastentities.ForecastResponse;
import io.reactivex.Observable;
import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface IWeatherAPI {
   String BASE_URL = "https://api.openweathermap.org/";

   @GET("data/2.5/forecast")
   Single<ForecastResponse> getForecast(
      @Query("q") String q,
      @Query("units") String units,
//      @Query("lang") String lang,
      @Query("appid") String appId
   );

   @GET("geo/1.0/direct")
   Observable<CitiesResponse> getCities(
      @Query("q") String q,
      @Query("limit") String limit,
      @Query("appid") String appId
   );
}

// https://openweathermap.org/forecast5#list
// https://openweathermap.org/api/one-call-3#history
// https://openweathermap.org/api/geocoding-api#direct

// https://api.openweathermap.org/data/2.5/forecast?units=metric&q=ростов-на-дону&appid=96676f38ba8c31b0c977ba021ebb4590&lang=ru
// http://api.openweathermap.org/geo/1.0/direct?q=новоросийск&limit=5&appid=96676f38ba8c31b0c977ba021ebb4590&lang=ru
