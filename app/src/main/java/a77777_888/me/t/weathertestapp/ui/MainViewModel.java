package a77777_888.me.t.weathertestapp.ui;


import static a77777_888.me.t.weathertestapp.model.Settings.API_KEY;
import static a77777_888.me.t.weathertestapp.model.Settings.UNITS_METRIC;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.inject.Inject;

import a77777_888.me.t.weathertestapp.data.local.FavoriteRoomEntity;
import a77777_888.me.t.weathertestapp.data.local.FavoritesDAO;
import a77777_888.me.t.weathertestapp.data.remote.IWeatherAPI;
import a77777_888.me.t.weathertestapp.model.Place;
import a77777_888.me.t.weathertestapp.model.UiForecast;
import a77777_888.me.t.weathertestapp.model.loadresults.LoadResult;
import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


@HiltViewModel
public class MainViewModel extends ViewModel {

   public final IWeatherAPI weatherAPI;
   private final FavoritesDAO favoritesDAO;
   private List<FavoriteRoomEntity> currentFavoritesList = new ArrayList<>();
   private Place currentPlace = null;
   private final ExecutorService service = Executors.newSingleThreadExecutor();

   private Disposable disposable = null;

   private final MutableLiveData<LoadResult<?>> _resultLiveData = new  MutableLiveData<>(null);
   public final LiveData<LoadResult<?>> resultLiveData = _resultLiveData;

   private final MutableLiveData<Boolean> _favoriteLiveData = new MutableLiveData<>(false);
   public final LiveData<Boolean> favoriteLiveData = _favoriteLiveData;

   @Inject
   public MainViewModel (
           IWeatherAPI weatherAPI,
           FavoritesDAO favoritesDAO
   ) {
      this.weatherAPI = weatherAPI;
      this.favoritesDAO = favoritesDAO;
   }

   public LiveData<List<FavoriteRoomEntity>> getFavoritesLiveData() {
       return favoritesDAO.getFavouritesListLiveData();
   }

    public List<FavoriteRoomEntity> getCurrentFavoritesList() {
        return currentFavoritesList;
    }

    public void setCurrentFavoritesList(List<FavoriteRoomEntity> currentFavoritesList) {
        this.currentFavoritesList = currentFavoritesList;
        _favoriteLiveData.setValue (
            (currentFavoritesList != null && currentPlace != null) ? favoritesContains(currentPlace) : false
        );
    }

    public Boolean favoritesContains(Place place) {
       if (currentFavoritesList != null && !currentFavoritesList.isEmpty() && place != null) {
           for (FavoriteRoomEntity entity : currentFavoritesList) {
               if (entity.getCityName().equals(place.getCityName()) &&
                    entity.getCountryCode().equals(place.getCountryCode())) { return true; }
           }
       }
       return false;
    }

    @SuppressWarnings("unused")
    public Place getCurrentPlace() {
        return currentPlace;
    }

    public void setCurrentPlace(Place currentPlace) {
        this.currentPlace = currentPlace;
        _favoriteLiveData.setValue (
            (currentFavoritesList != null && currentPlace != null) ? favoritesContains(currentPlace) : false
        );
        getData();
    }

    public void toggleFavorite() {
       if (favoritesContains(currentPlace)) removeFavorite(currentPlace);
       else addFavorite(currentPlace);
    }

    public void addFavorite(Place place) {
       try {
           service.submit(
               () -> favoritesDAO.addFavorite(new FavoriteRoomEntity(place))
           );
       } catch (Exception e) {
           e.printStackTrace();
       }

   }

   public void removeFavorite(Place place) {
       try {
           service.submit(
               () -> favoritesDAO.deleteFavorite(new FavoriteRoomEntity(place))
           );
       } catch (Exception e) { e.printStackTrace(); }
   }

   public void getData() { getData(currentPlace.toString()); }

   public void getData(String place) {
       _resultLiveData.setValue(new LoadResult<>(true));
       disposable = weatherAPI.getForecast(place, UNITS_METRIC, API_KEY)
           .subscribeOn(Schedulers.io())
           .observeOn(AndroidSchedulers.mainThread())
           .subscribe(
               forecastResponse -> {
                   _resultLiveData.setValue(new LoadResult<>(new UiForecast(forecastResponse)));
               },
               e -> {
                   _resultLiveData.setValue(new LoadResult<>(e));
               }
           );
   }

    @Override
   protected void onCleared() {
      if (disposable != null) disposable.dispose();
      super.onCleared();
   }

}
