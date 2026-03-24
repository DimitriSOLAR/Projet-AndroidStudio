package com.example.chuzzle;

import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

public class ChuzzleTouchListener implements View.OnTouchListener {

    private ChuzzleView vue;
    private TextView vueScore; // NOUVEAU : Pour l'affichage des points
    private float xInitial, yInitial;

    public ChuzzleTouchListener(ChuzzleView vue) {
        this.vue = vue;
        this.vueScore = vueScore;
    }

    public ChuzzleTouchListener(ChuzzleView grille, TextView texteScore) {
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int action = event.getAction();

        if (action == MotionEvent.ACTION_DOWN) {
            xInitial = event.getX();
            yInitial = event.getY();
            return true;

        } else if (action == MotionEvent.ACTION_UP) {
            float xFinal = event.getX();
            float yFinal = event.getY();

            float deltaX = xFinal - xInitial;
            float deltaY = yFinal - yInitial;

            int tailleCase = Math.min(vue.getWidth(), vue.getHeight()) / ChuzzleModel.TAILLE;

            int colToucheeX = (int) (xInitial / tailleCase);
            int ligneToucheeY = (int) (yInitial / tailleCase);

            if (colToucheeX >= 0 && colToucheeX < ChuzzleModel.TAILLE &&
                    ligneToucheeY >= 0 && ligneToucheeY < ChuzzleModel.TAILLE) {

                ChuzzleModel modele = vue.getModele();

                if (Math.abs(deltaX) > Math.abs(deltaY)) {
                    // GLISSEMENT HORIZONTAL
                    if (Math.abs(deltaX) > tailleCase / 4) {
                        int sens = (deltaX > 0) ? 1 : -1;
                        modele.glisserLigne(ligneToucheeY, sens);

                        // Si le mouvement ne crée pas de série, on l'annule !
                        if (!modele.verifierMatch()) {
                            modele.glisserLigne(ligneToucheeY, -sens);
                        }
                    }
                } else {
                    // GLISSEMENT VERTICAL
                    if (Math.abs(deltaY) > tailleCase / 4) {
                        int sens = (deltaY > 0) ? 1 : -1;
                        modele.glisserColonne(colToucheeX, sens);

                        // Si le mouvement ne crée pas de série, on l'annule !
                        if (!modele.verifierMatch()) {
                            modele.glisserColonne(colToucheeX, -sens);
                        }
                    }
                }

                // 1. On redessine la grille
                vue.invalidate();
                // 2. On met à jour l'affichage du score en temps réel
                if (vueScore != null) {
                    vueScore.setText("Score: " + modele.getScore());
                }
            }
            return true;
        }
        return false;
    }
}