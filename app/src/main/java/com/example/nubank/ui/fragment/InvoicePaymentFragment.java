package com.example.nubank.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.nubank.databinding.FragmentInvoicePaymentBinding;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;


public class InvoicePaymentFragment extends BottomSheetDialogFragment {

    private FragmentInvoicePaymentBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentInvoicePaymentBinding.inflate(getLayoutInflater(), container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.imgClose.setOnClickListener(view1 -> dismiss());
    }
}