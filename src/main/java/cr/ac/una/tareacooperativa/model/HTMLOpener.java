/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.tareacooperativa.model;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

/**
 *
 * @author justi
 */
public class HTMLOpener {

    public HTMLOpener() {

    }

    public void abrirArchivoHTML(String nameRuta) {
        if (Desktop.isDesktopSupported())
        {
            Desktop desktop = Desktop.getDesktop();
            File archivoHTML = new File(nameRuta);
            try
            {
                if (archivoHTML.exists())
                {
                    desktop.open(archivoHTML);
                } else
                {
                    System.out.println("El archivo HTML no existe en la ruta especificada.");
                }
            } catch (IOException e)
            {
                System.out.println("Se produjo un error al intentar abrir el archivo HTML: " + e.getMessage());
            }
        } else
        {
            System.out.println("El sistema no soporta la clase Desktop.");
        }
    }
}
