package a77777_888.me.t.weathertestapp.data.local;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {FavoriteRoomEntity.class}, version = 1, exportSchema = false)
public abstract class FavoritesDatabase extends RoomDatabase {
   public abstract FavoritesDAO getFavoritesDAO();
}
