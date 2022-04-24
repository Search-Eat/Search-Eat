package com.example.search_eat_pis.Controller;
import com.example.search_eat_pis.Model.Coordenada;
import com.example.search_eat_pis.Model.Sector;
import com.example.search_eat_pis.Model.Local;


import android.app.Activity;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.android.gms.tasks.Continuation;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;

public class DatabaseAdapter extends Activity {

   public static final String TAG = "DatabaseAdapter";

    public static FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final FirebaseStorage storage = FirebaseStorage.getInstance();
    private final FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseUser user;

    public static DatabaseAdapter databaseAdapter;
    public static vmInterface listener;

    public DatabaseAdapter(vmInterface listener) {
        this.listener = listener;
        databaseAdapter = this;
        FirebaseFirestore.setLoggingEnabled(true);
        initFirebase();
    }

    public interface vmInterface {
        void setSector(ArrayList<Sector> s);
        void setRestaurantes(ArrayList<Local> l);
        void setToast(String s);
    }

    public void initFirebase() {
        user = mAuth.getCurrentUser();

        if (user == null) {
            mAuth.signInAnonymously()
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "signInAnonymously:success");
                                listener.setToast("Authentication successful.");
                                user = mAuth.getCurrentUser();
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "signInAnonymously:failure", task.getException());
                                listener.setToast("Authentication failed.");

                            }
                        }
                    });
        } else {
            listener.setToast("Authentication with current user.");

        }
    }

    public void getSectores() {
        Log.d(TAG, "updatesectores");
        DatabaseAdapter.db.collection("Sectores")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

                            ArrayList<Sector> retrieved_s = new ArrayList<Sector>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                retrieved_s.add(new Sector(document.getString("latitud"), document.getString("longitud"), (ArrayList<String>) document.get("restaurant"), (ArrayList<String>) document.get("bar"), (ArrayList<String>) document.get("cafe")));
                            }
                            listener.setSector(retrieved_s);

                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    public void getRestaurantes(ArrayList<String> restaurantes, Coordenada c) {
        Log.d(TAG, "updaterestaurantes");
        DatabaseAdapter.db.collection("Locales")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

                            ArrayList<Local> retrieved_l = new ArrayList<Local>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                if (restaurantes.contains(document.getId())){
                                    Log.d(TAG, document.getId() + " => " + document.getData());
                                    Local l = new Local( document.getId(),
                                                                document.getString("dirección"),
                                            (ArrayList<String>) document.get("etiquetas"),
                                            (double)            document.get("latitud"),
                                            (double)            document.get("longitud"),
                                                                document.getString("nombre"),
                                                                document.get("valoración"),
                                            (long)              document.get("valoraciones"),
                                                                document.getString("foto"),
                                            (long)              document.get("precio"));
                                    l.setDistancia(c);
                                    retrieved_l.add(l);
                                }

                            }
                            listener.setRestaurantes(retrieved_l);

                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    public void updateValoracion(String id, String val, String num_val){
        db.collection("Locales").document(id)
                .update(
                        "valoración", val,
                        "valoraciones", num_val
                );
    }
}