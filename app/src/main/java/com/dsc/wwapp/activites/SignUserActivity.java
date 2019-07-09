package com.dsc.wwapp.activites;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;


import com.dsc.wwapp.R;
import com.dsc.wwapp.asynchronous.FirebaseAuthHandler;
import com.dsc.wwapp.fragments.LoginFragment;
import com.dsc.wwapp.utils.PrefManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class SignUserActivity extends AppCompatActivity {

    GoogleSignInClient mGoogleSignInClient;
    FirebaseAuthHandler firebaseAuthHandler;
    PrefManager prefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_user);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        firebaseAuthHandler = new FirebaseAuthHandler(this);

        findViewById(R.id.sign_in_button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                googleSignIn();
            }
        });

        findViewById(R.id.signup_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createUserEmailPassword(view);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        String email = null;
        prefManager = new PrefManager(this);
        try{
            email = firebaseAuth.getCurrentUser().getEmail();
        }catch (NullPointerException e){
            e.printStackTrace();
        }
        if(email == null){
            updateUI(account,null);
        }else
            updateUI(account, email);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 2) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignIn(task);
        }
    }

    private void handleSignIn(Task<GoogleSignInAccount> task) {

        try {
            final GoogleSignInAccount account = task.getResult(ApiException.class);

            Log.i(MainActivity.TAG,"handle sign in");
            // Signed in successfully, show authenticated UI.
            //firebaseAuthHandler.firebaseAuthWithGoogle(account);

            ExecutorService executor = Executors.newCachedThreadPool();
            Callable<Boolean> job = new Callable<Boolean>() {
                public Boolean call() {
                     return firebaseAuthHandler.firebaseAuthWithGoogle(account);

                }
            };
            Future<Boolean> future = executor.submit(job);
            try {
                Object result = future.get(10, TimeUnit.SECONDS);

            } catch (TimeoutException ex) {
                // handle the timeout
            } catch (InterruptedException e) {
                // handle the interrupts
            } catch (ExecutionException e) {
                // handle other exceptions
            } finally {
                future.cancel(true); // may or may not desire this
            }
            updateUI(account, null);

        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w(MainActivity.TAG, "signInResult:failed code=" + e.getStatusCode());
            updateUI(null, null);
        }

    }

    private void googleSignIn() {

        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, 2);

    }


    private void updateUI(GoogleSignInAccount account, String email) {

        if (account != null || email != null && email.equals(prefManager.getUserEmail())) {
            Intent intent = new Intent();
            intent.putExtra("login", 1);
            setResult(RESULT_OK, intent);
            finish();
        }

    }

    public void createUserEmailPassword(View view) {

        EditText email = findViewById(R.id.signup_email);
        EditText password = findViewById(R.id.signup_password);

        String strEmail = email.getText().toString();
        //run pregmatch to check if entered email is valid
        //pregmatch()

        String psk = password.getText().toString();
        Log.i(MainActivity.TAG, strEmail);
        Log.i(MainActivity.TAG, psk);

        Log.i(MainActivity.TAG, "signup");
        firebaseAuthHandler.createUser(strEmail, psk);

    }

    public void switchlogin(View view) {

        Fragment fragment = new LoginFragment();
        FragmentTransaction ft;
        FragmentManager mFragmentManager = this.getSupportFragmentManager();


        ft = mFragmentManager.beginTransaction();
        ft.replace(R.id.signup_activity_layout, fragment).addToBackStack("fragBack");
        ft.commit();


    }


}