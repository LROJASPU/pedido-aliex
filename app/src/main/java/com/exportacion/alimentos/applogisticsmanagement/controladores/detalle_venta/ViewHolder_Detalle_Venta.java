package com.exportacion.alimentos.applogisticsmanagement.controladores.detalle_venta;

import com.exportacion.alimentos.applogisticsmanagement.R;
import com.exportacion.alimentos.applogisticsmanagement.model.Detalle_Pedido;

import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.view.View;

import java.util.List;


public class ViewHolder_Detalle_Venta extends RecyclerView.ViewHolder {

    TextView _descripcion$Producto, _presentacion$Producto, _cantidad$Solicitada, _sub$Total;
    List<Detalle_Pedido> _lista$Detalle$Pedidos;

    public ViewHolder_Detalle_Venta(View itemView, List<Detalle_Pedido> _lista$Detalle$Pedidos) {
        super(itemView);
        this._lista$Detalle$Pedidos = _lista$Detalle$Pedidos;
        _descripcion$Producto = (TextView) itemView.findViewById(R.id.txt_producto);
        _presentacion$Producto = (TextView) itemView.findViewById(R.id.txt_presentacion);
        _cantidad$Solicitada = (TextView) itemView.findViewById(R.id.txt_cantidad_solicitada);
        _sub$Total = (TextView) itemView.findViewById(R.id.txt_sub_total);
    }
}
