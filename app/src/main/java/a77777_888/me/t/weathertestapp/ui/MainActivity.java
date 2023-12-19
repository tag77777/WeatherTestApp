package a77777_888.me.t.weathertestapp.ui;

import static a77777_888.me.t.weathertestapp.model.Settings.API_KEY;

import android.Manifest;
import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.OptIn;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.badge.BadgeUtils;
import com.google.android.material.badge.ExperimentalBadgeUtils;
import com.google.gson.JsonSyntaxException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import a77777_888.me.t.weathertestapp.R;
import a77777_888.me.t.weathertestapp.data.local.FavoriteRoomEntity;
import a77777_888.me.t.weathertestapp.data.remote.entities.citiesentities.CitiesResponse;
import a77777_888.me.t.weathertestapp.data.remote.entities.citiesentities.CitiesResponseItem;
import a77777_888.me.t.weathertestapp.databinding.ActivityMainBinding;
import a77777_888.me.t.weathertestapp.model.BackgroundReplacementAnimator;
import a77777_888.me.t.weathertestapp.model.Place;
import a77777_888.me.t.weathertestapp.model.UiDayStamp;
import a77777_888.me.t.weathertestapp.model.UiForecast;
import a77777_888.me.t.weathertestapp.model.UiTimestamp;
import a77777_888.me.t.weathertestapp.ui.adapters.DayStampAdapter;
import a77777_888.me.t.weathertestapp.ui.adapters.FavoritesAdapter;
import a77777_888.me.t.weathertestapp.ui.adapters.TimestampAdapter;
import dagger.hilt.android.AndroidEntryPoint;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;
import retrofit2.HttpException;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity
    implements DayStampAdapter.Publisher, FavoritesAdapter.FavoritesHandler {

    private static final int PERMISSION_REQUEST_CODE = 1;
    private static final String TAG = "TAG";

    private ActivityMainBinding binding;
    private MainViewModel viewModel;
    private final PublishSubject<String> placesSubject = PublishSubject.create();
    private Disposable disposable = null;
    private BackgroundReplacementAnimator backgroundReplacementAnimator;
    private final long duration = 1000l;
    private Animation pulseAnimation;
    private final Handler handler = new Handler(Looper.myLooper());
    private BadgeDrawable favoriteBade;
    private FavoritesAdapter favoritesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        backgroundReplacementAnimator = new BackgroundReplacementAnimator(binding.bg1, binding.bg2);

        setupUi();
        setupFavoriteObserver();
        setupFavoritesObserver();
        setupLoadResultObserver();
        setupPlacesPopupWindow();

//        binding.btn.setOnClickListener(
//            it -> {
//                setupSearchPlaceByLocation();
//            }
//        );
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (disposable != null) {
            disposable.dispose();
            disposable = null;
        }
    }


    private void foo() {
        ValueAnimator animator = ValueAnimator.ofFloat(0, 1);
        animator.setFloatValues(0.4f);

        binding.windTextView.animate().alpha(0f).setDuration(1000).start();

    }

    private void setupLoadResultObserver() {
        viewModel.resultLiveData.observe(this,
            loadResult -> {
                if (loadResult == null) return;

                if (loadResult.isSuccess()) {
                    binding.errorLayout.animate().setDuration(duration).alpha(0f).start();
                    binding.progressBar.animate().setDuration(duration).alpha(0f).start();
//                    binding.errorLayout.setVisibility(View.INVISIBLE);
//                    binding.progressBar.setVisibility(View.INVISIBLE);

                    UiForecast uiForecast = (UiForecast)loadResult.value();
                    publish(uiForecast.uiDayStamps.get(0));
                    binding.forecastRecyclerView.setAdapter(new DayStampAdapter(uiForecast.uiDayStamps, this));

                    binding.contentLayout.animate().setDuration(duration).alpha(1f).start();
//                    binding.contentLayout.setVisibility(View.VISIBLE);
                }
                else if (loadResult.isError()) {
                    binding.contentLayout.animate().alpha(0f).start();
                    binding.progressBar.animate().alpha(0f).start();
                    binding.errorLayout.animate().alpha(1f).start();
//                    binding.contentLayout.setVisibility(View.INVISIBLE);
//                    binding.progressBar.setVisibility(View.INVISIBLE);
//                    binding.errorLayout.setVisibility(View.VISIBLE);

                    if (loadResult.value() instanceof JsonSyntaxException)
                        binding.errorMessage.setText(R.string.parsing_error);
                    if (loadResult.value() instanceof HttpException)
                        binding.errorMessage.setText(getString(R.string.server_error_cod,((HttpException)loadResult.value()).code()));
                    if (loadResult.value() instanceof IOException)
                        binding.errorMessage.setText(R.string.connection_error);
                    else binding.errorMessage.setText(R.string.unknown_error);
                }
                else {// is loading
                    binding.contentLayout.animate().alpha(0f).start();
                    binding.progressBar.animate().alpha(1f).start();
                    binding.errorLayout.animate().alpha(0f).start();
//                    binding.contentLayout.setVisibility(View.INVISIBLE);
//                    binding.progressBar.setVisibility(View.VISIBLE);
//                    binding.errorLayout.setVisibility(View.INVISIBLE);
                }
                Log.e(TAG, "contentLayout.visibility = " + binding.contentLayout.getVisibility());
                Log.e(TAG, "contentLayout.alpha = " + binding.contentLayout.getAlpha());
            }
        );
    }

    private void setupFavoriteObserver() {
        viewModel.favoriteLiveData.observe(
            this,
            isFavorite -> {
                binding.favoriteImageButton.setImageResource(
                    (isFavorite) ? R.drawable.v_favorite : R.drawable.v_favorite_border
                );
            }
        );
    }

    private void setupFavoritesObserver() {
        handler.postDelayed(
            () -> {
                viewModel.getFavoritesLiveData().observe(
                    this,
                    favoritesList -> {
                        Log.e(TAG, "setupFavoritesObserver: SIZE =" + favoritesList.size());
                        viewModel.setCurrentFavoritesList(favoritesList);
                        favoritesAdapter.setList(favoritesList);

                        if (favoritesList.size() == 0) {
                            favoriteBade.setVisible(false, true);
                        } else {
                            favoriteBade.setVisible(true, true);
                            favoriteBade.setNumber(favoritesList.size());
                            binding.favoritesButton.startAnimation(pulseAnimation);
                        }
                    }
                );
            }, 1000L
        );
    }

    @OptIn(markerClass = ExperimentalBadgeUtils.class)
    private void  setupUi() {
        binding.contentLayout.setAlpha(0f);
        binding.errorLayout.setAlpha(0f);
        binding.progressBar.setAlpha(0f);
        binding.favoritesLayout.getRoot().setVisibility(View.INVISIBLE);
        binding.favoritesLayout.getRoot().setAlpha(0f);
//        binding.contentLayout.setVisibility(View.INVISIBLE);
//        binding.errorLayout.setVisibility(View.INVISIBLE);
//        binding.progressBar.setVisibility(View.INVISIBLE);

        binding.dayStampRecyclerView.setAdapter(new TimestampAdapter(new ArrayList<>()));
        binding.forecastRecyclerView.setAdapter(new DayStampAdapter(new ArrayList<>(), this));

        pulseAnimation = AnimationUtils.loadAnimation(this, R.anim.pulse);

        handler.postDelayed(
            () -> {
                favoriteBade = BadgeDrawable.create(this);
                BadgeUtils.attachBadgeDrawable(favoriteBade, binding.favoritesButton);
            },500L
        );

        binding.favoriteImageButton.setOnClickListener(
            v -> { viewModel.toggleFavorite(); }
        );

        binding.tryAgainButton.setOnClickListener(
            v -> { viewModel.getData(); }
        );

        favoritesAdapter = new FavoritesAdapter(viewModel.getCurrentFavoritesList(), this);
        binding.favoritesLayout.favoritesRecyclerView.setAdapter(favoritesAdapter);
        binding.favoritesLayout.favoritesQuiteButton.setOnClickListener(
            v -> { hideFavoritesLayout(); }
        );
        binding.favoritesButton.setOnClickListener(
            v -> { showFavoritesLayout(); }
        );

        binding.exitButton.setOnClickListener(v -> { finish(); });
        binding.quiteButton.setOnClickListener(v -> { finish(); });
    }


    private void setupSearchPlaceByLocation() {
        if (
            (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) !=
            PackageManager.PERMISSION_GRANTED) &&
            (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {
            ActivityCompat.requestPermissions(
                MainActivity.this, new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISSION_REQUEST_CODE);
        }

        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        boolean isNetworkEnabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        boolean isGPSEnabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);

        Log.e(TAG, "isNetworkEnabled = " + isNetworkEnabled);
        Log.e(TAG, "isGPSEnabled = " + isGPSEnabled);

//        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, this);

        if (location == null) Log.e(TAG, "location == null");
        else {
            double latitude = location.getLatitude();
            double longitude = location.getLongitude();
            Log.e(TAG, "latitude = " + latitude);
            Log.e(TAG, "longitude = " + longitude);
        }


//        binding.windTextView.setText(Double.toString(location.getLatitude()));
//        binding.pressureTextView.setText(Double.toString(location.getLongitude()));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permissions granted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Provide Permissions", Toast.LENGTH_SHORT).show();
//                finish();
            }
        }
    }

    private void setupPlacesPopupWindow() {
        binding.placeNameEditText.setThreshold(3);

        ArrayAdapter<Place> adapter = new ArrayAdapter<>(this, R.layout.place_list_popup_window_item,  new ArrayList<>())
        {
            @NonNull
            @Override
            public Filter getFilter() {
                return new Filter() {
                    @Override
                    protected FilterResults performFiltering(CharSequence constraint) {
                        ArrayList<Place> list = new ArrayList<>();
                        for (int i = 0; i < getCount(); i++){
                            list.add(getItem(i));
                        }

                        FilterResults results = new FilterResults();
                        results.values = list;
                        results.count = getCount();
                        return results;
                    }

                    @Override
                    protected void publishResults(CharSequence constraint, FilterResults results) {
                    }

                };
            }
        }
        ;

        binding.placeNameEditText.setAdapter(adapter);
        binding.placeNameEditText.enoughToFilter();
        binding.placeNameEditText.setThreshold(3);

        binding.placeNameEditText.addTextChangedListener(
            new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }

                @Override
                public void afterTextChanged(Editable s) {
                    Log.e("TAG", "text: " + s.toString());
                    placesSubject.onNext(s.toString());
                }
            }
        );

        binding.placeNameEditText.setOnFocusChangeListener(
            (v, hasFocus) -> { if (!hasFocus) hideSoftKeyboard(); }
        );

        binding.placeNameEditText.setOnItemClickListener(
            new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Place place = adapter.getItem(position);
                    Log.e(TAG, "onItemClick: " + place);

                    viewModel.setCurrentPlace(place);

                    hideSoftKeyboard();
                }
            }
        );

