package com.esgi.al11.blackjack21.metier;

public class Croupier extends Personne {

    @Override
        public String verif() {
        if(getValeurCartes()<17)
            return "PAS ASSEZ DE CARTE";
        if(getValeurCartes()>21)
            return "BUSTED";

        if(getValeurCartes()> PlateauDeJeu.getInstance().getJoueur().getValeurCartes())
            return "WON";
        else if (getValeurCartes()== PlateauDeJeu.getInstance().getJoueur().getValeurCartes())
            return "DRAW";
        else return "LOSE";
    }
}