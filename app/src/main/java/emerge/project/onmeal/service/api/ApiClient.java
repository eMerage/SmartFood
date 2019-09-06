package emerge.project.onmeal.service.api;




import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.concurrent.TimeUnit;

import emerge.project.onmeal.BuildConfig;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.facebook.FacebookSdk.getCacheDir;

/**
 * Created by Himanshu on 8/24/2017.
 */

public class ApiClient {

    public static final String baseUrl = BuildConfig.API_BASE_URL;
    private static Retrofit retrofit = null;

    private static int cacheSize = 10 * 1024 * 1024; // 10 MB
    private static Cache cache = new Cache(getCacheDir(), cacheSize);


    public static Retrofit getClient() {
        if (retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .client(provideOkHttpClient())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }


   private static OkHttpClient provideOkHttpClient() {
        OkHttpClient.Builder okhttpClientBuilder = new OkHttpClient.Builder();
        okhttpClientBuilder.connectTimeout(20, TimeUnit.SECONDS);
        okhttpClientBuilder.readTimeout(20, TimeUnit.SECONDS);
        okhttpClientBuilder.writeTimeout(20, TimeUnit.SECONDS);
       okhttpClientBuilder.cache(cache);
        return okhttpClientBuilder.build();
    }


}
