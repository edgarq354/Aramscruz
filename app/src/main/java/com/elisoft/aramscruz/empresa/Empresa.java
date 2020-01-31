package com.elisoft.aramscruz.empresa;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.elisoft.aramscruz.Menu_usuario;
import com.elisoft.aramscruz.R;
import com.elisoft.aramscruz.Suceso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Empresa extends AppCompatActivity {

    private ProgressDialog pDialog;
    ArrayList<CEmpresa> cEmpresas=new ArrayList<CEmpresa>();
    ListView lv_lista;

    Suceso suceso;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empresa2);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        lv_lista=(ListView)findViewById(R.id.lv_lista);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        lv_lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                CEmpresa hi=new CEmpresa();
                hi=cEmpresas.get(i);

                abrir_empresa(hi);

            }
        });






        servicio_volley_lista();

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }


    private void abrir_empresa(final CEmpresa hi) {
        AlertDialog.Builder builderSingle = new AlertDialog.Builder(Empresa.this);
        builderSingle.setIcon(R.drawable.ic_llamada);
        builderSingle.setTitle("Comunicate con la Empresa");

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(Empresa.this, android.R.layout.select_dialog_singlechoice);
        arrayAdapter.add("Llamar");
        arrayAdapter.add("WhatsApp");

        builderSingle.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String strName = arrayAdapter.getItem(which);
                if(strName.equals("Llamar")){
                    Intent llamada = new Intent(Intent.ACTION_DIAL);
                    llamada.setData(Uri.parse("tel:" +hi.getTelefono()));

                    if (ActivityCompat.checkSelfPermission(Empresa.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        verificar_permiso_llamada();
                    }else{
                        startActivity(llamada);
                    }
                }else if (strName.equals("WhatsApp")){
                    boolean isWhatsapp = appInstalledOrNot("com.whatsapp");
                    if (isWhatsapp)
                        AbrirWhatsApp(hi);
                }
                dialog.dismiss();
            }
        });
        builderSingle.show();
    }

    private boolean appInstalledOrNot(String uri) {
        PackageManager pm = getPackageManager();
        try {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
        }

        return false;
    }


    public void verificar_permiso_llamada()
    {
        final String[] PERMISSIONS = {Manifest.permission.CALL_PHONE };

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CALL_PHONE)) {
            //YA LO CANCELE Y VOUELVO A PERDIR EL PERMISO.

            AlertDialog.Builder dialogo1 = new AlertDialog.Builder(this);
            dialogo1.setTitle("Atención!");
            dialogo1.setMessage("Debes otorgar permisos de acceso a LLAMADA.");
            dialogo1.setCancelable(false);
            dialogo1.setPositiveButton("Solicitar permiso", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogo1, int id) {
                    dialogo1.cancel();
                    ActivityCompat.requestPermissions(Empresa.this,
                            PERMISSIONS,
                            1);

                }
            });
            dialogo1.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogo1, int id) {
                    dialogo1.cancel();

                }
            });
            dialogo1.show();
        } else {
            ActivityCompat.requestPermissions(Empresa.this,
                    PERMISSIONS,
                    1);
        }
    }

    void AbrirWhatsApp(CEmpresa hi) {

        Uri uri = Uri.parse("smsto: +591"+hi.getWhatsapp());
        Intent i = new Intent(Intent.ACTION_SENDTO, uri);
        i.setPackage("com.whatsapp");
        i.putExtra("sms_body", "Un movil por favor");
        startActivity(Intent.createChooser(i, getString(R.string.app_name)));

      /*  String formattedNumber = "+59176633339";
        try{
            Intent sendIntent =new Intent("android.intent.action.MAIN");
            sendIntent.setComponent(new ComponentName("com.whatsapp", "com.whatsapp.Conversation"));
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.setType("text/plain");
            sendIntent.putExtra(Intent.EXTRA_TEXT,"hola");
            sendIntent.putExtra("sms_body", "Hola que tal");
            sendIntent.putExtra("jid", formattedNumber +"@s.whatsapp.net");
            sendIntent.setPackage("com.whatsapp");
             startActivity(sendIntent);
        }
        catch(Exception e)
        {
            Toast.makeText(this,"Error/n"+ e.toString(),Toast.LENGTH_SHORT).show();
        }*/
    }

    private void servicio_volley_lista() {

        try {
            String v_url= getString(R.string.servidor)+"frmTaxi.php?opcion=get_empresa";

            JSONObject jsonParam= new JSONObject();
            RequestQueue queue = Volley.newRequestQueue(this);


            JsonObjectRequest myRequest= new JsonObjectRequest(
                    Request.Method.POST,
                    v_url,
                    jsonParam,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject respuestaJSON) {


                            try {
                                suceso=new Suceso(respuestaJSON.getString("suceso"),respuestaJSON.getString("mensaje"));

                                if (suceso.getSuceso().equals("1")) {
                                    JSONArray usu=respuestaJSON.getJSONArray("lista");
                                    cEmpresas=new ArrayList<CEmpresa>();
                                    for (int i=0;i<usu.length();i++)
                                    {
                                        String id=usu.getJSONObject(i).getString("id");
                                        String razon_social=usu.getJSONObject(i).getString("razon_social");
                                        String direccion=usu.getJSONObject(i).getString("direccion");
                                        String telefono=usu.getJSONObject(i).getString("telefono");
                                        String whatsapp=usu.getJSONObject(i).getString("whatsapp");
                                        String direccion_logo_corporativo=usu.getJSONObject(i).getString("direccion_logo_corporativo");


                                        cEmpresas.add(new CEmpresa( id,
                                                razon_social,
                                                direccion,
                                                telefono,
                                                whatsapp,
                                                direccion_logo_corporativo));
                                    }

                                    Item_empresa adaptador = new Item_empresa(getApplicationContext(),Empresa.this,cEmpresas);
                                    lv_lista.setAdapter(adaptador);

                                } else  {
                                    cEmpresas.clear();
                                    Item_empresa adaptador = new Item_empresa(getApplicationContext(),Empresa.this,cEmpresas);
                                    lv_lista.setAdapter(adaptador);
                                }

                                //...final de final....................





                            } catch (JSONException e) {
                                e.printStackTrace();
                                mensaje_error("Falla en tu conexión a Internet.");
                            }

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    mensaje_error("Falla en tu conexión a Internet.");
                }
            }
            ){
                public Map<String,String> getHeaders() throws AuthFailureError {
                    Map<String,String> parametros= new HashMap<>();
                    parametros.put("content-type","application/json; charset=utf-8");
                    parametros.put("Authorization","apikey 849442df8f0536d66de700a73ebca-us17");
                    parametros.put("Accept", "application/json");

                    return  parametros;
                }
            };


            queue.add(myRequest);
        } catch (Exception e) {

        }


    }

    public void mensaje_error(String mensaje)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Importante");
        builder.setMessage(mensaje);
        builder.setPositiveButton("OK", null);
        builder.create();
        builder.show();
    }


}
