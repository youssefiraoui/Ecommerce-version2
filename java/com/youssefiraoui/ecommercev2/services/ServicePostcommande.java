package com.youssefiraoui.ecommercev2.services;

import android.content.Context;

import com.youssefiraoui.ecommercev2.entities.Commande;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ServicePostcommande {

    Context context;

    public ServicePostcommande(Context context) {
        this.context = context;
    }

    public void insertCommande(final Commande commande){

        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest stringRequest=new StringRequest(Request.Method.POST,"http://192.168.43.1/Ecommercev1/insertcommande.php", new Response.Listener<String>() {

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
                parameters.put("adresse",commande.getAdresselivraison());

                parameters.put("idclient",String.valueOf(commande.getIdclient()));
                parameters.put("date",date);
                parameters.put("tel",commande.getTellivraison());



                return parameters;
            }
        };
        queue.add(stringRequest);



    }

    public void updateCommande(final int idcommande, final String adresse, final String tel){

        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest stringRequest=new StringRequest(Request.Method.POST,"http://192.168.43.1/Projetecommerce/updatecommande.php", new Response.Listener<String>() {

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
                parameters.put("id",String.valueOf(idcommande));
                parameters.put("adresseliv",adresse);
                parameters.put("tel",tel);



                return parameters;
            }
        };
        queue.add(stringRequest);



    }
}