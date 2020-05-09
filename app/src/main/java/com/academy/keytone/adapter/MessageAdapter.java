package com.academy.keytone.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.academy.keytone.R;
import com.academy.keytone.model.notice;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.GroceryViewHolder>{
    public List<notice> horizontalGrocderyList;
    Context context;

    public MessageAdapter(List<notice> horizontalGrocderyList, Context context){
        this.horizontalGrocderyList= horizontalGrocderyList;
        this.context = context;
    }

    @Override
    public GroceryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflate the layout file
        View groceryProductView = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_message, parent, false);
        GroceryViewHolder gvh = new GroceryViewHolder(groceryProductView);
        return gvh;
    }

    @Override
    public void onBindViewHolder(GroceryViewHolder holder, final int position) {

        holder.title.setText(horizontalGrocderyList.get(position).getTitle());
        holder.subtitle.setText(horizontalGrocderyList.get(position).getSubtitle());
        holder.time.setText(horizontalGrocderyList.get(position).getTime());
    }

    @Override
    public int getItemCount() {
        return horizontalGrocderyList.size();
    }

    public class GroceryViewHolder extends RecyclerView.ViewHolder {
       RelativeLayout rl_main;
        TextView title,subtitle,time;
        public GroceryViewHolder(View view) {
            super(view);
            title=view.findViewById(R.id.title);
            subtitle=view.findViewById(R.id.title1);
            time=view.findViewById(R.id.time);
            rl_main=view.findViewById(R.id.rl_main);
        }
    }
}