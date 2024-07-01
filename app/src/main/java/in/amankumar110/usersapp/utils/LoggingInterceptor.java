package in.amankumar110.usersapp.utils;

import android.util.Log;

import androidx.annotation.NonNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class LoggingInterceptor implements Interceptor {
        @NonNull
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            // Log the URL and headers
            Log.d("API Request", "Request URL: " + request.url());
            return chain.proceed(request);
        }

}
