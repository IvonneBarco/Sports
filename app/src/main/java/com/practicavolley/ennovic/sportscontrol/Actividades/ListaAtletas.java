package com.practicavolley.ennovic.sportscontrol.Actividades;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.practicavolley.ennovic.sportscontrol.Adapters.AdaptadorAtletasAsistencia;
import com.practicavolley.ennovic.sportscontrol.Clases.Preferences;
import com.practicavolley.ennovic.sportscontrol.Conexiones.Conexion;
import com.practicavolley.ennovic.sportscontrol.Modelos.AtletaVo;
import com.practicavolley.ennovic.sportscontrol.Modelos.DeportesVo;
import com.practicavolley.ennovic.sportscontrol.Modelos.LigasVo;
import com.practicavolley.ennovic.sportscontrol.Modelos.Usuario;
import com.practicavolley.ennovic.sportscontrol.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import es.dmoral.toasty.Toasty;

public class ListaAtletas extends AppCompatActivity {

    private Usuario user;
    private LigasVo liga;
    private DeportesVo sport;
    TextView nomdeporte, iddeporte;

    private String IDUSUARIO, ROLEUSUARIO, NOMBREUSUARIO;
    int idliga = 1;

    String[] datosid;
    String[][] datos = null;

    ProgressDialog progreso;


    //RECYCLER
    ArrayList<AtletaVo> listaAtletas;

