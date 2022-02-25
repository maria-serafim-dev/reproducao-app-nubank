package com.example.nubank.ui;

import androidx.annotation.NonNull;
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
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    private FirebaseAuth auth = FirebaseAuth.getInstance();

    private GoogleSignInClient mGoogleSignInClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        initializeGoogle();
        clickListenerButtonLogout();

    }

    private void initializeGoogle() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    private void clickListenerButtonLogout() {
        binding.imgLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mGoogleSignInClient.signOut();
                signOut();
                signOutGoogle();
                signOutFacebook();
                nextAcitivity();
            }
        });
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
        FirebaseUser currentUser = auth.getCurrentUser();

        if (account != null || currentUser != null) {
            Log.i("Mensagem", "Usuário logado");

        } else {
            Log.i("Mensagem", "Usuário não logado");
        }

    }
}