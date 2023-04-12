package com.rovaindu.serviesdashboard.controller.profile;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.rovaindu.serviesdashboard.HomeActivity;
import com.rovaindu.serviesdashboard.R;
import com.rovaindu.serviesdashboard.controller.map.base.AddressData;
import com.rovaindu.serviesdashboard.manager.ServiesAgentSharedPrefManager;
import com.rovaindu.serviesdashboard.retrofit.ApiInterface;
import com.rovaindu.serviesdashboard.retrofit.RetrofitClient;
import com.rovaindu.serviesdashboard.retrofit.models.City;
import com.rovaindu.serviesdashboard.retrofit.models.Country;
import com.rovaindu.serviesdashboard.retrofit.models.ServiesAgent;
import com.rovaindu.serviesdashboard.retrofit.response.CityResponse;
import com.rovaindu.serviesdashboard.retrofit.response.CountryResponse;
import com.rovaindu.serviesdashboard.retrofit.response.UpdateProfileResponse;
import com.rovaindu.serviesdashboard.utils.Constants;
import com.rovaindu.serviesdashboard.utils.map.PlacePicker;
import com.rovaindu.serviesdashboard.utils.views.TextViewAr;
import com.rovaindu.serviesdashboard.utils.views.toasty.Toasty;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProfileActivity extends AppCompatActivity {

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(this, getResources().getString(R.string.permisson_denied), Toast.LENGTH_SHORT).show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }


    private String check;
    private TextInputLayout txFirstName  , txInputEmail  , txInputPhone;
    private TextInputEditText etFirstName  , etEmail   , etPhone;
    private MaterialButton finishButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        TextViewAr appname = findViewById(R.id.appname);
        appname.setVisibility(View.VISIBLE);
        appname.setText(getResources().getString(R.string.edit_profile));

        // Back Button
        ImageView backButton = findViewById(R.id.backButton);
        backButton.setVisibility(View.VISIBLE);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        txFirstName = findViewById(R.id.txFirstName);
        txInputEmail = findViewById(R.id.txInputEmail);
        txInputPhone = findViewById(R.id.txInputPhone);


        etFirstName = findViewById(R.id.etFirstName);
        etEmail = findViewById(R.id.etEmail);
        etPhone = findViewById(R.id.etPhone);
        finishButton = findViewById(R.id.signup_finish_button);

        finishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(etFirstName.getText().length() > 0) {
                    if (isEmailValid(etEmail.getText().toString())) {
                                if (etPhone.getText().length() == 11) {

                                        attemptLogin(etFirstName.getText().toString(), etEmail.getText().toString(), etPhone.getText().toString());
                                } else {
                                    txInputPhone.setError(getResources().getString(R.string.please_enter_valid_phone));
                                }


                    } else {
                        txInputEmail.setError(getResources().getString(R.string.enter_valid_email));
                    }
                }
                else
                {
                    txFirstName.setError(getResources().getString(R.string.pleae_enter_first_name));
                }

            }
        });
        etFirstName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                etFirstName.setError(null);

                txFirstName.setErrorEnabled(false); // disable error


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        etEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                etEmail.setError(null);

                txInputEmail.setErrorEnabled(false); // disable error

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        etPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                etPhone.setError(null);

                txInputPhone.setErrorEnabled(false); // disable error


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    public static String convertImageToStringForServer(Bitmap imageBitmap){
        try {


            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            if (imageBitmap != null) {
                imageBitmap.compress(Bitmap.CompressFormat.JPEG, 60, stream);
                byte[] byteArray = stream.toByteArray();
                return Base64.encodeToString(byteArray, Base64.DEFAULT);
            } else {
                return null;
            }
        }
        catch (Exception e)
        {

            Log.d("REG", "convertImageToStringForServer: " + e.getLocalizedMessage());
            return null;
        }
    }




    private void attemptLogin(String name , String email , String phone ){

        ServiesAgent user = ServiesAgentSharedPrefManager.getInstance(getApplicationContext()).getUser();
        ApiInterface service = RetrofitClient.retrofitAPIWrite("ar" , user.getApiToken()).create(ApiInterface.class);

        FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
            @Override
            public void onComplete(@NonNull Task<InstanceIdResult> task) {


                RequestBody requestBody = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("name", name)
                        .addFormDataPart("email", email)
                        .addFormDataPart("phone", phone)
                        .addFormDataPart("start_time", "08:00:00")
                        .addFormDataPart("end_time", "17:00:00")
                        .build();
                Call<UpdateProfileResponse> call_login = service.update_profile(
                        requestBody
                );
                call_login.enqueue(new Callback<UpdateProfileResponse>() {
                    @Override
                    public void onResponse(Call<UpdateProfileResponse> call, Response<UpdateProfileResponse> response) {

                        if(response.body() != null) {
                            if (response.body().getErrors().size() > 0) {
                                //etPassword.setError(response.body().getMessage());
                                Log.d("REG", "onResponse: " + response.body().getMessage());
                            } else {
                                Log.d("REG", "onResponse: "+response.body().getData().getEmail());
                                //TODO POJO USER INFO
                                //TODO POJO USER INFO
                                if(!ServiesAgentSharedPrefManager.getInstance(getApplicationContext()).isLoggedIn()) {

                                    ServiesAgentSharedPrefManager.getInstance(getApplicationContext()).userLogin(response.body().getData());
                                }
                                startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                                finish();
                            }

                        }
                        else
                        {
                            Log.d("REG", "onResponse: " + response.code());
                            Log.d("REG", "onResponse: " + response.message());
                            Log.d("REG", "onResponse: " + response.errorBody().toString());
                        }
                    }

                    @Override
                    public void onFailure(Call<UpdateProfileResponse> call, Throwable t) {
                        Log.d("REG", "onFailure: " + t.getLocalizedMessage());
                        txInputEmail.setError(getResources().getString(R.string.invild_email_or_password));
                    }
                });



            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("REG", "onFailure: " + e.getLocalizedMessage());
            }
        });



    }

    private boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }


    private boolean validateEmail() {

        check = etEmail.getText().toString();

        if (check.length() < 8 || check.length() > 40) {
            return false;
        } else if (!check.matches("^[A-za-z0-9.@]+")) {
            return false;
        } else if (!check.contains("@") || !check.contains(".")) {
            return false;
        }

        return true;
    }







}