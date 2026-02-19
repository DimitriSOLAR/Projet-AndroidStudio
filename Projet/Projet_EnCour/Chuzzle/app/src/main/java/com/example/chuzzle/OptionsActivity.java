package com.example.chuzzle;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;

public class OptionsActivity extends Activity {

    private Button boutonRetour;
    private Switch interrupteurHardMode;
    private boolean modeDifficileActif;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        // Liaison avec les éléments du fichier XML
        boutonRetour = findViewById(R.id.bouton_retour_options);
        interrupteurHardMode = findViewById(R.id.interrupteur_hard_mode);

        // Écouteur pour l'interrupteur (exactement comme ton ancien code)
        interrupteurHardMode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean estCoche) {
                if(estCoche) {
                    modeDifficileActif = true;
                } else {
                    modeDifficileActif = false;
                }
            }
        });

        // Action du bouton de retour
        boutonRetour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // On prépare une intention pour renvoyer la donnée à l'accueil
                Intent intentionResultat = new Intent();
                intentionResultat.putExtra("modeDifficile", modeDifficileActif);

                // On fixe le résultat (le code 2 est celui que tu utilisais)
                setResult(2, intentionResultat);

                // On ferme la fenêtre
                finish();
            }
        });
    }
}
