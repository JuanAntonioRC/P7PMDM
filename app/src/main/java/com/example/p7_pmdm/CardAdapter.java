package com.example.p7_pmdm;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.p7_pmdm.Model.Sitio_pojo;

import java.util.ArrayList;
import java.util.List;

public class CardAdapter extends ArrayAdapter {
    private List<Sitio_pojo> listado = new ArrayList<>();

    static class CardViewHolder {
        TextView line1;
    }

    public CardAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    public void add(Sitio_pojo object) {
        listado.add(object);
        super.add(object);
    }

    @Override
    public int getCount() {
        return this.listado.size();
    }

    @Override
    public Sitio_pojo getItem(int index) {
        return this.listado.get(index);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.list_item_card, parent, false);
            CardViewHolder viewHolder = new CardViewHolder();
            viewHolder.line1 = row.findViewById(R.id.line1);

            Sitio_pojo p = getItem(position);
            viewHolder.line1.setText(p.getNombre());
            RatingBar ratingBar = row.findViewById(R.id.lic_ratingBar);
            ratingBar.setRating(p.getValoracion());
            row.setTag(viewHolder);


            row.setTag(viewHolder);
        }
        return row;
    }

}

