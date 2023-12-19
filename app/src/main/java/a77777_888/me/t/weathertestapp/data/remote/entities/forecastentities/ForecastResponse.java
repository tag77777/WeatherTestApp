package a77777_888.me.t.weathertestapp.data.remote.entities.forecastentities;

import com.google.gson.annotations.SerializedName;

import java.util.List;

@SuppressWarnings("unused")

public class ForecastResponse {

  @SerializedName("city")
  private City city;

  @SerializedName("cnt")
  private int cnt;

  @SerializedName("cod")
  private String cod;

  @SerializedName("message")
  private double message;

  @SerializedName("list")
  private List<Timestamp> timestampList;

  public City getCity() {
    return city;
  }

  public void setCity(City city) {
    this.city = city;
  }

  public int getCnt() {
    return cnt;
  }

  public void setCnt(int cnt) {
    this.cnt = cnt;
  }

  public String getCod() {
    return cod;
  }

  public void setCod(String cod) {
    this.cod = cod;
  }

  public double getMessage() {
    return message;
  }

  public void setMessage(double message) {
    this.message = message;
  }

  public List<Timestamp> getTimestampList() {
    return timestampList;
  }

  public void setTimestampList(List<Timestamp> timestampList) {
    this.timestampList = timestampList;
  }
}