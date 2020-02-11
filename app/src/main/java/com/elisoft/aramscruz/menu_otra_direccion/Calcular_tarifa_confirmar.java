package com.elisoft.aramscruz.menu_otra_direccion;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;



import com.elisoft.aramscruz.Pedido_usuario;
import com.elisoft.aramscruz.Punto;
import com.elisoft.aramscruz.R;
import com.elisoft.aramscruz.Suceso;
import com.elisoft.aramscruz.empresa.CEmpresa;
import com.elisoft.aramscruz.empresa.Empresa;
import com.elisoft.aramscruz.empresa.Item_empresa;
import com.elisoft.aramscruz.perfil.Perfil_pasajero;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.squareup.picasso.Picasso;

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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class Calcular_tarifa_confirmar extends AppCompatActivity implements OnMapReadyCallback, View.OnClickListener {


    private GoogleMap mMap;
    String[] direccion = {"",""};
    JSONObject rutas=null;
    LatLngBounds AUSTRALIA;
    double latitud_inicio,longitud_inicio,latitud_fin,longitud_fin;
    TextView tv_punto_inicio,tv_punto_fin;
    ProgressDialog pDialog;
    Suceso suceso;

    CheckBox cb_tipo_pedido_empresa;

    TextView tv_monto_distancia,tv_monto_tiempo,tv_tarifa_normal,tv_tarifa_de_lujo,tv_tarifa_con_aire,tv_tarifa_maletero,tv_tarifa_con_pedido, tv_tarifa_con_reserva,tv_tarifa_moto,tv_tarifa_moto_pedido;
    TextView tv_tarifa_camioncito;
    TextView tv_tarifa_compras;

    TextView tv_tarifa_grua_torito;
    TextView tv_tarifa_aeropuerto;

    TextView tv_billetera;

    TextView tv_nombre_empresa;


    int cantidad_solicitud_tarifa=0;
    int cantidad_solicitud_ruta=0;

    double monto_normal=0,monto_billetera=0;


    LocationManager manager = null;
    AlertDialog alert = null;

    LinearLayout
            pedi_taxi,
            pedir_movil_lujo,
            pedir_movil_maletero,
            pedir_movil_aire,
            pedir_movil_pedido,
            pedir_movil_reserva,
            pedir_moto,
            ll_solicitar_ahora;

    FrameLayout fm_empresas;
    ImageView im_cerrar;
    ListView lv_lista_empresas;
    String id_empresa="0";
    ArrayList<CEmpresa> cEmpresas=new ArrayList<CEmpresa>();


    LinearLayout
            pedir_movil_normal_seleccion,
            pedir_movil_lujo_seleccion,
            pedir_movil_maletero_seleccion,
            pedir_movil_aire_seleccion,
            pedir_moto_seleccion,
            pedir_movil_camioncito_seleccion,
            pedir_movil_compras_seleccion,
            pedir_movil_empresa_seleccion,
            pedir_movil_grua_torito_seleccion,
            pedir_movil_aeropuerto_seleccion;

    ImageView
            im_movil_normal,
            im_movil_lujo,
            im_movil_maletero,
            im_movil_aire,
            im_moto,
            im_movil_camioncito,
            im_movil_compras,
            im_movil_empresa,
            im_movil_grua_torito,
            im_movil_aeropuerto;


    boolean sw_ver_taxi_cerca = false;
    private JSONArray puntos_taxi;


    Marker marker=null;
    Marker marker1=null;

    double lat_1=0,lon_1=0;

    AlertDialog alert2 = null;

    SharedPreferences mis_datos;

    int clase_vehiculo=1;

    private RequestQueue queue=null;
















    Marker marker_1=null;
    Marker marker_2=null;
    Marker marker_3=null;
    Marker marker_4=null;
    Marker marker_5=null;
    Marker marker_6=null;
    Marker marker_7=null;
    Marker marker_8=null;
    Marker marker_9=null;
    Marker marker_10=null;
    String cond_1="";
    String cond_2="";
    String cond_3="";
    String cond_4="";
    String cond_5="";
    String cond_6="";
    String cond_7="";
    String cond_8="";
    String cond_9="";
    String cond_10="";

    String fecha_1="";
    String fecha_2="";
    String fecha_3="";
    String fecha_4="";
    String fecha_5="";
    String fecha_6="";
    String fecha_7="";
    String fecha_8="";
    String fecha_9="";
    String fecha_10="";

    String fecha_ultimo="";

    Handler handle=new Handler();
    int interseccion=0;


    //INICIO MARCAR RUTA
    private List<LatLng> bangaloreRoute;
    //FIN MARCAR RUTA

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_calcular_tarifa_confirmar);
        tv_punto_inicio=(TextView)findViewById(R.id.tv_punto_inicio);
        tv_punto_fin=(TextView)findViewById(R.id.tv_punto_final);
        tv_monto_distancia=(TextView)findViewById(R.id.tv_monto_distancia);
        tv_monto_tiempo=(TextView)findViewById(R.id.tv_monto_tiempo);
        tv_tarifa_normal=(TextView)findViewById(R.id.tv_tarifa_normal);
        tv_tarifa_de_lujo=(TextView)findViewById(R.id.tv_tarifa_de_lujo);
        tv_tarifa_con_aire=(TextView)findViewById(R.id.tv_tarifa_con_aire);
        tv_tarifa_maletero=(TextView)findViewById(R.id.tv_tarifa_maletero);
        tv_tarifa_con_pedido=(TextView)findViewById(R.id.tv_tarifa_con_pedido);
        tv_tarifa_con_reserva=(TextView)findViewById(R.id.tv_tarifa_con_reserva);
        tv_tarifa_moto=(TextView)findViewById(R.id.tv_tarifa_moto);
        tv_tarifa_moto_pedido=(TextView)findViewById(R.id.tv_tarifa_moto_pedido);
        tv_billetera=(TextView)findViewById(R.id.tv_billetera);

        tv_tarifa_camioncito=(TextView)findViewById(R.id.tv_tarifa_camioncito);
        tv_tarifa_compras=(TextView)findViewById(R.id.tv_tarifa_compras);
        tv_nombre_empresa=(TextView)findViewById(R.id.tv_nombre_empresa);
        tv_tarifa_grua_torito=(TextView)findViewById(R.id.tv_tarifa_grua_torito);
        tv_tarifa_aeropuerto=(TextView)findViewById(R.id.tv_tarifa_aeropuerto);

        cb_tipo_pedido_empresa=(CheckBox)findViewById(R.id.cb_tipo_pedido_empresa);





        ll_solicitar_ahora = (LinearLayout) findViewById(R.id.ll_solicitar_ahora);

        fm_empresas= (FrameLayout) findViewById(R.id.fm_empresas);
        im_cerrar=(ImageView) findViewById(R.id.im_cerrar);
        lv_lista_empresas=(ListView) findViewById(R.id.lv_lista_empresas);

        pedi_taxi = (LinearLayout) findViewById(R.id.pedir_movil);
        pedir_movil_aire = (LinearLayout) findViewById(R.id.pedir_movil_aire);
        pedir_movil_lujo = (LinearLayout) findViewById(R.id.pedir_movil_lujo);
        pedir_movil_maletero = (LinearLayout) findViewById(R.id.pedir_movil_maletero);
        pedir_movil_pedido = (LinearLayout) findViewById(R.id.pedir_movil_pedido);
        pedir_movil_reserva = (LinearLayout) findViewById(R.id.pedir_movil_reserva);
        pedir_moto = (LinearLayout)findViewById(R.id.pedir_moto);


//seleccion
        pedir_movil_normal_seleccion = (LinearLayout) findViewById(R.id.pedir_movil_normal_seleccion);
        pedir_movil_aire_seleccion = (LinearLayout) findViewById(R.id.pedir_movil_aire_seleccion);
        pedir_movil_lujo_seleccion = (LinearLayout) findViewById(R.id.pedir_movil_lujo_seleccion);
        pedir_movil_maletero_seleccion = (LinearLayout) findViewById(R.id.pedir_movil_maletero_seleccion);
        pedir_moto_seleccion = (LinearLayout)findViewById(R.id.pedir_moto_seleccion);
        pedir_movil_camioncito_seleccion= findViewById(R.id.pedir_movil_camioncito_seleccion);
        pedir_movil_compras_seleccion=findViewById(R.id.pedir_movil_compras_seleccion);
        pedir_movil_empresa_seleccion=findViewById(R.id.pedir_movil_empresa_seleccion);
        pedir_movil_grua_torito_seleccion=findViewById(R.id.pedir_movil_grua_torito_seleccion);
        pedir_movil_aeropuerto_seleccion = (LinearLayout)findViewById(R.id.pedir_movil_aeropuerto_seleccion);

        im_movil_normal = (ImageView) findViewById(R.id.im_movil_normal);
        im_movil_aire = (ImageView) findViewById(R.id.im_movil_aire);
        im_movil_lujo = (ImageView) findViewById(R.id.im_movil_lujo);
        im_movil_maletero = (ImageView) findViewById(R.id.im_movil_maletero);
        im_moto = (ImageView)findViewById(R.id.im_moto);
        im_movil_camioncito=findViewById(R.id.im_movil_camioncito);
        im_movil_compras=findViewById(R.id.im_movil_compras);
        im_movil_empresa=findViewById(R.id.im_movil_empresa);
        im_movil_grua_torito=findViewById(R.id.im_movil_grua_torito);
        im_movil_aeropuerto=findViewById(R.id.im_movil_aeropuerto);
