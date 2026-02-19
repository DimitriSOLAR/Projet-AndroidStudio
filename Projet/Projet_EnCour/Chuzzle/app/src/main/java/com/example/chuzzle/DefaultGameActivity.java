package com.example.chuzzle;

import android.app.Activity;
import android.os.Bundle;

public class DefaultGameActivity extends Activity {

    @Override
    protected void onCreate(Bundle etatSauvegarde) {
        super.onCreate(etatSauvegarde);
        setContentView(R.layout.activity_default_game);
    }
}
