package com.it.insidetowns.theinsidetowns.Helpers;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Utilities {
    private static Utilities sharedInstance;

    //These variables hold the positions of side menu
    public int menuSelectedPosition = 0;
    public int oldmenuSelectedPosition = 0;

    private Utilities() {
    }  //private constructor.

    public static Utilities getInstance() {
        if (sharedInstance == null) { //if there is no instance available... create new one
            sharedInstance = new Utilities();
        }
        return sharedInstance;
    }

    //Validating Email field
    public boolean isValidEmail(String email) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public String readFile(String fileName, Context activity) throws IOException {
        InputStream is = activity.getAssets().open(fileName);
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String content = "";
        String line;
        while ((line = br.readLine()) != null) {
            content = content + line;
        }
        br.close();
        return content;
    }


    /*To display the app version on the screen */
    public static String getAppVersion(Context context) {
        // Initializing Variable with null.
        PackageInfo pInfo;
        // Block for Handling Exception.
        try {
            pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            // Returning Version Name Of Package.
            return pInfo.versionName;

        } catch (PackageManager.NameNotFoundException e) {
            // Printing Exception that was handled.
            e.printStackTrace();
        }

        return "0.1";
    }


    /*This method is used to  check the internet connectivity  whether net is connected or not*/
    public boolean isConnectedToInternet(Activity activity) {

        ConnectivityManager connectivity = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivity != null) {
            NetworkInfo nInfo = connectivity.getActiveNetworkInfo();

            return nInfo != null && nInfo.getState() == NetworkInfo.State.CONNECTED;
        }
        return false;
    }
}
