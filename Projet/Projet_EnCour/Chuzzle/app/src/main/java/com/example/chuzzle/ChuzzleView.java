package com.example.chuzzle;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class ChuzzleView extends View
{
    private ChuzzleModel modele;
    private Paint pinceau;
    private int[] couleursPions;

    public ChuzzleView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        this.modele = new ChuzzleModel(5);
        this.pinceau = new Paint();
        this.pinceau.setAntiAlias(true);
        
        this.couleursPions = new int[]{
            Color.RED, Color.parseColor("#3498db"), Color.parseColor("#2ecc71"), 
            Color.YELLOW, Color.parseColor("#9b59b6")
        };
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);
        int largeur = getWidth();
        int hauteur = getHeight();
        int tailleCase = Math.min(largeur, hauteur) / ChuzzleModel.TAILLE;

        for (int i = 0; i < ChuzzleModel.TAILLE; i++)
        {
            for (int j = 0; j < ChuzzleModel.TAILLE; j++)
            {
                int couleurIndex = modele.getCouleur(i, j);
                pinceau.setColor(couleursPions[couleurIndex]);
                
                float cx = j * tailleCase + tailleCase / 2f;
                float cy = i * tailleCase + tailleCase / 2f;
                float rayon = tailleCase / 2.5f;
                
                canvas.drawCircle(cx, cy, rayon, pinceau);
            }
        }
    }
    
    public ChuzzleModel getModele()
    {
        return modele;
    }
}
