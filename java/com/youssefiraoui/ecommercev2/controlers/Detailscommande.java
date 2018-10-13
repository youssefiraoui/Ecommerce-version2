package com.youssefiraoui.ecommercev2.controlers;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.youssefiraoui.ecommercev2.R;
import com.youssefiraoui.ecommercev2.entities.Commande;
import com.youssefiraoui.ecommercev2.entities.LigneCommande;
import com.youssefiraoui.ecommercev2.entities.Livreur;
import com.youssefiraoui.ecommercev2.services.ServicePostcommande;
import com.youssefiraoui.ecommercev2.services.ServicePostlignecommande;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class Detailscommande extends AppCompatActivity {

    ArrayList<LigneCommande> list;
    ArrayList<Livreur> livs;

    Spinner spinner;
    EditText log;
    EditText date;
    EditText adresse;
    EditText telephone;
    Button button;
    Context context;
    SharedPreferences sharedPreferences2;
    int idliv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_commande);
/*
        context=this;
        idliv=0;
        sharedPreferences2 = getSharedPreferences(MainActivity.Myprefs, MODE_PRIVATE);
        livs=new ArrayList<Livreur>();

        String json=getIntent().getStringExtra("liste");
        System.out.println(json);

        try {
            JSONObject jsonObject ;
            jsonObject = new JSONObject(json);

            JSONArray jsonArray = jsonObject.getJSONArray("lignes");
            list = new ArrayList<LigneCommande>();

            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject ligne = jsonArray.getJSONObject(i);
                LigneCommande ligneCommande = new LigneCommande();

                ligneCommande.setIdproduit(ligne.getInt("idproduit"));
                ligneCommande.setNom(ligne.getString("nom"));
                ligneCommande.setQtecomm(ligne.getInt("quantite"));

                list.add(ligneCommande);


            }

            System.out.println(list);

            spinner = (Spinner) findViewById(R.id.spinnerlivreur);
            telephone=(EditText) findViewById(R.id.tellivreur);

            RequestQueue queue= Volley.newRequestQueue(context);
            JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, "http://0f982338.ngrok.io/Projetecommerce/getLivreurs.php", null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

                    try{

                    System.out.println(response);
                    JSONArray livreurs = response.getJSONArray("livreurs");

                    for (int i = 0; i < livreurs.length(); i++) {

                        JSONObject livreur = livreurs.getJSONObject(i);
                        Livreur livreur1 = new Livreur();
                        livreur1.setIdlivreur(livreur.getInt("idlivreur"));
                        livreur1.setNomlivreur(livreur.getString("nomlivreur"));
                        livreur1.setPrenomlivreur(livreur.getString("prenomlivreur"));
                        livreur1.setTellivreur(livreur.getString("tellivreur"));
                        livs.add(livreur1);}

                        ArrayList<String> livres = new ArrayList<String>();
                        for (Livreur l : livs) {
                            livres.add(l.getNomlivreur() + " " + l.getPrenomlivreur());
                        }

                        System.out.println(livs);

                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, livres);
                        spinner.setAdapter(adapter);


                        //afficher le tel du livreur quand on clique sur son nom

                        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                telephone.setText(livs.get(position).getTellivreur());
                                idliv = livs.get(position).getIdlivreur();



                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });

                        String nomcomplet=sharedPreferences2.getString("nomcomplet","default");

                        log=(EditText) findViewById(R.id.nameText);
                        log.setText(nomcomplet);
                        adresse=(EditText) findViewById(R.id.adresseEdittext)

                        date=(EditText) findViewById(R.id.datecommandeEdittext);
                        Date date1=new Date();
                        SimpleDateFormat format=new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
                        date.setText(format.format(date1).toString());
                        button = (Button) findViewById(R.id.commandButton);
                        button.setOnClickListener(new View.OnClickListener() {

                            @Override
                            public void onClick(View v) {
                                try {

                                Commande commande = new Commande();

                                commande.setIdclient(sharedPreferences2.getInt("idclient", 0));
                                commande.setIdlivreur(idliv);

                                commande.setAdresselivraison(adresse.getText().toString());
                                DateFormat format = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss", Locale.ENGLISH);
                                commande.setDatecommande(format.parse(date.getText().toString()));

                                    ServicePostcommande servicePostcommande = new ServicePostcommande(context);
                                    servicePostcommande.insertCommande(commande);
                                    ServicePostlignecommande servicePostlignecommande = new ServicePostlignecommande(context);

                                    for (LigneCommande l : list) {
                                        servicePostlignecommande.insertLignescommandes(l, commande);
                                    }




                                } catch (ParseException e) {

                                    System.out.println(e);

                                }


                            }
                        });


                    }catch (JSONException e){
                        System.out.println(e);
                    }
            }}, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    System.out.println(error);

                }

            });

            queue.add(jsonObjectRequest);


        }catch (JSONException e){

               System.out.println(e);
        }




    }*/
    }
}
