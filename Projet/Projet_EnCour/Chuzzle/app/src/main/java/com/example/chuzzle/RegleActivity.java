package com.example.chuzzle;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class RegleActivity extends Activity {

    private Button boutonRetour;

    @Override
    protected void onCreate(Bundle etatSauvegarde) {
        super.onCreate(etatSauvegarde);
        setContentView(R.layout.activity_regles);

        boutonRetour = findViewById(R.id.bouton_retour_regles);

        // Bouton pour fermer les règles (sans lambda)
        boutonRetour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View vue) {
                finish(); // Ferme l'activité et retourne au menu
            }
        });
    }
}
