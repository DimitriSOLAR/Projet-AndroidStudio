package com.example.chuzzle;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class DefaultGameActivity extends Activity {

    private ChuzzleView grille;

    @Override
    protected void onCreate(Bundle etatSauvegarde) {
        super.onCreate(etatSauvegarde);
        setContentView(R.layout.activity_default_game);

        // Liaison vue
        grille = findViewById(R.id.grille_jeu);
        TextView texteScore = findViewById(R.id.texte_score);

        // Initialisation
        ChuzzleModel modeleDuJeu = new ChuzzleModel();
        grille.setModele(modeleDuJeu);

        ChuzzleTouchListener ecouteurTouch = new ChuzzleTouchListener(grille, texteScore);
        grille.setOnTouchListener(ecouteurTouch);

        // Restauration de la sauvegarde
        if (etatSauvegarde != null) {
            int[] donneesGrille = etatSauvegarde.getIntArray("grille_sauvegardee");
            if (donneesGrille != null) {
                ChuzzleModel modele = grille.getModele();
                int index = 0;
                for (int x = 0; x < ChuzzleModel.TAILLE; x++) {
                    for (int y = 0; y < ChuzzleModel.TAILLE; y++) {
                        modele.setCouleur(x, y, donneesGrille[index++]);
                    }
                }
                texteScore.setText("Score: " + modele.getScore());
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        ChuzzleModel modele = grille.getModele();

        if (modele != null) {
            int[] donneesGrille = new int[ChuzzleModel.TAILLE * ChuzzleModel.TAILLE];
            int index = 0;
            for (int x = 0; x < ChuzzleModel.TAILLE; x++) {
                for (int y = 0; y < ChuzzleModel.TAILLE; y++) {
                    donneesGrille[index++] = modele.getCase(x, y);
                }
            }
            outState.putIntArray("grille_sauvegardee", donneesGrille);
        }
    }
}