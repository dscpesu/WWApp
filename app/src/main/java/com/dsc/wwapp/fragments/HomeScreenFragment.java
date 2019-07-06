package com.dsc.wwapp.fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.dsc.wwapp.R;
import com.dsc.wwapp.utils.PrefManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static com.dsc.wwapp.activites.MainActivity.TAG;
import static com.dsc.wwapp.utils.Constants.PACKAGE_NAME;

public class HomeScreenFragment extends Fragment {

    private FirebaseFirestore db;
    private FirebaseAuth authUser;
    private PrefManager pref;

    public HomeScreenFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        db = FirebaseFirestore.getInstance();
        authUser = FirebaseAuth.getInstance();
        pref = new PrefManager(Objects.requireNonNull(getContext()));


        if(pref.getFirebaseDocId() == null){
            getDocIdWithEmail();
        }else {
            db.collection("tasks")
                    .document(pref.getFirebaseDocId())
                    .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                    if (task.isSuccessful()) {
                        String doc_email = (String) task.getResult().get("user_email");
                        long profile = (long) task.getResult().get("profileRank");
                        Log.i(TAG, "data retreived from doc id:" + doc_email + " ,profileRank " + profile);
                    }
                }
            });
        }

        View view = inflater.inflate(R.layout.fragment_homescreen,container,false);

        return view;
    }

    private void inputData() {

        Map<String, Object> user = new HashMap<>();
        user.put("doesShower", 0);
        user.put("doesUtensils", 0);
        user.put("keepTapOnBrush", 0);
        user.put("profileRank", 5);
        user.put("rankBrush", 1);
        user.put("rankShower", 0);
        user.put("user_email",this.authUser.getCurrentUser().getEmail());



// Add a new document with a generated ID
        db.collection("tasks")
                .add(user)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.i(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.i(TAG, "Error adding document", e);
                    }
                });
    }

    @Override
    public void onStart() {
        super.onStart();



    }

    private void getDocIdWithEmail() {

        db.collection("tasks")
                .whereEqualTo("user_email" , Objects.requireNonNull(authUser.getCurrentUser()).getEmail())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                                Log.i(TAG, document.getId() + " => " + document.getData());
                                pref.setFirebaseDocId(document.getId());
                            }

                        }else
                            Log.i(TAG,"task unsucessfull");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.i(TAG,"task failed");
                    }
                });
    }
}
