package com.chromepaymobile.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chromepaymobile.Models.MSRecycleModel;
import com.chromepaymobile.R;

import java.util.ArrayList;

public class MSRecycleAdapter extends RecyclerView.Adapter<MSRecycleAdapter.ViewHolder> {

    ArrayList<MSRecycleModel> msList;
    Context context;

    public MSRecycleAdapter(ArrayList<MSRecycleModel> msList, Context context) {
        this.msList = msList;
        this.context = context;
    }

    @NonNull
    @Override
    public MSRecycleAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.more_service_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MSRecycleAdapter.ViewHolder holder, int position) {

        MSRecycleModel msRecycleModel = msList.get(position);

        holder.textView.setText(msRecycleModel.getName());
        holder.imageView.setImageResource(msRecycleModel.getImg());
    }

    @Override
    public int getItemCount() {
        return msList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView textView;
        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.mic_ic);
            textView = itemView.findViewById(R.id.mic_service_tv);
        }
    }
}
