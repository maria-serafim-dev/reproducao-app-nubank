package com.example.nubank.ui.fragment;


import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.example.nubank.R;
import com.example.nubank.databinding.FragmentChangeCreditLimitBinding;
import com.example.nubank.viewModel.AccountViewModel;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;


public class ChangeCreditLimitFragment extends BottomSheetDialogFragment {

    private FragmentChangeCreditLimitBinding binding;
    private AccountViewModel viewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentChangeCreditLimitBinding.inflate(getLayoutInflater(), container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from((View) (view.getParent()));
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        viewModel = new ViewModelProvider(requireActivity()).get(AccountViewModel.class);
        binding.editValor.requestFocus();
        binding.editValor.setText(viewModel.limite.getValue());
        binding.tvLimiteDisponivelUso.setText(getString(R.string.text_limite_disponivel_uso, viewModel.limite.getValue()));
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        return (BottomSheetDialog) super.onCreateDialog(savedInstanceState);
    }
}