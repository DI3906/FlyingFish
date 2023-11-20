package com.example.game;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DBNAME = "puntuacion.db";

    public DBHelper(Context context) {
        super(context, DBNAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE score(id INT PRIMARY KEY, puntuacion INT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS score");
        onCreate(db);
    }

    public Boolean insertarDatos(int puntuacion){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("puntuacion", puntuacion);

        long resultado = db.insert("score", null, values);
        if (resultado == -1) return false;
        else
            return true;
    }

    //metodo para mostrar los resultados
    //SELECT * ,MAX(puntuacion) FROM score ORDER BY id DESC LIMIT 5
    public Cursor obtenerPuntuacion(){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * , MAX(puntuacion) FROM score ORDER BY id DESC LIMIT 5";
        Cursor cursor = db.rawQuery(query, null);
        return cursor;
    }
}
