package cr.ac.una.tareacooperativa.model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Logger;

import static java.util.logging.Level.SEVERE;

public class RegistroSolicitudDeposito {
    private ArrayList<SolicitudDeposito> solicitudes;
    public static final String DIRECTORY = "./Registros/";
    private static final String ARCHIVO_SOLICITUDES = "dataSolicitudes.json";

    public RegistroSolicitudDeposito(){
        solicitudes = new ArrayList<>();
    }

    public String agregarDeposito(SolicitudDeposito solicitud){
        if(solicitud != null){
            solicitudes.add(solicitud);
            return "Solicitud enviada exitosamente";
        }else{
            return "Error al agregar el deposito";
        }
    }

    public String eliminarSolicitud(SolicitudDeposito solicitud){
        if(solicitud != null){
            solicitudes.remove(solicitud);
            return "Solicitud eliminada exitosamente";
        }else{
            return "Error al eliminar la solicitud";
        }
    }

    public ArrayList<SolicitudDeposito> getDepositos(){
        return solicitudes;
    }

    public void guardarSolicitudes() {
        File directorio = new File(DIRECTORY);
        if (!directorio.exists())
        {
            directorio.mkdirs();
        }

        Gson gson = new Gson();
        String json = gson.toJson(solicitudes);
        try (FileWriter fileWriter = new FileWriter(DIRECTORY + ARCHIVO_SOLICITUDES))
        {
            fileWriter.write(json);
        } catch (IOException ex)
        {
            Logger.getLogger(RegistroAsociado.class.getName()).log(SEVERE, "[IOException archivo no encontrado]", ex);
        }
    }

    public void cargarSolicitudes(){
        File archivo = new File(DIRECTORY + ARCHIVO_SOLICITUDES);
        if (!archivo.exists() || archivo.length() == 0)
        {
            System.out.println("El archivo está vacío o no existe.");
            return;
        }
        try (FileReader fileReader = new FileReader(archivo))
        {
            Gson gson = new Gson();
            solicitudes = gson.fromJson(fileReader, new TypeToken<ArrayList<Asociado>>() {
            }.getType());
            System.out.println("Datos cargados desde " + DIRECTORY + " correctamente.");
        } catch (IOException ex)
        {
            Logger.getLogger(RegistroAsociado.class.getName()).log(SEVERE, "[IOException archivo no encontrado]", ex);
        }
    }
}