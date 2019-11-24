package com.it.insidetowns.theinsidetowns.network;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.it.insidetowns.theinsidetowns.objects.EventSearch.EventListSearch;
import com.it.insidetowns.theinsidetowns.objects.FavObjects.GetCatListTest;
import com.it.insidetowns.theinsidetowns.objects.FavObjects.ProfileObject;
import com.it.insidetowns.theinsidetowns.objects.LoginRes.LoginCredentials;
import com.it.insidetowns.theinsidetowns.objects.LoginRes.LoginResponse;
import com.it.insidetowns.theinsidetowns.objects.ProductSearch.ProductListSearch;
import com.it.insidetowns.theinsidetowns.objects.ProfileUpdate.ProfUpdateMessage;
import com.it.insidetowns.theinsidetowns.objects.ProfileUpdate.ProfileCredentials;
import com.it.insidetowns.theinsidetowns.objects.ShopSearch.ShopListSearch;
import com.it.insidetowns.theinsidetowns.objects.SubCatSearch.SubCatListSearch;
import com.it.insidetowns.theinsidetowns.objects.chngPwd.ChgMessage;
import com.it.insidetowns.theinsidetowns.objects.chngPwd.Credentials;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

;

/**
 * Ref: 1. https://futurestud.io/blog/android-basic-authentication-with-retrofit
 * Created by Mahesh on 17/8/16.
 */
public class RestApi {
    public static String BASE_URL = "http://theitapp.tech/api/";

  //  public static String BASE_URLTest = "http://theitapp.tech/api/";
    private static SharedPreferences sp;

    private static RestApi instance = null;


    public static synchronized RestApi get() {
        if (instance == null) {
            instance = new RestApi();
        }
        return instance;
    }

    public static void init(Context context) {
        sp = context.getSharedPreferences("GSS", Context.MODE_PRIVATE);
    }

    public RestService getRestService() {
        if (sp == null) {
            throw new IllegalStateException("init method should be called first.");
        }
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        return buildAdapter(BASE_URL, RestService.class, builder);
    }
    public RestService getRestServiceTest() {
      /* if (sp == null) {
            throw new IllegalStateException("init method should be called first.");
        }*/
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        return buildAdapter(BASE_URL, RestService.class, builder);
    }
    private String getApiToken() {
        //TODO: Need to confirm on Basic or Bearer ?
        final String credentials = "Basic " + sp.getString("apiToken", "");
        //Log.e("apiToken",credentials);
        //final String base64EncodedCredentials = Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
        //return base64EncodedCredentials;
        return credentials;
    }

    private <T> T buildAdapter(String baseUrl, Class<T> clazz, OkHttpClient.Builder builder) {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        //
        OkHttpClient defaultHttpClient = builder
                .addInterceptor(interceptor)
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request original = chain.request();
                        // Request customization: add request headers
                        Request.Builder requestBuilder = original.newBuilder()
                                .header("Content-Type", "application/x-www-form-urlencoded");
                                /*.header("App-Id", "PROSPORT")
                                .header("Client-Type", "APP");*/
                        Request request = requestBuilder.build();
                        return chain.proceed(request);
                    }
                })
                .build();
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                .client(defaultHttpClient)
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();



        return retrofit.create(clazz);
    }

    private <T> T buildAdapter1(String baseUrl, Class<T> clazz, OkHttpClient.Builder builder) {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        //
        OkHttpClient defaultHttpClient = builder
                .addInterceptor(interceptor)
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request original = chain.request();
                        // Request customization: add request headers
                        Request.Builder requestBuilder = original.newBuilder()
                                .header("Content-Type", "application/x-www-form-urlencoded");
                                /*.header("App-Id", "PROSPORT")
                                .header("Client-Type", "APP");*/
                        Request request = requestBuilder.build();
                        return chain.proceed(request);
                    }
                })
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .client(defaultHttpClient)
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(clazz);
    }

    public interface RestService {


        @GET("Common/FavCategories")
        Call<GetCatListTest> getCatListTest(@Query("Userid") String Authkey);

        @POST("user/Login")
        Call<LoginResponse> getLogin(@Body LoginCredentials loginCredentials);
      //  Call<LoginResponseRetro> getLogin(@Path("Email") String Email, @Path("Password") String Password);

        @POST("user/ProfileUpdate")
        Call<ProfUpdateMessage> getProfUpdate(@Body ProfileCredentials profileCredentials);


        @POST("user/ChangePassword")
        Call<ChgMessage> getChngPwd(@Body Credentials credentials);


        @GET("user/Profile")
        Call<ProfileObject> getProfile(@Query("UserID") String Authkey);

        @GET("Product/ProductsSearch")
        Call<ProductListSearch> getProductSearch2(@Query("title") String Authkey, @Query("latitude") String lat, @Query("longitude") String longg);


        @GET("ProductType/ProductsTypeSearch")
        Call<ProductListSearch> getProductSearch(@Query("title") String Authkey, @Query("latitude") String lat, @Query("longitude") String longg);


        @GET("Common/SubCategoriesSearch")
        Call<SubCatListSearch> getSubCatSearch(@Query("Search") String Search);

       /* @GET("api/Events/EventsSearch")
        Call<EventListSearch> getEventSearch(@Query("lat") String lat, @Query("longitude") String longg,@Query("title") String Authkey);
*/
        @GET("Events/EventsSearch")
        Call<EventListSearch> getEventSearch(@Query("lat") String lat, @Query("longitude") String longg,@Query("title") String Authkey);


        @GET("Product/ShopsSearch")
        Call<ShopListSearch> getShopSearch(@Query("title") String Authkey, @Query("lat") String lat, @Query("longitude") String longg);



        // http://theitapp.tech/api/user/Profile?UserID=3


    }
}

