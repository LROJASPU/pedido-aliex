package com.exportacion.alimentos.applogisticsmanagement;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.exportacion.alimentos.applogisticsmanagement.controladores.AccesoServicio;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class Login_Activity extends AppCompatActivity {

    private Button $boton_ingresar;
    private EditText $edit_text_correo, $edit_text_password;
    private String $dni_vendedor="Inicializado", $nombre_vendedor = "Inicializado", $mensaje_usuario = "Inicializado";
    private AccesoServicio $accesos_servicio;

    public String validar_ingreso(String respuesta){
        String resultado = "Inicializado";
        String estado ="Inicializado";
        try{
            JSONObject objectJSON = new JSONObject(respuesta);
            estado = objectJSON.getString("ESTADO");
            if(objectJSON.length()>1){
                if(estado.equals("2") || estado.equals("3")){
                    //Rescatar mensaje de JSON
                    resultado = objectJSON.getString("MENSAJE");
                }else{
                    //Rescatar el DNI y enviarlo
                    JSONArray $datos_vendedor = objectJSON.optJSONArray("DATOS");
                    JSONObject $vendedor = $datos_vendedor.getJSONObject(0);
                    $dni_vendedor = $vendedor.getString("DNI_EMPLEADOS");
                    $nombre_vendedor = $vendedor.getString("APE_PATERNO") + " " + $vendedor.getString("APE_MATERNO") + ", " + $vendedor.getString("NOMBRES_EMP");
                    resultado = null;
                }
            }else {
                //Rescartar mensaje de error al recibir el JSON
                resultado = "Error al obtener datos del archivo JSON";
            }
        }catch (Exception e){
            resultado = "Se ha detectado una excepción";
        }
        return resultado;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Oculta el ActionBar de la actividad de login.
        getSupportActionBar().hide();

        //Rescata los elementos de la actividad
        $edit_text_correo = (EditText) findViewById(R.id.correoEditText);
        $edit_text_password = (EditText) findViewById(R.id.passwordEditText);
        $boton_ingresar = (Button) findViewById(R.id.btnIngresar);

        //Evento del boton ingresar
        $boton_ingresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread tr = new Thread(){
                    @Override
                    public void run(){
                        $accesos_servicio = new AccesoServicio("ingreso_sistema.php");
                        final String $archivo_json = $accesos_servicio.obtenerArchivoJson("dni="+$edit_text_correo.getText().toString()+"&pass="+$edit_text_password.getText().toString());
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                $mensaje_usuario = validar_ingreso($archivo_json);
                                if($mensaje_usuario == null){
                                    Intent intent = new Intent(Login_Activity.this,Principal_Activity.class);
                                    intent.putExtra("$dni_vendedor",$dni_vendedor);
                                    intent.putExtra("$nombre_vendedor",$nombre_vendedor);
                                    startActivity(intent);
                                }else{
                                    Toast.makeText(getApplicationContext(),$mensaje_usuario,Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                    }
                };
                tr.start();
            }
        });

    }
}
