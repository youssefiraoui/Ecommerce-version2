package com.youssefiraoui.ecommercev2.entities;

import java.util.Date;

public class Client {

    private int idclient;
    private String nomclient;
    private String prenomclient;
    private String telclient;
    private String adresseclient;
    private String loginclient;
    private String mdpclient;
    private Date datenaissance;
    private String email;

    public Client() {

    }

    public int getIdclient() {
        return idclient;
    }

    public void setIdclient(int idclient) {
        this.idclient = idclient;
    }

    public String getNomclient() {
        return nomclient;
    }

    public void setNomclient(String nomclient) {
        this.nomclient = nomclient;
    }

    public String getPrenomclient() {
        return prenomclient;
    }

    public void setPrenomclient(String prenomclient) {
        this.prenomclient = prenomclient;
    }

    public String getTelclient() {
        return telclient;
    }

    public void setTelclient(String telclient) {
        this.telclient = telclient;
    }

    public String getAdresseclient() {
        return adresseclient;
    }

    public void setAdresseclient(String adresseclient) {
        this.adresseclient = adresseclient;
    }

    public String getLoginclient() {
        return loginclient;
    }

    public void setLoginclient(String loginclient) {
        this.loginclient = loginclient;
    }

    public String getMdpclient() {
        return mdpclient;
    }

    public void setMdpclient(String mdpclient) {
        this.mdpclient = mdpclient;
    }

    public Date getDatenaissance() {
        return datenaissance;
    }

    public void setDatenaissance(Date datenaissance) {
        this.datenaissance = datenaissance;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}