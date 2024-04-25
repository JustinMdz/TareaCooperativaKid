package cr.ac.una.tareacooperativa.model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Logger;

import static java.util.logging.Level.SEVERE;

/**
 * <p>
 * Clase Registro de solicitudes de deposito <br>
 * Controla el flujo de datos de las solicitudes de deposito.
 * </p>
 *
 * @author Stiward Araya
 * @author Justin Mendez
 */
public class RegistroSolicitudDeposito {

    private ArrayList<SolicitudDeposito> solicitudes;
    public static final String DIRECTORY = "./Registros/";
    private static final String ARCHIVO_SOLICITUDES = "dataSolicitudes.json";

    public RegistroSolicitudDeposito() {
        solicitudes = new ArrayList<>();
    }

    public AsociadoCuenta buscarAsociadoCuenta() {

        return null;

    }

    public ArrayList<Integer> getDepositosPorFolioYId(String folio, Integer idCuenta) {
        ArrayList<Integer> montos = new ArrayList<>();
        for (SolicitudDeposito solicitud : solicitudes)
        {
            AsociadoCuenta asoCuenta = solicitud.getAsociadoCuenta();
            if (asoCuenta.getFolioAsociado().equals(folio) && asoCuenta.getIdCuenta() == idCuenta)
            {
                montos.add(solicitud.getDeposito());
            }
        }

        return montos;
    }

    public SolicitudDeposito buscarSolicitudDeposito(String folio, Integer idCuenta) {
        for (SolicitudDeposito solicitud : solicitudes)
        {
            AsociadoCuenta asoCuenta = new AsociadoCuenta(folio, idCuenta, 0);
            if (asoCuenta.getFolioAsociado().equals(solicitud.getAsociadoCuenta().getFolioAsociado()) && asoCuenta.getIdCuenta() == solicitud.getAsociadoCuenta().getIdCuenta())
            {
                return solicitud;
            }
        }
        return null;
    }

    public SolicitudDeposito buscarSolicitudDepositoPorMonto(String folio, Integer idCuenta, Integer monto) {
        for (SolicitudDeposito solicitud : solicitudes)
        {
            AsociadoCuenta asoCuenta = new AsociadoCuenta(folio, idCuenta, 0);
            if (asoCuenta.getFolioAsociado().equals(solicitud.getAsociadoCuenta().getFolioAsociado()) && asoCuenta.getIdCuenta() == solicitud.getAsociadoCuenta().getIdCuenta() && monto == solicitud.getDeposito())
            {
                return solicitud;
            }
        }
        return null;
    }

    public String agregarDeposito(SolicitudDeposito solicitud) {
        if (solicitud != null)
        {
            solicitudes.add(solicitud);
            return "Solicitud enviada exitosamente";
        } else
        {
            return "Error al agregar el deposito";
        }
    }

    public String eliminarSolicitud(SolicitudDeposito solicitud) {
        if (solicitud != null)
        {
            solicitudes.remove(solicitud);
            return "Solicitud eliminada exitosamente";
        } else
        {
            return "Error al eliminar la solicitud";
        }
    }

    public ArrayList<SolicitudDeposito> getDepositos() {
        return solicitudes;
    }

    public void guardarSolicitudes() {
        File directorio = new File(DIRECTORY);
        if (!directorio.exists())
        {
            directorio.mkdirs();
        }
//
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

    public void cargarSolicitudes() {
        File archivo = new File(DIRECTORY + ARCHIVO_SOLICITUDES);
        if (!archivo.exists() || archivo.length() == 0)
        {
            System.out.println("El archivo está vacío o no existe.");
            return;
        }
        try (FileReader fileReader = new FileReader(archivo))
        {
            Gson gson = new Gson();
            solicitudes = gson.fromJson(fileReader, new TypeToken<ArrayList<SolicitudDeposito>>() {
            }.getType());
            System.out.println("Datos cargados desde " + DIRECTORY + " correctamente.");
        } catch (IOException ex)
        {
            Logger.getLogger(RegistroAsociado.class.getName()).log(SEVERE, "[IOException archivo no encontrado]", ex);
        }
    }

    public void eliminarInformacionAsociado(String folioAsociado) {
        Iterator<SolicitudDeposito> iterator = solicitudes.iterator();
        while (iterator.hasNext())
        {
            SolicitudDeposito solicitudDeposito = iterator.next();
            if (solicitudDeposito.getAsociadoCuenta().getFolioAsociado().equals(folioAsociado))
            {
                iterator.remove();
            }
        }
    }

}
