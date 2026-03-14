package com.example.chuzzle;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.CompoundButton;

public class OptionsListener implements View.OnClickListener, CompoundButton.OnCheckedChangeListener
{

    private OptionsActivity activite;

    public OptionsListener(OptionsActivity activite)
    {
        this.activite = activite;
    }

    @Override
    public void onCheckedChanged(CompoundButton bouton, boolean estCoche)
    {

        SharedPreferences prefs = activite.getSharedPreferences("ChuzzlePrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("modeDifficile", estCoche);
        editor.apply();
    }

    @Override
    public void onClick(View vue) {
        if (vue.getId() == R.id.bouton_retour_options)
        {
            this.activite.finish();
        }
    }
}
