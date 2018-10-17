package com.android.nissan;

import android.app.ProgressDialog;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public class ListKendaraan extends AppCompatActivity {

    public static ArrayList<modelKendaraan> arrKendaraan = new ArrayList<>();
    KendaraanAdapter kendaraanAdapter;
    RecyclerView rvKendaraan;
    SwipeRefreshLayout swipeRefresh;
    TextView teksNoData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_kendaraan);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("List Kendaraan");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        init();

    }

    private void init() {
        swipeRefresh = findViewById(R.id.swipLayout);
        rvKendaraan = findViewById(R.id.rvKendaraan);
        teksNoData = findViewById(R.id.teksNoData);
        kendaraanAdapter = new KendaraanAdapter(this, arrKendaraan);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        rvKendaraan.setLayoutManager(llm);
        rvKendaraan.setHasFixedSize(true);

        final ProgressDialog pDialog = new ProgressDialog(this);
        pDialog.setMessage("Memuat data ...");
        pDialog.setCancelable(false);
        pDialog.show();

        DB db = new DB(this);
        db.listKendaraan();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                rvKendaraan.setAdapter(kendaraanAdapter);
                if (arrKendaraan.size() > 0) {
                    teksNoData.setVisibility(View.GONE);
                    swipeRefresh.setVisibility(View.VISIBLE);
                }
                else {
                    teksNoData.setVisibility(View.VISIBLE);
                    swipeRefresh.setVisibility(View.GONE);
                }
                pDialog.dismiss();
            }
        },3000);

        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefresh.setRefreshing(false);
                arrKendaraan.clear();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        rvKendaraan.setAdapter(kendaraanAdapter);
                        pDialog.dismiss();
                    }
                },3000);
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed() {
        SoundBtn.soundBtn(ListKendaraan.this);
        finish();
    }
}
