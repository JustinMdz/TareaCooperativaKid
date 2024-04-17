package cr.ac.una.tareacooperativa.model;

/**
 * <p>
 * Clase objeto para cuenta
 * </p>
 *
 * @author Stiward Araya
 * @author Justin Mendez
 */
public class Cuenta {

    private int id;
    private String nombreTipoCuenta;

    public Cuenta() {
        id = 0;
        nombreTipoCuenta = "";
    }

    public Cuenta(int nId, String nTipoCuenta) {
        id = nId;
        nombreTipoCuenta = nTipoCuenta;
    }

    //setters
    public void setId(int nId) {
        id = nId;
    }

    public void setNombre(String nNombreTipoCuenta) {
        nombreTipoCuenta = nNombreTipoCuenta;
    }

    //getters
    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombreTipoCuenta;
    }

}
