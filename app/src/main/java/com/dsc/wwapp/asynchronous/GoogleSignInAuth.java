package com.dsc.wwapp.asynchronous;

import android.content.Context;

import com.dsc.wwapp.R;
import com.dsc.wwapp.utils.PrefManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

public class GoogleSignInAuth {

    Context context;
    GoogleSignInClient mGoogleSignInClient;
    FirebaseAuthHandler firebaseAuthHandler;
    PrefManager prefManager;

    public GoogleSignInAuth(Context context){

        this.context = context;
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(context.getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(context, gso);
    }




}
