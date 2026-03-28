package com.example.chuzzle;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

public class OptionsActivity extends Activity
{
    private LinearLayout layout;
    private TextView titre, description;
    private Switch swHardMode, swModeClair;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        layout = findViewById(R.id.layout_options);
        titre = findViewById(R.id.titre_options);
        description = findViewById(R.id.description_hard_mode);
        Button boutonRetour = findViewById(R.id.bouton_retour_options);
        swHardMode = findViewById(R.id.interrupteur_hard_mode);
        swModeClair = findViewById(R.id.interrupteur_mode_clair);

        SharedPreferences prefs = getSharedPreferences("ChuzzlePrefs", Context.MODE_PRIVATE);
        
        // État Hard Mode
        swHardMode.setChecked(prefs.getBoolean("modeDifficile", false));
        
        // État Mode Clair
        boolean modeClair = prefs.getBoolean("modeClair", false);
        swModeClair.setChecked(modeClair);
        appliquerTheme(modeClair);

        OptionsListener ecouteur = new OptionsListener(this);

        boutonRetour.setOnClickListener(ecouteur);
        swHardMode.setOnCheckedChangeListener(ecouteur);
        swModeClair.setOnCheckedChangeListener(ecouteur);
    }

    public void appliquerTheme(boolean estClair) {
        if (estClair) {
            layout.setBackgroundColor(Color.WHITE);
            titre.setTextColor(Color.BLACK);
            description.setTextColor(Color.DKGRAY);
            swHardMode.setTextColor(Color.BLACK);
            swModeClair.setTextColor(Color.BLACK);
        } else {
            layout.setBackgroundColor(Color.parseColor("#222222"));
            titre.setTextColor(Color.parseColor("#EEEEEE"));
            description.setTextColor(Color.parseColor("#AAAAAA"));
            swHardMode.setTextColor(Color.parseColor("#EEEEEE"));
            swModeClair.setTextColor(Color.parseColor("#EEEEEE"));
        }
    }
}
