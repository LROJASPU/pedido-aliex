package com.exportacion.alimentos.applogisticsmanagement.model;


public class Detalle_Pedido {
    private String _descripcion$Producto;
    private String _presentacion$Producto;
    private int _cantidad$Solicitada;
    private double _sub$Total;

    public Detalle_Pedido(String _descripcion$Producto, String _presentacion$Producto, int _cantidad$Solicitada, double _sub$Total) {
        this._descripcion$Producto = _descripcion$Producto;
        this._presentacion$Producto = _presentacion$Producto;
        this._cantidad$Solicitada = _cantidad$Solicitada;
        this._sub$Total = _sub$Total;
    }

    public String get_presentacion$Producto() {
        return _presentacion$Producto;
    }

    public void set_presentacion$Producto(String _presentacion$Producto) {
        this._presentacion$Producto = _presentacion$Producto;
    }

    public String get_descripcion$Producto() {
        return _descripcion$Producto;
    }

    public void set_descripcion$Producto(String _descripcion$Producto) {
        this._descripcion$Producto = _descripcion$Producto;
    }

    public int get_cantidad$Solicitada() {
        return _cantidad$Solicitada;
    }

    public void set_cantidad$Solicitada(int _cantidad$Solicitada) {
        this._cantidad$Solicitada = _cantidad$Solicitada;
    }

    public double get_sub$Total() {
        return _sub$Total;
    }

    public void set_sub$Total(double _sub$Total) {
        this._sub$Total = _sub$Total;
    }
}
