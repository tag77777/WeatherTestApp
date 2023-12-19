package a77777_888.me.t.weathertestapp.data.local;

import androidx.room.Entity;

import a77777_888.me.t.weathertestapp.model.IPlace;
import a77777_888.me.t.weathertestapp.model.Place;
import io.reactivex.annotations.NonNull;

@Entity(tableName = "favorites", primaryKeys = {"primaryKey"})
public class FavoriteRoomEntity implements IPlace {

   @NonNull
//   @PrimaryKey
   private final long primaryKey;
   private final  String cityName;
   private final String countryCode;
   private final  String state;

   public FavoriteRoomEntity(long primaryKey, String cityName, String countryCode, String state) {
      this.primaryKey = primaryKey;
      this.cityName = cityName;
      this.countryCode = countryCode;
      this.state = state;
   }

   public FavoriteRoomEntity(Place place) {
      this(place.hashCode(), place.getCityName(), place.getCountryCode(), place.getState());
   }

   public long getPrimaryKey() {
      return primaryKey;
   }

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

   @androidx.annotation.NonNull
   @Override
   public String toString() { return cityName + " " + countryCode; }
}