//fin seleccion

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);


        try{
            Bundle bundle=getIntent().getExtras();
            latitud_inicio=bundle.getDouble("latitud_inicio");
            longitud_inicio=bundle.getDouble("longitud_inicio");
            latitud_fin=bundle.getDouble("latitud_fin");
            longitud_fin=bundle.getDouble("longitud_fin");

            if(latitud_inicio!=latitud_fin && longitud_inicio!=longitud_fin)
            {
                marcar_ruta();
            }
            

            solicitar_tarifa();
        }catch (Exception e){

        }

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);





        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ll_solicitar_ahora.setOnClickListener(this);

        pedi_taxi.setOnClickListener(this);
        pedir_movil_lujo.setOnClickListener(this);
        pedir_movil_aire.setOnClickListener(this);
        pedir_movil_maletero.setOnClickListener(this);
        pedir_movil_pedido.setOnClickListener(this);
        pedir_movil_reserva.setOnClickListener(this);
        pedir_moto.setOnClickListener(this);
        cb_tipo_pedido_empresa.setOnClickListener(this);

        //SELECCIONAR
        pedir_movil_normal_seleccion.setOnClickListener(this);
        pedir_movil_aire_seleccion.setOnClickListener(this);
        pedir_movil_lujo_seleccion.setOnClickListener(this);
        pedir_movil_maletero_seleccion.setOnClickListener(this);
        pedir_moto_seleccion.setOnClickListener(this);
        pedir_movil_camioncito_seleccion.setOnClickListener(this);
        pedir_movil_compras_seleccion.setOnClickListener(this);
        pedir_movil_empresa_seleccion.setOnClickListener(this);
        pedir_movil_grua_torito_seleccion.setOnClickListener(this);
        pedir_movil_aeropuerto_seleccion.setOnClickListener(this);

        im_cerrar.setOnClickListener(this);



        actualizar_billetera();
        direccion[0] =obtener_direccion( latitud_inicio,longitud_inicio);
        direccion[1] =obtener_direccion( latitud_fin,longitud_fin);
        tv_punto_inicio.setText(String.valueOf(direccion[0]));
        tv_punto_fin.setText(String.valueOf(direccion[1]));

        mis_datos=getSharedPreferences(getString(R.string.mis_datos),MODE_PRIVATE);



        lv_lista_empresas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                CEmpresa hi=new CEmpresa();
                hi=cEmpresas.get(i);
                id_empresa=hi.getId();
                tv_nombre_empresa.setText(hi.getRazon_social());
                String  url=  getString(R.string.servidor_web)+"storage"+hi.getDireccion_logo_corporativo();
                Picasso.with(Calcular_tarifa_confirmar.this).load(url).placeholder(R.drawable.ic_empresa).into(im_movil_empresa);
                fm_empresas.setVisibility(View.INVISIBLE);
            }
        });

        servicio_volley_lista_empresa();

        //MARCAR RUTA

    }

    public void solicitar_tarifa()
    {
          servicio_calcular_tarifa();

    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng inicio = new LatLng(latitud_inicio, longitud_inicio);
        LatLng fin = new LatLng(latitud_fin, longitud_fin);
        try {

            marker1= this.mMap.addMarker(new MarkerOptions()
                    .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_punto_inicio_1))
                    .anchor((float) 0.5, (float) 0.8)
                    .flat(true)
                    .position(inicio)
                    .title(direccion[0]));
            marker1.showInfoWindow();


        } catch (Exception e) {
        }

        try {

            marker= this.mMap.addMarker(new MarkerOptions()
                    .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_punto_fin_1))
                    .anchor((float) 0.5, (float) 0.8)
                    .flat(true)
                    .position(fin)
                    .title(direccion[1]));
            marker.showInfoWindow();

        } catch (Exception e) {

        }
        crear_puntos_conductor();
        ver_moviles();
        marcar_ruta();





