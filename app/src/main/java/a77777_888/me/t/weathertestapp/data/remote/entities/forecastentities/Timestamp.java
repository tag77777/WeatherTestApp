package a77777_888.me.t.weathertestapp.data.remote.entities.forecastentities;

import com.google.gson.annotations.SerializedName;

import java.util.List;

@SuppressWarnings("unused")

public class Timestamp {

  @SerializedName("dt")
  private long dt;

  @SerializedName("dt_txt")
  private String dtTxt;

  @SerializedName("weather")
  private List<WeatherItem> weather;

  @SerializedName("main")
  private Main main;

  @SerializedName("clouds")
  private Clouds clouds;

  @SerializedName("sys")
  private Sys sys;

  @SerializedName("wind")
  private Wind wind;

  @SerializedName("rain")
  private Rain rain;

  public long getDt() {
    return dt;
  }

  public void setDt(long dt) {
    this.dt = dt;
  }

  public String getDtTxt() {
    return dtTxt;
  }

  public void setDtTxt(String dtTxt) {
    this.dtTxt = dtTxt;
  }

  public List<WeatherItem> getWeather() {
    return weather;
  }

  public void setWeather(List<WeatherItem> weather) {
    this.weather = weather;
  }

  public Main getMain() {
    return main;
  }

  public void setMain(Main main) {
    this.main = main;
  }

  public Clouds getClouds() {
    return clouds;
  }

  public void setClouds(Clouds clouds) {
    this.clouds = clouds;
  }

  public Sys getSys() {
    return sys;
  }

  public void setSys(Sys sys) {
    this.sys = sys;
  }

  public Wind getWind() {
    return wind;
  }

  public void setWind(Wind wind) {
    this.wind = wind;
  }

  public Rain getRain() {
    return rain;
  }

  public void setRain(Rain rain) {
    this.rain = rain;
  }
}