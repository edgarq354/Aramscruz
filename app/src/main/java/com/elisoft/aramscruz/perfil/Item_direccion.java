package com.elisoft.aramscruz.perfil;


import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.elisoft.aramscruz.R;

import java.util.ArrayList;


/**
 * Created by ELIO on 28/10/2016.
 */

public  class Item_direccion extends BaseAdapter {

    String url_pagina;
    String nombre;
    protected Activity activity;
    protected ArrayList<CDireccion> items;
    private Context mContext;

    CDireccion ped;


    public Item_direccion(Context c,  Activity activity, ArrayList<CDireccion> items) {
        this.activity = activity;
        this.items = items;

        this.mContext=c;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    public void clear() {
        items.clear();
    }

    public void addAll(ArrayList<CDireccion> Pedidos) {
        for (int i = 0; i < Pedidos.size(); i++) {
            items.add(Pedidos.get(i));
        }
    }

    @Override
    public Object getItem(int arg0) {
        return items.get(arg0);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (convertView == null) {
            LayoutInflater inf = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inf.inflate(R.layout.item_direccion, null);
        }
        ped = items.get(position);

TextView tv_nombre= v.findViewById(R.id.tv_nombre);


        TextView tv_direccion= v.findViewById(R.id.tv_direccion);
       tv_nombre.setText(ped.getNombre());
       tv_direccion.setText(ped.getDireccion());







        return v;
    }





}