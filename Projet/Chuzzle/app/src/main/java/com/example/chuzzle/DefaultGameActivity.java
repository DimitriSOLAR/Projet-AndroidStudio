package com.example.chuzzle;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.view.View;

public class DefaultGameActivity extends Activity {

    private ChuzzleView grille;

    @Override
    protected void onCreate(Bundle etatSauvegarde) {
        super.onCreate(etatSauvegarde);
        setContentView(R.layout.activity_default_game);

        // Liaison des composants
        grille = (ChuzzleView) findViewById(R.id.grille_jeu);
        TextView texteScore = (TextView) findViewById(R.id.texte_score);
        TextView texteSeed = (TextView) findViewById(R.id.texte_seed);
        Button boutonFermer = (Button) findViewById(R.id.bouton_fermer);

        // Recupere la seed
        long seed = getIntent().getLongExtra("SEED", -1);
        
        ChuzzleModel modeleDuJeu;
        if (seed != -1) {
            modeleDuJeu = new ChuzzleModel(6, seed);
        } else {
            modeleDuJeu = new ChuzzleModel(6);
        }

        // Affiche la seed
        texteSeed.setText("Seed: " + modeleDuJeu.getSeed());
        texteSeed.setVisibility(View.VISIBLE);
        
        // Applique les preferences
        SharedPreferences preferencesDeConfiguration = getSharedPreferences("ChuzzlePrefs", Context.MODE_PRIVATE);
        boolean modeDifficile = preferencesDeConfiguration.getBoolean("modeDifficile", false);
        modeleDuJeu.setModeDifficile(modeDifficile);

        grille.setModele(modeleDuJeu);
        grille.setScoreView(texteScore);

        // Ecouteur pour fermer
        DefaultGameListener ecouteur = new DefaultGameListener(this);
        boutonFermer.setOnClickListener(ecouteur);

        // Restaure la sauvegarde du cycle de vie
        if (etatSauvegarde != null) {
            int[] donneesGrille = etatSauvegarde.getIntArray("grille_sauvegardee");
            if (donneesGrille != null) {
                ChuzzleModel modeleRecupere = grille.getModele();
                int index = 0;
                for (int x = 0; x < ChuzzleModel.TAILLE; x++) {
                    for (int y = 0; y < ChuzzleModel.TAILLE; y++) {
                        modeleRecupere.setCouleur(x, y, donneesGrille[index]);
                        index = index + 1;
                    }
                }
                texteScore.setText("Score: " + modeleRecupere.getScore());
            }
        }

        appliquerTheme();
    }

    // Change les couleurs selon le mode
    private void appliquerTheme() {
        SharedPreferences preferencesDeConfiguration = getSharedPreferences("ChuzzlePrefs", Context.MODE_PRIVATE);
        boolean estClair = preferencesDeConfiguration.getBoolean("modeClair", false);

        LinearLayout layoutJeu = (LinearLayout) findViewById(R.id.layout_game);
        TextView titreJeu = (TextView) findViewById(R.id.titre_jeu);
        TextView descriptionJeu = (TextView) findViewById(R.id.description_jeu);
        TextView scoreJeu = (TextView) findViewById(R.id.texte_score);
        TextView graineJeu = (TextView) findViewById(R.id.texte_seed);
        Button boutonQuitter = (Button) findViewById(R.id.bouton_fermer);

        if (estClair) {
            layoutJeu.setBackgroundColor(Color.WHITE);
            titreJeu.setTextColor(Color.BLACK);
            descriptionJeu.setTextColor(Color.DKGRAY);
            scoreJeu.setTextColor(Color.BLACK);
            graineJeu.setTextColor(Color.GRAY);
            grille.setBackgroundColor(Color.parseColor("#EEEEEE"));
        } else {
            layoutJeu.setBackgroundColor(Color.parseColor("#222222"));
            titreJeu.setTextColor(Color.parseColor("#EEEEEE"));
            descriptionJeu.setTextColor(Color.parseColor("#AAAAAA"));
            scoreJeu.setTextColor(Color.WHITE);
            graineJeu.setTextColor(Color.parseColor("#AAAAAA"));
            grille.setBackgroundColor(Color.parseColor("#333333"));
        }
    }

    // Sauvegarde avant destruction
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        ChuzzleModel modeleActuel = grille.getModele();
        if (modeleActuel != null) {
            int[] donneesGrille = new int[ChuzzleModel.TAILLE * ChuzzleModel.TAILLE];
            int index = 0;
            for (int x = 0; x < ChuzzleModel.TAILLE; x++) {
                for (int y = 0; y < ChuzzleModel.TAILLE; y++) {
                    donneesGrille[index] = modeleActuel.getCase(x, y);
                    index = index + 1;
                }
            }
            outState.putIntArray("grille_sauvegardee", donneesGrille);
        }
    }
}
