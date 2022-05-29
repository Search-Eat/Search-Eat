package com.example.search_eat_pis.Vista;
import com.example.search_eat_pis.Controller.DatabaseAdapter;
import com.example.search_eat_pis.MapsActivity;
import com.example.search_eat_pis.Model.Local;
import com.example.search_eat_pis.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
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
    public void onBindViewHolder(final ListAdapter.ViewHolder holder, @SuppressLint("RecyclerView") final int position){
         holder.bindData(mData.get(position));
         holder.getMapa().setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 Intent register = new Intent(context, MapsActivity.class);
                 register.putExtra("latitud",Double.toString(mData.get(position).getCoordenada().getLatitud()));
                 register.putExtra("longitud",Double.toString(mData.get(position).getCoordenada().getLongitud()));
                 register.putExtra("nombre", mData.get(position).getNombre());
                 context.startActivity(register);
             }
         });

         holder.getReserva().setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 Intent register = new Intent(context, ReservaActivity.class);
                 register.putExtra("boton",holder.getNombre_rest().toString());
                 context.startActivity(register);
             }
         });
    }

    public void setItems(List<Local> items){mData=items;}

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView iconImage;
        TextView nombre_rest,direccion,distancia,precio,puntuacion;
        ImageButton mapa,reserva;
        private final DatabaseAdapter adapter = DatabaseAdapter.databaseAdapter;

        public TextView getNombre_rest() {
            return nombre_rest;
        }

        public TextView getDistancia() {
            return distancia;
        }

        public TextView getPrecio() {
            return precio;
        }

        public TextView getPuntuacion() {
            return puntuacion;
        }

        public ImageButton getMapa() {
            return mapa;
        }

        public ImageButton getReserva() {
            return reserva;
        }

        ViewHolder(View itemView){
            super(itemView);
            iconImage = itemView.findViewById(R.id.imageView3);
            nombre_rest = itemView.findViewById(R.id.Nombre_Restaurante);
            direccion = itemView.findViewById(R.id.direccion);
            distancia = itemView.findViewById(R.id.distancia);
            precio = itemView.findViewById(R.id.precio);
            puntuacion = itemView.findViewById(R.id.puntacion);
            mapa = itemView.findViewById(R.id.maps);
            reserva = itemView.findViewById(R.id.reserva);

        }
        void bindData(final Local item){
            nombre_rest.setText(item.getNombre());
            direccion.setText(" Dirección: "+item.getDireccion());
            distancia.setText(" Distancia: "+item.getDistancia());
            precio.setText(" Precio: "+item.getPrecio_medio());
            precio.setText("Puntuación: "+item.getValoracion());
            if(!item.getFoto().equals("null")) {
                adapter.downloadPhotoFromStorage(item.getiD(),iconImage);
            }

        }

    }

}
