package com.example.nubank.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.nubank.R;
import com.example.nubank.databinding.FragmentPurchaseInformationBinding;
import com.example.nubank.model.Purchase;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.text.NumberFormat;

public class PurchaseInformationFragment extends BottomSheetDialogFragment {

    private FragmentPurchaseInformationBinding binding;
    Purchase purchase;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentPurchaseInformationBinding.inflate(getLayoutInflater(), container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        BottomSheetBehavior<View> bottomSheetBehavior = BottomSheetBehavior.from((View) (view.getParent()));
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        purchase = PurchaseInformationFragmentArgs.fromBundle(getArguments()).getPurchase();
        clickListenerButtonClose();
        inicializarValores();
    }

    private void inicializarValores() {
        binding.tvData.setText(purchase.getDataCompra() + "," + purchase.getHorarioCompra());
        binding.tvNomeEmpresa.setText(purchase.getNomeEmpresa());
        binding.tvCartao.setText(getString(R.string.text_cartao_compra, purchase.getNumeroCartao()));
        binding.tvValorCompra.setText(formatarMoeda(purchase.getValorCompra()));
        binding.tvParcelas.setText(getString(R.string.text_parcelas_compras, purchase.getQtdeParcelas(), formatarMoeda(purchase.getValorParcela())));
    }

    private void clickListenerButtonClose() {
        binding.imgClose.setOnClickListener(view -> dismiss());
    }

    private String formatarMoeda(double valor) {
        return NumberFormat.getCurrencyInstance().format(valor);
    }
}
