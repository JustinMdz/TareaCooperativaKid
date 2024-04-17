package cr.ac.una.tareacooperativa.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

/**
 * Universidad Nacional - Programación II 2024
 * Controlador de la pantalla de retiro de dinero
 * @author Stiward Araya 
 * @author Justin Mendez
 */
public class RetiroDineroController implements Initializable {

    @FXML
    private TextField txtCantidadRetiro;

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {}    

    @FXML
    private void volver(ActionEvent event) {
        //TODO volver a la ventana anterior
    }

    
}
