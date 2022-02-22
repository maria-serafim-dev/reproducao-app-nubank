package com.example.nubank.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nubank.R;
import com.example.nubank.databinding.ActivityAutheticationBindingImpl;
import com.example.nubank.databinding.ActivityAutheticationBinding;
import com.example.nubank.databinding.ActivityAutheticationTwoBinding;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AutheticationTwo extends AppCompatActivity {

    private FirebaseAuth auth = FirebaseAuth.getInstance();
    //ActivityAutheticationTwoBinding binding;
    ActivityAutheticationTwoBinding binding;
    //private GoogleSignInClient mGoogleSignInClient;
    private GoogleSignInClient mGoogleSignInClient;
    private static final int RC_SIGN_IN = 9001;
    private CallbackManager mCallbackManagerFace;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_authetication);
        auth.signOut();

        // binding = ActivityAutheticationBinding.inflate(getLayoutInflater());
        // setContentView(binding.getRoot());

        binding = DataBindingUtil.setContentView(this, R.layout.activity_authetication_two);

        binding.buttonFacebookTwo.setReadPermissions("email", "public_profile");

        mCallbackManagerFace = CallbackManager.Factory.create();




        binding.buttonFacebookTwo.registerCallback(mCallbackManagerFace, new FacebookCallback<LoginResult>() {
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

        /* -------------------- Ouvinte do Botão de Login com e-mail e senha --------------------*/
        binding.buttonTwo.setOnClickListener(new View.OnClickListener() {
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
                            irProximaActivity();
                        } else {
                            Log.i("signIn", "Erro ao logar usuário");
                            Log.i("signIn", task.getResult().toString());
                            //Toast.makeText(getApplicationContext(), "", Toast.LENGTH_SHORT).show();

                        }
                    }
                });
            }
        });

        /* ------------------------------------------------------------------*/

        /* -------------------- Login Google --------------------*/
        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        binding.buttonGoogleTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.buttonGoogleTwo:
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


    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            //String teste_name = account.getDisplayName();
            //Signed in successfully, show authenticated UI.
            //Log.i("Mensagem", teste_name);
            updateUI(account);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("Mensagem de erro", "signInResult:failed code=" + e.getStatusCode());
            updateUI(null);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        //Verificação se o usuário está logado com Google;
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        FirebaseUser currentUser = auth.getCurrentUser();

        if (account == null || currentUser != null) {
            Log.i("CreateUser", "Não Logado");
        } else {
            String teste_name = account.getEmail();
            // Signed in successfully, show authenticated UI.
            Log.i("Mensagem", teste_name);
            Log.i("CreateUser", "Logadaço " + teste_name);
            mGoogleSignInClient.signOut();
        }

        updateUI(account);
    }
    private void updateUI(GoogleSignInAccount user) {

    }
    /* -------------------- Finaliza Login Google --------------------*/


    /*@Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        FirebaseUser currentUser = auth.getCurrentUser();
        if (currentUser != null || account != null) {
            Log.i("CreateUser", "Usuário logado");
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        } else {
            Log.i("CreateUser", "Usuário não logado");
        }
        updateUI(currentUser);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Pass the activity result back to the Facebook SDK
        mCallbackManagerFace.onActivityResult(requestCode, resultCode, data);
    }
*/


    private void signOut() {
        FirebaseAuth.getInstance().signOut();
    }
    private void handleFacebookAccessToken(AccessToken token) {
        Log.d("FaceBookLogin", "handleFacebookAccessToken:" + token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("FaceBookLogin", "signInWithCredential:success");
                            FirebaseUser user = auth.getCurrentUser();
                            //updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("FaceBookLogin", "signInWithCredential:failure", task.getException());
                            Toast.makeText(AutheticationTwo.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }
                    }
                });
    }

    /*private void updateUI(FirebaseUser user) {

    }*/

    public void irProximaActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}