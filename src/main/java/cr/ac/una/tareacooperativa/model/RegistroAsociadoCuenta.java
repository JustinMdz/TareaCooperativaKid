package cr.ac.una.tareacooperativa.model;

import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.logging.Logger;

import static java.util.logging.Level.SEVERE;

/**
 * <p>
 * Clase registro de AsociadoCuentas, controla el flujo<br>
 * de datos de las cuentas y asociados en el sistema.
 * </p>
 *
 * @author Stiward Araya
 * @author Justin Mendez
 */
public class RegistroAsociadoCuenta {

    public static final String ARCHIVO_ASOCUENTAS = "dataAsociadosCuentas.json";
    public static final String DIRECTORY = "./Registros/";

    ArrayList<AsociadoCuenta> asociadosCuentas;

    public RegistroAsociadoCuenta() {
        asociadosCuentas = new ArrayList<>();
    }

    private int getIndiceAsociadoCuenta(String folio, int idCuenta) {
        for (int i = 0; i < asociadosCuentas.size(); i++)
        {
            if (asociadosCuentas.get(i).getFolioAsociado().equals(folio) && asociadosCuentas.get(i).getIdCuenta() == idCuenta)
            {
                return i;
            }
        }
        return -1;
    }

    public AsociadoCuenta buscarAsociadoCuenta(String folio, int idCuenta) {
        for (AsociadoCuenta asoCuenta : asociadosCuentas)
        {
            if (asoCuenta.getFolioAsociado().equals(folio) && asoCuenta.getIdCuenta() == idCuenta)
            {
                return asoCuenta;
            }
        }
        return null;
    }

    public String agregarAsociadoCuenta(AsociadoCuenta asociadoCuenta) {
        if (buscarAsociadoCuenta(asociadoCuenta.getFolioAsociado(), asociadoCuenta.getIdCuenta()) == null)
        {
            asociadosCuentas.add(asociadoCuenta);
            return "Cuenta agregada al asociado exitosamente!";
        } else
        {
            return "";
        }
    }

    public String eliminarAsociadoCuenta(String folio, int idCuenta) {
        if (buscarAsociadoCuenta(folio, idCuenta) != null)
        {
            asociadosCuentas.remove(getIndiceAsociadoCuenta(folio, idCuenta));
            return "Cuenta eliminada del asociado exitosamente";
        } else
        {
            return "Cuenta no se ha podido elimanar del socio correctamente";
        }
    }

    public void depositarDinero(String folio, int idCuenta, Integer monto) {
        for (AsociadoCuenta asoCuenta : asociadosCuentas)
        {
            if (asoCuenta.getFolioAsociado().equals(folio) && asoCuenta.getIdCuenta() == idCuenta)
            {
                asoCuenta.depositarDinero(monto);
            }
        }
    }

    public String retirarDinero(String folio, int idCuenta, Integer monto) {
        for (AsociadoCuenta asoCuenta : asociadosCuentas)
        {
            if (asoCuenta.getFolioAsociado().equals(folio) && asoCuenta.getIdCuenta() == idCuenta)
            {
                if (monto <= asoCuenta.getBalanceCuenta())
                {
                    asoCuenta.retirarDinero(monto);
                    return "Retiro realizado exitosamente";
                }
            }

        }
        return "No existen suficientes fondos para realizar un retiro";
    }

    public ArrayList<AsociadoCuenta> getAsociadosCuentas() {
        return asociadosCuentas;
    }

    /**
     * <p>
     * Guarda los asociadosCuenta del ArrayList de la clase<br>
     * en un documento .json
     * </p>
     */
    public void guardarAsociadoCuenta() {

        File directorio = new File(DIRECTORY);
        if (!directorio.exists())
        {
            directorio.mkdirs();
        }
        Gson gson = new Gson();
        String json = gson.toJson(asociadosCuentas);
        try (FileWriter fileWriter = new FileWriter(DIRECTORY + ARCHIVO_ASOCUENTAS))
        {
            fileWriter.write(json);
            System.out.println("Datos guardados en: " + DIRECTORY + ARCHIVO_ASOCUENTAS + " correctamente.");
        } catch (IOException ex)
        {
            Logger.getLogger(RegistroAsociado.class.getName()).log(SEVERE, "[IOException archivo no encontrado]", ex);
        }
    }

    /**
     * <p>
     * Carga la lista de asociadosCuenta guardados en un<br>
     * documento .json en el ArrayList de la clase.
     * </p>
     */
    public void cargarAsociadoCuenta() {
        File archivo = new File(DIRECTORY + ARCHIVO_ASOCUENTAS);
        if (!archivo.exists() || archivo.length() == 0)
        {
            System.out.println("El archivo está vacío o no existe.");
            return;
        }
        try (FileReader fileReader = new FileReader(archivo))
        {
            Gson gson = new Gson();
            asociadosCuentas = gson.fromJson(fileReader, new TypeToken<ArrayList<AsociadoCuenta>>() {
            }.getType());
            System.out.println("Datos cargados desde " + DIRECTORY + ARCHIVO_ASOCUENTAS + " correctamente.");
        } catch (IOException ex)
        {
            Logger.getLogger(RegistroAsociado.class.getName()).log(SEVERE, "[IOException archivo no encontrado]", ex);
        }
    }

    public ArrayList<Integer> getIdCuentasByFolio(String folioAsociado) {
        ArrayList<Integer> idCuentasAsociado = new ArrayList<>();

        for (AsociadoCuenta asociadoCuenta : asociadosCuentas)
        {
            if (asociadoCuenta.getFolioAsociado().equals(folioAsociado))
            {
                idCuentasAsociado.add(asociadoCuenta.getIdCuenta());
            }
        }

        return idCuentasAsociado;

    }

    public boolean isAccountLinkedToAsociado(int idCuenta) {
        for (AsociadoCuenta asociadoCuenta : asociadosCuentas)
        {
            if (asociadoCuenta.getIdCuenta() == idCuenta)
            {
                return true;
            }

        }
        return false;
    }

    public void eliminarInformacionAsociado(String folioAsociado) {
        Iterator<AsociadoCuenta> iterator = asociadosCuentas.iterator();
        while (iterator.hasNext())
        {
            AsociadoCuenta asociadoCuenta = iterator.next();
            if (asociadoCuenta.getFolioAsociado().equals(folioAsociado))
            {
                iterator.remove();
            }
        }
    }

}
