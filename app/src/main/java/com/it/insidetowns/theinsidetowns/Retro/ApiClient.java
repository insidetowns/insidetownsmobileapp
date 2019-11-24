package com.it.insidetowns.theinsidetowns.Retro;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLHandshakeException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by ganesh on 5/23/2018.
 */

public class ApiClient {
    public static final String TYPE_NO_NETWORK = "NO_NETWORK";
    public static final String TYPE_CONNECTION_INTERRUPT = "CONNECTION_INTERRUPT";
    public static final String TYPE_SESSION_TIMEOUT = "SESSION_TIMEOUT";
    public static final String TYPE_SOMETHING_WENT_WRONG = "WENT_WRONG";
  //  private final static String URL_PRODUCTION = "http://pob.bananaappscenter.com/";
  //  private final static String URL_PRODUCTION = "http://edzenie.com/";
     private final static String URL_PRODUCTION = "http://theitapp.tech/";
    private final int TIME_OUT = 120;
    private final String KEY_PROTOCOL_VERSION = "X-ProtocolVersion";
    private final String VERSION = "1.0";
    private final String KEY_CLIENT = "client";
    private final String CLIENT = "android";

    HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
    TokenInterceptor tokenInterceptor = new TokenInterceptor();

    OkHttpClient client = new OkHttpClient.Builder().
            connectTimeout(TIME_OUT, TimeUnit.SECONDS).
            readTimeout(TIME_OUT, TimeUnit.SECONDS).
            addInterceptor(tokenInterceptor).
            addInterceptor(interceptor).
            build();

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(URL_PRODUCTION)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    static ApiClient apiClient;

   public static Retrofit getInstance() {
        if (apiClient == null)
            apiClient = new ApiClient();
        return apiClient.retrofit;
    }


    private ApiClient() {
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
    }


    public static class NoConnectivityException extends IOException {

        @Override
        public String getMessage() {
            return TYPE_NO_NETWORK;
        }

    }

    private class TokenInterceptor implements Interceptor {

        @Override
        public Response intercept(Chain chain) throws NoConnectivityException, SessionTimeOutException, ConnectionInterruptedException {
            Request initialRequest = chain.request();
            Request.Builder requestBuild = initialRequest.newBuilder()
                    .header(KEY_PROTOCOL_VERSION, VERSION)
                    .addHeader(KEY_CLIENT, CLIENT);

            initialRequest = requestBuild.build();

            Response response = null;
            try {
                response = chain.proceed(initialRequest);
            } catch (SocketTimeoutException ste) {
                throw new SessionTimeOutException();
            } catch (SSLHandshakeException she) {
                throw new ConnectionInterruptedException(TYPE_CONNECTION_INTERRUPT);
            } catch (Exception ex) {
                ex.printStackTrace();
                throw new ConnectionInterruptedException(TYPE_SOMETHING_WENT_WRONG);
            }
            return response;
        }
    }

    public class ConnectionInterruptedException extends SSLHandshakeException {

        /**
         * Constructs an exception reporting an error found by
         * an SSL subsystem during handshaking.
         *
         * @param reason describes the problem.
         */
        public ConnectionInterruptedException(String reason) {
            super(reason);
        }

        @Override
        public String getMessage() {
            return TYPE_CONNECTION_INTERRUPT;
        }

    }

    public class SessionTimeOutException extends SocketTimeoutException {
        @Override
        public String getMessage() {
            return TYPE_SESSION_TIMEOUT;
        }
    }


}
