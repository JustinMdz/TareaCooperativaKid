package cr.ac.una.tareacooperativa.model;

public class SolicitudDeposito {
    private AsociadoCuenta asociadoCuenta;
    private Integer deposito;

    public SolicitudDeposito(){
        asociadoCuenta = null;
        deposito = 0;
    }

    public SolicitudDeposito(AsociadoCuenta asociadoCuenta, Integer deposito){
        this.asociadoCuenta = asociadoCuenta;
        this.deposito = deposito;
    }

    public void setAsociadoCuenta(AsociadoCuenta asociadoCuenta){
        this.asociadoCuenta = asociadoCuenta;
    }

    public void setDeposito(Integer deposito){
        this.deposito = deposito;
    }

    public AsociadoCuenta getAsociadoCuenta(){
        return asociadoCuenta;
    }

    public int getDeposito(){
        return deposito;
    }

    public String getDataDeposito(){
        String detalle = "";
        detalle += "Asociado folio: "+asociadoCuenta.getFolioAsociado()+"\n";
        detalle += "solicita un deposito en su cuenta identificada: "+asociadoCuenta.getIdCuenta()+"\n";
        detalle += "por un monto de: "+deposito;
        return detalle;
    }
}
