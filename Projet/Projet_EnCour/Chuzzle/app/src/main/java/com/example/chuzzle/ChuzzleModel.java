package com.example.chuzzle;

import java.util.Random;

public class ChuzzleModel {

    public static final int TAILLE = 6;
    private int[][] grille; // [col][lig]
    private int nbCouleurs;
    private int score;
    private Random generateurAleatoire;

    public ChuzzleModel() {
        this(7);
    }

    public ChuzzleModel(int nbCouleurs) {
        this.nbCouleurs = nbCouleurs;
        this.grille = new int[TAILLE][TAILLE];
        this.score = 0;
        this.generateurAleatoire = new Random();
        this.initialiserGrille();
    }

    public void initialiserGrille() {
        for (int x = 0; x < TAILLE; x++) {
            for (int y = 0; y < TAILLE; y++) {
                int couleur;
                do {
                    couleur = generateurAleatoire.nextInt(nbCouleurs);
                } while (
                        (x >= 2 && this.grille[x-1][y] == couleur && this.grille[x-2][y] == couleur) ||
                                (y >= 2 && this.grille[x][y-1] == couleur && this.grille[x][y-2] == couleur)
                );
                this.grille[x][y] = couleur;
            }
        }
    }

    public int getCase(int x, int y) {
        return this.grille[x][y];
    }

    public void setCouleur(int x, int y, int valeur) {
        this.grille[x][y] = valeur;
    }

    public int getScore() {
        return this.score;
    }
    public void glisserLigne(int ligneY, int direction) {
        int[] temporaire = new int[TAILLE];
        for (int x = 0; x < TAILLE; x++) {
            int nouvellePosX = (x + direction) % TAILLE;
            if (nouvellePosX < 0) {
                nouvellePosX += TAILLE;
            }
            temporaire[nouvellePosX] = this.grille[x][ligneY];
        }
        for (int x = 0; x < TAILLE; x++) {
            this.grille[x][ligneY] = temporaire[x];
        }
    }

    public void glisserColonne(int colonneX, int direction) {
        int[] temporaire = new int[TAILLE];
        for (int y = 0; y < TAILLE; y++) {
            int nouvellePosY = (y + direction) % TAILLE;
            if (nouvellePosY < 0) {
                nouvellePosY += TAILLE;
            }
            temporaire[nouvellePosY] = this.grille[colonneX][y];
        }
        for (int y = 0; y < TAILLE; y++) {
            this.grille[colonneX][y] = temporaire[y];
        }
    }
    public boolean verifierMatch() {
        boolean trouve = false;
        boolean[][] aSupprimer = new boolean[TAILLE][TAILLE];

        // Vérification Horizontale
        for (int y = 0; y < TAILLE; y++) {
            for (int x = 0; x < TAILLE - 2; x++) {
                int couleur = this.grille[x][y];
                if (couleur != -1 && couleur == this.grille[x+1][y] && couleur == this.grille[x+2][y]) {
                    aSupprimer[x][y] = true;
                    aSupprimer[x+1][y] = true;
                    aSupprimer[x+2][y] = true;
                    trouve = true;
                }
            }
        }

        // Vérification Verticale
        for (int x = 0; x < TAILLE; x++) {
            for (int y = 0; y < TAILLE - 2; y++) {
                int couleur = this.grille[x][y];
                if (couleur != -1 && couleur == this.grille[x][y+1] && couleur == this.grille[x][y+2]) {
                    aSupprimer[x][y] = true;
                    aSupprimer[x][y+1] = true;
                    aSupprimer[x][y+2] = true;
                    trouve = true;
                }
            }
        }

        if (trouve) {
            this.remplacerPions(aSupprimer);
        }
        return trouve;
    }

    private void remplacerPions(boolean[][] masque) {

        for (int x = 0; x < TAILLE; x++) {
            for (int y = 0; y < TAILLE; y++) {
                if (masque[x][y]) {
                    this.grille[x][y] = -1;
                    this.score += 8; // Score
                }
            }
        }

        for (int x = 0; x < TAILLE; x++) {

            for (int y = TAILLE - 1; y >= 0; y--) {
                if (this.grille[x][y] == -1) {
                    for (int k = y - 1; k >= 0; k--) {
                        if (this.grille[x][k] != -1) {
                            this.grille[x][y] = this.grille[x][k];
                            this.grille[x][k] = -1;
                            break; // bouche-trou
                        }
                    }
                }
            }
        }
        for (int x = 0; x < TAILLE; x++) {
            for (int y = 0; y < TAILLE; y++) {
                if (this.grille[x][y] == -1) {
                    this.grille[x][y] = generateurAleatoire.nextInt(nbCouleurs);
                }
            }
        }
        this.verifierMatch();
    }
}