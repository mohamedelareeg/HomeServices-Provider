package com.rovaindu.serviesdashboard;

import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.google.android.material.button.MaterialButton;
import com.rovaindu.serviesdashboard.adapter.SliderAdapter;
import com.rovaindu.serviesdashboard.auth.LoginActivity;
import com.rovaindu.serviesdashboard.base.BaseActivity;
import com.rovaindu.serviesdashboard.manager.ServiesAgentSharedPrefManager;
import com.rovaindu.serviesdashboard.model.UserAddress;
import com.rovaindu.serviesdashboard.utils.views.TextViewAr;

public class MainActivity extends BaseActivity {

    private ViewPager sViewPager;
    private LinearLayout dotsLayout;
    private TextViewAr dots[];
    private MaterialButton signin_button;
    SliderAdapter sliderAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        // find view id
        initView();

        // create Adapter object
        sliderAdapter = new SliderAdapter(this);
        // set adapter in ViewPager
        sViewPager.setAdapter(sliderAdapter);
        // set PageChangeListener
        sViewPager.addOnPageChangeListener(viewPagerPageChangeListener);

        // adding bottom dots -> addBottomDots(0);
//      addDotIndicator(0);
        addBottomDots(0);
        //TODO POJO USER INFO
        if(!ServiesAgentSharedPrefManager.getInstance(getApplicationContext()).isLoggedIn()) {
            /*
            UserAddress addressData = new UserAddress(37.42199845544925, -122.0839998498559, "Mountain View, CA 94043");
            //int id, String name, String email, String thumb_image, String gender, String country, String city, String phone, String gcmtoken, UserAddress userAddress
            User user = new User(1, "محمد السيد", "mohaa.coder@yahoo.com",
                    "https://www.mantruckandbus.com/fileadmin/media/bilder/02_19/219_05_busbusiness_interviewHeader_1485x1254.jpg", "رجل"
                    , "المملكة العربية السعودية", "الرياض", "1277637646", "", addressData);

             */
            //ServiesAgentSharedPrefManager.getInstance(getApplicationContext()).userLogin(user);
        }
        signin_button = findViewById(R.id.signin_button);
        signin_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sendtoHome();

            }
        });
    }
    private void sendtoHome() {

        finish();
        if(ServiesAgentSharedPrefManager.getInstance(this).isLoggedIn()) {
            startActivity(new Intent(this, HomeActivity.class));
        }
        else
        {
            startActivity(new Intent(this, LoginActivity.class));
        }



    }
    private void initView() {
        sViewPager = (ViewPager) findViewById(R.id.sViewPager);
        dotsLayout = (LinearLayout) findViewById(R.id.layoutDots);
    }

    // viewPagerPage ChangeListener according to Dots-Points
    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(int position) {
            addBottomDots(position);
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }
    };



    // add dot indicator
    public void addDotIndicator(){
        dots = new TextViewAr[3];
        for (int i=0; i<dots.length; i++){
            dots[i] = new TextViewAr(this);
            dots[i].setText(Html.fromHtml("&#8266;"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(getResources().getColor(R.color.white));

            dotsLayout.addView(dots[i]);
        }
    }

    // set of Dots points
    private void addBottomDots(int currentPage) {
//        dots = new TextView[layouts.length];
        dots = new TextViewAr[3];
        dotsLayout.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextViewAr(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(getResources().getColor(R.color.white));  // dot_inactive
            dotsLayout.addView(dots[i]);
        }

        if (dots.length > 0)
            dots[currentPage].setTextColor(getResources().getColor(R.color.blue_500)); // dot_active
    }
}