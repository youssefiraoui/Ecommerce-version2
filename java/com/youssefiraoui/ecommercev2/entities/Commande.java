package com.youssefiraoui.ecommercev2.entities;

import java.util.Date;

public class Commande {
    private int idcommande;
    private String adresselivraison;
    private String nomlivraison;
    private String prenomlivraison;
    private String tellivraison;
    private Date datecommande;
    private int idclient;
    private int idlivreur;

    public String getAdresselivraison() {
        return adresselivraison;
    }

    public void setAdresselivraison(String adresselivraison) {
        this.adresselivraison = adresselivraison;
    }

    public String getNomlivraison() {
        return nomlivraison;
    }

    public void setNomlivraison(String nomlivraison) {
        this.nomlivraison = nomlivraison;
    }

    public String getPrenomlivraison() {
        return prenomlivraison;
    }

    public void setPrenomlivraison(String prenomlivraison) {
        this.prenomlivraison = prenomlivraison;
    }

    public String getTellivraison() {
        return tellivraison;
    }

    public void setTellivraison(String tellivraison) {
        this.tellivraison = tellivraison;
    }

    public Date getDatecommande() {
        return datecommande;
    }

    public void setDatecommande(Date datecommande) {
        this.datecommande = datecommande;
    }

    public int getIdclient() {
        return idclient;
    }

    public void setIdclient(int idclient) {
        this.idclient = idclient;
    }

    public void setIdcommande(int idcommande) {
        this.idcommande = idcommande;
    }

    public int getIdcommande() {
        return idcommande;
    }

    public int getIdlivreur() {
        return idlivreur;
    }

    public void setIdlivreur(int idlivreur) {
        this.idlivreur = idlivreur;
    }
}