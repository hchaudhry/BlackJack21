package com.esgi.al11.blackjack21.metier;

/**
 * Created by Lionel on 03/03/2016.
 */
public class Joueur extends Personne {

    private double solde;

    public Joueur() {
        solde = 500;
    }

    @Override
    public boolean verif() {
        if (getValeurCartes() > 21) {
            return false;
        }
        return true;
    }

    public double getSolde() {
        return solde;
    }

    public void setSolde(double solde) {
        this.solde = solde;
    }
}
