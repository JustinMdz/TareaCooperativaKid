package cr.ac.una.tareacooperativa.controller;

import cr.ac.una.tareacooperativa.model.Asociado;
import cr.ac.una.tareacooperativa.model.AsociadoCuenta;
import cr.ac.una.tareacooperativa.model.Cuenta;
import java.net.URL;
import java.util.ResourceBundle;

import cr.ac.una.tareacooperativa.model.RegistroAsociado;
import cr.ac.una.tareacooperativa.model.RegistroSolicitudDeposito;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import cr.ac.una.tareacooperativa.model.RegistroAsociadoCuenta;
import cr.ac.una.tareacooperativa.model.RegistroCuenta;
import cr.ac.una.tareacooperativa.model.SolicitudDeposito;
import cr.ac.una.tareacooperativa.util.AppContext;
import cr.ac.una.tareacooperativa.util.Mensaje;
import java.util.ArrayList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;

import javax.swing.*;

/**
 * Universidad Nacional - Programación II 2024
 * <p>
 * Controlador de la pantalla de transacciones
 * </p>
 *
 * @author Stiward Araya
 * @author Justin Mendez
 */
public class TransaccionesController extends Controller implements Initializable {

    private RegistroAsociadoCuenta registroAsociadoCuenta;
    private RegistroAsociado registroAsociado;
    private RegistroSolicitudDeposito registroSolicitudDeposito;
    private RegistroCuenta registroCuenta;

