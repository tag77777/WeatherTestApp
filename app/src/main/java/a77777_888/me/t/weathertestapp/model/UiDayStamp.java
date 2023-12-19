package a77777_888.me.t.weathertestapp.model;

import android.annotation.SuppressLint;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import a77777_888.me.t.weathertestapp.data.remote.entities.forecastentities.Timestamp;

public class UiDayStamp {
   public ArrayList<UiTimestamp> uiTimestamps = new ArrayList<>();
   private final String dateString;

   @SuppressLint("SimpleDateFormat")
   public UiDayStamp(Timestamp timestamp) {
      this.dateString = (new SimpleDateFormat("dd MMM", Locale.ENGLISH)).format(new Date(1000L * timestamp.getDt()));
      uiTimestamps.add(new UiTimestamp(timestamp));
   }

   public String getDateString() {
      return dateString;
   }

   public int getIconRes() {
      return getMainUiTimestamp().getDrawableResources().simpleIcon();
   }

   public String getTemperature() {
      return getMainUiTimestamp().getTemperature();
   }

   public String getDescription() {
      return getMainUiTimestamp().getDescription();
   }

   public UiTimestamp getMainUiTimestamp() {
      UiTimestamp result = null;
      for (UiTimestamp uiTimestamp :uiTimestamps) {
         if (uiTimestamp.getTimeString().equals("12:00")) {
            result = uiTimestamp;
            break;
         }
      }
      return (result == null) ? uiTimestamps.get(0) : result;
   }
}
