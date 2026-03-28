package com.example.chuzzle;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class SeedInputActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seed_input);

        // Liaison des composants
        EditText zoneSaisieGraine = (EditText) findViewById(R.id.edit_text_seed);
        Button boutonValider = (Button) findViewById(R.id.bouton_valider_seed);
        Button boutonRetour = (Button) findViewById(R.id.bouton_fermer_seed);

        // On utilise un ecouteur externe
        SeedInputListener ecouteur = new SeedInputListener(this, zoneSaisieGraine);
        
        boutonValider.setOnClickListener(ecouteur);
        boutonRetour.setOnClickListener(ecouteur);
    }
}
