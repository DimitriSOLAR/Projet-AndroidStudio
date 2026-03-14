package com.example.chuzzle;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;

public class RegleActivity extends Activity
{

    @Override
    protected void onCreate(Bundle etatSauvegarde)
    {
        super.onCreate(etatSauvegarde);
        setContentView(R.layout.activity_regles);

        Button boutonRetour = findViewById(R.id.bouton_retour_regles);
        RegleListener ecouteur = new RegleListener(this);
        boutonRetour.setOnClickListener(ecouteur);
    }
}
