package com.example.crud_api;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    EditText edtName;
    Button btnAdd;
    LinkedList<Student> linkedList = new LinkedList<>();
    CustomAdapter adapter;
    RecyclerView recyclerView;
    String url = "https://60c0231db8d36700175544db.mockapi.io/students";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        btnAdd = findViewById(R.id.btnAdd);
        edtName = findViewById(R.id.edtName);
        recyclerView = findViewById(R.id.rcvItem);
        getListStudent();

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewStudent(url);
                recyclerView.removeAllViews();
                //getListStudent();
                adapter.notifyDataSetChanged();
            }
        });
    }

    private void addNewStudent(String url) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(MainActivity.this,"them ok",Toast.LENGTH_SHORT);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this,"them k thanh cong",Toast.LENGTH_SHORT);
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> map = new HashMap<>();
                map.put("name", edtName.getText().toString());
                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void getListStudent() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest =new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for(int i=0; i< response.length();i++){
                    try {
                        JSONObject object = (JSONObject) response.get(i);
                        int id = object.getInt("id");
                        String name = object.getString("name");
                        Student s = new Student(id,name);
                        linkedList.add(s);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                adapter = new CustomAdapter(linkedList,MainActivity.this);
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this,1));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this,"Khong co du lieu",Toast.LENGTH_SHORT);
            }
        });
        requestQueue.add(jsonArrayRequest);
    }
}