package com.dsc.wwapp.activites;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
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

import java.util.regex.Matcher;

import static com.dsc.wwapp.activites.SignupActivity.VALID_EMAIL_ADDRESS_REGEX;

public class LoginActivity extends AppCompatActivity {


    GoogleSignInClient mGoogleSignInClient;
    FirebaseAuthHandler firebaseAuthHandler;
    EditText emailET;
    EditText pskET;
    PrefManager prefManager;
    ProgressDialog pd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        makeStatusbarTransparent();

        emailET = findViewById(R.id.editTextlogin);
        pskET = findViewById(R.id.editText2psk);
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

        findViewById(R.id.textView2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this,SignupActivity.class));
                finish();
            }
        });


        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(validateEmail()){

                    pd.show();
                    firebaseAuthHandler.loginEmailPassword(emailET.getText().toString(),pskET.getText().toString());

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            pd.dismiss();
                        }
                    },2000);
                }

            }
        });

        SignInButton signInButton = findViewById(R.id.gso_button2);
        signInButton.setSize(SignInButton.SIZE_STANDARD);

        findViewById(R.id.gso_button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pd.show();
                googleSignIn();


            }
        });

    }

    private void googleSignIn() {

        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, 2);

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 2) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignIn(task);
        }
        if(requestCode == 1){
            if(data != null){
                pd.dismiss();
                if(data.getIntExtra("signup",0) == 1 ){

                    startActivity(new Intent(this,MainActivity.class));
                }else {
                    Toast.makeText(this,"Encountered some problem. Please try again",Toast.LENGTH_SHORT).show();
                }
            }

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
}
