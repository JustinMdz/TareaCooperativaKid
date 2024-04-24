package cr.ac.una.tareacooperativa.model;

/**
 * <p>
 * Clase objeto para asociado cuenta.<br><br>
 * El objeto AsociadoCuenta representa y controla <br>
 * la relación entre Asociados y tipos de cuenta <br>
 * ya que ambas entidades presentan una cardinalidad <br>
 * n/n de modo que se eviten grupos de repetición o <br>
 * complicaciones innecesarias en el sistema.
 * </p>
 *
 * @author Stiward Araya
 * @author Justin Mendez
 */
public class Movimiento {

    private Integer idCuenta;
    private String folioAsociado;
    private Integer movimiento;

    public Movimiento() {
        idCuenta = 0;
        movimiento = 0;
        folioAsociado = "";
    }

    public Movimiento(String nFolioAsociado, int nIdCuenta, int nMovimiento) {
        idCuenta = nIdCuenta;
        movimiento = nMovimiento;
        folioAsociado = nFolioAsociado;
    }

    public void setFolioAsociado(String nFolioAsociado) {
        folioAsociado = nFolioAsociado;
    }

    public void setIdCuenta(int nIdCuenta) {
        idCuenta = nIdCuenta;
    }

    public void setMovimiento(int nMovimiento) {
        movimiento = nMovimiento;
    }

    public String getFolioAsociado() {
        return folioAsociado;
    }

    public Integer getIdCuenta() {
        return idCuenta;
    }

    public Integer getMovimiento() {
        return movimiento;
    }

    public void cambiarARetiro() {
        this.movimiento = movimiento * -1;
    }

    @Override
    public String toString() {
        if (movimiento > 0)
        {
            return "DEPOSITO: " + movimiento;
        }
        if (movimiento < 0)
        {
            return "RETIRO: " + movimiento;
        }
        return null;

    }

}