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
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;

/**
 * <p>Controlador del buzón</p>
 *
 * @author Stiward Araya
 * @author Justin Mendez
 */
public class BuzonController extends Controller implements Initializable {

    RegistroCuenta registroCuenta;
    RegistroAsociadoCuenta registroAsociadoCuenta;
    RegistroSolicitudDeposito registroSolicitudDeposito;
    RegistroAsociado registroAsociado;

    @javafx.fxml.FXML
    private MFXComboBox mcbCuentas;
    @javafx.fxml.FXML
    private Spinner spnMil;
    @javafx.fxml.FXML
    private Spinner spnDiezmil;
    @javafx.fxml.FXML
    private Spinner spnVeintemil;
    @javafx.fxml.FXML
    private MFXTextField txtfFolio;
    @javafx.fxml.FXML
    private Spinner spnDiez;
    @javafx.fxml.FXML
    private Spinner spnDosmil;
    @javafx.fxml.FXML
    private Spinner spnCincomil;
    @javafx.fxml.FXML
    private Spinner spnCincuenta;
    @javafx.fxml.FXML
    private Spinner spnCinco;
    @javafx.fxml.FXML
    private Spinner spnVeinticinco;
    @javafx.fxml.FXML
    private Spinner spnQuinientos;
    @javafx.fxml.FXML
    private Spinner spnCien;
    @javafx.fxml.FXML
    private MFXTextField txtfSaldo;
    @javafx.fxml.FXML
    private MFXButton mbtnEnviar;
    @javafx.fxml.FXML
    private MFXButton mbtnVer;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    @Override
    public void initialize() {
        setSpnValueFactories();
        cargarRegistros();
        txtfSaldo.setEditable(false);
        mbtnEnviar.setDisable(true);
        mbtnVer.setDisable(true);
    }

    @javafx.fxml.FXML
    public void onActionBtnEnviar(ActionEvent actionEvent) {
        if (getTotal() > 0) {
            String nombreCuenta = (String) mcbCuentas.getSelectionModel().getSelectedItem();
            System.out.println("Este es el nombre de la cuenta a buscar: " + nombreCuenta);
            Integer idCuenta = registroCuenta.getIdCuentaByNombre(nombreCuenta);
            System.out.println("Este es el ID de la cuenta a buscar: " + idCuenta);
            System.out.println("Este es el folio del asociado a buscar: " + txtfFolio.getText());
            guardarDeposito(txtfFolio.getText().toUpperCase(), idCuenta);

        } else {
            new Mensaje().showModal(Alert.AlertType.ERROR, "Error de usuario", getStage(), "Debes depositar la plata en el chanchito");
        }
    }

    @javafx.fxml.FXML
    public void onActionBtnLimpiar(ActionEvent actionEvent) {
        limpiarCampos();
    }

    private void limpiarCampos() {
        mbtnEnviar.setDisable(true);
        txtfFolio.setText("");
        mcbCuentas.getItems().clear();
        txtfSaldo.setText("");
        setSpnValueFactories();
    }

    @javafx.fxml.FXML
    public void onActionBtnBuscar(ActionEvent actionEvent) {
        mcbCuentas.getItems().clear();
        if (txtfFolio.getText().isEmpty()) {
            new Mensaje().showModal(Alert.AlertType.ERROR, "Error de usuario", getStage(), "Debes ingresar un Folio");
        } else if (registroAsociado.buscarAsociado(txtfFolio.getText().toUpperCase()) == null) {
            new Mensaje().showModal(Alert.AlertType.ERROR, "Error de usuario", getStage(), "Usuario no encontrado");
        } else {
            txtfFolio.setText(txtfFolio.getText().toUpperCase());
            ArrayList<AsociadoCuenta> asociadosCuentas = registroAsociadoCuenta.getAsociadosCuentas();
            for (AsociadoCuenta asoCu : asociadosCuentas) {
                if (asoCu.getFolioAsociado().equals(txtfFolio.getText().toUpperCase())) {
                    mcbCuentas.getItems().add(registroCuenta.buscarCuenta(asoCu.getIdCuenta()).getNombre());
                }
            }
            new Mensaje().showModal(Alert.AlertType.CONFIRMATION, "Cuentas", getStage(), "Cuentas cargadas");
            mbtnVer.setDisable(false);
        }
    }

