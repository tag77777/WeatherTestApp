package a77777_888.me.t.weathertestapp.di;

import javax.inject.Singleton;

import a77777_888.me.t.weathertestapp.data.remote.IWeatherAPI;
import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
@InstallIn(SingletonComponent.class)
public class NetworkModule {

    @Provides
    @Singleton
    public OkHttpClient provideOkHttpClient() {
        return new OkHttpClient.Builder()
            .addInterceptor(createLoggingInterceptor())
            .build();
    }

    @Provides
    @Singleton
    public  IWeatherAPI provideWeatherAPI(OkHttpClient client) {
        return new Retrofit.Builder()
                .baseUrl(IWeatherAPI.BASE_URL)
          .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(IWeatherAPI.class);
    }

    private  Interceptor  createLoggingInterceptor() {
        return new HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY);
    }
}
