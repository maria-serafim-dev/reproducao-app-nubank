package com.example.nubank.ui.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.nubank.databinding.ActivityMyCardBinding;

public class MyCardActivity extends AppCompatActivity {

    private ActivityMyCardBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMyCardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        clickListenerButtonClose();
    }

    private void clickListenerButtonClose() {
        binding.imgClose.setOnClickListener(view -> finish());

    }

}