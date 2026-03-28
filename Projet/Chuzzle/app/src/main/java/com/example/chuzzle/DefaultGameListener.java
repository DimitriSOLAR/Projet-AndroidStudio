package com.example.chuzzle;

import android.view.View;

public class DefaultGameListener implements View.OnClickListener {

    private DefaultGameActivity activite;

    public DefaultGameListener(DefaultGameActivity activite) {
        this.activite = activite;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.bouton_fermer) {
            this.activite.finish();
        }
    }
}
