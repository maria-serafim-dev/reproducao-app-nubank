package com.example.nubank.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.example.nubank.databinding.FragmentSummaryOfInvoicesBinding;
import com.example.nubank.ui.adapter.InvoiceAdapter;
import com.example.nubank.viewModel.AccountViewModel;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class SummaryOfInvoicesFragment extends BottomSheetDialogFragment {

    private FragmentSummaryOfInvoicesBinding binding;
    private final InvoiceAdapter adapter = new InvoiceAdapter();
    private AccountViewModel viewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentSummaryOfInvoicesBinding.inflate(getLayoutInflater(), container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        BottomSheetBehavior<View> bottomSheetBehavior = BottomSheetBehavior.from((View) (view.getParent()));
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        viewModel = new ViewModelProvider(requireActivity()).get(AccountViewModel.class);

        iniciarObserve();
        setAdapter();
        clickListenerButtonClose();
    }

    private void iniciarObserve() {
        viewModel.listaInvoice.observe(getViewLifecycleOwner(), lista -> adapter.submitList(lista));
    }

    private void setAdapter() {
        binding.recyclerSummaryOfInvoices.setAdapter(adapter);
    }

    private void clickListenerButtonClose() {
        binding.imgClose.setOnClickListener(view -> dismiss());
    }

}
