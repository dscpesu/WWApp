package com.dsc.wwapp.activites;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.dsc.wwapp.R;
import com.dsc.wwapp.asynchronous.FirebaseAuthHandler;
import com.dsc.wwapp.utils.PrefManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignupActivity extends AppCompatActivity {

    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    GoogleSignInClient mGoogleSignInClient;
    FirebaseAuthHandler firebaseAuthHandler;
    PrefManager prefManager;
    EditText emailET;
    EditText pskET;
    EditText pskET2;
    ProgressDialog pd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        emailET = findViewById(R.id.editText);
        pskET = findViewById(R.id.editText2);
        pskET2 = findViewById(R.id.editText3);

        prefManager = new PrefManager(this);
        pd = new ProgressDialog(this);
        pd.setIndeterminate(true);
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.setMessage("Just a minute");



        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();


        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        firebaseAuthHandler = new FirebaseAuthHandler(this);


        makeStatusbarTransparent();

        findViewById(R.id.textView2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                finish();

            }
        });


        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validateEmail()&& validatePassword()){
                    pd.show();
                    firebaseAuthHandler.signUpEmail(emailET.getText().toString(), pskET.getText().toString());

                    //TODO: remove all handler related to progress dialog pd in signupActivity.java and loginActivity.java
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            pd.cancel();
                        }
                    },4000);
                }

            }
        });


        // Set the dimensions of the sign-in button.
        SignInButton signInButton = findViewById(R.id.gso_button);
        signInButton.setSize(SignInButton.SIZE_STANDARD);

        findViewById(R.id.gso_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pd.show();
                googleSignIn();

            }
        });



    }

    @Override
    protected void onStart() {
        super.onStart();

        GoogleSignInAccount account = null;
        if(!prefManager.isFirstTimeLaunch()){
            account = GoogleSignIn.getLastSignedInAccount(this);

        }
        prefManager.setFirstTimeLaunch(false);
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

    private void updateUI(GoogleSignInAccount account, String email) {

        if (account != null || email != null && email.equals(prefManager.getUserEmail())) {
            pd.dismiss();
           startActivity(new Intent(this,MainActivity.class));
           finish();
        }

    }

    private void handleSignIn(Task<GoogleSignInAccount> task) {

        try {
            final GoogleSignInAccount account = task.getResult(ApiException.class);

            Log.i(MainActivity.TAG,"handle sign in");
            // Signed in successfully, show authenticated UI.
            firebaseAuthHandler.firebaseAuthWithGoogle(account);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    updateUI(account, null);
                }
            },2000);


        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w(MainActivity.TAG, "signInResult:failed code=" + e.getStatusCode());
            updateUI(null, null);
        }

    }

    private boolean validatePassword() {

        if(pskET.getText().toString().equals(pskET2.getText().toString())){
            if(pskET.getText().length() >= 6)
                return true;
            else{
                Toast.makeText(this, "Password too short.Minimum 6 characters required", Toast.LENGTH_SHORT).show();
                pskET.setText("");
                pskET2.setText("");
            }

        }else{
            Toast.makeText(this, "Passwords don't match.Try again", Toast.LENGTH_SHORT).show();
            pskET.setText("");
            pskET2.setText("");
        }

        return false;

    }

    private boolean validateEmail() {

        String email = emailET.getText().toString();
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX .matcher(email);
        if(matcher.find()){
            return true;
        }else
            Toast.makeText(this, "Invalid email address.Please try again", Toast.LENGTH_SHORT).show();
        return false;
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

    private void googleSignIn() {

        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, 2);

    }
}
