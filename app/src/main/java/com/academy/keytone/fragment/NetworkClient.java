package com.academy.keytone.fragment;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkClient {
    //admin url
    //http://betaapplication.com/matrix/
    //https://matrixmobilesecurity.com/login.php



    //private static final String BASE_URL = "http://betaapplication.com/matrix/antivirus/api/authentication/";
    //private static final String QR_CODE_BASE_URL = "http://betaapplication.com/matrix/api/api/authentication/";

    //live server
  // private static final String BASE_URL = "https://lab-6.sketchdemos.com/Matrix/antivirus/api/authentication/";
  // private static final String QR_CODE_BASE_URL = "https://lab-6.sketchdemos.com/Matrix/api/api/authentication/";

    //client live server
   private static final String BASE_URL = "https://testsite.co.in/Keystone/Api/";
    private static final String QR_CODE_BASE_URL = "https://testsite.co.in/Keystone/Api/";

    private static Retrofit retrofit = null;

    static Retrofit getRetrofitClient() {
        if (retrofit == null)
        {
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            final OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.addInterceptor(loggingInterceptor);
            builder.connectTimeout(300, TimeUnit.SECONDS);
            builder.readTimeout(80, TimeUnit.SECONDS);
            builder.writeTimeout(90, TimeUnit.SECONDS);
//          OkHttpClient okHttpClient = new OkHttpClient.Builder().build();

            Gson gson = new GsonBuilder().setLenient().create();
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(builder.build())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit;
    }

    public static Retrofit getQRCodeRetrofitClient() {
        if (retrofit == null) {
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            final OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.addInterceptor(loggingInterceptor);
            builder.connectTimeout(300, TimeUnit.SECONDS);
            builder.readTimeout(80, TimeUnit.SECONDS);
            builder.writeTimeout(90, TimeUnit.SECONDS);

            Gson gson = new GsonBuilder().setLenient().create();

            retrofit = new Retrofit.Builder()
                    .baseUrl(QR_CODE_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(builder
                            .build())
                    .build();

        }
        return retrofit;
    }
}