package com.dsc.wwapp.asynchronous;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.dsc.wwapp.R;
import com.dsc.wwapp.utils.PrefManager;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import static android.app.Activity.RESULT_OK;
import static com.dsc.wwapp.activites.MainActivity.TAG;

public class FirebaseAuthHandler {

    private Context context;
    private FirebaseAuth mAuth;
    private PrefManager pref;
    private FirestoreHandler firestoreHandler;
    private boolean isTaskSuccess =false;


    public FirebaseAuthHandler(Context context){
        this.context = context;
        mAuth = FirebaseAuth.getInstance();
        pref = new PrefManager(context);
        firestoreHandler = new FirestoreHandler(context);
    }

    public void createUser(String strEmail, String psk) {

        mAuth.createUserWithEmailAndPassword(strEmail, psk)
                .addOnCompleteListener((Activity) context, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            pref.setFirebaseAuthUserID(user.getUid());
                            pref.setUserEmail(user.getEmail());
                            firestoreHandler.addData(user.getUid(),user.getDisplayName());
                            Intent intent = new Intent();
                            intent.putExtra("login",1);
                            ((Activity) context).setResult(RESULT_OK,intent);
                            ((Activity) context).finish();

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());

                            Intent intent = new Intent();
                            intent.putExtra("login",0);
                            ((Activity) context).setResult(RESULT_OK,intent);
                            ((Activity) context).finish();
                        }

                        // ...
                    }
                })
        .addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                e.printStackTrace();
                Toast.makeText(context, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

                Intent intent = new Intent();
                intent.putExtra("login",0);

                ((Activity) context).setResult(RESULT_OK,intent);
                ((Activity) context).finish();
            }
        });





    }

    public void loginEmailPassword(String strEmail, String strpsk) {

        mAuth.signInWithEmailAndPassword(strEmail, strpsk)
                .addOnCompleteListener((Activity) context, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            pref.setFirebaseAuthUserID(user.getUid());
                            pref.setUserEmail(user.getEmail());
                            Intent intent = new Intent();
                            intent.putExtra("login",1);
                            ((Activity) context).setResult(RESULT_OK,intent);
                            ((Activity) context).finish();


                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());


                        }

                        // ...
                    }
                });

    }

    public boolean firebaseAuthWithGoogle(GoogleSignInAccount account) {

        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener((Activity) context, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            firestoreHandler.checkIfUserExistInDB(user.getUid(),user.getDisplayName());
                            isTaskSuccess = true;


                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            isTaskSuccess =false;

                        }

                        // ...
                    }
                });

        return isTaskSuccess;
    }
}
