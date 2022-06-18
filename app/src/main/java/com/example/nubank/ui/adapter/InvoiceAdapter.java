package com.example.nubank.ui.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nubank.databinding.ItemInvoiceBinding;
import com.example.nubank.model.Invoice;

public class InvoiceAdapter extends ListAdapter<Invoice, InvoiceAdapter.ViewHolder> {

    public InvoiceAdapter() {
        super(new InvoiceCallBack());
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemInvoiceBinding binding = ItemInvoiceBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.onBinding(getItem(position));
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemInvoiceBinding binding;

        public ViewHolder(ItemInvoiceBinding binding){
            super(binding.getRoot());
            this.binding = binding;
        }

        public void onBinding(Invoice item) {
            binding.tvMonth.setText(item.getMonth() + " " + item.getYear());
            binding.tvAmount.setText(String.valueOf(item.getAmount()));
        }
    }

}

class InvoiceCallBack extends DiffUtil.ItemCallback<Invoice>{

    @Override
    public boolean areItemsTheSame(@NonNull Invoice oldItem, @NonNull Invoice newItem) {
        return oldItem.getMonth().equals(newItem.getMonth()) && oldItem.getYear() == newItem.getYear();
    }

    @Override
    public boolean areContentsTheSame(@NonNull Invoice oldItem, @NonNull Invoice newItem) {
        return oldItem.equals(newItem);
    }
}
