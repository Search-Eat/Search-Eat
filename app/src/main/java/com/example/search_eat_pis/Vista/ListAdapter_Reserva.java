package com.example.search_eat_pis.Vista;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.search_eat_pis.Model.Reserva;
import com.example.search_eat_pis.R;

import java.util.List;

public class ListAdapter_Reserva extends RecyclerView.Adapter<ListAdapter_Reserva.ViewHolder_reseva> {

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
    public ListAdapter_Reserva.ViewHolder_reseva onCreateViewHolder(ViewGroup parent, int viewType){
        View view = mInflater.inflate(R.layout.list_element_reserva, null);
        return new ListAdapter_Reserva.ViewHolder_reseva(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListAdapter_Reserva.ViewHolder_reseva holder, int position) {
        holder.binData(mData.get(position));
    }

    public void setItems(List<Reserva> element){this.mData = element;}

    public class ViewHolder_reseva extends RecyclerView.ViewHolder{
        TextView nombre, fecha,num_pers;
        String aux;

        public ViewHolder_reseva(View itemView) {
            super(itemView);
            nombre = itemView.findViewById(R.id.nom_rest);
            fecha = itemView.findViewById(R.id.fecha_reserva);
            num_pers = itemView.findViewById(R.id.num_pers);
        }

        void binData(final Reserva item){
            nombre.setText(item.getLocal());
            fecha.setText(item.getFecha().getTime().toString().substring(0,item.getFecha().getTime().toString().length()-9));
            num_pers.setText(String.valueOf(item.getPersonas()));
        }
    }

}
