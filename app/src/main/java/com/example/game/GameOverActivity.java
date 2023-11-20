package com.example.game;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class GameOverActivity extends AppCompatActivity {

    private Button StartGameAgain, SaveData;
    private TextView Score;

    private String puntuacion;

    //base de datos
    DBHelper DB;

    //vista en el activity
    private ListView listViewScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        puntuacion = getIntent().getExtras().get("score").toString();

        StartGameAgain = (Button) findViewById(R.id.btnPlayAgain);

        Score = (TextView) findViewById(R.id.puntaje);

        SaveData = (Button) findViewById(R.id.btnSaveData);
        DB = new DBHelper(this);

        //vincular el tv
        //listViewScore = (ListView) findViewById(R.id.listViewScores);

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

                boolean insercionCorrecta = DB.insertarDatos(Integer.parseInt(puntuacion));

                if (insercionCorrecta){
                    Cursor cursor = DB.obtenerPuntuacion();

                    if (cursor.moveToFirst()){
                        int puntuacionAlta = cursor.getInt(1);

                        TextView textViewClasificacion = findViewById(R.id.clasificacion);
                        textViewClasificacion.setText("Puntuacion mas alta: " + puntuacionAlta);
                    }
                } else {
                    Toast.makeText(GameOverActivity.this, "Error", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
}