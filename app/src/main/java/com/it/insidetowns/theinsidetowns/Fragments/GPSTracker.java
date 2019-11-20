package com.it.insidetowns.theinsidetowns.Fragments;

import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.content.ContextCompat;
import android.util.Log;

/**
 * Created by Banana on 09-Mar-18.
 */

public class GPSTracker extends Service implements LocationListener {

    private final Context context;
    boolean isGPSEnabled =false;
    boolean isNetworkEnabled =false;
    boolean canGetLocation = false;

    Location location;
    protected LocationManager locationManager;

    public GPSTracker(Context context){
        this.context=context;
    }

    //Create a GetLocation Method //
    public Location getLocation(){
       // Log.e("130819 mcA ","TRY  ");
        try{

            locationManager = (LocationManager) context.getSystemService(LOCATION_SERVICE);
        //    Log.e("130819 mcA ","locationManager  "+locationManager);
            isGPSEnabled = locationManager.isProviderEnabled(locationManager.GPS_PROVIDER);
        //    Log.e("130819 mcA ","isGPSEnabled  "+isGPSEnabled);
            isNetworkEnabled=locationManager.isProviderEnabled(locationManager.NETWORK_PROVIDER);
        /*    Log.e("130819 mcA ","isNetworkEnabled  "+isNetworkEnabled);

            Log.e("130819 mcA ","context  "+context);
            Log.e("130819 mcA ","Manifest.permission.ACCESS_FINE_LOCATION)  "+ Manifest.permission.ACCESS_FINE_LOCATION);

            Log.e("130819 mcA ","isNetworkEnabled  "+ ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION));
            Log.e("130819 mcA ","isNetworkEnabled  "+ PackageManager.PERMISSION_GRANTED);
            Log.e("130819 mcA ","isNetworkEnabled  "+ ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION));
            Log.e("130819 mcA ","isNetworkEnabled  "+ PackageManager.PERMISSION_GRANTED );
*/
            if(ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                    || ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED ){
                Log.e("130819 mcA ","IF   ");
                if(isGPSEnabled){
                    if(location==null){
                        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000,10,this);
                        if(locationManager!=null){
                            location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                        }
                    }
                }
                // if lcoation is not found from GPS than it will found from network //
                if(location==null){
                    if(isNetworkEnabled){

                        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 10000,10,this);
                        if(locationManager!=null){
                            location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        }

                    }
                }

            }

        }catch(Exception ex){
            Log.e("130819 mcA ","IF ELSE  ");
        }
        return  location;
    }

    // followings are the default method if we imlement LocationListener //
    public void onLocationChanged(Location location){

    }

    public void onStatusChanged(String Provider, int status, Bundle extras){

    }
    public void onProviderEnabled(String Provider){

    }
    public void onProviderDisabled(String Provider){

    }
    public IBinder onBind(Intent arg0){
        return null;
    }

}