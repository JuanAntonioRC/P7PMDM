package com.example.p7_pmdm;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.Image;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.p7_pmdm.DataBaseManager.Esquema;
import com.example.p7_pmdm.Logic.LogicSitio;
import com.example.p7_pmdm.Model.Sitio_pojo;
import com.example.p7_pmdm.activity_edicion;

import java.util.Arrays;
import java.util.List;

import static com.example.p7_pmdm.App.sitioActivo;

public class MainActivity extends AppCompatActivity {

    Spinner spinnerFiltro;

    FloatingActionButton buttonadd;

    Intent intent;
    Intent intent2;

    ImageView buttonWorld;

    public ListView listView;

    private static List<Sitio_pojo> lstSitios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinnerFiltro = findViewById(R.id.spinnerFiltro);

        buttonWorld = findViewById(R.id.imageView);

        List secciones = Arrays.asList("Filtrado", "Sevilla", "Madrid", "Barcelona", "Valencia");
        spinnerFiltro.setAdapter(new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, secciones));

        buttonadd = findViewById(R.id.floatingActionButton);
        intent = new Intent(this, activity_edicion.class);
         intent2 = new Intent(this, MapsActivity.class);

        buttonWorld.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(intent2);

            }
        });


        buttonadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(intent);

            }
        });

        listView = findViewById(R.id.card_listView);
        listView.addHeaderView(new View(this)); // añade espacio arriba de la primera card
        listView.addFooterView(new View(this)); // añade espacio debajo de la última card

        listView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView parent, View view, int position, long id) {
                        sitioActivo = lstSitios.get(position - 1);
                        App.accion = App.INFORMACION;
                        startActivity(new Intent(getApplicationContext(), activity_informacion.class));
                    }
                }
        );

      spinnerFiltro.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
          @Override
          public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

              if(spinnerFiltro.getSelectedItem().toString() == "Filtro"){

                  mostrartodo();

              }
              else{

                  mostrarSele();

              }

          }

          @Override
          public void onNothingSelected(AdapterView<?> parent) {

          }
      });

    }

    @Override
    protected void onResume() {
        super.onResume();
        CardAdapter listadoDeCards = new CardAdapter(getApplicationContext(), R.layout.list_item_card);

        lstSitios = LogicSitio.listaProductos(this);
        if (lstSitios == null) {
            Toast.makeText(this, "La base de datos está vacía.", Toast.LENGTH_LONG).show();
        } else {
            for (Sitio_pojo p : lstSitios) {
                listadoDeCards.add(p);
            }
            listView.setAdapter(listadoDeCards);
        }

    }

    public void mostrartodo(){

        CardAdapter listadoDeCards = new CardAdapter(getApplicationContext(), R.layout.list_item_card);

        lstSitios = LogicSitio.listaProductos(this);
        if (lstSitios == null) {
            Toast.makeText(this, "La base de datos está vacía.", Toast.LENGTH_LONG).show();
        } else {
            for (Sitio_pojo p : lstSitios) {
                listadoDeCards.add(p);
            }
            listView.setAdapter(listadoDeCards);
        }

    }

    public void mostrarSele(){

        CardAdapter listadoDeCards = new CardAdapter(getApplicationContext(), R.layout.list_item_card);

        lstSitios = LogicSitio.listaProductos1(this, spinnerFiltro);
        if (lstSitios == null) {
            Toast.makeText(this, "La base de datos está vacía.", Toast.LENGTH_LONG).show();
        } else {
            for (Sitio_pojo p : lstSitios) {
                listadoDeCards.add(p);
            }
            listView.setAdapter(listadoDeCards);
        }

    }

}