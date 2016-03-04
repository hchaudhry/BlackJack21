package com.esgi.al11.blackjack21.metier;

import java.util.ArrayList;
import java.util.List;

public class Carte {

    private static int LAST_ID = 1;
    private int id;

    private String nom;

    public List<Integer> getValeurs() {
        return valeurs;
    }

    private List<Integer> valeurs;
    private Couleur couleur;

    public Carte() {

    }

    public Carte(Couleur couleur, List<Integer> valeurs, String nom) {
        id = LAST_ID;
        LAST_ID++;
        this.couleur = couleur;
        this.valeurs = new ArrayList<Integer>();
        this.valeurs.addAll(valeurs);
        this.nom = nom;
    }

    public String getNom() {
        return nom;
    }

    @Override
    public String toString() {
        return nom + " " + getCouleur();
    }

    public Couleur getCouleur() {
        return couleur;
    }
}