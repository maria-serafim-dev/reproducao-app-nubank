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
        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = binding.editEmail.getText().toString();
                String password = binding.editPassword.getText().toString();

                auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(CreateUserActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.i("signIn", "Sucesso ao logar usuário");
                            Toast.makeText(getApplicationContext(),"Usuário Criado com sucesso",Toast.LENGTH_LONG).show();
                            nextActivity();
                        } else {
                            Log.i("signIn", "Erro ao logar usuário");
                            Log.i("signIn", task.getResult().toString());
                            Toast.makeText(getApplicationContext(),"Usuário Criado com sucesso",Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });
    }

    public void nextActivity(){
        Intent intent = new Intent(this, Authetication.class);
        startActivity(intent);
    }
}