    @javafx.fxml.FXML
    public void onActionBtnVer(ActionEvent actionEvent) {
        Cuenta cuenta = registroCuenta.buscarCuentaNombre(mcbCuentas.getSelectedItem().toString());
        if (cuenta != null) {
            AsociadoCuenta asoCu = registroAsociadoCuenta.buscarAsociadoCuenta(txtfFolio.getText(), cuenta.getId());
            mcbCuentas.setText(cuenta.getNombre());
            txtfSaldo.setText(String.valueOf(asoCu.getBalanceCuenta()));
            mbtnEnviar.setDisable(false);
        } else {
            new Mensaje().showModal(Alert.AlertType.CONFIRMATION, "Cuentas", getStage(), "Debes preguntarle a un fucionario que te abra una cuenta");
        }

    }

    private void setSpnValueFactories() {
        SpinnerValueFactory<Integer> valueMil = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 10);
        SpinnerValueFactory<Integer> valueDosmil = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 10);
        SpinnerValueFactory<Integer> valueCincomil = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 10);
        SpinnerValueFactory<Integer> valueDiezmil = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 10);
        SpinnerValueFactory<Integer> valueVeintemil = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 10);
        SpinnerValueFactory<Integer> valueCinco = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 10);
        SpinnerValueFactory<Integer> valueDiez = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 10);
        SpinnerValueFactory<Integer> valueVeinticinco = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 10);
        SpinnerValueFactory<Integer> valueCincuenta = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 10);
        SpinnerValueFactory<Integer> valueCien = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 10);
        SpinnerValueFactory<Integer> valueQuinientos = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 10);

        valueMil.setValue(0);
        valueDosmil.setValue(0);
        valueCincomil.setValue(0);
        valueDiezmil.setValue(0);
        valueVeintemil.setValue(0);
        valueCinco.setValue(0);
        valueDiez.setValue(0);
        valueVeinticinco.setValue(0);
        valueCincuenta.setValue(0);
        valueCien.setValue(0);
        valueQuinientos.setValue(0);

        spnMil.setValueFactory(valueMil);
        spnDosmil.setValueFactory(valueDosmil);
        spnCincomil.setValueFactory(valueCincomil);
        spnDiezmil.setValueFactory(valueDiezmil);
        spnVeintemil.setValueFactory(valueVeintemil);
        spnCinco.setValueFactory(valueCinco);
        spnDiez.setValueFactory(valueDiez);
        spnVeinticinco.setValueFactory(valueVeinticinco);
        spnCincuenta.setValueFactory(valueCincuenta);
        spnCien.setValueFactory(valueCien);
        spnQuinientos.setValueFactory(valueQuinientos);
    }

    private Integer getTotal() {
        int total = 0;
        total += ( (Integer) spnMil.getValue() ) * 1000;
        total += ( (Integer) spnDosmil.getValue() ) * 2000;
        total += ( (Integer) spnCincomil.getValue() ) * 5000;
        total += ( (Integer) spnDiezmil.getValue() ) * 10000;
        total += ( (Integer) spnVeintemil.getValue() ) * 20000;
        total += ( (Integer) spnCinco.getValue() ) * 5;
        total += ( (Integer) spnDiez.getValue() ) * 10;
        total += ( (Integer) spnVeinticinco.getValue() ) * 25;
        total += ( (Integer) spnCincuenta.getValue() ) * 50;
        total += ( (Integer) spnCien.getValue() ) * 100;
        total += ( (Integer) spnQuinientos.getValue() ) * 500;
        return total;
    }

    private void cargarRegistros() {
        registroCuenta = ( (RegistroCuenta) AppContext.getInstance().get("cuentas") );
        registroAsociado = ( (RegistroAsociado) AppContext.getInstance().get("asociados") );
        registroAsociadoCuenta = ( (RegistroAsociadoCuenta) AppContext.getInstance().get("asociadosCuentas") );
        registroSolicitudDeposito = ( (RegistroSolicitudDeposito) AppContext.getInstance().get("depositos") );
        registroCuenta.cargarCuentas();
        registroAsociado.cargarAsociados();
        registroAsociadoCuenta.cargarAsociadoCuenta();
        registroSolicitudDeposito.cargarSolicitudes();
    }

    private void guardarDeposito(String folio, Integer idCuenta) {
        if (idCuenta > 0) {
            AsociadoCuenta asoCuenta = registroAsociadoCuenta.buscarAsociadoCuenta(txtfFolio.getText().toUpperCase(), idCuenta);
            if (asoCuenta != null) {
                SolicitudDeposito nuevoDeposito = new SolicitudDeposito(asoCuenta, getTotal());
                new Mensaje().showModal(Alert.AlertType.CONFIRMATION, "Chanchito", getStage(), registroSolicitudDeposito.agregarDeposito(nuevoDeposito));
                registroSolicitudDeposito.guardarSolicitudes();
                limpiarCampos();
            } else {
                new Mensaje().showModal(Alert.AlertType.ERROR, "Chanchito", getStage(), "AsociadoCuenta no encontrada");
            }
        }
    }

}
