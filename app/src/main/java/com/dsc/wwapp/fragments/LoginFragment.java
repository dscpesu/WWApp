package com.dsc.wwapp.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.dsc.wwapp.R;
import com.dsc.wwapp.activites.MainActivity;
import com.dsc.wwapp.asynchronous.FirebaseAuthHandler;
import com.dsc.wwapp.utils.PrefManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import static android.app.Activity.RESULT_OK;

public class LoginFragment extends Fragment {

    FirebaseAuthHandler firebaseAuthHandler;;
    GoogleSignInClient mGoogleSignInClient;
    FirebaseAuth firebaseAuth;
    PrefManager pref ;

    public LoginFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getActivity().findViewById(R.id.switchTosignup).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().popBackStackImmediate();

            }
        });

        getActivity().findViewById(R.id.loginButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginEmailPassword();
            }
        });

        getActivity().findViewById(R.id.sign_in_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                googleSignIn();
            }
        });

    }

    private void googleSignIn() {

        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent,2);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 2){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignIn(task);
        }
    }

    private void handleSignIn(Task<GoogleSignInAccount> task) {

        try {
            GoogleSignInAccount account = task.getResult(ApiException.class);

            // Signed in successfully, show authenticated UI.
            firebaseAuthHandler.firebaseAuthWithGoogle(account);
            updateUI(account,null);

        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w(MainActivity.TAG, "signInResult:failed code=" + e.getStatusCode());
            updateUI(null,null);
        }

    }

    private void updateUI(GoogleSignInAccount account,String email) {

        if(account!=null || email!=null && email.equals(pref.getUserEmail())){
            Intent intent = new Intent();
            intent.putExtra("login",1);
            getActivity().setResult(RESULT_OK,intent);
            getActivity().finish();
        }

    }

    @Override
    public void onStart() {
        super.onStart();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(getActivity(),gso);
        firebaseAuthHandler = new FirebaseAuthHandler(getContext());
        pref = new PrefManager(getActivity());

        firebaseAuth = FirebaseAuth.getInstance();


    }

    private void loginEmailPassword() {

        EditText EtEmail = getActivity().findViewById(R.id.login_email);
        EditText Etpassword = getActivity().findViewById(R.id.login_password);

        String  email = EtEmail.getText().toString();
        String password = Etpassword.getText().toString();

        firebaseAuthHandler.loginEmailPassword(email,password);
    }
}
