package com.example.search_eat_pis.Controller;
import com.example.search_eat_pis.Model.Coordenada;
import com.example.search_eat_pis.Model.Reserva;
import com.example.search_eat_pis.Model.Sector;
import com.example.search_eat_pis.Model.Local;


import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.example.search_eat_pis.Model.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.android.gms.tasks.Continuation;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
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
        void setLocales(ArrayList<Local> l);
        void setToast(String t);
        void setReservas(ArrayList<Reserva> r);
        void setUsuario(Usuario u);
        void setBoolean(boolean b);
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
        Log.d(TAG, "getSectores");
        DatabaseAdapter.db.collection("Sectores")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

                            ArrayList<Sector> retrieved_s = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                retrieved_s.add(new Sector( document.getString("latitud"),
                                                            document.getString("longitud"),
                                        (ArrayList<String>) document.get("restaurant"),
                                        (ArrayList<String>) document.get("bar"),
                                        (ArrayList<String>) document.get("cafe")));
                            }
                            listener.setSector(retrieved_s);

                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    public void getLocales(ArrayList<String> locales, Coordenada c) {
        Log.d(TAG, "updaterestaurantes");
        DatabaseAdapter.db.collection("Locales")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

                            ArrayList<Local> retrieved_l = new ArrayList<Local>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                if (locales.contains(document.getId())){
                                    Log.d(TAG, document.getId() + " => " + document.getData());
                                    Local l = new Local( document.getId(),
                                                                document.getString("direcci??n"),
                                            (ArrayList<String>) document.get("etiquetas"),
                                            (double)            document.get("latitud"),
                                            (double)            document.get("longitud"),
                                                                document.getString("nombre"),
                                                                document.get("valoraci??n"),
                                            (long)              document.get("valoraciones"),
                                                                document.getString("foto"),
                                            (long)              document.get("precio"));
                                    l.setDistancia(c);
                                    retrieved_l.add(l);
                                }

                            }
                            listener.setLocales(retrieved_l);

                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }



    public void updateValoracion(String id, double val, long num_val){
        Log.d(TAG, "updateValoracion");
        db.collection("Locales").document(id)
                .update(
                        "valoraci??n", val,
                        "valoraciones", num_val
                );
    }

    public void saveUsuario (String correo, String nombre, long telefono, String contrase??a, ArrayList<String> reservas) {

        // Create a new user with a first and last name
        Map<String, Object> usuario = new HashMap<>();
        usuario.put("correo", correo);
        usuario.put("nombre", nombre);
        usuario.put("tel??fono", telefono);
        usuario.put("contrase??a", contrase??a);
        usuario.put("reservas", reservas);

        Log.d(TAG, "saveUsuarios");
        // Add a new document with a generated ID
        db.collection("Usuarios").document(correo)
                .set(usuario)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully written!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error writing document", e);
                    }
                });
    }

    public void saveReserva (String id, String localID, String nombre, long telefono, String local, long personas, long a??o, long mes, long dia, long hora, long minuto) {

        // Create a new user with a first and last name
        Map<String, Object> reserva = new HashMap<>();
        reserva.put("id", id);
        reserva.put("localID", localID);
        reserva.put("nombre", nombre);
        reserva.put("telefono", telefono);
        reserva.put("local", local);
        reserva.put("personas", personas);
        reserva.put("a??o", a??o);
        reserva.put("mes", mes);
        reserva.put("dia", dia);
        reserva.put("hora", hora);
        reserva.put("minuto", minuto);

        Log.d(TAG, "saveReserva");
        // Add a new document with a generated ID
        db.collection("Reservas").document(id)
                .set(reserva)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully written!");
                        listener.setToast("Reserva guardada con ??xito.");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error writing document", e);
                    }
                });
    }

    public void getReservas(ArrayList<String> reservas){
        Log.d(TAG, "getReserva");
        DatabaseAdapter.db.collection("Reservas")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

                            ArrayList<Reserva> retrieved_r = new ArrayList<Reserva>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                if (reservas.contains(document.getId())){
                                    Log.d(TAG, document.getId() + " => " + document.getData());
                                    Reserva r = new Reserva(document.getString("localID"),
                                                            document.getString("nombre"),
                                            (long)          document.get("telefono"),
                                                            document.getString("local"),
                                            (long)          document.get("personas"),
                                            (long)          document.get("a??o"),
                                            (long)          document.get("mes"),
                                            (long)          document.get("dia"),
                                            (long)          document.get("hora"),
                                            (long)          document.get("minuto"));
                                    r.setId(document.getId());
                                    retrieved_r.add(r);
                                }

                            }
                            listener.setReservas(retrieved_r);

                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    public void getUsuario(String correo, String contrase??a){
        Log.d(TAG, "getUsuario");
        DatabaseAdapter.db.collection("Usuarios")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            Usuario usuario = null;
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                if (correo.equals(document.getId())){
                                    Log.d(TAG, document.getId() + " => " + document.getData());
                                    if(contrase??a.equals(document.getString("contrase??a"))){
                                        usuario = new Usuario(document.getString("correo"),
                                                document.getString("nombre"),
                                                (long)              document.get("tel??fono"),
                                                document.getString("contrase??a"),
                                                (ArrayList<String>) document.get("reservas"));
                                    }
                                }

                            }
                            listener.setUsuario(usuario);

                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    public void isValidCorreo(String correo){
        Log.d(TAG, "getUsuario");
        DatabaseAdapter.db.collection("Usuarios")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            boolean b = false;
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                if (correo.equals(document.getId())){
                                    listener.setBoolean(false);
                                    b = true;
                                }
                            }
                            if (b == false){
                                listener.setBoolean(true);
                            }

                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    public void deleteReserva(String id){
        Log.d(TAG, "deleteReserva");
        db.collection("Reservas").document(id)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully deleted!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error deleting document", e);
                    }
                });
    }
    public void deleteReservaUsuario(String reserva, String id){
        Log.d(TAG, "updateUsuario");
        db.collection("Usuarios").document(id).update("reservas", FieldValue.arrayRemove(reserva));
    }
    public void downloadPhotoFromStorage(String path, String id, ImageView img){
        StorageReference storageReference = storage.getReference().child(path+id+".png");

        try{
            File localfile = File.createTempFile("tempfile",".png");
            storageReference.getFile(localfile)
                    .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                            Bitmap bitmap = BitmapFactory.decodeFile(localfile.getAbsolutePath());
                            img.setImageBitmap(bitmap);
                        }
                    });
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}