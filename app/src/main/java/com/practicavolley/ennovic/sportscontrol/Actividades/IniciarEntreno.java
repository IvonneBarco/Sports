package com.practicavolley.ennovic.sportscontrol.Actividades;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.practicavolley.ennovic.sportscontrol.Clases.Preferences;
import com.practicavolley.ennovic.sportscontrol.Conexiones.Conexion;
import com.practicavolley.ennovic.sportscontrol.Modelos.Entreno;
import com.practicavolley.ennovic.sportscontrol.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import es.dmoral.toasty.Toasty;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static java.lang.Integer.valueOf;

public class IniciarEntreno extends AppCompatActivity {

    private static final String CARPETA_PRINCIPAL = "misImagenesApp/";//directorio principal
    private static final String CARPETA_IMAGEN = "SportControl";//carpeta donde se guardan las fotos
    private static final String DIRECTORIO_IMAGEN = CARPETA_PRINCIPAL + CARPETA_IMAGEN;//ruta carpeta de directorios
    private String path;//almacena la ruta de la imagen
    File fileImagen;
    Bitmap bitmap;

    private final int MIS_PERMISOS = 100;
    private static final int COD_SELECCIONA = 10;
    private static final int COD_FOTO = 20;

    //Notificacion
    NotificationCompat.Builder notificacion;
    int notificacionId = 1;
    String channelId = "my_channerl_01";
    long[] pattern = new long[]{1000, 500, 1000};

    //Entreno iniciar
    EditText campoEntrenamiento, descripcion;
    Button iniciar, actualizar, parar;
    Spinner spinner;
    String[] datos = null, datosid;
    String tmp = "", identificador = "";
    LinearLayout layout_check;
    ArrayList<Integer> chekedList = new ArrayList<Integer>();
    ArrayList<Integer> chekedguardList = new ArrayList<Integer>();

    //gps
    //TextView coordenadas;
    EditText coordenadas, e_longitud;
    LocationManager locationManager;
    LocationListener locationListener;
    android.app.AlertDialog alertaGPS = null;

    private String IDUSUARIO, ROLEUSUARIO;
    private String id_entrenamiento;
    int idliga = 1;


