package com.example.mitjag.dnevnik_rokov;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.provider.Settings;
import android.provider.SyncStateContract;
import android.support.v7.widget.ListViewCompat;
import android.telecom.Call;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.security.PrivateKey;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Mitja G on 24.5.2016.
 */
public class AdapterRoki extends RecyclerView.Adapter<AdapterRoki.ViewHolder> {
    private Dnevnik mDataset;
    Activity ac;
    public static Toast toast=null;
    private SparseBooleanArray selectedItems = new SparseBooleanArray();
    public RecyclerView prikaz;
    //private TextView datum;

    public AdapterRoki(Dnevnik myDataset,Activity ac){
        this.ac=ac;
        mDataset=myDataset;


    }



    @Override
    public AdapterRoki.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rowlayout, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if(mDataset.sizeDnevnik()>0) {
            Datum_uporabe trenutni =mDataset.getDnevnikById(position);
            final String name = trenutni.getArtikel().getNaziv();
            holder.txtHeader.setText(trenutni.getArtikel().getNaziv());
            holder.txtFooter.setText(trenutni.getArtikel().getOpis());

            Date dat=trenutni.getRok_uporabe().getTime();
            SimpleDateFormat format1 = new SimpleDateFormat("dd-MM-yyyy");
            String date1 = format1.format(dat);
            holder.txtTip.setText(date1);
            //datum.setText(date1);

        }else{

            holder.txtFooter.setVisibility(View.GONE);
            holder.txtHeader.setVisibility(View.GONE);
            holder.txtTip.setVisibility(View.GONE);

            holder.txtHeader.setText("Ni vnosa!");
            holder.txtHeader.setTextColor(Color.WHITE);
            holder.txtFooter.setText("");
            holder.txtTip.setText("");

            //datum.setText("");
        }
    }

    @Override
    public int getItemCount() {
        if(toast!=null){
            toast.cancel();
            toast=null;
        }

        if(mDataset.sizeDnevnik()>0) {
            return mDataset.sizeDnevnik();
        }
        if(toast==null) {
            toast = Toast.makeText(ac,
                    "Ni podatkov!", Toast.LENGTH_SHORT);
            toast.show();
        }
        return 0;
    }
    public class ViewHolder extends RecyclerView.ViewHolder  implements View.OnLongClickListener {
        // each data item is just a string in this case
        public TextView txtHeader;
        public TextView txtFooter;
        public TextView txtTip;

        public ViewHolder(View v) {
            super(v);
            txtHeader = (TextView) v.findViewById(R.id.firstLine);
            txtFooter = (TextView) v.findViewById(R.id.secondLine);
            txtTip= (TextView) v.findViewById(R.id.firstLineTip);

            v.setOnLongClickListener(this);
        }

        @Override
        public boolean onLongClick(View v){
            v.setSelected(true);
            toast = Toast.makeText(ac,
                    "Pregledan!", Toast.LENGTH_SHORT);
            toast.show();
            return true;
        }


    }


}

