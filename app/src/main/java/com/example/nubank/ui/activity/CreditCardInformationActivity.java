package com.example.nubank.ui.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.nubank.databinding.ActivityCreditCardInformationBinding;
import com.example.nubank.ui.adapter.TabViewPagerAdapter;
import com.example.nubank.ui.fragment.ChangeCreditLimitFragment;
import com.example.nubank.ui.fragment.InvoicePaymentFragment;
import com.example.nubank.ui.fragment.MyCardFragment;
import com.google.android.material.tabs.TabLayoutMediator;

public class CreditCardInformationActivity extends AppCompatActivity {

    private ActivityCreditCardInformationBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreditCardInformationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initializeInvoiceSlides();
        clickListenerButtonMyCards();
        clickListenerButtonInvoicePayment();
        clickListenerChangeCreditLimit();
    }

    private void initializeInvoiceSlides() {
        TabViewPagerAdapter adapter = new TabViewPagerAdapter(this);
        binding.viewPagerInformation.setAdapter(adapter);

        new TabLayoutMediator(binding.tabLayoutInformation, binding.viewPagerInformation, (tab, position) ->{}).attach();

    }

    private void clickListenerButtonMyCards() {
        binding.cardMyCards.setOnClickListener(view -> {
            MyCardFragment modalBottom = new MyCardFragment();
            modalBottom.show(getSupportFragmentManager(), "ModalBottomSheet");
        });

    }

    private void clickListenerButtonInvoicePayment() {
        binding.cardInvoicePayment.setOnClickListener(view->{
            InvoicePaymentFragment modalBottom = new InvoicePaymentFragment();
            modalBottom.show(getSupportFragmentManager(), "ModalBottomSheet");
        });
    }

    private void clickListenerChangeCreditLimit() {
        binding.cardChangeCreditLimit.setOnClickListener(view->{
            ChangeCreditLimitFragment modalBottom = new ChangeCreditLimitFragment();
            modalBottom.show(getSupportFragmentManager(), "ModalBottomSheet");
        });
    }
}