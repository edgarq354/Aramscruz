package com.elisoft.aramscruz.empresa;

import android.Manifest;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.elisoft.aramscruz.R;
import com.elisoft.aramscruz.guia_turistica.CLugar;
import com.elisoft.aramscruz.menu_otra_direccion.Otra_direccion;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Item_empresa extends BaseAdapter {

    protected Activity activity;
    protected ArrayList<CEmpresa> items;
    private Context mContext;

    public Item_empresa(Context c, Activity activity, ArrayList<CEmpresa> items) {
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
            v = inf.inflate(R.layout.item_empresa, null);
            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        final CEmpresa  ped = items.get(position);

        TextView nombre= v.findViewById(R.id.tv_nombre);
        TextView direccion= v.findViewById(R.id.tv_direccion);
        de.hdodenhof.circleimageview.CircleImageView im_perfil=v.findViewById(R.id.im_perfil);


        nombre.setText(ped.getRazon_social());
        direccion.setText(ped.getDireccion());

        String  url=  this.activity.getString(R.string.servidor_web)+"storage"+ped.getDireccion_logo_corporativo();

        Picasso.with(this.activity).load(url).placeholder(R.drawable.ic_empresa).into(im_perfil);


        return v;

    }




}
