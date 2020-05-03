package com.example.karateku;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PesertaAdapter extends RecyclerView.Adapter<PesertaAdapter.MyViewHolder> {

    Context contex;
    ArrayList<MyPeserta> myPesertas;

    public PesertaAdapter(Context c,ArrayList<MyPeserta> p)
    {
        contex = c;
        myPesertas = p;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        return new MyViewHolder(LayoutInflater.from(contex).inflate(R.layout.item_mypeserta,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int position) {

        myViewHolder.xnama_atlet.setText(myPesertas.get(position).getNama_atlet());
        myViewHolder.xttl.setText(myPesertas.get(position).getTtl());

        final String getNamaAtlet = myPesertas.get(position).getNama_atlet();
        myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(contex,PesertaDetails.class);
                intent.putExtra("nama_atlet",getNamaAtlet);
                contex.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return myPesertas.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView xnama_atlet,xttl;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            xnama_atlet = itemView.findViewById(R.id.xnama_atlet);
            xttl = itemView.findViewById(R.id.xttl);
        }
    }
}
