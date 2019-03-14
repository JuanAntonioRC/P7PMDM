package com.example.p7_pmdm.Logic;

import android.content.ContentValues;
import android.content.Context;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Spinner;


import com.example.p7_pmdm.DataBaseManager.*;
import com.example.p7_pmdm.Model.Sitio_pojo;

import java.util.ArrayList;
import java.util.List;

public class LogicSitio
{
    public static void insertarSitio(Context context, Sitio_pojo p) {
        ContentValues content = new ContentValues();
        content.put(Esquema.Sitios.COLUMN_NAME_NOMBRE, p.getNombre());
        content.put(Esquema.Sitios.COLUMN_NAME_LATITUD, p.getLatitud());
        content.put(Esquema.Sitios.COLUMN_NAME_LONGITUD, p.getLongitud());
        content.put(Esquema.Sitios.COLUMN_NAME_COMENTARIOS, p.getComentarios());
        content.put(Esquema.Sitios.COLUMN_NAME_VALORACION, p.getValoracion());
        content.put(Esquema.Sitios.COLUMN_NAME_CIUDAD, p.getCiudad());
        SQLiteDatabase conn = DB_SQLite.conectar(context, DB_SQLite.OPEN_MODE_WRITE);
        conn.insert(Esquema.Sitios.TABLE_NAME, null, content);
        DB_SQLite.desconectar(conn);
    }

    public static void eliminarLugar(Context context, Sitio_pojo p) {
        String sqlWhere = Esquema.Sitios.COLUMN_NAME_ID + " = " + p.getId();
        SQLiteDatabase conn = DB_SQLite.conectar(context, DB_SQLite.OPEN_MODE_WRITE);
        conn.delete(Esquema.Sitios.TABLE_NAME, sqlWhere, null);
        DB_SQLite.desconectar(conn);
    }

    public static void editarLugar(Context context, Sitio_pojo p) {
        ContentValues content = new ContentValues();
        content.put(Esquema.Sitios.COLUMN_NAME_NOMBRE, p.getNombre());
        content.put(Esquema.Sitios.COLUMN_NAME_LATITUD, p.getLatitud());
        content.put(Esquema.Sitios.COLUMN_NAME_LONGITUD, p.getLongitud());
        content.put(Esquema.Sitios.COLUMN_NAME_COMENTARIOS, p.getComentarios());
        content.put(Esquema.Sitios.COLUMN_NAME_VALORACION, p.getValoracion());
        content.put(Esquema.Sitios.COLUMN_NAME_CIUDAD, p.getCiudad());
        String sqlWhere = Esquema.Sitios.COLUMN_NAME_ID + " = " + p.getId();
        SQLiteDatabase conn = DB_SQLite.conectar(context, DB_SQLite.OPEN_MODE_WRITE);
        conn.update(Esquema.Sitios.TABLE_NAME, content, sqlWhere, null);
        DB_SQLite.desconectar(conn);
    }

