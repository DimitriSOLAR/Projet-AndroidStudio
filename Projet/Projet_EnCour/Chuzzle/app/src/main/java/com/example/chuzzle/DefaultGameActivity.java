package com.example.chuzzle;

import android.app.Activity;
import android.os.Bundle;

public class DefaultGameActivity extends Activity
{

    private ChuzzleView grille;

    @Override
    protected void onCreate(Bundle etatSauvegarde)
    {
        super.onCreate(etatSauvegarde);
        setContentView(R.layout.activity_default_game);

        grille = findViewById(R.id.grille_jeu);
        

        ChuzzleTouchListener ecouteurTouch = new ChuzzleTouchListener(grille);
        grille.setOnTouchListener(ecouteurTouch);


        if (etatSauvegarde != null)
        {
            int[] donneesGrille = etatSauvegarde.getIntArray("grille_sauvegardee");
            if (donneesGrille != null)
            {
                ChuzzleModel modele = grille.getModele();
                int index = 0;
                for (int i = 0; i < ChuzzleModel.TAILLE; i++)
                {
                    for (int j = 0; j < ChuzzleModel.TAILLE; j++)
                    {
                        modele.setCouleur(i, j, donneesGrille[index++]);
                    }
                }
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);

        int[] donneesGrille = new int[ChuzzleModel.TAILLE * ChuzzleModel.TAILLE];
        ChuzzleModel modele = grille.getModele();
        int index = 0;
        for (int i = 0; i < ChuzzleModel.TAILLE; i++)
        {
            for (int j = 0; j < ChuzzleModel.TAILLE; j++)
            {
                donneesGrille[index++] = modele.getCouleur(i, j);
            }
        }
        outState.putIntArray("grille_sauvegardee", donneesGrille);
    }
}
