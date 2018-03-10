package com.exportacion.alimentos.applogisticsmanagement.controladores.detalle_venta;

import com.exportacion.alimentos.applogisticsmanagement.R;
import com.exportacion.alimentos.applogisticsmanagement.model.Detalle_Pedido;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.View;
import java.util.List;

public class Adapter_Detalle_Venta extends RecyclerView.Adapter<ViewHolder_Detalle_Venta> {

    List<Detalle_Pedido> _lista$Detalle$Pedidos;

    public Adapter_Detalle_Venta(List<Detalle_Pedido> _lista$Detalle$Pedidos) {
        this._lista$Detalle$Pedidos = _lista$Detalle$Pedidos;
    }

    @Override
    public ViewHolder_Detalle_Venta onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.detalle_venta_item,parent,false);
        return new ViewHolder_Detalle_Venta(view, _lista$Detalle$Pedidos);
    }

    @Override
    public void onBindViewHolder(ViewHolder_Detalle_Venta holder, int position) {
        holder._descripcion$Producto.setText(_lista$Detalle$Pedidos.get(position).get_descripcion$Producto());
        holder._presentacion$Producto.setText(_lista$Detalle$Pedidos.get(position).get_presentacion$Producto());
        holder._cantidad$Solicitada.setText("" + _lista$Detalle$Pedidos.get(position).get_cantidad$Solicitada());
        holder._sub$Total.setText("S/ " + _lista$Detalle$Pedidos.get(position).get_sub$Total());
    }

    @Override
    public int getItemCount() {
        return _lista$Detalle$Pedidos.size();
    }
}
