package com.example.nubank.ui.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.nubank.databinding.ActivityCreditCardInformationBinding;
import com.example.nubank.ui.adapter.TabViewPagerAdapter;
import com.google.android.material.tabs.TabLayoutMediator;

public class CreditCardInformationActivity extends AppCompatActivity {

    private ActivityCreditCardInformationBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreditCardInformationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initializeInvoiceSlides();
        clickListenerButtonCards();

    }

    private void initializeInvoiceSlides() {
        TabViewPagerAdapter adapter = new TabViewPagerAdapter(this);
        binding.viewPagerInformation.setAdapter(adapter);

        new TabLayoutMediator(binding.tabLayoutInformation, binding.viewPagerInformation, (tab, position) ->{}).attach();

    }


    private void clickListenerButtonCards() {
        binding.cardMyCards.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), MyCardActivity.class);
            startActivity(intent);
        });

    }
}