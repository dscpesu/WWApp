package com.dsc.wwapp.activites;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.dsc.wwapp.adapter.OnboardingAdapter;
import com.dsc.wwapp.ui.OnboardingPageTransformer;
import com.dsc.wwapp.R;
import com.dsc.wwapp.utils.PrefManager;
import com.dsc.wwapp.utils.ProfileQuestions;

public class WelcomeActivity extends AppCompatActivity {


    public static PrefManager prefManager;
    private ProfileQuestions profileQuestions;
    private ViewPager viewPager;
    private OnboardingAdapter onboardingAdapter;
    private AnimationDrawable anim;
    private Animation smallToBig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.intro_screen);

        setAnimations();
        prefManager = new PrefManager(this);

        if (!prefManager.isFirstTimeLaunch()) {
            launchHomeScreen();
            finish();
        }

        if(prefManager.isFirstTimeLaunch()){
            profileQuestions = new ProfileQuestions(this);
            profileQuestions.createProfile();

        }


        ConstraintLayout container = (ConstraintLayout) findViewById(R.id.welcome_activity_root_container);
        anim = (AnimationDrawable) container.getBackground();
        anim.setExitFadeDuration(2000);

        makeStatusbarTransparent();


    }

    private void setAnimations() {
        smallToBig = AnimationUtils.loadAnimation(this, R.anim.small_to_big);
        findViewById(R.id.imageView2).startAnimation(smallToBig);
    }

    private int getItem(int i) {
        return viewPager.getCurrentItem() + i;
    }

    private void launchHomeScreen() {
        Log.i(MainActivity.TAG,"launch home screen");

        startActivity(new Intent(WelcomeActivity.this, SignupActivity.class));
        finish();
    }


    private void makeStatusbarTransparent() {
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }


    public void nextPage(View view) {
        int current = getItem(+1);
        if (current < onboardingAdapter.layouts.length) {
            // move to next screen
            viewPager.setCurrentItem(current);
        } else {
            launchHomeScreen();
        }
    }

    public void startIntro(View view) {




        //toggle debug mode from here
//        prefManager.setDebugMode(false);
//
//        if(prefManager.isDebugMode()){
//
//            prefManager.setFirstTimeLaunch(true);
//        }

        //check if welcome screen is already displayed or not



        // Making notification bar transparent
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }



        Animation animation = AnimationUtils.loadAnimation(this,R.anim.slide_down);
        findViewById(R.id.welcome_activity_root_container).startAnimation(animation);

        setContentView(R.layout.activity_welcome);
        viewPager = (ViewPager) findViewById(R.id.onboarding_view_pager);
        onboardingAdapter = new OnboardingAdapter(this);
        viewPager.setAdapter(onboardingAdapter);
        viewPager.setPageTransformer(false, new OnboardingPageTransformer());
    }

}
