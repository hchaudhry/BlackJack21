package com.esgi.al11.blackjack21.metier;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PlateauDeJeu {

    private List<Carte> cartesDuTas;
    private Personne joueur;
    private Croupier croupier;
    private static PlateauDeJeu instance;

    public PlateauDeJeu() {
        cartesDuTas = new ArrayList<Carte>();

        for (Couleur couleur : Couleur.values()) {
            for (Valeur valeur : Valeur.values()) {
                Carte carte;
                switch (valeur) {
                    case AS:{
                        List<Integer> valeurs = new ArrayList<Integer>();
                        valeurs.add(1);
                        valeurs.add(11);
                        carte = new Carte(couleur, valeurs, valeur.toString());
                        break;
                    }
                    case VALET :
                    case DAME :
                    case ROI : {
                        List<Integer> valeurs = new ArrayList<Integer>();
                        valeurs.add(10);
                        carte = new Carte(couleur, valeurs, valeur.toString());
                        break;
                    }
                    default:
                        List<Integer> valeurs = new ArrayList<Integer>();
                        valeurs.add(valeur.getValeur());
                        carte = new Carte(couleur, valeurs, valeur.toString());
                        break;
                }
                cartesDuTas.add(carte);
            }
        }

        joueur = new Joueur();
        croupier = new Croupier();

        instance = this;
    }

    public void distribution() {
        Collections.shuffle(cartesDuTas);
        for (int i = 0; i < 2; i++) {
            croupier.demandeCarte();
        }
        for (int i = 0; i < 2; i++) {
            joueur.demandeCarte();
        }
    }

    public static PlateauDeJeu getInstance() {
        if(instance == null) {
            instance = new PlateauDeJeu();
        }
        return instance;
    }

    public static void initInstance() {
        instance = null;
    }

    public Personne getJoueur() {
        return joueur;
    }

    public List<Carte> getCartesDuTas() {
        return cartesDuTas;
    }

    public Croupier getCroupier() {
        return croupier;
    }
}