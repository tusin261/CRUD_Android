package com.example.crud_api;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

import java.util.HashMap;
import java.util.Map;

public class UpdateStudent extends AppCompatActivity {

    EditText edtName2;
    Button btnUpdate,btnBack;
    String url = "https://60c0231db8d36700175544db.mockapi.io/students";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_student);

        edtName2 = findViewById(R.id.edtName2);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnBack = findViewById(R.id.btnBack);
        Intent intent = getIntent();
        int id = intent.getIntExtra("id", 0);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UpdateStudent.this,MainActivity.class));
            }
        });
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateStudent(url,id);
            }

            private void updateStudent(String url, int id) {
                StringRequest stringRequest = new StringRequest(
                        Request.Method.PUT, url + '/' + id, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        startActivity(new Intent(UpdateStudent.this,MainActivity.class));                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(UpdateStudent.this, "Khong the cap nhat", Toast.LENGTH_SHORT).show();
                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        HashMap<String, String> params = new HashMap<>();
                        params.put("name", edtName2.getText().toString());
                        return params;
                    }
                };

                RequestQueue requestQueue = Volley.newRequestQueue(UpdateStudent.this);
                requestQueue.add(stringRequest);
            }
        });
    }
}