package com.example.chuzzle;

import android.app.Activity;
import android.view.View;

public class RegleListener implements View.OnClickListener
{

    private Activity activite;

    public RegleListener(Activity activite) {
        this.activite = activite;
    }

    @Override
    public void onClick(View vue)
    {
        if (vue.getId() == R.id.bouton_retour_regles)
        {
            this.activite.finish();
        }
    }
}
