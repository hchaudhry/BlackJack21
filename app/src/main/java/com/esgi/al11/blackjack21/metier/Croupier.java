package com.esgi.al11.blackjack21.metier;

public class Croupier extends Personne {

    @Override
    public boolean verif() {
        if (getValeurCartes() > 16) {
            return false;
        }
        return true;
    }
}