package com.youssefiraoui.ecommercev2;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.youssefiraoui.ecommercev2.R;
import com.youssefiraoui.ecommercev2.entities.Commande;
import com.youssefiraoui.ecommercev2.services.ServicePostcommande;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class Customizecommande extends BaseAdapter {

    ArrayList<Commande> commandes;
    Context context;
    LayoutInflater layoutInflater;


    public Customizecommande(ArrayList<Commande> commandes, Context context) {

        this.commandes = commandes;
        this.context = context;
        layoutInflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        return this.commandes.size();
    }

    @Override
    public Object getItem(int position) {

        return this.commandes.get(position);

    }

    @Override
    public long getItemId(int position) {

        return position;

    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        final View row;
        row=layoutInflater.inflate(R.layout.item_commande,null);

        final TextView numcommande=(TextView) row.findViewById(R.id.commandeNum);
        numcommande.setText("Commande:"+" "+this.commandes.get(position).getIdcommande());

        TextView datecommande=(TextView) row.findViewById(R.id.dateitem);
        SimpleDateFormat format=new SimpleDateFormat("yyyy-mm-dd ");
        datecommande.setText(format.format(this.commandes.get(position).getDatecommande()));

        final TextView tel=(TextView) row.findViewById(R.id.telliv);
        tel.setText(this.commandes.get(position).getTellivraison());

        final TextView adresse=(TextView) row.findViewById(R.id.addliv);
        adresse.setText(this.commandes.get(position).getAdresselivraison());

        ImageView update=(ImageView) row.findViewById(R.id.update);


        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Dialog dialog1=new Dialog(context);
                dialog1.setContentView(R.layout.dialog_updatecommande);
                dialog1.show();


                final TextView newadresse=(TextView) dialog1.findViewById(R.id.adresse2);
                final TextView newtel=(TextView) dialog1.findViewById(R.id.tel2);

                Button updatebutton=(Button) dialog1.findViewById(R.id.updatebutton);
                updatebutton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        commandes.get(position).setAdresselivraison(newadresse.getText().toString());
                        commandes.get(position).setTellivraison(newtel.getText().toString());

                        tel.setText(commandes.get(position).getTellivraison());
                        adresse.setText(commandes.get(position).getAdresselivraison());
                        notifyDataSetChanged();
                        dialog1.dismiss();

                        String[] parts=numcommande.getText().toString().split(": ");
                        String mynum=parts[1];
                        System.out.println(mynum);

                        ServicePostcommande servicePostcommande=new ServicePostcommande(context);
                        servicePostcommande.updateCommande(Integer.parseInt(mynum),commandes.get(position).getAdresselivraison(),commandes.get(position).getTellivraison());
                    }

                });





            }});
        ImageView delete=(ImageView)row.findViewById(R.id.delete);

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alertdialogbuilder=new AlertDialog.Builder(context);
                alertdialogbuilder.setTitle("Supprimer une commande :");
                alertdialogbuilder.setIcon(R.drawable.commande);

                alertdialogbuilder.setMessage("Voulez vous supprimer la"+" "+numcommande.getText().toString()+" "+"?");

                alertdialogbuilder.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        commandes.remove(position);
                        notifyDataSetChanged();
                        notifyDataSetInvalidated();

                    }
                });

                alertdialogbuilder.setNegativeButton("Non", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                alertdialogbuilder.show();
            }
        });
















        return row;

    }
}