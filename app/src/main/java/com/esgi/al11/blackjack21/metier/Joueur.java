package com.esgi.al11.blackjack21.metier;

/**
 * Created by Lionel on 03/03/2016.
 */
public class Joueur extends Personne {


    @Override
    public String verif() {
        if (getValeurCartes() > 21) {
            return "BUSTED";
        }
        return "OK";
    }
}
