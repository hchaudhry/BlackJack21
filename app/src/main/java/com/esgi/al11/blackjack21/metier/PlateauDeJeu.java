package com.esgi.al11.blackjack21.metier;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PlateauDeJeu {

    private List<Carte> cartesDuTas;
    private Personne joueur = new Joueur();
    private Personne croupier = new Croupier();
    private static PlateauDeJeu instance;
    private int mise;

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

        ((Joueur)joueur).setSolde(((Joueur)joueur).getSolde() - mise);
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

    public Personne getCroupier() {
        return croupier;
    }

    public int getMise() {
        return mise;
    }

    public void setMise(int mise) {
        this.mise = mise;
    }

    public void calculSolde() {
        if(croupier.getValeurCartes() < 22 && croupier.getValeurCartes() > joueur.getValeurCartes()) {
            System.out.print("Croupier gagne et joueur perd sa mise");
        }
        if(joueur.getValeurCartes() == 21 && joueur.getCartes().size() == 2) {
            System.out.print("Joueur a blackjack et gagne 1.5 fois sa mise");
            ((Joueur) joueur).setSolde(((Joueur) joueur).getSolde() + mise + (1.5 * mise));
        } else if (joueur.getValeurCartes() > croupier.getValeurCartes()) {
            System.out.print("Joueur a gagné contre le croupier et double sa mise");
            ((Joueur) joueur).setSolde(((Joueur) joueur).getSolde() + (2 * mise));
        }
        if(croupier.getValeurCartes() == joueur.getValeurCartes()) {
            System.out.print("Match nul, le joueur récupère sa mise");
            ((Joueur) joueur).setSolde(((Joueur) joueur).getSolde() + mise);
        }
    }
}