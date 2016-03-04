package com.esgi.al11.blackjack21;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.esgi.al11.blackjack21.metier.PlateauDeJeu;
import  com.esgi.al11.blackjack21.metier.Carte;
import com.esgi.al11.blackjack21.persistence.MySharedPreference;

import java.util.List;

public class MainActivity extends AppCompatActivity implements IEndWorker {
 private PlateauDeJeu plateauDeJeu = PlateauDeJeu.getInstance();
 private GridView listMains;
 private ListView listMainsCroupier;
 private ArrayAdapter<Carte> arrayAdapterListMain;
 private ArrayAdapter<Carte> arrayAdapterListCroupier;
 private LinearLayout linearLayout;
 private LinearLayout linearLayoutHome;
 private TextView pointsJouer;
 private TextView pointsCroupier;


    private MySharedPreference preference;
    private List<Carte> cartesCroupier = null;
    private List<Carte> cartesJoueur = null;
    private boolean isFinish = false;
    private String pJoueurs;
    private String pCroupier;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        plateauDeJeu.distribution();
        linearLayout =(LinearLayout) findViewById(R.id.linearLayoutCards);
        linearLayoutHome =(LinearLayout) findViewById(R.id.linearLayoutCardsHome);
        pointsJouer =(TextView) findViewById(R.id.points);
        pointsCroupier = (TextView) findViewById(R.id.pointsCroupier);

        preference = new MySharedPreference(this);

        cartesCroupier = preference.getCards("cartesCroupier");
        if (cartesCroupier != null && !cartesCroupier.isEmpty()) {
            Log.i(MainActivity.class.getName(), "FROM SAVE CROUPIER");
            arrayAdapterListCroupier = new CardsAdapter(this, cartesCroupier);
            preference.removeValue("cartesCroupier");
        } else {
            Log.i(MainActivity.class.getName(), "NEW FOR CROUPIER");
            cartesCroupier = plateauDeJeu.getCroupier().getCartes();
            arrayAdapterListCroupier = new CardsAdapter(this, cartesCroupier);
        }

        cartesJoueur = preference.getCards("cartesJoueur");
        if (cartesJoueur != null && !cartesJoueur.isEmpty()) {
            Log.i(MainActivity.class.getName(), "FROM SAVE JOUEUR");
            arrayAdapterListMain = new CardsAdapter(this, cartesJoueur);
            preference.removeValue("cartesJoueur");
        } else {
            Log.i(MainActivity.class.getName(), "NEW FOR JOUEUR");
            cartesJoueur = plateauDeJeu.getJoueur().getCartes();
            arrayAdapterListMain =  new CardsAdapter( this, cartesJoueur);
        }

        drawList(linearLayout,arrayAdapterListMain);
        drawList(linearLayoutHome, arrayAdapterListCroupier , true);
        pointsJouer.setText((plateauDeJeu.getJoueur().getValeurCartes()).toString());


        pJoueurs = preference.getStringValue("pointsJoueur");
        if (pJoueurs != null) {
            pointsJouer.setText(pJoueurs);
        } else {
            pJoueurs = (plateauDeJeu.getJoueur().getValeurCartes()).toString();
            pointsJouer.setText(pJoueurs);
        }
        //pointsJouer.setText((plateauDeJeu.getJoueur().getValeurCartes()).toString());


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
                    Toast.LENGTH_LONG).show();
            Log.d("MBJ", "perdu");
            new EndWorker(plateauDeJeu,this).execute();
            drawList(linearLayoutHome,arrayAdapterListCroupier);
            pointsCroupier.setText((plateauDeJeu.getCroupier().getValeurCartes()).toString());



            isFinish = true;
            preference.removeValue("cartesCroupier");
            preference.removeValue("cartesJoueur");
        }

        drawList(linearLayout, arrayAdapterListMain);
        pointsJouer.setText((plateauDeJeu.getJoueur().getValeurCartes()).toString());

    }

    public void arreter(View v) throws InterruptedException {
        plateauDeJeu.getCroupier().jouer();
        arrayAdapterListCroupier.notifyDataSetChanged();
        plateauDeJeu.calculSoldeModeSimple(this);
        isFinish = true;
        preference.removeValue("cartesCroupier");
        preference.removeValue("cartesJoueur");
       drawList(linearLayout,arrayAdapterListMain);
       drawList(linearLayoutHome, arrayAdapterListCroupier);

       new EndWorker(plateauDeJeu,this).execute();
       pointsJouer.setText((plateauDeJeu.getJoueur().getValeurCartes()).toString());
       pointsCroupier.setText((plateauDeJeu.getCroupier().getValeurCartes()).toString());
    }
    public void reinit() {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                plateauDeJeu.initInstance();
                plateauDeJeu = plateauDeJeu.getInstance();
                arrayAdapterListCroupier = new CardsAdapter(
                        MainActivity.this,
                        plateauDeJeu.getCroupier().getCartes());
                arrayAdapterListMain =  new CardsAdapter(
                        MainActivity.this,
                        plateauDeJeu.getJoueur().getCartes());
                plateauDeJeu.distribution();
                drawList(linearLayout,arrayAdapterListMain);
                drawList(linearLayoutHome,arrayAdapterListCroupier, true);
                pointsJouer.setText((plateauDeJeu.getJoueur().getValeurCartes()).toString());
                pointsCroupier.setText("");
            }
        });

    }
}