    //Foto
    ImageButton btnFoto;
    ImageView imgFoto;
    ProgressDialog progreso;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iniciar_entreno);

        id_entrenamiento = String.valueOf(getIntent().getStringExtra("id_entrenamiento"));

        IDUSUARIO = Preferences.obtenerPreferencesString(this, Preferences.PREFERENCE_ID_USUARIO_LOGIN);
        ROLEUSUARIO = Preferences.obtenerPreferencesString(this, Preferences.PREFERENCE_ROLE_USUARIO_LOGIN);

        // Codigo flecha atras...
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_back_white));

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //regresar...
                DialogoRegresarrEntrenamiento();
            }
        });

        // * Codigo flecha atras...
        campoEntrenamiento = (EditText) findViewById(R.id.campoEntrenamiento);
        descripcion = (EditText) findViewById(R.id.descripcion_id);
        iniciar = (Button) findViewById(R.id.btnagregar);
        actualizar = (Button) findViewById(R.id.btnagregar2);
        parar = (Button) findViewById(R.id.btnagregar3);
        btnFoto = (ImageButton) findViewById(R.id.btnFoto);
        imgFoto = (ImageView) findViewById(R.id.imgFoto);

        campoEntrenamiento.setText(String.valueOf(getIntent().getStringExtra("nom_entrenamiento")));

        //gps
        //coordenadas = (TextView) findViewById(R.id.id_coordenadas);
        coordenadas = (EditText) findViewById(R.id.e_latitud_id);

        actualizar.setEnabled(false);

        parar.setEnabled(false);

        spinner = (Spinner) findViewById(R.id.spinner);

        layout_check = (LinearLayout) findViewById(R.id.base_layout);

        //this.listarAthletas();
        this.datoscheck();

        //Permisos
        if (solicitaPermisosVersionesSuperiores()) {
            btnFoto.setEnabled(true);
        } else {
            btnFoto.setEnabled(false);
        }


        iniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogoIniciarEntrenamiento();
            }
        });

        parar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //pararentreno();
                DialogoFinalizarEntrenamiento();
            }
        });

        actualizar.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                for (Integer marcados : chekedList) {
                    boolean estado = false;
                    for (Integer reco : chekedguardList) {
                        if (marcados == reco) {
                            estado = true;

                        }
                    }
                    if (!estado) {
                        String cadena = marcados + "";
                        registrarAthletas(tmp, cadena);
                        chekedguardList.add(marcados);

                    }

                }

                Toasty.info(IniciarEntreno.this, "ACTUALIZANDO...", Toast.LENGTH_LONG).show();


            }

        });

        btnFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mostrarDialogOpciones();
            }
        });


        //gps
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                updateLocationInfo(location);
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {
            }

            @Override
            public void onProviderEnabled(String s) {
            }

            @Override
            public void onProviderDisabled(String s) {
            }
        };
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        } else {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
            Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (lastKnownLocation != null) {
                updateLocationInfo(lastKnownLocation);
            }
        }
    }

    private void mostrarDialogOpciones() {
        final CharSequence[] opciones = {"Tomar Foto", "Elegir de Galeria", "Cancelar"};
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Elige una Opción");
        builder.setItems(opciones, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (opciones[i].equals("Tomar Foto")) {
                    abriCamara();
                } else {
                    if (opciones[i].equals("Elegir de Galeria")) {
                        Intent intent = new Intent(Intent.ACTION_PICK,
                                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        intent.setType("image/");
                        startActivityForResult(intent.createChooser(intent, "Seleccione"), COD_SELECCIONA);
                    } else {
                        dialogInterface.dismiss();
                    }
                }
            }
        });
        builder.show();
    }

    private void abriCamara() {
        File miFile = new File(Environment.getExternalStorageDirectory(), DIRECTORIO_IMAGEN);
        boolean isCreada = miFile.exists();

        if (isCreada == false) {
            isCreada = miFile.mkdirs();
        }

        if (isCreada == true) {
            Long consecutivo = System.currentTimeMillis() / 1000;
            String nombre = consecutivo.toString() + ".jpg";

            path = Environment.getExternalStorageDirectory() + File.separator + DIRECTORIO_IMAGEN
                    + File.separator + nombre;//indicamos la ruta de almacenamiento

            fileImagen = new File(path);

            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(fileImagen));

            ////
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                String authorities = this.getPackageName() + ".provider";
                Uri imageUri = FileProvider.getUriForFile(this, authorities, fileImagen);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            } else {
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(fileImagen));
            }
            startActivityForResult(intent, COD_FOTO);

            ////

        }

    }

    private String convertirImgString(Bitmap bitmap) {

        ByteArrayOutputStream array = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, array);
        byte[] imagenByte = array.toByteArray();
        String imagenString = Base64.encodeToString(imagenByte, Base64.DEFAULT);

        return imagenString;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case COD_SELECCIONA:
                Uri miPath = data.getData();
                imgFoto.setImageURI(miPath);

                try {
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), miPath);
                    imgFoto.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                break;
            case COD_FOTO:
                MediaScannerConnection.scanFile(this, new String[]{path}, null,
                        new MediaScannerConnection.OnScanCompletedListener() {
                            @Override
                            public void onScanCompleted(String path, Uri uri) {
                                Log.i("Path", "" + path);
                            }
                        });

                bitmap = BitmapFactory.decodeFile(path);
                imgFoto.setImageBitmap(bitmap);

                break;
        }
        bitmap = redimensionarImagen(bitmap, 600, 800);
    }

    private Bitmap redimensionarImagen(Bitmap bitmap, float anchoNuevo, float altoNuevo) {

        int ancho = bitmap.getWidth();
        int alto = bitmap.getHeight();

        if (ancho > anchoNuevo || alto > altoNuevo) {
            float escalaAncho = anchoNuevo / ancho;
            float escalaAlto = altoNuevo / alto;

            Matrix matrix = new Matrix();
            matrix.postScale(escalaAncho, escalaAlto);

            return Bitmap.createBitmap(bitmap, 0, 0, ancho, alto, matrix, false);

        } else {
            return bitmap;
        }
    }


    //permisos
    ////////////////

    private boolean solicitaPermisosVersionesSuperiores() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {//validamos si estamos en android menor a 6 para no buscar los permisos
            return true;
        }

        //validamos si los permisos ya fueron aceptados
        if ((this.checkSelfPermission(WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) && this.checkSelfPermission(CAMERA) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }


        if ((shouldShowRequestPermissionRationale(WRITE_EXTERNAL_STORAGE) || (shouldShowRequestPermissionRationale(CAMERA)))) {
            cargarDialogoRecomendacion();
        } else {
            requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE, CAMERA}, MIS_PERMISOS);
        }


        return false;//implementamos el que procesa el evento dependiendo de lo que se defina aqui
    }

    private View.OnClickListener ckListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            int id = view.getId();
            boolean checked = ((CheckBox) view).isChecked();
            if (checked) {
                chekedList.add(id);
            } else {
                chekedList.remove(new Integer(id));
            }
        }
    };

    public void registrar() {

        progreso = new ProgressDialog(this);
        progreso.setMessage("Cargando...");
        progreso.setCancelable(false);
        progreso.show();


        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Conexion.URL_WEB_SERVICES + "registrar-entrenos.php", new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                progreso.hide();

                //Toast.makeText(IniciarEntreno.this, estadox, Toast.LENGTH_LONG).show();
                notificacionEntrenamientoIniciado();
                Toasty.success(IniciarEntreno.this, "ENTRENAMIENTO INICIADO", Toast.LENGTH_LONG).show();

                DesactivarBoton(iniciar, false);
                iniciar.setEnabled(false);
                descripcion.setEnabled(false);
                spinner.setEnabled(false);
                parar.setEnabled(true);
                actualizar.setEnabled(true);

                Entreno user = new Entreno();
                try {
                    JSONObject objresultado = new JSONObject(response);
                    String estadox = objresultado.get("estado").toString();
                    identificador=objresultado.get("id").toString();
                    Toast.makeText(IniciarEntreno.this, estadox,Toast.LENGTH_LONG).show();
                    if (!estadox.equalsIgnoreCase("exito")) {
                        //Toast.makeText(this,"errot",Toast.LENGTH_LONG).show();
                        Toast.makeText(IniciarEntreno.this, "error", Toast.LENGTH_LONG).show();

                    } else {
                        //Toast.makeText(Registrar2.this, identificador,Toast.LENGTH_LONG).show();

                        for (Integer marcados : chekedList) {
                            String cadena = marcados + "";

                            registrarAthletas(tmp, cadena);
                            chekedguardList.add(marcados);
                            Log.i("Lista: ", marcados.toString());
                        }

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(IniciarEntreno.this, "No se ha podido conectar", Toast.LENGTH_SHORT).show();
                progreso.hide();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                String id_entrenamiento = String.valueOf(getIntent().getStringExtra("id_entrenamiento"));
                String imagen = convertirImgString(bitmap);

                Map<String, String> params = new HashMap<>();
                params.put("gps", coordenadas.getText().toString());
                params.put("entrenop", id_entrenamiento);
                params.put("descripcion", descripcion.getText().toString());
                params.put("imagen", imagen);

                return params;
            }
        };
        queue.add(stringRequest);
    }

    public void registrarAthletas(final String entreno, final String athleta) {
        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Conexion.URL_WEB_SERVICES + "registrar-athletas.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Entreno user = new Entreno();
                try {
                    JSONObject objresultado = new JSONObject(response);
                    String estadox = objresultado.get("estado").toString();
                    if (!estadox.equalsIgnoreCase("exito")) {
                        //Toast.makeText(this,"errot",Toast.LENGTH_LONG).show();
                        Toast.makeText(IniciarEntreno.this, "error athleta", Toast.LENGTH_LONG).show();
                    } else {
                        //Toast.makeText(Registrar2.this, "exito ",Toast.LENGTH_LONG).show();
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
                params.put("entreno", entreno);
                params.put("athleta", athleta);

                return params;
            }
        };
        queue.add(stringRequest);
    }

    public void pararentreno() {

        super.onPause();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                return;
            } else {
                locationManager.removeUpdates(locationListener);
            }
        } else {
            locationManager.removeUpdates(locationListener);
        }

        RequestQueue queue= Volley.newRequestQueue(this);

        StringRequest stringRequest=new StringRequest(Request.Method.POST, Conexion.URL_WEB_SERVICES + "actualizar-entrenos.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Entreno user=new Entreno();
                try {
                    JSONObject objresultado=new JSONObject(response);
                    String estadox=objresultado.get("estado").toString();
                    if(!estadox.equalsIgnoreCase("exito")){
                        //Toast.makeText(this,"errot",Toast.LENGTH_LONG).show();
                        Toast.makeText(IniciarEntreno.this, "error",Toast.LENGTH_LONG).show();
                    }else{
                        //Toast.makeText(Registrar2.this, "error",Toast.LENGTH_LONG).show();
                        Intent intent =new Intent(IniciarEntreno.this,OpcionesActivity.class);
                        startActivity(intent);

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
                params.put("id",identificador);
                params.put("gps2", coordenadas.getText().toString());
                return params;
            }
        };
        queue.add(stringRequest);
    }


    public void datoscheck() {


        RequestQueue queue = Volley.newRequestQueue(this);


        StringRequest stringRequest = new StringRequest(Request.Method.POST, Conexion.URL_WEB_SERVICES + "listar-entrenop.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //listarusr.setText(response);

                try {
                    JSONObject root = new JSONObject(response);
                    final JSONArray arrsemanas = root.getJSONArray("entrenop");
                    datos = new String[arrsemanas.length()];
                    datosid = new String[arrsemanas.length()];
                    if (arrsemanas.length() > 0) {

                        for (int i = 0; i < arrsemanas.length(); i++) {
                            JSONObject arrsemana = arrsemanas.getJSONObject(i);
                            datosid[i] = arrsemana.getString("id");
                            datos[i] = arrsemana.getString("nombre");
                            Log.d("datos", arrsemana.getString("nombre"));
                        }


                        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(IniciarEntreno.this, android.R.layout.simple_spinner_item, datos); //selected item will look like a spinner set from XML

                        spinner.setAdapter(spinnerArrayAdapter);

                        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id) {
                                //Toast.makeText(adapterView.getContext(),datosid[valueOf(id_entrenamiento)].toString(), Toast.LENGTH_SHORT).show();
                                tmp = datosid[pos].toString();
                                listarAthletas(tmp);
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

            //
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id", IDUSUARIO);

                return params;
            }


        };
        queue.add(stringRequest);


    }

    public void listarAthletas(final String id) {

        layout_check.removeAllViews();


        RequestQueue queue = Volley.newRequestQueue(this);


        StringRequest stringRequest = new StringRequest(Request.Method.POST, Conexion.URL_WEB_SERVICES + "listar-athletasentreno.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //listarusr.setText(response);

                try {
                    JSONObject root = new JSONObject(response);
                    final JSONArray arrsemanas = root.getJSONArray("athleta");

                    if (arrsemanas.length() > 0) {

                        for (int i = 0; i < arrsemanas.length(); i++) {
                            JSONObject arrsemana = arrsemanas.getJSONObject(i);

                            CheckBox cb = new CheckBox(IniciarEntreno.this);
                            cb.setId(valueOf(arrsemana.getInt("athlete")));
                            cb.setText(arrsemana.getString("nombre") + " " + arrsemana.getString("apellido"));
                            cb.setOnClickListener(ckListener);
                            layout_check.addView(cb);


                            Log.d("datos", arrsemana.getString("nombre"));
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
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id", id);

                return params;
            }

        };
        queue.add(stringRequest);


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            startListening();
        }
    }

    public void startListening() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        }
    }

    public void updateLocationInfo(Location location) {


        //latitud.setText("Latitud: " + Double.toString(location.getLatitude()));
        //longitud.setText("Longitud: " + Double.toString(location.getLongitude()));
        coordenadas.setText("" + Double.toString(location.getLatitude()) + "," + "" + Double.toString(location.getLongitude()));
        //latitud.setText("Latitud: " + Double.toString(location.getLatitude()) + " - " + "Longitud: " + Double.toString(location.getLongitude()));
        //e_longitud.setText("Longitud: " + Double.toString(location.getLongitude()));

    }

    private void AlertaNoGps() {
        final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setMessage("El sistema GPS esta desactivado, ¿Desea activarlo?")
                .setCancelable(false)
                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        dialog.cancel();
                    }
                });
        alertaGPS = builder.create();
        alertaGPS
                .show();
    }
