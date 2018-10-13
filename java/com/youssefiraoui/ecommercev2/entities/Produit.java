package com.youssefiraoui.ecommercev2.entities;

public class Produit {
    private int idproduit;
    private String nomproduit;
    private int prix;
    private String url;
    private String nomcatalogue;

    public int getIdproduit() {
        return idproduit;
    }

    public void setIdproduit(int idproduit) {
        this.idproduit = idproduit;
    }

    public String getNomproduit() {
        return nomproduit;
    }

    public void setNomproduit(String nomproduit) {
        this.nomproduit = nomproduit;
    }

    public int getPrix() {
        return prix;
    }

    public void setPrix(int prix) {
        this.prix = prix;
    }

    public String getNomcatalogue() {
        return nomcatalogue;
    }

    public void setNomcatalogue(String nomcatalogue) {
        this.nomcatalogue = nomcatalogue;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}