package com.exportacion.alimentos.applogisticsmanagement;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ListView;

import com.exportacion.alimentos.applogisticsmanagement.model.Productos;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lrojaspu on 15/10/2017.
 */

public class Adapter extends RecyclerView.Adapter<viewHolder> implements Filterable {

    List<Productos> listaProducto;
    List<Productos> listaProductoFiltrada;

    public Adapter(List<Productos> listaProducto) {
        this.listaProducto = listaProducto;
        this.listaProductoFiltrada = listaProducto;
    }

    @Override
    public viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item,parent,false);
        return new viewHolder(view, listaProductoFiltrada);
    }

    @Override
    public void onBindViewHolder(viewHolder holder, int position) {
        holder.descipcionProducto.setText(listaProductoFiltrada.get(position).getDescripcionProducto());
        holder.presentacionProducto.setText(listaProductoFiltrada.get(position).getPresentacionProducto());
        encontrarImagenProducto(holder,listaProductoFiltrada.get(position).getImagenProducto());
    }

    @Override
    public int getItemCount() {
        return listaProductoFiltrada.size();
    }

    public void encontrarImagenProducto(viewHolder holder, String nombreImagen){
        switch (nombreImagen){
            case "aceitunesa1kg":
                holder.imagenProducto.setImageResource(R.drawable.aceitunesa1kg);
                break;
            case "aceitunesa10g":
                holder.imagenProducto.setImageResource(R.drawable.aceitunesa10g);
                break;
            case "aceitunesa85g":
                holder.imagenProducto.setImageResource(R.drawable.aceitunesa85g);
                break;
            case "aceitunesa200g":
                holder.imagenProducto.setImageResource(R.drawable.aceitunesa200g);
                break;
            case "ajicriollo1kg":
                holder.imagenProducto.setImageResource(R.drawable.ajicriollo1kg);
                break;
            case "ajicriollo10g":
                holder.imagenProducto.setImageResource(R.drawable.ajicriollo10g);
                break;
            case "ajicriollo85g":
                holder.imagenProducto.setImageResource(R.drawable.ajicriollo85g);
                break;
            case "ajicriollo200g":
                holder.imagenProducto.setImageResource(R.drawable.ajicriollo200g);
                break;
            case "ketchup1kg":
                holder.imagenProducto.setImageResource(R.drawable.ketchup1kg);
                break;
            case "ketchup10g":
                holder.imagenProducto.setImageResource(R.drawable.ketchup10g);
                break;
            case "ketchup85g":
                holder.imagenProducto.setImageResource(R.drawable.ketchup85g);
                break;
            case "ketchup200g":
                holder.imagenProducto.setImageResource(R.drawable.ketchup200g);
                break;
            case "mayonesa1kg":
                holder.imagenProducto.setImageResource(R.drawable.mayonesa1kg);
                break;
            case "mayonesa10g":
                holder.imagenProducto.setImageResource(R.drawable.mayonesa10g);
                break;
            case "mayonesa200g":
                holder.imagenProducto.setImageResource(R.drawable.mayonesa200g);
                break;
            case "mayopalta85g":
                holder.imagenProducto.setImageResource(R.drawable.mayopalta85g);
                break;
            case "mayopalta200g":
                holder.imagenProducto.setImageResource(R.drawable.mayopalta200g);
                break;
            case "mostaza1kg":
                holder.imagenProducto.setImageResource(R.drawable.mostaza1kg);
                break;
            case "mostaza10g":
                holder.imagenProducto.setImageResource(R.drawable.mostaza10g);
                break;
            case "mayonesa85g":
                holder.imagenProducto.setImageResource(R.drawable.mayonesa85g);
                break;
            case "mostaza85g":
                holder.imagenProducto.setImageResource(R.drawable.mostaza85g);
                break;
            case "mostaza200g":
                holder.imagenProducto.setImageResource(R.drawable.mostaza200g);
                break;
            case "pastirroja200g":
                holder.imagenProducto.setImageResource(R.drawable.pastirroja200g);
                break;
            case "salsadetomate200g":
                holder.imagenProducto.setImageResource(R.drawable.salsadetomate200g);
                break;
            case "vinagreta1kg":
                holder.imagenProducto.setImageResource(R.drawable.vinagreta1kg);
                break;
            case "vinagreta85g":
                holder.imagenProducto.setImageResource(R.drawable.vinagreta85g);
                break;
            case "vinagreta200g":
                holder.imagenProducto.setImageResource(R.drawable.vinagreta200g);
                break;
            default:
                holder.imagenProducto.setImageResource(R.drawable.vinagreta200g);
                break;
        }
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String charString = constraint.toString();
                if(charString.isEmpty()){
                    listaProductoFiltrada = listaProducto;
                }else{
                    ArrayList<Productos> newFilteredList = new ArrayList<>();
                    for(Productos producto:listaProducto){
                        if(producto.getDescripcionProducto().toLowerCase().contains(charString)){
                            newFilteredList.add(producto);
                        }
                    }
                    listaProductoFiltrada = newFilteredList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = listaProductoFiltrada;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                listaProductoFiltrada = (ArrayList<Productos>) results.values;
                notifyDataSetChanged();
            }
        };
    }
}