/*
     @Override
   protected void onPause() {
        super.onPause();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                return;
            } else {
                locationManager.removeUpdates(locationListener);
            }
        } else {
            locationManager.removeUpdates(locationListener);
        }
    }
*/
    @Override
    public void onBackPressed() {
        DialogoSalirEntrenamiento();
    }

    //Dialogos

    public void DialogoRegresarrEntrenamiento() {
        android.support.v7.app.AlertDialog.Builder alerta = new android.support.v7.app.AlertDialog.Builder(IniciarEntreno.this);
        alerta.setMessage("")
                .setCancelable(false)
                .setPositiveButton("SI", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Intent intent = new Intent(IniciarEntreno.this, Entrenamientos.class);
                        startActivity(intent);
                        finish();
                    }
                })
                .setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //Codigo de continuar en la app
                        dialogInterface.cancel();
                    }
                });
        android.support.v7.app.AlertDialog titulo = alerta.create();
        titulo.setTitle("¿Salir del entrenamiento?");
        titulo.show();
    }

    public void DialogoIniciarEntrenamiento() {
        android.support.v7.app.AlertDialog.Builder alerta = new android.support.v7.app.AlertDialog.Builder(IniciarEntreno.this);
        alerta.setMessage("Presione SI para iniciar el entrenamiento o CANCELAR para regresar a la pantalla anterior")
                .setCancelable(false)
                .setPositiveButton("SI", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        registrar();

                    }
                })
                .setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //Codigo de continuar en la app
                        dialogInterface.cancel();
                    }
                });
        android.support.v7.app.AlertDialog titulo = alerta.create();
        titulo.setTitle("¿Iniciar Entrenamiento?");
        titulo.show();
    }

    public void DialogoFinalizarEntrenamiento() {
        android.support.v7.app.AlertDialog.Builder alerta = new android.support.v7.app.AlertDialog.Builder(IniciarEntreno.this);
        alerta.setMessage("Presione SI para finalizar el entrenamiento o CANCELAR para continuar en el mismo")
                .setCancelable(false)
                .setPositiveButton("SI", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toasty.error(IniciarEntreno.this, "ENTRENAMIENTO DETENIDO", Toast.LENGTH_LONG).show();
                        pararentreno();
                    }
                })
                .setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //Codigo de continuar en la app
                        dialogInterface.cancel();
                    }
                });
        android.support.v7.app.AlertDialog titulo = alerta.create();
        titulo.setTitle("¿Finalizar Entrenamiento?");
        titulo.show();
    }

    public void notificacionEntrenamientoIniciado() {
        final NotificationManager notificationManager = (NotificationManager) getSystemService(getApplicationContext().NOTIFICATION_SERVICE);

        notificacion = new NotificationCompat.Builder(this, null);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            //Nombre del canal
            CharSequence name = "Entrenamiento Iniciado";

            //Descripción
            String descripcion = "Entrenamiento en curso";
            int importancia = NotificationManager.IMPORTANCE_HIGH;

            NotificationChannel mchannel = new NotificationChannel(channelId, name, importancia);

            //Configuracion canal de notificación
            mchannel.setDescription(descripcion);
            mchannel.enableLights(true);

            //Configuraciones de notificación
            mchannel.setLightColor(Color.RED);
            mchannel.enableVibration(true);
            mchannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            notificationManager.createNotificationChannel(mchannel);

            notificacion = new NotificationCompat.Builder(this, channelId);


        }

        notificacion.setSmallIcon(R.drawable.logo_sportscontrol).setContentTitle("Entrenamiento en curso").setContentText("Toca aquí para ir al entrenamiento");
        notificacion.setAutoCancel(true);
        notificacion.setTicker("El entrenamiento ha iniciado");
        //notificacion.setUsesChronometer(true);
        notificacion.setVibrate(pattern);
        //Defino que la notificacion sea permamente
        //notificacion.setOngoing(true);
        Intent notificationIntent = new Intent(IniciarEntreno.this, IniciarEntreno.class);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);


        PendingIntent pendingIntent = PendingIntent.getActivity(IniciarEntreno.this, 0, notificationIntent, PendingIntent.FLAG_IMMUTABLE);
        notificationManager.cancel(1);
        notificacion.setContentIntent(pendingIntent);

        notificacion.setChannelId(channelId);
        notificationManager.notify(notificacionId, notificacion.build());
    }

    public void DialogoSalirEntrenamiento() {
        android.support.v7.app.AlertDialog.Builder alerta = new android.support.v7.app.AlertDialog.Builder(IniciarEntreno.this);
        alerta.setMessage("Tenga en cuenta que si usted ha iniciado el entrenamiento y sale de esta pantalla, este se perdera y no podrá iniciarlo nuevamente")
                .setCancelable(false)
                .setPositiveButton("SI", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Intent intent = new Intent(IniciarEntreno.this, Entrenamientos.class);
                        startActivity(intent);
                        finish();
                    }
                })
                .setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //Codigo de continuar en la app
                        dialogInterface.cancel();
                    }
                });
        android.support.v7.app.AlertDialog titulo = alerta.create();
        titulo.setTitle("¿Está seguro que desea salir?");
        titulo.show();
    }

    private void cargarDialogoRecomendacion() {
        AlertDialog.Builder dialogo = new AlertDialog.Builder(this);
        dialogo.setTitle("Permisos Desactivados");
        dialogo.setMessage("Debe aceptar los permisos para el correcto funcionamiento de la App");

        dialogo.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE, CAMERA}, 100);
                }
            }
        });
        dialogo.show();
    }

    //Menu home

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
            /*Intent i = new Intent(InicioEntrenamiento.this, OpcionesActivity.class);
            startActivity(i);
            finish();*/
            DialogoRegresarrEntrenamiento();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressLint("ResourceAsColor")
    public void DesactivarBoton(Button boton, Boolean b) {
        boton.setEnabled(false);
        boton.setBackgroundColor(R.color.colorGray);
    }


    @SuppressLint("ResourceAsColor")
    public void ActivarBoton(Button botona, Boolean c) {
        botona.setEnabled(true);
        botona.setBackgroundColor(R.color.colorPrimaryDark);
    }
}
