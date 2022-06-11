package com.example.nubank.ui.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.example.nubank.databinding.FragmentChangeCreditLimitBinding;
import com.example.nubank.viewModel.AccountViewModel;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.text.NumberFormat;
import java.util.ArrayList;


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
        BottomSheetBehavior<View> bottomSheetBehavior = BottomSheetBehavior.from((View) (view.getParent()));
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        viewModel = new ViewModelProvider(requireActivity()).get(AccountViewModel.class);
        binding.editValor.requestFocus();

        inicializeValues();
    }

    private void inicializeValues() {
        ArrayList<Float> range = new ArrayList<>();
        range.add(0.0F);
        range.add(0.0F);
        viewModel.limiteDisponivel.observe(getViewLifecycleOwner(), it -> {
            binding.tvLimiteDisponivelUso.setText(NumberFormat.getCurrencyInstance().format(it));
            range.remove(0);
            range.add(0, it);
            binding.rangerSlider.setValues(range);
        });

        viewModel.limiteAtual.observe(getViewLifecycleOwner(), it -> {
            binding.editValor.setText(NumberFormat.getCurrencyInstance().format(it));
            range.remove(1);
            range.add(1, it);
            binding.rangerSlider.setValues(range);

        });

        viewModel.limiteTotal.observe(getViewLifecycleOwner(), it -> binding.rangerSlider.setValueTo(it));
        clickListenerButtonClose();
    }

    private void clickListenerButtonClose() {
        binding.imgClose.setOnClickListener(view -> dismiss());
    }

}