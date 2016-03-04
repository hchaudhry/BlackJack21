package com.esgi.al11.blackjack21.metier;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lionel on 03/03/2016.
 */
public abstract class Personne {

    private List<Carte> cartes;

    public Personne() {
        cartes = new ArrayList<Carte>();
    }

    public List<Carte> getCartes() {
        return cartes;
    }

    public void ajouteCarte(Carte carte) {
        cartes.add(carte);
    }

    public Integer getValeurCartes() {
        Integer valeur = 0;
        for (Carte carte : cartes) {
            if(carte.getNom() == Valeur.AS.toString()) {
                if(valeur + 11 < 22) {
                    valeur += 11;
                } else {
                    valeur += 1;
                }
            } else {
                valeur += carte.getValeurs().get(0);
            }
        }
        return valeur;
    }

    public void demandeCarte() {
        ajouteCarte(PlateauDeJeu.getInstance().getCartesDuTas().get(0));
        PlateauDeJeu.getInstance().getCartesDuTas().remove(0);
    }

    public abstract boolean verif();
}