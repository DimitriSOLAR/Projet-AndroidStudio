package com.example.chuzzle;

import java.util.Random;

public class ChuzzleModel
{
    public static final int TAILLE = 6;
    private int[][] grille;
    private int nbCouleurs;
    private int score;

    public ChuzzleModel(int nbCouleurs)
    {
        this.nbCouleurs = nbCouleurs;
        this.grille = new int[TAILLE][TAILLE];
        this.score = 0;
        this.initialiserGrille();
    }

    public void initialiserGrille()
    {
        Random random = new Random();
        for (int i = 0; i < TAILLE; i++)
        {
            for (int j = 0; j < TAILLE; j++)
            {
                this.grille[i][j] = random.nextInt(nbCouleurs);
            }
        }
    }

    public int getCouleur(int ligne, int col)
    {
        return this.grille[ligne][col];
    }

    public void setCouleur(int ligne, int col, int valeur)
    {
        this.grille[ligne][col] = valeur;
    }

    public int getScore()
    {
        return this.score;
    }

    public void glisserLigne(int ligne, int direction)
    {
        int[] temporaire = new int[TAILLE];
        for (int j = 0; j < TAILLE; j++)
        {
            int nouvellePos = (j + direction) % TAILLE;
            if (nouvellePos < 0)
            {
                nouvellePos += TAILLE;
            }
            temporaire[nouvellePos] = this.grille[ligne][j];
        }
        this.grille[ligne] = temporaire;
    }

    public void glisserColonne(int col, int direction)
    {
        int[] temporaire = new int[TAILLE];
        for (int i = 0; i < TAILLE; i++)
        {
            int nouvellePos = (i + direction) % TAILLE;
            if (nouvellePos < 0)
            {
                nouvellePos += TAILLE;
            }
            temporaire[nouvellePos] = this.grille[i][col];
        }
        for (int i = 0; i < TAILLE; i++)
        {
            this.grille[i][col] = temporaire[i];
        }
    }

    public boolean verifierMatch()
    {
        boolean trouve = false;
        boolean[][] aSupprimer = new boolean[TAILLE][TAILLE];

        for (int i = 0; i < TAILLE; i++) {
            for (int j = 0; j < TAILLE - 2; j++)
            {
                int couleur = this.grille[i][j];
                if (couleur == this.grille[i][j+1] && couleur == this.grille[i][j+2])
                {
                    aSupprimer[i][j] = true;
                    aSupprimer[i][j+1] = true;
                    aSupprimer[i][j+2] = true;
                    trouve = true;
                }
            }
        }

        for (int j = 0; j < TAILLE; j++)
        {
            for (int i = 0; i < TAILLE - 2; i++)
            {
                int couleur = this.grille[i][j];
                if (couleur == this.grille[i+1][j] && couleur == this.grille[i+2][j])
                {
                    aSupprimer[i][j] = true;
                    aSupprimer[i+1][j] = true;
                    aSupprimer[i+2][j] = true;
                    trouve = true;
                }
            }
        }

        if (trouve)
        {
            this.remplacerPions(aSupprimer);
        }
        return trouve;
    }

    private void remplacerPions(boolean[][] masque)
    {
        Random random = new Random();
        for (int i = 0; i < TAILLE; i++)
        {
            for (int j = 0; j < TAILLE; j++)
            {
                if (masque[i][j])
                {
                    this.grille[i][j] = random.nextInt(nbCouleurs);
                    this.score += 10;
                }
            }
        }
    }
}
