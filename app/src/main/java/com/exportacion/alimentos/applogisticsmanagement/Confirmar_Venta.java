package com.exportacion.alimentos.applogisticsmanagement;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.exportacion.alimentos.applogisticsmanagement.controladores.AccesoServicio;
import com.exportacion.alimentos.applogisticsmanagement.controladores.detalle_venta.Adapter_Detalle_Venta;
import com.exportacion.alimentos.applogisticsmanagement.model.Detalle_Pedido;
import com.exportacion.alimentos.applogisticsmanagement.model.Productos;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class Confirmar_Venta extends AppCompatActivity implements View.OnClickListener{

    private Button _cancelar$venta,_confirmar$venta;
    private ArrayList<Detalle_Pedido> _lista$Detalle$Pedido = new ArrayList<>();
    private AccesoServicio _acceso$Servicio;
    Adapter_Detalle_Venta _detalle$Adapter;
    String $archivo_json_detalle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmar__venta);

        _cancelar$venta = (Button) findViewById(R.id.btn_cancelarVenta);
        _confirmar$venta = (Button) findViewById(R.id.btn_confirmarVenta);

        Thread _hilo = new Thread(){
            @Override
            public void run() {
                _acceso$Servicio = new AccesoServicio("obtener_informacion_pedido.php");
                final String $archivo_json = _acceso$Servicio.obtenerArchivoJson("");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String $mensaje_usuario = obtenerDetallePedido($archivo_json);
                        if($mensaje_usuario == null){
                            RecyclerView contenedor = (RecyclerView) findViewById(R.id.contenedor_detalle_venta);
                            contenedor.setHasFixedSize(true);
                            LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                            _detalle$Adapter = new Adapter_Detalle_Venta(_lista$Detalle$Pedido);
                            contenedor.setAdapter(_detalle$Adapter);
                            contenedor.setLayoutManager(layoutManager);
                            contenedor.addItemDecoration(new DividerItemDecoration(contenedor.getContext(),layoutManager.getOrientation()));
                        }else{
                            Toast.makeText(getApplicationContext(),"MENSAJE USUARIO:" + $mensaje_usuario,Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        };

        _hilo.start();

        _confirmar$venta.setOnClickListener(this);
        _cancelar$venta.setOnClickListener(this);


    }

    public String obtenerDetallePedido(String respuesta){
        String resultado = "Inicializado";
        String estado ="Inicializado";
        String _descripcion, _presentacion;
        int _cantidad;
        double _subTotal;

        try{
            JSONObject $contenido_json = new JSONObject(respuesta);
            String $estado = $contenido_json.getString("ESTADO");
            if($contenido_json.length()>1){
                if($estado.equals("1")){
                    JSONArray _detalle$pedidos = $contenido_json.optJSONArray("DETALLE_DATOS");
                    for (int i = 0; i < _detalle$pedidos.length(); i++){
                        JSONObject _detalle = _detalle$pedidos.getJSONObject(i);
                        _descripcion = _detalle.getString("DESCRIPCION");
                        _presentacion = _detalle.getString("PRESENTACION");
                        _cantidad = _detalle.getInt("CANTIDAD");
                        _subTotal = _detalle.getDouble("SUB_TOTAL");
                        _lista$Detalle$Pedido.add(new Detalle_Pedido(_descripcion,_presentacion,_cantidad,_subTotal));
                    }
                    resultado = null;
                }else{
                    resultado = "el estado es diferente a 1";
                }
            }else {
                resultado = "Longitud json menor a 1";
            }
        }catch (Exception e){
            resultado = "Se ha detectado una excepción";
        }
        return resultado;
    }

    @Override
    public void onClick(View v) {
        final String _estado$confirmacion;
        if(v.getId() == R.id.btn_confirmarVenta){
            _estado$confirmacion = "CONFORME";
        }else{
            _estado$confirmacion = "CANCELADO";
        }

        Thread $hilo_secundario = new Thread(){
            @Override
            public void run() {
                _acceso$Servicio = new AccesoServicio("confirmar_venta.php");
                final String $archivo_json_consulta_precio = _acceso$Servicio.obtenerArchivoJson("confirmacion="+_estado$confirmacion);
                $archivo_json_detalle = $archivo_json_consulta_precio;
            }
        };
        $hilo_secundario.start();
        Toast.makeText(getApplicationContext(),"El estado del pedido tiene un estado: "+ _estado$confirmacion,Toast.LENGTH_LONG).show();
        Intent intent = new Intent(Confirmar_Venta.this,Principal_Activity.class);
        startActivity(intent);
    }
}
