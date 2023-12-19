package a77777_888.me.t.weathertestapp.model;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Iterator;

import a77777_888.me.t.weathertestapp.data.remote.entities.forecastentities.ForecastResponse;
import a77777_888.me.t.weathertestapp.data.remote.entities.forecastentities.Timestamp;

public class UiForecast {
   private final String cod;
   public ArrayList<UiDayStamp> uiDayStamps = new ArrayList<>();

   public UiForecast(ForecastResponse response) {
      this.cod = response.getCod();

      Iterator<Timestamp> iterator = response.getTimestampList().iterator();

      if (!iterator.hasNext()) {
         throw new IllegalArgumentException("ForecastResponse is empty");
      } else {
         UiDayStamp currentUiDayStamp = new UiDayStamp(iterator.next());

         while (iterator.hasNext()) {
            @NonNull
            Timestamp currentTimestamp = iterator.next();

            if (currentTimestamp.getDtTxt().startsWith("00:00", 11)) {
               this.uiDayStamps.add(currentUiDayStamp);
               currentUiDayStamp = new UiDayStamp(currentTimestamp);
            } else currentUiDayStamp.uiTimestamps.add(new UiTimestamp(currentTimestamp));
         }
      }
   }
}
