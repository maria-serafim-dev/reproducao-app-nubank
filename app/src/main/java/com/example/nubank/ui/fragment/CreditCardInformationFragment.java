package com.example.nubank.ui.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.example.nubank.databinding.FragmentCreditCardInformationBinding;
import com.example.nubank.ui.adapter.TabViewPagerAdapter;
import com.google.android.material.tabs.TabLayoutMediator;

public class CreditCardInformationFragment extends Fragment {

    private FragmentCreditCardInformationBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentCreditCardInformationBinding.inflate(getLayoutInflater(), container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

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
            NavDirections action = CreditCardInformationFragmentDirections.actionCreditCardInformationFragmentToMyCardFragment();
            Navigation.findNavController(view).navigate(action);
        });

    }

    private void clickListenerButtonInvoicePayment() {
        binding.cardInvoicePayment.setOnClickListener(view->{
            NavDirections action = CreditCardInformationFragmentDirections.actionCreditCardInformationFragmentToInvoicePaymentFragment();
            Navigation.findNavController(view).navigate(action);
        });
    }

    private void clickListenerChangeCreditLimit() {
        binding.cardChangeCreditLimit.setOnClickListener(view->{
            NavDirections action = CreditCardInformationFragmentDirections.actionCreditCardInformationFragmentToChangeCreditLimitFragment();
            Navigation.findNavController(view).navigate(action);
        });
    }
}