    @javafx.fxml.FXML
    private MFXTextField txtfFolio;
    @javafx.fxml.FXML
    private MFXComboBox mcbCuentas;
    @javafx.fxml.FXML
    private MFXTextField txtfSaldo;
    @javafx.fxml.FXML
    private Spinner spnDosmil;
    @javafx.fxml.FXML
    private Spinner spnCincomil;
    @javafx.fxml.FXML
    private Spinner spnMil;
    @javafx.fxml.FXML
    private Spinner spnDiezmil;
    @javafx.fxml.FXML
    private Spinner spnVeintemil;
    @javafx.fxml.FXML
    private Spinner spnDiez;
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
    @FXML
    private MFXComboBox mcbDepositos;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        mcbCuentas.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) ->
        {
            if (mcbCuentas.getSelectedItem() != null)
            {
                cargarSolicitudDepositos();
            }
        });

        mcbDepositos.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) ->
        {
            if (mcbDepositos.getSelectedItem() != null)
            {
                procesarMonto((Integer) mcbDepositos.getSelectedItem());
            }
        });
    }

    @Override
    public void initialize() {
        setSpnValueFactories();
    }

    @javafx.fxml.FXML
    public void onActionBtnRetirar(ActionEvent actionEvent) {
        if (getTotal() > 0)
        {
            if (txtfFolio.getText().isEmpty())
            {
                new Mensaje().showModal(Alert.AlertType.ERROR, "Error Funcionario", getStage(), "Debes escribir un folio");
            } else if (mcbCuentas.getSelectedItem() == null)
            {
                new Mensaje().showModal(Alert.AlertType.ERROR, "Error Funcionario", getStage(), "Debes seleccionar una cuenta");
            } else if (mcbDepositos.getSelectedItem() == null)
            {
                new Mensaje().showModal(Alert.AlertType.ERROR, "Error Funcionario", getStage(), "Debes seleccionar un montoDeposito");
            } else
            {
                Integer monto = getTotal();

                registroAsociadoCuenta.retirarDinero(txtfFolio.getText().toUpperCase(), registroCuenta.getIdCuentaByNombre((String) mcbCuentas.getSelectedItem()), monto);
                if (monto == null)
                {
                    new Mensaje().showModal(Alert.AlertType.ERROR, "Error Funcionario", getStage(), "No hay suficientes fondos en esta cuenta para realizar un retiro");
                } else
                {
                    SolicitudDeposito deposito = registroSolicitudDeposito.buscarSolicitudDepositoPorMonto(txtfFolio.getText(), registroCuenta.getIdCuentaByNombre((String) mcbCuentas.getSelectedItem()), (Integer) mcbDepositos.getSelectedItem());

                    if (deposito != null)
                    {
                        registroSolicitudDeposito.eliminarSolicitud(deposito);
                        registroSolicitudDeposito.guardarSolicitudes();
                        registroAsociadoCuenta.guardarAsociadoCuenta();
                    }

                    JOptionPane.showMessageDialog(null, getTotal());
                    new Mensaje().showModal(Alert.AlertType.CONFIRMATION, "Funcionario", getStage(), "Retiro realizado con exito");
                }

            }
        } else
        {
            new Mensaje().showModal(Alert.AlertType.ERROR, "Error Funcionario", getStage(), "Debes depositar un monto");
        }
        mcbDepositos.getItems().clear();
    }

    @javafx.fxml.FXML
    public void onActionBtnDepositar(ActionEvent actionEvent) {
        if (getTotal() > 0)
        {
            if (txtfFolio.getText().isEmpty())
            {
                new Mensaje().showModal(Alert.AlertType.ERROR, "Error Funcionario", getStage(), "Debes escribir un folio");
            } else if (mcbCuentas.getSelectedItem() == null)
            {
                new Mensaje().showModal(Alert.AlertType.ERROR, "Error Funcionario", getStage(), "Debes seleccionar una cuenta");
            } else if (mcbDepositos.getSelectedItem() == null)
            {
                new Mensaje().showModal(Alert.AlertType.ERROR, "Error Funcionario", getStage(), "Debes seleccionar un montoDeposito");
            } else
            {

                registroAsociadoCuenta.depositarDinero(txtfFolio.getText().toUpperCase(), registroCuenta.getIdCuentaByNombre((String) mcbCuentas.getSelectedItem()), getTotal());
                registroAsociadoCuenta.guardarAsociadoCuenta();

                SolicitudDeposito deposito = registroSolicitudDeposito.buscarSolicitudDepositoPorMonto(txtfFolio.getText(), registroCuenta.getIdCuentaByNombre((String) mcbCuentas.getSelectedItem()), (Integer) mcbDepositos.getSelectedItem());

                if (deposito != null)
                {
                    registroSolicitudDeposito.eliminarSolicitud(deposito);
                    registroSolicitudDeposito.guardarSolicitudes();
                    mcbDepositos.getItems().clear();
                }

                JOptionPane.showMessageDialog(null, getTotal());
                new Mensaje().showModal(Alert.AlertType.CONFIRMATION, "Funcionario", getStage(), "Deposito realizado con exito");
            }
        } else
        {
            new Mensaje().showModal(Alert.AlertType.ERROR, "Error Funcionario", getStage(), "Debes depositar un monto");
        }
        mcbDepositos.getItems().clear();
    }

    @javafx.fxml.FXML
    public void onActionBtnBuscar(ActionEvent actionEvent) {
        cargarRegistros();
        if (txtfFolio.getText().isEmpty())
        {
            new Mensaje().showModal(Alert.AlertType.ERROR, "Error de usuario", getStage(), "Debes ingresar un Folio");
        } else if (registroAsociado.buscarAsociado(txtfFolio.getText().toUpperCase()) == null)
        {
            new Mensaje().showModal(Alert.AlertType.ERROR, "Error de usuario", getStage(), "Usuario no encontrado");
        } else
        {
            txtfFolio.setText(txtfFolio.getText().toUpperCase());
            ArrayList<AsociadoCuenta> asociadosCuentas = registroAsociadoCuenta.getAsociadosCuentas();
            for (AsociadoCuenta asoCu : asociadosCuentas)
            {
                if (asoCu.getFolioAsociado().equals(txtfFolio.getText().toUpperCase()))
                {
                    mcbCuentas.getItems().add(registroCuenta.buscarCuenta(asoCu.getIdCuenta()).getNombre());
                }
            }
            new Mensaje().showModal(Alert.AlertType.CONFIRMATION, "Cuentas", getStage(), "Cuentas cargadas");
        }

    }

    private void cargarSolicitudDepositos() {
        mcbDepositos.getItems().clear();
        ArrayList<SolicitudDeposito> solicitudDepositos = registroSolicitudDeposito.getDepositos();

        Cuenta cuenta = registroCuenta.buscarCuentaNombre(mcbCuentas.getSelectedItem().toString());

        for (SolicitudDeposito solicitud : solicitudDepositos)
        {
            if (cuenta != null)
            {
                mcbDepositos.getItems().add(registroSolicitudDeposito.buscarSolicitudDepositoMonto(txtfFolio.getText().toUpperCase(), cuenta.getId()));
            }
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
        total += ((Integer) spnMil.getValue()) * 1000;
        total += ((Integer) spnDosmil.getValue()) * 2000;
        total += ((Integer) spnCincomil.getValue()) * 5000;
        total += ((Integer) spnDiezmil.getValue()) * 10000;
        total += ((Integer) spnVeintemil.getValue()) * 20000;
        total += ((Integer) spnCinco.getValue()) * 5;
        total += ((Integer) spnDiez.getValue()) * 10;
        total += ((Integer) spnVeinticinco.getValue()) * 25;
        total += ((Integer) spnCincuenta.getValue()) * 50;
        total += ((Integer) spnCien.getValue()) * 100;
        total += ((Integer) spnQuinientos.getValue()) * 500;
        return total;
    }

    private void procesarMonto(Integer monto) {
        if ((monto / 20000) != 0)
        {
            spnVeintemil.getValueFactory().setValue(monto / 20000);
            monto -= 20000 * (monto / 20000);
        }
        if ((monto / 10000) != 0)
        {
            spnDiezmil.getValueFactory().setValue(monto / 10000);
            monto -= 10000 * (monto / 10000);
        }
        if ((monto / 5000) != 0)
        {
            spnCincomil.getValueFactory().setValue(monto / 5000);
            monto -= 5000 * (monto / 5000);
        }
        if ((monto / 2000) != 0)
        {
            spnDosmil.getValueFactory().setValue(monto / 2000);
            monto -= 2000 * (monto / 2000);
        }
        if ((monto / 1000) != 0)
        {
            spnMil.getValueFactory().setValue(monto / 1000);
            monto -= 1000 * (monto / 1000);
        }
        if ((monto / 500) != 0)
        {
            spnQuinientos.getValueFactory().setValue(monto / 500);
            monto -= 500 * (monto / 500);
        }
        if ((monto / 100) != 0)
        {
            spnCien.getValueFactory().setValue(monto / 100);
            monto -= 100 * (monto / 100);
        }
        if ((monto / 50) != 0)
        {
            spnCincuenta.getValueFactory().setValue(monto / 50);
            monto -= 50 * (monto / 50);
        }
        if ((monto / 25) != 0)
        {
            spnVeinticinco.getValueFactory().setValue(monto / 25);
            monto -= 25 * (monto / 25);
        }
        if ((monto / 10) != 0)
        {
            spnDiez.getValueFactory().setValue(monto / 10);
            monto -= 10 * (monto / 10);
        }
        if ((monto / 2000) != 0)
        {
            spnCinco.getValueFactory().setValue(monto / 5);
            monto -= 5 * (monto / 5);
        }
    }

    private void cargarRegistros() {
        limpiarCombobox();
        registroCuenta = ((RegistroCuenta) AppContext.getInstance().get("cuentas"));
        registroAsociado = ((RegistroAsociado) AppContext.getInstance().get("asociados"));
        registroAsociadoCuenta = ((RegistroAsociadoCuenta) AppContext.getInstance().get("asociadosCuentas"));
        registroSolicitudDeposito = ((RegistroSolicitudDeposito) AppContext.getInstance().get("depositos"));
        registroCuenta.cargarCuentas();
        registroAsociado.cargarAsociados();
        registroAsociadoCuenta.cargarAsociadoCuenta();
        registroSolicitudDeposito.cargarSolicitudes();
    }

    private void limpiarCombobox() {
        mcbDepositos.getItems().clear();
        mcbCuentas.getItems().clear();
    }
}
