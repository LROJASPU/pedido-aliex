package com.exportacion.alimentos.applogisticsmanagement.model;

import java.util.UUID;

public class Productos {
    private String id_producto;
    private String descripcionProducto;
    private String categoriaProducto;
    private String presentacionProducto;
    private String imagenProducto;
    private double precioUnitario;
    private int cantidadProducto;

    public Productos(String descripcionProducto, String categoriaProducto,String presentacionProducto,String imagenProducto ,double precioUnitario,int cantidadProducto) {
        this.id_producto= UUID.randomUUID().toString();
        this.descripcionProducto = descripcionProducto;
        this.categoriaProducto = categoriaProducto;
        this.presentacionProducto = presentacionProducto;
        this.imagenProducto = imagenProducto;
        this.precioUnitario = precioUnitario;
        this.cantidadProducto = cantidadProducto;
    }

    public Productos(String id_producto, String descripcionProducto, String categoriaProducto, String presentacionProducto, String imagenProducto, double precioUnitario, int cantidadProducto) {
        this.id_producto = id_producto;
        this.descripcionProducto = descripcionProducto;
        this.categoriaProducto = categoriaProducto;
        this.presentacionProducto = presentacionProducto;
        this.imagenProducto = imagenProducto;
        this.precioUnitario = precioUnitario;
        this.cantidadProducto = cantidadProducto;
    }

    public String getId_producto (){
        return  id_producto;
    }

    public String getDescripcionProducto(){
        return descripcionProducto;
    }

    public void setDescripcionProducto(String descripcionProducto){
        this.descripcionProducto = descripcionProducto;
    }

    public String getCategoriaProducto(){
        return  categoriaProducto;
    }

    public void setCategoriaProducto(String categoriaProducto){
        this.categoriaProducto = categoriaProducto;
    }

    public String getPresentacionProducto(){
        return  presentacionProducto;
    }

    public void setPresentacionProducto(String presentacionProducto){
        this.presentacionProducto = presentacionProducto;
    }

    public String getImagenProducto(){
        return imagenProducto;
    }

    public void setImagenProducto(String imagenProducto){
        this.imagenProducto = imagenProducto;
    }

    public double getPrecioUnitario(){
        return  precioUnitario;
    }

    public void setPrecioUnitario(double precioUnitario){
        this.precioUnitario = precioUnitario;
    }

    public int getCantidadProducto(){
        return cantidadProducto;
    }

    public void setCantidadProducto(int cantidadProducto){
        this.cantidadProducto = cantidadProducto;
    }
}
