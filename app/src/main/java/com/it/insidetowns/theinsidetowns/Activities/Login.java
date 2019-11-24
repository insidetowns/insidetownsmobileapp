package com.it.insidetowns.theinsidetowns.Activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
//import com.facebook.CallbackManager;
//import com.facebook.FacebookCallback;
//import com.facebook.FacebookException;
//import com.facebook.FacebookSdk;
//import com.facebook.GraphRequest;
//import com.facebook.GraphResponse;
//import com.facebook.appevents.AppEventsLogger;
//import com.facebook.login.LoginManager;
//import com.facebook.login.LoginResult;
//import com.facebook.login.widget.LoginButton;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.gson.GsonBuilder;
import com.it.insidetowns.theinsidetowns.R;
import com.it.insidetowns.theinsidetowns.Retro.InsideView;
import com.it.insidetowns.theinsidetowns.Retro.Loginpresenter;
import com.it.insidetowns.theinsidetowns.network.RestApi;
import com.it.insidetowns.theinsidetowns.objects.LoginRes.LoginCredentials;
import com.it.insidetowns.theinsidetowns.objects.LoginRes.LoginResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;


/**
 * Created by Banana on 16-Feb-18.
 */

public class Login extends BaseSocialAuthActivity implements InsideView {

    EditText Email, Password;
    LinearLayout SignIn;
    TextView SignUp, googleLogin, fbLogin;
    // LinearLayout loaderBg;
    LinearLayout loaderBg;
    LoginCredentials loginCredentials;
    Loginpresenter loginpresenter;
    String fbName, fbEmail, fbId, gName, gEmail, gProfileUrl;

    LoginButton loginButton;
    CallbackManager callbackManager;
    SharedPreferences sharedPrefs;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
        setContentView(R.layout.login);
        checkForLogin();
        initSocialAuth();

        Email = (EditText) findViewById(R.id.Email);
        Password = (EditText) findViewById(R.id.Password);
        SignIn = (LinearLayout) findViewById(R.id.SignIn);
        SignUp = (TextView) findViewById(R.id.SignUp);
        googleLogin = (TextView) findViewById(R.id.googleLogin);
        fbLogin = (TextView) findViewById(R.id.btn_fb);
        loaderBg = (LinearLayout) findViewById(R.id.loaderBg);
        loginCredentials = new LoginCredentials();
        loginpresenter = new Loginpresenter(this);

        loginButton = findViewById(R.id.login_button);

        FbLogin();

        fbLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginButton.performClick();
            }
        });
        SignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
