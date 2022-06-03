package com.example.nubank.viewModel;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.example.nubank.model.Purchase;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class AccountViewModel extends ViewModel {

    private final MutableLiveData<Double> _balance = new MutableLiveData<>(1715.00);

    public LiveData<String> balance = Transformations.map(_balance, bl ->
            NumberFormat.getCurrencyInstance().format(bl));

    private final MutableLiveData<Double> _totalFaturaFechada = new MutableLiveData<>(1685.00);

    public LiveData<String> totalFaturaFechada = Transformations.map(_totalFaturaFechada, bl ->
            NumberFormat.getCurrencyInstance().format(bl));

    private final MutableLiveData<Double> _total = new MutableLiveData<>(1715.00);

    public LiveData<String> total = Transformations.map(_total, bl ->
            NumberFormat.getCurrencyInstance().format(bl));

    private final MutableLiveData<Float> _limiteDisponivel = new MutableLiveData<>(5000.00F);

    public LiveData<Float> limiteDisponivel = _limiteDisponivel;

    private final MutableLiveData<Integer> _limiteTotal = new MutableLiveData<>(8750);

    public LiveData<Integer> limiteTotal = _limiteTotal;

    private final MutableLiveData<Float> _limiteAtual = new MutableLiveData<>(8500.00F);

    public LiveData<Float> limiteAtual = _limiteAtual;

    private final MutableLiveData<Double> _faturaAtual = new MutableLiveData<>(652.54);

    public LiveData<String> faturaAtual = Transformations.map(_faturaAtual, bl ->
            NumberFormat.getCurrencyInstance().format(bl));

    private final MutableLiveData<Double> _proximaFatura = new MutableLiveData<>(54.00);

    public LiveData<String> proximaFatura = Transformations.map(_proximaFatura, bl ->
            NumberFormat.getCurrencyInstance().format(bl));

    private final MutableLiveData<List<Purchase>> _listaCompras = new MutableLiveData<>(criarLista());
    public LiveData<List<Purchase>>  listaCompras = _listaCompras;


    public List<Purchase> criarLista(){
        Purchase p1 = new Purchase("Apple", "25/03/2022", "15:32", 7534656, 52.65, 3, 2);
        Purchase p2 = new Purchase("Amazon", "21/02/2022", "15:32", 7534656, 100.00, 5, 1);
        ArrayList<Purchase> lista = new ArrayList<>();
        lista.add(p1);
        lista.add(p2);
        return lista;
    }
}
