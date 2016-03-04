package com.esgi.al11.blackjack21;

import android.os.AsyncTask;

import com.esgi.al11.blackjack21.metier.PlateauDeJeu;

/**
 * Created by macbookpro on 04/03/16.
 */
public class EndWorker extends AsyncTask {
    private final PlateauDeJeu plateauDeJeu ;
    private final MainActivity mainActivity;


    public EndWorker(PlateauDeJeu plateauDeJeu, MainActivity mainActivity) {
        this.plateauDeJeu = plateauDeJeu;
        this.mainActivity = mainActivity;
    }

    @Override
    protected Object doInBackground(Object[] params) {
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        mainActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mainActivity.reinit();
            }
        });
        return null;
    }
}