    //Refencia al reclycler
    RecyclerView recyclerAtletas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_atletas);

        consulta();

        IDUSUARIO = Preferences.obtenerPreferencesString(this, Preferences.PREFERENCE_ID_USUARIO_LOGIN);
        ROLEUSUARIO = Preferences.obtenerPreferencesString(this, Preferences.PREFERENCE_ROLE_USUARIO_LOGIN);
        NOMBREUSUARIO = Preferences.obtenerPreferencesString(this, Preferences.PREFERENCE_NOMBRE_USUARIO_LOGIN);

        Log.i("DATOS USUARIO: ", IDUSUARIO + " " + ROLEUSUARIO + " " + NOMBREUSUARIO);

        // Codigo flecha atras...
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_back_white));

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //regresar...
                finish();
            }
        });

        // * Codigo flecha atras...

        //Inicio * Recycler
        listaAtletas = new ArrayList<>();
        recyclerAtletas = (RecyclerView) findViewById(R.id.RecyclerAtletas_Id);
        recyclerAtletas.setLayoutManager(new LinearLayoutManager(this));
        recyclerAtletas.setHasFixedSize(true);
        //Fin * Recycler


        listarAsistencia();
    }

    private void listarAsistencia() {

        progreso = new ProgressDialog(this);
        progreso.setMessage("Cargando...");
        progreso.setCancelable(false);
        progreso.show();

        // Initialize a new RequestQueue instance
        RequestQueue requestQueue = Volley.newRequestQueue(ListaAtletas.this);

        // Initialize a new JsonArrayRequest instance
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Conexion.URL_WEB_SERVICES + "listar-athletas.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progreso.hide();

                        AtletaVo athlete = null;

                        try {
                            JSONObject objresultado = new JSONObject(response);
                            JSONArray athletes = objresultado.getJSONArray("athleta");

                            if (athletes.length() <= 0) {
                                //Toast.makeText(Atletas.this, "NO HAY DATOS", Toast.LENGTH_SHORT).show();
                                Drawable icon = getResources().getDrawable(R.drawable.ic_empty);
                                Toasty.normal(ListaAtletas.this, "No se han encontrado datos", icon).show();

                            } else {

                                for (int i = 0; i < athletes.length(); i++) {
                                    athlete = new AtletaVo();
                                    JSONObject objAtletas = athletes.getJSONObject(i);

                                    athlete.setIdatleta(String.valueOf(objAtletas.optInt("athlete_id")));
                                    athlete.setNombreatleta(objAtletas.optString("nombre"));
                                    athlete.setApellidoatleta(objAtletas.optString("apellido"));
                                    athlete.setNivelrendimientoatleta(objAtletas.optString("nivelrendimiento"));
                                    athlete.setFoto(R.drawable.weightlifting);
                                    listaAtletas.add(athlete);


                                }
                                AdaptadorAtletasAsistencia adapter = new AdaptadorAtletasAsistencia(listaAtletas);

                                //evento click
                                adapter.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        //Toast.makeText(getApplicationContext(), "" + listaAtletas.get(recyclerAtletas.getChildAdapterPosition(view)).getIdatleta(), Toast.LENGTH_SHORT).show();
                                        Log.i("VALOR CHECKBOC ", listaAtletas.get(recyclerAtletas.getChildAdapterPosition(view)).getIdatleta());
                                        String id = listaAtletas.get(recyclerAtletas.getChildAdapterPosition(view)).getIdatleta();
                                        String NOMBREATLETA =  listaAtletas.get(recyclerAtletas.getChildAdapterPosition(view)).getNombreatleta() + " " + listaAtletas.get(recyclerAtletas.getChildAdapterPosition(view)).getApellidoatleta();
                                        //sendEmail(datos[0][5],datos[0][3]+" "+datos[0][4]," "+NOMBREUSUARIO + " ");
                                        sendEmail(datos[0][5],NOMBREATLETA," "+NOMBREUSUARIO + " ");
                                    }
                                });
                                //fin evento click

                                recyclerAtletas.setAdapter(adapter);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            //Toast.makeText(Atletas.this, "NO HAY CONEXIÓN", Toast.LENGTH_SHORT).show();
                            Drawable icon = getResources().getDrawable(R.drawable.ic_sin_conexion);
                            Toasty.normal(ListaAtletas.this, "No se puede establecer una conexión", icon).show();
                        }

                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Do something when error occurred
            }
        }) {

            //LOS CAMPOS EN VERDE DEBEN SER IGUAL AL DEL ARCHIVO PHP
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id", String.valueOf(4));
                params.put("liga", String.valueOf(idliga));
                return params;
            }
        };

        // Add JsonArrayRequest to the RequestQueue
        requestQueue.add(stringRequest);
    }



    public void consulta(){
        RequestQueue queue= Volley.newRequestQueue(this);

        StringRequest stringRequest=new StringRequest(Request.Method.POST, "https://www.guialistas.com.br/celular/vista/listar-atletasgmedico.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject root = new JSONObject(response);
                    final JSONArray arrsemanas = root.getJSONArray("grupomedico");
                    datos =new String[arrsemanas.length()][7];
                    datosid=new String[arrsemanas.length()];
                    if (arrsemanas.length()>0) {

                        //datos de atletas en una matriz para su uso
                        for (int i = 0; i < arrsemanas.length(); i++) {
                            JSONObject arrsemana = arrsemanas.getJSONObject(i);
                            datosid[i]=arrsemana.getString("id");
                            datos[i][0]=arrsemana.getString("entreatle");
                            datos[i][1]=arrsemana.getString("idatle");
                            datos[i][2]=arrsemana.getString("id");
                            datos[i][3]=arrsemana.getString("nombre");
                            datos[i][4]=arrsemana.getString("apellido");
                            datos[i][5]=arrsemana.getString("mail");
                            datos[i][6]=arrsemana.getString("perfil");
                            //Log.d("datos",arrsemana.getString("nombre"));

                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params=new HashMap<>();
                params.put("id", IDUSUARIO);


                return params;
            }
        };
        queue.add(stringRequest);
    }

    protected void sendEmail(String mail,String nombre, String entrenador) {

        String[] TO = {mail}; //aquí pon tu correo
        String[] CC = {""};
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_CC, CC);
// Esto podrás modificarlo si quieres, el asunto y el cuerpo del mensaje
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "SOLICITUD DE APOYO");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Hola!, \n \n El entrenador " + entrenador + " solicita tu conocimiento para ayudar a su deportista " + nombre + ". \n Por favor, comunícate con él lo más pronto posible para acordar una fecha y hora de encuentro. \n \n Gracias por tu colabración!");

        try {
            Drawable icon = getResources().getDrawable(R.drawable.ic_empty);
            Toasty.normal(this, "Preparando para enviar email", icon).show();
            startActivity(Intent.createChooser(emailIntent, "Enviar email..."));
            finish();
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(this,"No tienes clientes de email instalados.", Toast.LENGTH_SHORT).show();
        }
    }
}
