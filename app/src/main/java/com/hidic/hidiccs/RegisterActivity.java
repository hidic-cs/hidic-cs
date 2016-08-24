package com.hidic.hidiccs;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import helper.SQLiteHandler;
import helper.SessionManager;
import volley.AppController;
import volley.ConfigUrl;

public class RegisterActivity extends AppCompatActivity {

    // Logcat tag
    private static final String TAG = RegisterActivity.class.getSimpleName();
    private EditText inputFirstName;
    private EditText inputLastName;
    private EditText inputPhone;
    private EditText inputEmail;
    private EditText inputPassword;
    private ProgressDialog progressDialog;
    private SessionManager sessionManager;
    private SQLiteHandler sqLiteHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        inputFirstName = (EditText) findViewById(R.id.first_name);
        inputLastName = (EditText) findViewById(R.id.last_name);
        inputPhone = (EditText) findViewById(R.id.phone);
        inputEmail = (EditText) findViewById(R.id.email);
        inputPassword = (EditText) findViewById(R.id.password);

        // Progress dialog
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);

        // Session manager
        sessionManager = new SessionManager(getApplicationContext());

        // SQLite database handler
        sqLiteHandler = new SQLiteHandler(getApplicationContext());

        // check if patient is already logged in or not
        if (sessionManager.isLoggedIn()) {
            // Patient is already logged in. Take him to main activity
            Intent intent = new Intent(getApplicationContext(),
                    MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    public void linkToLogin(View view) {
        Intent loginIntent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(loginIntent);
    }

    public void register(View view) {
        String firstName = inputFirstName.getText().toString();
        String lastName = inputLastName.getText().toString();
        String phone = inputPhone.getText().toString();
        String email = inputEmail.getText().toString();
        String password = inputPassword.getText().toString();

        if (!firstName.isEmpty() && !lastName.isEmpty() && !phone.isEmpty() && !email.isEmpty() && !password.isEmpty()) {
            registerPatient(firstName, lastName, phone, email, password);
        } else {
            Snackbar.make(view, "Please fill in all the details", Snackbar.LENGTH_LONG).show();
        }
    }

    /**
     * Function to store the patient in MYySQL database.
     * Will post
     *
     * @param firstName of the patient
     * @param lastName  of the patient
     * @param phone     number of the patient
     * @param email     of the patient
     * @param password  for the account
     */
    private void registerPatient(final String firstName, final String lastName, final String phone,
                                 final String email, final String password) {
        // Tag used to cancel the request
        String tag_string_req = "req_register";

        progressDialog.setMessage("Registering...");
        showDialog();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, ConfigUrl.URL_REGISTER, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Register response: " + response);
                hideDialog();

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    boolean error = jsonObject.getBoolean("error");
                    if (!error) {
                        // Patient successfully stored in MySQL
                        // Now store the user in SQLite
                        String uid = jsonObject.getString("uid");

                        JSONObject patient = jsonObject.getJSONObject("patient");
                        String firstName = patient.getString("first_name");
                        String lastName = patient.getString("last_name");
                        String phone = patient.getString("phone");
                        String email = patient.getString("email");
                        String created_at = patient.getString("created_at");

                        // Inserting row into patient's table
                        sqLiteHandler.addPatient(firstName, lastName, phone, email, uid, created_at);

                        // Launch login activity
                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        // Error occurred in registration. Get the error
                        // message
                        String errorMsg = jsonObject.getString("error_msg");
                        Toast.makeText(getApplicationContext(),
                                errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.e(TAG, "Registration Error: " + volleyError.getMessage());
                Toast.makeText(getApplicationContext(),
                        volleyError.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                Map<String, String> params = new HashMap<>();
                params.put("tag", "register");
                params.put("first_name", firstName);
                params.put("last_name", lastName);
                params.put("phone", phone);
                params.put("email", email);
                params.put("password", password);

                return params;
            }
        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(stringRequest, tag_string_req);
    }

    private void showDialog() {
        if (!progressDialog.isShowing())
            progressDialog.show();
    }

    private void hideDialog() {
        if (progressDialog.isShowing())
            progressDialog.dismiss();
    }

}
