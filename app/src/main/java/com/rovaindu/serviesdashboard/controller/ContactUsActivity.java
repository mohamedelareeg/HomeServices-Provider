package com.rovaindu.serviesdashboard.controller;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import com.rovaindu.serviesdashboard.HomeActivity;
import com.rovaindu.serviesdashboard.R;
import com.rovaindu.serviesdashboard.manager.ServiesAgentSharedPrefManager;
import com.rovaindu.serviesdashboard.retrofit.ApiInterface;
import com.rovaindu.serviesdashboard.retrofit.RetrofitClient;
import com.rovaindu.serviesdashboard.retrofit.models.ServiesAgent;
import com.rovaindu.serviesdashboard.retrofit.models.UserSendComplain;
import com.rovaindu.serviesdashboard.utils.views.TextViewAr;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContactUsActivity extends AppCompatActivity {
    ServiesAgent user;
    private TextViewAr btnProcced;
    private EditText contactFullName ,contactEmailID , contactPhone , contactTitle , contactMessage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);

        TextViewAr appname = findViewById(R.id.appname);
        appname.setVisibility(View.VISIBLE);
        appname.setText(getResources().getString(R.string.forget_password));

        // Back Button
        ImageView backButton = findViewById(R.id.backButton);
        backButton.setVisibility(View.VISIBLE);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        contactFullName = findViewById(R.id.contactFullName);
        contactEmailID = findViewById(R.id.contactEmailID);
        contactPhone = findViewById(R.id.contactPhone);
        contactTitle = findViewById(R.id.contactTitle);
        contactMessage = findViewById(R.id.contactMessage);
        btnProcced = findViewById(R.id.btnProcced);
        if(ServiesAgentSharedPrefManager.getInstance(getApplicationContext()).isLoggedIn())
        {
            user = ServiesAgentSharedPrefManager.getInstance(getApplicationContext()).getUser();
            contactFullName.setText(user.getName());
            contactEmailID.setText(user.getEmail());
            if(user.getPhone() != null && !user.getPhone().equals("0"))
            {
                contactPhone.setText(user.getPhone());
            }
        }

        btnProcced.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String name = contactFullName.getText().toString();
                final String email = contactEmailID.getText().toString();
                final String phone = contactPhone.getText().toString();
                final String title = contactTitle.getText().toString();
                final String message = contactMessage.getText().toString();


                if (!TextUtils.isEmpty(name)) {
                    if(!TextUtils.isEmpty(email)) {
                        if(!TextUtils.isEmpty(phone)) {
                            if(!TextUtils.isEmpty(message)) {
                                if (isEmailValid(contactEmailID.getText().toString())) {
                                    if (contactPhone.getText().length() == 11) {
                                        sendData(name, email, phone , title, message );
                                    }
                                    else
                                    {
                                        contactPhone.setError(getResources().getString(R.string.error_phone));
                                    }
                                }
                                else
                                {
                                    contactEmailID.setError(getResources().getString(R.string.enter_valid_email));
                                }
                            }
                            else
                            {
                                contactMessage.setError(getResources().getString(R.string.required));
                            }
                        }
                        else
                        {
                            contactPhone.setError(getResources().getString(R.string.required));
                        }
                    }
                    else
                    {
                        contactEmailID.setError(getResources().getString(R.string.required));
                    }
                }
                else
                {
                    contactFullName.setError(getResources().getString(R.string.required));
                }
            }
        });

    }
    public void sendData(String fullName , String email , String phone , String title , String message )
    {

        ServiesAgent user = ServiesAgentSharedPrefManager.getInstance(getApplicationContext()).getUser();
        ApiInterface service = RetrofitClient.retrofitAPIWrite("ar" , user.getApiToken()).create(ApiInterface.class);

        FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
            @Override
            public void onComplete(@NonNull Task<InstanceIdResult> task) {


                RequestBody requestBody = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("title", title)
                        .addFormDataPart("body", message)
                        .build();
                Call<UserSendComplain> call_login = service.send_user_complain(
                        requestBody
                );
                call_login.enqueue(new Callback<UserSendComplain>() {
                    @Override
                    public void onResponse(Call<UserSendComplain> call, Response<UserSendComplain> response) {

                        if(response.body() != null) {
                            if (response.body().getErrors().size() > 0) {
                                //etPassword.setError(response.body().getMessage());
                                Log.d("REG", "onResponse: " + response.body().getMessage());
                            } else {

                                showConfirmedAlert();
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
                    public void onFailure(Call<UserSendComplain> call, Throwable t) {
                        Log.d("REG", "onFailure: " + t.getLocalizedMessage());

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
    private void showConfirmedAlert(){

        try {
            View parentLayout = findViewById(android.R.id.content);
            Snackbar snackbar = Snackbar
                    .make(parentLayout, getResources().getString(R.string.message_sended_successfuly), Snackbar.LENGTH_LONG);

            // Changing action button text color
            View sbView = snackbar.getView();
            TextView textView = (TextView) sbView.findViewById(R.id.snackbar_text);
            textView.setTextColor(Color.WHITE);

            snackbar.show();
        }
        catch (Exception e)
        {
            Toast.makeText(this, getResources().getString(R.string.message_sended_successfuly), Toast.LENGTH_SHORT).show();
        }

    }
    private void showInternetAlert(View view){

        Snackbar snackbar = Snackbar
                .make(view, "No internet connection!", Snackbar.LENGTH_LONG)
                .setAction("RETRY", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                    }
                });

        // Changing message text color
        snackbar.setActionTextColor(Color.RED);

        // Changing action button text color
        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(R.id.snackbar_text);
        textView.setTextColor(Color.YELLOW);

        snackbar.show();

    }
}