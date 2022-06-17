package com.example.nubank.ui.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.nubank.R;
import com.example.nubank.databinding.FragmentAllInvoiceBinding;
import com.example.nubank.viewModel.AccountViewModel;

import java.text.NumberFormat;


public class AllInvoiceFragment extends Fragment {

    private FragmentAllInvoiceBinding binding;
    private AccountViewModel viewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAllInvoiceBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(requireActivity()).get(AccountViewModel.class);
        inicializeValues();
        clickListenerChangeCreditLimit();

    }

    private void clickListenerChangeCreditLimit() {
        binding.tvLimite.setOnClickListener(view1 -> {
            NavDirections actions = CreditCardInformationFragmentDirections.actionCreditCardInformationFragmentToChangeCreditLimitFragment();
            Navigation.findNavController(view1).navigate(actions);
        });
    }

    private void inicializeValues() {
        viewModel.totalFaturaFechada.observe(getViewLifecycleOwner(), it -> binding.tvTotalFaturaAtual.setText(getString(R.string.text_valor_fatura_fechada, it)));
        viewModel.limiteDisponivel.observe(getViewLifecycleOwner(), it -> binding.tvLimite.setText(getString(R.string.text_valor_limite_teste, NumberFormat.getCurrencyInstance().format(it))));
        viewModel.faturaAtual.observe(getViewLifecycleOwner(), it -> binding.tvFaturaAtual.setText(getString(R.string.text_valor_fatura_atual, it)));
        viewModel.proximaFatura.observe(getViewLifecycleOwner(), it -> binding.tvProximaFatura.setText(getString(R.string.text_valor_proxima_fatura, it)));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}