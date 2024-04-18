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

/**
 * <p>
 * Clase registro de Cuentas, controla el flujo<br>
 * de datos de las cuentas en el sistema.
 * </p>
 *
 * @author Stiward Araya
 * @author Justin Mendez
 */
public class RegistroCuenta {

    ArrayList<Cuenta> cuentas;
    public static final String DIRECTORY = "./Registros/";
    public static final String ARCHIVO_CUENTAS = "dataCuentas.json";

    public RegistroCuenta() {
        cuentas = new ArrayList<>();
    }

    public ArrayList<Cuenta> getCuentas() {
        return cuentas;
    }

    private int getIndiceCuenta(int idCuenta) {
        for (int i = 0; i < cuentas.size(); i++)
        {
            if (cuentas.get(i).getId() == idCuenta)
            {
                return i;
            }
        }
        return -1;
    }

    public Cuenta buscarCuentaNombre(String nombre){
        for (Cuenta cuenta : cuentas)
        {
            if (cuenta.getNombre().equals(nombre))
            {
                return cuenta;
            }
        }
        return null;
    }

    public Cuenta buscarCuenta(int idCuenta) {
        for (Cuenta cuenta : cuentas)
        {
            if (cuenta.getId() == idCuenta)
            {
                return cuenta;
            }
        }
        return null;
    }

    public String agregarCuenta(Cuenta cuenta) {
        if (buscarCuenta(cuenta.getId()) == null)
        {
            cuentas.add(cuenta);
            return "Cuenta agregada exitosamente!";
        } else
        {
            return "Esa Cuenta ya está registrada";
        }
    }

    public String eliminarCuenta(int idCuenta) {
        if (buscarCuenta(idCuenta) != null)
        {
            cuentas.remove(getIndiceCuenta(idCuenta));
            return "Cuenta eliminada exitosamente!";
        } else
        {
            return "Esa cuenta no se encuentra registrada";
        }
    }

    public String modificarCuenta(Cuenta cuenta) {
        if (buscarCuenta(cuenta.getId()) != null)
        {
            cuentas.set(getIndiceCuenta(cuenta.getId()), cuenta);
            return "Cuenta modificada exitosamente!";
        } else
        {
            return "Esa cuenta no se encuentra registrada";
        }
    }

    public Integer getProximoIdCuenta() {
        Integer proximoId = 0;

        for (Cuenta cuenta : cuentas)
        {
            proximoId = cuenta.getId();
        }

        return proximoId + 1;
    }

    public Integer buscarCuentaPorNombre(String nameCuenta) {
        for (Cuenta cuentaId : cuentas)
        {
            if (cuentaId.getNombre().equals(nameCuenta))
            {
                return cuentaId.getId();
            }

        }
        return 0;
    }

    public String getNombreCuentaByID(Integer idCuenta) {
        for (Cuenta cuentaId : cuentas)
        {
            if (cuentaId.getId() == idCuenta)
            {
                return cuentaId.getNombre();
            }

        }
        return "";
    }

    /**
     * <p>
     * Guarda las cuentas del ArrayList de la clase<br>
     * en un documento .json
     * </p>
     */
    public void guardarCuentas() {

        File directorio = new File(DIRECTORY);
        if (!directorio.exists())
        {
            directorio.mkdirs();
        }
        Gson gson = new Gson();
        String json = gson.toJson(cuentas);
        try (FileWriter fileWriter = new FileWriter(DIRECTORY + ARCHIVO_CUENTAS))
        {
            fileWriter.write(json);
            System.out.println("Datos guardados en: " + DIRECTORY + ARCHIVO_CUENTAS + " correctamente.");
        } catch (IOException ex)
        {
            Logger.getLogger(RegistroAsociado.class.getName()).log(SEVERE, "[IOException archivo no encontrado]", ex);
        }
    }

    /**
     * <p>
     * Carga la lista de cuentas guardadas en un<br>
     * documento .json en el ArrayList de la clase.
     * </p>
     */
    public void cargarCuentas() {
        File archivo = new File(DIRECTORY + ARCHIVO_CUENTAS);
        if (!archivo.exists() || archivo.length() == 0)
        {
            System.out.println("El archivo está vacío o no existe.");
            return;
        }
        try (FileReader fileReader = new FileReader(archivo))
        {
            Gson gson = new Gson();
            cuentas = gson.fromJson(fileReader, new TypeToken<ArrayList<Cuenta>>() {
            }.getType());
            System.out.println("Datos cargados desde " + DIRECTORY + ARCHIVO_CUENTAS + " correctamente.");
        } catch (IOException ex)
        {
            Logger.getLogger(RegistroAsociado.class.getName()).log(SEVERE, "[IOException archivo no encontrado]", ex);
        }
    }

    public ArrayList<String> toStringCuentas() {

        ArrayList<String> nombreCuenta = new ArrayList<>();

        for (Cuenta cuenta : cuentas)
        {
            nombreCuenta.add(cuenta.getNombre());
        }

        return nombreCuenta;
    }

    public ArrayList<String> toStringCuentasAndId() {

        ArrayList<String> tipoAndID = new ArrayList<>();

        for (Cuenta cuenta : cuentas)
        {
            tipoAndID.add(cuenta.getNombre() + " : " + Integer.toString(cuenta.getId()));
        }

        return tipoAndID;
    }

    public boolean isCuentaIntheList(String nombreCuenta) {

        for (Cuenta cuenta : cuentas)
        {
            if (cuenta.getNombre().equals(nombreCuenta))
            {
                return true;
            }

        }

        return false;

    }
}
