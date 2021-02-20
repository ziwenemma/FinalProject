package com.example.finalproject;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;



public class AdapterParent extends RecyclerView.Adapter<AdapterParent.AdapterViewHolder> {
    public Context c;
    public ArrayList<InformationSitter> arrayList;
    int num = 0;
    StorageReference storageReference;

    public AdapterParent(Context c, ArrayList<InformationSitter> arrayList) {
        this.c = c;
        this.arrayList = arrayList;

    }


    @Override
    public int getItemCount() {
        return arrayList.size();
    }


    @Override
    public long getItemId(int position) {
        return position;
    }


    @NonNull
    @Override
    public AdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cardview_parent, parent, false);
        return new AdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final AdapterViewHolder holder, int position) {
        InformationSitter informationSitter = arrayList.get(position);
        holder.t1.setText(informationSitter.getSitterName());
        holder.t2.setText(informationSitter.getSitterCity());
        holder.t3.setText(informationSitter.getSitterRate());
        holder.t4.setText(informationSitter.getSitterDesc());
        Picasso.get().load(informationSitter.getImage());

    }

    /**
     * text view for the items displayed
     */

    public class AdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView t1;
        public TextView t2;
        public TextView t3;
        public TextView t4;
        public ImageView i1;

        public AdapterViewHolder(@NonNull View itemView) {
            super(itemView);

            t1 = (TextView) itemView.findViewById(R.id.text1);
            t2 = (TextView) itemView.findViewById(R.id.text5);
            t3 = (TextView) itemView.findViewById(R.id.text3);
            t4=(TextView)itemView.findViewById(R.id.text2);
            i1=(ImageView)itemView.findViewById(R.id.productimage);

        }

        @Override
        public void onClick(View v) {

        }
    }

}