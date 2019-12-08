package com.elisoft.aramscruz.tarifario;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.elisoft.aramscruz.R;
import com.elisoft.aramscruz.Suceso;
import com.elisoft.aramscruz.notificaciones.SharedPrefManager;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class Calcular_Tarifa extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    String[] direccion = {"",""};
    JSONObject rutas=null;
    LatLngBounds AUSTRALIA;
    double latitud_inicio,longitud_inicio,latitud_fin,longitud_fin;
    TextView tv_punto_inicio,tv_punto_fin,tv_;
    ProgressDialog pDialog;
    Suceso suceso;
    TextView tv_monto_distancia,tv_monto_tiempo,tv_tarifa_normal,tv_tarifa_de_lujo,tv_tarifa_con_aire,tv_tarifa_maletero,tv_tarifa_con_pedido, tv_tarifa_con_reserva,tv_tarifa_moto,tv_tarifa_moto_pedido;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calcular__tarifa);
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

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);




        try{
            Bundle bundle=getIntent().getExtras();
            latitud_inicio=bundle.getDouble("latitud_inicio");
            longitud_inicio=bundle.getDouble("longitud_inicio");
            latitud_fin=bundle.getDouble("latitud_fin");
            longitud_fin=bundle.getDouble("longitud_fin");
            marcar_ruta();

             servicio_calcular_tarifa( String.valueOf(latitud_inicio), String.valueOf(longitud_inicio),String.valueOf(latitud_fin),String.valueOf(longitud_fin));

        }catch (Exception e){

        }

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);



                String direccion1 =obtener_direccion( latitud_inicio,longitud_inicio);
                String direccion2 =obtener_direccion( latitud_fin,longitud_fin);
                tv_punto_inicio.setText(String.valueOf(direccion1));
                tv_punto_fin.setText(String.valueOf(direccion2));

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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

            Marker marker1= this.mMap.addMarker(new MarkerOptions()
                    .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_punto_inicio_1))
                    .anchor((float) 0.5, (float) 0.8)
                    .flat(true)
                    .position(inicio)
                    .title("Inicio"));

        } catch (Exception e) {
        }

        try {

            Marker marker= this.mMap.addMarker(new MarkerOptions()
                    .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_punto_fin_1))
                    .anchor((float) 0.5, (float) 0.8)
                    .flat(true)
                    .position(fin)
                    .title("fin"));

        } catch (Exception e) {

        }
        marcar_ruta();





// Set the camera to the greatest possible zoom level that includes the
// bounds

        //agregaranimacion al mover la camara...
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(inicio)      // Sets the center of the map to Mountain View
                .zoom(12)                   // Sets the zoom
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

    //servicio calcular tarifa
    private void servicio_calcular_tarifa(String latitud_inicio,String longitud_inicio,String latitud_fin,String longitud_fin) {
        pDialog = new ProgressDialog(Calcular_Tarifa.this);
        pDialog.setTitle(getString(R.string.app_name));
        pDialog.setMessage("Calculando la tarifa");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();
        try {

            String token= SharedPrefManager.getInstance(this).getDeviceToken();

            JSONObject jsonParam= new JSONObject();
            jsonParam.put("latitud_inicio",latitud_inicio);
            jsonParam.put("longitud_inicio",longitud_inicio);
            jsonParam.put("latitud_fin",latitud_fin);
            jsonParam.put("longitud_fin",longitud_fin);
            jsonParam.put("token", token);
            String url=getString(R.string.servidor) + "frmCarrera.php?opcion=calcular_tarifa";
            RequestQueue queue = Volley.newRequestQueue(this);


            JsonObjectRequest myRequest= new JsonObjectRequest(
                    Request.Method.POST,
                    url,
                    jsonParam,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject respuestaJSON) {
                            String metros,minuto,normal,de_lujo,con_aire,maletero,pedido,reserva,moto,moto_pedido;
                            pDialog.cancel();

                            try {

                                suceso= new Suceso(respuestaJSON.getString("suceso"),respuestaJSON.getString("mensaje"));

                                if (suceso.getSuceso().equals("1")) {

                                    metros=respuestaJSON.getString("metros");
                                    minuto=respuestaJSON.getString("minutos");
                                    normal=respuestaJSON.getString("normal");
                                    de_lujo=respuestaJSON.getString("de_lujo");
                                    con_aire=respuestaJSON.getString("con_aire");
                                    maletero=respuestaJSON.getString("maletero");
                                    pedido=respuestaJSON.getString("pedido");
                                    reserva=respuestaJSON.getString("reserva");
                                    moto=respuestaJSON.getString("moto");
                                    moto_pedido=respuestaJSON.getString("moto_pedido");

                                    ///----final
                                    int minuto1=Integer.parseInt(minuto);
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
                                    tv_tarifa_normal.setText("Bs. "+ normal);
                                    tv_tarifa_de_lujo.setText("Bs. "+ de_lujo);
                                    tv_tarifa_con_aire.setText("Bs. "+ con_aire);
                                    tv_tarifa_maletero.setText("Bs. "+ maletero);
                                    tv_tarifa_con_pedido.setText("Bs. "+ pedido);
                                    tv_tarifa_con_reserva.setText("Bs. "+ reserva);
                                    tv_tarifa_moto.setText("Bs. "+moto);
                                    tv_tarifa_moto_pedido.setText("Bs. "+ moto_pedido);

                                }else {
                                    mensaje_error(suceso.getMensaje());
                                }

                            } catch (JSONException e) {
                                pDialog.cancel();
                                e.printStackTrace();
                            }

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    pDialog.cancel();
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

    //servicio taxi ruta
    private void servicio_taxi_ruta() {

        try {

            String token= SharedPrefManager.getInstance(this).getDeviceToken();

            JSONObject jsonParam= new JSONObject();
            jsonParam.put("token", token);
            String url="https://maps.googleapis.com/maps/api/directions/json?origin=" + latitud_inicio + "," + longitud_inicio + "&destination=" + latitud_fin + "," + longitud_fin + "&mode=driving&key=AIzaSyB1h4N5nfpkF1Hg30P88c_1MvH9qG9Tcvs";
            RequestQueue queue = Volley.newRequestQueue(this);


            JsonObjectRequest myRequest= new JsonObjectRequest(
                    Request.Method.POST,
                    url,
                    jsonParam,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject respuestaJSON) {


                            try {
                                rutas= new JSONObject(respuestaJSON.toString());
                            //final
                                dibujar_ruta(rutas);

                            } catch (JSONException e) {
                                mensaje_error("No pudimos conectarnos al servidor.\nVuelve a intentarlo.");
                                e.printStackTrace();
                            }

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    mensaje_error("No pudimos conectarnos al servidor.\nVuelve a intentarlo.");
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
            mensaje_error("No pudimos conectarnos al servidor.\nVuelve a intentarlo.");
        }

    }





    public String formatearMinutosAHoraMinuto(int minutos) {
        String formato = "%02d:%02d";
        long horasReales = TimeUnit.MINUTES.toHours(minutos);
        long minutosReales = TimeUnit.MINUTES.toMinutes(minutos) - TimeUnit.HOURS.toMinutes(TimeUnit.MINUTES.toHours(minutos));
        return String.format(formato, horasReales, minutosReales);
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

                        }
                    }

                    tiempo=(String)((JSONObject)((JSONObject)jLegs.get(j)).get("duration")).get("text");
                }
            }




            mMap.addPolyline(polylineOptions.width(8).color(Color.BLACK));

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


}
