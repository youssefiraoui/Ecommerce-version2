package com.youssefiraoui.ecommercev2.controlers;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.youssefiraoui.ecommercev2.R;
import com.youssefiraoui.ecommercev2.entities.Client;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;


public class MainActivity extends AppCompatActivity {
    TextView login2;
    TextView mdp2;
    public static final String Myprefs="MYPREFS";
    SharedPreferences sharedPreferences;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        login2=(TextView) findViewById(R.id.loginEditText);
        mdp2=(TextView) findViewById(R.id.passwordEditText);
        sharedPreferences=getSharedPreferences(Myprefs,MODE_PRIVATE);


    }
    public void connect(View view){
        RequestQueue queue = Volley.newRequestQueue(this);
        String mdp1=mdp2.getText().toString();
        String  login1=login2.getText().toString();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                "http://localhost/Ecommercev1/showClient.php?login="+login1+"&password="+mdp1,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse( JSONObject  response) {

                            Client c = new Client();
                            try {

                                JSONArray client = response.getJSONArray("client");
                                if (client.length() == 0) {
                                    System.out.println("Ce client ne correspond pas");
                                    Toast.makeText(getApplicationContext(), "Vérifiez les champs composés", Toast.LENGTH_SHORT).show();

                                } else {
                                        JSONObject object = client.getJSONObject(0);
                                        c.setLoginclient(object.getString("loginclient"));
                                        c.setMdpclient(object.getString("mdpclient"));
                                        c.setIdclient(object.getInt("idclient"));
                                        SharedPreferences.Editor editor=sharedPreferences.edit();
                                        editor.putInt("idclient",c.getIdclient());
                                        editor.putString("nomcomplet",c.getLoginclient());
                                        editor.commit();
                                        System.out.println(response);
                                        Intent intent=new Intent(getApplicationContext(),Acceuil.class);
                                        intent.putExtra("login",c.getLoginclient());
                                        startActivity(intent);

                                }
                            }catch (Exception e){
                                System.out.println(e);
                            }
                            System.out.println(response);



                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println("**************************Ereeur: " + error);

                    }
                });

        queue.add(jsonObjectRequest);
    }

    public void inscription(View view){
        Intent intent=new Intent(getApplicationContext(),Inscription.class);
        startActivity(intent);
    }



    }

