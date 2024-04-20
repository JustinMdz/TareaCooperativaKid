package cr.ac.una.tareacooperativa.model;

import java.util.Random;

/**
 * <p>
 * Clase objeto para los niños asociados
 * </p>
 *
 * @author Stiward Araya
 * @author Justin Mendez
 */
public class Asociado {

    private String folioAsociado;
    private String nombre;
    private String primerApellido;
    private String segundoApellido;
    private String rutaFoto;
    private int edad;

    public Asociado() {
        folioAsociado = "";
        nombre = "";
        primerApellido = "";
        segundoApellido = "";
        rutaFoto = "";
        edad = 0;
    }

    public Asociado(String nNombre, String nPrimerApellido, String nSegundoApellido, String nRutaFoto, int nEdad) {
        nombre = capitalizeFirstCharacter(nNombre);
        primerApellido = capitalizeFirstCharacter(nPrimerApellido);
        segundoApellido = capitalizeFirstCharacter(nSegundoApellido);
        rutaFoto = nRutaFoto;
        edad = nEdad;
        folioAsociado = crearFolio();
    }

    private String capitalizeFirstCharacter(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }

        return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
    }

    // Setters
    public void setFolioAsociado(String nFolioAsociado) {
        folioAsociado = nFolioAsociado;
    }

    public void setNombre(String nNombre) {
        nombre = capitalizeFirstCharacter(nNombre);
    }

    public void setPrimerApellido(String nPrimerApellido) {
        primerApellido = capitalizeFirstCharacter(nPrimerApellido);
    }

    public void setSegundoApellido(String nSegundoApellido) {
        segundoApellido = capitalizeFirstCharacter(nSegundoApellido);
    }

    public void setRutaFoto(String nRutaFoto) {
        rutaFoto = nRutaFoto;
    }

    public void setEdad(int nEdad) {
        edad = nEdad;
    }

    // Getters
    public String getFolio() {
        return folioAsociado;
    }

    public String getNombre() {
        return nombre;
    }

    public String getPrimerApellido() {
        return primerApellido;
    }

    public String getSegundoApellido() {
        return segundoApellido;
    }

    public String getRutaFoto() {
        return rutaFoto;
    }

    public int getEdad() {
        return edad;
    }

    /**
     * <p>
     * Usa las iniciales del asociado y un numero <br>
     * generado del 0 al 999 para construir el folio <br>
     * y retornarlo.
     * </p>
     *
     * @return Folio creado con las iniciales del asociado
     */
    public String crearFolio() {
        String folio = String.valueOf(nombre.charAt(0)) + primerApellido.charAt(0) + segundoApellido.charAt(0);
        folio = folio.toUpperCase();
        Random rand = new Random();
        int numeroAleatorio = rand.nextInt(1000);
        String snumeroAleatorio = String.valueOf(numeroAleatorio);
        folio += snumeroAleatorio;

        return folio;
    }

}
