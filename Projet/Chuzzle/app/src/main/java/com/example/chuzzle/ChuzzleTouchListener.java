package com.example.chuzzle;

import android.view.MotionEvent;
import android.view.View;

public class ChuzzleTouchListener implements View.OnTouchListener {

    private ChuzzleView vue;
    private float initialX, initialY;
    private int ligneSelectionnee = -1;
    private int colonneSelectionnee = -1;
    private boolean estMouvementHorizontal = false;
    private boolean directionVerrouillee = false;

    public ChuzzleTouchListener(ChuzzleView vue) {
        this.vue = vue;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        ChuzzleModel model = vue.getModele();
        if (model == null) return false;

        float currentX = event.getX();
        float currentY = event.getY();
        int largeurCase = vue.getWidth() / ChuzzleModel.TAILLE;
        int hauteurCase = vue.getHeight() / ChuzzleModel.TAILLE;

        if (largeurCase <= 0 || hauteurCase <= 0) return true;

        int action = event.getAction();

        // Debut du toucher
        if (action == MotionEvent.ACTION_DOWN) {
            initialX = currentX;
            initialY = currentY;
            ligneSelectionnee = (int) (initialY / hauteurCase);
            colonneSelectionnee = (int) (initialX / largeurCase);
            directionVerrouillee = false;
        } 
        // Deplacement du doigt
        else if (action == MotionEvent.ACTION_MOVE) {
            float deltaX = currentX - initialX;
            float deltaY = currentY - initialY;

            if (!directionVerrouillee) {
                if (Math.abs(deltaX) > 20) {
                    estMouvementHorizontal = true;
                    directionVerrouillee = true;
                } else if (Math.abs(deltaY) > 20) {
                    estMouvementHorizontal = false;
                    directionVerrouillee = true;
                }
            }

            if (directionVerrouillee) {
                if (estMouvementHorizontal) {
                    if (ligneSelectionnee >= 0 && ligneSelectionnee < ChuzzleModel.TAILLE) {
                        if (deltaX > largeurCase) {
                            model.glisserLigne(ligneSelectionnee, 1);
                            initialX = currentX;
                        } else if (deltaX < -largeurCase) {
                            model.glisserLigne(ligneSelectionnee, -1);
                            initialX = currentX;
                        }
                    }
                } else {
                    if (colonneSelectionnee >= 0 && colonneSelectionnee < ChuzzleModel.TAILLE) {
                        if (deltaY > hauteurCase) {
                            model.glisserColonne(colonneSelectionnee, 1);
                            initialY = currentY;
                        } else if (deltaY < -hauteurCase) {
                            model.glisserColonne(colonneSelectionnee, -1);
                            initialY = currentY;
                        }
                    }
                }
                vue.invalidate();
            }
        } 
        // Fin du toucher
        else if (action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_CANCEL) {
            vue.rafraichirScore();
            directionVerrouillee = false;
            ligneSelectionnee = -1;
            colonneSelectionnee = -1;
        }
        return true;
    }
}
