package com.example.game;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
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

        nombre =  (EditText) findViewById(R.id.userName);

        StartGameAgain = (Button) findViewById(R.id.btnPlayAgain);

        Score = (TextView) findViewById(R.id.puntaje);

        SaveData = (Button) findViewById(R.id.btnSaveData);
        DB = new DBHelper(this);


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

                boolean insercionCorrecta = DB.insertarDatos(user, Integer.parseInt(puntuacion));

                if (insercionCorrecta){
                    //Obtener la puntuacion mas alta
                    Cursor cursor = DB.obtenerPuntuacion();

                    if (cursor.moveToFirst()){
                        String nombreJugador = cursor.getString(0);
                        int puntuacionAlta = cursor.getInt(1);

                        // Mostrar la puntuacion mas alta a traves de un TextView
                        TextView textViewClasificacion = findViewById(R.id.clasificacion);
                        textViewClasificacion.setText(nombreJugador + "es el jugador con: " + puntuacionAlta + ", la mas alta.");
                    }
                    cursor.close();
                } else {
                    Toast.makeText(GameOverActivity.this, "Error", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
}