package com.example.search_eat_pis.Vista;
import com.example.search_eat_pis.Model.Local;
import com.example.search_eat_pis.R;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {
    private  List<Local> mData;
    private LayoutInflater mInflater;
    private Context context;

    public ListAdapter(List<Local> itemlist, Context context){
        this.mInflater = LayoutInflater.from(context);
        this.context=context;
        this.mData=itemlist;

    }
    @Override
    public int getItemCount(){ return mData.size();}

    @Override
    public ListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = mInflater.inflate(R.layout.list_element, null);
        return new ListAdapter.ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(final ListAdapter.ViewHolder holder, final int position){
        holder.bindData(mData.get(position));
    }

    public void setItems(List<Local> items){mData=items;}

    public class ViewHolder extends RecyclerView.ViewHolder{
        //ImageView iconImage;
        TextView nombre_rest,direccion,distancia,precio;
        //ImageButton mapa,reserva;

        ViewHolder(View itemView){
            super(itemView);
            nombre_rest = itemView.findViewById(R.id.Nombre_Restaurante);
            direccion = itemView.findViewById(R.id.direccion);
            distancia = itemView.findViewById(R.id.distancia);
            precio = itemView.findViewById(R.id.precio);

        }
        void bindData(final Local item){
            nombre_rest.setText(item.getNombre());
            direccion.setText(item.getDireccion());
            distancia.setText(item.getDistancia());
            precio.setText(item.getPrecio_medio());
        }
    }

}
