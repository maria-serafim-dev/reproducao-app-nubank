package com.example.nubank.ui.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nubank.databinding.ItemPurchaseBinding;
import com.example.nubank.model.Purchase;

import java.text.NumberFormat;

public class PurcheseAdapter extends ListAdapter<Purchase, PurcheseAdapter.PurcheseViewHolder> {

    ItemPurchaseBinding binding;
    public PurcheseAdapter(){
        super(new PurcheseCallback());
    }

    @NonNull
    @Override
    public PurcheseAdapter.PurcheseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = ItemPurchaseBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new PurcheseViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull PurcheseAdapter.PurcheseViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    static class PurcheseViewHolder extends RecyclerView.ViewHolder {

        ItemPurchaseBinding binding;
        public PurcheseViewHolder(ItemPurchaseBinding itemView) {
            super(itemView.getRoot());
            this.binding = itemView;
        }

        public void bind(Purchase purchase){
            this.binding.tvNomeEmpresa.setText(purchase.getNomeEmpresa());
            this.binding.tvHorarioCompra.setText(purchase.getHorarioCompra());
            this.binding.tvValorCompra.setText(NumberFormat.getCurrencyInstance().format(purchase.getValorCompra()));
        }
    }
}

class PurcheseCallback extends DiffUtil.ItemCallback<Purchase>{

        @Override
        public boolean areItemsTheSame(Purchase oldItem, Purchase newItem) {
            return oldItem.getId() == newItem.getId();
        }
        @Override
        public boolean areContentsTheSame(Purchase oldItem, Purchase newItem) {
            return oldItem.getValorCompra() == newItem.getValorCompra();
        }

}