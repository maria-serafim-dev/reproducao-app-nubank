package com.example.nubank.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;

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
    private final FirebaseAuth auth = FirebaseAuth.getInstance();

    private GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        initializeGoogle();
        initializeName();
        initializeListennerImageEye();
        clickListenerButtonLogout();
        clickListenerButtonAccount();

    }

    private void clickListenerButtonAccount() {
        binding.imgArrowRight.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), CreditCardInformationActivity.class);
            startActivity(intent);
        });
    }

    private void initializeListennerImageEye() {
        binding.imgEyes.setOnClickListener(view -> {
            if (binding.imgEyes.getContentDescription().equals(getString(R.string.dc_eyes_open))) {
                binding.tvSaldoConta.setText(R.string.txt_balance_hidden);
                binding.imgEyes.setImageDrawable(AppCompatResources.getDrawable(this, R.drawable.ic_eyes_closed));
                binding.imgEyes.setContentDescription(getString(R.string.dc_eyes_closed));
            } else {
                binding.tvSaldoConta.setText(R.string.txt_balance);
                binding.imgEyes.setImageDrawable(AppCompatResources.getDrawable(this, R.drawable.ic_eyes_open));
                binding.imgEyes.setContentDescription(getString(R.string.dc_eyes_open));
            }
        });

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
        finish();
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