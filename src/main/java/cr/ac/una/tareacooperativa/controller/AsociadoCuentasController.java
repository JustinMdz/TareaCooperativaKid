package cr.ac.una.tareacooperativa.controller;

import java.net.URL;
import java.util.ResourceBundle;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;

/**
 * <p>
 * Controlador de la pantalla de estados de cuenta
 * </p>
 *
 * @author Stiward Araya
 * @author Justin Mendez
 */
public class AsociadoCuentasController extends Controller implements Initializable {

    @javafx.fxml.FXML
    private MFXComboBox mcbTipoCuenta;
    @javafx.fxml.FXML
    private MFXTextField txtfFolio;
    @javafx.fxml.FXML
    private MFXTextField txtfSaldo;
    @javafx.fxml.FXML
    private MFXTextField txtfTipoCuenta;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    @Override
    public void initialize() {
    }

    @javafx.fxml.FXML
    public void onActionBtnBuscar(ActionEvent actionEvent) {
        //TODO registroAsociado.buscarAsociado((txtfFolio.getText()).toUpperCase());->
    }

    @javafx.fxml.FXML
    public void onActionBtnLimpiar(ActionEvent actionEvent) {
        txtfFolio.setText("");
        txtfSaldo.setText("");
        txtfTipoCuenta.setText("");
    }
}
