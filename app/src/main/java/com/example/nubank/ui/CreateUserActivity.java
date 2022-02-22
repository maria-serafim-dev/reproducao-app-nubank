package com.example.nubank.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.example.nubank.R;
import com.example.nubank.databinding.ActivityCreateUserBinding;

public class CreateUserActivity extends AppCompatActivity {

    ActivityCreateUserBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_create_user);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_create_user);

    }
}