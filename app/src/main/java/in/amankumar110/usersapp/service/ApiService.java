package in.amankumar110.usersapp.service;

import static in.amankumar110.usersapp.utils.ApiHelper.BASE_URL;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory;
import in.amankumar110.usersapp.models.Response;
import in.amankumar110.usersapp.utils.LoggingInterceptor;
import io.reactivex.rxjava3.core.Single;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class ApiService {

    private static Retrofit dbInstance = null;
    public interface ApiInterface {

        @GET("users")
        Single<Response> getUsers(@Query("pageNo") int page, @Query("pageSize") int results);

    }

    public static ApiInterface getService() {

        if (dbInstance == null) {
            dbInstance = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                    .client(new OkHttpClient.Builder()
                            .addInterceptor(new LoggingInterceptor())
                            .build())
                    .build();
        }

        return dbInstance.create(ApiInterface.class);
    }

}
