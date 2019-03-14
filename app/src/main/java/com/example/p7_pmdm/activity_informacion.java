package com.example.p7_pmdm;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.MenuBuilder;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;

import com.example.p7_pmdm.DataBaseManager.DB_SQLite;
import com.example.p7_pmdm.DataBaseManager.Esquema;
import com.example.p7_pmdm.Model.Sitio_pojo;

public class activity_informacion extends AppCompatActivity {

    Spinner spinnerFiltro;
    TextView infoNombre, infoLongitud, infoLatitud, infoComentario, dataCity;
    RatingBar ratingBar;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informacion);

        infoNombre = findViewById(R.id.infoNombre);
        infoLatitud = findViewById(R.id.infoLatitud);
        infoLongitud = findViewById(R.id.infoLongitud);
        infoComentario = findViewById(R.id.infoComentario);
        ratingBar = findViewById(R.id.ratingBar3);

        dataCity = findViewById(R.id.dataCity);

        infoNombre.setText(App.sitioActivo.getNombre());
        infoLongitud.setText(App.sitioActivo.getLongitud().toString());
        infoLatitud.setText(App.sitioActivo.getLatitud().toString());
        infoComentario.setText(App.sitioActivo.getComentarios());
        ratingBar.setRating(App.sitioActivo.getValoracion());
        dataCity.setText(App.sitioActivo.getCiudad());

    }

    @SuppressLint("RestrictedApi")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menuinfo, menu);
        MenuBuilder m = (MenuBuilder) menu;
        m.setOptionalIconsVisible(true);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId())
        {
            case R.id.opcion1:
                startActivity(new Intent(getApplicationContext(), activity_edicion.class));
                finish();
                break;
            case R.id.opcion2:
                Eliminar();
                finish();
                break;
        }


        return super.onOptionsItemSelected(item);
    }

    private void Eliminar()
    {
        DB_SQLite db = new DB_SQLite(this);
        SQLiteDatabase conn = db.getWritableDatabase();
        String sqlWhere = Esquema.Sitios.COLUMN_NAME_ID + " LIKE '" + App.sitioActivo.getId() + "'";
        conn.delete(Esquema.Sitios.TABLE_NAME, sqlWhere, null);
        conn.close();
    }
}
