package com.example.crud_api;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.LinkedList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.RecycleViewHolder> {

    private LinkedList<Student> linkedList;
    private Context context;
    private LayoutInflater inflater;


    public CustomAdapter(LinkedList<Student> linkedList, Context context) {
        inflater =LayoutInflater.from(context);
        this.linkedList = linkedList;
        this.context = context;
    }

    @NonNull
    @Override
    public CustomAdapter.RecycleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =inflater.inflate(R.layout.listitem,parent,false);

        return new RecycleViewHolder(view,this);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomAdapter.RecycleViewHolder holder, int position) {
        Student student =linkedList.get(position);
        holder.tvName.setText(student.getName());

    }

    @Override
    public int getItemCount() {
        return linkedList.size();
    }


    public class RecycleViewHolder extends RecyclerView.ViewHolder {

        private CustomAdapter adapter;
        TextView tvName;
        Button btnEdit,btnDelete;
        public RecycleViewHolder(@NonNull View itemView, CustomAdapter customAdapter) {
            super(itemView);
            String url = "https://60c0231db8d36700175544db.mockapi.io/students";

            tvName =itemView.findViewById(R.id.tvName);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnDelete = itemView.findViewById(R.id.btnDelete);
            this.adapter =customAdapter;


            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getLayoutPosition();
                    Student student =linkedList.get(position);
                    deleteStudent(url,student.getId());
                }

                private void deleteStudent(String url, int id) {
                    StringRequest stringRequest = new StringRequest(
                            Request.Method.DELETE, url + '/' + id, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
                            linkedList.remove(getAdapterPosition());
                            notifyItemRemoved(getAdapterPosition());
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                        }
                    });

                    RequestQueue requestQueue = Volley.newRequestQueue(context);
                    requestQueue.add(stringRequest);
                }
            });
        }


    }
}