    public static List listaProductos(Context context) {
        List prod = new ArrayList<>();
        String[] sqlFields = {Esquema.Sitios.COLUMN_NAME_ID, Esquema.Sitios.COLUMN_NAME_NOMBRE, Esquema.Sitios.COLUMN_NAME_LONGITUD, Esquema.Sitios.COLUMN_NAME_LATITUD, Esquema.Sitios.COLUMN_NAME_CIUDAD, Esquema.Sitios.COLUMN_NAME_COMENTARIOS, Esquema.Sitios.COLUMN_NAME_VALORACION};
        String sqlWhere = "";
        String sqlOrderBy = Esquema.Sitios.COLUMN_NAME_NOMBRE + " ASC";

        SQLiteDatabase conn = DB_SQLite.conectar(context, DB_SQLite.OPEN_MODE_READ);
        Cursor cursor = conn.query(Esquema.Sitios.TABLE_NAME, sqlFields, sqlWhere, null, null, null, sqlOrderBy);
        if (cursor.getCount() == 0) {
            prod = null;
        } else {
            cursor.moveToFirst();
            do {
                Long dataId = cursor.getLong(cursor.getColumnIndex(Esquema.Sitios.COLUMN_NAME_ID));
                String dataNombre = cursor.getString(cursor.getColumnIndex(Esquema.Sitios.COLUMN_NAME_NOMBRE));
                Double dataLongitud = cursor.getDouble(cursor.getColumnIndex(Esquema.Sitios.COLUMN_NAME_LONGITUD));
                Double dataLatitud = cursor.getDouble(cursor.getColumnIndex(Esquema.Sitios.COLUMN_NAME_LATITUD));
                String dataComentario = cursor.getString(cursor.getColumnIndex(Esquema.Sitios.COLUMN_NAME_COMENTARIOS));
                Float dataFloat = cursor.getFloat(cursor.getColumnIndex(Esquema.Sitios.COLUMN_NAME_VALORACION));
                String dataCiudad = cursor.getString(cursor.getColumnIndex(Esquema.Sitios.COLUMN_NAME_CIUDAD));

                prod.add(new Sitio_pojo(dataId, dataNombre, dataLongitud, dataLatitud, dataComentario, dataFloat, dataCiudad));

            } while (cursor.moveToNext());
        }
        cursor.close();
        DB_SQLite.desconectar(conn);
        return prod;
    }

    public static List listaProductos1(Context context, Spinner spinner) {
        List prod = new ArrayList<>();
        String[] sqlFields = {Esquema.Sitios.COLUMN_NAME_ID, Esquema.Sitios.COLUMN_NAME_NOMBRE, Esquema.Sitios.COLUMN_NAME_LONGITUD, Esquema.Sitios.COLUMN_NAME_LATITUD, Esquema.Sitios.COLUMN_NAME_CIUDAD, Esquema.Sitios.COLUMN_NAME_COMENTARIOS, Esquema.Sitios.COLUMN_NAME_VALORACION};
        String sqlWhere = "ciudad='"+spinner.getSelectedItem().toString() + "'";
        String sqlOrderBy = Esquema.Sitios.COLUMN_NAME_NOMBRE + " ASC";

        SQLiteDatabase conn = DB_SQLite.conectar(context, DB_SQLite.OPEN_MODE_READ);
        Cursor cursor = conn.query(Esquema.Sitios.TABLE_NAME, sqlFields, sqlWhere, null, null, null, sqlOrderBy);
        if (cursor.getCount() == 0) {
            prod = null;
        } else {
            cursor.moveToFirst();
            do {
                Long dataId = cursor.getLong(cursor.getColumnIndex(Esquema.Sitios.COLUMN_NAME_ID));
                String dataNombre = cursor.getString(cursor.getColumnIndex(Esquema.Sitios.COLUMN_NAME_NOMBRE));
                Double dataLongitud = cursor.getDouble(cursor.getColumnIndex(Esquema.Sitios.COLUMN_NAME_LONGITUD));
                Double dataLatitud = cursor.getDouble(cursor.getColumnIndex(Esquema.Sitios.COLUMN_NAME_LATITUD));
                String dataComentario = cursor.getString(cursor.getColumnIndex(Esquema.Sitios.COLUMN_NAME_COMENTARIOS));
                Float dataFloat = cursor.getFloat(cursor.getColumnIndex(Esquema.Sitios.COLUMN_NAME_VALORACION));
                String dataCiudad = cursor.getString(cursor.getColumnIndex(Esquema.Sitios.COLUMN_NAME_CIUDAD));

                prod.add(new Sitio_pojo(dataId, dataNombre, dataLongitud, dataLatitud, dataComentario, dataFloat, dataCiudad));

            } while (cursor.moveToNext());
        }
        cursor.close();
        DB_SQLite.desconectar(conn);
        return prod;
    }


}
