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

public class PurchaseAdapter extends ListAdapter<Purchase, PurchaseAdapter.PurchaseViewHolder> {

    ItemPurchaseBinding binding;
    private OnItemClickListener onItemClickListener;

    public PurchaseAdapter(){
        super(new PurchaseCallback());
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public PurchaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = ItemPurchaseBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new PurchaseViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull PurchaseViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    class PurchaseViewHolder extends RecyclerView.ViewHolder {

        ItemPurchaseBinding binding;
        public PurchaseViewHolder(ItemPurchaseBinding itemView) {
            super(itemView.getRoot());
            this.binding = itemView;
        }

        public void bind(Purchase purchase){
            this.binding.tvNomeEmpresa.setText(purchase.getNomeEmpresa());
            this.binding.tvHorarioCompra.setText(purchase.getHorarioCompra());
            this.binding.tvValorCompra.setText(NumberFormat.getCurrencyInstance().format(purchase.getValorCompra()));

            this.binding.layoutItemPurchase.setOnClickListener(view -> onItemClickListener.onItemClick(view, purchase));
        }
    }
}

class PurchaseCallback extends DiffUtil.ItemCallback<Purchase>{

        @Override
        public boolean areItemsTheSame(Purchase oldItem, Purchase newItem) {
            return oldItem.getId() == newItem.getId();
        }
        @Override
        public boolean areContentsTheSame(Purchase oldItem, @NonNull Purchase newItem) {
            return oldItem.equals(newItem);
        }

}