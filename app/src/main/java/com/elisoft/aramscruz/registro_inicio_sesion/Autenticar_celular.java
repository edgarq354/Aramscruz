package com.elisoft.aramscruz.registro_inicio_sesion;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.elisoft.aramscruz.Condiciones_terminos;
import com.elisoft.aramscruz.Constants;
import com.elisoft.aramscruz.Menu_usuario;
import com.elisoft.aramscruz.R;
import com.elisoft.aramscruz.Suceso;
import com.elisoft.aramscruz.notificaciones.SharedPrefManager;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;

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
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Autenticar_celular extends AppCompatActivity implements View.OnClickListener, GoogleApiClient.OnConnectionFailedListener{
    Button continuar;
    EditText celular;




    ProgressDialog pDialog;
    Suceso suceso;

    String imei="";


    AccessTokenTracker accessTokenTracker;
    private LoginButton loginButton;
    private SignInButton bt_google;
    public  static  final int SIGN_IN_CODE=777;
    private GoogleApiClient googleApiClient;
    private CallbackManager callbackManager;
    String s_nombre,s_apellido,s_email;

    

    @Override
    protected void onStart() {
        boolean sw=estaConectado();

        super.onStart();
    }

    private static final String[] SMS_PERMISSIONS = { Manifest.permission.INTERNET,
            Manifest.permission.ACCESS_NETWORK_STATE };
    @Override
    public void onClick(View v) {
        if(v.getId()== R.id.continuar)
        {
            Intent confirmar=new Intent(getApplicationContext(),Confirmar_sms.class);
            confirmar.putExtra("celular",celular.getText().toString());
            startActivity(confirmar);
            finish();
            finish();
        }else if(v.getId()==R.id.tv_condiciones){
            startActivity(new Intent(this,Condiciones_terminos.class));
        }
    }
    //USUARIO
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_autenticar_celular);
        continuar=(Button)findViewById(R.id.continuar);
        celular=(EditText)findViewById(R.id.celular);





        continuar.setEnabled(false);


        continuar.setOnClickListener(this);

        celular.addTextChangedListener(new TextWatcher() {

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
                if (verificar_celular(s)) {
                    continuar.setEnabled(true);
                    celular.setTextColor(Color.BLACK);
                } else {
                    continuar.setEnabled(false);
                    celular.setTextColor(Color.RED);
                }
            }
        });





        if (AccessToken.getCurrentAccessToken() != null) {
            LoginManager.getInstance().logOut();
        }

        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.elisoft.aramscruz",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                //Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }


        callbackManager = CallbackManager.Factory.create();
        loginButton = (LoginButton) findViewById(R.id.loginButton);




        loginButton.setReadPermissions(Arrays.asList("public_profile, email"));

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                //obtenemos todos los datos del usuario
                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {

                                String name = "";
                                String email = "";
                                String id_facebook = "";
                                try{
                                    id_facebook=object.getString("id");
                                }catch (Exception e)
                                {e.printStackTrace();}
                                try {
                                    name = object.getString("name");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                try {
                                    email = object.getString("email");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                s_nombre=obtener_nombre(name);
                                s_apellido=obtener_apellido(name);
                                s_email=email;
                                registrar_usuario(s_nombre,s_apellido,s_email,id_facebook,0);

                            }
                        });

                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email");
                request.setParameters(parameters);
                request.executeAsync();


            }

            @Override
            public void onCancel() {
                Toast.makeText(getApplicationContext(), "Se ha cancelado el login", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(getApplicationContext(), "Se produjo un Error al autentificar." + error, Toast.LENGTH_LONG).show();
            }
        });


        //INICIO DE LA AUTENTIFICACION CON GOOGLE
        GoogleSignInOptions gso=new  GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this,this)
                .addApi(Auth.GOOGLE_SIGN_IN_API,gso)
                .build();
        bt_google=(SignInButton)findViewById(R.id.bt_google);
        bt_google.setSize(SignInButton.SIZE_WIDE);
        bt_google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
                startActivityForResult(intent,SIGN_IN_CODE);
            }
        });


        verificar_todos_los_permisos();


    }



    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        int per=0;
        switch (requestCode) {
            case 1: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 ) {
                    for (int i=0;i<grantResults.length;i++){
                        if(grantResults[i] == PackageManager.PERMISSION_GRANTED){
                            per++;
                        }
                    }

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {

                    finish();
                }

                if(per<grantResults.length){
                    finish();
                }else{
                    //tiene todos los permisos...
                  //  Intent intent = new Intent(Autenticar_celular.this, Servicio_guardar_contacto_empresa.class);
                  //  intent.setAction(Constants.ACTION_RUN_ISERVICE);
                  //  startService(intent);
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }



    private void registrar_usuario(String s_nombre, String s_apellido, String s_email, String s_id_autentificacion,int ir_google) {

        if( s_nombre.trim().length()>=3  && s_apellido.trim().length()>=3) {
            final String token = SharedPrefManager.getInstance(this).getDeviceToken();

            TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                verificar_permiso_imei();
            }else{
                imei=telephonyManager.getDeviceId();
            }

            if (token != null || token == "") {
                if(ir_google==1){
                    servicio_iniciar_sesion_google(s_nombre, s_apellido, s_email, token,s_id_autentificacion,imei);
                }else
                {
                    servicio_iniciar_sesion_facebook(s_nombre, s_apellido, s_email, token,s_id_autentificacion,imei);
                }

            } else {

                mensaje_error("No se a podido generar el Token. porfavor active sus datos de Red e instale Google Pay Service");
            }

        }
        else
        {
            mensaje("Por Favor acepte los terminos de privacidad de Facebook.");
            finish();
        }
    }



    public void verificar_permiso_sms()
    {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_SMS)) {
            //YA LO CANCELE Y VOUELVO A PERDIR EL PERMISO.

            AlertDialog.Builder dialogo1 = new AlertDialog.Builder(this);
            dialogo1.setTitle("Atención!");
            dialogo1.setMessage("Debes otorgar permisos de SMS para realizar la autenficación");
            dialogo1.setCancelable(false);
            dialogo1.setPositiveButton("Solicitar permiso", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogo1, int id) {
                    dialogo1.cancel();
                    ActivityCompat.requestPermissions(Autenticar_celular.this,
                            SMS_PERMISSIONS,
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
            ActivityCompat.requestPermissions(Autenticar_celular.this,
                    SMS_PERMISSIONS,
                    1);
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }




    public void servicio_iniciar_sesion_google(final String s_nombre,
                                               final String s_apellido,
                                               String s_email,
                                               String token,
                                               String s_id_autentificacion,
                                               String imei)
    {
        pDialog = new ProgressDialog(Autenticar_celular.this);
        pDialog.setTitle(getString(R.string.app_name));
        pDialog.setMessage("Autenticando ....");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(true);
        pDialog.show();


        try {
            JSONObject jsonParam= new JSONObject();
            jsonParam.put("nombre", s_nombre);
            jsonParam.put("apellido", s_apellido);
            jsonParam.put("email", s_email);
            jsonParam.put("token", token);
            jsonParam.put("id_google", s_id_autentificacion);
            jsonParam.put("imei", imei);

            String url=getString(R.string.servidor) + "frmUsuario.php?opcion=registrar_usuario_google_directo";
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
                                    String id=respuestaJSON.getString("id_usuario");
                                    String email=respuestaJSON.getString("correo");
                                    String celular=respuestaJSON.getString("celular");
                                    cargar_datos(s_nombre,s_apellido,email,id,celular);

                                    //final
                                    registrado_facebook();

                                } else  {
                                    error_sesion_usuario();
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


    public void servicio_iniciar_sesion_facebook(final String s_nombre,
                                                 final String s_apellido,
                                                 String s_email,
                                                 String token,
                                                 String s_id_autentificacion,
                                                 String imei)
    {
        pDialog = new ProgressDialog(Autenticar_celular.this);
        pDialog.setTitle(getString(R.string.app_name));
        pDialog.setMessage("Autenticando ....");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(true);
        pDialog.show();


        try {
            JSONObject jsonParam= new JSONObject();
            jsonParam.put("nombre", s_nombre);
            jsonParam.put("apellido", s_apellido);
            jsonParam.put("email", s_email);
            jsonParam.put("token", token);
            jsonParam.put("id_facebook", s_id_autentificacion);
            jsonParam.put("imei", imei);

            String url=getString(R.string.servidor) + "frmUsuario.php?opcion=registrar_usuario_facebook_directo";
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
                                    String id=respuestaJSON.getString("id_usuario");
                                    String email=respuestaJSON.getString("correo");
                                    String celular=respuestaJSON.getString("celular");
                                    cargar_datos(s_nombre,s_apellido,email,id,celular);

                                    //final
                                    registrado_facebook();

                                } else  {
                                    error_sesion_usuario();
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

    private void error_sesion_usuario() {
        mensaje_error("Usuario Incorrecto.");
    }


    private void registrado_facebook() {
        SharedPreferences usuario=getSharedPreferences("perfil",MODE_PRIVATE);
        if(usuario.getString("id_usuario","").equals("")==false  && usuario.getString("id_usuario","").equals("null")==false) {


            AlertDialog.Builder dialogo1 = new AlertDialog.Builder(this);
            dialogo1.setTitle(getString(R.string.app_name));
            dialogo1.setMessage("Muchas gracias por registrate en " + Html.fromHtml("<b>" + getString(R.string.app_name) + "</b>") + ".\n" +
                    "Ahora podés pedir tu Movil  desde tu celular y el Movil llegara donde tu estes.\nSin " +
                    "llamadas, sin moverte.");
            dialogo1.setCancelable(false);
            dialogo1.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogo1, int id) {
                    saltar_principal();
                }
            });

            dialogo1.show();
        }
    }
    private void Autenticar_celular() {
        SharedPreferences usuario=getSharedPreferences("perfil",MODE_PRIVATE);
        if(usuario.getString("id_usuario","").equals("")==false  && usuario.getString("id_usuario","").equals("null")==false) {

            saltar_principal();

        }
    }
    public void saltar_principal()
    {
        startActivity(new Intent(this, Menu_usuario.class));
    }
    public void mensaje(String mensaje)
    {
        Toast toast = Toast.makeText(this,mensaje, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast.show();
    }

    public void cargar_datos(String nombre, String apellido, String email, String celular, String id, String codigo)
    {
        SharedPreferences usuario=getSharedPreferences("perfil",MODE_PRIVATE);
        SharedPreferences.Editor editar=usuario.edit();
        editar.putString("nombre",nombre);
        editar.putString("apellido",apellido);
        editar.putString("email",email);
        editar.putString("celular",celular);
        editar.putString("imei",imei);
        editar.putString("id_usuario",id);
        editar.putString("codigo",codigo);
        editar.putString("login_usuario","1");
        editar.commit();
    }
    public void cargar_datos(String nombre, String apellido, String email, String id, String celular)
    {
        SharedPreferences usuario=getSharedPreferences("perfil",MODE_PRIVATE);
        SharedPreferences.Editor editar=usuario.edit();
        editar.putString("nombre",nombre);
        editar.putString("apellido",apellido);
        editar.putString("email",email);
        editar.putString("id_usuario",id);
        editar.putString("celular",celular);
        editar.putString("imei",imei);
        editar.putString("login_usuario","1");
        editar.commit();
    }

    public boolean verificar_login_usuario()
    {
        SharedPreferences perfil=getSharedPreferences("perfil",MODE_PRIVATE);
        return (perfil.getString("login_usuario","").equals("1"));

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

    public String obtener_nombre(String nombre)
    {int contador=0;
        String nom="";try{
        for (int i=0;i<nombre.length();i++)
        {
            if(nombre.charAt(i)== 32)
            {
                contador++;
            }
        }

        if(contador==1)
        {

            for (int i=0;i<nombre.length();i++)
            {
                if(nombre.charAt(i)== 32)
                {
                    nom=nombre.substring(0,i);
                    i=nombre.length();
                }
            }
        }
        else if(contador>=2)
        {int c=0;
            for (int i=nombre.length()-1;i>0;i--)
            {
                if(nombre.charAt(i)== 32)
                {
                    c++;
                }
                if(c==2)
                {
                    nom=nombre.substring(0,i);
                    i=0;
                }
            }
        }
        else
        {
            nom=nombre;
        }
    }
    catch (Exception e)
    {
        nom=nombre;
    }
        return nom;
    }

    public String obtener_apellido(String nombre)
    {int contador=0;
        String ape="";
        try {
            for (int i = 0; i < nombre.length(); i++) {
                if (nombre.charAt(i) == 32) {
                    contador++;
                }
            }

            if (contador == 1) {

                for (int i = 0; i < nombre.length(); i++) {
                    if (nombre.charAt(i) == 32) {
                        ape = nombre.substring(i + 1);
                        i = nombre.length();
                    }
                }
            } else if (contador >= 2) {
                int c = 0;
                for (int i=nombre.length()-1;i>0;i--) {
                    if (nombre.charAt(i) == 32) {
                        c++;
                    }
                    if (c == 2) {
                        ape = nombre.substring(i + 1);
                        i = 0;
                    }
                }
            } else {
                ape = nombre;
            }
        }catch (Exception e)
        {
            ape=nombre;
        }
        return ape;
    }


    @Override
    protected  void onActivityResult(int requesCode,int resultCode,Intent data)
    {super.onActivityResult(requesCode,resultCode,data);
        callbackManager.onActivityResult(requesCode,resultCode,data);

        if(requesCode==SIGN_IN_CODE){
            //verificar si se logeo con su cuenta de GOOGLE.
            GoogleSignInResult result= Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }

    private void handleSignInResult(GoogleSignInResult result2) {
        if(result2.isSuccess()){
            OptionalPendingResult<GoogleSignInResult> opr= Auth.GoogleSignInApi.silentSignIn(googleApiClient);
            if(opr.isDone()) {
                GoogleSignInResult result1 = opr.get();


                GoogleSignInAccount account = result1.getSignInAccount();
                String nombre = account.getDisplayName();
                String email = account.getEmail();
                String id_google = account.getId();
                s_nombre = obtener_nombre(nombre);
                s_apellido = obtener_apellido(nombre);
                s_email = email;
                registrar_usuario(s_nombre, s_apellido, s_email, id_google, 1);
            }
        }else{
            Toast.makeText(this,"No se pudo iniciar sesión",Toast.LENGTH_SHORT).show();
        }
    }

    //VERIFICAR SI ESTA CON CONEXION WIFI
    protected Boolean conectadoWifi(){
        ConnectivityManager connectivity = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo info = connectivity.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            if (info != null) {
                return info.isConnected();
            }
        }
        return false;
    }
    //VERIFICAR SI ESTA CON CONEXION DE DATOS
    protected Boolean conectadoRedMovil(){
        ConnectivityManager connectivity = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo info = connectivity.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            if (info != null) {
                return info.isConnected();
            }
        }
        return false;
    }

    protected Boolean estaConectado(){
        if(conectadoWifi()){
            return true;
        }else{
            if(conectadoRedMovil()){
                return true;
            }else{
                mensaje_error("Tu Dispositivo no tiene Conexion a Internet.");
                return false;
            }
        }
    }

    public void verificar_permiso_imei()
    {
        final String[] PERMISSIONS = { Manifest.permission.INTERNET,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.ACCESS_NETWORK_STATE };

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_PHONE_STATE)) {
            //YA LO CANCELE Y VOUELVO A PERDIR EL PERMISO.

            AlertDialog.Builder dialogo1 = new AlertDialog.Builder(this);
            dialogo1.setTitle("Atención!");
            dialogo1.setMessage("Debes otorgar permisos de acceso al ID del Telefono por tema de Seguridad.");
            dialogo1.setCancelable(false);
            dialogo1.setPositiveButton("Solicitar permiso", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogo1, int id) {
                    dialogo1.cancel();
                    ActivityCompat.requestPermissions(Autenticar_celular.this,
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
            ActivityCompat.requestPermissions(Autenticar_celular.this,
                    PERMISSIONS,
                    1);
        }
    }


    public void verificar_todos_los_permisos()
    {
        final String[] SMS_PERMISSIONS1 = {
                Manifest.permission.INTERNET,
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.INTERNET,
                Manifest.permission.READ_PHONE_STATE};


        ActivityCompat.requestPermissions(Autenticar_celular.this,
                SMS_PERMISSIONS1,
                1);


    }




    //motista
    private boolean verificar_celular(CharSequence s) {
        boolean sw=false;
        try{
            int numero= Integer.parseInt(s.toString());
            if(numero>=60000000 && numero<=79999999)
            {
                sw=true;
            }
        }catch (Exception e)
        {
            sw=false;
        }
        return sw;
    }





}
