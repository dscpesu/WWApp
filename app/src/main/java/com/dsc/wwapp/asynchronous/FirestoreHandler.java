package com.dsc.wwapp.asynchronous;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.dsc.wwapp.utils.PrefManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

import static com.dsc.wwapp.activites.MainActivity.TAG;

public class FirestoreHandler {

    private FirebaseFirestore db;
    private Context context;
    private DocumentSnapshot snapshot;
    private String fieldValue;
    private PrefManager pref;

    public FirestoreHandler(Context context){
        this.context = context;
        db = FirebaseFirestore.getInstance();
        pref = new PrefManager(context);
    }

    public void addData(String uid){ //later add params here
        Map<String, Object> user = new HashMap<>();
        //demo values
        user.put("first", "John");
        user.put("last", "Doe");
        user.put("born", 1988);
        user.put("uid",uid);

        // Add a new document with a generated ID
        db.collection("tasks")
                .add(user)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                        pref.setFirebaseDocId(documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });
    }

    public void getAllData(String docId){

        db.collection("tasks")
                .document(docId)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful()){
                            snapshot = task.getResult();
                            for (Map.Entry<String, Object> document : snapshot.getData().entrySet()) {
                                Log.d(TAG, document.getKey() + " => " + document.getValue());
                                Toast.makeText(context, "Success. Check logcat for details", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context, "Failed with exception" + e.getMessage(), Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        });


    }

    public String getData(String docId,final String key){

        db.collection("tasks")
                .document(docId)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful()){
                            fieldValue = (String) task.getResult().get(key);
                        }
                    }
                });

        if(fieldValue != null)
            return fieldValue;
        return "";
    }

    public void checkIfUserExistInDB(final String uid) {

        db.collection("tasks")
                .whereEqualTo("uid",uid)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        DocumentSnapshot snapshot = queryDocumentSnapshots.getDocuments().get(0);
                        pref.setFirebaseDocId(snapshot.getId());
                        for (Map.Entry<String, Object> document : snapshot.getData().entrySet()) {
                            Log.d(TAG, document.getKey() + " => " + document.getValue());

                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //user does not exist in db. create a new field in db

                        addData(uid);
                    }
                });

    }
}
