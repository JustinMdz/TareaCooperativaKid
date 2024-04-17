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
public class AsociadoCuenta {

    private Integer idCuenta;
    private String folioAsociado;
    private Integer balanceCuenta;

    public AsociadoCuenta() {
        idCuenta = 0;
        balanceCuenta = 0;
        folioAsociado = "";
    }

    public AsociadoCuenta(String nFolioAsociado, int nIdCuenta, int nBalanceCuenta) {
        idCuenta = nIdCuenta;
        balanceCuenta = nBalanceCuenta;
        folioAsociado = nFolioAsociado;
    }

    public void setFolioAsociado(String nFolioAsociado) {
        folioAsociado = nFolioAsociado;
    }

    public void setIdCuenta(int nIdCuenta) {
        idCuenta = nIdCuenta;
    }

    public void setBalanceCuenta(int nbalanceCuenta) {
        balanceCuenta = nbalanceCuenta;
    }

    public String getFolioAsociado() {
        return folioAsociado;
    }

    public Integer getIdCuenta() {
        return idCuenta;
    }

    public Integer getBalanceCuenta() {
        return balanceCuenta;
    }

    public String depositarDinero(int cantidad) {
        balanceCuenta += cantidad;
        return "Deposito de dinero Exitoso";
    }

    public String retirarDinero(int cantidad) {
        if (cantidad < balanceCuenta)
        {
            balanceCuenta -= cantidad;
            return "Retiro de de dinero Existoso";
        } else
        {
            return "No tienes ese dinero en la cuenta";
        }
    }

}
