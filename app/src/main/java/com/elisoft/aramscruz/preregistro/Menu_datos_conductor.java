package com.elisoft.aramscruz.preregistro;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.elisoft.aramscruz.R;
import com.elisoft.aramscruz.Suceso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class Menu_datos_conductor extends AppCompatActivity implements View.OnClickListener{

    String direccion_imagen="";
    String direccion_imagen_carnet_1="";
    String direccion_imagen_licencia_1="";
    String direccion_imagen_tic="";
    String ci="";

    Button bt_carnet,bt_licencia,bt_perfil,bt_tic;
    ImageView im_carnet,im_licencia,im_perfil,im_tic;

    Suceso suceso;
    ProgressDialog pDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_datos_conductor);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        bt_carnet=(Button)findViewById(R.id.bt_carnet);
        bt_licencia=(Button)findViewById(R.id.bt_licencia);
        bt_perfil=(Button)findViewById(R.id.bt_perfil);
        bt_tic=(Button)findViewById(R.id.bt_tic);
        im_carnet=(ImageView)findViewById(R.id.im_carnet);
        im_licencia=(ImageView) findViewById(R.id.im_licencia);
        im_perfil=(ImageView) findViewById(R.id.im_perfil);
        im_tic=(ImageView) findViewById(R.id.im_tic);

        bt_carnet.setOnClickListener(this);
        bt_licencia.setOnClickListener(this);
        bt_perfil.setOnClickListener(this);
        bt_tic.setOnClickListener(this);



        try{
            Bundle bundle=getIntent().getExtras();
            ci=bundle.getString("ci");
            direccion_imagen=bundle.getString("direccion_imagen");
            direccion_imagen=bundle.getString("direccion_imagen");
            direccion_imagen_carnet_1=bundle.getString("direccion_imagen_carnet_1");
            direccion_imagen_licencia_1=bundle.getString("direccion_imagen_licencia_1");
            direccion_imagen_tic=bundle.getString("direccion_imagen_tic");

        }catch (Exception e)
        {
        }
        verificar_todas_las_fotos();
        verificar_datos();

    }

    public void verificar_todas_las_fotos()
    {
        if(direccion_imagen.length()>5)
        {
            im_perfil.setBackgroundResource(R.drawable.ic_ok);
        }  if(direccion_imagen_carnet_1.length()>5)
        {
            im_carnet.setBackgroundResource(R.drawable.ic_ok);

        }
        if(direccion_imagen_licencia_1.length()>5)
        {
            im_licencia.setBackgroundResource(R.drawable.ic_ok);

        }
        if(direccion_imagen_tic.length()>5)
        {
            im_tic.setBackgroundResource(R.drawable.ic_ok);

        }





    }


    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

    @Override
    protected void onRestart() {
        verificar_datos();
        super.onRestart();
    }



    public  void verificar_datos()
    {
        // Servicio_conductor servicio = new Servicio_conductor();
        // servicio.execute(getString(R.string.servidor) + "frmTaxi.php?opcion=get_direccion_conductor", "1");// parametro que recibe el doinbackground

        String url=getString(R.string.servidor) + "frmTaxi.php?opcion=get_direccion_conductor";
        try {
            JSONObject jsonParam= new JSONObject();
            jsonParam.put("ci",ci);

            RequestQueue queue = Volley.newRequestQueue(this);


            JsonObjectRequest myRequest= new JsonObjectRequest(
                    Request.Method.POST,
                    url,
                    jsonParam,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject respuestaJSON) {
                            pDialog.dismiss();
                            try {
                                String error=respuestaJSON.getString("suceso");

                                if (error.equals("1")) {
                                    JSONArray dato=respuestaJSON.getJSONArray("perfil_conductor");

                                    direccion_imagen=dato.getJSONObject(0).getString("direccion_imagen");
                                    direccion_imagen_carnet_1=respuestaJSON.getString("direccion_imagen_carnet_1");
                                    direccion_imagen_licencia_1=respuestaJSON.getString("direccion_imagen_licencia_1");
                                    direccion_imagen_tic=respuestaJSON.getString("direccion_imagen_tic");


                                    //final
                                    verificar_todas_las_fotos();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    pDialog.dismiss();
                }
            }
            ){
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> parametros= new HashMap<>();
                    parametros.put("content-type","application/json; charset=utf-8");
                    parametros.put("Authorization","apikey 849442df8f0536d66de700a73ebca-us17");
                    parametros.put("Accept", "application/json");

                    return  parametros;
                }
            };
            pDialog = new ProgressDialog(Menu_datos_conductor.this);
            pDialog.setTitle(getString(R.string.app_name));
            pDialog.setMessage("Verificando . .");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
            queue.add(myRequest);
        } catch (Exception e) {

        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.bt_perfil:
                Intent ip=new Intent(this,Foto_conductor.class);
                ip.putExtra("ci", ci);
                ip.putExtra("direccion_imagen", direccion_imagen);

                startActivity(ip);
                break;
            case R.id.bt_carnet:
                Intent ic=new Intent(this,Foto_carnet.class);
                ic.putExtra("direccion_imagen_carnet_1", direccion_imagen_carnet_1);
                ic.putExtra("ci", ci);
                startActivity(ic);
                break;
            case R.id.bt_licencia:
                Intent il=new Intent(this,Foto_licencia.class);
                il.putExtra("direccion_imagen_licencia_1", direccion_imagen_licencia_1);
                il.putExtra("ci", ci);
                startActivity(il);
                break;
            case R.id.bt_tic:
                Intent il1=new Intent(this,Foto_licencia.class);
                il1.putExtra("direccion_imagen_tic", direccion_imagen_tic);
                il1.putExtra("ci", ci);
                startActivity(il1);
                break;
        }

    }


    public class Servicio_conductor extends AsyncTask<String,Integer,String> {


        @Override
        protected String doInBackground(String... params) {

            String cadena = params[0];
            URL url = null;  // url donde queremos obtener informacion
            String devuelve = "";
//verificar si los datos en la base de datos con su cedula de identidad.
            if (params[1] == "1") {
                try {
                    HttpURLConnection urlConn;

                    DataOutputStream printout;
                    DataOutputStream input;

                    url = new URL(cadena);
                    urlConn = (HttpURLConnection) url.openConnection();
                    urlConn.setDoInput(true);
                    urlConn.setDoOutput(true);
                    urlConn.setUseCaches(false);
                    urlConn.setRequestProperty("Content-Type", "application/json");
                    urlConn.setRequestProperty("Accept", "application/json");
                    urlConn.connect();

                    //se crea el objeto JSON
                    JSONObject jsonParam = new JSONObject();
                    jsonParam.put("ci",ci);

                    //Envio los prametro por metodo post
                    OutputStream os = urlConn.getOutputStream();
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, StandardCharsets.UTF_8));
                    writer.write(jsonParam.toString());
                    writer.flush();
                    writer.close();

                    int respuesta = urlConn.getResponseCode();

                    StringBuilder result = new StringBuilder();

                    if (respuesta == HttpURLConnection.HTTP_OK) {

                        String line;
                        BufferedReader br = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
                        while ((line = br.readLine()) != null) {
                            result.append(line);
                        }

                        //Creamos un objeto JSONObject para poder acceder a los atributos (campos) del objeto.
                        JSONObject respuestaJSON = new JSONObject(result.toString());//Creo un JSONObject a partir del
                        // StringBuilder pasando a cadena.                    }

                        SystemClock.sleep(950);

                        //Accedemos a vector de resultados.
                        String error = respuestaJSON.getString("suceso");// suceso es el campo en el Json
                        suceso=new Suceso(respuestaJSON.getString("suceso"),respuestaJSON.getString("mensaje"));

                        if (error.equals("1")) {
                            JSONArray dato=respuestaJSON.getJSONArray("perfil_conductor");

                            direccion_imagen=dato.getJSONObject(0).getString("direccion_imagen");
                            direccion_imagen_carnet_1=respuestaJSON.getString("direccion_imagen_carnet_1");
                            direccion_imagen_licencia_1=respuestaJSON.getString("direccion_imagen_licencia_1");


                            devuelve="1";
                        }  else
                        {
                            devuelve="2";
                        }

                    }

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }


            return devuelve;
        }


        @Override
        protected void onPreExecute() {
            //para el progres Dialog
            pDialog = new ProgressDialog(Menu_datos_conductor.this);
            pDialog.setTitle(getString(R.string.app_name));
            pDialog.setMessage("Verificando . .");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            pDialog.dismiss();//ocultamos proggress dialog


            if (s.equals("1")) {
               verificar_todas_las_fotos();
            }
            else
            {

            }
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onCancelled(String s) {
            super.onCancelled(s);
        }
    }
}
