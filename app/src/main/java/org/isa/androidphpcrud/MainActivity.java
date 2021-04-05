package org.isa.androidphpcrud;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    // les champs
    private EditText editTextUserName,editTextEmail, editTextPassword;
    // le bouton
    private Button buttonRegister;
    // le progressdialog
    private ProgressDialog progressDialog;
    //le textview to access login:
    TextView tvLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editTextUserName = findViewById(R.id.editTextUserName);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        tvLogin = findViewById(R.id.textViewLogin);
        buttonRegister = findViewById(R.id.buttonRegister);
        buttonRegister.setOnClickListener(this);
        tvLogin.setOnClickListener(this);

        //instancier progressdialog :
        progressDialog = new ProgressDialog(this);

    }

    @Override
    public void onClick(View v) {
        // si clic sur buttonRegister :
        if (v == buttonRegister)
            registerUser();
        //si clic sur login, we want to open loginactivity :
        if(v == tvLogin){
            finish();
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
        }

    }

    private void registerUser() {
        final String email = editTextEmail.getText().toString().trim();
        final String username = editTextUserName.getText().toString().trim();
        final String password = editTextPassword.getText().toString().trim();

        // On affiche un popup de progression
        progressDialog.setMessage("Enregistrement utilisateur");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Constants.URL_REGISTER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // arret du popup
                        progressDialog.dismiss();
                        // récupération du Json de réponse
                        try {
                            //converting response to json object
                            JSONObject jsonObject = new JSONObject(response);
                            // affichage de la réponse
                            Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_LONG).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.hide();
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
                ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("username", username);
                params.put("email", email);
                params.put("password", password);
                return params;
            }
        };

        //1e façon simple - sans utiliser de RequestHandler (singleton) :
//        RequestQueue requestQueue = Volley.newRequestQueue(this);
//        requestQueue.add(stringRequest);

        //en utilisant singleton (RequestHandler): on instancie notre Singleton et on y ajoute notre requestqueue:
        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);

        //BILAN 08/01 11H: mainacty ok, on peut inscrire new user in bdd, mais si clic sur login, loginacty ne vient pas, à vérifier
        //pb identifié avec postman dans tutoAPIvolley : fichier userLogin.php utilise method getUserByUsername (DbOperations.php)
        // qui retourne tjs user#1 au lieu du user voulu donc à corriger

    }


}