package com.android.nissan;

/*
 * Created by faozi on 01/02/18.
 */

import android.annotation.SuppressLint;
import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;


public class KendaraanAdapter extends RecyclerView.Adapter {

    public static Activity activity;
    public static ArrayList<modelKendaraan> items;
    modelKendaraan mrt;

    private final int VIEW_ITEM = 1;
    private int lastPosition = -1;

    public KendaraanAdapter(Activity act, ArrayList<modelKendaraan> data) {
        activity = act;
        items = data;
    }

    @Override
    public int getItemViewType(int position) {
        int VIEW_PROG = 0;
        return items.get(position) != null ? VIEW_ITEM : VIEW_PROG;
    }

    public static class BrandViewHolder extends RecyclerView.ViewHolder {

        TextView tTgl, tNopol, tMerk, tJanji;
        CardView cardView;

        BrandViewHolder(View v) {
            super(v);

            tTgl = v.findViewById(R.id.teksTgl);
            tMerk = v.findViewById(R.id.teksMerk);
            tNopol = v.findViewById(R.id.teksNopol);
            tJanji = v.findViewById(R.id.teksJanji);
            cardView = v.findViewById(R.id.card_view);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        RecyclerView.ViewHolder vh = null;
        if (viewType == VIEW_ITEM) {
            View v = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.item_list_kendaraan, parent, false);

            vh = new KendaraanAdapter.BrandViewHolder(v);
        }

        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        if (holder instanceof KendaraanAdapter.BrandViewHolder) {

            mrt = items.get(position);

            ((BrandViewHolder) holder).tTgl.setText(mrt.getTglInput());
            ((BrandViewHolder) holder).tMerk.setText(mrt.getMerek());
            ((BrandViewHolder) holder).tNopol.setText(mrt.getNopol());
            ((BrandViewHolder) holder).tJanji.setText(mrt.getTglJanji());
            ((BrandViewHolder) holder).cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mrt = items.get(position);
//                    activity.startActivity(new Intent(activity, DetailJadwal.class));
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

}
