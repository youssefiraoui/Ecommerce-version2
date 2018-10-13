package com.youssefiraoui.ecommercev2.services;

import android.content.Context;
import android.os.AsyncTask;

import com.youssefiraoui.ecommercev2.entities.Commande;
import com.youssefiraoui.ecommercev2.entities.LigneCommande;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ServicePostlignecommande {

    Context context;

    public ServicePostlignecommande(Context context) {
        this.context = context;
    }

    public void ServicegetIdcommande(final Context context, Commande commande, final ArrayList<LigneCommande> list){

        RequestQueue queue = Volley.newRequestQueue(context);
        SimpleDateFormat format=new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        String date=format.format(commande.getDatecommande());
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, "http://192.168.43.1/Ecommercev1/getidcommande.php?idclient="+commande.getIdclient()+"&date="+date, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                System.out.println(response);
                try {


                    JSONArray array=response.getJSONArray("idcommande");
                    JSONObject jsonObject=array.getJSONObject(0);
                    final int id=jsonObject.getInt("idcommande");
                    System.out.println(id);

                } catch (JSONException e) {
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

    }

    public void insertLignescommandes2(final int idcommande, final LigneCommande ligneCommande){

        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest stringRequest=new StringRequest(Request.Method.POST,"http://11f0be47.ngrok.io/Projetecommerce/insertlignecommande2.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                System.out.println("**************************Response: " + response);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                System.out.println("**************************Response: " + error);
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {


                Map<String,String> parameters=new HashMap<String, String>();
                parameters.put("idcommande",String.valueOf(idcommande));
                parameters.put("idproduit",String.valueOf(ligneCommande.getIdproduit()));

                parameters.put("quantite",String.valueOf(ligneCommande.getQtecomm()));

                return parameters;
            }
        };
        queue.add(stringRequest);
    }



    public void insertLignescommandes(final LigneCommande ligneCommande, final Commande commande){


            RequestQueue queue = Volley.newRequestQueue(context);
            StringRequest stringRequest=new StringRequest(Request.Method.POST,"http://192.168.43.1/Ecommercev1/insertlignecommande.php", new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    System.out.println("**************************Response: " + response);

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    System.out.println("**************************Response: " + error);
                }
            }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {

                    SimpleDateFormat format=new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
                    String date=format.format(commande.getDatecommande()).toString();

                    Map<String,String> parameters=new HashMap<String, String>();
                    parameters.put("idclient",String.valueOf(commande.getIdclient()));

                    parameters.put("datecommande",date);

                    parameters.put("idproduit",String.valueOf(ligneCommande.getIdproduit()));

                    parameters.put("quantite",String.valueOf(ligneCommande.getQtecomm()));

                    return parameters;
                }
            };
            queue.add(stringRequest);
        }





}