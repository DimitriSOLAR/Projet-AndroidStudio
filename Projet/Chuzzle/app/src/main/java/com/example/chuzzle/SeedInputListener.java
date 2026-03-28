package com.example.chuzzle;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class SeedInputListener implements View.OnClickListener {

    private SeedInputActivity activite;
    private EditText editSeed;

    public SeedInputListener(SeedInputActivity activite, EditText editSeed) {
        this.activite = activite;
        this.editSeed = editSeed;
    }

    @Override
    public void onClick(View v) {
        // Bouton de validation
        if (v.getId() == R.id.bouton_valider_seed) {
            String seedStr = editSeed.getText().toString();
            
            if (seedStr.isEmpty()) {
                Toast.makeText(activite, "Entre une graine !", Toast.LENGTH_SHORT).show();
            } else {
                try {
                    long seed = Long.parseLong(seedStr);
                    Intent intent = new Intent(activite, DefaultGameActivity.class);
                    intent.putExtra("SEED", seed);
                    activite.startActivity(intent);
                    activite.finish();
                } catch (Exception e) {
                    Toast.makeText(activite, "Graine invalide", Toast.LENGTH_SHORT).show();
                }
            }
        }
        // Bouton de fermeture (croix)
        else if (v.getId() == R.id.bouton_fermer_seed) {
            this.activite.finish();
        }
    }
}
