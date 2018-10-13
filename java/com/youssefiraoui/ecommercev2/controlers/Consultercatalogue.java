package com.youssefiraoui.ecommercev2.controlers;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.youssefiraoui.ecommercev2.CustomizedListView;
import com.youssefiraoui.ecommercev2.R;
import com.youssefiraoui.ecommercev2.entities.LigneCommande;
import com.youssefiraoui.ecommercev2.entities.Produit;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.youssefiraoui.ecommercev2.controlers.Acceuil.addProduct;
import static com.youssefiraoui.ecommercev2.controlers.Acceuil.getAddedProducts;
import static com.youssefiraoui.ecommercev2.controlers.Acceuil.getProducts;

public class Consultercatalogue extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Spinner spinner;
    ArrayList<String> list;
    ListView listView;
    Context context=this;
    int length;
    public static final String prefs="prefs";
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultercatalogue);
        sharedPreferences=getSharedPreferences(prefs,MODE_PRIVATE);
        final ImageView menu=(ImageView) findViewById(R.id.menu);
        spinner=(Spinner) findViewById(R.id.spinner);
        list=new ArrayList<String>();
        list.add("lessive");
        list.add("detergent");
        list.add("savon medical");
        list.add("savon liquide");
        list.add("savon noir");
        list.add("savon menage");
        list.add("savon menage");
        list.add("savon toilette");
        list.add("parfum homme");
        list.add("parfum femme");
        list.add("parfum bebe");
        list.add("desodorisant");
        list.add("deodorant");
        list.add("shampoing");
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,list);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
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
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String item=list.get(position);
        System.out.println(list);
        Log.i("Item selectionné",item);
        getProductsCatalogue(item);

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        System.out.println("Nothing");

    }

    public  void getProductsCatalogue(String nom){
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                "http://192.168.43.1/Ecommercev1/getSpecificProducts.php?selectedItem="+nom,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse( JSONObject  response) {
                        final ArrayList<Produit> liste=new ArrayList<Produit>();
                        try {
                            JSONArray produit = response.getJSONArray("specificprod");
                            for (int i=0;i<produit.length();i++){
                                Produit p=new Produit();
                                JSONObject object = produit.getJSONObject(i);
                                p.setIdproduit(object.getInt("idproduit"));
                                p.setNomproduit(object.getString("nomproduit"));
                                p.setPrix(object.getInt("prix"));
                                p.setNomcatalogue(object.getString("nomcatalogue"));
                                p.setUrl(object.getString("url"));
                                liste.add(p);
                            }
                            length=liste.size();
                            CustomizedListView customizedListView=new CustomizedListView(liste,context);
                            listView=findViewById(R.id.produitlistView);
                            listView.setAdapter(customizedListView);
                            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                                //afficher un dialog pour ajouter la quantite commandee d'un produit

                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                                    TextView nom=(TextView) view.findViewById(R.id.name);
                                    TextView prix=(TextView) view.findViewById(R.id.prixprod);
                                    ImageView image=(ImageView) view.findViewById(R.id.produitpanier);
                                    int id2=context.getResources().getIdentifier(liste.get(position).getUrl(),"drawable",context.getPackageName());
                                    final Dialog dialog=new Dialog(context);
                                    dialog.setContentView(R.layout.dialog_product);
                                    dialog.setCanceledOnTouchOutside(true);
                                    final TextView name=(TextView) dialog.findViewById(R.id.nameproduct);
                                    name.setText(nom.getText().toString());
                                    final TextView price=(TextView) dialog.findViewById(R.id.priceproduct);
                                    price.setText(prix.getText().toString());
                                    ImageView productImage=(ImageView) dialog.findViewById(R.id.productImage);
                                    productImage.setImageResource(R.drawable.ariel1);
                                    final TextView quantite=(TextView) dialog.findViewById(R.id.quantity);
                                    quantite.setText(String.valueOf(0));
                                    productImage.setImageResource(id2);
                                    ImageView add2=(ImageView) dialog.findViewById(R.id.add);
                                    add2.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            int i=Integer.parseInt(quantite.getText().toString());
                                            i=i+1;
                                            quantite.setText(String.valueOf(i));
                                        }
                                    });
                                    ImageView del2=(ImageView) dialog.findViewById(R.id.del);
                                    del2.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            int i = Integer.parseInt(quantite.getText().toString());
                                            if (i != 0) {
                                                i = i - 1;
                                                quantite.setText(String.valueOf(i));
                                            }
                                        }});
                                    dialog.show();
                                    Button ajouter=(Button) dialog.findViewById(R.id.addpanel);
                                    ajouter.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            boolean x=false;
                                            if(Integer.parseInt(quantite.getText().toString())==0){
                                                Toast.makeText(dialog.getContext(), "Entrez une quantité", Toast.LENGTH_SHORT).show();
                                              }
                                            else {
                                                LigneCommande ligneCommande=new LigneCommande();
                                                Produit p=new Produit();
                                                ligneCommande.setIdproduit(liste.get(position).getIdproduit());
                                                ligneCommande.setNom(liste.get(position).getNomproduit());
                                                ligneCommande.setQtecomm(Integer.parseInt(quantite.getText().toString()));
                                                p.setUrl(liste.get(position).getUrl());
                                                p.setPrix(liste.get(position).getPrix());
                                                //here i have created : ligneCommange & p
                                                if(addProduct(ligneCommande)){
                                                    addProduct(p);
                                                    Toast.makeText(dialog.getContext(), name.getText().toString()+" "+"ajouté", Toast.LENGTH_SHORT).show();
                                                    dialog.dismiss();
                                                }else{
                                                    dialog.dismiss();
                                                    Toast.makeText(context, "Produit disponible dans le panier", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                    }});
                            }});
                        }catch (Exception e){
                            System.out.println(e);
                        }
                        System.out.println(liste);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println("**************************Erreur: " + error);
                    }
                });
        queue.add(jsonObjectRequest);
    }

    public void addPanel(View view) throws JSONException {
        if(getAddedProducts().isEmpty()){
            Toast.makeText(getApplicationContext(), "Panier vide", Toast.LENGTH_SHORT).show();
        }
        else{
            SharedPreferences.Editor editor=sharedPreferences.edit();
            JSONObject jsonObject=new JSONObject();
            JSONObject jsonObject1=new JSONObject();
            jsonObject=this.getJsonobject(getAddedProducts());
            jsonObject1=this.getJsonobject2(getProducts());

            editor.putString("lignes",jsonObject.toString());
            editor.putString("prods",jsonObject1.toString());
            editor.commit();
            Intent intent=new Intent(context,Panier.class);
            startActivity(intent);
        }
    }

    public JSONObject getJsonobject(ArrayList<LigneCommande> liste){
        JSONObject jObject = new JSONObject();
        try{
            JSONArray jsonArray=new JSONArray();
            for(LigneCommande ligne: liste){
                JSONObject jsonObject=new JSONObject();
                jsonObject.put("idproduit",ligne.getIdproduit());
                jsonObject.put("nom",ligne.getNom());
                jsonObject.put("quantite",ligne.getQtecomm());
                jsonArray.put(jsonObject);
            }
            jObject.put("lignes",jsonArray);

        }catch (JSONException jse) {
            jse.printStackTrace();
        }
        return jObject;
    }
    public JSONObject getJsonobject2(ArrayList<Produit> liste){
        JSONObject jObject = new JSONObject();
        try{
            JSONArray jsonArray=new JSONArray();
            for(Produit p: liste){
                JSONObject jsonObject=new JSONObject();
                jsonObject.put("prix",p.getPrix());
                jsonObject.put("url",p.getUrl());
                jsonArray.put(jsonObject);
            }
            jObject.put("prods",jsonArray);

        }catch (JSONException jse) {
            jse.printStackTrace();
        }
        return jObject;
    }


}