/*                if (Email.getText().toString().equalsIgnoreCase("11")) {
                    Email.setText("dsr@gmail.com");
                    Password.setText("123456");
                }
                if (Email.getText().toString().equalsIgnoreCase("12")) {
                    Email.setText("dsr2@gmail.com");
                    Password.setText("123456");
                }*/

                if (TextUtils.isEmpty(Email.getText().toString())) {
                    Email.requestFocus();
                    Email.setError("Please enter Email Id.");
                } else if (!Validation.isValidEmail(Email.getText().toString())) {
                    Email.requestFocus();
                    Email.setError("Please enter proper Email Id.");

                } else if (TextUtils.isEmpty(Password.getText().toString())) {
                    Password.requestFocus();
                    Password.setError("Please enter password.");
                } else {
                    //    LoginSubmit();

                    NewLoginSubmit();

                   /* Intent intent = new Intent(Login.this, Home.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_up, R.anim.stay);
                    finish();*/
                }
            }
        });

        googleLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                googleLogin();
            }
        });

        SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Login.this, SignUp.class);
                startActivity(i);
            }
        });


    }

    private void googleLogin() {

        Auth.GoogleSignInApi.signOut(googleApiClient);

/*        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // ...
                    }
                });*/
        Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        startActivityForResult(intent, RC_SIGN_IN);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
          //callbackManager.onActivityResult(requestCode, resultCode, data);
        if (callbackManager != null) {
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("onActivity Result",""+RC_SIGN_IN+"   resultCode : "+resultCode+" Result Ok"+Activity.RESULT_OK);

        if (requestCode == RC_SIGN_IN ) {

            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            Log.e("onActivity Result","if "+result.isSuccess());
            if (result.isSuccess()) {
                Log.e("onActivity Result","if  suceess");
                GoogleSignInAccount account = result.getSignInAccount();
//                txt.setText(account.getDisplayName());
//                txt2.setText(account.getEmail());
//                try {
//                    Glide.with(MainActivity.this).load(account.getPhotoUrl().toString()).into(iv);
//                } catch (Exception e) {
//
//                }
                gName = account.getDisplayName();
                gEmail = account.getEmail();
//gProfileUrl = account.getPhotoUrl().toString();
                loginCredentials.setEmail(account.getEmail());
                loginCredentials.setSocialLoginId(account.getId());
                loginCredentials.setIsSocial(true);
                loginCredentials.setSocialLoginType("google");
                showProgressDialog();
                loginpresenter.getLogin(loginCredentials, 101);

                Log.e("gName ","gName  "+gName);
                Log.e("gEmail ","gEmail  "+gEmail);

            }
        }
    }

    private void NewLoginSubmit() {
        /*SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(Login.this);
        String ID = sharedPrefs.getString("ID", "");*/

        loginCredentials.setEmail(Email.getText().toString().trim());
        loginCredentials.setPassword(Password.getText().toString().trim());

        Log.e("090919 ", " Email " + Email.getText().toString().trim());


        loaderBg.setVisibility(View.VISIBLE);
        final Call<LoginResponse> dataSyncResponseCall = RestApi.get().getRestServiceTest().getLogin(loginCredentials);
        //    final Call<LoginResponseRetro> dataSyncResponseCall = RestApi.get().getRestServiceTest().getLogin(Email.getText().toString().trim(),Password.getText().toString().trim());

        dataSyncResponseCall.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, retrofit2.Response<LoginResponse> response) {
                //   Log.e("010919 "," 3ww "+response.body().getFavCatDetailsList().get(0).getId());
                loaderBg.setVisibility(View.GONE);


                try {

                    //   Log.e("110919 ", " 3ww " + response.body());
                    //  JSONObject jsonObject = new JSONObject(response);
                    //  Log.e("110919 ", " 3ww " + jsonObject.getString("username"));

                    SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(Login.this);
                    SharedPreferences.Editor editor = sharedPrefs.edit();
                    editor.putString("ID", "" + response.body().getID());
                    editor.putString("username", "" + response.body().getUsername());
                    editor.putString("mobile", "" + response.body().getPhone());
                    editor.putString("User_Pic", "http://" + response.body().getUser_Pic());
                    editor.commit();
                    Intent intent = new Intent(Login.this, Home.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_up, R.anim.stay);
                    finish();
                } catch (Exception e) {
                    e.printStackTrace();
                    BaseActivity b = new BaseActivity();
                    b.ShowAnimatedDialogAllMsg(Login.this, "m1", "Invalid credentials please try again.");

                }


                //    editor.putString("mobile", ""+jsonObject.getString("username"));


            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {


                loaderBg.setVisibility(View.GONE);
            }
        });
    }

    private void LoginSubmit() {
      /*  _ProgressDialog = new ProgressDialog(this).show(this,"Login","Please wait...",true,true,new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                 Toast.makeText(DeviceList.this, "Could not find the device!", Toast.LENGTH_SHORT).show();
                  finish();
            }
        });*/
        loaderBg.setVisibility(View.VISIBLE);
        RequestQueue queue = Volley.newRequestQueue(Login.this);
        final StringRequest reqQueue = new StringRequest(Request.Method.POST, "" + getResources().getString(R.string.AppURL) + "api/user/Login", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //    Log.e("010819 "," "+response);
                //  loaderBg.setVisibility(View.GONE);
                loaderBg.setVisibility(View.GONE);
                //Toast.makeText(Login.this, "Loged in successfully...", Toast.LENGTH_SHORT);

                try {
                    JSONObject jsonObject = new JSONObject(response);

                    //      Log.e("010819 "," id "+jsonObject.getString("ID")); // id =3
                    SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(Login.this);
                    SharedPreferences.Editor editor = sharedPrefs.edit();
                    editor.putString("ID", "" + jsonObject.getString("ID"));
                    editor.putString("username", "" + jsonObject.getString("username"));
                    editor.putString("mobile", "" + jsonObject.getString("phone"));
                    editor.putString("User_Pic", "http://" + jsonObject.getString("User_Pic"));
                    //    editor.putString("mobile", ""+jsonObject.getString("username"));
                    editor.commit();
                    Intent intent = new Intent(Login.this, Home.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_up, R.anim.stay);
                    finish();
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                try {
                    //    loaderBg.setVisibility(View.GONE);
                    loaderBg.setVisibility(View.GONE);
                    String message = null;
                    if (volleyError instanceof NetworkError) {
                        {
                            //message = "Cannot connect to Internet...Please check your connection!";
                            BaseActivity b = new BaseActivity();
                            b.ShowAnimatedDialogAll(Login.this, "1");
                        }
                    } else if (volleyError.networkResponse.statusCode == 401) {
                        {
                            //   message = "Invalid credentials. Please try again...";
                            BaseActivity b = new BaseActivity();
                            b.ShowAnimatedDialogAll(Login.this, "2");
                        }
                    } else if (volleyError instanceof ServerError) {

                        if (volleyError.networkResponse.statusCode == 417) {
                            // message = "Invalid credentials. Please try again...";
                            BaseActivity b = new BaseActivity();
                            b.ShowAnimatedDialogAll(Login.this, "2");
                        } else if (volleyError.networkResponse.statusCode == 500) {
                            //   message = "Server could not be found. Please check.";
                            BaseActivity b = new BaseActivity();
                            b.ShowAnimatedDialogAll(Login.this, "3");
                        } else if (volleyError.networkResponse.statusCode == 401) {
                            //      message = "Invalid credentials. Please try again...";
                            BaseActivity b = new BaseActivity();
                            b.ShowAnimatedDialogAll(Login.this, "2");
                        } else {
                            BaseActivity b = new BaseActivity();
                            b.ShowAnimatedDialogAll(Login.this, "3");
                            //    message = "The server could not be found. Please try again after some time!!";
                        }

                    } else if (volleyError instanceof AuthFailureError) {
                        //   message = "Cannot connect to Internet...Please check your connection!";
                        BaseActivity b = new BaseActivity();
                        b.ShowAnimatedDialogAll(Login.this, "1");

                    } else if (volleyError instanceof ParseError) {
                        BaseActivity b = new BaseActivity();
                        b.ShowAnimatedDialogAll(Login.this, "4");
                        //    message = "Parsing error! Please try again after some time!!";
                    } else if (volleyError instanceof NoConnectionError) {
                        BaseActivity b = new BaseActivity();
                        b.ShowAnimatedDialogAll(Login.this, "5");
                        //  message = "Cannot connect to Internet...Please check your connection!";
                    } else if (volleyError instanceof TimeoutError) {
                        //  message = "Connection TimeOut! Please check your internet connection.";
                        BaseActivity b = new BaseActivity();
                        b.ShowAnimatedDialogAll(Login.this, "8");

                    }
                } catch (Exception e) {

                    //     Toast.makeText(Login.this, "Error: " + message, Toast.LENGTH_LONG).show();
                }
            }
        }) {
            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    JSONObject jsonObject = new JSONObject();


                    // jsonObject.put("Firstname", ""+name.getText().toString());
                    jsonObject.put("Email", "" + Email.getText().toString());
                    jsonObject.put("Password", "" + Password.getText().toString());
                    //jsonObject.put("Phone", ""+phone.getText().toString());


                    return jsonObject.toString().getBytes("utf-8");
                } catch (Exception ex) {
                    //  Toast.makeText(Login.this, "Some error occurred. Please try again", Toast.LENGTH_LONG).show();
                    BaseActivity b = new BaseActivity();
                    b.ShowAnimatedDialogAll(Login.this, "6");
                }
                return super.getBody();
            }


            @Override
            public String getBodyContentType() {
                return "application/json";
            }
        };
        queue.add(reqQueue);
    }

    @Override
    public void onResume() {
        super.onResume();

    }


    //super.onBackPressed();
    @Override
    public void onBackPressed() {
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }


    @Override
    public void onPause() {
        super.onPause();

    }


    @Override
    public void setSuccessResponse(retrofit2.Response response, int requestType) {
        dismissDialog();
        if (response != null && response.isSuccessful()) {
            LoginResponse loginResponse = (LoginResponse) response.body();
            if (loginResponse != null) {
                if (gName == null) {

                    sharedPrefs = PreferenceManager.getDefaultSharedPreferences(Login.this);
                    editor = sharedPrefs.edit();
                    editor.putString("ID", "" + loginResponse.getID() + "");
                    editor.putString("username", fbName);
                    if (loginResponse.getPhone() != null) {
                        editor.putString("mobile", "" + loginResponse.getPhone());
                    } else {
                        editor.putString("mobile", "" + "Add Ph.no");

                    }

                    //editor.putString("User_Pic", "http://" + gProfileUrl);
                    //    editor.putString("mobile", ""+jsonObject.getString("username"));
                    editor.commit();
                } else {

                    sharedPrefs = PreferenceManager.getDefaultSharedPreferences(Login.this);
                    editor = sharedPrefs.edit();
                    editor.putString("ID", "" + loginResponse.getID() + "");
                    editor.putString("username", gName);
                    if (loginResponse.getPhone() != null) {
                        editor.putString("mobile", "" + loginResponse.getPhone());
                    } else {
                        editor.putString("mobile", "" + "Add Ph.no");

                    }

                    //editor.putString("User_Pic", "http://" + gProfileUrl);
                    //    editor.putString("mobile", ""+jsonObject.getString("username"));
                    editor.commit();
                }
                startActivity(new Intent(this, Home.class));
                finish();
            }

        }
        try {
            LoginResponse msgError = new GsonBuilder().create().fromJson(response.errorBody().string(), LoginResponse.class);
            Toast.makeText(this, msgError.getfMessage().getMessage(), Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public void setErrorResponse(String message, int requestType) {
        dismissDialog();
       //   Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void FbLogin() {

        callbackManager = CallbackManager.Factory.create();
        loginButton.setReadPermissions(Arrays.asList(
                "public_profile", "email"));
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
    /*            Log.d("pic", "https://graph.facebook.com/" + loginResult.getAccessToken().getUserId() + "/picture?type=small");
                DataHelper.profilePic = "https://graph.facebook.com/" + loginResult.getAccessToken().getUserId() + "/picture?type=large";
                DataHelper.setProfilePic(LoginActivity.this, "https://graph.facebook.com/" + loginResult.getAccessToken().getUserId() + "/picture?type=small");
                DataHelper.setIsUser(LoginActivity.this, true);
                DataHelper.setIsGuestLogin(false);
                Toast.makeText(LoginActivity.this, "Login successfully completed", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                finish();*/
                // App code
                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                                Log.v("LoginActivity", response.toString());

                                // Application code
                                try {
                                    if(object.has("email")){
                                        fbEmail = object.getString("email");
                                    }else{
                                        fbEmail = "";
                                    }
                                    //fbEmail = object.getString("email");
                                    fbId = object.getString("id");
                                    fbName = object.getString("name");
                                    gName = null;// 01/31/1980 format

                                    loginCredentials.setEmail(fbEmail);
                                    loginCredentials.setFbLoginId(object.getString("id"));
                                    loginCredentials.setIsSocial(true);
                                    loginCredentials.setSocialLoginType("facebook");
                                    showProgressDialog();
                                    loginpresenter.getLogin(loginCredentials, 101);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    Toast.makeText(Login.this, "Please Login with valid email", Toast.LENGTH_SHORT).show();
                                    checkForLogin();
                                }

                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email,gender,birthday");
                request.setParameters(parameters);
                request.executeAsync();
            }

            @Override
            public void onCancel() {
                Toast.makeText(getApplicationContext(), "login cancled", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException error) {
                Log.d("error", error.toString());
                Toast.makeText(getApplicationContext(), "error login", Toast.LENGTH_SHORT).show();
            }
        });
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (callbackManager != null) {
//            callbackManager.onActivityResult(requestCode, resultCode, data);
//        }
//        super.onActivityResult(requestCode, resultCode, data);
//    }

   private void checkForLogin() {
        if (LoginManager.getInstance() != null) {
            LoginManager.getInstance().logOut();
        }
    }
}
