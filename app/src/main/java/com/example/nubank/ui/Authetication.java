package com.example.nubank.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.nubank.R;
import com.example.nubank.databinding.ActivityAutheticationBinding;
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
import com.google.android.gms.tasks.Task;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class Authetication extends AppCompatActivity {

    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private ActivityAutheticationBinding binding;
    private GoogleSignInClient mGoogleSignInClient;
    private static final int RC_SIGN_IN = 9001;
    private CallbackManager mCallbackManagerFace;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAutheticationBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        initializeFacebook();
        initializeGoogle();

        registerCallbackFacebook();
        clickListenerButtonGoogle();
        clickListenerButtonEmailPassword();

        clickListenerNewUser();
        clickListenerForgotPassword();
        clickListenerInputs();
    }

    private void clickListenerInputs() {
        binding.editEmail.setOnKeyListener((view, i, keyEvent) -> handleKeyEvent(view, i));
        binding.editPassword.setOnKeyListener((view, i, keyEvent) -> handleKeyEvent(view, i));

    }

    private boolean handleKeyEvent(View view, int keyCode) {
        if (keyCode == KeyEvent.KEYCODE_ENTER) {
            InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
            return true;
        }
        return false;
    }


    private boolean validarCampos() {
        binding.tfEmail.setError(null);
        binding.tfPassword.setError(null);

        boolean retorno = true;


        if (TextUtils.isEmpty(binding.editPassword.getText()) || binding.editPassword.getText() == null) {
            binding.tfPassword.setError("Dígite uma senha");
            binding.editPassword.requestFocus();
            retorno = false;
        }

        if (TextUtils.isEmpty(binding.editEmail.getText()) || binding.editEmail.getText() == null) {
            binding.tfEmail.setError("Dígite um email");
            binding.editEmail.requestFocus();
            retorno = false;
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(binding.editEmail.getText()).matches()) {
            binding.tfEmail.setError("E-mail inválido");
            binding.editEmail.requestFocus();
            retorno = false;
        }

        return retorno;

    }


    private void initializeFacebook() {
        binding.btFacebook.setReadPermissions("email", "public_profile");

        mCallbackManagerFace = CallbackManager.Factory.create();
    }

    private void initializeGoogle() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    private void clickListenerButtonGoogle() {
        binding.btGoogle.setOnClickListener(view -> signIn());
    }

    private void clickListenerButtonEmailPassword() {
        binding.btContinue.setOnClickListener(view -> {

            if (validarCampos()) {
                String email = binding.editEmail.getText().toString();
                String password = binding.editPassword.getText().toString();

                auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Log.i("signIn", "Sucesso ao logar usuário");
                        nextActivity(auth.getCurrentUser().getDisplayName());
                    } else {
                        messageErro("e-mail e senha");
                        Log.i("signIn", "Erro ao logar usuário", task.getException());
                    }
                });
            }
        });
    }

    private void registerCallbackFacebook() {

        binding.btFacebook.registerCallback(mCallbackManagerFace, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d("FaceBookLogin", "facebook:onSuccess:" + loginResult);
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
        } else {
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
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        Log.d("GoogleLogin", "signInWithCredential:success");
                        FirebaseUser user = auth.getCurrentUser();
                        nextActivity(user.getDisplayName());
                    } else {
                        messageErro("Google");
                        Log.w("GoogleLogin", "signInWithCredential:failure", task.getException());
                    }
                });
    }

    @Override
    public void onStart() {
        super.onStart();

        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        FirebaseUser currentUser = auth.getCurrentUser();

        if (currentUser != null || account != null) {
            Log.i("Message", "Usuário Logado");
            nextActivity(currentUser.getDisplayName());
        } else {
            Log.i("Message", "Usuário não logado");
        }
    }


    private void handleFacebookAccessToken(AccessToken token) {
        Log.d("FaceBookLogin", "handleFacebookAccessToken:" + token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());

        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        Log.d("FaceBookLogin", "signInWithCredential:success");
                        FirebaseUser user = auth.getCurrentUser();
                        nextActivity(user.getDisplayName());
                    } else {
                        Log.w("FaceBookLogin", "signInWithCredential:failure", task.getException());
                        messageErro("Facebook");
                    }
                });
    }

    public void nextActivity(String nome) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("nome", nome);
        startActivity(intent);
        finish();
    }

    public void nextActivityNewUser() {
        Intent intent = new Intent(this, CreateUserActivity.class);
        startActivity(intent);
    }

    private void clickListenerNewUser() {
        binding.tvNewUser.setOnClickListener(view -> nextActivityNewUser());
    }

    private void messageErro(String provedor) {
        Toast.makeText(Authetication.this, "Falha na autenticação com " + provedor,
                Toast.LENGTH_LONG).show();
    }

    private void clickListenerForgotPassword() {

        binding.tvEsqueciSenha.setOnClickListener(view -> {
            View customAlertDialogView = LayoutInflater.from(this)
                    .inflate(R.layout.custom_dialog, null, false);
            MaterialAlertDialogBuilder materialAlertDialogBuilder = new MaterialAlertDialogBuilder(Authetication.this);
            materialAlertDialogBuilder.setView(customAlertDialogView)
                    .setTitle("Recuperar senha")
                    .setMessage("Insira o e-mail para recuperar a senha")
                    .setPositiveButton("Enviar", (dialogInterface, i) -> {
                        TextInputLayout inputEmail = customAlertDialogView.findViewById(R.id.tf_email_reset_password);
                        String email = inputEmail.getEditText().getText().toString();
                        sendPasswordResetEmail(email);
                    })
                    .setNegativeButton("Cancelar", (dialogInterface, i) -> dialogInterface.cancel())
                    .show();
        });

    }

    private void sendPasswordResetEmail(String email) {
        FirebaseAuth auth = FirebaseAuth.getInstance();

        auth.sendPasswordResetEmail(email)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Snackbar.make(binding.tvEsqueciSenha, R.string.txt_send_email, Snackbar.LENGTH_SHORT).show();
                    } else {
                        Snackbar.make(binding.tvEsqueciSenha, R.string.txt_not_send_email, Snackbar.LENGTH_SHORT).show();
                    }
                });
    }

}



