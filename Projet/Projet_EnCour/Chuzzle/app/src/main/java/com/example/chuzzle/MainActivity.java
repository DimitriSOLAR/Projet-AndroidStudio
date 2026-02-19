package com.example.chuzzle;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle etatSauvegarde) {
        super.onCreate(etatSauvegarde);
        setContentView(R.layout.activity_main);

        // Liaison avec l'interface
        Button boutonNormale = findViewById(R.id.bouton_partie_normale);
        Button boutonRegle = findViewById(R.id.bouton_partie_regle);
        Button boutonOptions = findViewById(R.id.bouton_options);

        boutonNormale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View vue) {
                Intent actiondefaut = new Intent(MainActivity.this, DefaultGameActivity.class);
                startActivity(actiondefaut);
            }
        });

        boutonRegle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View vue) {
                Intent afficherRegles = new Intent(MainActivity.this, RegleActivity.class);
                startActivity(afficherRegles);
            }
        });

        boutonOptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View vue) {
                Intent intentionOptions = new Intent(MainActivity.this, OptionsActivity.class);
                startActivity(intentionOptions);
            }
        });
    }
}