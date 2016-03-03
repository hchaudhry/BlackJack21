package com.esgi.al11.blackjack21;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.esgi.al11.blackjack21.metier.PlateauDeJeu;
import  com.esgi.al11.blackjack21.metier.Carte;
import java.util.List;

public class MainActivity extends AppCompatActivity {
 private PlateauDeJeu plateauDeJeu = PlateauDeJeu.getInstance();
 private GridView listMains;
 private ListView listMainsCroupier;
 private ArrayAdapter<Carte> arrayAdapterListMain;
 private ArrayAdapter<Carte> arrayAdapterListCroupier;
 private LinearLayout linearLayout;
 private LinearLayout linearLayoutHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        plateauDeJeu.distribution();
        linearLayout =(LinearLayout) findViewById(R.id.linearLayoutCards);
        linearLayoutHome =(LinearLayout) findViewById(R.id.linearLayoutCardsHome);
        //listMains = (GridView) findViewById(R.id.hand);

        //listMainsCroupier =(ListView) findViewById(R.id.homeHand);

        arrayAdapterListCroupier = new CardsAdapter(
                this,
                plateauDeJeu.getCroupier().getCartes());
        //listMainsCroupier.setAdapter(arrayAdapterListCroupier);

        arrayAdapterListMain =  new CardsAdapter(
                this,
               plateauDeJeu.getJoueur().getCartes());
        //listMains.setAdapter(arrayAdapterListMain);
        drawList(linearLayout,arrayAdapterListMain);
        drawList(linearLayoutHome,arrayAdapterListCroupier);

    }
    private void drawList(LinearLayout v , ArrayAdapter<Carte> listCart){
        v.removeAllViews();
        LayoutInflater inflater = ((Activity)this).getLayoutInflater();
        for(int i=0 ; i<listCart.getCount() ; i++){
            Carte carte = listCart.getItem(i);
            View row = inflater.inflate(R.layout.cartes_row, v, false);
            CardHolder holder = new CardHolder();
            holder.img = (ImageView) row.findViewById(R.id.img_card);
            String imgName = carte.getNom().toLowerCase() + "_" + carte.getCouleur().toString().toLowerCase();
            holder.img.setImageResource(this.getResources().getIdentifier(imgName,"drawable",this.getPackageName()));
            v.addView(row);
        }


    }



    public void continuer(View v) {
        plateauDeJeu.getJoueur().demandeCarte();
        String isNotLost = plateauDeJeu.getJoueur().verif();

        if(isNotLost.equals("BUSTED"))
        {
            Toast.makeText(this, "Perdu !!!",
                    Toast.LENGTH_LONG).show();
            Log.d("MBJ", "perdu");
        }
        drawList(linearLayout,arrayAdapterListMain);
    }

    public void arreter(View v) {
    plateauDeJeu.getCroupier().jouer();
    arrayAdapterListCroupier.notifyDataSetChanged();
    String isWinner = plateauDeJeu.getCroupier().verif();
    if(isWinner.equals("WON")){
        Toast.makeText(this, "Le croupier a gagnié",
                Toast.LENGTH_LONG).show();
    }
    else if(isWinner.equals("LOSE") || isWinner.equals("BUSTED") ){
        Toast.makeText(this, "Vous avez a gagnié",
                Toast.LENGTH_LONG).show();
    }
    else if(isWinner.equals("DRAW")){
        Toast.makeText(this, "Vous égaux",
                Toast.LENGTH_LONG).show();

    }
    }
}
