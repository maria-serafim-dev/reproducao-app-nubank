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
import com.example.nubank.databinding.ActivityCreateUserBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class CreateUserActivity extends AppCompatActivity {

    private FirebaseAuth auth = FirebaseAuth.getInstance();

    ActivityCreateUserBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_create_user);

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
        if (TextUtils.isEmpty(binding.editEmail.getText())) {
            binding.editEmail.setError("Dígite um email");
            binding.editEmail.requestFocus();
            return false;
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(binding.editEmail.getText()).matches()) {
            binding.editEmail.setError("E-mail inválido");
            binding.editEmail.requestFocus();
            return false;
        }

        if (TextUtils.isEmpty(binding.editPassword.getText())) {
            binding.editPassword.setError("Dígite uma senha");
            binding.editPassword.requestFocus();
            return false;
        }

        return true;

    }

    public void nextActivity() {
        Intent intent = new Intent(this, Authetication.class);
        startActivity(intent);
    }
}