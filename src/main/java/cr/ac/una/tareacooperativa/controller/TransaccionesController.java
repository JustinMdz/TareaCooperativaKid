package cr.ac.una.tareacooperativa.controller;

import com.itextpdf.kernel.pdf.PdfName;
import cr.ac.una.tareacooperativa.model.Asociado;
import cr.ac.una.tareacooperativa.model.AsociadoCuenta;
import cr.ac.una.tareacooperativa.model.Cuenta;
import cr.ac.una.tareacooperativa.model.Movimiento;

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
import cr.ac.una.tareacooperativa.model.RegistroMovimiento;
import cr.ac.una.tareacooperativa.model.SolicitudDeposito;
import cr.ac.una.tareacooperativa.util.AppContext;
import cr.ac.una.tareacooperativa.util.Mensaje;

import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;

import javax.swing.*;

/**
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
    private RegistroMovimiento registroMovimiento;

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
                retirarDineroAsociado();
            } else
            {
                new Mensaje().showModal(Alert.AlertType.ERROR, "Error Funcionario", getStage(), "Solo los depositos necesitan solitud");
                cargarSolicitudDepositos();
            }
        } else
        {
            new Mensaje().showModal(Alert.AlertType.ERROR, "Error Funcionario", getStage(), "Debes seleccionar un monto para su retiro");
        }
        mcbDepositos.getItems().clear();
        setSpnValueFactories();
    }

    private void retirarDineroAsociado() {
        Integer monto = getTotal();
        String folioAso = txtfFolio.getText().toUpperCase();
        Integer cuentaId = registroCuenta.getIdCuentaByNombre((String) mcbCuentas.getSelectedItem());

        String mensaje = registroAsociadoCuenta.retirarDinero(folioAso, cuentaId, monto);
        Movimiento movimiento = new Movimiento(folioAso, cuentaId, monto);
        registroMovimiento.agregarMovimientoRetiro(movimiento);
        registroMovimiento.guardarMovimientos();
        registroAsociadoCuenta.guardarAsociadoCuenta();
        new Mensaje().showModal(Alert.AlertType.INFORMATION, " Funcionario", getStage(), mensaje);
    }

    private void depositoSinSoliciutd() {
        Integer monto = getTotal();
        String folioAso = txtfFolio.getText().toUpperCase();
        Integer cuentaId = registroCuenta.getIdCuentaByNombre((String) mcbCuentas.getSelectedItem());

        registroAsociadoCuenta.depositarDinero(folioAso, cuentaId, monto);
        Movimiento movimiento = new Movimiento(folioAso, cuentaId, monto);

        registroMovimiento.agregarMovimientoDeposito(movimiento);
        registroAsociadoCuenta.guardarAsociadoCuenta();
        registroMovimiento.guardarMovimientos();

        new Mensaje().showModal(Alert.AlertType.INFORMATION, " Funcionario", getStage(), "Monto depositado con exito");

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
                depositoSinSoliciutd();
            } else
            {
                Integer monto = getTotal();
                String folioAso = txtfFolio.getText().toUpperCase();
                Integer cuentaId = registroCuenta.getIdCuentaByNombre((String) mcbCuentas.getSelectedItem());
                Movimiento movimiento = new Movimiento(folioAso, cuentaId, monto);

                registroAsociadoCuenta.depositarDinero(folioAso, cuentaId, monto);
                registroMovimiento.agregarMovimientoDeposito(movimiento);
                registroMovimiento.guardarMovimientos();
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
        cargarSolicitudDepositos();
        setSpnValueFactories();
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
        Cuenta cuenta = registroCuenta.buscarCuentaNombre(mcbCuentas.getSelectedItem().toString());
        ArrayList<Integer> solicitudDepositosMontos = registroSolicitudDeposito.getDepositosPorFolioYId(txtfFolio.getText(), cuenta.getId());

        if (cuenta != null)
        {
            if (!solicitudDepositosMontos.isEmpty())
            {
                mcbDepositos.getItems().addAll(solicitudDepositosMontos);
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
        registroMovimiento = (RegistroMovimiento) AppContext.getInstance().get("movimientos");
        registroMovimiento.cargarMovimientos();
    }

    private void cargarRegistroMovimiento() {

    }

    private void limpiarCombobox() {
        mcbDepositos.getItems().clear();
        mcbCuentas.getItems().clear();
    }
}
