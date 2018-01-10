package com.example.rixwansharif.travelanch_company;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.Selection;
import android.text.TextWatcher;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;

public class LoginActivity extends AppCompatActivity {

    private static TextView forgot_pass_text;
    private static Button login_btn;
    private  static EditText phone_text,pass_text;
    private String phone_number,password;



    CheckInternet cd=new CheckInternet(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ad7ab5")));
        getSupportActionBar().setTitle("Back");
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);


        // /Define Widgets


        forgot_pass_text=(TextView) findViewById(R.id.forgot_pass_textview);
        login_btn=(Button) findViewById(R.id.set_new_pass_btn);
        phone_text=(EditText) findViewById(R.id.login_phone_number_text);
        pass_text=(EditText) findViewById(R.id.new_cnfrm_pass_text);

        //Underline Text
        forgot_pass_text.setPaintFlags(forgot_pass_text.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        forgot_pass_text.setText("Forgot Password");


        //prefix
        phone_text.setText("+92 ");
        Selection.setSelection(phone_text.getText(), phone_text.getText().length());
        phone_text.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().startsWith("+92 ")) {
                    phone_text.setText("+92 ");
                    Selection.setSelection(phone_text.getText(), phone_text.getText().length());

                }

            }
        });


        //Click listener for forgot password

        forgot_pass_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlphaAnimation buttonClick = new AlphaAnimation(1.0F, 0.2F);
                buttonClick.setDuration(250);
                v.startAnimation(buttonClick);
                forgot_pass_text_click();
            }
        });





        //Click listener login

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlphaAnimation buttonClick = new AlphaAnimation(1.0F, 0.2F);
                buttonClick.setDuration(250);
                v.startAnimation(buttonClick);

                Login();
            }
        });


    }

    private void forgot_pass_text_click()
    {
        //Intent intent=new Intent(getApplicationContext(), ForgotPasswordActivity.class);
        //startActivity(intent);

    }

    private void Login()
    {
        Intialize();
        if(Validate())
        {
            Login_Process();

        }
        else
        {
            Toast.makeText(getApplicationContext(), "Login Failed", Toast.LENGTH_SHORT).show();
        }
    }

    //validation

    private boolean Validate()
    {


        boolean valid=true;

        if( phone_number.length() < 12)
        {

            phone_text.setError("Phone Number is Required");
            phone_text.requestFocus();
            valid=false;
        }
        if( password.length() == 0 )
        {
            pass_text.setError("Password is required");
            pass_text.requestFocus();
            valid=false;
        }


        return valid;
    }


    //Intializing values

    private void Intialize()
    {
        String Temp="";

        if(phone_text.getText().length()>4) {

            for (int i = 4; i < phone_text.getText().length(); i++) {

                Temp = Temp + phone_text.getText().toString().trim().charAt(i);
            }
            phone_number="92"+Temp;
        }
        else {
            phone_number = "";
        }

        password=pass_text.getText().toString().trim();

    }


    private void Login_Process()
    {



        if (cd.isConnected())
        {

            final ProgressDialog loading = ProgressDialog.show(this, "Signing In", "Please wait...", false, true);

            Timer timer_a = new Timer();
            timer_a.schedule(new TimerTask() {
                @Override
                public void run() {


                    LoginActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (loading.isShowing()) {
                                loading.dismiss();

                                Toast.makeText(getApplicationContext(), "Something has gone wrong, Try again !", Toast.LENGTH_SHORT).show();

                            }


                        }

                    });


                }
            }, 15000);


            Response.Listener<String> responseListener = new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {


                    try {
                        JSONObject jsonResponse = new JSONObject(response);

                        if ((jsonResponse.getString("response").equalsIgnoreCase("success"))) {
                            //Creating a shared preference
                            SharedPreferences sharedPreferences = LoginActivity.this.getSharedPreferences(config.SHARED_PREF_NAME, Context.MODE_PRIVATE);

                            //Creating editor to store values to shared preferences
                            SharedPreferences.Editor editor = sharedPreferences.edit();

                            //Adding values to editor
                            editor.putBoolean(config.LOGGEDIN_SHARED_PREF, true);
                            editor.putString(config.Phone_SHARED_PREF, phone_number);
                            editor.putString(config.Company_SHARED_PREF, jsonResponse.getString("company_name"));
                            editor.putString(config.Contact_Person_SHARED_PREF, jsonResponse.getString("contact_person"));
                            editor.putString(config.Company_RegNo_SHARED_PREF, jsonResponse.getString("company_registration_no"));
                            editor.putString(config.Address_SHARED_PREF, jsonResponse.getString("address"));
                            editor.putString(config.City_SHARED_PREF, jsonResponse.getString("city"));
                            editor.putString(config.Rating_SHARED_PREF, jsonResponse.getString("rating"));
                            editor.putString(config.Image_SHARED_PREF, jsonResponse.getString("image"));



                            //Saving values to editor
                            editor.commit();

                            loading.dismiss();

                            //Starting profile activity
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                        }

                        else

                        {
                            loading.dismiss();
                            Toast.makeText(LoginActivity.this, "Invalid phone or password", Toast.LENGTH_LONG).show();
                        }
                    }
                    catch (JSONException e)
                    {
                        loading.dismiss();
                        Toast.makeText(LoginActivity.this, "Error!", Toast.LENGTH_LONG).show();
                        e.printStackTrace();
                    }

                }


            };

            Response.ErrorListener errorListener = new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError volleyError) {

                    loading.dismiss();
                    Toast.makeText(LoginActivity.this, volleyError.getMessage(), Toast.LENGTH_LONG).show();

                }
            };

            LoginRequest loginRequest = new LoginRequest(phone_number, password,getDeviceToken() ,responseListener, errorListener);
            RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
            queue.add(loginRequest);
        }
        else
        {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

            alertDialogBuilder
                    .setMessage("Check Internet Connection !")
                    .setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {


                        }
                    });

            //
            AlertDialog alertDialog = alertDialogBuilder.create();

            // show it
            alertDialog.show();

        }



    }

    private String getDeviceToken()
    {
        SharedPreferences sharedPreferences = getSharedPreferences(config.SHARED_PREF_NAME, Context.MODE_PRIVATE);

        String token= sharedPreferences.getString(config.Device_Token, "Not Available");

        return token;
    }

    @Override
    public void onBackPressed() {
        Intent intent=new Intent(this,MainPageActivity.class);
        startActivity(intent);
    }

}
