package a77777_888.me.t.weathertestapp.model;

import androidx.annotation.NonNull;

import a77777_888.me.t.weathertestapp.data.local.FavoriteRoomEntity;
import a77777_888.me.t.weathertestapp.data.remote.entities.citiesentities.CitiesResponseItem;

public class Place implements IPlace {
   private final String cityName;
   private final String state;
   private final String countryCode;

   public Place(String cityName, String state, String countryCode) {
      this.cityName = cityName;
      this.state = state;
      this.countryCode = countryCode;
   }

   public Place(FavoriteRoomEntity entity) {
      this.cityName = entity.getCityName();
      this.state = entity.getState();
      this.countryCode = entity.getCountryCode();
   }

   public Place(CitiesResponseItem item) {
      this(item.getName(), item.getState(), item.getCountry());
   }

   @SuppressWarnings("unused")
   public String getRequestString() { return cityName + "," + state + "," + countryCode;}

   @Override
   public String getCityName() {
      return cityName;
   }

   @Override
   public String getCountryCode() {
      return countryCode;
   }

   @Override
   public String getState() {
      return state;
   }

   @NonNull
   @Override
   public String toString() {
      return cityName + /*"\n" + state + */"," + countryCode;
   }

   @Override
   public int hashCode() {
      return (cityName + countryCode + state).hashCode();
   }
}
