package com.example.chuzzle;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends Activity
{
    @Override
    protected void onCreate(Bundle etatSauvegarde)
    {
        super.onCreate(etatSauvegarde);
        setContentView(R.layout.activity_main);

        Button boutonNormale = findViewById(R.id.bouton_partie_normale);
        Button boutonDestinee = findViewById(R.id.bouton_partie_destinee);
        Button boutonRegle = findViewById(R.id.bouton_partie_regle);
        Button boutonOptions = findViewById(R.id.bouton_options);

        AccueilListener ecouteur = new AccueilListener(this);
        
        boutonNormale.setOnClickListener(ecouteur);
        boutonDestinee.setOnClickListener(ecouteur);
        boutonRegle.setOnClickListener(ecouteur);
        boutonOptions.setOnClickListener(ecouteur);
    }

    @Override
    protected void onResume() {
        super.onResume();
        appliquerTheme();
    }

    // Change les couleurs de l'accueil
    private void appliquerTheme() {
        SharedPreferences preferences = getSharedPreferences("ChuzzlePrefs", Context.MODE_PRIVATE);
        boolean estClair = preferences.getBoolean("modeClair", false);

        LinearLayout layoutPrincipal = findViewById(R.id.layout_main);
        TextView titrePrincipal = findViewById(R.id.titre_main);
        Button bouton1 = findViewById(R.id.bouton_partie_normale);
        Button bouton2 = findViewById(R.id.bouton_partie_destinee);
        Button bouton3 = findViewById(R.id.bouton_partie_regle);
        Button bouton4 = findViewById(R.id.bouton_options);

        if (layoutPrincipal == null) {
            return;
        }

        if (estClair) {
            layoutPrincipal.setBackgroundColor(Color.WHITE);
            if (titrePrincipal != null) {
                titrePrincipal.setTextColor(Color.BLACK);
            }
            bouton1.setBackgroundTintList(android.content.res.ColorStateList.valueOf(Color.LTGRAY));
            bouton1.setTextColor(Color.BLACK);
            bouton2.setBackgroundTintList(android.content.res.ColorStateList.valueOf(Color.LTGRAY));
            bouton2.setTextColor(Color.BLACK);
            bouton3.setBackgroundTintList(android.content.res.ColorStateList.valueOf(Color.LTGRAY));
            bouton3.setTextColor(Color.BLACK);
            bouton4.setBackgroundTintList(android.content.res.ColorStateList.valueOf(Color.LTGRAY));
            bouton4.setTextColor(Color.BLACK);
        } else {
            layoutPrincipal.setBackgroundColor(Color.parseColor("#222222"));
            if (titrePrincipal != null) {
                titrePrincipal.setTextColor(Color.WHITE);
            }
            bouton1.setBackgroundTintList(android.content.res.ColorStateList.valueOf(Color.parseColor("#222222")));
            bouton1.setTextColor(Color.WHITE);
            bouton2.setBackgroundTintList(android.content.res.ColorStateList.valueOf(Color.parseColor("#222222")));
            bouton2.setTextColor(Color.WHITE);
            bouton3.setBackgroundTintList(android.content.res.ColorStateList.valueOf(Color.parseColor("#222222")));
            bouton3.setTextColor(Color.WHITE);
            bouton4.setBackgroundTintList(android.content.res.ColorStateList.valueOf(Color.parseColor("#222222")));
            bouton4.setTextColor(Color.WHITE);
        }
    }
}
