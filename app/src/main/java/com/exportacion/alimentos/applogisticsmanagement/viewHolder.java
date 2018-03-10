package com.exportacion.alimentos.applogisticsmanagement;



import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.exportacion.alimentos.applogisticsmanagement.controladores.AccesoServicio;
import com.exportacion.alimentos.applogisticsmanagement.model.Productos;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

public class viewHolder extends RecyclerView.ViewHolder {

    private EditText $cantidad_EditText;
    private AccesoServicio $acceso_servicio;
    Handler handler;
    Button btnConsultarPrecio, btnSolicitar;
    ImageView imagenProducto;
    TextView descipcionProducto, presentacionProducto;
    List<Productos> listaProducto;

    public viewHolder(View itemView,List<Productos> datos) {
        super(itemView);
        $cantidad_EditText = (EditText) itemView.findViewById(R.id.cantidad_EditText);
        btnConsultarPrecio = (Button) itemView.findViewById(R.id.btnConsultarPrecio);
        btnSolicitar = (Button) itemView.findViewById(R.id.btnSolicitar);
        imagenProducto = (ImageView) itemView.findViewById(R.id.imagenProducto);
        descipcionProducto = (TextView) itemView.findViewById(R.id.descripcionProducto);
        presentacionProducto = (TextView) itemView.findViewById(R.id.presentacionProducto);
        listaProducto = datos;

        btnSolicitar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handler = new Handler();
                int position = getAdapterPosition();
                final Productos producto = listaProducto.get(position);
                final int $cantidad_producto = validarCantidad($cantidad_EditText);
                Thread hilo = new Thread(){
                    @Override
                    public void run() {
                        $acceso_servicio = new AccesoServicio("ingresar_detalle_pedido.php");
                        final String $archivo_json_detalle_pedido = $acceso_servicio.obtenerArchivoJson("codigo="+producto.getId_producto()+"&&cantidad="+ $cantidad_producto);
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                String $mensaje_usuario = validarMensaje($archivo_json_detalle_pedido);
                                Toast.makeText(btnConsultarPrecio.getContext(),$mensaje_usuario,Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                };
                hilo.start();
            }
        });
        btnConsultarPrecio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handler = new Handler();
                int position = getAdapterPosition();
                final Productos producto = listaProducto.get(position);
                final int $cantidad_producto = validarCantidad($cantidad_EditText);

                Thread $hilo_secundario = new Thread(){
                    @Override
                    public void run() {
                        $acceso_servicio = new AccesoServicio("consultar_precio.php");
                        final String $archivo_json_consulta_precio = $acceso_servicio.obtenerArchivoJson("codigo="+producto.getId_producto()+"&&cantidad="+ $cantidad_producto);
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                String $mensaje_usuario = validarMensaje($archivo_json_consulta_precio);
                                Toast.makeText(btnConsultarPrecio.getContext(),$mensaje_usuario,Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                };
                $hilo_secundario.start();
            }
        });
    }

    public String validarMensaje(String respuesta){
        String resultado = "Inicializado";
        String estado ="Inicializado";
        try{
            JSONObject objectJSON = new JSONObject(respuesta);
            estado = objectJSON.getString("ESTADO");
            if(objectJSON.length()>1){
                if(estado.equals("2")){
                    //Rescatar mensaje de JSON
                    resultado = "Nota: Se ha detectado un error al momento de registrar el producto";

                }else{
                    JSONArray $datos_mensaje = objectJSON.optJSONArray("MENSAJE");
                    JSONObject $mensaje = $datos_mensaje.getJSONObject(0);
                    resultado = $mensaje.getString("NOTA");
                }
            }else {
                //Rescartar mensaje de error al recibir el JSON
                resultado = "Error al obtener datos del archivo JSON";
            }
        }catch (Exception e){
            resultado = "";
        }
        return resultado;
    }

    public int validarCantidad(EditText $cantidad_EditText){
        int $cantidad = 0;
        if($cantidad_EditText.getText().toString().isEmpty()){
            Toast.makeText(btnConsultarPrecio.getContext(),"Por favor, es necesario ingresar la cantidad",Toast.LENGTH_LONG).show();
        }else{
            $cantidad  = Integer.parseInt($cantidad_EditText.getText().toString());
            if($cantidad < 0){
                Toast.makeText(btnConsultarPrecio.getContext(),"Por favor, considerar solo numeros positivos",Toast.LENGTH_LONG).show();
                $cantidad = 1;
            }
        }
        return $cantidad;
    }
}
