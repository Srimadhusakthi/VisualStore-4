package com.visualstore.Retrfofit;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.visualstore.Model.LoginResultData;
import com.visualstore.Model.StoreId;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Retro {

    private static String mTestURl = "";
    private static String mProductionURL = "http://103.21.59.241/carl_visustore/master_api/my_profile";
//    private static String mProductionURL = "http://45.33.108.121/carl_visustore/index.php/master_api/login_app";
    public static Retro instance = new Retro();
    private static Retrofit.Builder builder;
    public static OkHttpClient.Builder client;
    private Retro()  {

            client = new OkHttpClient.Builder().readTimeout(20,TimeUnit.SECONDS).connectTimeout(20,TimeUnit.SECONDS);
            builder =  new Retrofit.Builder()
                    .baseUrl(mProductionURL)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create());

    }

    public static Retro get(){
        return  instance;
    }




/*    public <T> T create(final Class<T> service) {

        Retrofit retrofit = builder.client(client.build()).build();
        return retrofit.create(service);
    }*/


    public <WT> WT createWithOutToken(final Class<WT> service) {

        Retrofit retrofit = null;

            AuthenticationInterceptor interceptor =
                    new AuthenticationInterceptor();

            if (!client.interceptors().contains(interceptor))
                client.addInterceptor(interceptor);


        builder.client(client.build());
        retrofit = builder.build();

        return retrofit.create(service);
    }



    public <T> T createWithToken(final Class<T> service) {

        String authToken = StoreId.get().getAuth_id();
        Retrofit retrofit = null;

        if (!TextUtils.isEmpty(authToken)) {
            AuthenticationInterceptorToken interceptor =
                    new AuthenticationInterceptorToken(authToken);

            if (!client.interceptors().contains(interceptor))
                client.addInterceptor(interceptor);
        }

        builder.client(client.build());
        retrofit = builder.build();

        return retrofit.create(service);
    }


    public static class AuthenticationInterceptor implements Interceptor {


        public AuthenticationInterceptor() {

        }

        @Override
        public Response intercept(Chain chain) throws IOException {

            Request original = chain.request();

            Request.Builder builder = original.newBuilder()
                    .header("Admin-Service", "visustore-RESTApi " ).header("Auth-Key","BwebRestAPI");

            Request request = builder.build();
            return chain.proceed(request);
        }
    }

    public static class AuthenticationInterceptorToken implements Interceptor {

        private String authToken;

        public AuthenticationInterceptorToken(String token) {
            this.authToken = token;
        }

        @Override
        public Response intercept(Chain chain) throws IOException {

            Request original = chain.request();

            Request.Builder builder = original.newBuilder()
                    .header("Admin-Service", "visustore-RESTApi " )
                    .header("Auth-Key","BwebRestAPI")
                    .header("Auth-Id", authToken);

            Request request = builder.build();
            return chain.proceed(request);
        }
    }





}
