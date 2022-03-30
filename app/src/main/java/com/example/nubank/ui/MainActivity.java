package com.example.nubank.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.nubank.R;
import com.example.nubank.databinding.ActivityMainBinding;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {


    private ActivityMainBinding binding;
    private FirebaseAuth auth = FirebaseAuth.getInstance();

    private GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        initializeGoogle();
        initializeName();
        clickListenerButtonLogout();

    }

    private void initializeName() {
        String nome = this.getIntent().getStringExtra("nome");
        binding.tvGreetings.setText(getString(R.string.txt_greetings, nome));
    }

    private void initializeGoogle() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    private void clickListenerButtonLogout() {
        binding.imgLogout.setOnClickListener(view -> {
            mGoogleSignInClient.signOut();
            signOut();
            signOutGoogle();
            signOutFacebook();
            nextAcitivity();
        });
    }

    public void nextAcitivity(){
        Intent intent = new Intent(this, Authetication.class);
        startActivity(intent);
    }

    private void signOut() {
        auth.signOut();
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