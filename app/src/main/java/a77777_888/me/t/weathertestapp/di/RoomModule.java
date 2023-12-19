package a77777_888.me.t.weathertestapp.di;

import android.content.Context;

import androidx.room.Room;

import a77777_888.me.t.weathertestapp.data.local.FavoritesDAO;
import a77777_888.me.t.weathertestapp.data.local.FavoritesDatabase;
import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class RoomModule {

    @Provides
    public static FavoritesDatabase provideDatabase(@ApplicationContext Context context) {
        return Room.databaseBuilder(
            context,
            FavoritesDatabase.class,
            "favorites_db"
        ).fallbackToDestructiveMigration().build();
    }

    @Provides
    public FavoritesDAO povideFavoritesDAO(FavoritesDatabase db) {
        return db.getFavoritesDAO();
    }
}
