package com.academy.keytone.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.academy.keytone.R;
import com.academy.keytone.fragment.EventsDetailsCalender;
import com.academy.keytone.model.EventDetails;

import java.util.List;

public class calenderAdapter extends RecyclerView.Adapter<calenderAdapter.GroceryViewHolder>{
    private List<EventDetails> horizontalGrocderyList;
    Context context;


    public calenderAdapter(List<EventDetails> horizontalGrocderyList, Context context){
        this.horizontalGrocderyList= horizontalGrocderyList;
        this.context = context;
    }

    @Override
    public GroceryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflate the layout file
        View groceryProductView = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_calender, parent, false);
        GroceryViewHolder gvh = new GroceryViewHolder(groceryProductView);
        return gvh;
    }

    @Override
    public void onBindViewHolder(GroceryViewHolder holder, final int position) {


        if(position==0){

            // holder.ll_main.setBackgroundColor(Color.parseColor("#567845"));
            holder.ll_main.setBackgroundDrawable(ContextCompat.getDrawable(context, R.mipmap.back_image) );
        }

        else {
            holder.img_ex.setImageResource(R.mipmap.calender_event);
           holder.textname.setTextColor(ContextCompat.getColor(context, R.color.black));
           holder.text_name1.setTextColor(ContextCompat.getColor(context, R.color.black));
           holder.text_name2.setTextColor(ContextCompat.getColor(context, R.color.black));
            holder.ll_main.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.rounded_rectangle) );


        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatActivity activity4 = (AppCompatActivity)context;
                Fragment myFragment4= new EventsDetailsCalender();
                activity4.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, myFragment4).addToBackStack(null).commit();
            }
        });

    }

    @Override
    public int getItemCount() {
        return horizontalGrocderyList.size();
    }

    public class GroceryViewHolder extends RecyclerView.ViewHolder {
       LinearLayout ll_main;
       ImageView img_ex;
       TextView textname,text_name1,text_name2;

        public GroceryViewHolder(View view) {
            super(view);
            ll_main=view.findViewById(R.id.ll_main);
            img_ex=view.findViewById(R.id.img_ex);
            textname=view.findViewById(R.id.txtname);
            text_name1=view.findViewById(R.id.text_name1);
            text_name2=view.findViewById(R.id.text_name2);

        }
    }
}