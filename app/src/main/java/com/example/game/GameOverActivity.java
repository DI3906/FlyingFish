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

        listViewScore = (ListView) findViewById(R.id.listViewScores);

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
                    //si se realizo correctamente, mostrar los registros
                    Cursor cursor = DB.obtenerPuntuacion();

                    if (cursor.moveToFirst()){

                        do {

                            SimpleCursorAdapter adapter = new SimpleCursorAdapter(
                                    GameOverActivity.this,
                                    android.R.layout.simple_list_item_2, // Layout por defecto del ListView
                                    cursor,
                                    new String[]{"id", "puntuacion"}, // Columnas del cursor
                                    new int[]{android.R.id.text1, android.R.id.text2}, // IDs de las vistas en el layout
                                    0);

                            listViewScore.setAdapter(adapter);
                        } while (cursor.moveToNext());
                    }
                } else {
                    Toast.makeText(GameOverActivity.this, "Error", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
}