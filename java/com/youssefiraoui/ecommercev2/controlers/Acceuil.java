package com.youssefiraoui.ecommercev2.controlers;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.youssefiraoui.ecommercev2.R;
import com.youssefiraoui.ecommercev2.entities.LigneCommande;
import com.youssefiraoui.ecommercev2.entities.Produit;

import java.util.ArrayList;

public class Acceuil extends AppCompatActivity {

    private static ArrayList<LigneCommande> panier; // Contient les produits ajoutés au panier
    private static ArrayList<Produit> products; // Contient les url et les prixs des produits ajoutés au panier

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acceuil);
        init(); // Appel a la fontion init()
        Intent intent=getIntent();
        Toast.makeText(getApplicationContext(), "Bienvenue"+" "+intent.getStringExtra("login"), Toast.LENGTH_SHORT).show();
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

    private void init(){ // initialise les arraylists;
        panier=new ArrayList<LigneCommande>();
        products=new ArrayList<Produit>();
    }

    public void consulter(View view){

        Intent intent=new Intent(getApplicationContext(),Consultercommandes.class);
        startActivity(intent);
    }
    public void consulter2(View view){
        Intent intent=new Intent(getApplicationContext(),Consultercatalogue.class);
        startActivity(intent);
    }

    //return true if ligneCommande added else return false;
    public static boolean addProduct(LigneCommande ligneCommande){ //Ajoute une ligne de commande dans le panier
        for(int i=0;i<panier.size();i++){
            if(ligneCommande.getIdproduit()==panier.get(i).getIdproduit()){
                return false;
            }
        }
        panier.add(ligneCommande);
        return true;
    }
    public static void addProduct(Produit prod){
        products.add(prod);
    }

    //Delete a product from panier, prend en argument le produit,
    public static boolean deleteProduct(Produit produit){
        boolean deleted = false;
        for(int i=0;i<panier.size();i++){
            if(produit.getIdproduit()==panier.get(i).getIdproduit()){
                panier.remove(i);
                products.remove(i);
                deleted=true;
            }
        }
        return deleted;
    }

    public static ArrayList<LigneCommande> getAddedProducts(){
        return panier;
    }
    public static ArrayList<Produit> getProducts(){
        return products;
    }
}
