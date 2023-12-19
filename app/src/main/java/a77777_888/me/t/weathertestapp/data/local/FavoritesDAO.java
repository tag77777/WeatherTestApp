package a77777_888.me.t.weathertestapp.data.local;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public abstract class FavoritesDAO {

    @Query("SELECT * FROM favorites")
    public abstract LiveData<List<FavoriteRoomEntity>> getFavouritesListLiveData();

    @Insert(onConflict = OnConflictStrategy.ABORT)
    public abstract void addFavorite(FavoriteRoomEntity entity);

    @Delete
    public abstract void deleteFavorite(FavoriteRoomEntity entity);

}
