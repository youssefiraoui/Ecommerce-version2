package com.youssefiraoui.ecommercev2.services;

import android.content.Context;

import com.youssefiraoui.ecommercev2.entities.Livreur;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.ArrayList;

public class ServiceGetLivreurs {

    Context context;
    ArrayList<Livreur> listelivreurs=new ArrayList<>();

    public ServiceGetLivreurs(Context context) {
        this.context = context;
        intlivreurs(context);
    }

    public void intlivreurs(Context context){

        RequestQueue queue= Volley.newRequestQueue(context);
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, "http://192.168.43.1/Ecommercev1/getLivreurs.php", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray livreurs = response.getJSONArray("livreurs");
                    for (int i = 0; i < livreurs.length(); i++) {

                        JSONObject livreur = livreurs.getJSONObject(i);
                        Livreur livreur1 = new Livreur();
                        livreur1.setIdlivreur(livreur.getInt("idlivreur"));
                        livreur1.setNomlivreur(livreur.getString("nomlivreur"));
                        livreur1.setPrenomlivreur(livreur.getString("prenomlivreur"));
                        livreur1.setTellivreur(livreur.getString("tellivreur"));
                        listelivreurs.add(livreur1);
                    }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            }}, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error);
            }
        });
        queue.add(jsonObjectRequest);


    }

    public ArrayList<Livreur> getLivreurs() {
        return listelivreurs;
    }
}