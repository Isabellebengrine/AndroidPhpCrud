package org.isa.androidphpcrud;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText eTUsername, eTPassword;
    private Button btnLogin;
    private TextView tVRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if (SharedPrefManager.getInstance(this).isLoggedIn()) {
            finish();
            startActivity(new Intent(this, ProfileActivity.class));
        }

        eTUsername = findViewById(R.id.eTUserName);
        eTPassword = findViewById(R.id.eTPassword);
        btnLogin = findViewById(R.id.buttonLogin);
        btnLogin.setOnClickListener(this);
        tVRegister.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        // si clic sur button :
        if (v == btnLogin)
            userLogin();
        //si clic sur register, we want to open mainactivity :
        if(v == tVRegister){
            finish();
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        }
    }

    private void userLogin() {

        //first getting the values
        final String username = eTUsername.getText().toString();
        final String password = eTPassword.getText().toString();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_LOGIN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            //converting response to json object
                            JSONObject obj = new JSONObject(response);

                            Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();

//                                //getting the user from the response
//                                JSONObject userJson = obj.getJSONObject("user");
//
//                                //creating a new user object
//                                User user = new User(
//                                        userJson.getInt("id"),
//                                        userJson.getString("username"),
//                                        userJson.getString("email")
//                                );

                                //storing the user in shared preferences
//                                SharedPrefManager.getInstance(getApplicationContext()).userLogin(user);

                                //starting the profile activity
//                                finish();
//                                startActivity(new Intent(getApplicationContext(), ProfileActivity.class));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("username", username);
                params.put("password", password);
                return params;
            }
        };

        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }
}