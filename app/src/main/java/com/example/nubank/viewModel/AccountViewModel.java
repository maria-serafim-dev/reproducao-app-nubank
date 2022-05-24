package com.example.nubank.viewModel;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import java.text.NumberFormat;

public class AccountViewModel extends ViewModel {

    private final MutableLiveData<Double> _balance = new MutableLiveData<>(1715.00);

    public LiveData<String> balance = Transformations.map(_balance, bl ->
            NumberFormat.getCurrencyInstance().format(bl));


    private final MutableLiveData<Double> _totalFatura = new MutableLiveData<>(1685.00);

    public LiveData<String> totalFatura = Transformations.map(_totalFatura, bl ->
            NumberFormat.getCurrencyInstance().format(bl));

    private final MutableLiveData<Double> _total = new MutableLiveData<>(1715.00);

    public LiveData<String> total = Transformations.map(_total, bl ->
            NumberFormat.getCurrencyInstance().format(bl));

    private final MutableLiveData<Double> _limite = new MutableLiveData<>(85.00);

    public LiveData<String> limite = Transformations.map(_limite, bl ->
            NumberFormat.getCurrencyInstance().format(bl));

    private final MutableLiveData<Double> _faturaAtual = new MutableLiveData<>(652.54);

    public LiveData<String> faturaAtual = Transformations.map(_faturaAtual, bl ->
            NumberFormat.getCurrencyInstance().format(bl));

    private final MutableLiveData<Double> _proximaFatura = new MutableLiveData<>(54.00);

    public LiveData<String> proximaFatura = Transformations.map(_proximaFatura, bl ->
            NumberFormat.getCurrencyInstance().format(bl));

}
