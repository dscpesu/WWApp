package com.dsc.wwapp.activites;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dsc.wwapp.R;
import com.dsc.wwapp.adapter.NewsAdapter;
import com.dsc.wwapp.asynchronous.FirestoreHandler;
import com.dsc.wwapp.fragments.DashboardFragment;
import com.dsc.wwapp.fragments.GoalsFragment;
import com.dsc.wwapp.fragments.NewsFeedFragment;
import com.dsc.wwapp.fragments.QuestionsFragment;
import com.dsc.wwapp.fragments.ReservoirFragment;
import com.dsc.wwapp.ui.NotificationHandler;
import com.dsc.wwapp.utils.PrefManager;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.Map;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    public static final String TAG = "com.pratik.wwapp";
    private PrefManager prefManager;
    private NotificationHandler nf;
    private FragmentTransaction ft = null;
    private FragmentManager mFragmentManager;
    private boolean doubleBackToExitPressedOnce = false;
    private FirestoreHandler firestoreHandler;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        prefManager = new PrefManager(this);
        firestoreHandler = new FirestoreHandler(this);


        nf = new NotificationHandler();
        nf.createNotificationChannel(this);

        mFragmentManager = this.getSupportFragmentManager();

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        findViewById(R.id.getData).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getData();
            }
        });

       // getSupportFragmentManager().beginTransaction().add(R.id.home_screen,new HomeScreenFragment(),"home");
        if(!prefManager.isQuestionAsked()){
            prefManager.setQuestionAsked(true);
            displaySelectedScreen(R.id.questions_fragment);
        }

    }

    private void getData() {

        Toast.makeText(this, "Retreving data", Toast.LENGTH_SHORT).show();
        String docId = prefManager.getFirebaseDocId();
        if(docId != null) {
            Log.i(TAG,"pref: " + docId);
            firestoreHandler.getAllData(docId);


        }


    }

    @Override
    public void onBackPressed() {


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        int backstack = getSupportFragmentManager().getBackStackEntryCount();
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if(backstack>0){
                for(int i =0 ; i<backstack ; i++)
                    mFragmentManager.popBackStackImmediate();
                    getSupportActionBar().show();
                    findViewById(R.id.getData).setVisibility(View.VISIBLE);
                setTitle(R.string.app_name);
            }else {
                if (doubleBackToExitPressedOnce) {
                    super.onBackPressed();
                    return;
                }
                this.doubleBackToExitPressedOnce = true;
                Toast.makeText(this, "Please back again to exit", Toast.LENGTH_SHORT).show();

                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        doubleBackToExitPressedOnce=false;
                    }
                }, 2000);
            }

        }

    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        int id = menuItem.getItemId();

        displaySelectedScreen(id);

        return true;
     }

    private void displaySelectedScreen(int id) {

        Fragment fragment = null;

        switch (id){
            case R.id.nav_dashboard :
                fragment = new DashboardFragment();
                break;
            case R.id.nav_reservoir :
                fragment = new ReservoirFragment();
                break;
            case R.id.questions_fragment :
                fragment = new QuestionsFragment();
                break;
            case R.id.nav_newsfeed:
                fragment = new NewsFeedFragment();
                break;
            case R.id.nav_goal:
                fragment = new GoalsFragment();
                break;


        }
        if (fragment != null) {
            ft = mFragmentManager.beginTransaction();
            ft.replace(R.id.content_frame, fragment).addToBackStack("fragBack");
            ft.commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 5){
            int backstack = getSupportFragmentManager().getBackStackEntryCount();
            int val = data.getIntExtra("login",-1);
            Log.i(TAG, String.valueOf(val));
            if(val == 1 ){
                if(!prefManager.isQuestionAsked())
                    Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show();
                if(backstack>0){
                    for(int i =0 ; i<backstack ; i++)
                        mFragmentManager.popBackStackImmediate();

                    setTitle(R.string.app_name);

                }
            }else if(val == -1) {
                Toast.makeText(this, "Encountered some problem.try again later", Toast.LENGTH_SHORT).show();
            }

        }
    }

    public void login(View view) {
        startActivityForResult(new Intent(MainActivity.this, SignUserActivity.class),5);
    }


}

