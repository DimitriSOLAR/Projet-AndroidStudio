package com.example.chuzzle;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

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
        
        appliquerTheme();
    }

    private void appliquerTheme() {
        SharedPreferences prefs = getSharedPreferences("ChuzzlePrefs", Context.MODE_PRIVATE);
        boolean estClair = prefs.getBoolean("modeClair", false);

        LinearLayout layout = findViewById(R.id.layout_regles);
        TextView titre = findViewById(R.id.titre_regles);
        TextView principeTitre = findViewById(R.id.titre_principe);
        TextView principeTexte = findViewById(R.id.texte_principe);
        Button bouton = findViewById(R.id.bouton_retour_regles);

        if (estClair) {
            layout.setBackgroundColor(Color.WHITE);
            titre.setTextColor(Color.BLACK);
            principeTitre.setTextColor(Color.BLACK);
            principeTexte.setTextColor(Color.DKGRAY);
            bouton.setBackgroundTintList(android.content.res.ColorStateList.valueOf(Color.LTGRAY));
            bouton.setTextColor(Color.BLACK);
        } else {
            layout.setBackgroundColor(Color.parseColor("#222222"));
            titre.setTextColor(Color.parseColor("#EEEEEE"));
            principeTitre.setTextColor(Color.parseColor("#EEEEEE"));
            principeTexte.setTextColor(Color.parseColor("#EEEEEE"));
            bouton.setBackgroundTintList(android.content.res.ColorStateList.valueOf(Color.parseColor("#222222")));
            bouton.setTextColor(Color.WHITE);
        }
    }
}
