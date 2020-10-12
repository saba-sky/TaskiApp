package com.example.taski_firebase_version;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class DO_Adapter extends RecyclerView.Adapter<DO_Adapter.MyViewHolder> {
    Context context;
    ArrayList<My_Does> list;


    public DO_Adapter(Context c, ArrayList<My_Does> d) {
        context = c;
        list = d;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item, parent, false), viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.titel_DO.setText(list.get(position).getTitel_DO());
        holder.date_DO.setText(list.get(position).getDate_DO());
        holder.desc_DO.setText(list.get(position).getDesc_DO());


        final String gentileDO = list.get(position).getTitel_DO();
        final String getDate = list.get(position).getDate_DO();
        final String getDesc = list.get(position).getDesc_DO();
        final String getKey = list.get(position).getKey_DO();
        final String getTail_Key = list.get(position).getDesc_DO();

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, Activity_Edite_Task.class);
                System.out.println("Do Adapter is called");
                intent.putExtra("titel_DO", gentileDO);
                intent.putExtra("date_DO", getDate);
                intent.putExtra("desc_DO", getDesc);
                intent.putExtra("key_DO", getKey);
                intent.putExtra("teil_Key", getTail_Key);
                System.out.println("Get key is: " + getKey);
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView titel_DO, date_DO, desc_DO;

        public MyViewHolder(@NonNull final View itemView, final int position) {
            super(itemView);
            titel_DO = itemView.findViewById(R.id.item_Titel);
            date_DO = itemView.findViewById(R.id.item_date);
            desc_DO = itemView.findViewById(R.id.item_desc);
        }


        @Override
        public String toString() {
            return super.toString();
        }
    }
}