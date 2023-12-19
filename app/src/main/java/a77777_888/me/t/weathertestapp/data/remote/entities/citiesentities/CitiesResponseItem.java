// CitiesResponse.java

// YApi QuickType插件生成，具体参考文档:https://plugins.jetbrains.com/plugin/18847-yapi-quicktype/documentation

package a77777_888.me.t.weathertestapp.data.remote.entities.citiesentities;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")

public class CitiesResponseItem {
    @SerializedName("local_names")
    private LocalNames localNames;
    public LocalNames getLocalNames() { return localNames; }
    public void setLocalNames(LocalNames value) { this.localNames = value; }

    private String country;
    public String getCountry() { return country; }
    public void setCountry(String value) { this.country = value; }

    private String name;
    public String getName() { return name; }
    public void setName(String value) { this.name = value; }

    private String state;
    public String getState() { return state; }
    public void setState(String value) { this.state = value; }

    private double lon;
    public double getLon() { return lon; }
    public void setLon(double value) { this.lon = value; }

    private double lat;
    public double getLat() { return lat; }
    public void setLat(double value) { this.lat = value; }
}