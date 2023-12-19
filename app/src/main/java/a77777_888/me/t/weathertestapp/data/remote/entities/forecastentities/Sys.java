package a77777_888.me.t.weathertestapp.data.remote.entities.forecastentities;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")

public class Sys {

  @SerializedName("pod")
  private String pod;

  public String getPod() {
    return pod;
  }

  public void setPod(String pod) {
    this.pod = pod;
  }
}