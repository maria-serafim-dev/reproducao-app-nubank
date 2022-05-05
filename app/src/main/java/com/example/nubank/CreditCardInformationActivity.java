package com.example.nubank;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.nubank.databinding.ActivityCreditCardInformationBinding;
import com.google.android.material.tabs.TabLayoutMediator;

public class CreditCardInformationActivity extends AppCompatActivity {

    private ActivityCreditCardInformationBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreditCardInformationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initializeInvoiceSlides();
    }

    private void initializeInvoiceSlides() {
        TabViewPagerAdapter adapter = new TabViewPagerAdapter(this);
        binding.viewPagerInformation.setAdapter(adapter);

        new TabLayoutMediator(binding.tabLayoutInformation, binding.viewPagerInformation, (tab, position) ->{}).attach();

    }
}