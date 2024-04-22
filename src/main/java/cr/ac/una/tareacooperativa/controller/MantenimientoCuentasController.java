package cr.ac.una.tareacooperativa.controller;

import cr.ac.una.tareacooperativa.model.Cuenta;
import cr.ac.una.tareacooperativa.model.RegistroAsociadoCuenta;
import cr.ac.una.tareacooperativa.model.RegistroCuenta;
import cr.ac.una.tareacooperativa.util.AppContext;
import cr.ac.una.tareacooperativa.util.Mensaje;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXListView;
import io.github.palexdev.materialfx.controls.MFXTextField;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.BorderPane;

/**
 * <p>
 * Controlador del mantenimiento de cuentas
 * </p>
 *
 * @author Stiward Araya
 * @author Justin Mendez
 */
public class MantenimientoCuentasController extends Controller implements Initializable {

    private RegistroCuenta registroCuenta;
    private RegistroAsociadoCuenta registroAsociadoCuenta;
    private boolean isEditButtonPressed;
    private boolean isCreateButtonPressed;
    private boolean isDeleteAccountButtonPressed;
    private boolean isEditAccountButtonPressed;

    @FXML
    private MFXButton btnEditar;
    @FXML
    private MFXButton btnCrear;

    @FXML
    private MFXButton btnEliminarCuenta;
    @FXML
    private MFXButton btnCambiarNombre;
    @FXML
    private MFXTextField txtfEditarNombreTipoCuenta;
    @FXML
    private MFXTextField txtfNuevoNombreTipoCuenta;
    @FXML
    private MFXButton btnGuardar;
    @FXML
    private MFXListView<String> listView;
    @FXML
    private MFXTextField txtfIdCuenta;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    @Override
    public void initialize() {
        disableAll();
        limpiarCampos();
        registroCuenta = ((RegistroCuenta) AppContext.getInstance().get("cuentas"));
        registroCuenta.cargarCuentas();
        listView.getItems().clear();
        this.listView.getItems().addAll(registroCuenta.toStringCuentasAndId());
    }

    @FXML
    private void onActionbtnEditar(ActionEvent event) {
        listView.setDisable(false);
        txtfIdCuenta.setDisable(false);
        txtfEditarNombreTipoCuenta.setDisable(false);
        btnEliminarCuenta.setDisable(false);
        btnCambiarNombre.setDisable(false);
        txtfNuevoNombreTipoCuenta.setDisable(true);
        this.txtfIdCuenta.clear();
        this.isCreateButtonPressed = false;
        this.isEditButtonPressed = true;

    }

    @FXML
    private void onActionbtnCrear(ActionEvent event) {
        disableAll();
        txtfNuevoNombreTipoCuenta.setDisable(false);
        this.isCreateButtonPressed = true;
        this.isEditButtonPressed = false;
    }

    private void eliminarCuenta() {
        registroAsociadoCuenta = ((RegistroAsociadoCuenta) AppContext.getInstance().get("asociadosCuentas"));
        registroAsociadoCuenta.cargarAsociadoCuenta();
        int idCuenta = Integer.parseInt(txtfIdCuenta.getText());
        if (!registroAsociadoCuenta.isAccountLinkedToAsociado(idCuenta))
        {
            String aux = registroCuenta.eliminarCuenta(idCuenta);
            registroCuenta.guardarCuentas();
            new Mensaje().showModal(Alert.AlertType.CONFIRMATION, "Mantenimiento", getStage(), aux);
            this.listView.getItems().clear();
            this.listView.getItems().addAll(registroCuenta.toStringCuentasAndId());
            this.isDeleteAccountButtonPressed = true;
        } else
        {
            new Mensaje().showModal(Alert.AlertType.ERROR, "Mantenimiento", getStage(), "No se ha podido elimnar la cuenta correctamente, esta cuenta esta vinculada a un o unos asociados.");
        }

    }

    @FXML
    private void onActionBtnEliminarCuenta(ActionEvent event) {
        if (!txtfIdCuenta.getText().isEmpty())
        {
            try
            {
                eliminarCuenta();
            } catch (NumberFormatException e)
            {
                new Mensaje().showModal(Alert.AlertType.ERROR, "Error", getStage(), "Ingrese un número válido para el ID de la cuenta.");
            }
        } else
        {
            new Mensaje().showModal(Alert.AlertType.ERROR, "Error", getStage(), "Debes ingresar un ID de cuenta para poder eliminarla");
        }
    }

