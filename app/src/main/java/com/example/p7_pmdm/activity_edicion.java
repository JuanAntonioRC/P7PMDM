package com.example.p7_pmdm;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.Service;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.MenuBuilder;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.example.p7_pmdm.Logic.LogicSitio;

import static com.example.p7_pmdm.App.sitioActivo;

public class activity_edicion extends AppCompatActivity implements LocationListener {

    Spinner spinnerFiltro;

    ImageButton imageButton;

    final String TAG = "GPS";

    private final static int ALL_PERMISSIONS_RESULT = 101;
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10;
    private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1;

    static TextView txtLatitud;
    static TextView txtLongitud;
    static TextView txtnombre;
    static RatingBar ratingBar2;
    static TextView txtComentario;



    LocationManager locationManager;
    Location loc;

    ArrayList permissions = new ArrayList<>();
    ArrayList<String> permissionsToRequest;
    ArrayList<String> permissionsRejected = new ArrayList<>();

    boolean isGPS = false;
    boolean isNetwork = false;
    boolean canGetLocation = true;

    String infoNombre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edicion);

        txtComentario = findViewById(R.id.txtComentarioEdicion);
        txtnombre = findViewById(R.id.txtNombre);
        spinnerFiltro = findViewById(R.id.spinnerFiltro);
        ratingBar2 = findViewById(R.id.ratingBar2);

        List secciones = Arrays.asList("Filtrado", "Sevilla", "Madrid", "Barcelona", "Valencia");
        spinnerFiltro.setAdapter(new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, secciones));

        txtLatitud = findViewById(R.id.txtLatitud);
        txtLongitud = findViewById(R.id.txtLongitud);

        imageButton = findViewById(R.id.buttonLocalizacion);


        locationManager = (LocationManager) getSystemService(Service.LOCATION_SERVICE);
        isGPS = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        isNetwork = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
        permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);
        permissionsToRequest = findUnAskedPermissions(permissions);


        txtLatitud = findViewById(R.id.txtLatitud);
        txtLongitud = findViewById(R.id.txtLongitud);

       if (!sitioActivo.getNombre().toString().isEmpty()){

           txtnombre.setText(App.sitioActivo.getNombre());
           txtLongitud.setText(App.sitioActivo.getLongitud().toString());
           txtLatitud.setText(App.sitioActivo.getLatitud().toString());
           txtComentario.setText(App.sitioActivo.getComentarios());
           ratingBar2.setRating(App.sitioActivo.getValoracion());

       }else{

           txtnombre.setText("");
           txtLongitud.setText("");
           txtLatitud.setText("");
           txtComentario.setText("");

       }



        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.i("tag", txtnombre.getText().toString());

                if (!isGPS && !isNetwork) {
                    Log.i(TAG, "Conexión OFF");
                    showSettingsAlert();
                    getLastLocation();
                } else {
                    Log.i(TAG, "Conexión ON");
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (permissionsToRequest.size() > 0) {
                            requestPermissions(permissionsToRequest.toArray(new String[permissionsToRequest.size()]), ALL_PERMISSIONS_RESULT);
                            canGetLocation = false;
                        }
                    }
                    getLocation();
                }

            }
        });



    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(!txtnombre.getText().toString().isEmpty()){

            sitioActivo.setNombre(txtnombre.getText().toString());
            sitioActivo.setLatitud(Double.parseDouble(txtLatitud.getText().toString()));
            sitioActivo.setLongitud(Double.parseDouble(txtLongitud.getText().toString()));
            sitioActivo.setValoracion(ratingBar2.getRating());
            sitioActivo.setCiudad(spinnerFiltro.getSelectedItem().toString());
            sitioActivo.setComentarios(txtComentario.getText().toString());

            LogicSitio.insertarSitio(this, sitioActivo);
            finish();

        }




        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onLocationChanged(Location location) {
        updateUI(location);
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {
    }

    @Override
    public void onProviderEnabled(String s) {
        getLocation();
    }

    @Override
    public void onProviderDisabled(String s) {
        if (locationManager != null) {
            locationManager.removeUpdates(this);
        }
    }

    private void getLocation() {
        try {
            if (canGetLocation) {
                if (isGPS) { // recibiendo señal desde GPS_PROVIDER
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                    if (locationManager != null) {
                        loc = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                        if (loc != null)
                            updateUI(loc);
                    }
                } else if (isNetwork) { // recibiendo señal desde NETWORK_PROVIDER
                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                    if (locationManager != null) {
                        loc = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        if (loc != null)
                            updateUI(loc);
                    }
                } else {
                    loc.setLatitude(0);
                    loc.setLongitude(0);
                    updateUI(loc);
                }
            }
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    private void getLastLocation() {
        try {
            Criteria criteria = new Criteria();
            String provider = locationManager.getBestProvider(criteria, false);
            Location location = locationManager.getLastKnownLocation(provider);
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    private ArrayList findUnAskedPermissions(ArrayList<String> wanted) {
        ArrayList result = new ArrayList();
        for (String perm : wanted) {
            if (!hasPermission(perm)) {
                result.add(perm);
            }
        }
        return result;
    }

    private boolean hasPermission(String permission) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return (checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED);
        }
        return true;
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case ALL_PERMISSIONS_RESULT:
                for (String perms : permissionsToRequest) {
                    if (!hasPermission(perms)) {
                        permissionsRejected.add(perms);
                    }
                }
                if (permissionsRejected.size() > 0) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (shouldShowRequestPermissionRationale(permissionsRejected.get(0))) {
                            showMessageOKCancel("Se requiere permisos para ejecutar la aplicación.",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            requestPermissions(permissionsRejected.toArray(new String[permissionsRejected.size()]), ALL_PERMISSIONS_RESULT);
                                        }
                                    });
                            return;
                        }
                    }
                } else {
                    canGetLocation = true;
                    getLocation();
                }
                break;
        }
    }

    public void showSettingsAlert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("El GPS no está activado");
        alertDialog.setMessage("¿Quieres activar el GPS?");
        alertDialog.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
            }
        });
        alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        alertDialog.show();
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    private void updateUI(Location loc) {
        txtLatitud.setText(Double.toString(loc.getLatitude()));
        txtLongitud.setText(Double.toString(loc.getLongitude()));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (locationManager != null) {
            locationManager.removeUpdates(this);
        }
    }
}
