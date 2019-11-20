package com.it.insidetowns.theinsidetowns.Activities;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.it.insidetowns.theinsidetowns.R;


import java.text.DecimalFormat;

/**
 * Created by BananaApps on 5/29/2017.
 */

public class
BaseActivity extends AppCompatActivity {
    ProgressDialog progressDialog;
    String EMAIL_REGEX = "[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}";
    String EMAIL_REGEX_UK = "[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}+\\.[A-Za-z]{2,4}";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    public void showToast(String txt) {
        Toast toast = Toast.makeText(this, txt, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL, 0, 0);
        toast.show();
    }

    public  int okMessage(String title, String msg)
    {
        AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(this);
        dlgAlert.setMessage(msg);
        dlgAlert.setTitle(title);
        dlgAlert.setPositiveButton("OK", null);
        dlgAlert.setCancelable(true);
        dlgAlert.create().show();
        return 1;
    }

    public  boolean IsEmpty(String msg)
    {
       if(msg.length()==0 || msg==null)
           return false;
        else
           return true;
    }
    public  boolean IsLength(String ph)
    {
        if(ph.length()!=11)
            return false;
        else
            return true;
    }

    public  boolean IsValidEmail(String msg)
    {
        if(msg.matches(EMAIL_REGEX) || msg.matches(EMAIL_REGEX_UK))
            return true;
        else
            return false;
    }


public void showYesNo()
{
    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which){
                case DialogInterface.BUTTON_POSITIVE:
                   moveTaskToBack(true);

                    break;

                case DialogInterface.BUTTON_NEGATIVE:
                    break;
            }
        }
    };

    AlertDialog.Builder builder = new AlertDialog.Builder(BaseActivity.this);
    builder.setMessage("Are you sure want to exit?").setPositiveButton("Yes", dialogClickListener)
            .setNegativeButton("No", dialogClickListener).show();
}

    public void showProgress() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
        }
        progressDialog.setMessage("Please wait..");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
    }
    public void dismissDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }


    public void showProgressDialog() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Processing...");
        progressDialog.setCancelable(false);
        progressDialog.show();

    }
    public void dismissProgressDialog() {
        if (progressDialog != null) {
            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
        }
    }

    public void getAuthorizationToken() {
   //   Communicator communicator = new Communicator();
    //  Log.e("100718 "," Token called ");
    /*  Log.e(" 050618 "," "+Constants.USER_NAME);
        Log.e(" 050618 "," "+Constants.PASSWORD);
        Log.e(" 050618 "," "+Constants.GRANTTYPE);*/

   //   communicator.getToken("token", Constants.USER_NAME, Constants.PASSWORD, Constants.GRANTTYPE);
    }



    public double CalculationByDistance(String StartP, String StartEp, String Endp, String EndEp) {
        double lat1 = Double.parseDouble(StartP);
        double lat2 = Double.parseDouble(StartEp);
        double lon1 = Double.parseDouble(Endp);
        double lon2 = Double.parseDouble(EndEp);

       /* double Rad = 6372.8; //meters
        double dLat = Math.toRadians(lon1 - lat1);
        double dLon = Math.toRadians(lon2 - lat2);
        lat1 = Math.toRadians(lat1);
        lat2 = Math.toRadians(lat2);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) + Math.sin(dLon / 2) * Math.sin(dLon / 2) * Math.cos(lat1) * Math.cos(lat2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        double haverdistanceKM = (Rad * c)*1.852;
        return  haverdistanceKM;*/
        Location startLocation = new Location("start_location");
        startLocation.setLatitude(lat1);
        startLocation.setLongitude(lat2);

        Location destLocation = new Location("end_location");
        destLocation.setLatitude(lon1);
        destLocation.setLongitude(lon2);

//        double distance = startLocation.distanceTo(destLocation);
        double distance = startLocation.distanceTo(destLocation) / 1000;

        DecimalFormat format = new DecimalFormat("0.#");

       return  Double.parseDouble(format.format(distance));
    }

    public void ShowAnimatedDialog(Context c, String msg) {
        TextView fname, cancelLL;
        final Dialog dialog = new Dialog(c);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        dialog.setContentView(R.layout.error_msg);
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogTheme; //style id
        fname = (TextView) dialog.findViewById(R.id.fname);
        fname.setText("" + msg);
        dialog.show();
        cancelLL = (TextView) dialog.findViewById(R.id.cancelLL);
        cancelLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

            }
        });
    }

    public void ShowAnimatedDialogAll(Context c, String no) {
        TextView fname, cancelLL,error_head;
        ImageView qrcode;
        final Dialog dialog = new Dialog(c);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        dialog.setContentView(R.layout.error_msg_new);
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogTheme; //style id
        fname = (TextView) dialog.findViewById(R.id.fname);
        error_head = (TextView) dialog.findViewById(R.id.error_head);
        qrcode = (ImageView) dialog.findViewById(R.id.qrcode);
        if(no.equalsIgnoreCase("1")) {
            error_head.setText("Whoops ! No internet");
            String msg = "It seems like you're not connected\n" +
                    " to internet";
            fname.setText("" + msg);
            qrcode.setImageResource(R.mipmap.no_internet);
        }
        else
        if(no.equalsIgnoreCase("2")) {
            error_head.setText("Invalid");
            String msg = "Invalid credentials. Please try again...";
            fname.setText("" + msg);
            qrcode.setImageResource(R.mipmap.no_internet);
        }
        else
        if(no.equalsIgnoreCase("3")) {
            error_head.setText("Server Error");
            String msg = "Server could not be found. Please check.";
            fname.setText("" + msg);
            qrcode.setImageResource(R.mipmap.no_internet);
        }
        else
        if(no.equalsIgnoreCase("4")) {
            error_head.setText("Parsing error!");
            String msg = "Parsing error! Please try again after some time!!";
            fname.setText("" + msg);
            qrcode.setImageResource(R.mipmap.no_internet);
        }
        // Cannot connect to Internet...Please check your connection!
        else
        if(no.equalsIgnoreCase("5")) {
            error_head.setText("Parsing error!");
            String msg = "Cannot connect to Internet...Please check your connection!";
            fname.setText("" + msg);
            qrcode.setImageResource(R.mipmap.no_internet);
        }
        else
        if(no.equalsIgnoreCase("6")) {
            error_head.setText("Error!");
            String msg = "Some error occurred. Please try again.";
            fname.setText("" + msg);
            qrcode.setImageResource(R.mipmap.no_internet);
        }
        else
        if(no.equalsIgnoreCase("7")) {
            error_head.setText("SubCategories Error!");
            String msg = "No SubCategories are registered.";
            fname.setText("" + msg);
            qrcode.setImageResource(R.mipmap.no_internet);
        }
        else
        if(no.equalsIgnoreCase("8")) {
            error_head.setText("Timeout Error!");
            String msg = "Connection TimeOut! Please check your internet connection.";
            fname.setText("" + msg);
            qrcode.setImageResource(R.mipmap.no_internet);
        }
        else
        if(no.equalsIgnoreCase("9")) {
            error_head.setText("Search Text Error!");
            String msg = "Please enter search text.";
            fname.setText("" + msg);
            qrcode.setImageResource(R.mipmap.no_internet);
        }
        dialog.show();
        cancelLL = (TextView) dialog.findViewById(R.id.cancelLL);
        cancelLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

            }
        });
    }

    public void ShowAnimatedDialogAllMsg(Context c, String no,String msgText) {
        TextView fname, cancelLL,error_head;
        ImageView qrcode;
        final Dialog dialog = new Dialog(c);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        dialog.setContentView(R.layout.error_msg_new);
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogTheme; //style id
        fname = (TextView) dialog.findViewById(R.id.fname);
        error_head = (TextView) dialog.findViewById(R.id.error_head);
        qrcode = (ImageView) dialog.findViewById(R.id.qrcode);
        if(no.equalsIgnoreCase("m1")) {
            error_head.setText("OMG!");
            String msg = ""+msgText;
            fname.setText("" + msg);
            qrcode.setImageResource(R.mipmap.no_events);
        }
        else
        if(no.equalsIgnoreCase("m2")) {
            error_head.setText("Success!");
            String msg = ""+msgText;
            fname.setText("" + msg);
            qrcode.setImageResource(R.mipmap.no_events);
        }
        else
        if(no.equalsIgnoreCase("2")) {
            error_head.setText("Invalid");
            String msg = "Invalid credentials. Please try again...";
            fname.setText("" + msg);
            qrcode.setImageResource(R.mipmap.no_internet);
        }
        else
        if(no.equalsIgnoreCase("3")) {
            error_head.setText("Server Error");
            String msg = "Server could not be found. Please check.";
            fname.setText("" + msg);
            qrcode.setImageResource(R.mipmap.no_internet);
        }
        else
        if(no.equalsIgnoreCase("4")) {
            error_head.setText("Parsing error!");
            String msg = "Parsing error! Please try again after some time!!";
            fname.setText("" + msg);
            qrcode.setImageResource(R.mipmap.no_internet);
        }
        // Cannot connect to Internet...Please check your connection!
        else
        if(no.equalsIgnoreCase("5")) {
            error_head.setText("Parsing error!");
            String msg = "Cannot connect to Internet...Please check your connection!";
            fname.setText("" + msg);
            qrcode.setImageResource(R.mipmap.no_internet);
        }
        else
        if(no.equalsIgnoreCase("6")) {
            error_head.setText("Error!");
            String msg = "Some error occurred. Please try again.";
            fname.setText("" + msg);
            qrcode.setImageResource(R.mipmap.no_internet);
        }
        else
        if(no.equalsIgnoreCase("7")) {
            error_head.setText("SubCategories Error!");
            String msg = "No SubCategories are registered.";
            fname.setText("" + msg);
            qrcode.setImageResource(R.mipmap.no_internet);
        }
        dialog.dismiss();

        cancelLL = (TextView) dialog.findViewById(R.id.cancelLL);
        cancelLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

            }
        });
    }

/*    public boolean ShowAnimatedDialogReturn(Context c, String msg) {
        TextView fname, cancelLL;
        final int[] s = {0};
        final Dialog dialog = new Dialog(c);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        dialog.setContentView(R.layout.animated_error_msg);
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogTheme; //style id
        fname = (TextView) dialog.findViewById(R.id.fname);
        fname.setText("" + msg);
        dialog.show();
        cancelLL = (TextView) dialog.findViewById(R.id.cancelLL);
        cancelLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                s[0] =1;
            }
        });

        if(s[0]==1)

        return true;
        else
            return false;
    }

    public void ShowAnimatedDialogOk(Context c, String msg) {
        TextView fname, cancelLL;
        final Dialog dialog = new Dialog(c);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        dialog.setContentView(R.layout.animated_error_msg_ok);
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogTheme; //style id
        fname = (TextView) dialog.findViewById(R.id.fname);
        fname.setText("" + msg);
        dialog.show();
        cancelLL = (TextView) dialog.findViewById(R.id.cancelLL);
        cancelLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

            }
        });
    }*/

}
