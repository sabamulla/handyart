package com.example.registration;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class SignUp extends AppCompatActivity  implements View.OnClickListener {

    private EditText editTextusername, editTextemail, editTextpassword;
    private Button button2;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        editTextusername = (EditText) findViewById(R.id.editTextusername);
        editTextemail = (EditText) findViewById(R.id.editTextemail);
        editTextpassword = (EditText) findViewById(R.id.editTextpassword);

        button2 = (Button) findViewById(R.id.button2);

        progressDialog = new ProgressDialog(this);

        button2.setOnClickListener(this);

    }
     private void registerUser(){
         final String username= editTextusername.getText().toString().trim();
         final String  email= editTextemail.getText().toString().trim();
         final String password= editTextpassword.getText().toString().trim();


         progressDialog.setMessage("Registering user...");
         progressDialog.show();
         StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_REGISTER,
                 new Response.Listener<String>() {
                     @Override
                     public void onResponse(String response) {

                         progressDialog.dismiss();

                         try {
                             JSONObject jsonObject =new JSONObject(response);
                             Toast.makeText(getApplicationContext(),jsonObject.getString("message"),Toast.LENGTH_LONG).show();

                         } catch (JSONException e) {
                             e.printStackTrace();
                         }

                     }
                 },
                 new Response.ErrorListener() {
                     @Override
                     public void onErrorResponse(VolleyError error) {

                         progressDialog.hide();
                         Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_LONG).show();
                     }
                 }){
             @Override
             protected Map<String, String> getParams() throws AuthFailureError {
                 Map<String,String> param = new HashMap<>();
                 param.put("username",username);
                 param.put("email",email);
                 param.put("password",password);

                 return param;
             }
         };

         RequestQueue requestQueue = Volley.newRequestQueue(this);
         requestQueue.add(stringRequest);
     }


    @Override
    public void onClick(View v) {
         registerUser();

    }
}
