package a77777_888.me.t.weathertestapp.model;

import a77777_888.me.t.weathertestapp.R;
import a77777_888.me.t.weathertestapp.data.remote.entities.forecastentities.Timestamp;

public class UiTimestamp {
    final private String temperature;
    final private String feltTemperature;
    final private DrawableResources drawableResources;
    final private String description;
    final private String wind;
    final private String humidity;
    final private String pressure;
    final private String timeString;

    public UiTimestamp(Timestamp timestamp) {
        this.temperature = parseTemperature(timestamp.getMain().getTemp());
        this.feltTemperature = parseTemperature(timestamp.getMain().getFeltTemperature());
        this.drawableResources = getDrawableResources(timestamp.getWeather().get(0).getId());
        this.description = timestamp.getWeather().get(0).getDescription();
        this.wind = getWindDWithDirection(
            timestamp.getWind().getSpeed(), timestamp.getWind().getDeg()
        );
        this.humidity = Integer.toString(timestamp.getMain().getHumidity() );
        this.pressure = Long.toString( Math.round(timestamp.getMain().getPressure() * 0.75) ) + "mmHg";
        this.timeString = timestamp.getDtTxt().substring(11, 16);
    }

    public String getWindDWithDirection(double speed, int degrees) {
        String speedMpS = speed + "m/s ";

        if ((degrees >= 355 && degrees <= 360) || (degrees >= 0 && degrees <= 5))
            return speedMpS + "N";
        if (degrees > 5 && degrees < 85) return speedMpS + "NI" ;
        if (degrees >= 85 && degrees <= 95) return speedMpS + "I";
        if (degrees > 95 && degrees < 175) return speedMpS + "SI";
        if (degrees >= 175 && degrees <= 185) return speedMpS + "S";
        if (degrees > 185 && degrees < 265) return speedMpS + "SW";
        if (degrees >= 265 && degrees <= 275) return speedMpS + "W";
        if (degrees > 275 && degrees < 355) return speedMpS + "NW";
        return speedMpS;
    }

    private String parseTemperature(double t) {
        long longT = Math.round(t);
        String stringT = Long.toString(longT);
        return (longT > 0) ? "+" + stringT : stringT;
    }

    private DrawableResources getDrawableResources(int weatherCode) {

        if (weatherCode >= 200 && weatherCode < 300)
            return new DrawableResources(
                R.drawable.bg_thunderstorm,
                R.drawable.v_thunderstorm,
                R.drawable.v_thunderstorm_simple
            );

        if (weatherCode >= 300 && weatherCode < 400)
            return new DrawableResources(
                R.drawable.bg_drizzle,
                R.drawable.v_drizzle,
                R.drawable.v_drizzle_simple
            );

        if (weatherCode >= 500 && weatherCode < 600)
            return new DrawableResources(
                R.drawable.bg_rain,
                R.drawable.v_rain,
                R.drawable.v_rain_simple
            );

        if (weatherCode >= 600 && weatherCode < 700)
            return new DrawableResources(
                R.drawable.bg_snow,
                R.drawable.v_snow,
                R.drawable.v_snow_simple
            );

        if (weatherCode >= 700 && weatherCode < 800)
            return new DrawableResources(
                R.drawable.bg_mist,
                R.drawable.v_mist,
                R.drawable.v_mist_simple
            );

        if (weatherCode == 800)
            return new DrawableResources(
                R.drawable.bg_clear,
                R.drawable.v_clear,
                R.drawable.v_clear_simple
            );

        if (weatherCode >= 801 && weatherCode < 850)
            return new DrawableResources(
                R.drawable.bg_clouds,
                R.drawable.v_clouds,
                R.drawable.v_clouds_simple
            );


        return new DrawableResources(
            R.drawable.bg,
            R.drawable.v_empty,
            R.drawable.v_empty
        );
    }

    public String getTemperature() {
        return temperature;
    }

    public String getFeltTemperature() {
        return feltTemperature;
    }

    public DrawableResources getDrawableResources() {
        return drawableResources;
    }

    public String getDescription() {
        return description;
    }

    public String getWind() {
        return wind;
    }

    public String getHumidity() {
        return humidity;
    }

    public String getPressure() {
        return pressure;
    }

    public String getTimeString() {
        return timeString;
    }
}
