package com.example.nubank.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
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
    //private GoogleSignInClient mGoogleSignInClient;
    private GoogleSignInClient mGoogleSignInClient;
    private static final int RC_SIGN_IN = 9001;
    private CallbackManager mCallbackManagerFace;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_authetication);

        binding.buttonFacebook.setReadPermissions("email", "public_profile");

        mCallbackManagerFace = CallbackManager.Factory.create();

        //auth.signOut();
        //signOutGoogle();
        registerCallbackFacebook();
        //clickListenerButtonGoogle();
        clickListenerButtonEmailPassword();


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();



        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        binding.buttonGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.buttonGoogle:
                        signIn();
                        break;
                    // ...
                    default:
                        throw new IllegalStateException("Unexpected value: " + view.getId());
                }
            }
        });
        /* ------------------------------------------------------------------*/



        /* -------------------- Login com e-mail e senha --------------------*/


        /* -------------------- Login --------------------*/
        /*auth.signInWithEmailAndPassword("maria@gmail.com", "123456")
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.i("signIn", "Sucesso ao logar usuário");
                        } else {
                            Log.i("signIn", "Erro ao logar usuário");
                        }
                    }
                });*/



        /* Logout */
        //auth.signOut();

        /* Verifica se usuário está logado*/
        /*if (auth.getCurrentUser() != null) {
            Log.i("CreateUser", "Usuário logado");
        } else {
            Log.i("CreateUser", "Usuário não logado");
        }*/

        /* -------------------- Cria um usuário --------------------*/
        /*
        auth.createUserWithEmailAndPassword("maria@gmail.com", "123456")
                .addOnCompleteListener(Authetication.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.i("CreateUser", "Sucesso ao cadastrar usuário");
                        } else {
                            Log.i("CreateUser", "Erro ao cadastrar usuário");
                        }
                    }
                });*/
        /* ------------------------------------------------------------------*/

    }

    private void clickListenerButtonGoogle() {

    }
    private void clickListenerButtonEmailPassword() {
        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = binding.editEmail.getText().toString();
                String senha = binding.editPassword.getText().toString();
                Toast.makeText(getApplicationContext(), email, Toast.LENGTH_SHORT).show();

                //auth.signOut();
                auth.signInWithEmailAndPassword(email, senha).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.i("signIn", "Sucesso ao logar usuário");
                            nextAcitivity();
                        } else {
                            Log.i("signIn", "Erro ao logar usuário");
                            Log.i("signIn", task.getResult().toString());
                            //Toast.makeText(getApplicationContext(), "", Toast.LENGTH_SHORT).show();

                        }
                    }
                });
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
            nextAcitivity();
            updateUIGoogle(account);
        } catch (ApiException e) {
            Log.w("Error message", "signInResult:failed code=" + e.getStatusCode());
            updateUIGoogle(null);
        }
    }


    private void updateUIGoogle(GoogleSignInAccount user) {
        AuthCredential credential = GoogleAuthProvider.getCredential(user.getIdToken(), null);
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("GoogleActivity", "signInWithCredential:success");
                            FirebaseUser user = auth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("GoogleActivity", "signInWithCredential:failure", task.getException());
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
            Log.i("CreateUser", "Usuário Logado");
            //mGoogleSignInClient.signOut();
            nextAcitivity();
        } else {
            Log.i("CreateUser", "Usuário não logado");
        }
        updateUI(currentUser);
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
                            nextAcitivity();
                            updateUI(user);
                        } else {

                            Log.w("FaceBookLogin", "signInWithCredential:failure", task.getException());
                            Toast.makeText(Authetication.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });
    }

    private void signOut() {
        auth.getInstance().signOut();
    }
    private void signOutFacebook() {
        //auth.getInstance().signOut();
        LoginManager.getInstance().logOut();
    }
    private void signOutGoogle() {
        mGoogleSignInClient.signOut();
    }

    private void updateUI(FirebaseUser user) {

    }

    public void nextAcitivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}



