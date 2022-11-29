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

import com.chromepaymobile.AgentCommissionActivity;
import com.chromepaymobile.AgentPerformenceActivity;
import com.chromepaymobile.AllDidActivity;
import com.chromepaymobile.AwatingDidActivity;
import com.chromepaymobile.LoanApplyCustomerActivity;
import com.chromepaymobile.LoanPassActivity;
import com.chromepaymobile.UnBlockedDidActivity;
import com.chromepaymobile.Models.ADBRecycleModel;
import com.chromepaymobile.R;
import com.chromepaymobile.SettingsActivity;

import java.util.ArrayList;

public class ADBRecycleAdapter extends RecyclerView.Adapter<ADBRecycleAdapter.ViewHolder> {

    ArrayList<ADBRecycleModel> adbList;
    Context context;

    public ADBRecycleAdapter(ArrayList<ADBRecycleModel> adbList, Context context) {
        this.adbList = adbList;
        this.context = context;
    }

    @NonNull
    @Override
    public ADBRecycleAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.agent_dashboard_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ADBRecycleAdapter.ViewHolder holder, int position) {

        ADBRecycleModel adbRecycleModel = adbList.get(position);

        holder.textView.setText(adbRecycleModel.getName());
        holder.imageView.setImageResource(adbRecycleModel.getImage());

        final int pos = holder.getAdapterPosition();

        if (pos == 0){

            holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, AllDidActivity.class);
                    context.startActivity(intent);
                }
            });
        }

        if (pos ==1){

            holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, AwatingDidActivity.class);
                    context.startActivity(intent);
                }
            });
        }

        if (pos == 2){

            holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, UnBlockedDidActivity.class);
                    context.startActivity(intent);
                }
            });
        }

        if(pos == 3){

            holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, AgentCommissionActivity.class);
                    context.startActivity(intent);
                }
            });
        }


        if (pos  == 4){

            holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, LoanApplyCustomerActivity.class);
                    context.startActivity(intent);
                }
            });
        }

        if (pos == 5){

            holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(context, LoanPassActivity.class);
                    context.startActivity(intent);
                }
            });
        }

        if (pos == 6){

            holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, AgentPerformenceActivity.class);
                    context.startActivity(intent);
                }
            });
        }

        if (pos == 7){

            holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, SettingsActivity.class);
                    context.startActivity(intent);
                }
            });

        }
    }

    @Override
    public int getItemCount() {
        return adbList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView textView;
        ImageView imageView;
        RelativeLayout relativeLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.profile_tv_adb);
            imageView = itemView.findViewById(R.id.agent_dash_ic);
            relativeLayout = itemView.findViewById(R.id.parent_l_adb);
        }
    }
}
