package cr.ac.una.tareacooperativa.controller;

import java.net.URL;
import java.util.ResourceBundle;
import cr.ac.una.tareacooperativa.util.FlowController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

/**
 * <p>
 *     Controlador de la pantalla principal de asociados<br>
 *     Contiene los metodos para mostrar las ventanas de asociados.
 * </p>
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
        FlowController.getInstance().goView("TransaccionesView");
    }

    @FXML
    public void onActionBtnSalir(ActionEvent actionEvent) {
        FlowController.getInstance().salir();
    }
}