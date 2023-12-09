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
        db.execSQL("CREATE TABLE score(username VARCHAR primary key, puntuacion INT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS score");
        onCreate(db);
    }

//    public Boolean insertarDatos(int puntuacion){
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues values = new ContentValues();
//
//        values.put("puntuacion", puntuacion);
//
//        long resultado = db.insert("score", null, values);
//        if (resultado == -1) return false;
//        else
//            return true;
//    }

    //metodo para insetar datos
    public Boolean insertarDatos(String username, int puntuacion){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("username", username);
        values.put("puntuacion", puntuacion);

        long resultado = db.insert("score", null, values);
        if (resultado == -1) return false;
        else
            return true;
    }

    //metodo para verificar usuario
    public Boolean checkUserName(String username){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from score where username = ?", new String[] {username});
        if (cursor.getCount()>0) return true;
        else
            return false;
    }

    //metodo para obtener la puntuacion mas alta
    public Cursor obtenerPuntuacion(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT username, MAX(puntuacion) FROM score", null);
        return cursor;
    }
}