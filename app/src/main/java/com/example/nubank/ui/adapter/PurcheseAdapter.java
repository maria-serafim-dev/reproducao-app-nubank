package com.example.nubank.ui.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nubank.databinding.ItemPurchaseBinding;
import com.example.nubank.model.Purchase;

import java.text.NumberFormat;
import java.util.ArrayList;

public class PurcheseAdapter extends RecyclerView.Adapter<PurcheseAdapter.PurcheseViewHolder> {

    Purchase p1 = new Purchase("Apple", "25/03/2022", "15:32", 7534656, 52.65, 3, 2);
    Purchase p2 = new Purchase("Amazon", "21/02/2022", "15:32", 7534656, 100.00, 5, 1);
    ArrayList<Purchase> lista = new ArrayList<>();

    public PurcheseAdapter(){
        lista.add(p1);
        lista.add(p2);
    }

    @NonNull
    @Override
    public PurcheseAdapter.PurcheseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemPurchaseBinding binding = ItemPurchaseBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new PurcheseViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull PurcheseAdapter.PurcheseViewHolder holder, int position) {
        holder.bind(lista.get(position));
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    static class PurcheseViewHolder extends RecyclerView.ViewHolder {

        ItemPurchaseBinding binding;
        public PurcheseViewHolder(ItemPurchaseBinding itemView) {
            super(itemView.getRoot());
            this.binding = itemView;
        }

        public void bind(Purchase purchase){
            binding.tvNomeEmpresa.setText(purchase.getNomeEmpresa());
            binding.tvHorarioCompra.setText(purchase.getHorarioCompra());
            binding.tvValorCompra.setText(NumberFormat.getCurrencyInstance().format(purchase.getValorCompra()));
        }
    }
}
