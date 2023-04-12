package com.rovaindu.serviesdashboard.auth;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.emoji.bundled.BundledEmojiCompatConfig;
import androidx.emoji.text.EmojiCompat;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.rovaindu.serviesdashboard.HomeActivity;
import com.rovaindu.serviesdashboard.R;
import com.rovaindu.serviesdashboard.base.BaseActivity;
import com.rovaindu.serviesdashboard.controller.forgetpassword.ForgetPasswordActivity;
import com.rovaindu.serviesdashboard.manager.ServiesAgentSharedPrefManager;
import com.rovaindu.serviesdashboard.retrofit.ApiInterface;
import com.rovaindu.serviesdashboard.retrofit.RetrofitClient;
import com.rovaindu.serviesdashboard.retrofit.response.UserResponse;
import com.rovaindu.serviesdashboard.utils.views.TextViewAr;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends BaseActivity {


    private EditText etEmail , etPassword;
    private MaterialButton signinButton;
    private TextView signupText , viewForgetPassword;
    private TextInputLayout etpasswordPanel;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Window w = getWindow();
        w.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        TextViewAr appname = findViewById(R.id.appname);
        appname.setVisibility(View.VISIBLE);
        appname.setText(getResources().getString(R.string.sign_in));
        EmojiCompat.Config config = new BundledEmojiCompatConfig(this);
        EmojiCompat.init(config);
        //initializing vars
        etpasswordPanel = findViewById(R.id.etpasswordPanel);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etPassword.setTransformationMethod(new PasswordTransformationMethod());
        signinButton = findViewById(R.id.signin_button);
        signupText = findViewById(R.id.signup_textview);
        signinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isEmailValid(etEmail.getText().toString())){
                    if (etPassword.getText().length() > 6){
                        attemptLogin(etEmail.getText().toString(), etPassword.getText().toString());
                    }else {
                        etpasswordPanel.setError(getResources().getString(R.string.password_should_longer_than_six));
                    }
                }else {
                    etEmail.setError(getResources().getString(R.string.enter_valid_email));
                }
            }
        });
        etPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                etPassword.setError(null);

                etpasswordPanel.setErrorEnabled(false); // disable error

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        viewForgetPassword = findViewById(R.id.viewForgetPassword);
        viewForgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, ForgetPasswordActivity.class);
                startActivity(i);
            }
        });


        signupText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, CreateAgentActivity.class);
                View sharedView = findViewById(R.id.logo);
                String transName = "splash_anim";
                ActivityOptions transitionActivityOptions = ActivityOptions.makeSceneTransitionAnimation(LoginActivity.this, sharedView, transName);
                startActivity(intent, transitionActivityOptions.toBundle());
            }
        });

    }


    private void attemptLogin(String email, String password){


        ApiInterface service = RetrofitClient.retrofitWrite("ar").create(ApiInterface.class);

        FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
            @Override
            public void onComplete(@NonNull Task<InstanceIdResult> task) {


                RequestBody requestBody = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("email", email)
                        .addFormDataPart("password", password)
                        .build();
                Call<UserResponse> call = service.Login(requestBody);

                call.enqueue(new Callback<UserResponse>() {
                    @Override
                    public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {

                        if(response.body() != null) {
                            if (response.body().getErrors().size() > 0) {
                                etPassword.setError(response.body().getMessage());
                                Log.d("REG", "onResponse: " + response.body().getMessage());
                            } else {
                                Log.d("REG", "onResponse: "+response.body().getData().getEmail());
                                //TODO POJO USER INFO
                                if (!ServiesAgentSharedPrefManager.getInstance(getApplicationContext()).isLoggedIn()) {
/*
                                    UserAddress addressData = new UserAddress(37.42199845544925, -122.0839998498559, "Mountain View, CA 94043");
                                    //int id, String name, String email, String thumb_image, String gender, String country, String city, String phone, String gcmtoken, UserAddress userAddress
                                    User user = new User(1, "محمد السيد", "mohaa.coder@yahoo.com",
                                            "https://www.mantruckandbus.com/fileadmin/media/bilder/02_19/219_05_busbusiness_interviewHeader_1485x1254.jpg", "رجل"
                                            , "المملكة العربية السعودية", "الرياض", "1277637646", "", addressData);


 */
                                    //SharedPrefManager.getInstance(getApplicationContext()).userLogin(user);
                                    ServiesAgentSharedPrefManager.getInstance(getApplicationContext()).userLogin(response.body().getData());
                                }
                                startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                                LoginActivity.this.finish();
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
                    public void onFailure(Call<UserResponse> call, Throwable t) {
                        Log.d("REG", "onFailure: " + t.getLocalizedMessage());
                        etEmail.setError(getResources().getString(R.string.invild_email_or_password));
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
}