package com.example.nubank;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.nubank.databinding.FragmentAllInvoiceBinding;


public class AllInvoiceFragment extends Fragment {

    private FragmentAllInvoiceBinding binding;


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
}