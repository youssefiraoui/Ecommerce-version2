package com.youssefiraoui.ecommercev2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.youssefiraoui.ecommercev2.R;
import com.youssefiraoui.ecommercev2.entities.Produit;

import java.util.ArrayList;

public class CustomizedListView extends BaseAdapter  {
     ArrayList<Produit> list;
     Context context;
     LayoutInflater layoutInflater;

    public CustomizedListView(ArrayList<Produit> list,Context context) {
        this.list = list;
        this.context=context;
        layoutInflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return this.list.size();

    }

    @Override
    public Object getItem(int position) {
        return this.list.get(position);

    }

    @Override
    public long getItemId(int position) {
        return position;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

            View row;
            row=layoutInflater.inflate(R.layout.customized_list,null);
            TextView nom=(TextView) row.findViewById(R.id.name);
            TextView prix=(TextView) row.findViewById(R.id.prixprod);
            ImageView imageView=(ImageView) row.findViewById(R.id.produitpanier);
            nom.setText((CharSequence) this.list.get(position).getNomproduit());
            prix.setText((CharSequence) String.valueOf(this.list.get(position).getPrix())+" "+"DHS");
            int id=this.context.getResources().getIdentifier(this.list.get(position).getUrl(),"drawable",this.context.getPackageName());
            imageView.setImageResource(id);

        return row;

    }


}