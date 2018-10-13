package com.youssefiraoui.ecommercev2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.youssefiraoui.ecommercev2.R;
import com.youssefiraoui.ecommercev2.entities.LigneCommande;
import com.youssefiraoui.ecommercev2.entities.Produit;

import java.util.ArrayList;

public class Custompannel extends BaseAdapter {
    ArrayList<LigneCommande> ligneCommandes;
    ArrayList<Produit> produits;
    Context context;
    LayoutInflater layoutInflater;

    public Custompannel(ArrayList<LigneCommande> ligneCommandes, ArrayList<Produit> produits, Context context) {
        this.ligneCommandes = ligneCommandes;
        this.produits=produits;
        this.context=context;
        layoutInflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return this.ligneCommandes.size();
    }

    @Override
    public Object getItem(int position) {
        return this.ligneCommandes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row;
        row=layoutInflater.inflate(R.layout.itempannel,null);
        TextView nom=(TextView) row.findViewById(R.id.name);
        TextView prix=(TextView) row.findViewById(R.id.price);
        TextView quantite=(TextView) row.findViewById(R.id.q);
        ImageView image=(ImageView) row.findViewById(R.id.produitpanier);
        nom.setText((CharSequence) this.ligneCommandes.get(position).getNom());
        prix.setText((CharSequence) String.valueOf(this.produits.get(position).getPrix())+" "+"DHS");
        quantite.setText((CharSequence) String.valueOf(this.ligneCommandes.get(position).getQtecomm())+" "+"pieces");
        int id=this.context.getResources().getIdentifier(this.produits.get(position).getUrl(),"drawable",this.context.getPackageName());
        image.setImageResource(id);
        return row;
    }
}