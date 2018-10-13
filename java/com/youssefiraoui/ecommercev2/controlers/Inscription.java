package com.youssefiraoui.ecommercev2.controlers;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.youssefiraoui.ecommercev2.R;
import com.youssefiraoui.ecommercev2.entities.Client;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class Inscription extends AppCompatActivity {
    EditText nom;
    EditText prenom;
    EditText tel;
    EditText adresse;
    EditText login;
    EditText mdp;
    EditText date;
    EditText mail;
    Client c;
    Date date2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscription);
        login=(EditText)findViewById(R.id.logEditText);
        mdp=(EditText)findViewById(R.id.passeditText);
        date=(EditText)findViewById(R.id.dateeditText);
        mail=(EditText) findViewById(R.id.maileditText);
        tel=(EditText) findViewById(R.id.teleditText);

    }


    public  void sinscrire(View view) throws ParseException {

        if(  login.getText().toString().isEmpty() || mdp.getText().toString().isEmpty() || date.getText().toString().isEmpty() || mail.getText().toString().isEmpty() || tel.getText().toString().isEmpty()) {
                Toast.makeText(getApplicationContext(), "Veuillez inscrire tous les champs", Toast.LENGTH_SHORT).show();
        }
        else {
            try {
                c = new Client();
                c.setTelclient(tel.getText().toString());
                c.setLoginclient(login.getText().toString());

                c.setMdpclient(mdp.getText().toString());
                c.setEmail(mail.getText().toString());

                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());


            } catch (Exception e) {
                e.printStackTrace();
            }

    // Formulation de la requette et traitement de la reponse
    RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
    StringRequest stringRequest = new StringRequest(
            Request.Method.POST,
            "http://192.168.43.1/Ecommercev1/insertClient.php",
            new Response.Listener<String>() {

                @Override
                public void onResponse(String response) {
                    System.out.println("**************************Response: " + response);


                }
            },
            new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    System.out.println("**************************Response: " + error);


                }
            }){

        @Override
        protected Map<String, String> getParams() throws AuthFailureError {

            Map<String,String> parameters=new HashMap<String, String>();
            parameters.put("tel",c.getTelclient());
            parameters.put("mail",c.getEmail());
            
            parameters.put("login",c.getLoginclient());
            parameters.put("password",c.getMdpclient());
            parameters.put("date",String.valueOf(c.getDatenaissance()));
                 return parameters;
        }
    };


    queue.add(stringRequest);

    Intent intent=new Intent(getApplicationContext(),MainActivity.class);
    Toast.makeText(getApplicationContext(), "Inscription réussie", Toast.LENGTH_SHORT).show();
    startActivity(intent);
}


    }

}
