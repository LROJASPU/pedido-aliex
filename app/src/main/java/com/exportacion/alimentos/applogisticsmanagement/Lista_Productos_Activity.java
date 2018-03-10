package com.exportacion.alimentos.applogisticsmanagement;

import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v7.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.exportacion.alimentos.applogisticsmanagement.controladores.AccesoServicio;
import com.exportacion.alimentos.applogisticsmanagement.controladores.Productos_Business;
import com.exportacion.alimentos.applogisticsmanagement.model.Productos;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class Lista_Productos_Activity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    private String $dni_cliente, $nombre_cliente, $categoria_cliente;
    Adapter productoAdapter;
    private Productos_Business $controlador_productos;
    private AccesoServicio $accesos_servicio;
    private ArrayList<Productos> listaProductos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_productos);

        $dni_cliente = getIntent().getStringExtra("$dni_cliente");
        $nombre_cliente = getIntent().getStringExtra("$nombre_cliente");
        $categoria_cliente = getIntent().getStringExtra("$categoria");

        Thread hilo = new Thread(){
            @Override
            public void run() {
                $accesos_servicio = new AccesoServicio("consulta_productos.php");
                final String $archivo_json = $accesos_servicio.obtenerArchivoJson("");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String $mensaje_usuario = obtenerListaProductos($archivo_json);
                        if($mensaje_usuario == null){
                            RecyclerView contenedor = (RecyclerView) findViewById(R.id.contenedor);
                            contenedor.setHasFixedSize(true);
                            LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                            productoAdapter = new Adapter(listaProductos);
                            contenedor.setAdapter(productoAdapter);
                            contenedor.setLayoutManager(layoutManager);
                        }else {
                            Toast.makeText(getApplicationContext(),$mensaje_usuario,Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        };
        hilo.start();
    }

    public String obtenerListaProductos(String respuesta){
        String resultado = "Inicializado";
        String estado ="Inicializado";
        String $codigo = "", $descripcion = "",$categoria = "",$presentacion = "",$imagen = "";
        double $precio = 0.0;
        int $cantidad = 0;

        try{
            JSONObject $contenido_json = new JSONObject(respuesta);
            String $estado = $contenido_json.getString("ESTADO");
            if($contenido_json.length()>1){
                if($estado.equals("2")){
                    resultado = "Estado 2";
                }else{
                    JSONArray $datos_productos = $contenido_json.optJSONArray("DATOS");
                    for (int i = 0; i < $datos_productos.length(); i++){
                        JSONObject $producto = $datos_productos.getJSONObject(i);
                        $codigo = $producto.getString("id_producto");
                        $descripcion = $producto.getString("des_producto");
                        $categoria = obtenerCategoria($producto.getInt("id_categoria"));
                        $presentacion = $producto.getString("pres_producto");
                        $imagen = $producto.getString("imag_producto");
                        $precio = obtenerPrecioEspecial($categoria_cliente,$producto.getDouble("precio_producto"));
                        $cantidad = $producto.getInt("cantidad_producto");
                        listaProductos.add(new Productos($codigo,$descripcion,$categoria,$presentacion,$imagen,$precio,$cantidad));
                    }
                    resultado = null;
                }
            }else {
                resultado = "Longitud json menor a 1";
            }
        }catch (Exception e){
            resultado = "Se ha detectado una excepción";
        }
        return resultado;
    }

    public double obtenerPrecioEspecial(String $categoria , double $precio){
        double $preciofinal = $precio;
        switch ($categoria){
            case "Premium":
                $preciofinal = $precio * 0.95;
                break;
            case "Oro":
                $preciofinal = $precio * 0.90;
                break;
            case "Inmortal":
                $preciofinal = $precio * 0.80;
                break;
            default:
                $preciofinal = $precio;
                break;
        }
        return  Math.rint($precio);
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

    @Override
    public boolean onCreateOptionsMenu (Menu menu) {
        getMenuInflater().inflate(R.menu.menu_items,menu);
        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setOnQueryTextListener(this);
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        productoAdapter.getFilter().filter(newText);
        return false;
    }
}
