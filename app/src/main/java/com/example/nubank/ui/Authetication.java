package com.example.nubank.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.nubank.R;
import com.example.nubank.databinding.ActivityAutheticationBinding;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class Authetication extends AppCompatActivity {

    private FirebaseAuth auth = FirebaseAuth.getInstance();
    ActivityAutheticationBinding binding;
    private GoogleSignInClient mGoogleSignInClient;
    private static final int RC_SIGN_IN = 9001;
    private CallbackManager mCallbackManagerFace;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_authetication);


        incializarFacebook();
        incializarGoogle();

        registerCallbackFacebook();
        clickListenerButtonGoogle();
        clickListenerButtonEmailPassword();

        clickListenerNewUser();

    }

    private boolean validarCampos() {
        if (TextUtils.isEmpty(binding.editEmail.getText())) {
            binding.editEmail.setError("Dígite um email");
            binding.editEmail.requestFocus();
            return false;
        } else if(!android.util.Patterns.EMAIL_ADDRESS.matcher(binding.editEmail.getText()).matches()){
            binding.editEmail.setError("E-mail inválido");
            binding.editEmail.requestFocus();
            return false;
        }

        if(TextUtils.isEmpty(binding.editPassword.getText())) {
            binding.editPassword.setError("Dígite uma senha");
            binding.editPassword.requestFocus();
            return false;
        }

        return true;

    }


    private void incializarFacebook() {
        binding.buttonFacebook.setReadPermissions("email", "public_profile");

        mCallbackManagerFace = CallbackManager.Factory.create();
    }

    private void incializarGoogle() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }



    private void clickListenerButtonGoogle() {
        binding.buttonGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });
    }

    private void clickListenerButtonEmailPassword() {
        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validarCampos()) {
                    String email = binding.editEmail.getText().toString();
                    String senha = binding.editPassword.getText().toString();

                    auth.signInWithEmailAndPassword(email, senha).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Log.i("signIn", "Sucesso ao logar usuário");
                                nextActivity();
                            } else {
                                mensagemErro();
                                Log.i("signIn", "Erro ao logar usuário");
                                Log.i("signIn", task.getResult().toString());

                            }
                        }
                    });
                }
            }
        });
    }

    private void registerCallbackFacebook() {

        binding.buttonFacebook.registerCallback(mCallbackManagerFace, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d("FaceBookLogin", "facebook:onSuccess:" +loginResult);
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Log.d("FaceBookLogin", "facebook:onCancel:");
            }

            @Override
            public void onError(FacebookException error) {
                Log.d("FaceBookLogin", "facebook:onError:", error);
            }
        });
    }


    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }else{
            mCallbackManagerFace.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            firebaseAuthWithGoogle(account.getIdToken());

        } catch (ApiException e) {
            Log.w("Error message", "signInResult:failed code=" + e.getStatusCode());

        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d("GoogleLogin", "signInWithCredential:success");
                            FirebaseUser user = auth.getCurrentUser();
                            nextActivity();
                        } else {
                            mensagemErro();
                            Log.w("GoogleLogin", "signInWithCredential:failure", task.getException());
                            updateUI(null);
                        }
                    }
                });
    }

    @Override
    public void onStart() {
        super.onStart();

        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        FirebaseUser currentUser = auth.getCurrentUser();

        if (currentUser != null || account != null) {
            Log.i("Mensagem", "Usuário Logado");
            nextActivity();
        } else {
            Log.i("Mensagem", "Usuário não logado");
        }
    }


    private void handleFacebookAccessToken(AccessToken token) {
        Log.d("FaceBookLogin", "handleFacebookAccessToken:" + token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());

        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d("FaceBookLogin", "signInWithCredential:success");
                            FirebaseUser user = auth.getCurrentUser();
                            nextActivity();
                            updateUI(user);
                        } else {

                            Log.w("FaceBookLogin", "signInWithCredential:failure", task.getException());
                            mensagemErro();
                            updateUI(null);
                        }
                    }
                });
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

    private void updateUI(FirebaseUser user) {

    }

    public void nextActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void nextActivityNewUser(){
        Intent intent = new Intent(this, CreateUserActivity.class);
        startActivity(intent);
    }

    private void clickListenerNewUser() {

        binding.txtNovoUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nextActivityNewUser();
            }
        });
    }

    private void mensagemErro(){
        Toast.makeText(Authetication.this, "Falha na autenticação",
                Toast.LENGTH_SHORT).show();
    }
}



