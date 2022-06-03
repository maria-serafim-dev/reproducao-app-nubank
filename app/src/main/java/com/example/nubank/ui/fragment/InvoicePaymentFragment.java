package com.example.nubank.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.example.nubank.databinding.FragmentInvoicePaymentBinding;
import com.example.nubank.viewModel.AccountViewModel;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
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
        BottomSheetBehavior<View> bottomSheetBehavior = BottomSheetBehavior.from((View) (view.getParent()));
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        AccountViewModel viewModel = new ViewModelProvider(requireActivity()).get(AccountViewModel.class);
        binding.imgClose.setOnClickListener(view1 -> dismiss());
        binding.editValor.requestFocus();

        viewModel.faturaAtual.observe(getViewLifecycleOwner(), it-> binding.editValor.setText(it));

    }
}