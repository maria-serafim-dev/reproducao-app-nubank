package com.example.nubank.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.nubank.R;
import com.example.nubank.databinding.ActivityMainBinding;

import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    // getInstance() -> Devolve a instancia do firebase.
    // getReference() -> Devolve a referencia de um nó. Sem parenteses
    private DatabaseReference referencia = FirebaseDatabase.getInstance().getReference();
    ActivityMainBinding binding;
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    GoogleSignInClient mGoogleSignInClient;
    GoogleSignInAccount account;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);

        /*GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();


        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);*/

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);


        binding.imageUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mGoogleSignInClient.signOut();
                //signOut();
                signOutGoogle();
                nextAcitivity();
            }
        });

        account = GoogleSignIn.getLastSignedInAccount(this);
        //Log.i("Teste", "" + account);
        /*DatabaseReference no_usuario = referencia.child("user");
        Usuario usuario = new Usuario();
        usuario.setNome("Leonardo Serafim Bezerra");
        usuario.setIdade(25);
        no_usuario.child("004").setValue(usuario);


        no_usuario.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.i("FIREBASE", snapshot.getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });*/

    }
    public void nextAcitivity(){
        Intent intent = new Intent(this, Authetication.class);
        startActivity(intent);
    }

    private void signOut() {
        auth.getInstance().signOut();
    }
    private void signOutFacebook() {

        LoginManager.getInstance().logOut();
    }
    private void signOutGoogle() {
        mGoogleSignInClient.signOut();
    }


    public void onStart() {
        super.onStart();

        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        //FirebaseUser currentUser = auth.getCurrentUser();

        if (account != null) {
            Log.i("CreateUser", "Main Activity2 - Usuário logado");
            //nextAcitivity();
        } else {
            Log.i("CreateUser", "Main Activity - Usuário não logado");
        }
        //updateUI(currentUser);
    }
}