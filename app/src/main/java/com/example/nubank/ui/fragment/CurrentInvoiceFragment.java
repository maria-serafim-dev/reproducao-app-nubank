package com.example.nubank.ui.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.nubank.R;
import com.example.nubank.databinding.FragmentCurrentInvoiceBinding;
import com.example.nubank.viewModel.AccountViewModel;


public class CurrentInvoiceFragment extends Fragment {

    private FragmentCurrentInvoiceBinding binding;
    private AccountViewModel viewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCurrentInvoiceBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(AccountViewModel.class);
        inicializeValues();
    }

    private void inicializeValues() {
        viewModel.totalFatura.observe(getViewLifecycleOwner(), it -> binding.tvFaturaAtual.setText(getString(R.string.text_valor_limite, it)));

        viewModel.limite.observe(getViewLifecycleOwner(), it -> binding.tvCurrentLimite.setText(getString(R.string.text_limite_disponivel, it)));

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}