package com.it.insidetowns.theinsidetowns.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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
import com.it.insidetowns.theinsidetowns.R;

import org.json.JSONObject;


/**
 * Created by Banana on 16-Feb-18.
 */

public class SignUp extends Activity {

    EditText Email,Password,phone,cnf,name;
    LinearLayout SignUp;
    TextView SignIn;
    LinearLayout loaderBg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

        Email = (EditText) findViewById(R.id.Email);
        phone = (EditText) findViewById(R.id.phone);
        Password = (EditText) findViewById(R.id.Password);
        cnf = (EditText) findViewById(R.id.cnf);
        SignIn = (TextView) findViewById(R.id.SignIn);
        SignUp = (LinearLayout) findViewById(R.id.SignUp);
        name = (EditText) findViewById(R.id.name);
        loaderBg = (LinearLayout) findViewById(R.id.loaderBg);

        SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(name.getText().toString()))
                {
                    name.requestFocus();
                    name.setError("Please enter name.");
                }
                else
                if(TextUtils.isEmpty(Email.getText().toString()))
                {
                    Email.requestFocus();
                    Email.setError("Please enter Email Id.");
                }
                else
                if(!Validation.isValidEmail(Email.getText().toString()))
                {
                    Email.requestFocus();
                    Email.setError("Please enter proper Email Id.");

                }
                else
                if(TextUtils.isEmpty(phone.getText().toString()))
                {
                    phone.requestFocus();
                    phone.setError("Please enter Phone number");
                }
                else
                if(phone.getText().toString().length()!=10)
                {
                    phone.requestFocus();
                    phone.setError("Please enter 10 digit Phone number");
                }
                else
                if(TextUtils.isEmpty(Password.getText().toString()))
                {
                    Password.requestFocus();
                    Password.setError("Please enter password.");
                }
                else
                if(TextUtils.isEmpty(cnf.getText().toString()))
                {
                    cnf.requestFocus();
                    cnf.setError("Please enter confirm password.");
                }
                else
                if(!Password.getText().toString().trim().equalsIgnoreCase(cnf.getText().toString().trim()))
                {
                    Password.requestFocus();
                    Password.setText("");
                    cnf.setText("");
                    Password.setError("Please enter did not match.");
                }
                else
                {
                    LoginSubmit();
                   /* Intent i = new Intent(SignUp.this,Login.class);
                    startActivity(i);*/
                }
            }
        });


        SignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SignUp.this,Login.class);
                startActivity(i);
            }
        });



    }

    private void LoginSubmit() {
        loaderBg.setVisibility(View.VISIBLE);
        RequestQueue queue = Volley.newRequestQueue(SignUp.this);
        final StringRequest reqQueue = new StringRequest(Request.Method.POST, ""+getResources().getString(R.string.AppURL)+"api/user/Register", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("010819 "," "+response);
                loaderBg.setVisibility(View.GONE);
                Toast toast = Toast.makeText(SignUp.this, "Registration done successfully...", Toast.LENGTH_SHORT);

                Intent i = new Intent(SignUp.this,Login.class);
                startActivity(i);
            }


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                loaderBg.setVisibility(View.GONE);
                String message = null;
                if (volleyError instanceof NetworkError) {
                    //    message = "Cannot connect to Internet...Please check your connection!";
                    BaseActivity b = new BaseActivity();
                    b.ShowAnimatedDialogAll(SignUp.this, "1");
                } else if (volleyError instanceof ServerError) {

                    if (volleyError.networkResponse.statusCode == 417) {
                        message = "Invalid credentials. Please try again...";
                        BaseActivity b = new BaseActivity();
                        b.ShowAnimatedDialogAllMsg(SignUp.this, "m1",""+message);


                    }
                    else
                    if (volleyError.networkResponse.statusCode == 500) {
                        //   message = "Server could not be found. Please check.";
                        BaseActivity b = new BaseActivity();
                        b.ShowAnimatedDialogAll(SignUp.this, "3");
                    } else {
                        //   message = "The server could not be found. Please try again after some time!!";
                        BaseActivity b = new BaseActivity();
                        b.ShowAnimatedDialogAll(SignUp.this, "3");
                    }
                } else if (volleyError instanceof AuthFailureError) {
                    //    message = "Cannot connect to Internet...Please check your connection!";
                    BaseActivity b = new BaseActivity();
                    b.ShowAnimatedDialogAll(SignUp.this, "1");
                } else if (volleyError instanceof ParseError) {
                    //   message = "Parsing error! Please try again after some time!!";
                    BaseActivity b = new BaseActivity();
                    b.ShowAnimatedDialogAll(SignUp.this, "4");
                } else if (volleyError instanceof NoConnectionError) {
                    //    message = "Cannot connect to Internet...Please check your connection!";
                    BaseActivity b = new BaseActivity();
                    b.ShowAnimatedDialogAll(SignUp.this, "1");
                } else if (volleyError instanceof TimeoutError) {
                    //    message = "Connection TimeOut! Please check your internet connection.";
                    BaseActivity b = new BaseActivity();
                    b.ShowAnimatedDialogAll(SignUp.this, "8");
                }
                //   Toast.makeText(getContext(), "Error: " + message, Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    JSONObject jsonObject = new JSONObject();


                    jsonObject.put("Firstname", ""+name.getText().toString());
                    jsonObject.put("Email", ""+Email.getText().toString());
                    jsonObject.put("Password", ""+Password.getText().toString());
                    jsonObject.put("Phone", ""+phone.getText().toString());


                    return jsonObject.toString().getBytes("utf-8");
                } catch (Exception ex) {
                //    Toast.makeText(SignUp.this, "Some error occurred. Please try again", Toast.LENGTH_LONG).show();
                    BaseActivity b = new BaseActivity();
                    b.ShowAnimatedDialogAll(SignUp.this, "6");
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
        public void onBackPressed(){
            Intent a = new Intent(Intent.ACTION_MAIN);
            a.addCategory(Intent.CATEGORY_HOME);
            a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(a);
        }


    @Override
    public void onPause() {
        super.onPause();

    }



}
