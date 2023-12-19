package a77777_888.me.t.weathertestapp.data.remote.entities.forecastentities;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")

public class Rain {

  @SerializedName("3h")
  private double jsonMember3h;

  public double getJsonMember3h() {
    return jsonMember3h;
  }

  public void setJsonMember3h(double jsonMember3h) {
    this.jsonMember3h = jsonMember3h;
  }
}