package com.devtaghreed.contentprovider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.devtaghreed.contentprovider.databinding.ItemBinding;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.App_ViewHolder> {
    ArrayList<Contacts> itemArrayList;
    onClickListener onClickListener;

    public Adapter(ArrayList<Contacts> itemArrayList, onClickListener onClickListener) {
        this.itemArrayList = itemArrayList;
        this.onClickListener = onClickListener;
    }

    @NonNull
    @Override
    public App_ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemBinding binding = ItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new App_ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull App_ViewHolder holder, int position) {
        if (itemArrayList == null)
            return;
        Contacts contacts = itemArrayList.get(holder.getAdapterPosition());

        holder.name.setText(contacts.getName());
        holder.number.setText(contacts.getNumber());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onClickListener.onClick(holder.getAdapterPosition());

            }
        });
    }

    @Override
    public int getItemCount() {
        return itemArrayList != null ? itemArrayList.size() : 0;
    }

    class App_ViewHolder extends RecyclerView.ViewHolder {
        TextView name, number;

        public App_ViewHolder(@NonNull ItemBinding binding) {
            super(binding.getRoot());
            name = binding.tvName;
            number = binding.tvNumber;
        }
    }
}
