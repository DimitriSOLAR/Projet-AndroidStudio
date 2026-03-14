package com.example.chuzzle;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends Activity
{

    @Override
    protected void onCreate(Bundle etatSauvegarde)
    {
        super.onCreate(etatSauvegarde);
        setContentView(R.layout.activity_main);

        Button boutonNormale = findViewById(R.id.bouton_partie_normale);
        Button boutonRegle = findViewById(R.id.bouton_partie_regle);
        Button boutonOptions = findViewById(R.id.bouton_options);


        AccueilListener ecouteur = new AccueilListener(this);
        
        boutonNormale.setOnClickListener(ecouteur);
        boutonRegle.setOnClickListener(ecouteur);
        boutonOptions.setOnClickListener(ecouteur);
    }
}
