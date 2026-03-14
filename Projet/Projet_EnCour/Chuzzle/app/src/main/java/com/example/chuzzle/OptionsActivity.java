package com.example.chuzzle;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Switch;

public class OptionsActivity extends Activity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        Button boutonRetour = findViewById(R.id.bouton_retour_options);
        Switch interrupteurHardMode = findViewById(R.id.interrupteur_hard_mode);


        SharedPreferences prefs = getSharedPreferences("ChuzzlePrefs", Context.MODE_PRIVATE);
        boolean modeDifficile = prefs.getBoolean("modeDifficile", false);
        interrupteurHardMode.setChecked(modeDifficile);

        OptionsListener ecouteur = new OptionsListener(this);

        boutonRetour.setOnClickListener(ecouteur);
        interrupteurHardMode.setOnCheckedChangeListener(ecouteur);
    }
}
