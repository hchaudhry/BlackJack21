package com.esgi.al11.blackjack21;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.esgi.al11.blackjack21.metier.Carte;
import com.esgi.al11.blackjack21.metier.PlateauDeJeu;


public class Mode_normal extends AppCompatActivity implements IEndWorker{

    private PlateauDeJeu plateauDeJeu = PlateauDeJeu.getInstance();
    private GridView listMains;
    private ListView listMainsCroupier;
    private ArrayAdapter<Carte> arrayAdapterListMain;
    private ArrayAdapter<Carte> arrayAdapterListCroupier;
    private LinearLayout linearLayout;
    private LinearLayout linearLayoutHome;
    private TextView pointsJouer;
    private TextView pointsCroupier;
    private TextView valeurMise;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mode_normal);
        plateauDeJeu.distribution();
        linearLayout =(LinearLayout) findViewById(R.id.linearLayoutCards);
        linearLayoutHome =(LinearLayout) findViewById(R.id.linearLayoutCardsHome);
        pointsJouer =(TextView) findViewById(R.id.points);
        pointsCroupier = (TextView) findViewById(R.id.pointsCroupier);
        valeurMise =(TextView) findViewById(R.id.mise);
        arrayAdapterListCroupier = new CardsAdapter(
                this,
                plateauDeJeu.getCroupier().getCartes());

        arrayAdapterListMain =  new CardsAdapter(
                this,
                plateauDeJeu.getJoueur().getCartes());
        drawList(linearLayout,arrayAdapterListMain);
        drawList(linearLayoutHome, arrayAdapterListCroupier , true);
        pointsJouer.setText((plateauDeJeu.getJoueur().getValeurCartes()).toString());


    }

    private void drawList(LinearLayout linearLayout, ArrayAdapter<Carte> arrayAdapterListMain) {
        drawList(linearLayout,arrayAdapterListMain, false);
    }

    private void drawList(LinearLayout v , ArrayAdapter<Carte> listCart, boolean isReturned){
        v.removeAllViews();
        LayoutInflater inflater = ((Activity)this).getLayoutInflater();
        for(int i=0 ; i<listCart.getCount() ; i++){

            Carte carte = listCart.getItem(i);
            LinearLayout row = (LinearLayout) inflater.inflate(R.layout.cartes_row, v, false);
            if(v.getChildCount()==0){
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) row.getLayoutParams();
                layoutParams.setMargins(0, 0, 0, 0);
            }

            CardHolder holder = new CardHolder();
            holder.img = (ImageView) row.findViewById(R.id.img_card);
            if(v.getChildCount()==1 && isReturned){
                holder.img.setImageResource(R.drawable.back);

            }
            else {
                String imgName = carte.getNom().toLowerCase() + "_" + carte.getCouleur().toString().toLowerCase();
                holder.img.setImageResource(this.getResources().getIdentifier(imgName, "drawable", this.getPackageName()));
            }
            v.addView(row);

        }
    }


    public void continuer(View v) {
        plateauDeJeu.getJoueur().demandeCarte();
        boolean isNotLost = plateauDeJeu.getJoueur().verif();

        if(!isNotLost)
        {
            Toast.makeText(this, "Perdu !!!",
                    Toast.LENGTH_SHORT).show();
            Log.d("MBJ", "perdu");
            Toast.makeText(this, "votre solde est de "+plateauDeJeu.getJoueur().getSolde(),
                    Toast.LENGTH_LONG).show();
            new EndWorker(plateauDeJeu,this).execute();
            drawList(linearLayoutHome,arrayAdapterListCroupier);
            pointsCroupier.setText((plateauDeJeu.getCroupier().getValeurCartes()).toString());
        }

        drawList(linearLayout, arrayAdapterListMain);
        pointsJouer.setText((plateauDeJeu.getJoueur().getValeurCartes()).toString());

    }

    public void arreter(View v) throws InterruptedException {
        plateauDeJeu.getCroupier().jouer();
        arrayAdapterListCroupier.notifyDataSetChanged();
        plateauDeJeu.calculSolde(this);
        drawList(linearLayout,arrayAdapterListMain);
        drawList(linearLayoutHome, arrayAdapterListCroupier);
        new EndWorker(plateauDeJeu,this).execute();
        pointsJouer.setText((plateauDeJeu.getJoueur().getValeurCartes()).toString());
        pointsCroupier.setText((plateauDeJeu.getCroupier().getValeurCartes()).toString());

    }
    public void reveleCarte(){

    }
    public void reinit() {

        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                plateauDeJeu.initInstance();
                plateauDeJeu = plateauDeJeu.getInstance();
                arrayAdapterListCroupier = new CardsAdapter(
                        Mode_normal.this,
                        plateauDeJeu.getCroupier().getCartes());
                arrayAdapterListMain =  new CardsAdapter(
                        Mode_normal.this,
                        plateauDeJeu.getJoueur().getCartes());
                plateauDeJeu.distribution();
                drawList(linearLayout,arrayAdapterListMain);
                drawList(linearLayoutHome,arrayAdapterListCroupier, true);
                pointsJouer.setText((plateauDeJeu.getJoueur().getValeurCartes()).toString());
                pointsCroupier.setText("");
                valeurMise.setText("");
            }
        });

    }
    public void miser (View v){
        plateauDeJeu.setMise(plateauDeJeu.getMise()+10, plateauDeJeu.getJoueur().getSolde(), this);
        plateauDeJeu.getJoueur().setSolde(plateauDeJeu.getJoueur().getSolde()- plateauDeJeu.getMise());
        valeurMise.setText("Mise :"+plateauDeJeu.getMise());
    }
}