//        binding.placeNameEditText.setOnEditorActionListener(
//            (view, actionId, event) -> {
//                Log.e(TAG,"ON_EDITOR_ACTION = " + actionId);
//                if (actionId == EditorInfo.IME_ACTION_SEARCH){
//                    String searchString = view.getText().toString();
//                    viewModel.getData(searchString);
//                    hideSoftKeyboard();
//                    return true;
//                }
//                return false;
//            }
//        );

        disposable = placesSubject
            .distinctUntilChanged()
            .debounce(500, TimeUnit.MILLISECONDS)
//            .filter(str -> str.length() > 2)
            .switchMap(str ->
                 (str.length() < 3) ? Observable.just(new CitiesResponse())  :
                viewModel.weatherAPI.getCities(str, "5", API_KEY)

            )
            .map(
                citiesResponseItems -> {

                    List<Place> places = new ArrayList<>();
                    for (CitiesResponseItem item : citiesResponseItems) {
                        places.add(new Place(item));
                    }
                    Log.e(TAG, "places = " + places);
                    return places;

                }
            )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    places -> {
                        Log.e("TAG", places.toString());
                        adapter.clear();
                        adapter.addAll(places);
                        adapter.notifyDataSetChanged();
                    },
                    e -> {
                        Log.e("TAG", e.toString());
                        Toast.makeText(this,e.toString(),Toast.LENGTH_LONG).show();
                    }
                );

    }

    private void showFavoritesLayout() {
        binding.favoritesLayout.getRoot().setVisibility(View.VISIBLE);
        binding.favoritesLayout.getRoot().animate().alpha(1f).setDuration(duration)
            .setListener(
                new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(@NonNull Animator animation) {
                    }

                    @Override
                    public void onAnimationEnd(@NonNull Animator animation) {
                        binding.favoritesLayout.getRoot().setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onAnimationCancel(@NonNull Animator animation) {
                        binding.favoritesLayout.getRoot().setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onAnimationRepeat(@NonNull Animator animation) {
                    }
                }
            )
            .start();
    }

    private void hideFavoritesLayout() {
        binding.favoritesLayout.getRoot().animate().alpha(0f).setDuration(duration)
            .setListener(
                new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(@NonNull Animator animation) {
                    }

                    @Override
                    public void onAnimationEnd(@NonNull Animator animation) {
                        binding.favoritesLayout.getRoot().setVisibility(View.INVISIBLE);
                    }

                    @Override
                    public void onAnimationCancel(@NonNull Animator animation) {
                        binding.favoritesLayout.getRoot().setVisibility(View.INVISIBLE);
                    }

                    @Override
                    public void onAnimationRepeat(@NonNull Animator animation) {
                    }
                }
            ).start();
    }

    @Override
    public void publish(UiDayStamp uiDayStamp) {
        UiTimestamp uiTimestamp = uiDayStamp.getMainUiTimestamp();

        binding.temperatureTextView.setText(getString(R.string.degrees_celsius, uiTimestamp.getTemperature()));
        binding.iconImageView.setImageResource(
            uiTimestamp.getDrawableResources().icon()
        );
        binding.weatherDescriptionTextView.setText(uiTimestamp.getDescription());
        binding.effectTemperatureTextView.setText(getString(R.string.degrees_celsius, uiTimestamp.getFeltTemperature()));
        binding.windTextView.setText(uiTimestamp.getWind());
        binding.humidityTextView.setText(getString(R.string.humidity, uiTimestamp.getHumidity()));
        binding.pressureTextView.setText(uiTimestamp.getPressure());
        binding.dayStampRecyclerView.setAdapter(new TimestampAdapter(uiDayStamp.uiTimestamps));

        backgroundReplacementAnimator.animate(uiTimestamp.getDrawableResources().background());
        binding.placeNameEditText.clearFocus();
        binding.contentLayout.requestFocus();
    }

    @Override
    public void publishFavorite(Place place) {
        viewModel.setCurrentPlace(place);
        binding.placeNameEditText.setText(place.toString());
        hideFavoritesLayout();
        binding.placeNameEditText.clearFocus();
        binding.contentLayout.requestFocus();
    }

    @Override
    public void removeFavorite(FavoriteRoomEntity entity) {
        viewModel.removeFavorite(new Place(entity));
    }

    private void hideSoftKeyboard() {
        WindowInsetsControllerCompat controller =  WindowCompat.getInsetsController(getWindow(), binding.getRoot());
        controller.hide(WindowInsetsCompat.Type.ime());
    }
}