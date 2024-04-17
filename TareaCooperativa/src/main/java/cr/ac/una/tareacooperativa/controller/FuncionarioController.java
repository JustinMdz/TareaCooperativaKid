package cr.ac.una.tareacooperativa.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import cr.ac.una.tareacooperativa.util.FlowController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;


/**
 * <p>
 *     Controlador de la pantalla principal de funcionarios<br>
 *     Contiene los metodos para mostrar las ventanas de funcionarios
 * </p>
 * @author Stiward Araya
 * @author Justin Mendez
 */
public class FuncionarioController extends Controller implements Initializable {

    @FXML
    private BorderPane root;

    @Override
    public void initialize(URL url, ResourceBundle rb) { }

    @Override
    public void initialize() {}

    @FXML
    public void onActionAsociadosButton(ActionEvent actionEvent) {
        FlowController.getInstance().goView("MantenimientoAsociadosView");
    }

    @FXML
    public void onActionCarnetButton(ActionEvent actionEvent) {
        FlowController.getInstance().goView("ImpresionCarnetView");
    }

    @FXML
    public void onActionCuentasButton(ActionEvent actionEvent) {
        FlowController.getInstance().goView("CuentasView");
    }

    @FXML
    public void onActionTransaccionesButton(ActionEvent actionEvent) {
        FlowController.getInstance().goView("TransaccionesView");
    }

    @FXML
    private void onActionSalirButton(ActionEvent event) throws IOException {
        FlowController.getInstance().salir();
    }

}