package com.rovaindu.serviesdashboard.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.rovaindu.serviesdashboard.HomeActivity;
import com.rovaindu.serviesdashboard.R;
import com.rovaindu.serviesdashboard.manager.ServiesAgentSharedPrefManager;
import com.rovaindu.serviesdashboard.retrofit.ApiInterface;
import com.rovaindu.serviesdashboard.retrofit.RetrofitClient;
import com.rovaindu.serviesdashboard.retrofit.models.ServiesAgent;
import com.rovaindu.serviesdashboard.retrofit.response.ContactUsResponse;
import com.rovaindu.serviesdashboard.utils.views.TextViewAr;

import java.util.Objects;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContactFragment extends Fragment {


    public ContactFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private EditText field_body;
    private TextViewAr btnContinue;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contact, container, false);
        // Inflate the layout for this fragment
        ((HomeActivity) Objects.requireNonNull(getActivity())).appname.setText(getResources().getString(R.string.contact_us));
        // Inflate the layout for this fragment
        field_body = view.findViewById(R.id.field_body);
        btnContinue = view.findViewById(R.id.btnContinue);

        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(field_body.getText().length() > 0) {
                    ContactUs(field_body.getText().toString());
                }
            }
        });

        return view;
    }

    private void ContactUs(String ContactBody){

        ServiesAgent user = ServiesAgentSharedPrefManager.getInstance(getActivity()).getUser();
        ApiInterface service = RetrofitClient.retrofitAPIWrite("ar" , user.getApiToken()).create(ApiInterface.class);

        FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
            @Override
            public void onComplete(@NonNull Task<InstanceIdResult> task) {

                RequestBody requestBody = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("body", ContactBody)
                        .build();

                Call<ContactUsResponse> call_login = service.contact_us(
                        requestBody
                );
                call_login.enqueue(new Callback<ContactUsResponse>() {
                    @Override
                    public void onResponse(Call<ContactUsResponse> call, Response<ContactUsResponse> response) {

                        if(response.body() != null) {
                            if (response.body().getErrors().size() > 0) {
                                Log.d("REG", "onResponse: " + response.body().getMessage());
                            } else {
                                getActivity().onBackPressed();
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
                    public void onFailure(Call<ContactUsResponse> call, Throwable t) {
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
}