package com.example.nubank.ui.activity;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.nubank.databinding.ActivityCreateUserBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class CreateUserActivity extends AppCompatActivity {

    private FirebaseAuth auth = FirebaseAuth.getInstance();

    private ActivityCreateUserBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreateUserBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        clickListenerButtonEmailPassword();
        clickListenerButtonCancel();
        clickListenerInputs();

    }

    private void clickListenerInputs() {
        binding.editName.setOnKeyListener((view, i, keyEvent) -> handleKeyEvent(view, i));
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

    private void clickListenerButtonEmailPassword() {
        binding.btContinue.setOnClickListener(view -> {
            if (validarCampos()) {
                String nome = binding.editName.getText().toString();
                String email = binding.editEmail.getText().toString();
                String password = binding.editPassword.getText().toString();

                auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(CreateUserActivity.this, task -> {
                            if (task.isSuccessful()) {
                                Log.i("createUser", "Sucesso ao logar usu??rio");
                                Toast.makeText(getApplicationContext(), "Usu??rio Criado com sucesso", Toast.LENGTH_LONG).show();
                                FirebaseUser userId = auth.getCurrentUser();
                                atualizarUsuario(userId, nome);
                            } else {
                                Log.i("createUser", "Erro ao logar usu??rio");
                                Log.i("createUser", "Resultado", task.getException());
                                if(task.getException() instanceof FirebaseAuthUserCollisionException){
                                    binding.tfEmail.setError("J?? existe um usu??rio com esse e-mail");
                                    binding.editEmail.requestFocus();
                                }
                            }
                        });
            }
        });
    }


    private void clickListenerButtonCancel() {
        binding.btCancel.setOnClickListener(view -> backActivity());
    }

    private void atualizarUsuario(FirebaseUser userId, String nome) {
        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(nome)
                .build();

        userId.updateProfile(profileUpdates)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        nextActivity();
                    }
                });
    }

    private boolean validarCampos() {
        binding.tfEmail.setError(null);
        binding.tfPassword.setError(null);
        binding.tfName.setError(null);

        boolean retorno = true;

        if (TextUtils.isEmpty(binding.editPassword.getText()) || binding.editPassword.getText() == null) {
            binding.tfPassword.setError("D??gite uma senha");
            binding.editPassword.requestFocus();
            retorno = false;
        }

        if (TextUtils.isEmpty(binding.editEmail.getText()) || binding.editEmail.getText() == null) {
            binding.tfEmail.setError("D??gite um email");
            binding.editEmail.requestFocus();
            retorno = false;
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(binding.editEmail.getText()).matches()) {
            binding.tfEmail.setError("E-mail inv??lido");
            binding.editEmail.requestFocus();
            retorno = false;
        }

        if (TextUtils.isEmpty(binding.editName.getText()) || binding.editName.getText() == null) {
            binding.tfName.setError("D??gite um nome");
            binding.editName.requestFocus();
            retorno = false;
        }

        return retorno;

    }

    public void nextActivity() {
        Intent intent = new Intent(this, Authetication.class);
        startActivity(intent);
        finish();
    }

    public void backActivity() {
        finish();
    }
}