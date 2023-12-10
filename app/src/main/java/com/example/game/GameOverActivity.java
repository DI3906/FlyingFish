package com.example.game;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class GameOverActivity extends AppCompatActivity {

    private Button StartGameAgain, SaveData;

    private EditText nombre;

    private TextView Score, clasificacion;

    private String puntuacion;

    //base de datos
    //DBHelper DB;

    //vista en el activity
    private ListView listViewScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        puntuacion = getIntent().getExtras().get("score").toString();

        nombre =  (EditText) findViewById(R.id.userName);

        StartGameAgain = (Button) findViewById(R.id.btnPlayAgain);

        Score = (TextView) findViewById(R.id.puntaje);

        clasificacion = (TextView) findViewById(R.id.clasificacion);

        SaveData = (Button) findViewById(R.id.btnSaveData);
        //DB = new DBHelper(this);


        //evento para jugar otra vez
        StartGameAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainIntent = new Intent(GameOverActivity.this, MainActivity.class);
                startActivity(mainIntent);
            }
        });

        Score.setText("Tus puntos: " + puntuacion);

        //evento para mostrar clasificacion
        SaveData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = nombre.getText().toString().trim();

                if (TextUtils.isEmpty(user) ){
                    Toast.makeText(GameOverActivity.this, "Rellene todos los campos", Toast.LENGTH_SHORT).show();
                } else{
                    BaseDeDatos();
                    Toast.makeText(GameOverActivity.this, "Datos guardados", Toast.LENGTH_SHORT).show();
                }
            }
        });


        //METODO PARA LA BD
        DBHelper admin = new DBHelper(this, "BD", null, 1);
        SQLiteDatabase BD = admin.getWritableDatabase();

        Cursor consulta = BD.rawQuery(
                "SELECT * FROM puntaje WHERE score = (SELECT MAX(score) FROM puntaje)", null
        );

        if (consulta.moveToFirst()){
            String temp_nombre = consulta.getString(0);
            String temp_score = consulta.getString(1);

            clasificacion.setText("Record: " + temp_score + " de " + temp_nombre);

            //cerrar la BD
            BD.close();
        } else BD.close();
    }

    public void BaseDeDatos(){
        String user = nombre.getText().toString().trim();

        DBHelper admin = new DBHelper(this, "BD", null, 1);
        SQLiteDatabase BD = admin.getWritableDatabase();

        Cursor consulta = BD.rawQuery(
                "SELECT * FROM puntaje WHERE score = (SELECT MAX(score) FROM puntaje)", null
        );

        if (consulta.moveToFirst()){
            String temp_nombre = consulta.getString(0);
            String temp_score = consulta.getString(1);

            int puntuacionActual = Integer.parseInt(puntuacion);//conversion a entero la puntuacion q se obtiene en string
            int bestScore = Integer.parseInt(temp_score);

            if (puntuacionActual > bestScore){
                ContentValues modificacion = new ContentValues();
                modificacion.put("nombre", user);
                modificacion.put("score", puntuacion);

                BD.update("puntaje", modificacion, "score = " + bestScore, null);
            }
            BD.close();
        } else {
            ContentValues insertar = new ContentValues();

            insertar.put("nombre", user);
            insertar.put("score", puntuacion);

            BD.insert("puntaje", null, insertar);
            BD.close();
        }
    }
}