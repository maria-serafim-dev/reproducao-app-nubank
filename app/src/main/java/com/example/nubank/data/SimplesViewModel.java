package com.example.nubank.data;


import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SimplesViewModel extends ViewModel {
    private FirebaseAuth auth = FirebaseAuth.getInstance();

    public void auth(){
       Log.i("Mensagem", "Estou aqui");
       /* auth.signInWithEmailAndPassword(email, senha)
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
    }

}
