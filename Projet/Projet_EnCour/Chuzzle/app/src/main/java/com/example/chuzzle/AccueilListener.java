package com.example.chuzzle;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

public class AccueilListener implements View.OnClickListener
{
    private Activity activite;
    public AccueilListener (Activity activite)
    {
        this.activite = activite;
    }

    @Override
    public void onClick(View vue)
    {
        int idVue = vue.getId();


        if (idVue == R.id.bouton_partie_normale)
        {
            Intent intentionNormale = new Intent(activite, DefaultGameActivity.class);
            activite.startActivity(intentionNormale);

        } else if (idVue == R.id.bouton_partie_regle)
        {
            Intent intentionRegles = new Intent(activite, RegleActivity.class);
            activite.startActivity(intentionRegles);

        } else if (idVue == R.id.bouton_options)
        {
            Intent intentionOptions = new Intent(activite, OptionsActivity.class);
            activite.startActivity(intentionOptions);
        }
    }
}