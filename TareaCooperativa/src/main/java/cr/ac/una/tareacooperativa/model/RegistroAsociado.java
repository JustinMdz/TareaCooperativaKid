package cr.ac.una.tareacooperativa.model;

import java.util.ArrayList;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Logger;

import static java.util.logging.Level.SEVERE;

/**
 * Clase registro de asociados, controla el flujo <br>
 * de datos de los asociados en el sistema.
 *
 * @author Stiward Araya
 * @author Justin Mendez
 */
public class RegistroAsociado {

    private ArrayList<Asociado> asociados;
    public static final String DIRECTORY = "./Registros/";
    private static final String ARCHIVO_ASOCIADOS = "dataAsociados.json";

    public RegistroAsociado() {
        asociados = new ArrayList<>();
    }

    private int getIndiceAsociado(String folio) {
        for (int i = 0; i < asociados.size(); i++)
        {
            if (asociados.get(i).getFolio().equals(folio))
            {
                return i;
            }
        }
        return -1;
    }

    public Asociado buscarAsociado(String folio) {
        for (Asociado asociado : asociados)
        {
            if (asociado.getFolio().equals(folio))
            {
                return asociado;
            }
        }
        return null;
    }

    public String agregarAsociado(Asociado asociadoNuevo) {
        if (buscarAsociado(asociadoNuevo.getFolio()) == null)
        {
            asociados.add(asociadoNuevo);
            return "Asociado agregado exitosamente!";
        } else
        {
            return "Ese Asociado ya está registrado";
        }
    }

    public String eliminarAsociado(String folio) {
        if (buscarAsociado(folio) != null)
        {
            asociados.remove(getIndiceAsociado(folio));
            return "Asociado eliminado exitosamente!";
        } else
        {
            return "Ese asociado no se encuentra registrado";
        }
    }

    public String modificarAsociado(Asociado asociado) {
        if (buscarAsociado(asociado.getFolio()) != null)
        {
            asociados.set(getIndiceAsociado(asociado.getFolio()), asociado);
            return "Asociado modificado exitosamente!";
        } else
        {
            return "Ese asociado no se encuentra registrado";
        }
    }

    /**
     * <p>
     * Guarda los asociados del ArrayList de la clase<br>
     * en un documento .json
     * </p>
     */
    public void guardarAsociados() {
        File directorio = new File(DIRECTORY);
        if (!directorio.exists())
        {
            directorio.mkdirs();
        }

        Gson gson = new Gson();
        String json = gson.toJson(asociados);
        try (FileWriter fileWriter = new FileWriter(DIRECTORY + ARCHIVO_ASOCIADOS))
        {
            fileWriter.write(json);
        } catch (IOException ex)
        {
            Logger.getLogger(RegistroAsociado.class.getName()).log(SEVERE, "[IOException archivo no encontrado]", ex);
        }
    }

    /**
     * <p>
     * Carga la lista de asociados guardadas en un<br>
     * documento .json en el ArrayList de la clase.
     * </p>
     */
    public void cargarAsociados() {
        File archivo = new File(DIRECTORY + ARCHIVO_ASOCIADOS);
        if (!archivo.exists() || archivo.length() == 0)
        {
            System.out.println("El archivo está vacío o no existe.");
            return;
        }
        try (FileReader fileReader = new FileReader(archivo))
        {
            Gson gson = new Gson();
            asociados = gson.fromJson(fileReader, new TypeToken<ArrayList<Asociado>>() {
            }.getType());
            System.out.println("Datos cargados desde " + DIRECTORY + " correctamente.");
        } catch (IOException ex)
        {
            Logger.getLogger(RegistroAsociado.class.getName()).log(SEVERE, "[IOException archivo no encontrado]", ex);
        }
    }

}
