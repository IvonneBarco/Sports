package com.practicavolley.ennovic.sportscontrol.Actividades;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.practicavolley.ennovic.sportscontrol.Conexiones.Conexion;
import com.practicavolley.ennovic.sportscontrol.Modelos.Entrenop;
import com.practicavolley.ennovic.sportscontrol.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import es.dmoral.toasty.Toasty;

public class EntrenoProgramado extends AppCompatActivity {

    //Entreno programado

    Button btnfecha, btnhorainicio, btnhorafin;
    EditText efecha, ehora, fhora;
    private int dia, mes, ano, hora, minutos;

    EditText nombre, fecha, hinicio, hfin, lugar, descripcion;
    Button agregar;
    Spinner spinner;
    String[] datos = null, datosid;
    String tmp = "";

    private static final String CERO = "0";
    private static final String BARRA = "/";

    //Calendario para obtener fecha & hora
    public final Calendar c = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entreno_programado);

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

        nombre = (EditText) findViewById(R.id.nombre_id);
       /* fecha=(EditText) findViewById(R.id.fecha_id);
        hinicio=(EditText) findViewById(R.id.hinicio_id);
        hfin=(EditText) findViewById(R.id.hfin_id);*/
        lugar = (EditText) findViewById(R.id.lugar_id);

        descripcion = (EditText) findViewById(R.id.descripcion_id);
        agregar = (Button) findViewById(R.id.btnagregar);

        spinner = (Spinner) findViewById(R.id.spinner);

        //HORA Y FECHA
        btnfecha = (Button) findViewById(R.id.btn_fecha);
        btnhorainicio = (Button) findViewById(R.id.btn_hora_inicio);
        btnhorafin = (Button) findViewById(R.id.btn_hora_fin);
        fecha = (EditText) findViewById(R.id.fecha_id);
        hinicio = (EditText) findViewById(R.id.hinicio_id);
        hfin = (EditText) findViewById(R.id.hfin_id);

        btnfecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fecha();
            }
        });
        btnhorainicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                horaInicio();
            }
        });

        btnhorafin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                horaFin();
            }
        });

        this.datoscheck();

        //spinner.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, datos));

        agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Toast.makeText(EntrenoProgramado.this, "Registro exitoso...",Toast.LENGTH_LONG).show();
                Toasty.success(EntrenoProgramado.this, "Registro exitoso").show();
                registrar();

            }
        });


    }

    /*public void fecha(){
        final Calendar c= Calendar.getInstance();

        dia=c.get(Calendar.DAY_OF_MONTH);
        mes=c.get(Calendar.MONTH);
        ano=c.get(Calendar.WEEK_OF_YEAR);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                //fecha.setText(dayOfMonth+"/"+(monthOfYear+1)+"/"+year);
                fecha.setText(year+"/"+(monthOfYear+1)+"/"+dayOfMonth);

            }
        }
                ,dia,mes,ano);
        datePickerDialog.show();
    }*/

    private void fecha() {


        //Variables para obtener la fecha
        final int mes = c.get(Calendar.MONTH);
        final int dia = c.get(Calendar.DAY_OF_MONTH);
        final int anio = c.get(Calendar.YEAR);

        DatePickerDialog recogerFecha = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                //Esta variable lo que realiza es aumentar en uno el mes ya que comienza desde 0 = enero
                final int mesActual = month + 1;
                //Formateo el día obtenido: antepone el 0 si son menores de 10
                String diaFormateado = (dayOfMonth < 10) ? CERO + String.valueOf(dayOfMonth) : String.valueOf(dayOfMonth);
                //Formateo el mes obtenido: antepone el 0 si son menores de 10
                String mesFormateado = (mesActual < 10) ? CERO + String.valueOf(mesActual) : String.valueOf(mesActual);
                //Muestro la fecha con el formato deseado
                //fecha.setText(diaFormateado + BARRA + mesFormateado + BARRA + year);
                fecha.setText(year + BARRA + mesFormateado + BARRA + diaFormateado);


            }
            //Estos valores deben ir en ese orden, de lo contrario no mostrara la fecha actual
            /**
             *También puede cargar los valores que usted desee
             */
        }, anio, mes, dia);
        //Muestro el widget
        recogerFecha.show();

    }

    public void horaInicio() {
        final Calendar c = Calendar.getInstance();
        hora = c.get(Calendar.HOUR_OF_DAY);
        minutos = c.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                hinicio.setText(hourOfDay + ":" + minute);
            }
        }, hora, minutos, false);
        timePickerDialog.show();
    }

    public void horaFin() {
        final Calendar c = Calendar.getInstance();
        hora = c.get(Calendar.HOUR_OF_DAY);
        minutos = c.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                hfin.setText(hourOfDay + ":" + minute);
            }
        }, hora, minutos, false);
        timePickerDialog.show();
    }

    public void registrar() {
        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Conexion.URL_WEB_SERVICES + "registrar-entrenosprogramados.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Entrenop user = new Entrenop();
                try {
                    JSONObject objresultado = new JSONObject(response);
                    String estadox = objresultado.get("estado").toString();
                    if (!estadox.equalsIgnoreCase("exito")) {
                        //Toast.makeText(this,"errot",Toast.LENGTH_LONG).show();
                        Toast.makeText(EntrenoProgramado.this, "error", Toast.LENGTH_SHORT).show();
                    } else {

                        //Toast.makeText(EntrenoProgramado.this, "Registro exitoso", Toast.LENGTH_SHORT).show();
                        Toasty.success(EntrenoProgramado.this, "Registro exitoso").show();
                        Intent intent = new Intent(EntrenoProgramado.this.getBaseContext(), OpcionesActivity.class);
                        startActivity(intent);
                        finish();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("nombre", nombre.getText().toString());
                params.put("fecha", fecha.getText().toString());
                params.put("hinicio", hinicio.getText().toString());
                params.put("hfin", hfin.getText().toString());
                params.put("lugar", lugar.getText().toString());
                params.put("semana_id", tmp);
                params.put("descripcion", descripcion.getText().toString());

                return params;
            }
        };
        queue.add(stringRequest);
    }

    public void datoscheck() {


        RequestQueue queue = Volley.newRequestQueue(EntrenoProgramado.this);


        StringRequest stringRequest = new StringRequest(Request.Method.POST, Conexion.URL_WEB_SERVICES + "listar-semanas.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //listarusr.setText(response);

                try {
                    JSONObject root = new JSONObject(response);
                    final JSONArray arrsemanas = root.getJSONArray("semanas");
                    datos = new String[arrsemanas.length()];
                    datosid = new String[arrsemanas.length()];
                    if (arrsemanas.length() > 0) {

                        for (int i = 0; i < arrsemanas.length(); i++) {
                            JSONObject arrsemana = arrsemanas.getJSONObject(i);
                            datosid[i] = arrsemana.getString("id");
                            datos[i] = arrsemana.getString("nombre");
                            Log.d("datos", arrsemana.getString("nombre"));
                        }


                        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(EntrenoProgramado.this, android.R.layout.simple_spinner_item, datos); //selected item will look like a spinner set from XML

                        spinner.setAdapter(spinnerArrayAdapter);

                        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id) {
                                //Toast.makeText(adapterView.getContext(),datosid[pos].toString(), Toast.LENGTH_SHORT).show();
                                tmp = datosid[pos].toString();
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {
                            }
                        });

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


            }
        }) {


        };
        queue.add(stringRequest);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_home) {
            //Preferences.savePreferencesBoolean(this, false, Preferences.PREFERENCES_ESTADO_SWITCH);
            Intent i = new Intent(EntrenoProgramado.this, OpcionesActivity.class);
            startActivity(i);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
