package com.exportacion.alimentos.applogisticsmanagement;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.exportacion.alimentos.applogisticsmanagement.controladores.AccesoServicio;
import com.exportacion.alimentos.applogisticsmanagement.model.Productos;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;

public class Principal_Activity extends AppCompatActivity {

    private TextView $text_view_dni_vendedor, $text_view_nombre_vendedor;
    private String $dni_cliente, $nombre_cliente, $categoria;
    private double latitude = 0.0, longitude = 0.0;
    private AccesoServicio $accesos_servicio;
    private EditText $edit_text_dni_cliente;
    private Button $boton_validar;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    //Intent intent = new Intent(Principal_Activity.this,Lista_Productos_Activity.class);
                    //intent.putExtra("DNI",editTextEmail.getText().toString());
                    //startActivity(intent);
                    return true;
                case R.id.navigation_dashboard:
                    Intent _intent = new Intent(Principal_Activity.this, Confirmar_Venta.class);
                    startActivity(_intent);
                    return true;
                case R.id.navigation_notifications:
                    //mTextMessage.setText(R.string.title_notifications);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        //Rescatar elementos de la actividad principal
        $boton_validar = (Button) findViewById(R.id.validar_boton);
        $text_view_dni_vendedor = (TextView) findViewById(R.id.dniTextView);
        $text_view_nombre_vendedor = (TextView) findViewById(R.id.nombreTextView);
        $edit_text_dni_cliente = (EditText) findViewById(R.id.dni_clienteTextView);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);

        //Pasar los valores para mostrar
        $text_view_dni_vendedor.setText(getIntent().getStringExtra("$dni_vendedor"));
        $text_view_nombre_vendedor.setText(getIntent().getStringExtra("$nombre_vendedor"));
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        //Método para obtener la ubicacion
        obtener_ubicacion();

        //Evento del boton de validar cliente
        $boton_validar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread $hilo_secundario =  new Thread(){
                    @Override
                    public void run() {
                        //REALIZA LA TAREA DE VALIDAR SI EXISTE EL CLIENTE REGISTRADO
                        $accesos_servicio = new AccesoServicio("validar_cliente.php");
                        final String $archivo_json_validar_cliente = $accesos_servicio.obtenerArchivoJson("dni="+$edit_text_dni_cliente.getText().toString());

                        //REALIZA LA TAREA DE REGISTRAR EL PEDIDO
                        $accesos_servicio = new AccesoServicio("ingresar_pedido.php");
                        final String $archivo_json_pedido = $accesos_servicio.obtenerArchivoJson("dni_cliente="+$edit_text_dni_cliente.getText().toString()+"&&dni_vendedor="+$text_view_dni_vendedor.getText().toString()+"&&ubicacion="+latitude + ", "+ longitude);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                String $mensaje_validacion = validar_existencia_cliente($archivo_json_validar_cliente);
                                if($mensaje_validacion == null){
                                    Intent intent = new Intent(Principal_Activity.this,Lista_Productos_Activity.class);
                                    intent.putExtra("$dni_cliente",$dni_cliente);
                                    intent.putExtra("$nombre_cliente",$nombre_cliente);
                                    intent.putExtra("$categoria",$categoria);
                                    startActivity(intent);
                                    Toast.makeText(getApplicationContext(),"El cliente atendido es: "+$nombre_cliente,Toast.LENGTH_LONG).show();


                                }else{
                                    Toast.makeText(getApplicationContext(),$mensaje_validacion,Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                    }
                };
                $hilo_secundario.start();
            }
        });

    }

    public String validar_existencia_cliente (String $archivo_json){
        String $mensaje_usuario = "Inicializado";
        String $estado = "Inicializado";

        try{
            JSONObject $contenido_json = new JSONObject($archivo_json);
            $estado = $contenido_json.getString("ESTADO");
            if($estado.equals("1")){
                JSONArray $datos_clientes = $contenido_json.getJSONArray("DATOS");
                JSONObject $cliente = $datos_clientes.getJSONObject(0);
                $dni_cliente = $cliente.getString("DNI");
                $nombre_cliente = $cliente.getString("NOMBRE");
                $categoria = $cliente.getString("CATEGORIA");
                $mensaje_usuario = null;
            }else {
                $mensaje_usuario = "El cliente no se encuentra registrado";
            }
        }catch (Exception e){
            $mensaje_usuario = "Error! Al realizar la lectura del archivo JSON";
        }


        return $mensaje_usuario;
    }

    LocationListener locationListener = new LocationListener() {

        @Override
        public void onLocationChanged(Location location) {
            validarUbicacion(location);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };

    public void validarUbicacion(Location location){
        if(location != null){
            latitude = location.getLatitude();
            longitude = location.getLongitude();
        }
    }

    public void obtener_ubicacion() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(getApplicationContext(),"Por favor encender la herramienta GPS.",Toast.LENGTH_LONG).show();
            return;
        }
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,1000,0,locationListener);
        validarUbicacion(location);
    }
}
