package a77777_888.me.t.weathertestapp.data.remote.entities.forecastentities;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")

public class Clouds {

  @SerializedName("all")
  private int all;

  public int getAll() {
    return all;
  }

  public void setAll(int all) {
    this.all = all;
  }
}