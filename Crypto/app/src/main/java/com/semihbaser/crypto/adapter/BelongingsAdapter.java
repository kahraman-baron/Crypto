package com.semihbaser.crypto.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.semihbaser.crypto.databinding.RecyclerRowBinding;
import com.semihbaser.crypto.model.Belongings;
import com.semihbaser.crypto.view.UploadActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class BelongingsAdapter extends RecyclerView.Adapter<BelongingsAdapter.BelongingsHolder> {

    private ArrayList<Belongings> belongingsArrayList;


    public BelongingsAdapter(ArrayList<Belongings> belongingsArrayList) {
        this.belongingsArrayList = belongingsArrayList;

    }

    @NonNull

    @Override
    public BelongingsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerRowBinding recyclerRowBinding = RecyclerRowBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new BelongingsHolder(recyclerRowBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull BelongingsAdapter.BelongingsHolder holder, int position) {
        holder.binding.recyclerViewNameText.setText(belongingsArrayList.get(position).name);
        holder.binding.recyclerViewEmailText.setText("Uploaded By: " + belongingsArrayList.get(position).email);
        holder.binding.recyclerViewPriceText.setText(belongingsArrayList.get(position).price);
        Picasso.get().load(belongingsArrayList.get(position).downloadUrl).into(holder.binding.imageView);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.itemView.getContext(), UploadActivity.class);
                intent.putExtra("info", "old");
                holder.itemView.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return belongingsArrayList.size();
    }

    public class BelongingsHolder extends RecyclerView.ViewHolder {


        private RecyclerRowBinding binding;

        public BelongingsHolder(RecyclerRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }
    }
}
