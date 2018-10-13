package com.youssefiraoui.ecommercev2.controlers;

import android.app.Dialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;

import android.content.Intent;
import android.content.SharedPreferences;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

import android.widget.ArrayAdapter;
import android.widget.Button;

import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.youssefiraoui.ecommercev2.Custompannel;
import com.youssefiraoui.ecommercev2.R;
import com.youssefiraoui.ecommercev2.entities.Commande;
import com.youssefiraoui.ecommercev2.entities.LigneCommande;
import com.youssefiraoui.ecommercev2.entities.Livreur;
import com.youssefiraoui.ecommercev2.entities.Produit;
import com.youssefiraoui.ecommercev2.services.ServicePostcommande;
import com.youssefiraoui.ecommercev2.services.ServicePostlignecommande;
import android.support.v4.app.NotificationCompat;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static com.youssefiraoui.ecommercev2.controlers.Acceuil.deleteProduct;
import static com.youssefiraoui.ecommercev2.controlers.Acceuil.getAddedProducts;
import static com.youssefiraoui.ecommercev2.controlers.Acceuil.getProducts;

public class Panier extends AppCompatActivity implements View.OnClickListener{
    ListView listView;
    SharedPreferences sharedPreferences;
    SharedPreferences sharedPreferences2;
    TextView textView;
    Button commander;
    Context context;
    int total = 0;
    ArrayList<Livreur> livs;
    ArrayList<LigneCommande> list;
    android.support.v4.app.NotificationCompat.Builder notification;
    static final int uniqueId=123456;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.panier);
        int prixuniaire = 0;
        context=this;
        sharedPreferences = getSharedPreferences(Consultercatalogue.prefs, MODE_PRIVATE);
        sharedPreferences2 = getSharedPreferences(MainActivity.Myprefs, MODE_PRIVATE);

        notification=new NotificationCompat.Builder(context);
        notification.setAutoCancel(true);

        livs=new ArrayList<>();
        commander=(Button) findViewById(R.id.commanderBtn);
        String json = sharedPreferences.getString("lignes", "default");
        String json2 = sharedPreferences.getString("prods", "default");
        int id = sharedPreferences2.getInt("idclient", 0);
        System.out.println(json2);
        Log.i("ID", String.valueOf(id));
        listView = (ListView) findViewById(R.id.listviewpannel);
        textView = (TextView) findViewById(R.id.totalTextView);
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(json);
            JSONObject jsonObject2 = new JSONObject(json2);
            JSONArray jsonArray = jsonObject.getJSONArray("lignes");
            JSONArray prods = jsonObject2.getJSONArray("prods");
            list = new ArrayList<LigneCommande>();
            final ArrayList<Produit> list2 = new ArrayList<Produit>();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject ligne = jsonArray.getJSONObject(i);
                JSONObject prod = prods.getJSONObject(i);
                LigneCommande ligneCommande = new LigneCommande();
                Produit p = new Produit();
                ligneCommande.setIdproduit(ligne.getInt("idproduit"));
                ligneCommande.setNom(ligne.getString("nom"));
                ligneCommande.setQtecomm(ligne.getInt("quantite"));
                p.setPrix(prod.getInt("prix"));
                p.setUrl(prod.getString("url"));
                list.add(ligneCommande);
                list2.add(p);
                prixuniaire = (p.getPrix()) * (ligneCommande.getQtecomm());
                total = total + prixuniaire;
            }
            textView.setText(String.valueOf(total) + " " + "DHS");
            Log.d("Prods", getProducts()+".");
            final Custompannel panier = new Custompannel(getAddedProducts(), getProducts(), this);
            listView.setAdapter(panier);
            commander.setOnClickListener(this);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent,  View view, final int position, long id) {
                    final TextView textView2=(TextView) view.findViewById(R.id.name);
                    AlertDialog.Builder alertdialogbuilder=new AlertDialog.Builder(context);
                    alertdialogbuilder.setTitle("Supprimer du panier :");
                    alertdialogbuilder.setIcon(R.drawable.danger);
                    alertdialogbuilder.setMessage("Voulez vous supprimer le produit"+" "+textView2.getText().toString()+" "+"du panier ?");
                    alertdialogbuilder.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //supprimer le produit du panier
                            Produit prod = new Produit();
                            prod.setIdproduit(list.get(position).getIdproduit());
                            total=total-(list.get(position).getQtecomm())*(list2.get(position).getPrix());
                            System.out.println(total);
                            if(deleteProduct(prod)){
                                textView.setText(String.valueOf(total)+ " " + "DHS");
                                list.remove(position);
                                list2.remove(position);
                                panier.notifyDataSetChanged();
                                panier.notifyDataSetInvalidated();
                                Toast.makeText(context, "Produit supprimé", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                Toast.makeText(context, "Erreur: Product not deleted !", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    alertdialogbuilder.setNegativeButton("Non", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });

                    AlertDialog alertDialog=alertdialogbuilder.create();
                    alertDialog.show();
                }
            });

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);//Menu Resource, Menu
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item1:
                Intent intent=new Intent(getApplicationContext(),Acceuil.class);
                startActivity(intent);
                return true;
            case R.id.item2:
                Intent intent2=new Intent(getApplicationContext(),Panier.class);
                startActivity(intent2);
                return true;
            case R.id.item3:
                Intent intent3=new Intent(getApplicationContext(),Consultercatalogue.class);
                startActivity(intent3);
                return true;

            case R.id.item4:
                Intent intent4=new Intent(getApplicationContext(),Consultercommandes.class);
                startActivity(intent4);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }







    @Override
    public void onClick(View v) {

        final Dialog dialog=new Dialog(context);
        dialog.setContentView(R.layout.dialog_commande);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setCancelable(true);
        final String nomcomplet=sharedPreferences2.getString("nomcomplet","default");
        EditText log=(EditText) dialog.findViewById(R.id.nameText);
        final EditText tel=(EditText) dialog.findViewById(R.id.tellivraison);
        log.setText(nomcomplet);
        final EditText date=(EditText) dialog.findViewById(R.id.datecommandeEdittext);
        final Date date1=new Date();
        SimpleDateFormat format=new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        date.setText(format.format(date1).toString());

        dialog.show();


        Button button = (Button) dialog.findViewById(R.id.commandButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    EditText adresse = (EditText) dialog.findViewById(R.id.adresseEdittext);
                    final Commande commande = new Commande();
                    commande.setIdclient(sharedPreferences2.getInt("idclient", 0));
                    commande.setAdresselivraison(adresse.getText().toString());
                    commande.setTellivraison(tel.getText().toString());
                    DateFormat format = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss", Locale.ENGLISH);
                    commande.setDatecommande(format.parse(date.getText().toString()));
                    System.out.println(commande.getIdclient());
                    System.out.println(commande.getAdresselivraison());

                    ServicePostcommande servicePostcommande = new ServicePostcommande(context);
                    servicePostcommande.insertCommande(commande);
                    System.out.println(commande.getDatecommande());

                    Runnable progressRunnable = new Runnable() {

                        @Override
                        public void run() {

                            ServicePostlignecommande servicePostlignecommande = new ServicePostlignecommande(context);

                            for (int i=0;i<list.size();i++){

                                servicePostlignecommande.insertLignescommandes(list.get(i),commande);

                            }
                        }
                    };

                    Handler pdCanceller = new Handler();
                    pdCanceller.postDelayed(progressRunnable, 1000);






                    Intent intent1=new Intent(context,Consultercommandes.class);

                   //make a notification to say that the order has been created
                    notification.setSmallIcon(R.mipmap.ic_launcher_round);
                    notification.setLargeIcon(BitmapFactory.decodeResource(context.getResources(),R.drawable.commande));
                    notification.setTicker("Commande ajoutée");
                    notification.setWhen(System.currentTimeMillis());
                    notification.setContentTitle("Commande du client"+" "+sharedPreferences2.getString("nomcomplet","default"));
                    notification.setContentText("Votre commande a été ajoutée avec succes");

                    Uri alarmSound= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                    notification.setSound(alarmSound);

                    //give device the ability to access our app

                    PendingIntent pendingIntent=PendingIntent.getActivity(context,0,intent1,PendingIntent.FLAG_UPDATE_CURRENT); //give the access for an intent in our app
                    notification.setContentIntent(pendingIntent);

                    //build notification and issues it
                    NotificationManager notificationManager=(NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                    notificationManager.notify(uniqueId,notification.build());
                    startActivity(intent1);





                } catch (ParseException e) {
                    System.out.println(e);
                }


            }
        });





    }




}

