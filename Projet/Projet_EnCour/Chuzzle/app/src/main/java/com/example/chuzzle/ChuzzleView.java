package com.example.chuzzle;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

public class ChuzzleView extends View {

    private ChuzzleModel modele;
    private Paint pinceau;

    public ChuzzleView(Context contexte, AttributeSet attributs) {
        super(contexte, attributs);
        pinceau = new Paint();
        pinceau.setAntiAlias(true);
    }

    public void setModele(ChuzzleModel modele) {
        this.modele = modele;
        invalidate(); // Force le redessin
    }

    // LA MÉTHODE MANQUANTE QUI CAUSAIT L'ERREUR !
    public ChuzzleModel getModele() {
        return this.modele;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (modele == null) {
            return;
        }

        float tailleCase = getWidth() / 6f;
        float rayon = (tailleCase / 2f) - 10f;

        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {

                float centreX = (i * tailleCase) + (tailleCase / 2f);
                float centreY = (j * tailleCase) + (tailleCase / 2f);

                int typeCase = modele.getCase(i, j);

                pinceau.setStyle(Paint.Style.FILL);
                pinceau.setStrokeWidth(0);

                switch (typeCase) {
                    case 0:
                        pinceau.setColor(Color.RED);
                        canvas.drawCircle(centreX, centreY, rayon, pinceau);
                        break;
                    case 1:
                        pinceau.setColor(Color.BLUE);
                        canvas.drawRect(centreX - rayon, centreY - rayon, centreX + rayon, centreY + rayon, pinceau);
                        break;
                    case 2:
                        pinceau.setColor(Color.GREEN);
                        Path triangleHaut = new Path();
                        triangleHaut.moveTo(centreX, centreY - rayon);
                        triangleHaut.lineTo(centreX + rayon, centreY + rayon);
                        triangleHaut.lineTo(centreX - rayon, centreY + rayon);
                        triangleHaut.close();
                        canvas.drawPath(triangleHaut, pinceau);
                        break;
                    case 3:
                        pinceau.setColor(Color.MAGENTA);
                        float epaisseur = rayon / 3f;
                        canvas.drawRect(centreX - epaisseur, centreY - rayon, centreX + epaisseur, centreY + rayon, pinceau);
                        canvas.drawRect(centreX - rayon, centreY - epaisseur, centreX + rayon, centreY + epaisseur, pinceau);
                        break;
                    case 4:
                        pinceau.setColor(Color.parseColor("#FFA500"));
                        Path losange = new Path();
                        losange.moveTo(centreX, centreY - rayon);
                        losange.lineTo(centreX + rayon, centreY);
                        losange.lineTo(centreX, centreY + rayon);
                        losange.lineTo(centreX - rayon, centreY);
                        losange.close();
                        canvas.drawPath(losange, pinceau);
                        break;
                    case 5:
                        pinceau.setColor(Color.CYAN);
                        Path triangleBas = new Path();
                        triangleBas.moveTo(centreX - rayon, centreY - rayon);
                        triangleBas.lineTo(centreX + rayon, centreY - rayon);
                        triangleBas.lineTo(centreX, centreY + rayon);
                        triangleBas.close();
                        canvas.drawPath(triangleBas, pinceau);
                        break;
                    case 6:
                        pinceau.setColor(Color.GRAY);
                        pinceau.setStyle(Paint.Style.STROKE);
                        pinceau.setStrokeWidth(12f);
                        canvas.drawCircle(centreX, centreY, rayon - 6f, pinceau);
                        break;
                    case -1:
                        // Case vide, on ne dessine rien
                        break;
                }
            }
        }
    }
}