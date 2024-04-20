package cr.ac.una.tareacooperativa.model;

/**
 * <p>
 * Clase objeto de solicitud de deposito
 * </p>
 *
 * @author Stiward Araya
 * @author Justin Mendez
 */
public class SolicitudDeposito {

    private AsociadoCuenta asociadoCuenta;
    private Integer deposito;

    public SolicitudDeposito() {
        asociadoCuenta = null;
        deposito = 0;
    }

    public SolicitudDeposito(AsociadoCuenta asociadoCuenta, Integer deposito) {
        this.asociadoCuenta = asociadoCuenta;
        this.deposito = deposito;
    }

    public void setAsociadoCuenta(AsociadoCuenta asociadoCuenta) {
        this.asociadoCuenta = asociadoCuenta;
    }

    public void setDeposito(Integer deposito) {
        this.deposito = deposito;
    }

    public AsociadoCuenta getAsociadoCuenta() {
        return asociadoCuenta;
    }

    public int getDeposito() {
        return deposito;
    }

    public String convertToString(Integer number) {
        try {
            return String.valueOf(number);
        } catch (NumberFormatException e) {
            System.err.println("NumberFormatException occurred: " + e.getMessage());
            return null; // or throw a custom exception if needed
        } catch (Exception e) {
            System.err.println("An unexpected exception occurred: " + e.getMessage());
            return null; // or throw a custom exception if needed
        }
    }

    public String getDataDeposito() {
        String detalle = "";
        detalle += "Asociado folio: " + asociadoCuenta.getFolioAsociado() + "\n";
        detalle += "solicita un deposito en su cuenta identificada: " + asociadoCuenta.getIdCuenta() + "\n";
        detalle += "por un monto de: " + deposito;
        return detalle;
    }
}
