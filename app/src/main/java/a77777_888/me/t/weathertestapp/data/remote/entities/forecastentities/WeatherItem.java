package a77777_888.me.t.weathertestapp.data.remote.entities.forecastentities;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")

public class WeatherItem {

  @SerializedName("icon")
  private String icon;

  @SerializedName("description")
  private String description;

  @SerializedName("main")
  private String main;

  @SerializedName("id")
  private int id;

  public String getIcon() {
    return icon;
  }

  public void setIcon(String icon) {
    this.icon = icon;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getMain() {
    return main;
  }

  public void setMain(String main) {
    this.main = main;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }
}