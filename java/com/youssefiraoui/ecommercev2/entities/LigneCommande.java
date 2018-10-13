package com.youssefiraoui.ecommercev2.entities;

public class LigneCommande {
    private int idproduit;
    private String nom;
    private int idcommande;
    private int qtecomm;
    private int idligne;

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getIdligne() {
        return idligne;
    }

    public void setIdligne(int idligne) {
        this.idligne = idligne;
    }

    public int getIdproduit() {
        return idproduit;
    }

    public void setIdproduit(int idproduit) {
        this.idproduit = idproduit;
    }

    public int getIdcommande() {
        return idcommande;
    }

    public void setIdcommande(int idcommande) {
        this.idcommande = idcommande;
    }

    public int getQtecomm() {
        return qtecomm;
    }

    public void setQtecomm(int qtecomm) {
        this.qtecomm = qtecomm;
    }
}