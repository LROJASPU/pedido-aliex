package com.exportacion.alimentos.applogisticsmanagement.controladores;

import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class AccesoServicio {

    private HttpURLConnection $conexion;
    private String $archivo_json;
    private String $parametros;
    private String $nombre_servicio;

    public AccesoServicio(String $nombre_servicio) {
        this.$nombre_servicio = $nombre_servicio;
    }

    public HttpURLConnection get$conexion() {
        return $conexion;
    }

    public void set$conexion(HttpURLConnection $conexion) {
        this.$conexion = $conexion;
    }

    public String get$archivo_json() {
        return $archivo_json;
    }

    public void set$archivo_json(String $archivo_json) {
        this.$archivo_json = $archivo_json;
    }

    public String get$parametros() {
        return $parametros;
    }

    public void set$parametros(String $parametros) {
        this.$parametros = $parametros;
    }

    public String get$nombre_servicio() {
        return $nombre_servicio;
    }

    public void set$nombre_servicio(String $nombre_servicio) {
        this.$nombre_servicio = $nombre_servicio;
    }

    public String obtenerArchivoJson(String $parametros){
        String parametros = $parametros;
        HttpURLConnection conection = null;
        String respuesta="";
        try{
            URL url = new URL("http://192.168.43.144/alimentosService/" + get$nombre_servicio());
            conection = (HttpURLConnection) url.openConnection();
            conection.setRequestMethod("POST");
            conection.setRequestProperty("Content-Length",""+Integer.toString(parametros.getBytes().length));
            conection.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(conection.getOutputStream());
            wr.writeBytes(parametros);
            wr.close();

            Scanner inStream = new Scanner(conection.getInputStream());

            while (inStream.hasNext()){
                respuesta+=(inStream.nextLine());
            }
        }catch (Exception e){

        }
        return respuesta;
    }

}
