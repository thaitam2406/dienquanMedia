package com.dienquan.tieulam.serverAPI;

import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.logging.HttpLoggingInterceptor;

import java.io.IOException;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;

/**
 * Created by tamhuynh on 1/31/16.
 */
public class RestClient {

    public static APIServer apiServerInterface;

    public synchronized static APIServer getClient() {
        if (apiServerInterface == null) {

            OkHttpClient okClient = new OkHttpClient();
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            // set your desired log level
            logging.setLevel(HttpLoggingInterceptor.Level.HEADERS);
            logging.setLevel(HttpLoggingInterceptor.Level.BASIC);
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            okClient.interceptors().add(logging);
            okClient.interceptors().add(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    return chain.proceed(chain.request());
                }
            });

            Retrofit client = new Retrofit.Builder()
                    .baseUrl(APIConfig.domainAPI)
                    .client(okClient)
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            apiServerInterface = client.create(APIServer.class);
        }
        return apiServerInterface;
    }

}
