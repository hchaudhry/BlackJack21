package com.esgi.al11.blackjack21;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.esgi.al11.blackjack21.metier.Carte;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by macbookpro on 03/03/16.
 */
public class CardsAdapter extends ArrayAdapter<Carte> {
    private final Context context;
    private final List<Carte> carteArrayList;
    public CardsAdapter(Context context, List<Carte> carteArrayList  ) {
        super(context,R.layout.cartes_row, carteArrayList);
        this.context = context;
        this.carteArrayList = carteArrayList;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;


        CardHolder holder;
        if(row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(R.layout.cartes_row, parent, false);

            holder = new CardHolder();
            holder.img = (ImageView) row.findViewById(R.id.img_card);
            row.setTag(holder);
        }
        else
        {
            holder = (CardHolder)row.getTag();
        }

        Carte carte = carteArrayList.get(position);
        String imgName = carte.getNom().toLowerCase() + "_" + carte.getCouleur().toString().toLowerCase();
        holder.img.setImageResource(context.getResources().getIdentifier(imgName, "drawable", context.getPackageName()));


        return row;
    }


}
