package com.chromepaymobile.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chromepaymobile.Models.CDBRecycleModel;
import com.chromepaymobile.ProfileActivity;
import com.chromepaymobile.R;

import java.util.ArrayList;

public class CDBRecycleAdapter extends RecyclerView.Adapter<CDBRecycleAdapter.ViewHolder> {

    ArrayList<CDBRecycleModel> cDBList;
    Context context;

    public CDBRecycleAdapter(ArrayList<CDBRecycleModel> cDBList, Context context) {
        this.cDBList = cDBList;
        this.context = context;
    }

    @NonNull
    @Override
    public CDBRecycleAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.customer_dashboard_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CDBRecycleAdapter.ViewHolder holder, int position) {

        CDBRecycleModel cdbRecycleModel = cDBList.get(position);

        holder.textView.setText(cdbRecycleModel.getName());
        holder.imageView.setImageResource(cdbRecycleModel.getImg());

        final int pos = holder.getAdapterPosition();
        if (pos == 0){
            holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context,ProfileActivity.class);
                    context.startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return cDBList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView textView;
        ImageView imageView;
        RelativeLayout relativeLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.profile_tv_cdb);
            imageView = itemView.findViewById(R.id.dash_ic);
            relativeLayout = itemView.findViewById(R.id.parent_l_cdb);
        }
    }
}
