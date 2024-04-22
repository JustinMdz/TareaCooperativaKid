package cr.ac.una.tareacooperativa.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import cr.ac.una.tareacooperativa.model.*;
import cr.ac.una.tareacooperativa.util.AppContext;
import cr.ac.una.tareacooperativa.util.Mensaje;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;

/**
 * <p>
 * Controlador de la pantalla de estados de cuenta
 * </p>
 *
 * @author Stiward Araya
 * @author Justin Mendez
 */
public class AsociadoCuentasController extends Controller implements Initializable {

    RegistroAsociado registroAsociado;
    RegistroAsociadoCuenta registroAsociadoCuenta;
    RegistroCuenta registroCuenta;

    @javafx.fxml.FXML
    private MFXComboBox mcbTipoCuenta;
    @javafx.fxml.FXML
    private MFXTextField txtfFolio;
    @javafx.fxml.FXML
    private MFXTextField txtfSaldo;
    @javafx.fxml.FXML
    private MFXTextField txtfTipoCuenta;
    @javafx.fxml.FXML
    private MFXButton mbtnVer;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    @Override
    public void initialize() {
        txtfSaldo.setEditable(false);
        txtfTipoCuenta.setEditable(false);
        mbtnVer.setDisable(true);
    }

    @javafx.fxml.FXML
    public void onActionBtnBuscar(ActionEvent actionEvent) {
        txtfFolio.setText(txtfFolio.getText().toUpperCase());
        cargarRegistros();

        if (txtfFolio.getText().isEmpty())
        {
            new Mensaje().showModal(Alert.AlertType.ERROR, "Error de usuario", getStage(), "Debes ingresar un Folio");
        } else if (registroAsociado.buscarAsociado(txtfFolio.getText()) == null)
        {
            new Mensaje().showModal(Alert.AlertType.ERROR, "Error de usuario", getStage(), "Usuario no encontrado");
        } else
        {
            ArrayList<AsociadoCuenta> asociadosCuentas = registroAsociadoCuenta.getAsociadosCuentas();
            for (AsociadoCuenta asoCu : asociadosCuentas)
            {
                if (asoCu.getFolioAsociado().equals(txtfFolio.getText()))
                {
                    mcbTipoCuenta.getItems().add(registroCuenta.buscarCuenta(asoCu.getIdCuenta()).getNombre());
                }
            }
            new Mensaje().showModal(Alert.AlertType.CONFIRMATION, "Cuentas", getStage(), "Cuentas cargadas");
            mbtnVer.setDisable(false);
        }
    }

    @javafx.fxml.FXML
    public void onActionBtnVer(ActionEvent actionEvent) {
        Cuenta cuenta = registroCuenta.buscarCuentaNombre(mcbTipoCuenta.getSelectedItem().toString());
        AsociadoCuenta asoCu = registroAsociadoCuenta.buscarAsociadoCuenta(txtfFolio.getText(), cuenta.getId());
        txtfTipoCuenta.setText(cuenta.getNombre());
        txtfSaldo.setText(String.valueOf(asoCu.getBalanceCuenta()));
    }

    @javafx.fxml.FXML
    public void onActionBtnLimpiar(ActionEvent actionEvent) {
        txtfFolio.setText("");
        txtfSaldo.setText("");
        txtfTipoCuenta.setText("");
        mcbTipoCuenta.getItems().clear();
    }

    private void cargarRegistros() {
        registroAsociado = ((RegistroAsociado) AppContext.getInstance().get("asociados"));
        registroAsociadoCuenta = ((RegistroAsociadoCuenta) AppContext.getInstance().get("asociadosCuentas"));
        registroCuenta = ((RegistroCuenta) AppContext.getInstance().get("cuentas"));
        registroAsociado.cargarAsociados();
        registroAsociadoCuenta.cargarAsociadoCuenta();
        registroCuenta.cargarCuentas();
    }
}
