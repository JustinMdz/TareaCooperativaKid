/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.tareacooperativa.model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import static java.util.logging.Level.SEVERE;
import java.util.logging.Logger;

/**
 *
 * @author justi
 */
public class RegistroMovimiento {

    public static final String ARCHIVO_MOVIMIENTOS = "dataMovimientos.json";
    public static final String DIRECTORY = "./Registros/";

    ArrayList<Movimiento> movimientos;

    public RegistroMovimiento() {
        movimientos = new ArrayList<>();
    }

    public ArrayList<Movimiento> getMovimientos() {
        return movimientos;
    }

    private int getIndiceMovimiento(String folio, int idCuenta) {
        for (int i = 0; i < movimientos.size(); i++)
        {
            if (movimientos.get(i).getFolioAsociado().equals(folio) && movimientos.get(i).getIdCuenta() == idCuenta)
            {
                return i;
            }
        }
        return -1;
    }

    public Movimiento buscarMovimiento(String folio, int idCuenta) {
        for (Movimiento movimiento : movimientos)
        {
            if (movimiento.getFolioAsociado().equals(folio) && movimiento.getIdCuenta() == idCuenta)
            {
                return movimiento;
            }
        }
        return null;
    }

    public String agregarMovimientoDeposito(Movimiento movimiento) {
        movimientos.add(movimiento);
        return "MOVIMIENTODEPOSITO";
    }

    public String agregarMovimientoRetiro(Movimiento movimiento) {
        movimiento.cambiarARetiro();
        movimientos.add(movimiento);
        return "MOVIMIENTORETIRO";
    }

    public String eliminarMovimiento(String folio, int idCuenta) {
        if (buscarMovimiento(folio, idCuenta) != null)
        {
            movimientos.remove(getIndiceMovimiento(folio, idCuenta));
            return "Cuenta eliminada del asociado exitosamente";
        } else
        {
            return "Cuenta no se ha podido elimanar del socio correctamente";
        }
    }

    public void guardarMovimientos() {

        File directorio = new File(DIRECTORY);
        if (!directorio.exists())
        {
            directorio.mkdirs();
        }
        Gson gson = new Gson();
        String json = gson.toJson(movimientos);
        try (FileWriter fileWriter = new FileWriter(DIRECTORY + ARCHIVO_MOVIMIENTOS))
        {
            fileWriter.write(json);
            System.out.println("Datos guardados en: " + DIRECTORY + ARCHIVO_MOVIMIENTOS + " correctamente.");
        } catch (IOException ex)
        {
            Logger.getLogger(RegistroAsociado.class.getName()).log(SEVERE, "[IOException archivo no encontrado]", ex);
        }
    }

    public void cargarMovimientos() {
        File archivo = new File(DIRECTORY + ARCHIVO_MOVIMIENTOS);
        if (!archivo.exists() || archivo.length() == 0)
        {
            System.out.println("El archivo está vacío o no existe.");
            return;
        }
        try (FileReader fileReader = new FileReader(archivo))
        {
            Gson gson = new Gson();
            movimientos = gson.fromJson(fileReader, new TypeToken<ArrayList<Movimiento>>() {
            }.getType());
            System.out.println("Datos cargados desde " + DIRECTORY + ARCHIVO_MOVIMIENTOS + " correctamente.");
        } catch (IOException ex)
        {
            Logger.getLogger(RegistroAsociado.class.getName()).log(SEVERE, "[IOException archivo no encontrado]", ex);
        }
    }

    public ArrayList<String> getMovimientosDetalles(String folioAsociado, Integer cuentaId) {
        ArrayList<String> movimientosAsociado = new ArrayList<>();

        for (Movimiento movimiento : movimientos)
        {
            if (movimiento.getFolioAsociado().equals(folioAsociado) && movimiento.getIdCuenta() == cuentaId)
            {
                movimientosAsociado.add(movimiento.toString());
            }
        }

        return movimientosAsociado;
    }

    public boolean isAccountLinkedToAsociado(int idCuenta) {
        for (Movimiento movimiento : movimientos)
        {
            if (movimiento.getIdCuenta() == idCuenta)
            {
                return true;
            }

        }
        return false;
    }

    public void eliminarInformacionAsociado(String folioAsociado) {
        Iterator<Movimiento> iterator = movimientos.iterator();
        while (iterator.hasNext())
        {
            Movimiento movimiento = iterator.next();
            if (movimiento.getFolioAsociado().equals(folioAsociado))
            {
                iterator.remove();
            }
        }
    }

}
