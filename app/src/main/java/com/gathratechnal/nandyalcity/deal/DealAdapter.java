package com.gathratechnal.nandyalcity.deal;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gathratechnal.nandyalcity.R;
import com.gathratechnal.nandyalcity.deal.model.Result;

import java.util.List;


public class DealAdapter  extends RecyclerView.Adapter<DealAdapter.ViewHolder> {
    private List<Result> listResult;
    private Context context;

    public DealAdapter(List<Result> results, Context context) {
        this.listResult = results;
        this.context = context;
    }

    @Override
    public DealAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_deal, viewGroup, false);
        return new DealAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final DealAdapter.ViewHolder viewHolder, int i) {

        viewHolder.description_textView.setText(listResult.get(i).getDealTitle());
        viewHolder.image_title_textView.setText(listResult.get(i).getDetails());

        if (!listResult.get(i).getAppImage().isEmpty()) {
            try {
                Glide.with(context).load(listResult.get(i).getAppImage())
                        .into(viewHolder.restaurant_imageView);
            } catch (Exception e) {
                //Do Nothing
            }
        }

    }

    @Override
    public int getItemCount() {
        return listResult.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        TextView description_textView;
        TextView image_title_textView;
        ImageView restaurant_imageView;

        public ViewHolder(View view) {
            super(view);

            description_textView = view.findViewById(R.id.description_textView);
            image_title_textView = view.findViewById(R.id.image_title_textView);
            restaurant_imageView = view.findViewById(R.id.restaurant_imageView);
        }
    }

}
