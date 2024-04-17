package cr.ac.una.tareacooperativa.controller;

import cr.ac.una.tareacooperativa.util.FlowController;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;

/**
 * Universidad Nacional - Programación II 2024
 * <p>
 *     Controlador de la pantalla de profesores
 * </p>
 * @author Stiward Araya
 * @author Justin Mendez
 */
public class ProfesorController extends Controller implements Initializable {

    @FXML
    private BorderPane root;
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {}

    @Override
    public void initialize() {}

    @FXML
    private void onActionBtnCuentas(ActionEvent event) {
       FlowController.getInstance().goView("MantenimientoCuentasView");
    }

    @FXML
    private void onActionBtnCooperativa(ActionEvent event){
        FlowController.getInstance().goView("MantenimientoCopeeView");
    }

}
