package com.youssefiraoui.ecommercev2.controlers;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.youssefiraoui.ecommercev2.Customizecommande;
import com.youssefiraoui.ecommercev2.R;
import com.youssefiraoui.ecommercev2.entities.Commande;
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

public class Consultercommandes extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    int id;
    Context context;
    ListView listView;
    ArrayList<Commande> commandes=new ArrayList<Commande>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sharedPreferences = getSharedPreferences(MainActivity.Myprefs, MODE_PRIVATE);
        id=sharedPreferences.getInt("idclient",0);
        context=this;



        //make a progress dialog for showing process running
        final ProgressDialog dialog=ProgressDialog.show(this,"","Loading. Please wait...",false);
        dialog.setCancelable(true);
        Runnable progressRunnable = new Runnable() {

            @Override
            public void run() {
                dialog.cancel();
            }
        };

        Handler pdCanceller = new Handler();
        pdCanceller.postDelayed(progressRunnable, 2000);

        setContentView(R.layout.activity_consultercommandes);
        System.out.println(id);



        //show orders by user in a custom list view

        RequestQueue queue= Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, "http://192.168.43.1/Ecommercev1/showcommandes.php?id="+ id, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                System.out.println(response);

                try {


                    JSONArray tableau=response.getJSONArray("commande");

                    for (int i=0;i<tableau.length();i++){

                        Commande commande=new Commande();
                        JSONObject object=tableau.getJSONObject(i);
                        commande.setIdcommande(object.getInt("idcommande"));

                        DateFormat format = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss", Locale.ENGLISH);
                        Date date=format.parse(object.getString("datecommande"));
                        commande.setDatecommande(date);
                        commande.setTellivraison(object.getString("tellivraison"));
                        commande.setAdresselivraison(object.getString("adresselivraison"));
                        commandes.add(commande);

                    }

                    System.out.println(commandes);



                    final Customizecommande customizecommande=new Customizecommande(commandes,context);
                    listView=(ListView) findViewById(R.id.listviewcommandes);
                    listView.setAdapter(customizecommande);

                } catch (JSONException e) {

                    System.out.println(e);

                } catch (ParseException e) {

                    System.out.println(e);

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                System.out.println(error);

            }
        });
        queue.add(jsonObjectRequest);
        System.out.println("+++++++"+commandes);
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
}
