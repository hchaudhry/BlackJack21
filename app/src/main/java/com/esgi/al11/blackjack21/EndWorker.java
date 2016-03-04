package com.esgi.al11.blackjack21;

import android.os.AsyncTask;

import com.esgi.al11.blackjack21.metier.PlateauDeJeu;

/**
 * Created by macbookpro on 04/03/16.
 */
public class EndWorker extends AsyncTask {
    private final PlateauDeJeu plateauDeJeu ;
    private final IEndWorker activity;

    public EndWorker(PlateauDeJeu plateauDeJeu, IEndWorker activity) {
        this.plateauDeJeu = plateauDeJeu;
        this.activity = activity;
    }

    @Override
    protected Object doInBackground(Object[] params) {
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        activity.reinit();
        return null;
    }
}
