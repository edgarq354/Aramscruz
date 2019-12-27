package com.elisoft.aramscruz.registro_inicio_sesion;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AlertDialog.Builder;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.elisoft.aramscruz.Menu_usuario;
import com.elisoft.aramscruz.R;
import com.elisoft.aramscruz.Registrar_nombre_completo;
import com.elisoft.aramscruz.Suceso;
import com.elisoft.aramscruz.notificaciones.SharedPrefManager;

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


public class Confirmar_sms extends AppCompatActivity implements View.OnClickListener {
    private String celular;
    Button codeInputButton;
    TextView enviar_mensaje;
    EditText inputCode;
    TextView mensaje;
    String token="",imei="";
    JSONArray perfil;
    Suceso suceso;
    ProgressDialog pDialog;


    private static final String TAG = "VerificationActivity";

    private boolean mShouldFallback = true;
    private static final String[] SMS_PERMISSIONS = { android.Manifest.permission.INTERNET,
            android.Manifest.permission.READ_SMS,
            android.Manifest.permission.RECEIVE_SMS,
            android.Manifest.permission.ACCESS_NETWORK_STATE };
    ProgressBar cargando;
    Handler handle=new Handler();

    int i=0;
    private boolean mIsSmsVerification;
    public static final String SMS = "sms";
    private String mPhoneNumber;
    TextView tv_tiempo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmar_sms);
        mensaje=(TextView)findViewById(R.id.mensaje);
        codeInputButton=(Button)findViewById(R.id.codeInputButton);
        inputCode=(EditText)findViewById(R.id.inputCode);
        cargando=(ProgressBar)findViewById(R.id.cargando);
        enviar_mensaje=(TextView)findViewById(R.id.enviar_mensaje);
        tv_tiempo=(TextView)findViewById(R.id.tv_tiempo);


        inputCode.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (verificar_codigo(s)) {
                    codeInputButton.setEnabled(true);
                    inputCode.setTextColor(Color.BLACK);
                } else {
                    codeInputButton.setEnabled(false);
                    inputCode.setTextColor(Color.RED);
                }
            }
        });


        try{
            Bundle bundle=getIntent().getExtras();
            celular=""+bundle.getString("celular");
            mPhoneNumber = "+591"+celular;
            final String method ="sms";
            mIsSmsVerification = method.equalsIgnoreCase("sms");
            progress_en_proceso();
            enviar_sms();
            TextView tv_titulo=(TextView)findViewById(R.id.tv_titulo);
            tv_titulo.setText("Verificar "+mPhoneNumber);

        }catch (Exception e)
        {
            finish();
        }


        codeInputButton.setOnClickListener(this);
        enviar_mensaje.setOnClickListener(this);




    }

    private boolean verificar_codigo(CharSequence s) {
        boolean sw=false;
        try{

            if(s.toString().trim().length()>=4)
            {
                sw=true;
            }
        }catch (Exception e)
        {
            sw=false;
        }
        return sw;
    }
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.codeInputButton) {
                verificar_codigo();

            /*
            token = SharedPrefManager.getInstance(getApplicationContext()).getDeviceToken();
            if (token != null && token != "") {

                Servicio hilo_cargar = new Servicio();
                hilo_cargar.execute(getString(R.string.servidor) + "frmUsuario.php?opcion=iniciar_sesion_con_celular", "1", celular, token);// parametro que recibe el doinbackground
            }
            else
            {
                AlertDialog.Builder dialogo1 = new AlertDialog.Builder(getApplicationContext());
                dialogo1.setTitle("Vamos a verificar el número de telefono");
                dialogo1.setMessage("No tiene token de acceso.  \n por favor vuelva a intentar mas tarde. \n para generar el Token ncesita tener instalado el Google Play Service.");
                dialogo1.setCancelable(false);
                dialogo1.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {

                    }
                });
                dialogo1.show();
            }
            */
        }
        else if(v.getId()==R.id.enviar_mensaje)
        {
            enviar_mensaje.setVisibility(View.INVISIBLE);
            progress_en_proceso();

            enviar_sms();
        }
    }



    //USUARIO
    public void verificar_codigo()
    {
        token = SharedPrefManager.getInstance(getApplicationContext()).getDeviceToken();

        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(Confirmar_sms.this, android.Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            verificar_permiso_imei();
        }else{
            imei=telephonyManager.getDeviceId();
        }

        if (token != null && token != "") {
            servicio_iniciar_sesion_con_celular(celular, token,imei,inputCode.getText().toString().trim());
        }
        else
        {
            AlertDialog.Builder dialogo1 = new AlertDialog.Builder(getApplicationContext());
            dialogo1.setTitle("Vamos a verificar el número de telefono");
            dialogo1.setMessage("No tiene token de acceso.  \n por favor vuelva a intentar mas tarde. \n para generar el Token ncesita tener instalado el Google Play Service.");
            dialogo1.setCancelable(false);
            dialogo1.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogo1, int id) {

                }
            });
            dialogo1.show();
        }
    }

    public void enviar_sms()
    {
           servicio_enviar_mensaje(celular);
    }


    //USUARIO
    public void obtener_codigo()
    {
        try {

            Uri smsUri = Uri.parse("content://sms/inbox");
            Cursor cursor = getContentResolver().query(smsUri, null, null, null, null);
             /* Moving To First */
            if (!cursor.moveToFirst()) { /* false = cursor is empty */
                return;
            }
            for (int k = 0; k < cursor.getColumnCount() && !cursor.getString(2).equals("+46769446575"); k++) {
                cursor.moveToNext();
            }
            if (cursor.getString(2).equals("+46769446575")) {
                inputCode.setText(obtener_codigo(cursor.getString(12)));
            }
            cursor.close();
        }catch (Exception e)
        {

        }
    }
    //USUARIO
    public String obtener_codigo(String texto)
    {String codigo="";
        for (int i=0;i<texto.length();i++)
        {
            if(es_numero(String.valueOf(texto.charAt(i))))
            {
                codigo+=texto.charAt(i);
            }
        }
        return codigo;
    }
    //USUARIO
    public boolean es_numero(String numero)
    {
        try{
            Long.parseLong(numero);
        }catch (Exception e)
        {
            return false;
        }
        return true;
    }

    //USUARIO
    public  void progress_en_proceso()
    {

        i=0;
        new Thread(new Runnable() {
            @Override
            public void run() {

                while (i<59)
                {
                    i=i+1;

                    handle.post(new Runnable() {
                        @Override
                        public void run() {
                            cargando.setProgress(i);

                            if(i==60)
                            {
                                tv_tiempo.setText("01:00");
                            }
                            else
                            {
                                tv_tiempo.setText("00:"+i);
                            }

                            if( i==10||i==30||i==50)
                            {
                                obtener_codigo();

                            }
                            if(i>=55)
                            {
                                enviar_mensaje.setVisibility(View.VISIBLE);
                            }

                        }
                    });
                    try{

                        Thread.sleep(1000);
                    }catch (InterruptedException e)
                    {
                        e.printStackTrace();
                    }
                }
            }
        }).start();


    }
    //USUARIO
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }






    //servicio de verificar si ya esta registrado el cellular.

    public void cargar_datos(String nombre, String apellido, String email, String celular, String id, String codigo)
    {
        SharedPreferences usuario=getSharedPreferences("perfil",MODE_PRIVATE);
        SharedPreferences.Editor editar=usuario.edit();
        editar.putString("nombre",nombre);
        editar.putString("apellido",apellido);
        editar.putString("email",email);
        editar.putString("celular",celular);
        editar.putString("id_usuario",id);
        editar.putString("codigo",codigo);
        editar.putString("imei",imei);
        editar.putString("login_usuario","1");
        editar.commit();
    }
    public void saltar_principal()
    {
        startActivity(new Intent(this, Menu_usuario.class));
        finish();
    }
    private void iniciar_sesion() {
        SharedPreferences usuario=getSharedPreferences("perfil",MODE_PRIVATE);
        if(usuario.getString("id_usuario","").equals("")==false  && usuario.getString("id_usuario","").equals("null")==false) {

            saltar_principal();

        }
    }
    public void mensaje(String mensaje)
    {
        Toast toast = Toast.makeText(this,mensaje, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast.show();
    }

    //servicio de verificar si ya esta registrado el cellular.
    public void servicio_iniciar_sesion_con_celular(final String celular,
                                                    String token,
                                                    String imei,
                                                    String codigo)
    {
        pDialog = new ProgressDialog(Confirmar_sms.this);
        pDialog.setTitle(getString(R.string.app_name));
        pDialog.setMessage("Verificando el Codigo");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(true);
        pDialog.show();


        try {
            JSONObject jsonParam= new JSONObject();
            jsonParam.put("celular", celular);
            jsonParam.put("token", token);
            jsonParam.put("imei", imei);
            jsonParam.put("codigo", codigo);

            String url=getString(R.string.servidor) + "frmUsuario.php?opcion=iniciar_sesion_con_celular";
            RequestQueue queue = Volley.newRequestQueue(this);


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
                                    JSONArray dato=respuestaJSON.getJSONArray("perfil");
                                    String snombre= dato.getJSONObject(0).getString("nombre");
                                    String sapellido=dato.getJSONObject(0).getString("apellido") ;
                                    String semail= dato.getJSONObject(0).getString("correo") ;
                                    String scelular= dato.getJSONObject(0).getString("celular") ;
                                    String sid= dato.getJSONObject(0).getString("id") ;
                                    String scodigo= dato.getJSONObject(0).getString("codigo") ;
                                    cargar_datos(snombre,sapellido,semail,scelular,sid,scodigo);

                                    //final
                                    iniciar_sesion();

                                }else if(suceso.getSuceso().equals("3")) {
                                    Intent registrar=new Intent(getApplicationContext(),Registrar_nombre_completo.class);
                                    registrar.putExtra("celular",celular);
                                    startActivity(registrar);
                                    finish();
                                } else  {
                                    mensaje_error(suceso.getMensaje());
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
            pDialog.cancel();
            mensaje_error("Falla en tu conexión a Internet.");
        }
    }

    public void servicio_enviar_mensaje(String celular)
    {
        pDialog = new ProgressDialog(Confirmar_sms.this);
        pDialog.setTitle(getString(R.string.app_name));
        pDialog.setMessage("Enviando sms de verificación.");
        pDialog.setIndeterminate(true);
        pDialog.setCancelable(false);
        pDialog.show();

        try {
            JSONObject jsonParam= new JSONObject();
            jsonParam.put("celular", celular);

            String url=getString(R.string.servidor) + "frmUsuario.php?opcion=enviar_sms";
            RequestQueue queue = Volley.newRequestQueue(this);


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
                                    obtener_codigo();

                                } else  {
                                    mensaje_error(suceso.getMensaje());
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
            pDialog.cancel();
            mensaje_error("Falla en tu conexión a Internet.");
        }
    }


    public void mensaje_error(String mensaje)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.app_name));
        builder.setMessage(mensaje);
        builder.setPositiveButton("OK", null);
        builder.create();
        builder.show();
    }

    public void verificar_permiso_imei()
    {
        final String[] PERMISSIONS = { android.Manifest.permission.INTERNET,
                android.Manifest.permission.READ_PHONE_STATE,
                android.Manifest.permission.ACCESS_NETWORK_STATE };

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.READ_PHONE_STATE)) {
            //YA LO CANCELE Y VOUELVO A PERDIR EL PERMISO.

            AlertDialog.Builder dialogo1 = new AlertDialog.Builder(this);
            dialogo1.setTitle("Atención!");
            dialogo1.setMessage("Debes otorgar permisos de acceso al ID del Telefono por tema de Seguridad.");
            dialogo1.setCancelable(false);
            dialogo1.setPositiveButton("Solicitar permiso", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogo1, int id) {
                    dialogo1.cancel();
                    ActivityCompat.requestPermissions(Confirmar_sms.this,
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
            ActivityCompat.requestPermissions(Confirmar_sms.this,
                    PERMISSIONS,
                    1);
        }
    }


}



