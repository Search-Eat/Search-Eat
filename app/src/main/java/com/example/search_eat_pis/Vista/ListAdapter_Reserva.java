package com.example.search_eat_pis.Vista;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.search_eat_pis.Controller.DatabaseAdapter;
import com.example.search_eat_pis.Model.Reserva;
import com.example.search_eat_pis.Model.Usuario;
import com.example.search_eat_pis.R;

import java.util.List;

public class ListAdapter_Reserva extends RecyclerView.Adapter<ListAdapter_Reserva.ViewHolder_reserva> {

    private List<Reserva> mData;
    private LayoutInflater mInflater;
    private Context context;
    public ListAdapter_Reserva(List<Reserva> itemList, Context context){
        this.mInflater= LayoutInflater.from(context);
        this.context = context;
        this.mData = itemList;
    }

    @Override
    public int getItemCount(){return mData.size();}

    @Override
    public ListAdapter_Reserva.ViewHolder_reserva onCreateViewHolder(ViewGroup parent, int viewType){
        View view = mInflater.inflate(R.layout.list_element_reserva, null);
        return new ListAdapter_Reserva.ViewHolder_reserva(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListAdapter_Reserva.ViewHolder_reserva holder, @SuppressLint("RecyclerView") final int position) {
        holder.binData(mData.get(position));
        holder.getEliminar().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String reservaID = mData.get(position).getId();
                Usuario.usuario_actual.deleteReserva(reservaID);
                mData.remove(position);
            }
        });
        holder.getValorar().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    public void setItems(List<Reserva> element){this.mData = element;}

    public class ViewHolder_reserva extends RecyclerView.ViewHolder{
        private TextView nombre, fecha,num_pers, local, telefono;
        private ImageButton eliminar, valorar;
        private ImageView imagen;
        private final DatabaseAdapter adapter = DatabaseAdapter.databaseAdapter;
        public ImageButton getValorar(){return valorar;}
        public ImageButton getEliminar() {
            return eliminar;
        }

        public ViewHolder_reserva(View itemView) {
            super(itemView);
            nombre = itemView.findViewById(R.id.nombre_reserva);
            telefono = itemView.findViewById(R.id.telefono_reserva);
            fecha = itemView.findViewById(R.id.fecha_reserva);
            local = itemView.findViewById(R.id.Nombre_Restaurante_Reserva);
            num_pers = itemView.findViewById(R.id.numero_personas);
            eliminar = itemView.findViewById(R.id.boton_eliminar_reserva);
            valorar = itemView.findViewById(R.id.boton_valorar);
            imagen = itemView.findViewById(R.id.imagen_local_reserva);

        }

        void binData(final Reserva item){
            local.setText(item.getLocal());
            nombre.setText(" Nombre: " + item.getNombre());
            telefono.setText(" Teléfono: " + Long.toString(item.getTelefono()));
            fecha.setText(" Fecha de reserva: " + item.getFecha());
            num_pers.setText( " Número de personas: " + Long.toString(item.getPersonas()));
            adapter.downloadPhotoFromStorage("locales/",item.getLocalID(),imagen);
        }
    }

}
