package com.example.chuzzle;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

public class ChuzzleView extends View {

    private ChuzzleModel model;
    private TextView scoreView;
    private final Paint paint = new Paint();
    private final Path path = new Path();
    private Bitmap imageVerrou;
    private final Rect rectSource = new Rect();
    private final Rect rectDest = new Rect();

    public ChuzzleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint.setAntiAlias(true);

        try {
            imageVerrou = BitmapFactory.decodeResource(getResources(), R.drawable.cadenas);
        } catch (Exception e) {
            imageVerrou = null;
        }

        // Ecouteur tactile externe
        ChuzzleTouchListener ecouteur = new ChuzzleTouchListener(this);
        this.setOnTouchListener(ecouteur);
    }

    public void setModele(ChuzzleModel model) {
        this.model = model;
        invalidate();
    }

    public ChuzzleModel getModele() {
        return model;
    }

    public void setScoreView(TextView scoreView) {
        this.scoreView = scoreView;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (model == null) return;

        int larg = getWidth() / ChuzzleModel.TAILLE;
        int haut = getHeight() / ChuzzleModel.TAILLE;

        for (int x = 0; x < ChuzzleModel.TAILLE; x++) {
            for (int y = 0; y < ChuzzleModel.TAILLE; y++) {
                int val = model.getCase(x, y);
                int type = val % 10;
                
                paint.setColor(getCouleur(type));
                paint.setStyle(Paint.Style.FILL);

                float l = x * larg + 4;
                float t = y * haut + 4;
                float r = (x + 1) * larg - 4;
                float b = (y + 1) * haut - 4;
                float cx = (l + r) / 2;
                float cy = (t + b) / 2;
                float rad = (r - l) / 2;

                // Dessine les formes
                if (type == 0) {
                    canvas.drawCircle(cx, cy, rad, paint);
                } else if (type == 1) {
                    canvas.drawRect(l, t, r, b, paint);
                } else if (type == 2) {
                    path.reset();
                    path.moveTo(cx, t);
                    path.lineTo(r, b);
                    path.lineTo(l, b);
                    path.close();
                    canvas.drawPath(path, paint);
                } else if (type == 3) {
                    path.reset();
                    path.moveTo(cx, t);
                    path.lineTo(r, cy);
                    path.lineTo(cx, b);
                    path.lineTo(l, cy);
                    path.close();
                    canvas.drawPath(path, paint);
                } else if (type == 4) {
                    canvas.drawRect(cx - 10, t, cx + 10, b, paint);
                    canvas.drawRect(l, cy - 10, r, cy + 10, paint);
                } else if (type == 5) {
                    path.reset();
                    path.moveTo(cx, t);
                    path.lineTo(r, cy);
                    path.lineTo(r - 15, b);
                    path.lineTo(l + 15, b);
                    path.lineTo(l, cy);
                    path.close();
                    canvas.drawPath(path, paint);
                } else if (type == 6) {
                    canvas.drawCircle(cx, cy, rad, paint);
                    paint.setColor(Color.WHITE);
                    canvas.drawCircle(cx, cy, rad / 2, paint);
                }

                // Dessine le cadenas
                if (val >= 10) {
                    if (imageVerrou != null) {
                        rectSource.set(0, 0, imageVerrou.getWidth(), imageVerrou.getHeight());
                        rectDest.set((int)l + 2, (int)t + 2, (int)r - 2, (int)b - 2);
                        canvas.drawBitmap(imageVerrou, rectSource, rectDest, null);
                    } else {
                        paint.setColor(Color.BLACK);
                        paint.setStrokeWidth(12);
                        canvas.drawLine(l + 20, t + 20, r - 20, b - 20, paint);
                        canvas.drawLine(r - 20, t + 20, l + 20, b - 20, paint);
                    }
                }
            }
        }
    }

    private int getCouleur(int v) {
        if (v == 0) return Color.RED;
        if (v == 1) return Color.BLUE;
        if (v == 2) return Color.GREEN;
        if (v == 3) return Color.YELLOW;
        if (v == 4) return Color.MAGENTA;
        if (v == 5) return Color.CYAN;
        if (v == 6) return Color.parseColor("#FFA500");
        return Color.WHITE;
    }

    // Appele apres le mouvement pour verifier
    public void rafraichirScore() {
        while (model.verifierMatch()) {
            if (scoreView != null) {
                scoreView.setText("Score: " + model.getScore());
            }
        }
        invalidate();
    }
}
