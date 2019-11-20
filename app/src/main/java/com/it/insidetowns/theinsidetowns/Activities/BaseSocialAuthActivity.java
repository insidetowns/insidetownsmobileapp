package com.it.insidetowns.theinsidetowns.Activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

/*import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.login.LoginManager;*/

/**
 * Created by sabarigiri kobagapu on 05-07-2019.
 */
public class BaseSocialAuthActivity extends BaseActivity implements GoogleApiClient.OnConnectionFailedListener {
    GoogleApiClient googleApiClient;
    //CallbackManager callbackManager;
    final int RC_SIGN_IN = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void initSocialAuth() {

      //  callbackManager = CallbackManager.Factory.create();
        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().requestId().requestProfile().build();
        googleApiClient = new GoogleApiClient.Builder(this).enableAutoManage(this, this).addApi(Auth.GOOGLE_SIGN_IN_API, googleSignInOptions).build();
        Log.e("Google ",""+googleApiClient);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(this, "Something went wrong.Please contact your admin.", Toast.LENGTH_SHORT).show();
    }

    public void gmailSignOut() {
        if (googleApiClient != null)
            Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(new ResultCallback<Status>() {
                @Override
                public void onResult(@NonNull Status status) {

                }
            });
    }

  /*  public void fbSignOut() {
        if (AccessToken.getCurrentAccessToken() != null) {
            LoginManager.getInstance().logOut();
        }
    }*/
}
