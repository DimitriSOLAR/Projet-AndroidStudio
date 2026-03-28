package com.example.chuzzle;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ChuzzleModel {

    public static final int TAILLE = 6;
    private int chanceVerrou = 6;
    private int[][] grille; 
    private int nbCouleurs;
    private int score;
    private Random generateurAleatoire;
    private long seed;

    // Constructeur sans graine
    public ChuzzleModel() {
        this(6);
    }

    // Constructeur avec couleurs
    public ChuzzleModel(int nbCouleurs) {
        Random generateurTemporaire = new Random();
        long graineGeneree = generateurTemporaire.nextLong();
        if (graineGeneree < 0) {
            graineGeneree = -graineGeneree;
        }
        this.nbCouleurs = nbCouleurs;
        this.grille = new int[TAILLE][TAILLE];
        this.score = 0;
        this.seed = graineGeneree;
        this.generateurAleatoire = new Random(this.seed);
        this.initialiserGrille();
    }

    // Constructeur avec graine
    public ChuzzleModel(int nbCouleurs, long seed) {
        if (seed < 0) {
            seed = -seed;
        }
        this.nbCouleurs = nbCouleurs;
        this.grille = new int[TAILLE][TAILLE];
        this.score = 0;
        this.seed = seed;
        this.generateurAleatoire = new Random(this.seed);
        this.initialiserGrille();
    }

    // Change la difficulte
    public void setModeDifficile(boolean modeDifficile) {
        if (modeDifficile) {
            this.chanceVerrou = 21;
        } else {
            this.chanceVerrou = 6;
        }
    }

    // Remplit la grille au debut
    public void initialiserGrille() {
        for (int x = 0; x < TAILLE; x++) {
            for (int y = 0; y < TAILLE; y++) {
                int couleur = -1;
                boolean estValide = false;
                
                while (estValide == false) {
                    estValide = true;
                    couleur = generateurAleatoire.nextInt(nbCouleurs);
                    
                    if (x >= 2) {
                        if (this.grille[x-1][y] == couleur && this.grille[x-2][y] == couleur) {
                            estValide = false;
                        }
                    }
                    if (y >= 2) {
                        if (this.grille[x][y-1] == couleur && this.grille[x][y-2] == couleur) {
                            estValide = false;
                        }
                    }
                }
                this.grille[x][y] = couleur;
            }
        }
    }

    // Donne la valeur d une case
    public int getCase(int x, int y) {
        if (x < 0 || x >= TAILLE || y < 0 || y >= TAILLE) {
            return -1;
        }
        return this.grille[x][y];
    }

    // Change la couleur d une case
    public void setCouleur(int x, int y, int couleur) {
        if (x >= 0 && x < TAILLE && y >= 0 && y < TAILLE) {
            this.grille[x][y] = couleur;
        }
    }

    // Donne le score actuel
    public int getScore() {
        return this.score;
    }

    // Donne la graine de la partie
    public long getSeed() {
        return seed;
    }

    // Ajoute un verrou sur une case
    public void addVerrou(int x, int y){
        if (x >= 0 && x < TAILLE && y >= 0 && y < TAILLE) {
            if (this.grille[x][y] < 10 && this.grille[x][y] != -1) {
                this.grille[x][y] = this.grille[x][y] + 10;
            }
        }
    }

    // Verifie si la ligne a un verrou
    public boolean estLigneVerrouillee(int y) {
        if (y < 0 || y >= TAILLE) {
            return false;
        }
        for (int x = 0; x < TAILLE; x++) {
            if (this.grille[x][y] >= 10) {
                return true;
            }
        }
        return false;
    }

    // Verifie si la colonne a un verrou
    public boolean estColonneVerrouillee(int x) {
        if (x < 0 || x >= TAILLE) {
            return false;
        }
        for (int y = 0; y < TAILLE; y++) {
            if (this.grille[x][y] >= 10) {
                return true;
            }
        }
        return false;
    }

    // Deplace une ligne
    public void glisserLigne(int ligneY, int direction) {
        if (ligneY < 0 || ligneY >= TAILLE) {
            return;
        }
        if (estLigneVerrouillee(ligneY)) {
            return;
        }

        int[] tableauTemporaire = new int[TAILLE];
        for (int x = 0; x < TAILLE; x++) {
            int nouvellePosition = (x + direction) % TAILLE;
            if (nouvellePosition < 0) {
                nouvellePosition = nouvellePosition + TAILLE;
            }
            tableauTemporaire[nouvellePosition] = this.grille[x][ligneY];
        }
        for (int x = 0; x < TAILLE; x++) {
            this.grille[x][ligneY] = tableauTemporaire[x];
        }
    }

    // Deplace une colonne
    public void glisserColonne(int colonneX, int direction) {
        if (colonneX < 0 || colonneX >= TAILLE) {
            return;
        }
        if (estColonneVerrouillee(colonneX)) {
            return;
        }

        int[] tableauTemporaire = new int[TAILLE];
        for (int y = 0; y < TAILLE; y++) {
            int nouvellePosition = (y + direction) % TAILLE;
            if (nouvellePosition < 0) {
                nouvellePosition = nouvellePosition + TAILLE;
            }
            tableauTemporaire[nouvellePosition] = this.grille[colonneX][y];
        }
        for (int y = 0; y < TAILLE; y++) {
            this.grille[colonneX][y] = tableauTemporaire[y];
        }
    }

    // Verifie les alignements
    public boolean verifierMatch() {
        return verifierMatchCascade(0);
    }

    // Gere les cascades et le score
    private boolean verifierMatchCascade(int nombreSeriesAvant) {
        boolean[][] masqueSuppression = new boolean[TAILLE][TAILLE];
        List<Integer> listeLongueurs = new ArrayList<Integer>();

        // Verification horizontale
        for (int y = 0; y < TAILLE; y++) {
            int x = 0;
            while (x < TAILLE) {
                int couleurCase = this.grille[x][y] % 10;
                if (couleurCase == -1) {
                    x = x + 1;
                    continue;
                }
                int compteur = 1;
                while (x + compteur < TAILLE && (this.grille[x + compteur][y] % 10) == couleurCase) {
                    compteur = compteur + 1;
                }
                if (compteur >= 3) {
                    listeLongueurs.add(compteur);
                    for (int i = 0; i < compteur; i++) {
                        masqueSuppression[x + i][y] = true;
                    }
                }
                x = x + compteur;
            }
        }

        // Verification verticale
        for (int x = 0; x < TAILLE; x++) {
            int y = 0;
            while (y < TAILLE) {
                int couleurCase = this.grille[x][y] % 10;
                if (couleurCase == -1) {
                    y = y + 1;
                    continue;
                }
                int compteur = 1;
                while (y + compteur < TAILLE && (this.grille[x][y + compteur] % 10) == couleurCase) {
                    compteur = compteur + 1;
                }
                if (compteur >= 3) {
                    listeLongueurs.add(compteur);
                    for (int i = 0; i < compteur; i++) {
                        masqueSuppression[x][y + i] = true;
                    }
                }
                y = y + compteur;
            }
        }

        if (listeLongueurs.size() == 0) {
            return false;
        }

        // Calcul des points de base
        int pointsDeBase = 0;
        for (int i = 0; i < listeLongueurs.size(); i++) {
            int longueur = listeLongueurs.get(i);
            if (longueur == 3) {
                pointsDeBase = pointsDeBase + 8;
            } else if (longueur == 4) {
                pointsDeBase = pointsDeBase + 16;
            } else if (longueur == 5) {
                pointsDeBase = pointsDeBase + 32;
            } else {
                pointsDeBase = pointsDeBase + 64;
            }
        }

        // Calcul du score final avec bonus
        int nombreTotalSeries = nombreSeriesAvant + listeLongueurs.size();
        double multiplicateurBonus = 1.0 + (0.5 * (nombreTotalSeries - 1));
        this.score = this.score + (int)(pointsDeBase * multiplicateurBonus);

        remplacerPions(masqueSuppression);
        
        // Relance pour les cascades
        verifierMatchCascade(nombreTotalSeries);
        
        return true;
    }

    // Enleve les pions et fait tomber les autres
    private void remplacerPions(boolean[][] masqueSuppression) {
        for (int x = 0; x < TAILLE; x++) {
            for (int y = 0; y < TAILLE; y++) {
                if (masqueSuppression[x][y]) {
                    this.grille[x][y] = -1;
                }
            }
        }

        // Faire tomber les pions restants
        for (int x = 0; x < TAILLE; x++) {
            for (int y = TAILLE - 1; y >= 0; y--) {
                if (this.grille[x][y] == -1) {
                    for (int k = y - 1; k >= 0; k--) {
                        if (this.grille[x][k] != -1) {
                            this.grille[x][y] = this.grille[x][k];
                            this.grille[x][k] = -1;
                            break;
                        }
                    }
                }
            }
        }

        // Ajouter de nouveaux pions
        for (int x = 0; x < TAILLE; x++) {
            for (int y = 0; y < TAILLE; y++) {
                if (this.grille[x][y] == -1) {
                    this.grille[x][y] = generateurAleatoire.nextInt(nbCouleurs);
                    if (generateurAleatoire.nextInt(100) < chanceVerrou) {
                        addVerrou(x, y);
                    }
                }
            }
        }
    }
}
