package com.exportacion.alimentos.applogisticsmanagement.controladores;

import android.widget.Toast;

import com.exportacion.alimentos.applogisticsmanagement.model.Productos;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;

public class Productos_Business {


    public String obtenerListaProductos(String $archivo_json, ArrayList<Productos> $lista_productos){
        $lista_productos = new ArrayList<Productos>();
        String resultado = "Inicializado";
        String estado ="Inicializado";
        String codigo = "";
        String des = "";
        String categoria;
        String descrip;
        String imag;
        double precio;
        int cantidad;

        try{
            JSONObject $contenido_json = new JSONObject($archivo_json);
            String $estado = $contenido_json.getString("ESTADO");
            if($contenido_json.length()>1){
                if($estado.equals("2")){
                    resultado = "Estado 2";
                }else{
                    JSONArray $datos_productos = $contenido_json.optJSONArray("DATOS");
                    for (int i = 0; i < $datos_productos.length(); i++){
                        JSONObject $producto = $datos_productos.getJSONObject(i);
                        codigo = $producto.getString("id_producto");
                        des = $producto.getString("des_producto");
                        categoria = obtenerCategoria($producto.getInt("id_categoria"));
                        descrip = $producto.getString("pres_producto");
                        imag = $producto.getString("imag_producto");
                        precio = $producto.getDouble("precio_producto");
                        cantidad = $producto.getInt("cantidad_producto");
                        $lista_productos.add(new Productos(codigo,des,categoria,descrip,imag,precio,cantidad));
                    }
                }
            }else {
                resultado = "Longitud json menor a 1";
            }
        }catch (Exception e){
            resultado = "Se ha detectado una excepción";
        }
        return resultado;
    }

    public String obtenerCategoria(int $id_categoria){
        String $categoria;
        switch ($id_categoria){
            case 1:
                $categoria = "Salsas";
                break;
            case 2:
                $categoria =  "Pastas";
                break;
            case 3:
                $categoria = "Pulpas";
                break;
            default:
                $categoria = "Salsas";
                break;
        }
        return $categoria;
    }

}
