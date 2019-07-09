package com.dsc.wwapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

import androidx.recyclerview.widget.RecyclerView;

import com.dsc.wwapp.R;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.MyViewHolder> implements ItemTouchHelperAdapter {


    private Context  context;

    @Override
    public void onItemDismiss(int position) {

        notifyItemRemoved(position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView timeTv,NewsTv;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            timeTv =itemView.findViewById(R.id.timeTextCVs);
            NewsTv =itemView.findViewById(R.id.newsCardView);

        }
    }

    public NewsAdapter(Context context){
        this.context = context;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout, parent, false);

        MyViewHolder myViewHolder = new MyViewHolder(view);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

//        holder.timeTv.setText("Today");
//        holder.NewsTv.setText("Why waste?");

    }

    @Override
    public int getItemCount() {
        return 5;
    }


}
