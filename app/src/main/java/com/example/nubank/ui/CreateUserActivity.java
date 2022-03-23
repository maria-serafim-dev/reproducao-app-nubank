package com.example.nubank.ui;


import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.nubank.databinding.ActivityCreateUserBinding;
import com.google.firebase.auth.FirebaseAuth;

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
    }

    private void clickListenerButtonEmailPassword() {
        binding.button.setOnClickListener(view -> {
            if (validarCampos()) {
                String email = binding.editEmail.getText().toString();
                String password = binding.editPassword.getText().toString();

                auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(CreateUserActivity.this, task -> {
                            if (task.isSuccessful()) {
                                Log.i("signIn", "Sucesso ao logar usuário");
                                Toast.makeText(getApplicationContext(), "Usuário Criado com sucesso", Toast.LENGTH_LONG).show();
                                nextActivity();
                            } else {
                                Log.i("signIn", "Erro ao logar usuário");
                                Log.i("signIn", task.getResult().toString());
                                Toast.makeText(getApplicationContext(), "Usuário Criado com sucesso", Toast.LENGTH_LONG).show();
                            }
                        });
            }
        });
    }

    private boolean validarCampos() {
        binding.textEmail.setError(null);
        binding.textPassword.setError(null);

        Boolean retorno = true;


        if (TextUtils.isEmpty(binding.editPassword.getText())) {
            binding.textPassword.setError("Dígite uma senha");
            binding.editPassword.requestFocus();
            retorno = false;
        }

        if (TextUtils.isEmpty(binding.editEmail.getText())) {
            binding.textEmail.setError("Dígite um email");
            binding.editEmail.requestFocus();
            retorno = false;
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(binding.editEmail.getText()).matches()) {
            binding.textEmail.setError("E-mail inválido");
            binding.editEmail.requestFocus();
            retorno = false;
        }

        return retorno;

    }

    public void nextActivity() {
        Intent intent = new Intent(this, Authetication.class);
        startActivity(intent);
    }
}