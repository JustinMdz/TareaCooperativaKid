package cr.ac.una.tareacooperativa.controller;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.Flow;

import cr.ac.una.tareacooperativa.util.FlowController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

/**
 * Universidad Nacional de Costa Rica - Programación II 2024
 * <p>
 * Controlador de la pantalla principal de asociados<br>
 * Contiene los metodos para mostrar las ventanas de asociados.
 * </p>
 *
 * @author Stiward Araya
 * @author Justin Mendez
 */
public class AsociadoController extends Controller implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle rb) {}

    @Override
    public void initialize() {}

    @FXML
    public void onActionBtnRegistro(ActionEvent actionEvent) {
        FlowController.getInstance().goView("RegistroAsociadoView");
    }

    @FXML
    private void onActionBtnEstadoCuenta(ActionEvent event) {
        FlowController.getInstance().goView("AsociadoCuentasView");
    }

    @FXML
    private void onActionBtnBuzon(ActionEvent event) {
        FlowController.getInstance().goView("BuzonView");
    }

    @FXML
    public void onActionBtnSalir(ActionEvent actionEvent) {
        FlowController.getInstance().salir();
    }

    @FXML
    public void onActionLinkAcercaDe(ActionEvent actionEvent) throws IOException {
        Desktop desktop = Desktop.getDesktop();
        File file = new File("../web/index.html");
        desktop.open(file);
    }
}