    private void limpiarCampos() {
        txtfEditarNombreTipoCuenta.clear();
        txtfIdCuenta.clear();
        txtfNuevoNombreTipoCuenta.clear();
    }

    @FXML
    private void onActionBtnCambiarNombre(ActionEvent event) {
        if (txtfIdCuenta.getText().isEmpty())
        {
            new Mensaje().showModal(Alert.AlertType.ERROR, "Error", getStage(), "Ingrese un ID de cuenta valido");
            return;
        }

        if (txtfEditarNombreTipoCuenta.getText().isEmpty())
        {
            new Mensaje().showModal(Alert.AlertType.ERROR, "Error", getStage(), "Ingrese un nombre de cuenta para su modificación");
            return;
        }

        try
        {
            int idCuenta = Integer.parseInt(txtfIdCuenta.getText());
            String tipoCuenta = txtfEditarNombreTipoCuenta.getText();
            Cuenta nuevaCuenta = new Cuenta(idCuenta, tipoCuenta);
            new Mensaje().showModal(Alert.AlertType.CONFIRMATION, "Mantenimiento Cuentas", getStage(), registroCuenta.modificarCuenta(nuevaCuenta));
            this.isEditAccountButtonPressed = true;
            this.listView.getItems().clear();
            this.listView.getItems().addAll(registroCuenta.toStringCuentasAndId());
        } catch (NumberFormatException e)
        {
            new Mensaje().showModal(Alert.AlertType.ERROR, "Error", getStage(), "Ingrese un número válido para el ID de la cuenta.");
        }
    }

    @FXML
    private void onActionBtnGuardar(ActionEvent event) {
        if (isCreateButtonPressed)
        {
            almacenarCuenta();
            txtfNuevoNombreTipoCuenta.clear();

        } else if (isEditButtonPressed)
        {
            checkParteEditarCuenta();
            txtfIdCuenta.clear();
            txtfEditarNombreTipoCuenta.clear();
        } else
        {
            new Mensaje().showModal(Alert.AlertType.ERROR, "Error de usuario", getStage(), "Debes editar o crear una cuenta");
        }
        disableAll();
        limpiarCampos();
    }

    private void disableAll() {
        listView.setDisable(true);
        btnEliminarCuenta.setDisable(true);
        btnCambiarNombre.setDisable(true);
        txtfEditarNombreTipoCuenta.setDisable(true);
        txtfNuevoNombreTipoCuenta.setDisable(true);
        txtfIdCuenta.setDisable(true);
    }

    private void almacenarCuenta() {
        if (checkParteCrearCuenta())
        {
            Cuenta nuevaCuenta = new Cuenta();
            nuevaCuenta.setNombre(txtfNuevoNombreTipoCuenta.getText());
            nuevaCuenta.setId(registroCuenta.getProximoIdCuenta());
            new Mensaje().showModal(Alert.AlertType.CONFIRMATION, "Cuenta agregada", getStage(), registroCuenta.agregarCuenta(nuevaCuenta));
            registroCuenta.guardarCuentas();
            this.listView.getItems().clear();
            this.listView.getItems().addAll(registroCuenta.toStringCuentasAndId());
        }
    }

    private void checkParteEditarCuenta() {
        if (listView == null)
        {
            new Mensaje().showModal(Alert.AlertType.ERROR, "Error de usuario", getStage(), "No existen cuentas aun");

        } else if (txtfIdCuenta.getText().isEmpty())
        {
            new Mensaje().showModal(Alert.AlertType.ERROR, "Error de usuario", getStage(), "Debes eliminar una cuenta o modificarla");

        } else if (isDeleteAccountButtonPressed || isEditAccountButtonPressed)
        {
            registroCuenta.guardarCuentas();
            new Mensaje().showModal(Alert.AlertType.CONFIRMATION, "Mantenimiento Cuentas", getStage(), "Cambios guardados exitosamente");
            this.isDeleteAccountButtonPressed = false;
            this.isEditAccountButtonPressed = false;
        }

    }

    private boolean checkParteCrearCuenta() {
        if (txtfNuevoNombreTipoCuenta.getText().isEmpty())
        {
            new Mensaje().showModal(Alert.AlertType.ERROR, "Error de usuario", getStage(), "Debes nombrar una cuenta");
            return false;
        } else if (registroCuenta.isCuentaIntheList(txtfNuevoNombreTipoCuenta.getText()))
        {
            new Mensaje().showModal(Alert.AlertType.ERROR, "Error de usuario", getStage(), "Ya existe una cuenta con ese nombre");
            return false;
        }

        return true;
    }
}
