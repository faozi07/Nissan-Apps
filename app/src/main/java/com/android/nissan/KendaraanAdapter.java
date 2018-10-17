package com.android.nissan;

/*
 * Created by faozi on 01/02/18.
 */

import android.annotation.SuppressLint;
import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;


public class KendaraanAdapter extends RecyclerView.Adapter {

    public static Activity activity;
    public static ArrayList<modelKendaraan> items;
    modelKendaraan mrt;

    private final int VIEW_ITEM = 1;
    private int lastPosition = -1;

    private AlertDialog.Builder dialogs;
    private AlertDialog dialogListDetail;

    KendaraanAdapter(Activity act, ArrayList<modelKendaraan> data) {
        activity = act;
        items = data;
        dialogs = new AlertDialog.Builder(activity);
        dialogListDetail = dialogs.create();
    }

    @Override
    public int getItemViewType(int position) {
        int VIEW_PROG = 0;
        return items.get(position) != null ? VIEW_ITEM : VIEW_PROG;
    }

    public static class BrandViewHolder extends RecyclerView.ViewHolder {

        TextView tTgl, tNopol, tMerk, tJanji, tStatus;
        CardView cardView;

        BrandViewHolder(View v) {
            super(v);

            tTgl = v.findViewById(R.id.teksTgl);
            tMerk = v.findViewById(R.id.teksMerk);
            tNopol = v.findViewById(R.id.teksNopol);
            tStatus = v.findViewById(R.id.teksStatus);
            tJanji = v.findViewById(R.id.teksJanji);
            cardView = v.findViewById(R.id.card_view);
        }
    }

    @SuppressWarnings("ConstantConditions")
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
            ((BrandViewHolder) holder).tStatus.setText(mrt.getStatus());
            ((BrandViewHolder) holder).tJanji.setText(mrt.getTglJanji());

            if (mrt.getStatus().equals("Selesai")) {
                ((BrandViewHolder) holder).tStatus.setTextColor(activity.getResources().getColor(R.color.colorAccent));
            } else {
                ((BrandViewHolder) holder).tStatus.setTextColor(activity.getResources().getColor(R.color.colorRed));
            }

            ((BrandViewHolder) holder).cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mrt = items.get(position);
                    SoundBtn.soundBtn(activity);
                    dialogListDetail(mrt.getTglInput(), mrt.getMerek(), mrt.getNopol(), mrt. getTglJanji(), mrt.getJnsKerja());
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @SuppressLint("InflateParams")
    private void dialogListDetail(final String tgl, final String merk, final String nopol, final String janji, final String jenis) {
        LayoutInflater inflater;
        View dialog_layout;
        inflater = LayoutInflater.from(activity);
        dialog_layout = inflater.inflate(R.layout.dialog_list_detail, null);
        Button btnClose = dialog_layout.findViewById(R.id.btnClose);
        TextView teksTgl = dialog_layout.findViewById(R.id.teksTglMasuk);
        TextView teksMerk = dialog_layout.findViewById(R.id.teksMerk);
        TextView teksNopol = dialog_layout.findViewById(R.id.teksNopol);
        TextView teksJanji = dialog_layout.findViewById(R.id.teksJanji);
        TextView teksJenis = dialog_layout.findViewById(R.id.teksJenkerja);

        dialogs.setView(dialog_layout);
        dialogs.setCancelable(true);
        dialogListDetail = dialogs.create();
        if (!dialogListDetail.isShowing()) {
            dialogListDetail.show();
        }

        teksTgl.setText(tgl);
        teksMerk.setText(merk);
        teksNopol.setText(nopol);
        teksJanji.setText(janji);
        teksJenis.setText(jenis);

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SoundBtn.soundBtn(activity);
                dialogListDetail.dismiss();
            }
        });
    }

}