// Set the camera to the greatest possible zoom level that includes the
// bounds

        //agregaranimacion al mover la camara...
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(inicio)      // Sets the center of the map to Mountain View
                .zoom(15)                   // Sets the zoom
                .bearing(0)                // Sets the orientation of the camera to east
                .tilt(0)                   // Sets the tilt of the camera to 30 degrees
                .build();
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));



    }






    public String obtener_direccion(double lat, double lon) {
        Geocoder geocoder = new Geocoder(getBaseContext());
        List<Address> addresses = null;
        String s_direccion= "";

        try {
            addresses = geocoder.getFromLocation(lat, lon, 1);
        } catch (IOException ioException) {
            // Catch network or other I/O problems.

        } catch (IllegalArgumentException illegalArgumentException) {

        }

        // Handle case where no address was found.
        if (addresses == null || addresses.size() == 0) {
            //error. o no tiene datos recolectados...
        } else {

// Funcion que determina si se obtuvo resultado o no

            // Creamos el objeto address
            Address address = addresses.get(0);


            if (addresses.size() > 0) {
                int cantidad=addresses.get(0).getMaxAddressLineIndex();
                for (int i = 0; i <= cantidad; i++)
                {
                    s_direccion+= addresses.get(0).getAddressLine(i) + ",";
                }
            }


            // Creamos el string a partir del elemento direccion
            String direccionText = String.format("%s, %s",
                    address.getMaxAddressLineIndex() > 0 ? address.getAddressLine(0) : "",
                    address.getFeatureName());
        }
        return s_direccion;
    }

    public void marcar_ruta( )
    {
        try{
             servicio_taxi_ruta();
        }catch (Exception e)
        {

        }
    }


    public void servicio_calcular_tarifa()
    {
        //Servicio servicio = new Servicio();
        //servicio.execute(getString(R.string.servidor) + "frmCarrera.php?opcion=calcular_tarifa", "1", String.valueOf(latitud_inicio), String.valueOf(longitud_inicio),String.valueOf(latitud_fin),String.valueOf(longitud_fin));// parametro que recibe el doinbackground
        pDialog = new ProgressDialog(Calcular_tarifa_confirmar.this);
        pDialog.setTitle(getString(R.string.app_name));
        pDialog.setMessage("Calculando la tarifa");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();

        try {
            JSONObject jsonParam= new JSONObject();
            jsonParam.put("latitud_inicio", String.valueOf(latitud_inicio));
            jsonParam.put("longitud_inicio", String.valueOf(longitud_inicio));
            jsonParam.put("latitud_fin", String.valueOf(latitud_fin));
            jsonParam.put("longitud_fin", String.valueOf(longitud_fin));

            String url=getString(R.string.servidor) + "frmCarrera.php?opcion=calcular_tarifa";

            if (queue == null) {
                queue = Volley.newRequestQueue(this);
            }


            JsonObjectRequest myRequest= new JsonObjectRequest(
                    Request.Method.POST,
                    url,
                    jsonParam,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject respuestaJSON) {
                            pDialog.cancel();
                            try {
                                suceso=new Suceso(respuestaJSON.getString("suceso"),respuestaJSON.getString("mensaje"));

                                if (suceso.getSuceso().equals("1")) {
                                    String metros=respuestaJSON.getString("metros");
                                    String minuto=respuestaJSON.getString("minutos");
                                    String normal=respuestaJSON.getString("normal");
                                    String de_lujo=respuestaJSON.getString("de_lujo");
                                    String con_aire=respuestaJSON.getString("con_aire");
                                    String maletero=respuestaJSON.getString("maletero");
                                    String pedido=respuestaJSON.getString("pedido");
                                    String reserva=respuestaJSON.getString("reserva");
                                    String moto=respuestaJSON.getString("moto");
                                    String moto_pedido=respuestaJSON.getString("moto_pedido");

                                    String camioncito=respuestaJSON.getString("camioncito");
                                    String compras=respuestaJSON.getString("compras");
                                    String empresa=respuestaJSON.getString("empresa");
                                    String grua_torito=respuestaJSON.getString("grua_torito");
                                    String aeropuerto=respuestaJSON.getString("aeropuerto");


                                    //final
                                    int minuto1= Integer.parseInt(minuto);
                                    int distancia= Integer.parseInt(metros);
                                    int km=distancia/1000;
                                    int m=distancia%1000;
                                    if(distancia<1000)
                                    {
                                        tv_monto_distancia.setText("Mt. "+ metros);
                                    }else{
                                        tv_monto_distancia.setText("Km. "+ km+"."+m);
                                    }


                                    tv_monto_tiempo.setText("Hrs. "+ formatearMinutosAHoraMinuto(minuto1));

                                    tv_monto_tiempo.setText("Hrs. "+ formatearMinutosAHoraMinuto(minuto1));
                                    tv_tarifa_normal.setText(  normal+" BOB");
                                    tv_tarifa_de_lujo.setText("Bs. "+ de_lujo);
                                    tv_tarifa_con_aire.setText("Bs. "+ con_aire);
                                    tv_tarifa_maletero.setText("Bs. "+ maletero);
                                    tv_tarifa_con_pedido.setText("Bs. "+ pedido);
                                    tv_tarifa_con_reserva.setText("Bs. "+ reserva);
                                    tv_tarifa_moto.setText("Bs. "+moto);
                                    tv_tarifa_moto_pedido.setText("Bs. "+ moto_pedido);

                                    tv_tarifa_camioncito.setText("Bs. "+camioncito);
                                    tv_tarifa_compras.setText("Bs. "+compras);
                                    tv_tarifa_grua_torito.setText("Bs. "+grua_torito);
                                    tv_tarifa_aeropuerto.setText("Bs. "+ aeropuerto);

                                    monto_normal=Double.parseDouble(normal);

                                } else  {
                                    if(cantidad_solicitud_tarifa<3)
                                    {
                                        solicitar_tarifa();
                                    }else{
                                        mensaje_error(suceso.getMensaje());
                                    }
                                    cantidad_solicitud_tarifa++;
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                mensaje_error("Falla en tu conexión a Internet.");
                            }

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    pDialog.cancel();
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

    public void servicio_billetera(String id_usuario)
    {


        try {
            JSONObject jsonParam= new JSONObject();
            jsonParam.put("id_usuario", id_usuario);


            String url=getString(R.string.servidor) + "frmCarrera.php?opcion=calcular_tarifa";
            if (queue == null) {
                queue = Volley.newRequestQueue(this);
            }


            JsonObjectRequest myRequest= new JsonObjectRequest(
                    Request.Method.POST,
                    url,
                    jsonParam,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject respuestaJSON) {

                            String s_monto="";

                            try {
                                suceso=new Suceso(respuestaJSON.getString("suceso"),respuestaJSON.getString("mensaje"));

                                if (suceso.getSuceso().equals("1")) {
                                    s_monto=respuestaJSON.getString("monto");

                                    //final
                                    monto_billetera=Double.parseDouble(s_monto);

                                } else  {

                                }
                                tv_billetera.setText(monto_billetera+" BOB");


                            } catch (JSONException e) {
                                e.printStackTrace();

                            }

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

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
    public void servicio_taxi_ruta()
    {
        //Servicio servicio = new Servicio();
        //servicio.execute(getString(R.string.servidor) + "frmCarrera.php?opcion=calcular_tarifa", "1", String.valueOf(latitud_inicio), String.valueOf(longitud_inicio),String.valueOf(latitud_fin),String.valueOf(longitud_fin));// parametro que recibe el doinbackground

        try {
            JSONObject jsonParam= new JSONObject();

            String url="https://maps.googleapis.com/maps/api/directions/json?origin=" + latitud_inicio + "," + longitud_inicio + "&destination=" + latitud_fin + "," + longitud_fin + "&mode=driving&key=AIzaSyB1h4N5nfpkF1Hg30P88c_1MvH9qG9Tcvs";
            if (queue == null) {
                queue = Volley.newRequestQueue(this);
            }


            JsonObjectRequest myRequest= new JsonObjectRequest(
                    Request.Method.POST,
                    url,
                    jsonParam,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject respuestaJSON) {
                            pDialog.cancel();
                            String metros,minuto,normal,de_lujo,con_aire,maletero,pedido,reserva,moto,moto_pedido;
                            try {
                                rutas= new JSONObject(respuestaJSON.toString());//Creo un JSONObject a partir del
                                //final
                                dibujar_ruta(rutas);
                            } catch (JSONException e) {
                                e.printStackTrace();
                                if(cantidad_solicitud_ruta<3){
                                marcar_ruta();
                                }else{
                                    mensaje_error("No pudimos conectarnos al servidor.\nVuelve a intentarlo.");
                                }
                                cantidad_solicitud_ruta++;
                                }

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    pDialog.cancel();
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
            pDialog.cancel();
        }
    }



    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            //seleccion
            case R.id.pedir_movil_normal_seleccion:
                //movil normal
                seleccionar_movil(1);
                break;
            case R.id.pedir_movil_lujo_seleccion:
                //movi de lujo
                seleccionar_movil(2);
                break;
            case R.id.pedir_movil_aire_seleccion:
                //movil con aire acondicionado
                seleccionar_movil(3);
                break;
            case R.id.pedir_movil_maletero_seleccion:
                //movil con maletero
                seleccionar_movil(4);
                break;
            case R.id.pedir_moto_seleccion:
                //moto
                seleccionar_movil(7);
                break;

            case R.id.pedir_movil_camioncito_seleccion:
                //CAMIONCITO --CAMIONETA
                seleccionar_movil(15);
                break;
            case R.id.pedir_movil_compras_seleccion:
                //COMPRAS
                seleccionar_movil(16);
                break;
            case R.id.pedir_movil_empresa_seleccion:
                //EMPRESA
                fm_empresas.setVisibility(View.VISIBLE);
                //seleccionar_movil(17);
                break;
            case R.id.pedir_movil_grua_torito_seleccion:
                //GRUA TORITO
                seleccionar_movil(18);
                break;
            case R.id.pedir_movil_aeropuerto_seleccion:
                //AEROPUERTO
                seleccionar_movil(19);
                break;
            case  R.id.ll_solicitar_ahora:

                solicitar_ahora();

                break;
            //fin de seleccion
            case R.id.cb_tipo_pedido_empresa:
                actualizar_billetera();
                if(cb_tipo_pedido_empresa.isChecked()==true)
                {
                    double descuento=0;

                    if(monto_normal>monto_billetera)
                    {
                    descuento=monto_normal-monto_billetera;
                    }
                    tv_tarifa_normal.setText(descuento+" BOB");

                }else
                {
                    tv_tarifa_normal.setText(monto_normal+" BOB");
                }

                break;
            case R.id.pedir_movil:

                //solicita un movil de cualquier caracteristica.





                    if(existe_celular()==true)
                    {

                        try{
                            lat_1=latitud_inicio;
                            lon_1=longitud_inicio;
                        }catch (Exception e){
                            lat_1=0;
                            lon_1=0;
                        }
                        if(lat_1!=0&& lon_1!=0){
                            escribir_referencia(1,0,"Solicitando un movil");
                        }else{
                            AlertDialog.Builder dialogo1 = new AlertDialog.Builder(this);
                            dialogo1.setTitle("Atención");
                            dialogo1.setMessage("Por favor geolocalice su ubicación.");
                            dialogo1.setCancelable(false);
                            dialogo1.setPositiveButton("OK", null);
                            dialogo1.show();
                        }

                    } else{
                        actualizar_perfil();
                    }

                break;


            case R.id.pedir_movil_aire:

                    if(existe_celular()==true)
                    {


                        try{
                            lat_1=latitud_inicio;
                            lon_1=longitud_inicio;
                        }catch (Exception e){
                            lat_1=0;
                            lon_1=0;
                        }
                        if(lat_1!=0&& lon_1!=0){
                            escribir_referencia(3,0,"Solicitando un movil con aire acondicionado");
                        }else{
                            AlertDialog.Builder dialogo1 = new AlertDialog.Builder(this);
                            dialogo1.setTitle("Atención");
                            dialogo1.setMessage("Por favor geolocalice su ubicación.");
                            dialogo1.setCancelable(false);
                            dialogo1.setPositiveButton("OK", null);
                            dialogo1.show();
                        }


                    } else{
                        actualizar_perfil();
                    }


                break;

            case R.id.pedir_movil_maletero:

                    if(existe_celular()==true)
                    {
                        try{
                            lat_1=latitud_inicio;
                            lon_1=longitud_inicio;
                        }catch (Exception e){
                            lat_1=0;
                            lon_1=0;
                        }
                        if(lat_1!=0&& lon_1!=0){
                            escribir_referencia(4,0,"Solicitando un movil con maletero");
                        }else{
                            AlertDialog.Builder dialogo1 = new AlertDialog.Builder(this);
                            dialogo1.setTitle("Atención");
                            dialogo1.setMessage("Por favor geolocalice su ubicación.");
                            dialogo1.setCancelable(false);
                            dialogo1.setPositiveButton("OK", null);
                            dialogo1.show();
                        }

                    } else {
                        actualizar_perfil();
                    }

                break;

            case R.id.im_cerrar:
                fm_empresas.setVisibility(View.INVISIBLE);
                im_movil_empresa.setImageResource(R.drawable.ic_movil_empresa);
                id_empresa="0";
                tv_nombre_empresa.setText("~");
                break;
        }
        ver_moviles();
    }

    private void solicitar_ahora() {
        if(existe_celular()==true)
        {

            try{
                lat_1=latitud_inicio;
                lon_1=longitud_inicio;
            }catch (Exception e){
                lat_1=0;
                lon_1=0;
            }
            if(lat_1!=0&& lon_1!=0){
                escribir_referencia(clase_vehiculo,0,"Solicitando un movil");
            }else{
                AlertDialog.Builder dialogo1 = new AlertDialog.Builder(this);
                dialogo1.setTitle("Atención");
                dialogo1.setMessage("Por favor geolocalice su ubicación.");
                dialogo1.setCancelable(false);
                dialogo1.setPositiveButton("OK", null);
                dialogo1.show();
            }

        } else{
            actualizar_perfil();
        }
    }

    private void seleccionar_movil(int i) {
        clase_vehiculo=i;

        im_movil_normal.setImageResource(R.drawable.ic_movil_normal_gris);
      //im_movil_aire.setImageResource(R.mipmap.ic_movil_aire_gris);
        im_movil_lujo.setImageResource(R.drawable.ic_movil_lujo_gris);
        im_movil_maletero.setImageResource(R.drawable.ic_movil_maletero_gris);
        im_moto.setImageResource(R.drawable.ic_moto_gris);

        im_movil_camioncito.setImageResource(R.drawable.ic_movil_camioncito_gris);
        im_movil_compras.setImageResource(R.drawable.ic_movil_compras_gris);
        //im_movil_empresa.setImageResource(R.drawable.ic_movil_empresa_gris);
        im_movil_grua_torito.setImageResource(R.drawable.ic_movil_grua_torito_gris);
        im_movil_aeropuerto.setImageResource(R.drawable.ic_movil_aeropuerto_gris);

        switch (i){
            case 1:
                im_movil_normal.setImageResource(R.drawable.ic_movil_normal);
            break;
            case 2:
                im_movil_lujo.setImageResource(R.drawable.ic_movil_lujo);
                break;
            case 3:
                //MOVIL CON AIRE
               // im_movil_aire.setImageResource(R.mipmap.ic_movil_aire);
                break;
            case 4:
                im_movil_maletero.setImageResource(R.drawable.ic_movil_maletero);
                break;
            case 7:
                im_moto.setImageResource(R.drawable.ic_moto);
                break;
            case 15:
                //CAMIONCITO
                im_movil_camioncito.setImageResource(R.drawable.ic_movil_camioncito);
                break;
            case 16:
                //COMPRAS
                im_movil_compras.setImageResource(R.drawable.ic_movil_compras);
                break;
            case 17:
                //EMPRESA
               // im_movil_empresa.setImageResource(R.drawable.ic_movil_empresa);
                break;
            case 18:
                //GRUA TORITO
                im_movil_grua_torito.setImageResource(R.drawable.ic_movil_grua_torito);
                break;
            case 19:
                //GRUA TORITO
                im_movil_aeropuerto.setImageResource(R.drawable.ic_movil_aeropuerto);
                break;
        }
    }


    public void  escribir_referencia(final int clase_vehiculo,final int tipo_pedido_empresa,String tipo_pedido_texto)
    {

        // get prompts.xml view
        LayoutInflater layoutInflater = LayoutInflater.from(Calcular_tarifa_confirmar.this);
        View promptView = layoutInflater.inflate(R.layout.escribir_referencia, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Calcular_tarifa_confirmar.this);
        alertDialogBuilder.setView(promptView);

        final Button bt_cancelar= promptView.findViewById(R.id.bt_cancelar);
        final Button bt_pedir= promptView.findViewById(R.id.bt_pedir);
        final EditText et_referencia= promptView.findViewById(R.id.et_referencia);
        final TextView tv_tipo_pedido= promptView.findViewById(R.id.tv_tipo_pedido);
        tv_tipo_pedido.setText(tipo_pedido_texto);

        et_referencia.setText(mis_datos.getString("referencia",""));


        bt_cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
                alert2.cancel();
            }
        });

        bt_pedir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editar=mis_datos.edit();
                editar.putString("referencia",et_referencia.getText().toString());
                editar.commit();

                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
                pedir_taxi("0", et_referencia.getText().toString().trim(),clase_vehiculo,tipo_pedido_empresa);
                alert2.cancel();

            }
        });
        // create an alert dialog
        alert2 = alertDialogBuilder.create();
        alert2.show();
    }

    private void AlertNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("El sistema GPS esta desactivado, ¿Desea activarlo?")
                .setCancelable(false)
                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        if ( !manager.isProviderEnabled( LocationManager.GPS_PROVIDER ) ) {
                            startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                        }

                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        dialog.cancel();
                        finish();
                    }
                });
        alert = builder.create();
        alert.show();
    }
    public void pedir_taxi(String numero, String referencia, int clase_vehiculo, int tipo_pedido_empresa){
        ///verifica si el GPS esta activo.
        if ( !manager.isProviderEnabled( LocationManager.GPS_PROVIDER ) ) {
            AlertNoGps();
        }
        String billetera="0";
        if(cb_tipo_pedido_empresa.isChecked()==true)
        {
            billetera="1";
        }


            if (existe_celular() == true) {

                if (referencia.length() >= 0) {
                    Intent datos_pedido = new Intent(this, Pedido_usuario.class);
                    datos_pedido.putExtra("latitud", latitud_inicio);
                    datos_pedido.putExtra("longitud", longitud_inicio);
                    datos_pedido.putExtra("latitud_final", latitud_fin);
                    datos_pedido.putExtra("longitud_final", longitud_fin);
                    datos_pedido.putExtra("referencia", referencia);
                    datos_pedido.putExtra("numero",numero);
                    datos_pedido.putExtra("clase_vehiculo",clase_vehiculo);
                    datos_pedido.putExtra("direccion",tv_punto_inicio.getText().toString());
                    datos_pedido.putExtra("direccion_final",tv_punto_fin.getText().toString());
                    datos_pedido.putExtra("tipo_pedido_empresa",tipo_pedido_empresa);
                    datos_pedido.putExtra("estado_billetera",billetera);
                    datos_pedido.putExtra("id_empresa",id_empresa);
                    startActivity(datos_pedido);
                } else {
                    AlertDialog.Builder dialogo1 = new AlertDialog.Builder(this);
                    dialogo1.setTitle("Atención");
                    dialogo1.setMessage("Por favor introduzca una referencia para ayudar al conductor a ubicarlo.");
                    dialogo1.setCancelable(false);
                    dialogo1.setPositiveButton("OK", null);
                    dialogo1.show();
                }

            } else {
                actualizar_perfil();
            }



    }

    private void actualizar_perfil() {
        AlertDialog.Builder dialogo1 = new AlertDialog.Builder(this);
        dialogo1.setTitle(getString(R.string.app_name));
        dialogo1.setMessage("Por favor Ingrese su número del Telefono movil para que podamos identificarte.");
        dialogo1.setCancelable(false);
        dialogo1.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {
                startActivity(new Intent(getApplicationContext(),Perfil_pasajero.class));
            }
        });

        dialogo1.show();
    }

    public boolean existe_celular() {
        boolean sw = true;
        SharedPreferences perfil = getSharedPreferences("perfil", MODE_PRIVATE);
        if (perfil.getString("celular", "").equals("") == true  || perfil.getString("celular", "").length()<7 ) {
            sw = false;
        }
        return sw;
    }



    public String formatearMinutosAHoraMinuto(int minutos) {
        String formato = "%02d:%02d";
        long horasReales = TimeUnit.MINUTES.toHours(minutos);
        long minutosReales = TimeUnit.MINUTES.toMinutes(minutos) - TimeUnit.HOURS.toMinutes(TimeUnit.MINUTES.toHours(minutos));
        return String.format(formato, horasReales, minutosReales);
    }



    private void actualizar_billetera() {
        SharedPreferences sperfil=getSharedPreferences("perfil",MODE_PRIVATE);
        //Servicio_billetera hilo = new Servicio_billetera();
        //hilo.execute(getString(R.string.servidor) + "frmUsuario.php?opcion=get_monto_billetera", "1",sperfil.getString("id_usuario",""));// parametro que recibe el doinbackground
        servicio_billetera(sperfil.getString("id_usuario",""));

    }


    public void mensaje_error(String mensaje)
    {




        AlertDialog.Builder dialogo1 = new AlertDialog.Builder(this);
        dialogo1.setTitle("Importante");
        dialogo1.setMessage(mensaje);
        dialogo1.setCancelable(false);
        dialogo1.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {
                finish();
            }
        });

        dialogo1.show();




    }

    public void dibujar_ruta(JSONObject jObject){

        //DIBUJAR ANIMACION
        //createRoute();
       // startAnim();



        if (bangaloreRoute == null) {
            bangaloreRoute = new ArrayList<>();
        } else {
            bangaloreRoute.clear();
        }


        String tiempo="";
        JSONArray jRoutes = null;
        JSONArray jLegs = null;
        JSONArray jSteps = null;
        LatLng punto=new LatLng(0,0);
        PolylineOptions polylineOptions = new PolylineOptions();

        try {

            jRoutes = jObject.getJSONArray("routes");

            /** Traversing all routes */
            for(int i=0;i<jRoutes.length();i++){
                jLegs = ( (JSONObject)jRoutes.get(i)).getJSONArray("legs");
                /** Traversing all legs */
                for(int j=0;j<jLegs.length();j++){
                    jSteps = ( (JSONObject)jLegs.get(j)).getJSONArray("steps");
                    /** Traversing all steps */
                    for(int k=0;k<jSteps.length();k++){
                        String polyline = "";
                        polyline = (String)((JSONObject)((JSONObject)jSteps.get(k)).get("polyline")).get("points");
                        List<LatLng> list = decodePoly(polyline);

                        /** Traversing all points */
                        for(int l=0;l<list.size();l++){
                            double lat= list.get(l).latitude;
                            double lon= list.get(l).longitude;
                            punto = new LatLng(lat, lon);
                            polylineOptions.add(punto);

                            //PUNTO AGREGADO
                            bangaloreRoute.add(punto);

                        }
                    }

                    tiempo=(String)((JSONObject)((JSONObject)jLegs.get(j)).get("duration")).get("text");
                }
            }




        //    mMap.addPolyline(polylineOptions.width(8).color(Color.BLACK));

        } catch (JSONException e) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
            dibujar_ruta(rutas);
            e.printStackTrace();
        }catch (Exception e){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
            dibujar_ruta(rutas);
        }


        startAnim();
    }


    private void startAnim() {
        if (mMap != null) {
            MapAnimator.getInstance().animateRoute(mMap, bangaloreRoute);
        } else {
            Toast.makeText(getApplicationContext(), "Map not ready", Toast.LENGTH_LONG).show();
        }
    }

    private List<LatLng> decodePoly(String encoded) {

        List<LatLng> poly = new ArrayList<LatLng>();
        int index = 0, len = encoded.length();
        int lat = 0, lng = 0;

        while (index < len) {
            int b, shift = 0, result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lat += dlat;

            shift = 0;
            result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lng += dlng;

            LatLng p = new LatLng((((double) lat / 1E5)),
                    (((double) lng / 1E5)));
            poly.add(p);
        }

        return poly;
    }


    private void ver_moviles() {
        SharedPreferences ult=getSharedPreferences("ultimo_pedido",MODE_PRIVATE);
        //si no tiene pédidos se le va a mostrar en el mapa....
        if(ult.getString("id_pedido","").equals("")==true) {
            try {

                if(sw_ver_taxi_cerca==false) {
                    sw_ver_taxi_cerca=true;
                    servicio_volley_get_taxi_en_rango(String.valueOf(latitud_inicio), String.valueOf(longitud_inicio));
                }
            }catch (Exception e)
            {

                sw_ver_taxi_cerca=false;
                ver_moviles();
            }
        }else
        {
            try {
                int id_pedido= Integer.parseInt(ult.getString("id_pedido",""));
             //   Servicio hilo_taxi = new  Servicio();
              //  hilo_taxi.execute(getString(R.string.servidor) + "frmPedido.php?opcion=get_pedido_por_id_pedido", "5", String.valueOf(id_pedido));// parametro que recibe el doinbackground
            }catch (Exception e)
            {
                sw_ver_taxi_cerca=false;
                ver_moviles();
            }
        }
    }

    private void servicio_volley_get_taxi_en_rango(String latitud, String longitud) {

        try {
            JSONObject jsonParam= new JSONObject();
            jsonParam.put("latitud", String.valueOf(latitud));
            jsonParam.put("longitud", String.valueOf(longitud));
            jsonParam.put("clase_vehiculo", String.valueOf(clase_vehiculo));

            String url=getString(R.string.servidor) + "frmTaxi.php?opcion=get_taxi_en_rango_clase_vehiculo";
            if (queue == null) {
                queue = Volley.newRequestQueue(this);
            }


            JsonObjectRequest myRequest= new JsonObjectRequest(
                    Request.Method.POST,
                    url,
                    jsonParam,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject respuestaJSON) {
                            try {
                                suceso=new Suceso(respuestaJSON.getString("suceso"),respuestaJSON.getString("mensaje"));

                                if (suceso.getSuceso().equals("1")) {
                                    puntos_taxi = respuestaJSON.getJSONArray("taxi");
                                    //final
                                    sw_ver_taxi_cerca=false;
                                    agregar_en_mapa_ubicaciones_de_taxi();

                                } else  {
                                    sw_ver_taxi_cerca=false;

                                    Date date = new Date();
                                    DateFormat hourdateFormat = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
                                    fecha_ultimo=hourdateFormat.format(date);

                                    ocultar_conductores_no_activos();
                                    ver_moviles();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                sw_ver_taxi_cerca=false;
                                ver_moviles();
                            }

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
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


            myRequest.setRetryPolicy(new DefaultRetryPolicy(30000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            queue.add(myRequest);
        } catch (Exception e) {

        }
    }


    public void agregar_en_mapa_ubicaciones_de_taxi() {
        try {
            for (int i = 0; i < puntos_taxi.length(); i++) {
                int rotacion = Integer.parseInt(puntos_taxi.getJSONObject(i).getString("rotacion"));
                double lat = Double.parseDouble(puntos_taxi.getJSONObject(i).getString("latitud"));
                double lon = Double.parseDouble(puntos_taxi.getJSONObject(i).getString("longitud"));
                String distancia= puntos_taxi.getJSONObject(i).getString("distancia");
                String id= puntos_taxi.getJSONObject(i).getString("ci");
                String fecha= puntos_taxi.getJSONObject(i).getString("fecha");
                fecha_ultimo=fecha;
                int moto=Integer.parseInt(puntos_taxi.getJSONObject(i).getString("moto"));

                cargar_puntos_movil(lat, lon, rotacion, distancia, id, fecha,moto);

            }

            for (int i = 0; i < puntos_taxi.length(); i++) {
                int rotacion = Integer.parseInt(puntos_taxi.getJSONObject(i).getString("rotacion"));
                double lat = Double.parseDouble(puntos_taxi.getJSONObject(i).getString("latitud"));
                double lon = Double.parseDouble(puntos_taxi.getJSONObject(i).getString("longitud"));
                String distancia= puntos_taxi.getJSONObject(i).getString("distancia");
                String id= puntos_taxi.getJSONObject(i).getString("ci");
                String fecha= puntos_taxi.getJSONObject(i).getString("fecha");

                int moto=Integer.parseInt(puntos_taxi.getJSONObject(i).getString("moto"));

                cargar_puntos_movil_segundo(lat, lon,rotacion,distancia,id,fecha,moto);


            }


            ocultar_conductores_no_activos();


        } catch (Exception e) {

        }




        interseccion=0;

        new Thread(new Runnable() {
            @Override
            public void run() {

                while (interseccion<6)
                {
                    interseccion=interseccion+1;
                    if(interseccion>=4) {
                        interseccion = 7;
                        handle.post(new Runnable() {
                            @Override
                            public void run() {
                                ver_moviles();
                            }


                        });
                    }
                    try{
                        Thread.sleep(1000);
                    }catch (InterruptedException e)
                    {
                        ver_moviles();
                        e.printStackTrace();
                    }
                }

            }
        }).start();
    }
    public void cargar_puntos_movil( double lat,double lon,int rotacion,String distancia,String id,String fecha,int moto) {

      /*
        try {

            LatLng punto = new LatLng(lat, lon);
if(clase_vehiculo==1) {
    if (moto == 0) {
        mMap.addMarker(new MarkerOptions()
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_car_marker))
                .position(punto)
                .anchor((float) 0.5, (float) 0.8)
                .flat(true)
                .rotation(rotacion)
                .title("Mtrs. " + distancia));
    } else {
        mMap.addMarker(new MarkerOptions()
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_mot_marker))
                .position(punto)
                .anchor((float) 0.5, (float) 0.8)
                .flat(true)
                .rotation(rotacion)
                .title("Mtrs. " + distancia));
    }

}else if(clase_vehiculo==2)
{
    //movil de lujo
    mMap.addMarker(new MarkerOptions()
            .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_lujo_marker))
            .position(punto)
            .anchor((float) 0.5, (float) 0.8)
            .flat(true)
            .rotation(rotacion)
            .title("Mtrs. " + distancia));
}else if(clase_vehiculo==4)
{
    //maletero.
    mMap.addMarker(new MarkerOptions()
            .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_vagoneta_marker))
            .position(punto)
            .anchor((float) 0.5, (float) 0.8)
            .flat(true)
            .rotation(rotacion)
            .title("Mtrs. " + distancia));
}else if(clase_vehiculo==7)
{
    //moto
    mMap.addMarker(new MarkerOptions()
            .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_mot_marker))
            .position(punto)
            .anchor((float) 0.5, (float) 0.8)
            .flat(true)
            .rotation(rotacion)
            .title("Mtrs. " + distancia));
}else if(clase_vehiculo==15)
{
    //camioncito
    mMap.addMarker(new MarkerOptions()
            .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_camioncito_marker))
            .position(punto)
            .anchor((float) 0.5, (float) 0.8)
            .flat(true)
            .rotation(rotacion)
            .title("Mtrs. " + distancia));
}else if(clase_vehiculo==18)
{
    //grua
    mMap.addMarker(new MarkerOptions()
            .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_camioncito_marker))
            .position(punto)
            .anchor((float) 0.5, (float) 0.8)
            .flat(true)
            .rotation(rotacion)
            .title("Mtrs. " + distancia));
}




        } catch (Exception e) {

        }

        */




        LatLng ubicacion=new LatLng(lat,lon);

        if(id.equals(cond_1)){
            fecha_1=fecha;
            marker_1.setVisible(true);
            marker_1.setRotation(rotacion);
            MarkerAnimation.animateMarkerToGB(marker_1, ubicacion, new LatLngInterpolator.Spherical());

            if(clase_vehiculo==1)
            {
                //movil de lujo
                marker_1.setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_car_marker));
            }else if(clase_vehiculo==2)
            {
                //movil de lujo
                marker_1.setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_lujo_marker));
            }else if(clase_vehiculo==4)
            {
                //maletero.
                marker_1.setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_vagoneta_marker));
            }else if(clase_vehiculo==7)
            {
                //moto
                marker_1.setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_mot_marker));
            }else if(clase_vehiculo==15)
            {
                //camioncito
                marker_1.setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_camioncito_marker));
            }else if(clase_vehiculo==18)
            {
                //grua
                marker_1.setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_camioncito_marker));
            }
        }
        else if(id.equals(cond_2)){
            fecha_2=fecha;
            marker_2.setVisible(true);
            marker_2.setRotation(rotacion);
            MarkerAnimation.animateMarkerToGB(marker_2, ubicacion, new LatLngInterpolator.Spherical());

            if(clase_vehiculo==1)
            {
                //movil de lujo
                marker_2.setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_car_marker));
            }else if(clase_vehiculo==2)
            {
                //movil de lujo
                marker_2.setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_lujo_marker));
            }else if(clase_vehiculo==4)
            {
                //maletero.
                marker_2.setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_vagoneta_marker));
            }else if(clase_vehiculo==7)
            {
                //moto
                marker_2.setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_mot_marker));
            }else if(clase_vehiculo==15)
            {
                //camioncito
                marker_2.setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_camioncito_marker));
            }else if(clase_vehiculo==18)
            {
                //grua
                marker_2.setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_camioncito_marker));
            }
        }
        else  if(id.equals(cond_3)){
            fecha_3=fecha;
            marker_3.setVisible(true);
            marker_3.setRotation(rotacion);
            MarkerAnimation.animateMarkerToGB(marker_3, ubicacion, new LatLngInterpolator.Spherical());

            if(clase_vehiculo==1)
            {
                //movil de lujo
                marker_3.setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_car_marker));
            }else if(clase_vehiculo==2)
            {
                //movil de lujo
                marker_3.setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_lujo_marker));
            }else if(clase_vehiculo==4)
            {
                //maletero.
                marker_3.setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_vagoneta_marker));
            }else if(clase_vehiculo==7)
            {
                //moto
                marker_3.setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_mot_marker));
            }else if(clase_vehiculo==15)
            {
                //camioncito
                marker_3.setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_camioncito_marker));
            }else if(clase_vehiculo==18)
            {
                //grua
                marker_3.setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_camioncito_marker));
            }
        }
        else if(id.equals(cond_4)){
            fecha_4=fecha;
            marker_4.setVisible(true);
            marker_4.setRotation(rotacion);

            MarkerAnimation.animateMarkerToGB(marker_4, ubicacion, new LatLngInterpolator.Spherical());

            if(clase_vehiculo==1)
            {
                //movil de lujo
                marker_4.setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_car_marker));
            }else if(clase_vehiculo==2)
            {
                //movil de lujo
                marker_4.setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_lujo_marker));
            }else if(clase_vehiculo==4)
            {
                //maletero.
                marker_4.setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_vagoneta_marker));
            }else if(clase_vehiculo==7)
            {
                //moto
                marker_4.setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_mot_marker));
            }else if(clase_vehiculo==15)
            {
                //camioncito
                marker_4.setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_camioncito_marker));
            }else if(clase_vehiculo==18)
            {
                //grua
                marker_4.setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_camioncito_marker));
            }
        }
        else if(id.equals(cond_5)){
            fecha_5=fecha;
            marker_5.setVisible(true);
            marker_5.setRotation(rotacion);

            MarkerAnimation.animateMarkerToGB(marker_5, ubicacion, new LatLngInterpolator.Spherical());

            if(clase_vehiculo==1)
            {
                //movil de lujo
                marker_5.setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_car_marker));
            }else if(clase_vehiculo==2)
            {
                //movil de lujo
                marker_5.setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_lujo_marker));
            }else if(clase_vehiculo==4)
            {
                //maletero.
                marker_5.setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_vagoneta_marker));
            }else if(clase_vehiculo==7)
            {
                //moto
                marker_5.setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_mot_marker));
            }else if(clase_vehiculo==15)
            {
                //camioncito
                marker_5.setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_camioncito_marker));
            }else if(clase_vehiculo==18)
            {
                //grua
                marker_5.setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_camioncito_marker));
            }

        }
        else if(id.equals(cond_6)){
            fecha_6=fecha;
            marker_6.setVisible(true);
            marker_6.setRotation(rotacion);

            MarkerAnimation.animateMarkerToGB(marker_6, ubicacion, new LatLngInterpolator.Spherical());

            if(clase_vehiculo==1)
            {
                //movil de lujo
                marker_6.setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_car_marker));
            }else if(clase_vehiculo==2)
            {
                //movil de lujo
                marker_6.setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_lujo_marker));
            }else if(clase_vehiculo==4)
            {
                //maletero.
                marker_6.setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_vagoneta_marker));
            }else if(clase_vehiculo==7)
            {
                //moto
                marker_6.setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_mot_marker));
            }else if(clase_vehiculo==15)
            {
                //camioncito
                marker_6.setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_camioncito_marker));
            }else if(clase_vehiculo==18)
            {
                //grua
                marker_6.setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_camioncito_marker));
            }
        }
        else if(id.equals(cond_7)){
            fecha_7=fecha;
            marker_7.setVisible(true);
            marker_7.setRotation(rotacion);

            MarkerAnimation.animateMarkerToGB(marker_7, ubicacion, new LatLngInterpolator.Spherical());

            if(clase_vehiculo==1)
            {
                //movil de lujo
                marker_7.setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_car_marker));
            }else if(clase_vehiculo==2)
            {
                //movil de lujo
                marker_7.setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_lujo_marker));
            }else if(clase_vehiculo==4)
            {
                //maletero.
                marker_7.setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_vagoneta_marker));
            }else if(clase_vehiculo==7)
            {
                //moto
                marker_7.setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_mot_marker));
            }else if(clase_vehiculo==15)
            {
                //camioncito
                marker_7.setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_camioncito_marker));
            }else if(clase_vehiculo==18)
            {
                //grua
                marker_7.setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_camioncito_marker));
            }
        }
        else if(id.equals(cond_8)){
            fecha_8=fecha;
            marker_8.setVisible(true);
            marker_8.setRotation(rotacion);

            MarkerAnimation.animateMarkerToGB(marker_8, ubicacion, new LatLngInterpolator.Spherical());

            if(clase_vehiculo==1)
            {
                //movil de lujo
                marker_8.setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_car_marker));
            }else if(clase_vehiculo==2)
            {
                //movil de lujo
                marker_8.setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_lujo_marker));
            }else if(clase_vehiculo==4)
            {
                //maletero.
                marker_8.setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_vagoneta_marker));
            }else if(clase_vehiculo==7)
            {
                //moto
                marker_8.setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_mot_marker));
            }else if(clase_vehiculo==15)
            {
                //camioncito
                marker_8.setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_camioncito_marker));
            }else if(clase_vehiculo==18)
            {
                //grua
                marker_8.setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_camioncito_marker));
            }
        }
        else if(id.equals(cond_9)){
            fecha_9=fecha;
            marker_9.setVisible(true);
            marker_9.setRotation(rotacion);

            MarkerAnimation.animateMarkerToGB(marker_9, ubicacion, new LatLngInterpolator.Spherical());

            if(clase_vehiculo==1)
            {
                //movil de lujo
                marker_9.setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_car_marker));
            }else if(clase_vehiculo==2)
            {
                //movil de lujo
                marker_9.setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_lujo_marker));
            }else if(clase_vehiculo==4)
            {
                //maletero.
                marker_9.setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_vagoneta_marker));
            }else if(clase_vehiculo==7)
            {
                //moto
                marker_9.setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_mot_marker));
            }else if(clase_vehiculo==15)
            {
                //camioncito
                marker_9.setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_camioncito_marker));
            }else if(clase_vehiculo==18)
            {
                //grua
                marker_9.setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_camioncito_marker));
            }
        }
        else if(id.equals(cond_10)){
            fecha_10=fecha;
            marker_10.setVisible(true);
            marker_10.setRotation(rotacion);

            MarkerAnimation.animateMarkerToGB(marker_10, ubicacion, new LatLngInterpolator.Spherical());

            if(clase_vehiculo==1)
            {
                //movil de lujo
                marker_10.setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_car_marker));
            }else if(clase_vehiculo==2)
            {
                //movil de lujo
                marker_10.setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_lujo_marker));
            }else if(clase_vehiculo==4)
            {
                //maletero.
                marker_10.setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_vagoneta_marker));
            }else if(clase_vehiculo==7)
            {
                //moto
                marker_10.setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_mot_marker));
            }else if(clase_vehiculo==15)
            {
                //camioncito
                marker_10.setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_camioncito_marker));
            }else if(clase_vehiculo==18)
            {
                //grua
                marker_10.setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_camioncito_marker));
            }
        }

    }


    public void cargar_puntos_movil_segundo( double lat,double lon,int rotacion,String distancia,String id,String fecha, int moto)
    {


        LatLng ubicacion=new LatLng(lat,lon);

        if(cond_1.equals(id)&&fecha_1.equals(fecha_ultimo))
        {

        }else if(cond_2.equals(id)&&fecha_2.equals(fecha_ultimo))
        {

        }else if(cond_3.equals(id)&&fecha_3.equals(fecha_ultimo))
        {

        }else if(cond_4.equals(id)&&fecha_4.equals(fecha_ultimo))
        {

        }else if(cond_5.equals(id)&&fecha_5.equals(fecha_ultimo))
        {

        }else if(cond_6.equals(id)&&fecha_6.equals(fecha_ultimo))
        {

        }else if(cond_7.equals(id)&&fecha_7.equals(fecha_ultimo))
        {

        }else if(cond_8.equals(id)&&fecha_8.equals(fecha_ultimo))
        {

        }else if(cond_9.equals(id)&&fecha_9.equals(fecha_ultimo))
        {

        }else if(cond_10.equals(id)&&fecha_10.equals(fecha_ultimo))
        {

        }else if(fecha_1.equals(fecha_ultimo)==false){
            fecha_1=fecha;
            cond_1=id;
            marker_1.setVisible(true);
            marker_1.setRotation(rotacion);
            MarkerAnimation.animateMarkerToGB(marker_1, ubicacion, new LatLngInterpolator.Spherical());

            if(clase_vehiculo==1)
            {
                //movil de lujo
                marker_1.setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_car_marker));
            }else if(clase_vehiculo==2)
            {
                //movil de lujo
                marker_1.setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_lujo_marker));
            }else if(clase_vehiculo==4)
            {
                //maletero.
                marker_1.setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_vagoneta_marker));
            }else if(clase_vehiculo==7)
            {
                //moto
                marker_1.setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_mot_marker));
            }else if(clase_vehiculo==15)
            {
                //camioncito
                marker_1.setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_camioncito_marker));
            }else if(clase_vehiculo==18)
            {
                //grua
                marker_1.setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_camioncito_marker));
            }
        }
        else if(fecha_2.equals(fecha_ultimo)==false){
            fecha_2=fecha;
            cond_2=id;
            marker_2.setVisible(true);
            marker_2.setRotation(rotacion);

            MarkerAnimation.animateMarkerToGB(marker_2, ubicacion, new LatLngInterpolator.Spherical());

            if(clase_vehiculo==1)
            {
                //movil de lujo
                marker_2.setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_car_marker));
            }else if(clase_vehiculo==2)
            {
                //movil de lujo
                marker_2.setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_lujo_marker));
            }else if(clase_vehiculo==4)
            {
                //maletero.
                marker_2.setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_vagoneta_marker));
            }else if(clase_vehiculo==7)
            {
                //moto
                marker_2.setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_mot_marker));
            }else if(clase_vehiculo==15)
            {
                //camioncito
                marker_2.setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_camioncito_marker));
            }else if(clase_vehiculo==18)
            {
                //grua
                marker_2.setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_camioncito_marker));
            }
        }
        else  if(fecha_3.equals(fecha_ultimo)==false){
            fecha_3=fecha;
            cond_3=id;
            marker_3.setVisible(true);
            marker_3.setRotation(rotacion);

            MarkerAnimation.animateMarkerToGB(marker_3, ubicacion, new LatLngInterpolator.Spherical());

            if(clase_vehiculo==1)
            {
                //movil de lujo
                marker_3.setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_car_marker));
            }else if(clase_vehiculo==2)
            {
                //movil de lujo
                marker_3.setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_lujo_marker));
            }else if(clase_vehiculo==4)
            {
                //maletero.
                marker_3.setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_vagoneta_marker));
            }else if(clase_vehiculo==7)
            {
                //moto
                marker_3.setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_mot_marker));
            }else if(clase_vehiculo==15)
            {
                //camioncito
                marker_3.setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_camioncito_marker));
            }else if(clase_vehiculo==18)
            {
                //grua
                marker_3.setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_camioncito_marker));
            }
        }
        else if(fecha_4.equals(fecha_ultimo)==false){
            fecha_4=fecha;
            cond_4=id;
            marker_4.setVisible(true);
            marker_4.setRotation(rotacion);

            MarkerAnimation.animateMarkerToGB(marker_4, ubicacion, new LatLngInterpolator.Spherical());

            if(clase_vehiculo==1)
            {
                //movil de lujo
                marker_4.setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_car_marker));
            }else if(clase_vehiculo==2)
            {
                //movil de lujo
                marker_4.setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_lujo_marker));
            }else if(clase_vehiculo==4)
            {
                //maletero.
                marker_4.setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_vagoneta_marker));
            }else if(clase_vehiculo==7)
            {
                //moto
                marker_4.setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_mot_marker));
            }else if(clase_vehiculo==15)
            {
                //camioncito
                marker_4.setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_camioncito_marker));
            }else if(clase_vehiculo==18)
            {
                //grua
                marker_4.setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_camioncito_marker));
            }
        }
        else if(fecha_5.equals(fecha_ultimo)==false){
            fecha_5=fecha;
            cond_5=id;
            marker_5.setVisible(true);
            marker_5.setRotation(rotacion);

            MarkerAnimation.animateMarkerToGB(marker_5, ubicacion, new LatLngInterpolator.Spherical());

            if(clase_vehiculo==1)
            {
                //movil de lujo
                marker_5.setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_car_marker));
            }else if(clase_vehiculo==2)
            {
                //movil de lujo
                marker_5.setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_lujo_marker));
            }else if(clase_vehiculo==4)
            {
                //maletero.
                marker_5.setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_vagoneta_marker));
            }else if(clase_vehiculo==7)
            {
                //moto
                marker_5.setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_mot_marker));
            }else if(clase_vehiculo==15)
            {
                //camioncito
                marker_5.setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_camioncito_marker));
            }else if(clase_vehiculo==18)
            {
                //grua
                marker_5.setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_camioncito_marker));
            }
        }
        else if(fecha_6.equals(fecha_ultimo)==false){
            fecha_6=fecha;
            cond_6=id;
            marker_6.setVisible(true);
            marker_6.setRotation(rotacion);

            MarkerAnimation.animateMarkerToGB(marker_6, ubicacion, new LatLngInterpolator.Spherical());

            if(clase_vehiculo==1)
            {
                //movil de lujo
                marker_6.setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_car_marker));
            }else if(clase_vehiculo==2)
            {
                //movil de lujo
                marker_6.setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_lujo_marker));
            }else if(clase_vehiculo==4)
            {
                //maletero.
                marker_6.setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_vagoneta_marker));
            }else if(clase_vehiculo==7)
            {
                //moto
                marker_6.setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_mot_marker));
            }else if(clase_vehiculo==15)
            {
                //camioncito
                marker_6.setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_camioncito_marker));
            }else if(clase_vehiculo==18)
            {
                //grua
                marker_6.setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_camioncito_marker));
            }
        }
        else if(fecha_7.equals(fecha_ultimo)==false){
            fecha_7=fecha;
            cond_7=id;
            marker_7.setVisible(true);
            marker_7.setRotation(rotacion);

            MarkerAnimation.animateMarkerToGB(marker_7, ubicacion, new LatLngInterpolator.Spherical());

            if(clase_vehiculo==1)
            {
                //movil de lujo
                marker_7.setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_car_marker));
            }else if(clase_vehiculo==2)
            {
                //movil de lujo
                marker_7.setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_lujo_marker));
            }else if(clase_vehiculo==4)
            {
                //maletero.
                marker_7.setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_vagoneta_marker));
            }else if(clase_vehiculo==7)
            {
                //moto
                marker_7.setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_mot_marker));
            }else if(clase_vehiculo==15)
            {
                //camioncito
                marker_7.setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_camioncito_marker));
            }else if(clase_vehiculo==18)
            {
                //grua
                marker_7.setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_camioncito_marker));
            }
        }
        else if(fecha_8.equals(fecha_ultimo)==false){
            fecha_8=fecha;
            cond_8=id;
            marker_8.setVisible(true);
            marker_8.setRotation(rotacion);

            MarkerAnimation.animateMarkerToGB(marker_8, ubicacion, new LatLngInterpolator.Spherical());

            if(clase_vehiculo==1)
            {
                //movil de lujo
                marker_8.setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_car_marker));
            }else if(clase_vehiculo==2)
            {
                //movil de lujo
                marker_8.setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_lujo_marker));
            }else if(clase_vehiculo==4)
            {
                //maletero.
                marker_8.setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_vagoneta_marker));
            }else if(clase_vehiculo==7)
            {
                //moto
                marker_8.setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_mot_marker));
            }else if(clase_vehiculo==15)
            {
                //camioncito
                marker_8.setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_camioncito_marker));
            }else if(clase_vehiculo==18)
            {
                //grua
                marker_8.setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_camioncito_marker));
            }
        }
        else if(fecha_9.equals(fecha_ultimo)==false){
            fecha_9=fecha;
            cond_9=id;
            marker_9.setVisible(true);
            marker_9.setRotation(rotacion);

            MarkerAnimation.animateMarkerToGB(marker_9, ubicacion, new LatLngInterpolator.Spherical());

            if(clase_vehiculo==1)
            {
                //movil de lujo
                marker_9.setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_car_marker));
            }else if(clase_vehiculo==2)
            {
                //movil de lujo
                marker_9.setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_lujo_marker));
            }else if(clase_vehiculo==4)
            {
                //maletero.
                marker_9.setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_vagoneta_marker));
            }else if(clase_vehiculo==7)
            {
                //moto
                marker_9.setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_mot_marker));
            }else if(clase_vehiculo==15)
            {
                //camioncito
                marker_9.setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_camioncito_marker));
            }else if(clase_vehiculo==18)
            {
                //grua
                marker_9.setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_camioncito_marker));
            }
        }
        else if(fecha_10.equals(fecha_ultimo)==false){
            fecha_10=fecha;
            cond_10=id;
            marker_10.setVisible(true);
            marker_10.setRotation(rotacion);

            MarkerAnimation.animateMarkerToGB(marker_10, ubicacion, new LatLngInterpolator.Spherical());

            if(clase_vehiculo==1)
            {
                //movil de lujo
                marker_10.setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_car_marker));
            }else if(clase_vehiculo==2)
            {
                //movil de lujo
                marker_10.setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_lujo_marker));
            }else if(clase_vehiculo==4)
            {
                //maletero.
                marker_10.setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_vagoneta_marker));
            }else if(clase_vehiculo==7)
            {
                //moto
                marker_10.setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_mot_marker));
            }else if(clase_vehiculo==15)
            {
                //camioncito
                marker_10.setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_camioncito_marker));
            }else if(clase_vehiculo==18)
            {
                //grua
                marker_10.setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_camioncito_marker));
            }
        }

    }




    public void ocultar_conductores_no_activos()
    {
        if(fecha_1.equals(fecha_ultimo)==false){
            marker_1.setVisible(false);
        }
        if(fecha_2.equals(fecha_ultimo)==false){
            marker_2.setVisible(false);
        }
        if(fecha_3.equals(fecha_ultimo)==false){
            marker_3.setVisible(false);
        }
        if(fecha_4.equals(fecha_ultimo)==false){
            marker_4.setVisible(false);
        }
        if(fecha_5.equals(fecha_ultimo)==false){
            marker_5.setVisible(false);
        }
        if(fecha_6.equals(fecha_ultimo)==false){
            marker_6.setVisible(false);
        }
        if(fecha_7.equals(fecha_ultimo)==false){
            marker_7.setVisible(false);
        }
        if(fecha_8.equals(fecha_ultimo)==false){
            marker_8.setVisible(false);
        }
        if(fecha_9.equals(fecha_ultimo)==false){
            marker_9.setVisible(false);
        }
        if(fecha_10.equals(fecha_ultimo)==false){
            marker_10.setVisible(false);
        }

    }



    private void servicio_volley_lista_empresa() {

        try {
            String v_url= getString(R.string.servidor)+"frmTaxi.php?opcion=get_empresa";

            JSONObject jsonParam= new JSONObject();
            if (queue == null) {
                queue = Volley.newRequestQueue(this);
            }


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

                                    Item_empresa adaptador = new Item_empresa(getApplicationContext(), Calcular_tarifa_confirmar.this,cEmpresas);
                                    lv_lista_empresas.setAdapter(adaptador);

                                } else  {
                                    cEmpresas.clear();
                                    Item_empresa adaptador = new Item_empresa(getApplicationContext(),Calcular_tarifa_confirmar.this,cEmpresas);
                                    lv_lista_empresas.setAdapter(adaptador);
                                }

                                //...final de final....................





                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

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


    public void crear_puntos_conductor()
    {
        try {
            LatLng punto = new LatLng(latitud_inicio,longitud_inicio);
            marker_1=mMap.addMarker(new MarkerOptions()
                    .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_car_marker))
                    .position(punto)
                    .anchor((float)0.5,(float)0.8)
                    .flat(true)
                    .rotation(0)
                    .visible(false));
            marker_2=mMap.addMarker(new MarkerOptions()
                    .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_car_marker))
                    .position(punto)
                    .anchor((float)0.5,(float)0.8)
                    .flat(true)
                    .rotation(0)
                    .visible(false));
            marker_3=mMap.addMarker(new MarkerOptions()
                    .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_car_marker))
                    .position(punto)
                    .anchor((float)0.5,(float)0.8)
                    .flat(true)
                    .rotation(0)
                    .visible(false));
            marker_4=mMap.addMarker(new MarkerOptions()
                    .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_car_marker))
                    .position(punto)
                    .anchor((float)0.5,(float)0.8)
                    .flat(true)
                    .rotation(0)
                    .visible(false));
            marker_5=mMap.addMarker(new MarkerOptions()
                    .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_car_marker))
                    .position(punto)
                    .anchor((float)0.5,(float)0.8)
                    .flat(true)
                    .rotation(0)
                    .visible(false));
            marker_6=mMap.addMarker(new MarkerOptions()
                    .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_car_marker))
                    .position(punto)
                    .anchor((float)0.5,(float)0.8)
                    .flat(true)
                    .rotation(0)
                    .visible(false));
            marker_7=mMap.addMarker(new MarkerOptions()
                    .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_car_marker))
                    .position(punto)
                    .anchor((float)0.5,(float)0.8)
                    .flat(true)
                    .rotation(0)
                    .visible(false));
            marker_8=mMap.addMarker(new MarkerOptions()
                    .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_car_marker))
                    .position(punto)
                    .anchor((float)0.5,(float)0.8)
                    .flat(true)
                    .rotation(0)
                    .visible(false));
            marker_9=mMap.addMarker(new MarkerOptions()
                    .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_car_marker))
                    .position(punto)
                    .anchor((float)0.5,(float)0.8)
                    .flat(true)
                    .rotation(0)
                    .visible(false));
            marker_10=mMap.addMarker(new MarkerOptions()
                    .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_car_marker))
                    .position(punto)
                    .anchor((float)0.5,(float)0.8)
                    .flat(true)
                    .rotation(0)
                    .visible(false));

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }













    private void createRoute() {
        if (bangaloreRoute == null) {
            bangaloreRoute = new ArrayList<>();
        } else {
            bangaloreRoute.clear();
        }

        bangaloreRoute.add(new LatLng(-17.3408353,-63.2621419));
        bangaloreRoute.add(new LatLng(12.922294704121231, 77.61939525604248));
        bangaloreRoute.add(new LatLng(12.924637088068884, 77.6180648803711));
        bangaloreRoute.add(new LatLng(12.925557304321782, 77.6200819015503));
        bangaloreRoute.add(new LatLng(12.927104933097784, 77.62081146240234));
        bangaloreRoute.add(new LatLng(12.928234277770715, 77.62111186981201));
        bangaloreRoute.add(new LatLng(12.92990737159723, 77.6218843460083));
        bangaloreRoute.add(new LatLng(12.9337554448302, 77.62342929840088));
        bangaloreRoute.add(new LatLng(12.9346338010532, 77.62390136718749));
        bangaloreRoute.add(new LatLng(12.935177543831987, 77.62437343597412));
        bangaloreRoute.add(new LatLng(12.934487408564122, 77.62561798095703));
        bangaloreRoute.add(new LatLng(12.934320102757125, 77.62589693069457));
        bangaloreRoute.add(new LatLng(12.933860011209374, 77.62572526931763));
        bangaloreRoute.add(new LatLng(12.934550148212828, 77.62460947036743));
        bangaloreRoute.add(new LatLng(12.933379005502244, 77.62398719787598));
        bangaloreRoute.add(new LatLng(12.933065305628435, 77.62390136718749));
    }

}

