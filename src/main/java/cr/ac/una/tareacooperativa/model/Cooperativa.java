package cr.ac.una.tareacooperativa.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * <p>
 * Clase objeto para la cooperativa
 * </p>
 *
 * @author Stiward Araya
 * @author Justin Mendez
 */
public class Cooperativa {

    public static final String ARCHIVO_COOPERATIVA = "dataCoperativa.json";
    public static final String DIRECTORY = "./Registros/";

    public String rutaLogoCooperativa;
    public String nombreCooperativa;

    public Cooperativa() {
        rutaLogoCooperativa = "";
        nombreCooperativa = "";
    }

    public Cooperativa(String nLogoCooperativa, String nNombreCooperativa) {
        rutaLogoCooperativa = nLogoCooperativa;
        nombreCooperativa = nNombreCooperativa;
    }
    
    public void setNombreCooperativa(String nNombreCooperativa) {
        nombreCooperativa = nNombreCooperativa;
    }

    public void setLogoCooperativa(String nLogoCooperativa) {
        rutaLogoCooperativa = nLogoCooperativa;
    }

    public String getNombreCooperativa() {
        return nombreCooperativa;
    }

    public String getLogoCooperativa() {
        return rutaLogoCooperativa;
    }

    public void cargarDatosCooperativa() {
        try
        {
            File archivo = new File(DIRECTORY + ARCHIVO_COOPERATIVA);
            if (!archivo.exists() || archivo.length() == 0)
            {
                System.out.println("El archivo está vacío o no existe.");
                return;
            }

            Gson gson = new Gson();
            FileReader reader = new FileReader(DIRECTORY + ARCHIVO_COOPERATIVA);
            Cooperativa cooperativa = gson.fromJson(reader, Cooperativa.class);
            reader.close();

            this.nombreCooperativa = cooperativa.getNombreCooperativa();
            this.rutaLogoCooperativa = cooperativa.getLogoCooperativa();

        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void guardarDatosCooperativa() {
        try
        {
            File directorio = new File(DIRECTORY);
            if (!directorio.exists())
            {
                directorio.mkdirs();
            }

            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String json = gson.toJson(this);

            FileWriter writer = new FileWriter(DIRECTORY + ARCHIVO_COOPERATIVA);
            writer.write(json);
            writer.close();

            System.out.println("Datos de la cooperativa guardados exitosamente.");

        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

}
