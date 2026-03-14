package com.example.chuzzle;

import android.view.MotionEvent;
import android.view.View;

public class ChuzzleTouchListener implements View.OnTouchListener
{
    private ChuzzleView vue;
    private float xInitial, yInitial;

    public ChuzzleTouchListener(ChuzzleView vue)
    {
        this.vue = vue;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event)
    {
        int action = event.getAction();
        
        if (action == MotionEvent.ACTION_DOWN)
        {
            xInitial = event.getX();
            yInitial = event.getY();
            return true;
        } else if (action == MotionEvent.ACTION_UP)
        {
            float xFinal = event.getX();
            float yFinal = event.getY();
            
            float deltaX = xFinal - xInitial;
            float deltaY = yFinal - yInitial;
            
            int tailleCase = Math.min(vue.getWidth(), vue.getHeight()) / ChuzzleModel.TAILLE;
            

            int colTouchee = (int) (xInitial / tailleCase);
            int ligneTouchee = (int) (yInitial / tailleCase);
            

            if (ligneTouchee >= 0 && ligneTouchee < ChuzzleModel.TAILLE && 
                colTouchee >= 0 && colTouchee < ChuzzleModel.TAILLE)
            {
                

                if (Math.abs(deltaX) > Math.abs(deltaY))
                {
                    if (Math.abs(deltaX) > tailleCase / 4)
                    {
                        int sens = (deltaX > 0) ? 1 : -1;
                        vue.getModele().glisserLigne(ligneTouchee, sens);
                    }
                } else {

                    if (Math.abs(deltaY) > tailleCase / 4)
                    {
                        int sens = (deltaY > 0) ? 1 : -1;
                        vue.getModele().glisserColonne(colTouchee, sens);
                    }
                }

                vue.invalidate();
            }
            return true;
        }
        return false;
    }
}
