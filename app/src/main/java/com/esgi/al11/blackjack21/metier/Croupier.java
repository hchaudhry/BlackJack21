package com.esgi.al11.blackjack21.metier;

public class Croupier extends Personne {

    @Override
    public boolean verif() {
        if(getValeurCartes()<17)
            return true;
        return false;
    }

    public void jouer()
    {
        boolean result = verif();

        while(result) {
            demandeCarte();
            result = verif();
        }
    }
}